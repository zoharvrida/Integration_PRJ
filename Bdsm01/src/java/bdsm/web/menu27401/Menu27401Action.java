package bdsm.web.menu27401;


import static bdsm.web.Constant.C_WEB_SESSION_ID;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.json.JSONException;

import bdsm.fcr.model.ChAcctMast;
import bdsm.fcr.model.ChAcctMastPK;
import bdsm.fcr.model.ChProdMast;
import bdsm.fcr.model.ChProdMastPK;
import bdsm.model.MPAcctReg;
import bdsm.model.MPClass;
import bdsm.model.MPProductMIS;


/**
 * @author v00019372
 */
@SuppressWarnings({ "serial", "rawtypes" })
public class Menu27401Action extends bdsm.web.ModelDrivenBaseContentAction<MPAcctReg> {
	private MPAcctReg acctReg;
	private int mode;
	private int flagResult;
	private ChAcctMast account;
	private ChProdMast product;
	private MPProductMIS productMIS;
	private MPClass MPClass;
	private List listCompCommitment;
	private List listCompLogic;
	private String jsonStatus;
	private String errorCode;
	
	
    /**
     * 
     */
    public Menu27401Action() {
		this.acctReg = new MPAcctReg();
		this.account = new ChAcctMast();
		this.account.setCompositeId(new ChAcctMastPK());
		
		this.product = new ChProdMast();
		this.product.setCompositeId(new ChProdMastPK());
		
		this.productMIS = new MPProductMIS();
	}
	
	
    /**
     * 
     * @return
     */
    @Override
	@SuppressWarnings({ "unchecked" })
	public String exec() {
		Map<String, String> requestMap = new HashMap<String, String>();
		boolean isInquiryMode = (this.mode == 0);
		
		requestMap.put("noAccount", this.acctReg.getNoAccount());
		if (!isInquiryMode)
			requestMap.put("methodName", "validateAccount");
		else
			requestMap.put(C_WEB_SESSION_ID, this.getSessionId());
		
		try {
			Map accountMap, productMap, productMISMap, classMap, modelMap;
			Map<String, ? extends Object> resultMap = this.callHostHTTPRequest("MitraPasti", (isInquiryMode? "get": "callMethod"), requestMap);
			
			if ((accountMap = ((Map) resultMap.get("account"))) != null) {
				copyMapValueToObject(accountMap, this.account);
				
				if (this.account.getCodCust() != null) {
					if ((productMap = ((Map) resultMap.get("product"))) != null) {
						copyMapValueToObject(productMap, this.product);
						
						if (isInquiryMode) {
							if ((productMISMap = ((Map) resultMap.get("productMIS"))) != null) {
								copyMapValueToObject(productMISMap, this.productMIS);
								
								if ((classMap = ((Map) resultMap.get("MPClass"))) != null) {
									this.MPClass = new MPClass();
									copyMapValueToObject(classMap, this.MPClass);
									
									modelMap = (Map) resultMap.get("model");
									copyMapValueToObject(modelMap, this.acctReg);
									
									this.listCompCommitment = (List) resultMap.get("listCompCommitment");
									this.listCompLogic = (List) resultMap.get("listCompLogic");
								}
							}
							else
								this.flagResult = -5; // Product MIS not Registered in BDSM
						}
					}
					else
						this.flagResult = -4; // Product not Registered in BDSM
				}
				else
					this.flagResult = -2; // Account Registration already exists in BDSM System
			}
			else {
				if (isInquiryMode)
					this.flagResult = -3; // Data isn't exist or already expired
				else
					this.flagResult = -1; // Invalid Account Number
			}
			
		}
		catch (JSONException je) {
			this.getLogger().error(je, je);
			return ERROR;
		}
		
		return SUCCESS;
	}
	
    /**
     * 
     * @return
     */
    @SuppressWarnings({ "unchecked" })
	public String loadTabComponent() {
		Map<String, String> requestMap = new HashMap<String, String>();
		boolean isInquiryMode = (this.acctReg.getId() != null);
		
		requestMap.put("methodName", "listComponentByClass");
		requestMap.put("codeClass", this.acctReg.getCodeClass().toString());
		requestMap.put(C_WEB_SESSION_ID, this.getSessionId());
		if (isInquiryMode)
			requestMap.put("id", this.acctReg.getId().toString());
		
		try {
			Map<String, ? extends Object> resultMap = this.callHostHTTPRequest("MitraPasti", "callMethod", requestMap);
			
			this.listCompCommitment = (List) resultMap.get("listCompCommitment");
			this.listCompLogic = (List) resultMap.get("listCompLogic");
		}
		catch (JSONException je) {
			this.getLogger().error(je, je);
			return ERROR;
		}
		
		return "tabComponent";
	}

    /**
     * 
     * @return
     */
    @Override
	@SuppressWarnings("unchecked")
	public String doAdd() {
		Map<String, String> requestMap = new HashMap<String, String>(this.createParameterMapFromHTTPRequest());
		
		try {
			Map<String, ? extends Object> resultMap = this.callHostHTTPRequest("MitraPasti", "insert", requestMap);
			this.jsonStatus = (String) resultMap.get("jsonStatus");
			this.errorCode = (String) resultMap.get("errorCode");
			
			if (SUCCESS.equals(jsonStatus)) {
				this.addActionMessage(this.getText("success.0"));
			}
			else if (ERROR.equals(jsonStatus)) {
				this.addActionError("Failed: " + this.errorCode);
			}
		}
		catch (JSONException je) {
			this.getLogger().error(je, je);
			return ERROR;
		}
		
		return INPUT;
	}

    /**
     * 
     * @return
     */
    @Override
	@SuppressWarnings("unchecked")
	public String doEdit() {
		Map<String, String> requestMap = new HashMap<String, String>(this.createParameterMapFromHTTPRequest());
		boolean isChangePackage = (this.mode == 1);
		
		if (isChangePackage) {
			requestMap.put("methodName", "changePackage");
			requestMap.put("useTransaction", "true");
		}
		
		try {
			Map<String, ? extends Object> resultMap = this.callHostHTTPRequest("MitraPasti", (isChangePackage? "callMethod": "update"), requestMap);
			this.jsonStatus = (String) resultMap.get("jsonStatus");
			this.errorCode = (String) resultMap.get("errorCode");
			
			if (SUCCESS.equals(jsonStatus)) {
				this.addActionMessage(this.getText("success.1"));
			}
			else if (ERROR.equals(jsonStatus)) {
				this.addActionError("Failed: " + this.errorCode);
			}
		}
		catch (JSONException je) {
			this.getLogger().error(je, je);
			return ERROR;
		}
		
		return INPUT;
	}

    /**
     * 
     * @return
     */
    @Override
	@SuppressWarnings("unchecked")
	public String doDelete() {
		Map<String, String> requestMap = new HashMap<String, String>(this.createParameterMapFromHTTPRequest());
		
		try {
			Map<String, ? extends Object> resultMap = this.callHostHTTPRequest("MitraPasti", "delete", requestMap);
			this.jsonStatus = (String) resultMap.get("jsonStatus");
			this.errorCode = (String) resultMap.get("errorCode");
			
			if (SUCCESS.equals(jsonStatus)) {
				this.addActionMessage(this.getText("success.2"));
			}
			else if (ERROR.equals(jsonStatus)) {
				this.addActionError("Failed: " + this.errorCode);
			}
		}
		catch (JSONException je) {
			this.getLogger().error(je, je);
			return ERROR;
		}
		
		return INPUT;
	}
	

	
    /**
     * 
     * @return
     */
    @Override
	public MPAcctReg getModel() {
		return this.acctReg;
	}
	
    /**
     * 
     * @param mode
     */
    public void setMode(int mode) {
		this.mode = mode;
	}
	
    /**
     * 
     * @return
     */
    public int getFlagResult() {
		return this.flagResult;
	}
	
    /**
     * 
     * @return
     */
    public ChAcctMast getAccount() {
		return this.account;
	}
	
    /**
     * 
     * @return
     */
    public ChProdMast getProduct() {
		return this.product;
	}
	
    /**
     * 
     * @return
     */
    public MPProductMIS getProductMIS() {
		return this.productMIS;
	}
	
    /**
     * 
     * @return
     */
    public MPClass getMPClass() {
		return this.MPClass;
	}
	
    /**
     * 
     * @return
     */
    public List getListCompCommitment() {
		return this.listCompCommitment;
	}
	
    /**
     * 
     * @return
     */
    public List getListCompLogic() {
		return this.listCompLogic;
	}
	
}
