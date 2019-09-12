package bdsm.web.menu24301;


import java.util.Map;

import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;

import bdsm.model.WIC;
import bdsm.util.BdsmUtil;
import bdsm.util.HttpUtil;


/**
 * 
 * @author v00019372
 */
@SuppressWarnings("serial")
public class Menu24301Action extends bdsm.web.ModelDrivenBaseContentAction<WIC> {
	private static final String GET_ACTION = "WICHost_get.action";
	private static final String INSERT_ACTION = "WICHost_insert.action";
	private static final String UPDATE_ACTION = "WICHost_update.action";
	private static final String DELETE_ACTION = "WICHost_delete.action";
	
	private WIC wic = new WIC();
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
			params.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));
			
			String result = HttpUtil.request(this.getBdsmHost() + GET_ACTION, params);
			
			try {
				Map<String, Object> resultMap = (Map<String, Object>) JSONUtil.deserialize(result);
				
				if (resultMap.get("model") != null)
					copyMapValueToObject((Map) resultMap.get("model"), this.getModel());
				
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
		this.process("edit");
		return SUCCESS;
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
		String action = "";
		
		if ("add".equals(actionType))
			action = INSERT_ACTION;
		else if ("edit".equals(actionType))
			action = UPDATE_ACTION;
		else if ("delete".equals(actionType))
			action = DELETE_ACTION;
		
		params = this.createParameterMapFromHTTPRequest();
		params.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));
		
		try {
			String result = HttpUtil.request(this.getBdsmHost() + action, params);
			
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
	
}
