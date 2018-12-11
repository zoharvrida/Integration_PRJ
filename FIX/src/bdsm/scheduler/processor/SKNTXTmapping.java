/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.processor;

import bdsm.scheduler.MapKey;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.dao.SKNIncomingCreditDetailsDao;
import bdsm.scheduler.dao.SKNIncomingCreditFooterDao;
import bdsm.scheduler.model.SKNIncomingCreditDetails;
import bdsm.scheduler.model.SKNIncomingCreditFooter;
import bdsm.scheduler.model.SknNgIncomingCreditFPK;
import bdsm.util.BdsmUtil;
import bdsm.util.CRC16;
import bdsmhost.dao.FcrBaBankMastDao;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author v00019722
 */
public class SKNTXTmapping extends SKNRespGenerator {

	private String clearingDate;
	private HashMap textSetting;
	private static String FORMAT_G1_FILENAME_HEADER = "skn_gen1_credit.properties";

    /**
     * 
     * @param context
     */
    public SKNTXTmapping(Map context) {
		super(context);
	}

	private StringBuilder SKNdetailsTXT_gen(String delimiter, Integer count) {

		//getLogger().debug("(SKN)DETAIL ID_BATCH reps Details :" + BatchID);
		SKNIncomingCreditDetailsDao detailDao = new SKNIncomingCreditDetailsDao(session);
		//TmpSknngInOutCreditDetailDAO detailGen2Dao = new TmpSknngInOutCreditDetailDAO(session);
		StringBuilder sbDetails = new StringBuilder();
		String Dates = null;
		//detailGen2Dao.get(null);
		try {
			List<SKNIncomingCreditDetails> d = new ArrayList();
			//getLogger().debug("BEFORE PARSING DETAILS SKN_GEN1 :" + count);
			try {
				d = detailDao.getDetails(count, BatchID);
				//getLogger().info(d);
			} catch (Exception e) {
				getLogger().info("GET ERROR :" + e);
			}
			loadTextSetting();
			sbDetails.append(nullTo(d.get(0).getClearingDate(), "ClearingDate"));
			sbDetails.append(nullTo(d.get(0).getReferenceNo(), "ReferenceNo")).append(nullTo(d.get(0).getRemitterName(), "SenderName"));
			sbDetails.append(nullTo(d.get(0).getBeneficiaryName(), "BeneficiaryName")).append(nullTo(d.get(0).getBeneficiaryAccount(), "BeneficiaryAccountNo")).append(nullTo(d.get(0).getRemarks(), "Remarks"));
			String filterAmount = nullTo(d.get(0).getAmount(), "Amount");

			StringBuilder AmountToString = new StringBuilder();
			StringTokenizer tokenFilter = new StringTokenizer(filterAmount, ".");
			AmountToString.append(tokenFilter.nextToken());
			AmountToString.append(tokenFilter.nextToken());

			//getLogger().info("AMOUNT : " + AmountToString);
			
			StringTokenizer tokenSAmount = new StringTokenizer(textSetting.get("Amount").toString(),":");
			
			sbDetails.append(BdsmUtil.leftPad(AmountToString.toString(), Integer.parseInt(tokenSAmount.nextToken()),'0'));
			sbDetails.append(nullTo(d.get(0).getReceiverBankCode(), "BeneficiaryBankCode")).append(nullTo(d.get(0).getSenderBICode(), "SenderBIOfficeCode"));
			sbDetails.append(nullTo(d.get(0).getSenderSknCoordinatorCode(), "SenderSectorCode")).append(nullTo(d.get(0).getTransactionCode(), "TransactionCode")).append(nullTo(d.get(0).getBankType(), "BankType"));
			sbDetails.append(nullTo(d.get(0).getRemitterResidencyStatus(), "SenderResidencyStatus")).append(nullTo(d.get(0).getRemitterCitizenshipStatus(), "SenderCitizenshipStatus")).append(nullTo(d.get(0).getBeneficiaryProvinceCode(), "BeneficiaryProvinceCode"));
			sbDetails.append(nullTo(d.get(0).getBeneficiaryCityCode(), "BeneficiaryCityCode")).append(nullTo(d.get(0).getReceiverSknCoordinatorCode(), "BeneficiarySectorCode")).append(nullTo(d.get(0).getSenderClearingCode(), "SenderRoutingCode"));
			sbDetails.append(nullTo(d.get(0).getSor(), "SOR"));

			CrcCheck.append(sbDetails);
			//crcCheck.append(sbDetails);
			//getLogger().info("CRCNYA :" + CrcCheck);
			//getLogger().debug("DETAILS SKN_GEN1:" + sbDetails.toString());
		} catch (Exception e) {
			getLogger().error("ERROR PARSING DETAIL SKN_GEN1:" + e,e);
		}
		return sbDetails;
	}

	private StringBuilder SKNfooterTXT_gen(String delimiter, Integer count) {

		//getLogger().debug("(SKN)Footer ID_BATCH reps Details :" + BatchID);
		SKNIncomingCreditFooterDao footerDao = new SKNIncomingCreditFooterDao(session);

		SKNIncomingCreditFooter footer = new SKNIncomingCreditFooter();
		SknNgIncomingCreditFPK pk = new SknNgIncomingCreditFPK();
		pk.setBatchNo(BatchID);

		StringBuilder sbDetails = new StringBuilder();
		//sbDetails.append("3").append("1");
		String Dates = null;
		try {
			List<SKNIncomingCreditFooter> d = new ArrayList();
			//getLogger().debug("BEFORE PARSING FOOTER SKN_GEN1 :" + count);
			try {
				d = footerDao.getModel(BatchID);
				//getLogger().info(d);
			} catch (Exception e) {
				getLogger().info("GET ERROR :" + e);
			}
			// recalculate checkSum
			//getLogger().info("CRC C :" + CrcCheck);
			Integer checkSum = CRC16.CRC16(CrcCheck.toString(), 0);
			if (checkSum.toString().equalsIgnoreCase(d.get(0).getCheckSum())) {
				sbDetails.append(d.get(0).getCheckSum());
			} else {
				sbDetails.append(StringUtils.leftPad(checkSum.toString(), 5, '0'));
				footer.setCompositeId(pk);
				footer.setCheckSum(checkSum.toString());
				footerDao.update(footer);
			}
			//getLogger().debug("FOOTER SKN_GEN1:" + sbDetails.toString());
		} catch (Exception e) {
			getLogger().error("ERROR PARSING DETAIL SKN_GEN1:" + e);
		}
		return sbDetails;
	}

    /**
     * 
     * @return
     */
    @Override
	public Integer checkNumHeader() {
		return 0;
	}

    /**
     * 
     * @param count
     * @return
     */
    @Override
	public Integer checkNumDetail(Integer count) {
		Integer num;
		SKNIncomingCreditDetailsDao detailDao = new SKNIncomingCreditDetailsDao(session);
		getLogger().info("BATCH ID :" + BatchID);
		getLogger().info("clearing Date :" + clearingDate);
		List<SKNIncomingCreditDetails> h = detailDao.getModels(clearingDate, BatchID);
		//getLogger().info("LIST :" + h);
		num = h.size();
		getLogger().info("NUMBER OF SKN DETAIL : " + num);

		this.checknumDetails = num;
		return num;
	}

    /**
     * 
     * @param count
     * @return
     */
    @Override
	public StringBuilder headerS(Integer count) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

    /**
     * 
     * @param count
     * @return
     */
    @Override
	public StringBuilder detailS(Integer count) {
		StringBuilder detail = new StringBuilder();
		//getLogger().info("ASSIGN INTO MAPPER");
		detail = SKNdetailsTXT_gen(this.delimiter, count);
		return detail;
	}

    /**
     * 
     * @return
     */
    @Override
	public Integer checkHeaderFooterSum() {
		return 1;
	}

    /**
     * 
     * @return
     */
    @Override
	public Integer checkNumFooter() {

		Integer num;
		SKNIncomingCreditFooterDao footerDao = new SKNIncomingCreditFooterDao(session);
		getLogger().info("BATCH ID :" + BatchID);
		List<SKNIncomingCreditFooter> h = footerDao.getModel(BatchID);
		//getLogger().info("LIST :" + h);
		num = h.size();
		getLogger().info("NUMBER OF SKN FOOTER : " + num);

		this.checknumFooter = num;
		return num;
	}

    /**
     * 
     * @param context
     * @return
     */
    @Override
	public boolean validate(Map context) {
		// check code branch input and template
		SimpleDateFormat sdf = new SimpleDateFormat(StatusDefinition.Skndate);
		getLogger().debug("Start Validate File Response" + context.get(MapKey.reportFileName));
		getLogger().debug("Get Parameter1 :" + context.get(MapKey.param1));
		getLogger().debug("Get Parameter2 :" + context.get(MapKey.param2)); // clearing Date 
		getLogger().debug("Get Parameter3 :" + context.get(MapKey.param3));
		getLogger().debug("Get Parameter4 :" + context.get(MapKey.param4));
		getLogger().debug("Get Parameter5 :" + context.get(MapKey.param5));
		getLogger().debug("Get parameter6 :" + context.get(MapKey.param6)); // idbatch
		getLogger().debug("Get Hidden Parameter 1 :" + context.get(MapKey.hdUserid));
		getLogger().debug("Get Hidden Parameter 2 :" + context.get(MapKey.hdcdBranch));
		getLogger().debug("Get Hidden Parameter 3 :" + context.get(MapKey.hddtProcess));
		getLogger().debug("Get Hidden Parameter 4 :" + context.get(MapKey.hdidTemplate));
		getLogger().debug("Get Hidden Parameter 5 :" + context.get(MapKey.hdnamUser));
		getLogger().debug("Get Java Class :" + context.get(MapKey.javaClass));
		getLogger().debug("Get Report ID  :" + context.get(MapKey.reportId));
		getLogger().debug("Get Report Format:" + context.get(MapKey.reportFormat));
		getLogger().debug("Get Type Fix :" + context.get(MapKey.typeFix));
		String dateTime = null;
		FcrBaBankMastDao fcrDao = new FcrBaBankMastDao(session);
		try {
			dateTime = sdf.format(fcrDao.get().getDatProcess()).toString();
			//dateTime  = SchedulerUtil.getDate("yyMMdd");
		} catch (Exception ex) {
			Logger.getLogger(SKNTXTmapping.class.getName()).log(Level.SEVERE, null, ex);
		}
		StringBuilder batchNo = new StringBuilder();
		batchNo.append(BdsmUtil.leftPad(context.get(MapKey.hdcdBranch).toString(), 5, '0')).append(dateTime).append(context.get(MapKey.param6));
		getLogger().info("BATCH NO :" + batchNo);
		BatchID = batchNo.toString();
		try {
			clearingDate = context.get(MapKey.param2).toString();
		} catch (Exception e) {
			clearingDate = dateTime;
		}
		return true;
	}

    /**
     * 
     * @param count
     * @return
     */
    @Override
	public StringBuilder footerS(Integer count) {
		StringBuilder footer = new StringBuilder();
		getLogger().info("ASSIGN INTO MAPPER");
		footer = SKNfooterTXT_gen(this.delimiter, count);
		return footer;
	}

	private String nullTo(String tonull, String Mapping) {

		String nullval = "";
		String prop = "";
		String padding = "";
		String[] getProp = new String[5];
		Integer countToken = 0;
		Integer countReal = 0;
		HashMap textLength = textSetting;
		//getLogger().info("Mapping :" + Mapping);
		StringTokenizer token = new StringTokenizer(textLength.get(Mapping).toString(), ":");
		//getLogger().info("CHECKcOUNT :" + token.countTokens());
		while (token.hasMoreTokens()) {
			countToken = token.countTokens();
			getProp[countReal] = token.nextToken().toString();
			countReal++;
			//getLogger().info("CHECK TOKEN :" + getProp[countToken]);
		}
		int maxleng;
		//getLogger().info("PROPERTY MAP :" + textLength.get(Mapping).toString());
		getProp = textLength.get(Mapping).toString().split(":");
		maxleng = Integer.parseInt(getProp[0]);
		//getLogger().info("maxlength :" + maxleng);
		if (tonull == null) {
			for (int k = 0; k < maxleng; k++) {
				nullval = nullval + " ";
			}
		} else {
			//getLogger().info("field :" + tonull);
			if (tonull.length() == maxleng) {
				String trimmer = (tonull).trim();
				if (trimmer.length() < maxleng) {
					if ("L".equals(getProp[1])) {
						if ("S".equals(getProp[2])) {
							nullval = BdsmUtil.rightPad(trimmer, Integer.parseInt(getProp[0]), ' ');
						} else {
							nullval = BdsmUtil.rightPad(trimmer, Integer.parseInt(getProp[0]), '0');
						}
					} else {
						if ("S".equals(getProp[2])) {
							nullval = BdsmUtil.leftPad(trimmer, Integer.parseInt(getProp[0]), ' ');
						} else {
							nullval = BdsmUtil.leftPad(trimmer, Integer.parseInt(getProp[0]), '0');
						}
					}
				} else if (trimmer.length() == maxleng) {
					nullval = tonull;
				} else {
					nullval = trimmer.substring(0, maxleng);
				}
				//getLogger().info("samesize :" + tonull.length());
			} else if (tonull.length() < maxleng) {
				StringBuilder sb = new StringBuilder();
				//getLogger().info("CHECK INDEX : ");
				prop = getProp[1];

				if ("L".equals(getProp[1])) {
					if ("S".equals(getProp[2])) {
						nullval = BdsmUtil.rightPad(tonull, Integer.parseInt(getProp[0]), ' ');
					} else {
						nullval = BdsmUtil.rightPad(tonull, Integer.parseInt(getProp[0]), '0');
					}
				} else {
					if ("S".equals(getProp[2])) {
						nullval = BdsmUtil.leftPad(tonull, Integer.parseInt(getProp[0]), ' ');
					} else {
						nullval = BdsmUtil.leftPad(tonull, Integer.parseInt(getProp[0]), '0');
					}
				}
				//getLogger().info("CHECK VALUE :" + nullval);
			} else if (tonull.length() > maxleng) {
				//getLogger().info("more :" + tonull.length());
				try {
					String trimmer = (tonull).trim();
					if (trimmer.length() < maxleng) {
						if ("L".equals(getProp[1])) {
							if ("S".equals(getProp[2])) {
								nullval = BdsmUtil.rightPad(trimmer, Integer.parseInt(getProp[0]), ' ');
							} else {
								nullval = BdsmUtil.rightPad(trimmer, Integer.parseInt(getProp[0]), '0');
							}
						} else {
							if ("S".equals(getProp[2])) {
								nullval = BdsmUtil.leftPad(trimmer, Integer.parseInt(getProp[0]), ' ');
							} else {
								nullval = BdsmUtil.leftPad(trimmer, Integer.parseInt(getProp[0]), '0');
							}
						}
					} else {
						nullval = trimmer.substring(0, maxleng);
					}
					//getLogger().info("OVERFLOW :" + tonull);
				} catch (Exception e) {
					//getLogger().info("INDEX BOUND :" + e.getLocalizedMessage());
				}
			}

		}
		return nullval;
	}

	private void loadTextSetting() throws Exception {
		Properties properties = new Properties();
		InputStream in = SKNTXTmapping.class.getClassLoader().getResourceAsStream(FORMAT_G1_FILENAME_HEADER);
		properties.load(in);
		textSetting = new HashMap();
		textSetting.put("ClearingDate", properties.getProperty("ClearingDate"));
		textSetting.put("ReferenceNo", properties.getProperty("ReferenceNo"));
		textSetting.put("SenderName", properties.getProperty("SenderName"));
		textSetting.put("BeneficiaryName", properties.getProperty("BeneficiaryName"));
		textSetting.put("BeneficiaryAccountNo", properties.getProperty("BeneficiaryAccountNo"));
		textSetting.put("Remarks", properties.getProperty("Remarks"));
		textSetting.put("Amount", properties.getProperty("Amount"));
		textSetting.put("BeneficiaryBankCode", properties.getProperty("BeneficiaryBankCode"));
		textSetting.put("SenderBIOfficeCode", properties.getProperty("SenderBIOfficeCode"));
		textSetting.put("SenderSectorCode", properties.getProperty("SenderSectorCode"));
		textSetting.put("TransactionCode", properties.getProperty("TransactionCode"));
		textSetting.put("BankType", properties.getProperty("BankType"));
		textSetting.put("SenderResidencyStatus", properties.getProperty("SenderResidencyStatus"));
		textSetting.put("SenderCitizenshipStatus", properties.getProperty("SenderCitizenshipStatus"));
		textSetting.put("BeneficiaryProvinceCode", properties.getProperty("BeneficiaryProvinceCode"));
		textSetting.put("BeneficiaryCityCode", properties.getProperty("BeneficiaryCityCode"));
		textSetting.put("BeneficiarySectorCode", properties.getProperty("BeneficiarySectorCode"));
		textSetting.put("SenderRoutingCode", properties.getProperty("SenderRoutingCode"));
		textSetting.put("SOR", properties.getProperty("SOR"));
		in.close();
	}
}
