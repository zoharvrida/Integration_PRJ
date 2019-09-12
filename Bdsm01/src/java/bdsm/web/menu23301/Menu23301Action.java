package bdsm.web.menu23301;


import java.util.Map;

import org.apache.commons.lang.WordUtils;
import org.apache.struts2.json.JSONException;

import com.opensymphony.xwork2.ActionSupport;

import bdsm.fcr.model.CiCustMast;
import bdsm.model.BillerStandingInstruction;
import bdsm.web.Constant;


/**
 * @author v00019372
 */
@SuppressWarnings("serial")
public class Menu23301Action extends bdsm.web.ModelDrivenBaseContentAction<BillerStandingInstruction> {
	private BillerStandingInstruction SI = new BillerStandingInstruction();
	private CiCustMast customer;
	
	private String jsonStatus;
	private String errorCode;


    /**
     * 
     * @return
     */
    @Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String doInput() {
		Map<String, String> requestMap = new java.util.HashMap<String, String>(0);
		requestMap.put("methodName", "getMaxNominal");
		
		Map<String, ? extends Object> result;
		try {
			result = this.callHostHTTPRequest("Biller", "callMethod", requestMap);
			this.setObjData((Map) result.get("objData"));
		}
		catch (JSONException jse) {
			this.getLogger().error(jse, jse);
		}
		
		return INPUT;
	}

    /**
     * 
     * @return
     */
    @Override
	@SuppressWarnings("unchecked")
	public String exec() {
		Map<String, Object> resultMap;
		Map<String, String> params = this.createParameterMapFromHTTPRequest();
		
		try {
			resultMap = this.callHostHTTPRequest("Biller", "get", params);
			if (resultMap.get("model") != null) {
				copyMapValueToObject((Map<String, ? extends Object>) resultMap.get("model"), this.SI);
				this.setObjData((Map<String, Object>) resultMap.get("objData"));
				
				Map<String, String> requestMap = new java.util.HashMap<String, String>(0);
				requestMap.put("accountNo", this.SI.getAccountNo());
				this.accountNoLookup_(requestMap);
			}
			
			this.jsonStatus = (String) resultMap.get("jsonStatus");
			this.errorCode = (String) resultMap.get("errorCode");
		}
		catch (JSONException e) {
			e.printStackTrace();
		}
		
		return "data";
	}

    /**
     * 
     * @return
     */
    @Override
	@SuppressWarnings("unchecked")
	public String doAdd() {
		Map<String, ? extends Object> resultMap = null;
		Map<String, String> params = this.createParameterMapFromHTTPRequest();
		params.put("idMaintainedBy", this.session.get(Constant.C_IDUSER).toString());
		
		try {
			resultMap = this.callHostHTTPRequest("Biller", "insert", params);
			this.jsonStatus = (String) resultMap.get("jsonStatus");
			this.errorCode = (String) resultMap.get("errorCode");
		}
		catch(JSONException jex) {
			this.getLogger().fatal(jex, jex);
		}
		
		this.setObjData((Map<String, Object>) resultMap.get("objData"));
		if (SUCCESS.equals(this.jsonStatus)) {
			this.addActionMessage(this.getText("success.0"));
			
			copyMapValueToObject((Map<String, ? extends Object>) resultMap.get("model"), this.SI);
		}
		else if (ERROR.equals(this.jsonStatus)) {
			if ("exist".equals(this.errorCode)) {
				this.addActionError(this.getText("error.0"));
				this.SI.setId("");
			}
		}
		
		return SUCCESS;
	}

    /**
     * 
     * @return
     */
    @Override
	@SuppressWarnings("unchecked")
	public String doEdit() {
		Map<String, Object> resultMap = new java.util.HashMap<String, Object>(0);
		String return_ = this.process("update", resultMap);
		copyMapValueToObject((Map<String, ? extends Object>) resultMap.get("model"), this.SI);
		
		return return_;
	}

    /**
     * 
     * @return
     */
    @Override
	public String doDelete() {
		return this.process("delete", new java.util.HashMap<String, Object>(0));
	}
	
    /**
     * 
     * @return
     */
    public String accountNoLookup() {
		this.getLogger().info("[Begin] accountNoLookup()");
		
		try {
			if (isValidSession()) {
				this.accountNoLookup_(this.createParameterMapFromHTTPRequest());
				return "accountNoLookup";
			}
			else
				return logout();
		}
		catch (Throwable e) {
			setErrorMessage(e.getMessage());
			this.getLogger().fatal(e, e);
			
			return ActionSupport.ERROR;
		}
		finally {
			this.getLogger().info("[ End ] accountNoLookup()");
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void accountNoLookup_(Map<String, String> requestMap) throws JSONException {
		requestMap.put("methodName", "accountNoLookup");
		
		Map result = this.callHostHTTPRequest("Biller", "callMethod", requestMap);
		Map model = (Map) result.get("customer");
		if (model != null) {
			this.customer = new CiCustMast();
			copyMapValueToObject(model, this.customer);
		}
		
		if (this.getObjData() == null)
			this.setObjData((Map) result.get("objData"));
		else
			this.getObjData().putAll((Map) result.get("objData"));
		
		
		if (this.getObjData() != null) {
			// BIC
			if (this.getFromObjData("BIC") != null)
				this.putToObjData("BIC", this.getFromObjData("BIC").toString().substring(0, 4));
			
			// txtCustomerType
			String txtCustomerType = (String) this.getFromObjData("txtCustomerType");
			if (txtCustomerType != null)
				this.putToObjData("txtCustomerType", (txtCustomerType.toLowerCase().startsWith("personal"))? "Perorangan" : "Perusahaan");
			
			// txtResidentialStatus
			String txtResidentialStatus = (String) this.getFromObjData("txtResidentialStatus");
			if (txtResidentialStatus != null)
				this.putToObjData("txtResidentialStatus", WordUtils.capitalize(txtResidentialStatus.toLowerCase()));
		}
	}


	@SuppressWarnings("unchecked")
	private String process(String type, Map<String, Object> parentResultMap) {
		Map<String, Object> resultMap = null;
		Map<String, String> params = this.createParameterMapFromHTTPRequest();
		params.put("idMaintainedBy", this.session.get(Constant.C_IDUSER).toString());
		
		try {
			resultMap = this.callHostHTTPRequest("Biller", type, params);
			this.jsonStatus = (String) resultMap.get("jsonStatus");
			this.errorCode = (String) resultMap.get("errorCode");
			
			parentResultMap.putAll(resultMap);
			this.setObjData((Map<String, Object>) resultMap.get("objData"));
		}
		catch(JSONException jex) {
			this.getLogger().fatal(jex, jex);
		}
		
		if (SUCCESS.equals(this.jsonStatus))
			this.addActionMessage(this.getText((type.equals("update")? "success.1": "success.2")));
		else if (ERROR.equals(this.jsonStatus))
			this.addActionError(this.getText(this.errorCode));
		
		return SUCCESS;
	}



    /**
     * 
     * @return
     */
    @Override
	public BillerStandingInstruction getModel() {
		return this.SI;
	}

    /**
     * 
     * @return
     */
    public CiCustMast getCustomer() {
		return this.customer;
	}

    /**
     * 
     * @return
     */
    public java.util.List<Integer> getDateList() {
		java.util.List<Integer> list = new java.util.ArrayList<Integer>();
		for (int i=1; i<=31; i++)
			list.add(Integer.valueOf(i));
		
		return list;
	}

}
