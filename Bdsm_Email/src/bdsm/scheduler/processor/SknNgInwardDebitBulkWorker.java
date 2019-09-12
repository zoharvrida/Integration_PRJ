package bdsm.scheduler.processor;


import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;

import bdsm.model.BillerStandingInstruction;
import bdsm.fcr.model.ChAcctMast;
import bdsm.fcr.model.CiCustMast;
import bdsm.fcr.service.AccountService;
import bdsm.fcr.service.CustomerService;
import bdsm.model.SknNgInOutDebitBulkDKE;
import bdsm.model.SknNgInOutDebitBulkDetail;
import bdsm.model.SknNgInOutDebitBulkFooter;
import bdsm.model.SknNgInOutDebitBulkHeader;
import bdsm.model.TransactionParameter;
import bdsm.scheduler.MapKey;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.dao.SknNgInwardDebitWorkerDAO;
import bdsm.scheduler.dao.TmpBdgwDao;
import bdsm.scheduler.dao.TmpGefuResponsDao;
import bdsm.scheduler.exception.FIXException;
import bdsm.scheduler.model.TmpBdgwDetail;
import bdsm.scheduler.model.TmpBdgwFooter;
import bdsm.scheduler.model.TmpBdgwHeader;
import bdsm.scheduler.model.TmpBdgwPK;
import bdsm.scheduler.model.TmpGefuRespons;
import bdsm.scheduler.model.TmpGefuResponsPK;
import bdsm.service.SknNgInwardDebitBulkService;
import bdsm.util.BdsmUtil;
import bdsm.util.oracle.DateUtility;
import bdsmhost.dao.ParameterDao;
import bdsmhost.dao.SknNgInOutDebitBulkDAO;
import bdsmhost.dao.TransactionParameterDao;
import bdsmhost.dao.BillerStandingInstructionDAO;
import bdsmhost.fcr.dao.BaBankMastDAO;
import bdsmhost.fcr.dao.ChAcctMastDAO;
import bdsmhost.fcr.dao.CiCustMastDao;


/**
 * @author v00019372
 */
public class SknNgInwardDebitBulkWorker extends BaseProcessor {
	private static String MODULE_NAME = "GEFU_SKNNG_DPIB";
	private static String FCR_TABLE_HEADER = "EXT_CAWPAYSERV_HEADERRECDTO";
	private static String FCR_TABLE_DETAIL = "EXT_CAWPAYSERV_DETAILRECDTO";
	private static String FCR_TABLE_FOOTER = "EXT_CAWPAYSERV_FOOTERRECDTO";
	private static String DB_PACKAGE_NAME = "PKG_SKNNG_BULK";
	private static String DB_UPDATE_METHOD_NAME = "UPDATE_IN_OUT_DEBIT";
	private static String MIN_DATE = "minDate";
	private static String MAX_DATE = "maxDate";
	private static String DATE_ONLY_OF_BILLING = "dateOnlyOfBillingDate";
	
	private SknNgInwardDebitWorkerDAO inwardDebitDAO;
	private Date currentDate = new Date();
	private Date businessDate;
	private Integer SKNCentralBranchCode;
	private TransactionParameter trxParam;
	private boolean isBlank;
	

	public SknNgInwardDebitBulkWorker(Map<? extends String, ? extends Object> context) {
		super(context);
	}

	
	@Override
	protected boolean doExecute() throws Exception {
		String batchNo = this.context.get(MapKey.batchNo).toString();
		
		if (StatusDefinition.UNAUTHORIZED.equals(this.context.get(MapKey.status))) {
			this.getLogger().info("[BEGIN] Read Incoming DPIB");
			
			String param5 = this.context.get(MapKey.param5).toString();
			BufferedReader rd = new BufferedReader(new FileReader(param5));
			String line;
			int ctrRow, ctrRecord;
			long lBDGWDetails = 1L;
			BigDecimal totalBDGWAmount, totalBDGWAmountFee;
			
			
			SknNgInwardDebitBulkService service = new SknNgInwardDebitBulkService();
			SknNgInOutDebitBulkHeader modelHeader = null;
			SknNgInOutDebitBulkDKE modelDKE = null;
			SknNgInOutDebitBulkDetail modelDetail = null;
			SknNgInOutDebitBulkFooter modelFooter = null;
			
			ParameterDao parameterDAO = new ParameterDao(this.session);
			BillerStandingInstructionDAO SIDAO = new BillerStandingInstructionDAO(this.session);
			this.inwardDebitDAO = new SknNgInwardDebitWorkerDAO(this.session);
			SknNgInOutDebitBulkDAO debitBulkDAO = new SknNgInOutDebitBulkDAO(this.session);
			TmpBdgwDao bdgwDAO = new TmpBdgwDao(this.session);
			
			
			this.SKNCentralBranchCode = parameterDAO.get("SKN.CENTER_BRANCH_CODE").getValue();
			this.trxParam = (new TransactionParameterDao(this.session)).getByModuleName(this.context.get(MapKey.templateName).toString());
			this.businessDate = (new BaBankMastDAO(this.session)).get().getBusinessDate();
			
			
			this.isBlank = true;
			ctrRow = ctrRecord = 0;
			totalBDGWAmount = totalBDGWAmountFee = BigDecimal.ZERO;
			try {
				while ((line = rd.readLine()) != null) {
					ctrRow++;
					if (line.trim().length() == 0)
						continue;
					
					switch (line.charAt(0)) {
						case '0' : {
								if (modelHeader != null)
									throwFIXException("LINE FOOTER IS MISSING", ctrRow);
								
								modelHeader = service.parseToHeader(line, batchNo, ++ctrRecord);
								debitBulkDAO.insert(modelHeader);
								
								modelFooter = null;
							}
							break;
						case '1' : {
								if (modelHeader == null)
									throwFIXException("LINE HEADER IS MISSING", ctrRow);
								
								modelDKE = service.parseToDKE(line, batchNo, ++ctrRecord);
								modelDKE.setParentRecordId(modelHeader.getCompositeId().getRecordId());
								
								if (debitBulkDAO.isExistBatchDKE(modelHeader.getTanggalBatch(), modelDKE.getSOR()))
									modelDKE.setValid(Boolean.FALSE);
								else
									modelDKE.setValid(Boolean.TRUE);
									
								debitBulkDAO.insert(modelDKE);
							}
							break;
						case '2' : {
								if (modelHeader == null)
									throwFIXException("LINE HEADER IS MISSING", ctrRow);
								else if (modelDKE == null)
									throwFIXException("LINE DKE IS MISSING", ctrRow);
								
								modelDetail = service.parseToDetail(line, batchNo, ++ctrRecord);
								modelDetail.setParentRecordId(modelDKE.getCompositeId().getRecordId());
								debitBulkDAO.insert(modelDetail);
								
								if (modelDKE.isValid()) {
									BillerStandingInstruction SI = SIDAO.getById(modelDetail.getNomorReferensi().trim());
									if (validateAgainstStandingInstruction(SI, modelDetail, modelDKE, this.businessDate)) {
										if (this.isBlank)
											this.isBlank = false;
										
										if (modelHeader.getFCRBatchNo() == null)
											modelHeader.setFCRBatchNo(batchNo);
										
										totalBDGWAmount = totalBDGWAmount.add(SknNgInwardDebitBulkService.parseNominalToBigDecimal(modelDetail.getNominal()));
										totalBDGWAmountFee = totalBDGWAmountFee.add(this.trxParam.getFeeLLG());
										
										TmpBdgwDetail bDetail = this.createBDGWDetail(batchNo, ++lBDGWDetails, modelDetail, modelDKE.getSOR(), 1L); 
										bdgwDAO.insert(bDetail);
										
										modelDetail.setFCRRecordId(bDetail.getRecordId());
									}
								}
							}
							break;
						case '3' : {
								if (modelHeader == null)
									throwFIXException("LINE HEADER IS MISSING", ctrRow);
								else if (modelDKE == null)
									throwFIXException("LINE DKE IS MISSING", ctrRow);
								else if (modelDetail == null)
									throwFIXException("LINE DETAIL IS MISSING", ctrRow);
								
								modelFooter = service.parseToFooter(line, batchNo, ++ctrRecord);
								modelFooter.setParentRecordId(modelHeader.getCompositeId().getRecordId());
								
								debitBulkDAO.insert(modelFooter);
								
								// finish for one batch
								modelHeader = null; modelDKE = null; modelDetail = null;
							}
							break;
						default :
							throwFIXException("FIRST CHARACTER MUST BE 0(HEADER), 1(DKE), 2(DETAIL) OR 3(FOOTER)", ctrRow);
					}
				}
			}
			finally {
				rd.close();
			}
			
			if (this.isBlank == false) {
				bdgwDAO.insert(this.createBDGWHeader(batchNo, 1, FilenameUtils.getBaseName(param5), batchNo, totalBDGWAmount, totalBDGWAmountFee));
				bdgwDAO.insert(this.createBDGWFooter(batchNo, lBDGWDetails + 1, lBDGWDetails - 1, 1L));
			}
		}
		else if (StatusDefinition.AUTHORIZED.equals(this.context.get(MapKey.status))) {
			// Process GEFU
			(new SknNgInwardDebitBulkWorkerDAO(this.session)).runProcessGEFU(batchNo, this.trxParam.getCostCenter());
			
			if (this.isBlank == false) {
				// Insert gefu response
				TmpGefuResponsPK pk = new TmpGefuResponsPK();
				pk.setOcpackage(DB_PACKAGE_NAME);
				pk.setOcfunction(DB_UPDATE_METHOD_NAME);
				pk.setBatchNo(this.context.get(MapKey.batchNo).toString());
				TmpGefuRespons GEFUResponse = new TmpGefuRespons();
				GEFUResponse.setCompositeId(pk);
				GEFUResponse.setModuleDesc(" ");
				/*
				GEFUResponse.setInboxid(inboxIdRequester);
				GEFUResponse.setIdScheduler(idSchedulerXtract);
				GEFUResponse.setModuleDesc(MODULE_DESCRIPTION);
				*/
				(new TmpGefuResponsDao(this.session)).insert(GEFUResponse);
			}
		}
		
		this.getLogger().info("Saving all record done");
		
		return true;
	}
	
	protected boolean validateAgainstStandingInstruction(BillerStandingInstruction SI, SknNgInOutDebitBulkDetail sDetail, 
				SknNgInOutDebitBulkDKE sDKE, Date businessDate) throws Exception {
		boolean valid = false;
		CustomerService customerService = new CustomerService();
		AccountService acctService = new AccountService();
		BigDecimal nominal = SknNgInwardDebitBulkService.parseNominalToBigDecimal(sDetail.getNominal());
		CiCustMast customer;
		ChAcctMast account;
		
		customerService.setCiCustMastDAO(new CiCustMastDao(this.session));
		acctService.setFCRChAcctMastDAO(new ChAcctMastDAO(this.session));
		
		do {
			/* Standing Instruction Existence */
			if ((SI == null) || ("X".equalsIgnoreCase(SI.getFlagStatus()))) {
				setReject(sDetail, "7", "54", "Invalid Standing Instruction ID");
				continue;
			};
			
			/* Standing Instruction Time Validity */
			if ((SI.getValidUntil() != null) && (businessDate.after(SI.getValidUntil()))) {
				setReject(sDetail, "7", "54", "Expired Standing Instruction");
				continue;
			} 
			
			/* Standing Instruction Periodic */
			if (isValidBillingDate(SI, businessDate) == false) {
				setReject(sDetail, "7", "54", "Invalid Billing Date Instruction");
				continue;
			}
			
			/* Biller Account Validity */
			if (
					(sDKE.getIdentitasPesertaPengirimAsal().equals(SI.getBillerBankName()) == false) ||
					(Long.parseLong(sDKE.getNomorRekeningNasabahPenagih().trim()) != Long.parseLong(SI.getBillerAccountNo().trim()))
				) {
				setReject(sDetail, "7", "55", "Mismatch Account No And/Or Account Name of Biller");
				continue;
			}
			
			/* Billed Account Validity */
			account = acctService.getByAccountNo(sDetail.getNomorRekeningNasabahTertagih().trim());
			if ((account == null) || (account.getCodAcctStat().intValue() != 8)) {
				setReject(sDetail, "7", "52", "Invalid Account No");
				continue;
			}
			customer = customerService.getByCustomerId(account.getCodCust());
			if (
					(customer == null) ||
					(customer.getNamCustShrt().trim().equalsIgnoreCase(sDetail.getNamaNasabahTertagih().trim()) == false) ||
					(SI.getAccountNo().equals(sDetail.getNomorRekeningNasabahTertagih().trim()) == false)
				) {
				setReject(sDetail, "7", "53", "Mismatch Account No And/Or Account Name");
				continue;
			}
			
			/* Nominal Validity */
			if ((("F".equals(SI.getNominalType())) && (nominal.equals(SI.getNominal()))) ||
				(("M".equals(SI.getNominalType())) && (nominal.compareTo(SI.getNominal()) < 1)));
			else {
				setReject(sDetail, "7", "55", "Invalid Nominal Amount");
				continue;
			}
			
			
			valid = true;
		}
		while (false);
		
		return valid;
	}
	
	
	protected TmpBdgwHeader createBDGWHeader(String FCRBatchNo, long recordId, String desc1, String desc2, BigDecimal totalAmount, BigDecimal totalAmountFee) {
		TmpBdgwHeader bHeader = new TmpBdgwHeader();
		
		bHeader.setId(new TmpBdgwPK(recordId, FCRBatchNo));
		bHeader.setModuleName(MODULE_NAME);
		bHeader.setTarget(FCR_TABLE_HEADER);
		bHeader.setDateTimeProcessStart(this.currentDate);
		bHeader.setProfileName("IFSC2MD");
		bHeader.setBusinessDate(DateUtility.DATE_FORMAT_DDMMYYYY.format(this.businessDate));
		bHeader.setInterfaceType("02");
		bHeader.setGLNo(BdsmUtil.leftPad(this.trxParam.getGLNo(), 12, '0'));
		bHeader.setCustomerCenter(this.trxParam.getCostCenter());
		bHeader.setApplicationCode("40");
		bHeader.setAccountNo(bHeader.getGLNo());
		bHeader.setTransactionCurrency("000");
		bHeader.setAmount(BdsmUtil.convertMoneyToString(totalAmount, 16));
		bHeader.setLocalCurrencyAmountOfTransaction(bHeader.getAmount());
		bHeader.setAmountFee(BdsmUtil.convertMoneyToString(totalAmountFee, 16).trim());
		bHeader.setDescription1(desc1);
		bHeader.setDescription2(desc2);
		bHeader.setStatus(StatusDefinition.UNAUTHORIZED);
		bHeader.setRecordStatus("1");
		bHeader.setFeeProcessingIndicator("S");
		bHeader.setDescription3(" ");
		
		try {
			bHeader.setLength(Long.valueOf(bHeader.getDataLength()));
		}
		catch (Exception ex) {
			bHeader.setLength(188L);
		}
		
		return bHeader;
	}
	
	protected TmpBdgwDetail createBDGWDetail(String FCRBatchNo, long recordId, SknNgInOutDebitBulkDetail sDetail, String SOR, long parentRecordId) {
		String accountNo = sDetail.getNomorRekeningNasabahTertagih().trim();
		
		TmpBdgwDetail bDetail = new TmpBdgwDetail();
		bDetail.setId(new TmpBdgwPK(recordId, FCRBatchNo));
		bDetail.setModuleName(MODULE_NAME);
		bDetail.setTarget(FCR_TABLE_DETAIL);
		bDetail.setDateTimeProcessStart(this.currentDate);
		bDetail.setTransactionCode("0000");
		bDetail.setApplicationCode("00");
		bDetail.setAccountNo(accountNo.length() < 12? BdsmUtil.leftPad(accountNo, 12, '0'): accountNo);
		bDetail.setTransactionAmount(BdsmUtil.convertMoneyToString(new BigDecimal(sDetail.getNominal().trim().replace(",", ".")), 16).trim());
		bDetail.setTransactionCurrency("000");
		bDetail.setLocalCurrencyAmountOfTransaction(bDetail.getTransactionAmount());
		bDetail.setForeignExchangeRate(BdsmUtil.getLeadingZeroStringFromNumber(BigDecimal.ONE, 13, 7, false));
		bDetail.setCustomerCenter("07101");
		bDetail.setReference(sDetail.getNomorReferensi().trim());
		bDetail.setDescription1("SOR:" + SOR + ", Ref:" + sDetail.getNomorReferensi());
		bDetail.setDescription2("ID PEL:" + sDetail.getNomorIdPelanggan());
		bDetail.setAmountFee(BdsmUtil.convertMoneyToString(BigDecimal.ZERO, 16).trim());
		bDetail.setStatus(" ");
		bDetail.setRecordStatus("1");
		bDetail.setParentRecordId(parentRecordId);
		
		try {
			bDetail.setLength(Long.valueOf(bDetail.getDataLength()));
		}
		catch (Exception ex) {
			bDetail.setLength(204L);
		}
		
		return bDetail;
	}
	
	protected TmpBdgwFooter createBDGWFooter(String FCRBatchNo, long recordId, long totalDetail, long parentRecordId) {
		TmpBdgwFooter bFooter = new TmpBdgwFooter();
		
		bFooter.setId(new TmpBdgwPK(recordId, FCRBatchNo));
		bFooter.setModuleName(MODULE_NAME);
		bFooter.setTarget(FCR_TABLE_FOOTER);
		bFooter.setDateTimeProcessStart(this.currentDate);
		bFooter.setNoRecord(BdsmUtil.getLeadingZeroStringFromNumber(Long.valueOf(totalDetail + 2), 10, 0, false));
		bFooter.setRecordStatus("1");
		bFooter.setParentRecordId(parentRecordId);
		
		try {
			bFooter.setLength(Long.valueOf(bFooter.getDataLength()));
		}
		catch (Exception ex) {
			bFooter.setLength(12L);
		}
		
		return bFooter;
	}

	protected boolean isValidBillingDate(BillerStandingInstruction SI, Date billingDate) throws Exception {
		boolean result = false;
		Map<String, Object> mapDate = getRealPeriodDate(SI, billingDate);
		Date minDate, maxDate, nextDate;
		Date date1, date2;
		
		minDate = (Date) mapDate.get(MIN_DATE);
		maxDate = (Date) mapDate.get(MAX_DATE);
		nextDate = null;
		date1 = DateUtils.addDays(minDate, -1);
		date2 = DateUtils.addDays(maxDate, 1);
		
		result = (billingDate.after(date1) && billingDate.before(date2)); // restricted date validation
		
		if (result == false) { // check if next working day
			if (billingDate.after(maxDate)) {
				nextDate = this.inwardDebitDAO.getClearingBranchNextWorkingDate(this.SKNCentralBranchCode, DateUtils.addDays(maxDate, -1));
				if (DateUtils.isSameDay(billingDate, nextDate)) {
					result = true;
					date2 = DateUtils.addDays(nextDate, 1);
				}
			}
		}
		
		// check if already paid in this period (for monthly)
		if ((result == true) && (BillerStandingInstruction.PERIODIC_MONTHLY.equals(SI.getPaymentPeriodicType())) && (SI.getLastDebited() != null))
			result = (SI.getLastDebited().after(date1) && (SI.getLastDebited().before(date2))) == false;
		
		return result;
	}
	
	protected static Map<String, Object> getRealPeriodDate(BillerStandingInstruction SI, Date billingDate) throws Exception {
		Map<String, Object> resultMap = new java.util.HashMap<String, Object>(0);
		Calendar c = Calendar.getInstance();
		c.setTime(billingDate);
		Date date1, date2;
		int date = c.get(Calendar.DATE);
		
		if (SI.getPaymentMinDate() <= SI.getPaymentMaxDate()) { // min and max is in the same month
			resultMap.put(MIN_DATE, setValidDate(billingDate, SI.getPaymentMinDate()));
			resultMap.put(MAX_DATE, setValidDate(billingDate, SI.getPaymentMaxDate()));
		}
		else { // min is in the prev month of max
			if (date >= SI.getPaymentMinDate()) {
				date1 = setValidDate(billingDate, SI.getPaymentMinDate());
				resultMap.put(MIN_DATE, date1);
				date2 = DateUtils.addDays(DateUtility.getLastDayOfTheMonth(date1), 1); // 1st day of the next month
				resultMap.put(MAX_DATE, setValidDate(date2, SI.getPaymentMaxDate()));
			}
			else {
				date2 = setValidDate(billingDate, SI.getPaymentMaxDate());
				resultMap.put(MAX_DATE, date2);
				date1 = DateUtils.addDays(DateUtils.setDays(date2, 1), -1); // last day of the current month
				resultMap.put(MIN_DATE, setValidDate(date1, SI.getPaymentMinDate()));
			}
		}
		resultMap.put(DATE_ONLY_OF_BILLING, date);
		
		return resultMap;
	}
	
	protected static Date setValidDate(Date inputDate, int date) throws Exception {
		Date resultDate;
		
		try {
			resultDate = DateUtils.setDays(inputDate, date);
		}
		catch (Exception ex) {
			if ((ex instanceof IllegalArgumentException) && ("DAY_OF_MONTH".equals(ex.getMessage())))
				resultDate = DateUtility.getLastDayOfTheMonth(inputDate);
			else
				throw ex;
		}
		
		return resultDate;
	}
	
	protected static void setReject(SknNgInOutDebitBulkDetail model, String recordStatus, String rejectCode, String comments) {
		model.setRecordStatus(recordStatus);
		model.setComments(comments);
		model.setRejectCode(rejectCode);
	}
	
	protected static void throwFIXException(String message, int line) throws Exception {
		if (line == -1)
			throw new FIXException(message);
		else
			throw new FIXException(message + " (Line " + line + ")");
	}

}


class SknNgInwardDebitBulkWorkerDAO implements Work {
	private static Logger LOGGER = Logger.getLogger(SknNgInwardDebitBulkWorkerDAO.class);
	private static final String CALL_PACKAGE = "{ CALL PKG_SKNNG_BULK.PROCESS_INWARD_DEBIT(?, ?) }";
	private Session session;
	private String FCRBatchNo;
	private String branchCode;
	
	
	public SknNgInwardDebitBulkWorkerDAO(Session session) {
		this.session = session;
	}

	
	@Override
	public void execute(Connection connection) throws SQLException {
		LOGGER.info("running FCR Batch No: " + this.FCRBatchNo);
		
		CallableStatement stmt = connection.prepareCall(CALL_PACKAGE);
		stmt.setString(1, this.FCRBatchNo);
		stmt.setString(2, this.branchCode);
		
		stmt.executeUpdate();
	}
	
	public void runProcessGEFU(String FCRBatchNo, String branchCode) {
		this.FCRBatchNo = FCRBatchNo;
		this.branchCode = branchCode;
		
		this.session.doWork(this);
	}
	
}
