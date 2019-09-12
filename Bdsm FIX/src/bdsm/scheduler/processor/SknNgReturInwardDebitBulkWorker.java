package bdsm.scheduler.processor;


import static bdsm.service.SknNgBulkService.newLine;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import bdsm.fcr.model.BaCcyCode;
import bdsm.model.SknNgInOutReturBulkDKE;
import bdsm.model.SknNgInOutReturBulkDetail;
import bdsm.model.SknNgInOutReturBulkFooter;
import bdsm.model.SknNgInOutReturBulkHeader;
import bdsm.model.TransactionParameter;
import bdsm.scheduler.MapKey;
import bdsm.scheduler.PropertyPersister;
import bdsm.scheduler.dao.TmpGefuResponsDao;
import bdsm.scheduler.dao.TmpTxnUploadDetailDAO;
import bdsm.scheduler.dao.TmpTxnUploadFooterDAO;
import bdsm.scheduler.dao.TmpTxnUploadHeaderDAO;
import bdsm.scheduler.model.TmpGefuRespons;
import bdsm.scheduler.model.TmpGefuResponsPK;
import bdsm.scheduler.model.TmpTxnUploadDetail;
import bdsm.scheduler.model.TmpTxnUploadFooter;
import bdsm.scheduler.model.TmpTxnUploadHeader;
import bdsm.service.SknNgReturInwardDebitBulkService;
import bdsm.util.BdsmUtil;
import bdsm.util.oracle.DateUtility;
import bdsmhost.dao.SknNgInOutReturBulkDKEDAO;
import bdsmhost.dao.SknNgInOutReturBulkDetailDAO;
import bdsmhost.dao.SknNgInOutReturBulkFooterDAO;
import bdsmhost.dao.SknNgInOutReturBulkHeaderDAO;
import bdsmhost.dao.TransactionParameterDao;
import bdsmhost.fcr.dao.BaBankMastDAO;
import bdsmhost.fcr.dao.BaCcyCodeDAO;


/**
 * @author v00019372
 */
public class SknNgReturInwardDebitBulkWorker extends BaseProcessor {
    /**
     * 
     */
    public static final String PREFIX = "SKNRDB";
    /**
     * 
     */
    /**
     * 
     */
    /**
     * 
     */
    protected String batchNo, userId, cdBranch;
	private static String DB_PACKAGE_NAME = "PK_GEFU";
	private static String DB_UPDATE_METHOD_NAME = "responsTxnUpload";


    /**
     * 
     * @param context
     */
    public SknNgReturInwardDebitBulkWorker(Map<? extends String, ? extends Object> context) {
		super(context);
	}


    /**
     * 
     * @return
     * @throws Exception
     */
    @Override
	protected boolean doExecute() throws Exception {
		String reportFilename = (String) this.context.get(MapKey.reportFileName);
		if (this.batchNo == null) {
			this.batchNo = this.context.get(MapKey.param6).toString();
			this.userId = this.context.get(MapKey.hdUserid).toString();
			this.cdBranch = this.context.get(MapKey.hdcdBranch).toString();
		}
		
		int crcValue = 0;
		StringBuilder sbCRC = new StringBuilder();
		
		// DAO's
		SknNgInOutReturBulkHeaderDAO headerDAO = new SknNgInOutReturBulkHeaderDAO(this.session); 
		SknNgInOutReturBulkDKEDAO DKEDAO = new SknNgInOutReturBulkDKEDAO(this.session);
		SknNgInOutReturBulkDetailDAO detailDAO = new SknNgInOutReturBulkDetailDAO(this.session);
		SknNgInOutReturBulkFooterDAO footerDAO = new SknNgInOutReturBulkFooterDAO(this.session);
		
		// Extract Data Retur
		(new WorkerDAO()).extractData();
		
		
		Writer out = new OutputStreamWriter(new FileOutputStream(new File(PropertyPersister.dirFixOut, reportFilename)));
		List<SknNgInOutReturBulkHeader> listHeader = headerDAO.listByBatchNo(this.batchNo);
		List<SknNgInOutReturBulkDKE> listDKE;
		List<SknNgInOutReturBulkDetail> listDetail;
		SknNgInOutReturBulkHeader modelHeader = null;
		SknNgInOutReturBulkDKE modelDKE;
		SknNgInOutReturBulkDetail modelDetail;
		SknNgInOutReturBulkFooter modelFooter;

		if (listHeader.size() == 0) {
			out.write("");
			out.close();
			return true;
		}
		
		
		SknNgReturInwardDebitBulkService service = new SknNgReturInwardDebitBulkService();
		String identitasPengirim = null;
		BigDecimal totalNominal = BigDecimal.ZERO;
		
		// header
		for (int h=0; h<listHeader.size(); h++) {
			modelHeader = listHeader.get(h);
			
			/* Reformatting & Write to Fix Length Buffer */
			out.write(service.formatFromHeader(modelHeader));
			out.write(newLine());

			
			listDKE = DKEDAO.listByBatchNoAndParentRecordId(this.batchNo, modelHeader.getCompositeId().getRecordId());
			// DKE
			for (int dk=0; dk<listDKE.size(); dk++) {
				modelDKE = listDKE.get(dk);
				
				/* Reformatting & Write to Fix Length Buffer */
				String lineDKE = service.formatFromDKE(modelDKE); 
				out.write(lineDKE);
				out.write(newLine());
				sbCRC.append(lineDKE);
				
				
				listDetail = detailDAO.listByBatchNoAndParentRecordId(this.batchNo, modelDKE.getCompositeId().getRecordId());
				// detail
				for (int d=0; d<listDetail.size(); d++) {
					modelDetail = listDetail.get(d);
					
					/* Reformatting & Write to Fix Length Buffer */
					String lineDetail = service.formatFromDetail(modelDetail); 
					out.write(lineDetail);
					out.write(newLine());
					sbCRC.append(lineDetail);
				}
			}
			
			
			// the footer
			/* Reformatting & Write to Fix Length Buffer */
			modelFooter = footerDAO.listByBatchNoAndParentRecordId(this.batchNo, modelHeader.getCompositeId().getRecordId()).get(0);
			crcValue = bdsm.util.CRC16.CRC16(sbCRC.toString(), 0);
			
			// update CRC to footer value
			modelFooter.setCRC(String.valueOf(crcValue));
			out.write(service.formatFromFooter(modelFooter));
			out.write(newLine());
			
			sbCRC.delete(0, sbCRC.length());
			
			if (identitasPengirim == null)
				identitasPengirim = modelHeader.getIdentitasPesertaPengirim();
			
			totalNominal = totalNominal.add(SknNgReturInwardDebitBulkService.parseNominalToBigDecimal(modelHeader.getTotalNominalDKE()));
		}
		
		out.close();
		
		
		this.doGEFU(this.batchNo, identitasPengirim, totalNominal);
		
		return true;
	}
	
    /**
     * 
     * @param FCRBatchNo
     * @param identitasPengirim
     * @param totalNominal
     */
    protected void doGEFU(String FCRBatchNo, String identitasPengirim, BigDecimal totalNominal) {
		String moduleName = "GEFU_SKNNG_DROB";
		String codUserId = "INTFCAW";
		String currency = "IDR";
		
		BaCcyCodeDAO baCcyCodeDAO = new BaCcyCodeDAO(this.session);
		TmpTxnUploadHeaderDAO txnUploadHeaderDAO = new TmpTxnUploadHeaderDAO(this.session);
		TmpTxnUploadDetailDAO txnUploadDetailDAO = new TmpTxnUploadDetailDAO(this.session);
		TmpTxnUploadFooterDAO txnUploadFooterDAO = new TmpTxnUploadFooterDAO(this.session);
		
		TransactionParameter trxParam = (new TransactionParameterDao(this.session)).getByModuleName(PREFIX);
		Map<String, String> mapCostCentre = BdsmUtil.parseKeyAndValueToMap(trxParam.getCostCenter());
		String costCentre = mapCostCentre.get(identitasPengirim);
		
		Date businessDate = (new BaBankMastDAO(this.session)).get().getBusinessDate();
		BaCcyCode baCcy = baCcyCodeDAO.getByNamCcyShort(currency);
		BigDecimal decimalMultiplier = new BigDecimal(Math.pow(10, baCcy.getCtrCcyDec()));
		int counter = 1;
		
		String[] arrGLNo = trxParam.getGLNo().split(";");
		String GLNoDebit = arrGLNo[0].trim();
		String GLNoCredit = arrGLNo[1].trim();
		
		String refNo = new StringBuilder(PREFIX)
							.append(BdsmUtil.leftPad(FCRBatchNo.replaceAll("\\D", "").substring(0, 10), 10, '0')) // get first 10 digit characters
							.append(BdsmUtil.leftPad(String.valueOf(1), 4, '0')) // 4 digit sequence no
							.toString();
		
		// Header
		counter++;
		TmpTxnUploadHeader tHeader = new TmpTxnUploadHeader(FCRBatchNo, moduleName);
		tHeader.setDateCreate(DateUtility.DATE_FORMAT_YYYYMMDD.format(businessDate));
		tHeader.generateLength();
		txnUploadHeaderDAO.insert(tHeader);
		
		
		// Detail Debit
		TmpTxnUploadDetail tDetail_D = new TmpTxnUploadDetail(tHeader, counter++);
		tDetail_D.setCodAcctNo(GLNoDebit);
		tDetail_D.setCodCcBrn(Integer.valueOf(costCentre));
		tDetail_D.setCodLob(0);
		tDetail_D.setCodDrCr(TmpTxnUploadDetail.COD_DRCR_DEBIT);
		tDetail_D.setCodTxn(TmpTxnUploadDetail.COD_TXN_GL_DEBIT);
		tDetail_D.setTypTxn(TmpTxnUploadDetail.TYPE_TXN_GL);
		tDetail_D.setCodUserId(codUserId);
		tDetail_D.setProdCod(0);
		tDetail_D.setTxnCurrency(currency);
		tDetail_D.setAmtTxnTcy(totalNominal.multiply(decimalMultiplier));
		tDetail_D.setConvRate(BigDecimal.ONE);
		tDetail_D.setReferenceNo(refNo);
		tDetail_D.setRefDocNo("1");
		tDetail_D.setAmtTxnLcy(tDetail_D.getAmtTxnTcy().multiply(tDetail_D.getConvRate()));
		tDetail_D.setTxtTxnDesc("SKNNG Retur Inward Debit Bulk");
		tDetail_D.generateLength();
		txnUploadDetailDAO.insert(tDetail_D);
		
		// Detail Credit
		TmpTxnUploadDetail tDetail_C = new TmpTxnUploadDetail(tHeader, counter++);
		tDetail_C.setCodAcctNo(GLNoCredit);
		tDetail_C.setCodCcBrn(Integer.valueOf(costCentre));
		tDetail_C.setCodLob(0);
		tDetail_C.setCodDrCr(TmpTxnUploadDetail.COD_DRCR_CREDIT);
		tDetail_C.setCodTxn(TmpTxnUploadDetail.COD_TXN_GL_CREDIT);
		tDetail_C.setTypTxn(TmpTxnUploadDetail.TYPE_TXN_GL);
		tDetail_C.setCodUserId(codUserId);
		tDetail_C.setProdCod(0);
		tDetail_C.setTxnCurrency(currency);
		tDetail_C.setAmtTxnTcy(totalNominal.multiply(decimalMultiplier));
		tDetail_C.setConvRate(BigDecimal.ONE);
		tDetail_C.setReferenceNo(refNo);
		tDetail_C.setRefDocNo("1");
		tDetail_C.setAmtTxnLcy(tDetail_C.getAmtTxnTcy().multiply(tDetail_C.getConvRate()));
		tDetail_C.setTxtTxnDesc("SKNNG Retur Inward Debit Bulk");
		tDetail_C.generateLength();
		txnUploadDetailDAO.insert(tDetail_C);
		
		
		// Footer
		TmpTxnUploadFooter tFooter = new TmpTxnUploadFooter(tHeader, counter);
		tFooter.setNoOfDr(1);
		tFooter.setAmtDr(totalNominal.multiply(decimalMultiplier));
		tFooter.setNoOfCr(1);
		tFooter.setAmtCr(totalNominal.multiply(decimalMultiplier));
		tFooter.generateLength();
		txnUploadFooterDAO.insert(tFooter);
		
		// commit first, for processGEFU package can get the data copying into FCR 
		if (this.tx != null) {
			this.tx.commit();
			this.tx = this.session.beginTransaction();
		}
		
		
		txnUploadHeaderDAO.runTxn(Arrays.asList(this.batchNo, "BDSM", "TXNUPLOAD", costCentre, "TXNUPLOAD_SKNNG_DROB_" + (String) this.context.get(MapKey.reportFileName), moduleName));
		
		TmpGefuRespons GEFUResponse = new TmpGefuRespons();
		GEFUResponse.setCompositeId(new TmpGefuResponsPK(DB_PACKAGE_NAME, DB_UPDATE_METHOD_NAME, this.batchNo));
		GEFUResponse.setModuleDesc(moduleName);
		(new TmpGefuResponsDao(this.session)).insert(GEFUResponse);
	}
	
	
	/* Inner Class */
	private class WorkerDAO implements org.hibernate.jdbc.Work {
		private String extractCallPackage = "{ CALL PKG_SKNNG_BULK.extract_retur_inward_debit(?, ?, ?) }";
		
		public void extractData() {
			session.doWork(this);
		}

		@Override
		public void execute(Connection connection) throws SQLException {
			CallableStatement cs = connection.prepareCall(this.extractCallPackage);
			
			cs.setString(1, batchNo);
			cs.setInt(2, Integer.parseInt(cdBranch));
			cs.setString(3, userId);
			
			cs.execute();
			cs.close();
		}
	}

}
