package bdsmhost.web;


import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;

import bdsm.fcr.model.ChAcctMast;
import bdsm.fcr.model.CiCustMast;
import bdsm.fcr.service.AccountService;
import bdsm.fcr.service.CustomerService;
import bdsm.fcr.service.DataMasterService;
import bdsm.model.BillerStandingInstruction;
import bdsm.model.BillerStandingInstructionHistory;
import bdsm.model.Parameter;
import bdsm.util.BdsmUtil;
import bdsmhost.dao.BillerStandingInstructionDAO;
import bdsmhost.dao.ParameterDao;
import bdsmhost.fcr.dao.BaBankMastDAO;
import bdsmhost.fcr.dao.ChAcctMastDAO;
import bdsmhost.fcr.dao.CiCustMastDao;
import bdsmhost.fcr.dao.DataMasterDAO;
import bdsmhost.interceptor.AbstractRequestApprovalHistoryInterceptor;


/**
 * @author v00019372
 */
@SuppressWarnings("serial")
public class BillerAction extends ModelDrivenBaseHostAction<BillerStandingInstruction> {
	private static Logger LOGGER = Logger.getLogger(BillerAction.class);
	private org.hibernate.Session localSession;
	private BillerStandingInstruction model = new BillerStandingInstruction();
	private ChAcctMast account;
	private CiCustMast customer;
	
	
	@JSON(serialize=false)
	public String getMaxNominal() {
		ParameterDao paramDAO = new ParameterDao(this.getHSession());
		Parameter param = paramDAO.get("BILLER_STAND_INSTR_MAX_NOMINAL");
		this.putToObjData("maxNominal", (param != null)? new BigDecimal(param.getValue()): null);
		
		return SUCCESS;
	}
	
	public String accountNoLookup() {
		AccountService srvc = new AccountService();
		srvc.setFCRChAcctMastDAO(new ChAcctMastDAO(this.getHSession()));
		BaBankMastDAO baBankMastDAO = new BaBankMastDAO(this.getHSession());
		String strTemp = null;
		
		this.account = srvc.getByAccountNo(this.model.getAccountNo());
		if (this.account != null) {
			CustomerService service = new CustomerService();
			service.setCiCustMastDAO(new CiCustMastDao(this.getHSession()));
			
			this.customer = service.getByCustomerId(this.account.getCodCust());
			
			try {
				// BIC
				BillerStandingInstructionDAO bsiDAO = new BillerStandingInstructionDAO(this.getHSession()); 
				strTemp = bsiDAO.getBICode(this.account.getCodProd());
				this.putToObjData("BIC", strTemp);
				
				// Bank Name
				this.putToObjData("txtBankName", baBankMastDAO.get().getNamBank());
				
				// Text Customer Type
				strTemp = service.getCustomerTypeText(this.customer.getFlgCustTyp());
				this.putToObjData("txtCustomerType", StringUtils.defaultString(strTemp));
				
				// Text Residential Status
				strTemp = service.getResidentialStatusText(this.customer.getCiCustMastPK().getCodCustId());
				this.putToObjData("txtResidentialStatus", StringUtils.defaultString(strTemp));
			}
			catch (Exception ex) {
				LOGGER.error(ex, ex);
			}
		}
		
		return SUCCESS;
	}


	public ChAcctMast getAccount() {
		return this.account;
	}

	public CiCustMast getCustomer() {
		return this.customer;
	}


	@Override
	public BillerStandingInstruction getModel() {
		return this.model;
	}


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
		BillerStandingInstructionDAO bsiDAO = new BillerStandingInstructionDAO(this.getHSession());
		this.model = bsiDAO.getById(this.model.getId());
		
		if (this.model != null) {
			if (("X".equalsIgnoreCase(this.model.getFlagStatus())) && ("delete".equalsIgnoreCase(this.getFromStrData("mode"))))
				this.model = null;
			else
				this.descriptionLookup();
		}
		
		return SUCCESS;
	}


	@Override
	public String doSave() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public String doInsert() {
		BillerStandingInstructionDAO bsiDAO = new BillerStandingInstructionDAO(this.getHSession());
		java.text.DateFormat dateFormatter = new java.text.SimpleDateFormat("ddMMyy");
		StringBuilder id = new StringBuilder(this.model.getId());
		
		this.getMaxNominal();
		
		id.append(this.model.getAccountNo().substring(this.model.getAccountNo().length() - 3));
		String startIdBy = ""; //id.toString();
		
		id.append(dateFormatter.format(new java.util.Date()));
		id.append(BdsmUtil.leftPad(this.model.getBillingNo(), 15, '0'));
		
		this.model.setId(id.toString());
		
		if (bsiDAO.isAlreadyExist(startIdBy, this.model.getBillingNo(), this.model.getBillerAccountNo()) == false) {
			bsiDAO.insert(this.model);
			this.descriptionLookup();
			
			return SUCCESS;
		}
		else {
			this.setErrorCode("exist");
			
			return ERROR;
		}
	}

	@Override
	public String doUpdate() {
		String strReturn = this.process("update");
		this.descriptionLookup();
		
		return strReturn;
	}


	@Override
	public String doDelete() {
		return this.process("delete");
	}


	private String process(String type) {
		BillerStandingInstructionDAO bsiDAO = new BillerStandingInstructionDAO(this.getHSession());
		bsiDAO.setInterceptor(new AbstractRequestApprovalHistoryInterceptor<BillerStandingInstructionHistory>() {});
		
		this.model.setFlagStatus(type.equalsIgnoreCase("update")? "A": "X");
		bsiDAO.update(this.model);
		
		this.localSession = bsiDAO.getLocalSession();
		this.getMaxNominal();
		
		return SUCCESS;
	}
	
	private void descriptionLookup() {
		DataMasterService fcrDataMasterService = new DataMasterService();
		fcrDataMasterService.setDataMasterDAO(new DataMasterDAO(this.getHSession()));
		
		this.getDescriptionAndPutToObjData(fcrDataMasterService, "bankBillerStandingInstruction", "billerBankName");
	}

	private void getDescriptionAndPutToObjData(DataMasterService service, String masterCode, String propertyName) {
		try {
			Object value = org.apache.commons.beanutils.PropertyUtils.getProperty(this.model, propertyName);
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
	protected void closeSession() {
		super.closeSession();
		
		if ((this.localSession != null) && (this.localSession.isOpen()))
			this.localSession.close();
	}

}
