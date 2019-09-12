package bdsmhost.web;


import java.util.Calendar;

import bdsm.fcr.model.BaTxnMnemonic;
import bdsm.fcr.model.ChAcctMast;
import bdsm.fcr.model.GLMaster;
import bdsm.fcr.model.XfOlStTxnLogCurrent;
import bdsm.fcr.service.AccountService;
import bdsm.fcr.service.DataMasterService;
import bdsm.fcr.service.TransactionService;
import bdsm.model.WIC;
import bdsm.model.WICHistory;
import bdsm.model.WICTrx;
import bdsm.scheduler.PropertyPersister;
import bdsm.scheduler.exception.FIXException;
import bdsm.service.WICService;
import bdsm.util.BdsmUtil;
import bdsmhost.dao.WICDao;
import bdsmhost.dao.WICTrxDAO;
import bdsmhost.fcr.dao.BaTxnMnemonicDAO;
import bdsmhost.fcr.dao.ChAcctMastDAO;
import bdsmhost.fcr.dao.DataMasterDAO;
import bdsmhost.fcr.dao.GLMasterDAO;
import bdsmhost.fcr.dao.XfOlStTxnLogCurrentDAO;
import bdsmhost.interceptor.AbstractRequestApprovalHistoryInterceptor;


/**
 * 
 * @author v00019372
 */
@SuppressWarnings("serial")
public class WICAction extends ModelDrivenBaseHostAction<WIC> {
	private static final String DEPOSIT    = "01";
	private static final String WITHDRAWAL = "02";
	
	private final WICService wicService = new WICService();
	private org.hibernate.Session localSession;
	private WIC wic = new WIC();
	private WICTrx WICTrx = new WICTrx();
	

	/* === WIC === */
	
	@Override
	public String exec() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public String doList() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public String doGet() {
		this.wicService.setWICDAO(new WICDao(this.getHSession()));
		
		DataMasterService fcrDataMasterService = new DataMasterService();
		fcrDataMasterService.setDataMasterDAO(new DataMasterDAO(this.getHSession()));
		
		this.wic.setCustomerType(Integer.parseInt(String.valueOf(this.getFromStrData("customerTypeInq"))));
		this.wic.setIdType(this.getFromStrData("idTypeInq"));
		this.wic.setIdNumber(this.getFromStrData("idNumberInq").trim());
		
		Object[] data = null;
		String resultAction = ERROR;
		WIC wicResult = this.wicService.get(this.wic);
		if (wicResult != null) {
			this.wic = wicResult;
			
			this.getDescriptionAndPutToObjData(fcrDataMasterService, "WICCustomer", "customerType");
			this.getDescriptionAndPutToObjData(fcrDataMasterService, "idType", "idType");
			this.getDescriptionAndPutToObjData(fcrDataMasterService, "gender", "gender");
			this.getDescriptionAndPutToObjData(fcrDataMasterService, "maritalStatus", "maritalStatus");
			this.getDescriptionAndPutToObjData(fcrDataMasterService, "city", "city");
			this.getDescriptionAndPutToObjData(fcrDataMasterService, "state", "state");
			this.getDescriptionAndPutToObjData(fcrDataMasterService, "country", "country");
			this.getDescriptionAndPutToObjData(fcrDataMasterService, "citizenship", "citizenship");
			this.getDescriptionAndPutToObjData(fcrDataMasterService, "occupation", "occupation");
			
			if (this.wic.getResidentialEqualIdentity()) {
				this.wic.setResidentialAddress1(this.wic.getAddress1());
				this.wic.setResidentialAddress2(this.wic.getAddress2());
				this.wic.setResidentialAddress3(this.wic.getAddress3());
				this.wic.setResidentialPostalCode(this.wic.getPostalCode());
				
				this.putToObjData("residentialCity", this.getFromObjData("city"));
				this.putToObjData("residentialState", this.getFromObjData("state"));
				this.putToObjData("residentialCountry", this.getFromObjData("country"));
			}
			else {
				this.getDescriptionAndPutToObjData(fcrDataMasterService, "city", "residentialCity");
				this.getDescriptionAndPutToObjData(fcrDataMasterService, "state", "residentialState");
				this.getDescriptionAndPutToObjData(fcrDataMasterService, "country", "residentialCountry");
			}
			
			this.getDescriptionAndPutToObjData(fcrDataMasterService, "bidangUsaha", "businessType");
			this.getDescriptionAndPutToObjData(fcrDataMasterService, "sourceOfFunds", "sourceOfFunds");
			
			data = fcrDataMasterService.getDataById("incomePerMonthType", this.wic.getIncomePerMonthType());
			this.putToObjData("incomePerMonthType", (data!=null)? data[1]: null);
			
			resultAction = SUCCESS;
		}
		else {
			this.getDescriptionAndPutToObjData(fcrDataMasterService, "WICCustomer", "customerType");
			
			this.setErrorCode("error.1");
		}
		
		this.wicService.setWICDAO(null);
		return resultAction;
	}
	
	private void getDescriptionAndPutToObjData(DataMasterService service, String masterCode, String propertyName) {
		try {
			Object value = org.apache.commons.beanutils.PropertyUtils.getProperty(this.wic, propertyName);
			if (value != null) {
				Object[] data = service.getDataById(masterCode, value);
				this.putToObjData(propertyName, (data!=null)? data[1]: null);
			}
			else
				this.putToObjData(propertyName, "");
		}
		catch (Exception ex) {
			this.putToObjData(propertyName, "");
		}
	}
	
	
	
	@Override
	public String doSave() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public String doInsert() {
		return this.processWIC("insert", "success.0", "error.0");
	}

	@Override
	public String doUpdate() {
		return this.processWIC("update", "success.1", "error.1");
	}

	@Override
	public String doDelete() {
		return this.processWIC("delete", "success.2", "error.4");
	}
	
	protected String processWIC(String actionType, String successCode, String errorCode) {
		WICDao wicDAO = new WICDao(this.getHSession());
		wicDAO.setInterceptor(new AbstractRequestApprovalHistoryInterceptor<WICHistory>() {}); // Set interceptor
		this.wicService.setWICDAO(wicDAO);
		
		String resultAction = ERROR;
		try {
			if ("delete".equals(actionType) == false)
				this.wic.setIdNumber(this.wic.getIdNumber().trim()); // trim id number
			
			if ("insert".equals(actionType))
				this.wic = this.wicService.insert(this.wic);
			else if ("update".equals(actionType))
				this.wic = this.wicService.update(this.wic);
			else if ("delete".equals(actionType))
				this.wic = this.wicService.delete(this.wic);
			
			this.wicService.commit();
			this.setErrorCode(successCode);
			resultAction = SUCCESS;
		}
		catch (FIXException ex) {
			if (ex.getMessage().toLowerCase().contains("fail")) {
				this.setErrorCode(errorCode);
				this.wicService.rollback();
			}
			else
				throw new RuntimeException(ex);
		}
		
		this.localSession = wicDAO.getLocalSession();
		this.wicService.setWICDAO(null);
		return resultAction;
	}
	
	
	
	/* === WIC TRX === */
	
	public String doGetWICAndOriTrx() {
		return this.getWICAndTrx(true);
	}
	
	public String doGetWICAndTrx() {
		return this.getWICAndTrx(false);
	}
	
	protected String getWICAndTrx(boolean getFromTrxFCR) {
		String resultAction = ERROR;
		
		if (SUCCESS.equals(this.doGet())) {
			String trxRefNo = this.getFromStrData("trxRefNoInq").trim();
			String codBranch = this.getFromStrData("codBranch").trim();
			
			if (getFromTrxFCR) {
				if (this.WICTrx == null)
					this.WICTrx = new WICTrx();
				
				TransactionService trxService = new TransactionService();
				trxService.setFCRTransactionDAO(new XfOlStTxnLogCurrentDAO(this.getHSession()));
				XfOlStTxnLogCurrent trx = trxService.getByTrxRefNo(trxRefNo, codBranch);
				
				if (trx != null) {
					if ((PropertyPersister.WIC_TXN_MNEMONIC == null) || 
								BdsmUtil.isContainsIn(trx.getCodTxnMnemonic().toString(), PropertyPersister.WIC_TXN_MNEMONIC.split(";"))) {
						/* Get Transaction Date Time */
						Calendar calTxn = Calendar.getInstance();
						Calendar calValue = Calendar.getInstance(); 
						
						calTxn.setTime(trx.getDatTxnStr()); // datTxnStr : system timestamp transaction
						calValue.setTime(trx.getDatValueStr()); // datValueStr: business date transaction
						calTxn.set(Calendar.DATE, calValue.get(Calendar.DATE));
						calTxn.set(Calendar.MONTH, calValue.get(Calendar.MONTH));
						calTxn.set(Calendar.YEAR, calValue.get(Calendar.YEAR));
						
						this.WICTrx.setWICNo(this.wic.getWICNo());
						this.WICTrx.setRefNo(trxRefNo);
						this.WICTrx.setType("C".equals(trx.getFlgDrCr())? DEPOSIT: WITHDRAWAL);
						this.WICTrx.setDateTime(new java.sql.Timestamp(calTxn.getTimeInMillis()));
						this.WICTrx.setFastPath(trx.getCodTxnMnemonic());
						this.WICTrx.setAccountNo(trx.getCodAcctNo());
						this.WICTrx.setCurrencyCode(trx.getCodTxnCcy());
						this.WICTrx.setAmount(trx.getAmtTxnTcy());
						this.WICTrx.setDebitOrCredit(trx.getFlgDrCr());
						this.WICTrx.setNarrative(trx.getTxnNrrtv());
						this.WICTrx.setBranch(trx.getPk().getCodOrgBrn());
						this.WICTrx.setTellerId(trx.getCodUserId());
					}
					else {
						BaTxnMnemonicDAO baTxnMnemonicDAO = new BaTxnMnemonicDAO(this.getHSession());
						BaTxnMnemonic txnMnemonic = baTxnMnemonicDAO.getByCodTxnMnemonic(trx.getCodTxnMnemonic());
						
						this.WICTrx.setRefNo("disallowed");
						this.WICTrx.setNarrative(txnMnemonic.getTxtTxnDesc());
					}
				}
			}
			else {
				this.wicService.setWICTrxDAO(new WICTrxDAO(this.getHSession()));
				
				WICTrx wicTrx = new WICTrx(this.wic.getWICNo(), trxRefNo);
				this.WICTrx = this.wicService.getTrx(wicTrx);
			}
			
			if ((this.WICTrx != null) && (this.WICTrx.getRefNo() != null)) {
				/* Currency */
				if (this.WICTrx.getCurrencyCode() != null) {
					DataMasterService fcrDataMasterService = new DataMasterService();
					fcrDataMasterService.setDataMasterDAO(new DataMasterDAO(this.getHSession()));
					
					Object[] data = fcrDataMasterService.getDataById("currency", this.WICTrx.getCurrencyCode());
					this.putToObjData("namTxnCcy", (data!=null)? data[1]: null);
				}
				
				/* Account Name */
				if (this.WICTrx.getAccountNo() != null) {
					AccountService fcrAcctService = new AccountService();
					fcrAcctService.setFCRChAcctMastDAO(new ChAcctMastDAO(this.getHSession()));
					ChAcctMast acctMast = fcrAcctService.getByAccountNo(this.WICTrx.getAccountNo().trim());
					
					if (acctMast != null) { // Check on CH_ACCT_MAST table
						this.putToObjData("namAcctNo", acctMast.getCodAcctTitle());
						if (this.WICTrx.getCIFNo() == null)
							this.WICTrx.setCIFNo(Long.valueOf(acctMast.getCodCust().intValue()));
					}
					else { // Check on GL_MASTER table
						String GLAcctNo = this.WICTrx.getAccountNo();
						Integer accountNo = Integer.valueOf(GLAcctNo.substring(GLAcctNo.length() - 12).trim());
						Integer branchCode = Integer.valueOf(GLAcctNo.substring(0, GLAcctNo.length() - 12).trim());
						
						fcrAcctService.setFCRGLMasterDAO(new GLMasterDAO(this.getHSession()));
						GLMaster GLMaster = fcrAcctService.getGLAccountByBranchAccountAndCurrency(branchCode, accountNo, this.WICTrx.getCurrencyCode());
						
						this.putToObjData("namAcctNo", (GLMaster != null)? GLMaster.getNamGLCode(): "");
					}
				}
				
				resultAction = SUCCESS;
			}
			else
				this.setErrorCode("error.1");
		}
		
		return resultAction;
	}
	
	
	public String doInsertWICTrx() {
		return this.processWICTrx("insert", "success.0", "error.0");
	}
	
	public String doDeleteWICTrx() {
		return this.processWICTrx("delete", "success.2", "error.4");
	}
	
	protected String processWICTrx(String actionType, String successCode, String errorCode) {
		this.wicService.setWICTrxDAO(new WICTrxDAO(this.getHSession()));
		
		String resultAction = ERROR;
		try {
			if ("insert".equals(actionType))
				this.WICTrx = this.wicService.insertTrx(this.WICTrx);
			else if ("delete".equals(actionType))
				this.WICTrx = this.wicService.deleteTrx(this.WICTrx);
			
			this.wicService.commit();
			this.setErrorCode(successCode);
			resultAction = SUCCESS;
		}
		catch (FIXException ex) {
			if (ex.getMessage().toLowerCase().contains("fail")) {
				this.setErrorCode(errorCode);
				this.wicService.rollback();
			}
			else
				this.setErrorCode(ex.getMessage());
		}
		
		this.wicService.setWICTrxDAO(null);
		return resultAction;
	}
	
	@Override
	public WIC getModel() {
		return this.wic;
	}
	
	public WICTrx getWICTrx() {
		return this.WICTrx;
	}
	public void setWICTrx(WICTrx WICTrx) {
		this.WICTrx = WICTrx;
	}
	
	
	@Override
	protected void closeSession() {
		super.closeSession();
		
		if ((this.localSession != null) && (this.localSession.isOpen()))
			this.localSession.close();
	}
	
}
