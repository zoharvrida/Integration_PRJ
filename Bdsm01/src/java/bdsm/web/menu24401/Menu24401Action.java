package bdsm.web.menu24401;


import java.util.Map;

import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;

import bdsm.model.WIC;
import bdsm.model.WICTrx;
import bdsm.util.BdsmUtil;
import bdsm.util.HttpUtil;


/**
 * 
 * @author v00019372
 */
@SuppressWarnings("serial")
public class Menu24401Action extends bdsm.web.ModelDrivenBaseContentAction<WIC> {
	private static final String CALLMETHOD_ACTION = "WICHost_callMethod.action";
	private static final String GET_ORI_METHOD = "doGetWICAndOriTrx";
	private static final String GET_METHOD = "doGetWICAndTrx";
	private static final String INSERT_METHOD = "doInsertWICTrx";
	private static final String DELETE_METHOD = "doDeleteWICTrx";
	
	private WIC wic = new WIC();
	private WICTrx WICTrx = new WICTrx();
	private String jsonStatus;
	private String errorCode;

    /**
     * 
     * @return
     */
    @Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String exec() {
		Map<String, String> params = null;
		
		if (this.getFieldErrors().size() == 0) {
			params = this.createParameterMapFromHTTPRequest();
			
			if (this.getFromStrData("searchForType").equals("add"))
				params.put("methodName", GET_ORI_METHOD);
			else /* inquiry or delete */
				params.put("methodName", GET_METHOD);
			
			params.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));
			
			String result = HttpUtil.request(this.getBdsmHost() + CALLMETHOD_ACTION, params);
			
			try {
				Map<String, Object> resultMap = (Map<String, Object>) JSONUtil.deserialize(result);
				
				if (resultMap.get("model") != null)
					copyMapValueToObject((Map) resultMap.get("model"), this.getModel());
				if (resultMap.get("WICTrx") != null)
					copyMapValueToObject((Map) resultMap.get("WICTrx"), this.getWICTrx());
					
				
				this.setObjData((Map) resultMap.get("objData"));
				this.setObjType((Map) resultMap.get("objType"));
				this.convertObjDataValueAccordingToObjType();
				
				this.jsonStatus = (String) resultMap.get("jsonStatus");
				this.errorCode = (String) resultMap.get("errorCode");
			}
			catch (JSONException jex) {
				this.getLogger().fatal(jex, jex);
			}
		}
		
		if (SUCCESS.equals(this.jsonStatus)) {
			this.addActionMessage(getText(this.errorCode));
		}
		else if (ERROR.equals(this.jsonStatus)) {
			this.addActionError(getText(this.errorCode));
		}
		
		return "data";
	}
	
    /**
     * 
     * @return
     */
    @Override
	public String doAdd() {
		this.process("add");
		return SUCCESS;
	}

    /**
     * 
     * @return
     */
    @Override
	public String doEdit() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

    /**
     * 
     * @return
     */
    @Override
	public String doDelete() {
		this.process("delete");
		return SUCCESS;
	}
	
	
    /**
     * 
     * @param actionType
     */
    @SuppressWarnings("unchecked")
	protected void process(String actionType) {
		Map<String, String> params = null;
		
		params = this.createParameterMapFromHTTPRequest();
		params.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));
		
		if ("add".equals(actionType))
			params.put("methodName", INSERT_METHOD);
		else if ("delete".equals(actionType))
			params.put("methodName", DELETE_METHOD);
		
		params.put("useTransaction", Boolean.TRUE.toString());
		
		try {
			String result = HttpUtil.request(this.getBdsmHost() + CALLMETHOD_ACTION, params);
			
			Map<String, Object> resultMap = (Map<String, Object>) JSONUtil.deserialize(result);
			this.jsonStatus = (String) resultMap.get("jsonStatus");
			this.errorCode = (String) resultMap.get("errorCode");
		}
		catch(JSONException jex) {
			this.getLogger().fatal(jex, jex);
		}
		
		if (SUCCESS.equals(this.jsonStatus)) {
			this.addActionMessage(getText(this.errorCode));
		}
		else if (ERROR.equals(this.jsonStatus)) {
			this.addActionError(getText(this.errorCode));
		}
	}
	
	
    /**
     * 
     * @return
     */
    public WIC getModel() {
		return this.wic;
	}
	
    /**
     * 
     * @return
     */
    public WICTrx getWICTrx() {
		return this.WICTrx;
	}
    /**
     * 
     * @param WICTrx
     */
    public void setWICTrx(WICTrx WICTrx) {
		this.WICTrx = WICTrx;
	}
	
}
