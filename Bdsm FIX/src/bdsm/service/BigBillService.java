package bdsm.service;


import java.io.BufferedReader;
import java.util.Map;
import java.util.StringTokenizer;

import org.hibernate.Session;

import bdsm.model.BigBillTrx;
import bdsm.scheduler.dao.TmpBigBillTrxFlgDao;
import bdsm.scheduler.processor.BigBillExtractWorker;
import bdsmhost.dao.BigBillTrxDAO;


/**
 * @author v00019372
 */
public class BigBillService {
    /**
     * 
     */
    protected static String TIMEOUT_CODE = "TO";
    /**
     * 
     */
    protected static String INVALID_AMOUNT = "IA";
	
	
    /**
     * 
     * @param reader
     * @param session
     * @return
     * @throws Exception
     */
    public static String doParsingResult(BufferedReader reader, Session session) throws Exception {
		String[] fieldNames = {"idPelanggan", "typeTransaction", "recordId", "amountFlagging", "responseCode", "responseDescription", "amount", 
									"nama", "periode", "tarifDaya", "KWH", "sisaTunggakan"};
		
		String batchNo = null;
		BigBillTrxDAO bigBillTrxDAO = new BigBillTrxDAO(session);
		Map<String, String> dataMap = new java.util.HashMap<String, String>();
		String line, token, idPelanggan, typeTransaction;
		StringTokenizer st;
		int row = 0;
		
		while ((line = reader.readLine()) != null) {
			row++;
			st = new StringTokenizer(line.replace(";", "; "), ";");
			
			if (row == 1)
				if (st.countTokens() < 3)
					throw new Exception(line);
			
			
			dataMap.clear();
			int i=0;
			
			while (st.hasMoreTokens()) {
				if ((token = st.nextToken().trim()).equals("") == false)
					dataMap.put(fieldNames[i], token);
				
				i++;
			}
			
			BigBillTrx bigBill = bigBillTrxDAO.getByRecordId(dataMap.get("recordId"));
			if (bigBill != null) {
				idPelanggan = dataMap.get("idPelanggan");
				typeTransaction = dataMap.get("typeTransaction");
				
				if (batchNo == null)
					batchNo = new TmpBigBillTrxFlgDao(session).get(Long.valueOf(bigBill.getRecordId())).getBatchNo();
				
				if ((bigBill.getCustomerIdPelanggan().equals(idPelanggan)) && (bigBill.getTypeTransaction().equals(typeTransaction))) {
					// responseCode
					if (dataMap.containsKey("responseCode")) {
						bigBill.setResponStatus(dataMap.get("responseCode"));
						if (bigBill.getResponStatus().equals(TIMEOUT_CODE))
							bigBill.setRetry((bigBill.getRetry() == null? 0: bigBill.getRetry()) + 1);
						else if ((bigBill.getResponStatus().equals(INVALID_AMOUNT)) && (!dataMap.containsKey("amount"))) {
							String keySearch = "NEW AMT:";
							token = dataMap.get("responseDescription").toString();
							int pos = token.toUpperCase().indexOf(keySearch);
							bigBill.setAmountTagihan(new java.math.BigDecimal(token.substring(pos + keySearch.length())));
						}
					}
					
					// responseDescription
					if (dataMap.containsKey("responseDescription"))
						bigBill.setRemarks(dataMap.get("responseDescription"));
					
					// amount
					if (dataMap.containsKey("amount"))
						bigBill.setAmountTagihan(new java.math.BigDecimal(dataMap.get("amount")));
					
					// periode
					if (dataMap.containsKey("periode"))
						bigBill.setPeriode(dataMap.get("periode"));
					
					// tarifDaya
					if (dataMap.containsKey("tarifDaya"))
						bigBill.setTarifDaya(dataMap.get("tarifDaya"));
					
					// KWH
					if (dataMap.containsKey("KWH")) {
						token = dataMap.get("KWH").replaceAll("[-\\s]", "|");
						String[] arrStr = token.split("\\|");
						for (String s : arrStr)
							if (s.trim().equals("") == false) {
								if (bigBill.getKWHAwal() == null)
									bigBill.setKWHAwal(s.trim());
								else if (bigBill.getKWHAkhir() == null)
									bigBill.setKWHAkhir(s.trim());
								else
									break;
							}
					}
					
					// sisaTunggakan
					if (dataMap.containsKey("sisaTunggakan"))
						bigBill.setSisaTunggakan(dataMap.get("sisaTunggakan"));
					
					
					// recordStatus
					if (bigBill.getResponStatus().equals(TIMEOUT_CODE) == false) {
						if (BigBillExtractWorker.TYPE_INQUIRY.equals(bigBill.getTypeTransaction()))
							bigBill.setRecordStatus(BigBillExtractWorker.INQUIRY_RESPONDED);
						else if (BigBillExtractWorker.TYPE_FLAGGING.equals(bigBill.getTypeTransaction()))
							bigBill.setRecordStatus(BigBillExtractWorker.FLAGGING_RESPONDED);
					}
					else {
						if (BigBillExtractWorker.TYPE_INQUIRY.equals(bigBill.getTypeTransaction()))
							bigBill.setRecordStatus(BigBillExtractWorker.INQUIRY_REQUEST);
						else if (BigBillExtractWorker.TYPE_FLAGGING.equals(bigBill.getTypeTransaction()))
							bigBill.setRecordStatus(BigBillExtractWorker.FLAGGING_REQUEST);
					}
				}
			}
		}
		
		return batchNo;
	}

}
