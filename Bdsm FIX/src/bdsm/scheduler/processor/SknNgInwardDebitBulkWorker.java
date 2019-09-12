package bdsm.scheduler.processor;


import static bdsm.model.BillerStandingInstruction.NOMINAL_TYPE_FIX;
import static bdsm.model.BillerStandingInstruction.NOMINAL_TYPE_MAXIMUM;
import static bdsm.model.BillerStandingInstruction.PERIODIC_MONTHLY;


import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.impl.SessionImpl;
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
import bdsm.scheduler.PropertyPersister;
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
import bdsm.service.SknNgService;
import bdsm.util.BdsmUtil;
import bdsm.util.oracle.DateUtility;
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
    /**
     * 
     */
    protected static String FCR_TABLE_HEADER = "EXT_CAWPAYSERV_HEADERRECDTO";
    /**
     * 
     */
    protected static String FCR_TABLE_DETAIL = "EXT_CAWPAYSERV_DETAILRECDTO";
    /**
     * 
     */
    protected static String FCR_TABLE_FOOTER = "EXT_CAWPAYSERV_FOOTERRECDTO";
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
	private String costCentre;
	private boolean isBlank;
	

    /**
     * 
     * @param context
     */
    public SknNgInwardDebitBulkWorker(Map<? extends String, ? extends Object> context) {
		super(context);
	}

	
    /**
     * 
     * @return
     * @throws Exception
     */
    @Override
	protected boolean doExecute() throws Exception {
		this.inwardDebitDAO = new SknNgInwardDebitWorkerDAO(this.session);
		SknNgInOutDebitBulkDAO debitBulkDAO = new SknNgInOutDebitBulkDAO(this.session);
		
		
		String batchNo = this.context.get(MapKey.batchNo).toString();
		
		if (StatusDefinition.UNAUTHORIZED.equals(this.context.get(MapKey.status))) {
			this.getLogger().info("[BEGIN] Read Incoming DPIB");
			
			String filename = this.context.get(MapKey.param5).toString();
			String branchCode = filename.split("_")[1];
			String BICBranchCode = (new SknNgInwardDebitBulkWorkerDAO(this.session)).getBICFromBranchCode(Integer.valueOf(branchCode));
			BufferedReader rd = new BufferedReader(new FileReader(filename));
			String line;
			int ctrRow, ctrRecord;
			long lBDGWDetails = 1L;
			boolean isAnyValidDKE = false;
			BigDecimal totalBDGWAmount, totalBDGWAmountFee;
			
			
			SknNgInwardDebitBulkService service = new SknNgInwardDebitBulkService();
			SknNgInOutDebitBulkHeader modelHeader = null;
			SknNgInOutDebitBulkDKE modelDKE = null;
			SknNgInOutDebitBulkDetail modelDetail = null;
			SknNgInOutDebitBulkFooter modelFooter = null;
			
			BillerStandingInstructionDAO SIDAO = new BillerStandingInstructionDAO(this.session);
			TmpBdgwDao bdgwDAO = new TmpBdgwDao(this.session);
			
			this.trxParam = (new TransactionParameterDao(this.session)).getByModuleName(this.context.get(MapKey.templateName).toString());
			Map<String, String> mapCostCentre = BdsmUtil.parseKeyAndValueToMap(trxParam.getCostCenter());
			
			this.SKNCentralBranchCode = SknNgService.BIC_CONVENT.equalsIgnoreCase(BICBranchCode)? 
					PropertyPersister.SKN_CENTER_BRANCH_CODE_CONVENT: PropertyPersister.SKN_CENTER_BRANCH_CODE_CONVENT;
			this.businessDate = (new BaBankMastDAO(this.session)).get().getBusinessDate();
			this.costCentre = BdsmUtil.leftPad(mapCostCentre.get(BICBranchCode), 5, '0');
			
			
			
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
								modelFooter = null;
							}
							break;
						case '1' : {
								if (modelHeader == null)
									throwFIXException("LINE HEADER IS MISSING", ctrRow);
								
								modelDKE = service.parseToDKE(line, batchNo, ++ctrRecord);
								modelDKE.setParentRecordId(modelHeader.getCompositeId().getRecordId());
								
								if (debitBulkDAO.isExistBatchDKE(modelHeader.getTanggalBatch(), modelDKE.getSOR())) {
									modelDKE.setValid(Boolean.FALSE);
									this.getLogger().info("DKE batchDate " + modelHeader.getTanggalBatch() + " and SOR " + modelDKE.getSOR() + " already exist");
								}
								else {
									modelDKE.setValid(Boolean.TRUE);
									debitBulkDAO.insert(modelDKE);
									
									if (!isAnyValidDKE)
										isAnyValidDKE = true;
								}
							}
							break;
						case '2' : {
								if (modelHeader == null)
									throwFIXException("LINE HEADER IS MISSING", ctrRow);
								else if (modelDKE == null)
									throwFIXException("LINE DKE IS MISSING", ctrRow);
								
								if (modelDKE.isValid()) {
									modelDetail = service.parseToDetail(line, batchNo, ++ctrRecord);
									modelDetail.setParentRecordId(modelDKE.getCompositeId().getRecordId());
									debitBulkDAO.insert(modelDetail);
									
									BillerStandingInstruction SI = SIDAO.getById(modelDetail.getNomorReferensi().trim());
									if (SI == null)
										SI = SIDAO.getByBillingNoAndBillerAccountNo(modelDetail.getNomorIdPelanggan().trim(), modelDKE.getNomorRekeningNasabahPenagih().trim());
									
									if (validateAgainstStandingInstruction(SI, modelDetail, modelDKE, this.businessDate)) {
										if (this.isBlank)
											this.isBlank = false;
										
										if (modelHeader.getFCRBatchNo() == null)
											modelHeader.setFCRBatchNo(batchNo);
										
										totalBDGWAmount = totalBDGWAmount.add(SknNgInwardDebitBulkService.parseNominalToBigDecimal(modelDetail.getNominal()));
										totalBDGWAmountFee = totalBDGWAmountFee.add(this.trxParam.getFeeLLG());
										
										TmpBdgwDetail bDetail = this.createBDGWDetail(batchNo, ++lBDGWDetails, modelDetail, SI, 1L); 
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
								
								if (isAnyValidDKE) {
									modelFooter = service.parseToFooter(line, batchNo, ++ctrRecord);
									modelFooter.setParentRecordId(modelHeader.getCompositeId().getRecordId());
									
									debitBulkDAO.insert(modelHeader);
									debitBulkDAO.insert(modelFooter);
								}
								
								// finish for one batch
								modelHeader = null; modelDKE = null; modelDetail = null;
								isAnyValidDKE = false;
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
				bdgwDAO.insert(this.createBDGWHeader(batchNo, 1, FilenameUtils.getBaseName(filename), batchNo, totalBDGWAmount, totalBDGWAmountFee));
				bdgwDAO.insert(this.createBDGWFooter(batchNo, lBDGWDetails + 1, lBDGWDetails - 1, 1L));
			}
		}
		else if (StatusDefinition.AUTHORIZED.equals(this.context.get(MapKey.status))) {
			List<SknNgInOutDebitBulkHeader> headers = debitBulkDAO.listHeaderByBatchNo(batchNo);
			for (SknNgInOutDebitBulkHeader header : headers)
				header.setStatus("RUN");
			
			// Process GEFU
			(new SknNgInwardDebitBulkWorkerDAO(this.session)).runProcessGEFU(batchNo, this.costCentre);
			
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
	
    /**
     * 
     * @param SI
     * @param sDetail
     * @param sDKE
     * @param businessDate
     * @return
     * @throws Exception
     */
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
			if (SI == null) {
				setReject(sDetail, "7", "50", "Billing Without Dealing");
				continue;
			};
			
			if (SI.getId().equals(sDetail.getNomorReferensi().trim()) == false) {
				setReject(sDetail, "7", "53", "Invalid Standing Instruction ID");
				continue;
			}
			
			/* Standing Instruction Status */
			if ("X".equalsIgnoreCase(SI.getFlagStatus())) {
				setReject(sDetail, "7", "59", "Standing Instruction Has Been Deleted");
				continue;
			};
			
			/* Standing Instruction Time Validity */
			if ((SI.getValidUntil() != null) && (businessDate.after(SI.getValidUntil()))) {
				setReject(sDetail, "7", "59", "Expired Standing Instruction");
				continue;
			} 
			
			/* Standing Instruction Periodic */
			if (isValidBillingDate(SI, businessDate) == false) {
				setReject(sDetail, "7", "58", "Invalid Billing Date Instruction");
				continue;
			}
			
			/* Biller Account Validity */
			if (sDKE.getIdentitasPesertaPengirimAsal().equals(SI.getBillerBankName()) == false) {
				setReject(sDetail, "7", "56", "Mismatch Biller Bank Name");
				continue;
			}
			if (Long.parseLong(sDKE.getNomorRekeningNasabahPenagih().trim()) != Long.parseLong(SI.getBillerAccountNo().trim())) {
				setReject(sDetail, "7", "55", "Mismatch Biller Account No.");
				continue;
			}
			if (sDKE.getNamaNasabahPenagih().trim().replaceAll("\\s+", " ").equalsIgnoreCase(SI.getBillerName().trim().replaceAll("\\s+", " ")) == false) {
				setReject(sDetail, "7", "54", "Mismatch Biller Name");
				continue;
			}
			
			/* Billed Account Validity */
			if (sDetail.getNomorIdPelanggan().trim().equals(SI.getBillingNo().trim()) == false) {
				setReject(sDetail, "7", "60", "Invalid Billing No.");
				continue;
			}
			String accountNo = BdsmUtil.leftPad(sDetail.getNomorRekeningNasabahTertagih().trim(), 12, '0');
			if (Long.parseLong(accountNo) != Long.parseLong(SI.getAccountNo().trim())) {
				setReject(sDetail, "7", "52", "Mismatch Account No.");
				continue;
			}
			account = acctService.getByAccountNo(accountNo);
			if (account == null) {
				setReject(sDetail, "7", "61", "Invalid Account No.");
				continue;
			}
			if (account.getCodAcctStat().intValue() != 8) {
				setReject(sDetail, "7", "61", "Closed Account No.");
				continue;
			}
			customer = customerService.getByCustomerId(account.getCodCust());
			if (customer == null) {
				setReject(sDetail, "7", "61", "Closed Account No.");
				continue;
			}
			if (customer.getNamCustShrt().trim().replaceAll("\\s+", " ")
					.equalsIgnoreCase(sDetail.getNamaNasabahTertagih().trim().replaceAll("\\s+", " ")) == false) {
				setReject(sDetail, "7", "51", "Mismatch Account Name");
				continue;
			}
			
			/* Nominal Validity */
			if (((NOMINAL_TYPE_FIX.equals(SI.getNominalType())) && (nominal.compareTo(SI.getNominal()) == 0)) ||
				((NOMINAL_TYPE_MAXIMUM.equals(SI.getNominalType())) && (nominal.compareTo(SI.getNominal()) < 1)));
			else {
				setReject(sDetail, "7", "57", "Invalid Nominal Amount");
				continue;
			}
			BigDecimal netBalance = account.getBalAvailable().subtract(
					account.getAmtHld()
					.add(account.getAmtUnclr())
					.add(account.getBalAcctMinReqd())
			);
			if (nominal.compareTo(netBalance) > 0) {
				setReject(sDetail, "7", "62", "Insufficient Balance");
				continue;
			}
			
			
			valid = true;
		}
		while (false);
		
		return valid;
	}
	
	
    /**
     * 
     * @param FCRBatchNo
     * @param recordId
     * @param desc1
     * @param desc2
     * @param totalAmount
     * @param totalAmountFee
     * @return
     */
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
		bHeader.setCustomerCenter(this.costCentre);
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
	
    /**
     * 
     * @param FCRBatchNo
     * @param recordId
     * @param sDetail
     * @param SI
     * @param parentRecordId
     * @return
     */
    protected TmpBdgwDetail createBDGWDetail(String FCRBatchNo, long recordId, SknNgInOutDebitBulkDetail sDetail, BillerStandingInstruction SI, long parentRecordId) {
		String accountNo = sDetail.getNomorRekeningNasabahTertagih().trim();
		String refNo = new StringBuilder(this.context.get(MapKey.templateName).toString())
				.append(FCRBatchNo.replaceAll("\\D", "").substring(0, 10)) // get first 10 digit characters
				.append(BdsmUtil.leftPad(String.valueOf(recordId), 4, '0')) // 4 digit sequence no
				.toString();
		
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
		bDetail.setCustomerCenter(this.costCentre);
		bDetail.setReference(refNo);
		bDetail.setDescription1("INC DB SKN " + SI.getBillerBankName() + " " + SI.getId());
		bDetail.setDescription2("ID PEL:" + sDetail.getNomorIdPelanggan().trim() + ", BILLER:" + SI.getBillerName());
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
	
    /**
     * 
     * @param FCRBatchNo
     * @param recordId
     * @param totalDetail
     * @param parentRecordId
     * @return
     */
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

    /**
     * 
     * @param SI
     * @param billingDate
     * @return
     * @throws Exception
     */
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
		if ((result == true) && (PERIODIC_MONTHLY.equals(SI.getPaymentPeriodicType())) && (SI.getLastDebited() != null))
			result = (SI.getLastDebited().after(date1) && (SI.getLastDebited().before(date2))) == false;
		
		return result;
	}
	
    /**
     * 
     * @param SI
     * @param billingDate
     * @return
     * @throws Exception
     */
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
	
    /**
     * 
     * @param inputDate
     * @param date
     * @return
     * @throws Exception
     */
    protected static Date setValidDate(Date inputDate, int date) throws Exception {
		Date resultDate;
		
		try {
			resultDate = DateUtils.setDays(inputDate, date);
		}
		catch (Exception ex) {
			if ((ex instanceof IllegalArgumentException) && (ex.getMessage().startsWith("DAY_OF_MONTH")))
				resultDate = DateUtility.getLastDayOfTheMonth(inputDate);
			else
				throw ex;
		}
		
		return resultDate;
	}
	
    /**
     * 
     * @param model
     * @param recordStatus
     * @param rejectCode
     * @param comments
     */
    protected static void setReject(SknNgInOutDebitBulkDetail model, String recordStatus, String rejectCode, String comments) {
		model.setRecordStatus(recordStatus);
		model.setRejectCode(rejectCode);
		model.setComments(comments);
	}
	
    /**
     * 
     * @param message
     * @param line
     * @throws Exception
     */
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
	
	
	public String getBICFromBranchCode(Integer branchCode) throws Exception {
		CallableStatement stmt = ((SessionImpl) this.session).connection().prepareCall("{ ? = call PKG_SKNNG_BULK.GET_BIC_FROM_BRANCH_CODE(?) }");
		stmt.registerOutParameter(1, Types.VARCHAR);
		stmt.setInt(2, branchCode);
		stmt.execute();
		
		return stmt.getString(1);
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
