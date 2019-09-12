package bdsm.service;


import static bdsm.fcr.model.ChProdMast.COD_TYPE_PROD_SAVING;
import static bdsm.fcr.model.ChProdMast.COD_TYPE_PROD_CURRENT;
import static bdsm.model.AmortizeAccount.STATUS_OPENED;
import static bdsm.model.AmortizeAccount.STATUS_FINISHED;
import static bdsm.model.AmortizeAccount.FINISH_NORMAL;
import static bdsm.model.AmortizeAccount.FINISH_ACCT_CLOSED;
import static bdsm.model.AmortizeProgramMaster.AMORTIZE_METHOD_STRAIGHT_LINE;
import static bdsm.model.AmortizeProgramMaster.AMORTIZE_TYPE_ADVANCED;
import static bdsm.model.AmortizeProgramMaster.AMORTIZE_TYPE_ARREAR;

import static bdsm.scheduler.model.TmpTxnUploadDetail.COD_DRCR_DEBIT;
import static bdsm.scheduler.model.TmpTxnUploadDetail.COD_DRCR_CREDIT;
import static bdsm.scheduler.model.TmpTxnUploadDetail.COD_TXN_GL_DEBIT;
import static bdsm.scheduler.model.TmpTxnUploadDetail.COD_TXN_GL_CREDIT;
import static bdsm.scheduler.model.TmpTxnUploadDetail.TYPE_TXN_GL;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import bdsm.fcr.model.BaCcyCode;
import bdsm.fcr.model.BaCcyCodePK;
import bdsm.model.AmortizeAccount;
import bdsm.model.AmortizeGLNo;
import bdsm.model.AmortizeProgramDetail;
import bdsm.model.AmortizeProgramMaster;
import bdsm.model.AmortizeProgramMasterPK;
import bdsm.model.AmortizeRecord;
import bdsm.model.AmortizeRecordPK;
import bdsm.scheduler.PropertyPersister;
import bdsm.scheduler.StatusDefinition;
import bdsm.scheduler.dao.TmpGefuResponsDao;
import bdsm.scheduler.dao.TmpTxnUploadDetailDAO;
import bdsm.scheduler.dao.TmpTxnUploadFooterDAO;
import bdsm.scheduler.dao.TmpTxnUploadHeaderDAO;
import bdsm.scheduler.model.TmpGefuRespons;
import bdsm.scheduler.model.TmpGefuResponsPK;
import bdsm.scheduler.model.TmpTxnUploadDetail;
import bdsm.scheduler.model.TmpTxnUploadFooter;
import bdsm.scheduler.model.TmpTxnUploadHeader;
import bdsm.util.HibernateUtil;
import bdsm.util.SchedulerUtil;
import bdsm.util.oracle.DateUtility;
import bdsmhost.dao.AmortizeAccountDAO;
import bdsmhost.dao.AmortizeGLNoDAO;
import bdsmhost.dao.AmortizeProgramDetailDAO;
import bdsmhost.dao.AmortizeProgramMasterDAO;
import bdsmhost.dao.AmortizeRecordDAO;
import bdsmhost.fcr.dao.BaCcyCodeDAO;
import bdsmhost.fcr.dao.ChAcctMastDAO;
import bdsmhost.fcr.dao.ChProdMastDAO;


public class AmortizeGiftService {
	public  static final String[] MODULE_NAME                                 = {"AMORTIZE_GIFT_OPEN", "AMORTIZE_GIFT_CANCEL", "AMORTIZE_GIFT_EOM", "AMORTIZE_GIFT_EOM"};
	public  static final Integer  AMORTIZE_EVENT_PROCESS_TYPE_OPEN_ACCT       = 1;
	public  static final Integer  AMORTIZE_EVENT_PROCESS_TYPE_CANCEL_ACCT     = 2;
	public  static final Integer  AMORTIZE_EVENT_PROCESS_TYPE_EOM_ACTIVE_ACCT = 3;
	public  static final Integer  AMORTIZE_EVENT_PROCESS_TYPE_EOM_CLOSED_ACCT = 4;
	private static final Integer  LOB_SME                                     = 31;
	private static final Integer  LOB_COMM                                    = 42;
	private static final Integer  COD_CC_BRN_DATA_CONVERSION                  = 9024;
	private static final String   COD_USER_ID                                 = "INTFCAW";
	private static final String   GEFU_PACKAGE_NAME                           = "PK_GEFU";
	private static final String   GEFU_RESPONS_PROCEDURE_NAME                 = "responsTxnUpload";
	private static final String   DEBIT                                       = "D";
	private static final String   CREDIT                                      = "C";
	
	private static final Logger  LOGGER = Logger.getLogger(AmortizeGiftService.class);
	private static final Integer COMMIT_SIZE = 1000;

	
	private Session session;
	private BaCcyCodeDAO baCcyCodeDAO;
	private ChAcctMastDAO chAcctMastDAO;
	private ChProdMastDAO chProdMastDAO;
	private AmortizeGLNoDAO amortizeGLNoDAO;
	private AmortizeProgramMasterDAO amortizeProgramMasterDAO;
	private AmortizeProgramDetailDAO amortizeProgramDetailDAO;
	private AmortizeAccountDAO amortizeAccountDAO;
	private AmortizeRecordDAO amortizeRecordDAO;
	private TmpTxnUploadHeaderDAO txnUploadHeaderDAO;
	private TmpTxnUploadDetailDAO txnUploadDetailDAO;
	private TmpTxnUploadFooterDAO txnUploadFooterDAO;
	private TmpGefuResponsDao tmpGefuResponsDAO;
	
	
	/* == Setter Injection == */
	public void setSession(Session session) {
		this.session = session;
	}
	
	public void setBaCcyCodeDAO(BaCcyCodeDAO baCcyCodeDAO) {
		this.baCcyCodeDAO = baCcyCodeDAO;
		this.baCcyCodeDAO.setSession(this.session);
	}
	
	public void setChAcctMastDAO(ChAcctMastDAO chAcctMastDAO) {
		this.chAcctMastDAO = chAcctMastDAO;
		this.chAcctMastDAO.setSession(this.session);
	}
	
	public void setChProdMastDAO(ChProdMastDAO chProdMastDAO) {
		this.chProdMastDAO = chProdMastDAO;
		this.chProdMastDAO.setSession(this.session);
	}
	
	public void setAmortizeGLNoDAO(AmortizeGLNoDAO amortizeGLNoDAO) {
		this.amortizeGLNoDAO = amortizeGLNoDAO;
		this.amortizeGLNoDAO.setSession(this.session);
	}
	
	public void setAmortizeProgramMasterDAO(AmortizeProgramMasterDAO amortizeProgramMasterDAO) {
		this.amortizeProgramMasterDAO = amortizeProgramMasterDAO;
		this.amortizeProgramMasterDAO.setSession(this.session);
	}
	
	public void setAmortizeProgramDetailDAO(AmortizeProgramDetailDAO amortizeProgramDetailDAO) {
		this.amortizeProgramDetailDAO = amortizeProgramDetailDAO;
		this.amortizeProgramDetailDAO.setSession(this.session);
	}
	
	public void setAmortizeAccountDAO(AmortizeAccountDAO amortizeAccountDAO) {
		this.amortizeAccountDAO = amortizeAccountDAO;
		this.amortizeAccountDAO.setSession(this.session);
	}
	
	public void setAmortizeRecordDAO(AmortizeRecordDAO amortizeRecordDAO) {
		this.amortizeRecordDAO = amortizeRecordDAO;
		this.amortizeRecordDAO.setSession(this.session);
	}
	
	public void setTmpTxnUploadHeaderDAO(TmpTxnUploadHeaderDAO txnUploadHeaderDAO) {
		this.txnUploadHeaderDAO = txnUploadHeaderDAO;
		this.txnUploadHeaderDAO.setSession(this.session);
	}
	
	public void setTmpTxnUploadDetailDAO(TmpTxnUploadDetailDAO txnUploadDetailDAO) {
		this.txnUploadDetailDAO = txnUploadDetailDAO;
		this.txnUploadDetailDAO.setSession(this.session);
	}
	
	public void setTmpTxnUploadFooterDAO(TmpTxnUploadFooterDAO txnUploadFooterDAO) {
		this.txnUploadFooterDAO = txnUploadFooterDAO;
		this.txnUploadFooterDAO.setSession(this.session);
	}
	
	public void setTmpGefuResponsDAO(TmpGefuResponsDao tmpGefuResponsDAO) {
		this.tmpGefuResponsDAO = tmpGefuResponsDAO;
		this.tmpGefuResponsDAO.setSession(this.session);
	}
	
	
	/**
	 * @param fileId TxnUpload File ID
	 * @param businessDate Business Date
	 * @param eventProcessTypes List of Event Process Type, 1 = Open Account, 2 = Cancel Account, 3 = EOM Active Account, 4 = EOM Closed Account
	 * @param amortizeAccounts List of Amortize Account to be processed
	 * @param amortizeAmounts List of mapped amounts(2 decimal point each), 
	 * 			example: [{"D"=[5000.00], "C"=[4000.00, 1000.00]}, {"D"=[1000.00], "C"=[1000.00]}]
	 */
	public void doJournalPosting(String fileId, Date businessDate, List<Integer> eventProcessTypes, 
			List<AmortizeAccount> amortizeAccounts, List<Map<String, BigDecimal[]>> amortizeAmounts) {
		this.doJournalPosting(fileId, businessDate, eventProcessTypes, amortizeAccounts, null, amortizeAmounts, false, 0);
	}
	
	public void doJournalPosting(String fileId, Date businessDate, List<Integer> eventProcessTypes, 
				List<AmortizeAccount> amortizeAccounts, List<Map<String, Object>> FCRDatas,
				List<Map<String, BigDecimal[]>> amortizeAmounts, boolean onSeparatedThread, int threadNo) {
		List<AmortizeGLNo> listAmortGLNo = null;
		Map<String, AmortizeProgramMaster> progMasterMap = null;
		Map<Integer, AmortizeProgramDetail> progDetailMap = null;
		BaCcyCode ccyCode;
		TmpTxnUploadHeader txnUploadHeader;
		TmpTxnUploadDetail txnUploadDetail;
		TmpTxnUploadFooter txnUploadFooter;
		Integer eventProcessType;
		BigDecimal totalAmountDebit, totalAmountCredit, decimalMultiplier, amount;
		String currency;
		int totalDebit, totalCredit;
		int counter = 1;
		boolean isDataConversion;
		Session newSession = null;
		Transaction trx = null;
		
		
		if (onSeparatedThread) {
			newSession = HibernateUtil.getSession();
			
			this.baCcyCodeDAO.setSession(newSession);
			this.chAcctMastDAO.setSession(newSession);
			this.amortizeProgramMasterDAO.setSession(newSession);
			this.amortizeProgramDetailDAO.setSession(newSession);
			this.amortizeGLNoDAO.setSession(newSession);
			this.txnUploadHeaderDAO.setSession(newSession);
			this.txnUploadDetailDAO.setSession(newSession);
			this.txnUploadFooterDAO.setSession(newSession);
			this.tmpGefuResponsDAO.setSession(newSession);
			
			listAmortGLNo = this.initGLNo();
			progMasterMap = this.initProgramMaster();
			progDetailMap = this.initProgramDetail();
			
			trx = newSession.beginTransaction(); 
		}
		
		
		if (amortizeAccounts.size() == 0)
			return;
		
		
		totalAmountDebit = totalAmountCredit = BigDecimal.ZERO;
		decimalMultiplier = BigDecimal.ONE;
		totalDebit = totalCredit = 0;
		
		ccyCode = this.baCcyCodeDAO.get(new BaCcyCodePK(360, "A", PropertyPersister.codEntityVPD));
		currency = ccyCode.getNamCcyShort();
		decimalMultiplier = (ccyCode.getCtrCcyDec() > 0)? 
								new BigDecimal(Double.toString(Math.pow(10.0, ccyCode.getCtrCcyDec()))) : 
								BigDecimal.ONE;
								
		
		/* Txn Upload Header */
		txnUploadHeader = new TmpTxnUploadHeader(fileId, MODULE_NAME[eventProcessTypes.get(0) - 1]);
		txnUploadHeader.setDateCreate(DateUtility.DATE_FORMAT_YYYYMMDD.format(businessDate));
		txnUploadHeader.generateLength();
		this.txnUploadHeaderDAO.insert(txnUploadHeader);
		counter++;
		
		/* Txn Upload Details */
		int i = 0;
		for (; i<amortizeAccounts.size(); i++) {
			eventProcessType = eventProcessTypes.get(i);
			
			AmortizeAccount amortizeAccount = amortizeAccounts.get(i);
			Integer amortizeType = amortizeAccount.getType();
			String accountNo = amortizeAccount.getAccountNo();
			Map<String, Object> FCRData = FCRDatas.get(i);
			
			AmortizeProgramDetail progDetail = null;
			AmortizeProgramMaster progMaster = null;
			
			if (progDetailMap == null)
				progDetail = this.amortizeProgramDetailDAO.get(amortizeAccount.getProgramDetailId());
			else
				progDetail = progDetailMap.get(amortizeAccount.getProgramDetailId());
			
			if (progMasterMap == null) 
				progMaster = this.amortizeProgramMasterDAO.get(new AmortizeProgramMasterPK(progDetail.getGiftCode(), progDetail.getProductCode()));
			else
				progMaster = progMasterMap.get(progDetail.getGiftCode() + "~" + progDetail.getProductCode());
			
			List<AmortizeGLNo> listPostingGLNo = null;
			if (listAmortGLNo == null)
				listPostingGLNo = this.amortizeGLNoDAO.getByTypeAndProcessType(amortizeType, eventProcessType);
			else
				listPostingGLNo = this.getGLNoByTypeAndProcessType(listAmortGLNo, amortizeType, eventProcessType);
			
			
			Map<String, BigDecimal[]> amounts = amortizeAmounts.get(i);
			isDataConversion = "konversi".equalsIgnoreCase(amortizeAccount.getIdCreatedBy().trim());
			
			for (AmortizeGLNo amortGLNo: listPostingGLNo) {
				txnUploadDetail = new TmpTxnUploadDetail(txnUploadHeader, counter++);
				int counterDebit, counterCredit;
				
				counterDebit = counterCredit = 0;
				
				/* === [Begin] - CC Branch & LOB === */				
				if (AMORTIZE_TYPE_ADVANCED.equals(amortizeType)) {
					if ((AMORTIZE_EVENT_PROCESS_TYPE_OPEN_ACCT.equals(eventProcessType) && CREDIT.equals(amortGLNo.getCompositeId().getDrCr())) 
							|| (AMORTIZE_EVENT_PROCESS_TYPE_CANCEL_ACCT.equals(eventProcessType) && DEBIT.equals(amortGLNo.getCompositeId().getDrCr()))
					) {
						String[] arrStrCCBrn = amortGLNo.getCodCCBrn().split(";");
						String[] arrStrLOB = amortGLNo.getCodLOB().split(";");
						int idx = 0;
						
						if (amortGLNo.getCompositeId().getSequenceNo().equals(1)) { // Transaction
							if (progMaster.getVoucher().equals(Boolean.FALSE))
								idx = 1;
						}
						else if (amortGLNo.getCompositeId().getSequenceNo().equals(2)) { // Tax
							String LOB = this.chAcctMastDAO.getCod_LOB(accountNo);
							idx = 2;
							
							if (LOB_SME.equals(Integer.valueOf(LOB)))
								idx = 0;
							else if (LOB_COMM.equals(Integer.valueOf(LOB)))
								idx = 1;
						}
						
						txnUploadDetail.setCodCcBrn(Integer.valueOf(arrStrCCBrn[idx]));
						txnUploadDetail.setCodLob(Integer.valueOf(arrStrLOB[idx]));
					}
					else if (AMORTIZE_EVENT_PROCESS_TYPE_EOM_ACTIVE_ACCT.equals(eventProcessType) 
							|| AMORTIZE_EVENT_PROCESS_TYPE_EOM_CLOSED_ACCT.equals(eventProcessType)
					) {
						if (isDataConversion)
							txnUploadDetail.setCodCcBrn(COD_CC_BRN_DATA_CONVERSION);
					}
				}
				if (txnUploadDetail.getCodCcBrn() == null)
					txnUploadDetail.setCodCcBrn((amortGLNo.getCodCCBrn() == null)? ((Integer) FCRData.get("accountBranchCode")): Integer.valueOf(amortGLNo.getCodCCBrn()));
				if (txnUploadDetail.getCodLob() == null)
					txnUploadDetail.setCodLob(Integer.valueOf(amortGLNo.getCodLOB()));
				/* === [End] - CC Branch & LOB === */
				
				
				/* === [Begin] - GL No === */
				if ((AMORTIZE_TYPE_ARREAR.equals(amortizeType)) && (DEBIT.equals(amortGLNo.getCompositeId().getDrCr()))) {
					if (AMORTIZE_EVENT_PROCESS_TYPE_EOM_ACTIVE_ACCT.equals(eventProcessType) 
							|| AMORTIZE_EVENT_PROCESS_TYPE_EOM_CLOSED_ACCT.equals(eventProcessType)) {
						String[] arrStr = amortGLNo.getGLNo().split(";");
						int idx = 0;
						
						String accountTypeProduct = (String) FCRData.get("accountTypeProduct");
						if (COD_TYPE_PROD_SAVING.equals(accountTypeProduct))
							idx = 0;
						else if (COD_TYPE_PROD_CURRENT.equals(accountTypeProduct))
							idx = 1;
						
						txnUploadDetail.setCodAcctNo(arrStr[idx]);
					}
				}
				if (txnUploadDetail.getCodAcctNo() == null)
					txnUploadDetail.setCodAcctNo(amortGLNo.getGLNo());
				/* === [End] - GL No === */
				
				
				if (DEBIT.equals(amortGLNo.getCompositeId().getDrCr())) {
					txnUploadDetail.setCodDrCr(COD_DRCR_DEBIT);
					txnUploadDetail.setCodTxn(COD_TXN_GL_DEBIT);
					amount = amounts.get(DEBIT)[counterDebit++];
					totalDebit++;
					totalAmountDebit = totalAmountDebit.add(amount);
				}
				else {
					txnUploadDetail.setCodAcctNo(amortGLNo.getGLNo()); /* GL No */
					txnUploadDetail.setCodDrCr(COD_DRCR_CREDIT);
					txnUploadDetail.setCodTxn(COD_TXN_GL_CREDIT);
					amount = amounts.get(CREDIT)[counterCredit++];
					totalCredit++;
					totalAmountCredit = totalAmountCredit.add(amount);
				}
				
				
				txnUploadDetail.setTypTxn(TYPE_TXN_GL);
				txnUploadDetail.setCodUserId(COD_USER_ID);
				txnUploadDetail.setProdCod((amortGLNo.getCodProduct() == null)? progDetail.getProductCode(): amortGLNo.getCodProduct());
				txnUploadDetail.setTxnCurrency(currency);
				txnUploadDetail.setAmtTxnTcy(amount.multiply(decimalMultiplier));
				txnUploadDetail.setConvRate(BigDecimal.ONE);
				txnUploadDetail.setReferenceNo(String.valueOf(i + 1));
				txnUploadDetail.setRefDocNo(txnUploadDetail.getReferenceNo());
				txnUploadDetail.setAmtTxnLcy(txnUploadDetail.getAmtTxnTcy().multiply(txnUploadDetail.getConvRate()));
				txnUploadDetail.setTxtTxnDesc(progMaster.getIdAccrual() + " " + accountNo);
				
				txnUploadDetail.generateLength();
				this.txnUploadDetailDAO.insert(txnUploadDetail);
			}
			
			/* Commit */
			if ((onSeparatedThread) && (((i+1) % COMMIT_SIZE) == 0) && ((i+1) != amortizeAccounts.size())) {
				LOGGER.info("doJournalPosting - COMMIT at ThreadNo " + threadNo + " rowNo " + (i+1));
				trx.commit();
				trx = newSession.beginTransaction();
			}
		}
		
		/* Txn Upload Footer */
		txnUploadFooter = new TmpTxnUploadFooter(txnUploadHeader, counter);
		txnUploadFooter.setNoOfDr(totalDebit);
		txnUploadFooter.setAmtDr(totalAmountDebit.multiply(decimalMultiplier));
		txnUploadFooter.setNoOfCr(totalCredit);
		txnUploadFooter.setAmtCr(totalAmountCredit.multiply(decimalMultiplier));
		txnUploadFooter.generateLength();
		this.txnUploadFooterDAO.insert(txnUploadFooter);
		
		this.insertTmpGefuRespons(fileId, txnUploadHeader.getModuleName(), 0);
		
		if (onSeparatedThread) {
			newSession.flush();
			newSession.clear();
		}
		else {
			this.session.flush();
			this.session.clear();
		}
		
		
		/* Parameter for Ext File Upload Master */
		List<String> txnParamsList = new ArrayList<String>();
		txnParamsList.add(fileId);
		for (String s : PropertyPersister.AMORTTXNPARAM.split(";"))
			txnParamsList.add(s);
		txnParamsList.add(txnUploadHeader.getModuleName());
		
		this.txnUploadHeaderDAO.runTxn(txnParamsList);
		
		/* Commit */
		if (onSeparatedThread) {
			LOGGER.info("doJournalPosting - COMMIT at ThreadNo " + threadNo + " rowNo " + i);
			trx.commit();
		}
	}
	
	public void doAmortizeEOM(Date lastDateOfTheMonth, Date businessDate, String accountNo) {
		List<Map<String, Object>> FCRDataList;
		List<AmortizeAccount> acctList;
		List<Integer> programDetailTermList;
		AmortizeAccount ac;
		AmortizeRecord oldRec, newRec;
		Date endAmortizeDate;
		BigDecimal amortizedAmount, unAmortizedAmount;
		BigDecimal[] arrAmount;
		List<Map<String, BigDecimal[]>> journalAmountList;
		Map<String, BigDecimal[]> journalAmount;
		List<Integer> eventTypeList;
		String fileId;
		boolean isAccountClosed, isFinishedAmortization;
		int[] diff;
		int days;
		int firstRowNoOfBatch = 1, threadNo = 0;
		
		LOGGER.info("[BEGIN] doAmortizeEOM");
		
		do {
			threadNo++;
			fileId = SchedulerUtil.generateUUID2();
			FCRDataList = new ArrayList<Map<String, Object>>();
			programDetailTermList = new ArrayList<Integer>();
			eventTypeList = new ArrayList<Integer>();
			journalAmountList = new ArrayList<Map<String, BigDecimal[]>>();
			
			LOGGER.info("Query start row no. : " + firstRowNoOfBatch);
			
			acctList = this.amortizeAccountDAO.getByStatusAndMethod(
						accountNo, STATUS_OPENED, AMORTIZE_METHOD_STRAIGHT_LINE, // Opened Account and Straight Line Method
						lastDateOfTheMonth, firstRowNoOfBatch, PropertyPersister.AMORT_EOM_MAX_PER_BATCH, 
						programDetailTermList, FCRDataList);
			
			LOGGER.info("Accounts Retrieved: " + acctList.size());
			
			if (acctList.size() == 0)
				break;
			
			
			int start = (threadNo-1) * PropertyPersister.AMORT_EOM_MAX_PER_BATCH;
			
			for (int i=0; i<acctList.size(); i++) {
				ac = acctList.get(i);
				
				if (ac.getOpenDate().before(lastDateOfTheMonth) == false)
					continue;
				
				
				LOGGER.info("Account: " + (start + i+1) + ". " + ac.getId() + " - " + ac.getAccountNo() + ", openDate: " 
						+ DateUtility.DATE_FORMAT_DDMMMYYYY.format(ac.getOpenDate()));
				
				isAccountClosed = isFinishedAmortization = false;
				oldRec = null;
				newRec = new AmortizeRecord();
				days = 0;
				
				AmortizeRecord record = this.amortizeRecordDAO.getLastRecordByAmortizeAccountId(ac.getId());
				
				if (record == null) { // first amortize recording
					endAmortizeDate = DateUtils.addMonths(ac.getOpenDate(), programDetailTermList.get(i));
					
					diff = DateUtility.getDateDiff(ac.getOpenDate(), endAmortizeDate);
					newRec.setTermDays((short) diff[0]);
					
					diff = DateUtility.getDateDiff(ac.getOpenDate(), lastDateOfTheMonth);
				}
				else {
					oldRec = record;
					endAmortizeDate = DateUtils.addDays(ac.getOpenDate(), oldRec.getTermDays());
					newRec.setTermDays(oldRec.getTermDays());
					
					diff = DateUtility.getDateDiff(oldRec.getPostDate(), lastDateOfTheMonth); 
				}
				
				days = diff[0];
				amortizedAmount = BigDecimal.ZERO;
				unAmortizedAmount = ac.getGiftPriceGross().subtract((oldRec!=null)? oldRec.getAmortizedAccumalation(): BigDecimal.ZERO);
				
				String acctStat = ((Map<String, Object>) FCRDataList.get(i)).get("accountStatus").toString();
				if (this.getYearMonth(lastDateOfTheMonth) >= this.getYearMonth(endAmortizeDate)) { // last amortize recording 
					isFinishedAmortization = true;
				}
				else if (("," + StatusDefinition.AmortNegateAcctStat.replace(" ", "") + ",").indexOf("," + acctStat + ",") > 0) { 
					// FCR account status is closed
					isFinishedAmortization = true;
					isAccountClosed = true;
				}
				else {
					amortizedAmount = ac.getGiftPriceGross().multiply(
						new BigDecimal(((double) days) / newRec.getTermDays())).setScale(2, java.math.RoundingMode.FLOOR) ;
					
					// If amortize amount calculation greater than unamortized amount 
					if (amortizedAmount.compareTo(unAmortizedAmount) > 0)
						isFinishedAmortization = true;
				}
				
				
				if (isFinishedAmortization) {
					if (unAmortizedAmount.compareTo(BigDecimal.ZERO) > 0) // if positive
						amortizedAmount = unAmortizedAmount;
					else
						amortizedAmount = BigDecimal.ZERO;
					
					ac.setFinish(Boolean.TRUE);
					ac.setFinishType(isAccountClosed? FINISH_ACCT_CLOSED: FINISH_NORMAL);
					ac.setIdUpdatedBy("SYSTEM");
					ac.setDtmUpdated(SchedulerUtil.getTime());
				}
				
				
				newRec.setCompositeId(new AmortizeRecordPK(ac.getId(), (short) (1 + ((oldRec==null)? 0: oldRec.getCompositeId().getSequenceNo()))));
				newRec.setAmortizedThisMonth(amortizedAmount);
				newRec.setAmortizedAccumalation(amortizedAmount.add((oldRec==null)? BigDecimal.ZERO: oldRec.getAmortizedAccumalation()));
				newRec.setPostDate(lastDateOfTheMonth);
				newRec.setTxnUploadFileId(fileId);
				newRec.setTxnUploadReferenceNo(String.valueOf(i + 1));
				
				this.amortizeRecordDAO.insert(newRec);
				
				arrAmount = new BigDecimal[] {amortizedAmount};
				journalAmount = new HashMap<String, BigDecimal[]>();
				journalAmount.put(DEBIT, arrAmount);
				journalAmount.put(CREDIT, arrAmount);
				journalAmountList.add(journalAmount);
				
				eventTypeList.add(isAccountClosed? AMORTIZE_EVENT_PROCESS_TYPE_EOM_CLOSED_ACCT: AMORTIZE_EVENT_PROCESS_TYPE_EOM_ACTIVE_ACCT);
			}
			
			/* Posting Journal */
			new PostingJournalThread(
					threadNo,
					fileId,
					businessDate,
					eventTypeList,
					acctList,
					FCRDataList,
					journalAmountList
			)
			.start();
			
			this.session.flush();
			this.session.clear();
			
			firstRowNoOfBatch += PropertyPersister.AMORT_EOM_MAX_PER_BATCH;
		}
		while (true);
		
		
		LOGGER.info("Update Amortize Account to Finish");
		LOGGER.info("Updated rows: " + this.amortizeAccountDAO.updateStatusFinish(STATUS_OPENED, STATUS_FINISHED));
		
		
		LOGGER.info("[ END ] doAmortizeEOM");
	}
	
	protected void insertTmpGefuRespons(String batchNo, String moduleName, Integer idScheduler) {
		TmpGefuRespons GEFUResponse = new TmpGefuRespons();
		GEFUResponse.setCompositeId(new TmpGefuResponsPK(GEFU_PACKAGE_NAME, GEFU_RESPONS_PROCEDURE_NAME, batchNo));
		GEFUResponse.setIdScheduler(idScheduler);
		GEFUResponse.setModuleDesc(moduleName);
		
		this.tmpGefuResponsDAO.insert(GEFUResponse);
	}
	
	
	
	protected List<AmortizeGLNo> initGLNo() {
		return this.amortizeGLNoDAO.getAll();
	}
	
	protected Map<String, AmortizeProgramMaster> initProgramMaster() {
		Map<String, AmortizeProgramMaster> progMasterMap = new HashMap<String, AmortizeProgramMaster>();
		List<AmortizeProgramMaster> progMasterList = this.amortizeProgramMasterDAO.getAll();
		for (AmortizeProgramMaster progMaster: progMasterList)
			progMasterMap.put(progMaster.getCompositeId().getGiftCode() + "~" + progMaster.getCompositeId().getProductCode(), progMaster);
		
		return progMasterMap;
	}
	
	protected Map<Integer, AmortizeProgramDetail> initProgramDetail() {
		Map<Integer, AmortizeProgramDetail> progDetailMap = new HashMap<Integer, AmortizeProgramDetail>();
		List<AmortizeProgramDetail> progDetailList = this.amortizeProgramDetailDAO.getAll();
		for (AmortizeProgramDetail progDetail: progDetailList)
			progDetailMap.put(progDetail.getId(), progDetail);
		
		return progDetailMap;
	}
	
	protected List<AmortizeGLNo> getGLNoByTypeAndProcessType(List<AmortizeGLNo> allGLNo, Integer type, Integer processType) {
		List<AmortizeGLNo> result = new ArrayList<AmortizeGLNo>();
		
		for (AmortizeGLNo amortGLNo: allGLNo)
			if ((amortGLNo.getCompositeId().getType().equals(type)) && (amortGLNo.getCompositeId().getProcessType().equals(processType)))
					result.add(amortGLNo);
		
		return result;
	}
	
	protected AmortizeProgramMaster getProgramMasterByGiftCodeAndProductCode(Map<String, AmortizeProgramMaster> allProgMaster, String giftCode, Integer productCode) {
		return allProgMaster.get(giftCode + "~" + productCode);
	}
	
	protected AmortizeProgramDetail getProgramDetailById(Map<Integer, AmortizeProgramDetail> allProgDetail, Integer id) {
		return allProgDetail.get(id);
	}
	
	protected final int getYearMonth(Date date) {
		return Integer.parseInt(DateUtility.DATE_FORMAT_YYYYMMDD.format(date).substring(0, 6));
	}
	
	
	
	
	/* Inner Class */
	class PostingJournalThread extends Thread {
		private int threadNo;
		private String fileId;
		private Date businessDate;
		private List<Integer> eventProcessTypes;
		private List<AmortizeAccount> amortizeAccounts;
		private List<Map<String, Object>> FCRDatas;
		private List<Map<String, BigDecimal[]>> amortizeAmounts;
		
		
		public PostingJournalThread(int threadNo, String fileId, Date businessDate, List<Integer> eventProcessTypes, 
				List<AmortizeAccount> amortizeAccounts, List<Map<String, Object>> FCRDatas, 
				List<Map<String, BigDecimal[]>> amortizeAmounts) {
			this.threadNo = threadNo;
			this.fileId = fileId;
			this.businessDate = businessDate;
			this.eventProcessTypes = eventProcessTypes;
			this.amortizeAccounts = amortizeAccounts;
			this.FCRDatas = FCRDatas;
			this.amortizeAmounts = amortizeAmounts;
		}
		
		
		@Override
		public void run() {
			AmortizeGiftService srvc = new AmortizeGiftService();
			srvc.setBaCcyCodeDAO(new BaCcyCodeDAO(null));
			srvc.setChAcctMastDAO(new ChAcctMastDAO(null));
			srvc.setAmortizeProgramMasterDAO(new AmortizeProgramMasterDAO(null));
			srvc.setAmortizeProgramDetailDAO(new AmortizeProgramDetailDAO(null));
			srvc.setAmortizeGLNoDAO(new AmortizeGLNoDAO(null));
			srvc.setTmpTxnUploadHeaderDAO(new TmpTxnUploadHeaderDAO(null));
			srvc.setTmpTxnUploadDetailDAO(new TmpTxnUploadDetailDAO(null));
			srvc.setTmpTxnUploadFooterDAO(new TmpTxnUploadFooterDAO(null));
			srvc.setTmpGefuResponsDAO(new TmpGefuResponsDao(null));
			
			srvc.doJournalPosting(
					this.fileId,
					this.businessDate,
					this.eventProcessTypes,
					this.amortizeAccounts,
					this.FCRDatas,
					this.amortizeAmounts,
					true,
					this.threadNo
			);
		}
	}
	
}
