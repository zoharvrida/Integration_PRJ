package bdsm.dialog.json;


import static bdsm.web.Constant.C_WEB_SESSION_ID;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.JSONUtil;
import org.apache.struts2.json.annotations.JSON;

import bdsm.util.BdsmUtil;
import bdsm.util.HttpUtil;


/**
 * 
 * @author v00019372
 */
@SuppressWarnings({ "serial",  "rawtypes", "unchecked" })
public class Dialog25301PickListAction extends BaseDialogAction {
	private static final String AMORTIZE_GIFT_PROGRAM_MASTER_LIST = "AmortizeGiftProgramMaster_list";
	
	private Map<String, String> modelMap;
	private String giftCode;
	private String mode;
	private String term; // for autocompleter

	
    /**
     * 
     * @return
     * @throws Exception
     */
    @Override
	public List doList() throws Exception {
		HashMap<String, String> params = new HashMap<String, String>();
		String HTTPRequest = AMORTIZE_GIFT_PROGRAM_MASTER_LIST;
		
		params.put("compositeId.giftCode", this.giftCode);
		params.put("mode", this.mode);
		params.put("term", this.term);
		params.put(C_WEB_SESSION_ID, this.getSessionId());
		params.put("token", BdsmUtil.generateToken(this.getTokenKey(), this.getTzToken()));
		String result = HttpUtil.request(this.getBdsmHost() + HTTPRequest, params);

		HashMap ret = (HashMap) JSONUtil.deserialize(result);
		List listRet = (List<? extends Object>) ret.get("modelList");
		this.modelMap = (Map<String, String>) ret.get("modelMap"); 
		String status = (String) ret.get("jsonStatus");
		addActionError(status);
		
		return (listRet==null)? new java.util.ArrayList(): listRet;
	}
	
    /**
     * 
     * @return
     */
    @JSON(serialize=false)
	protected String getSessionId() {
		String sessionId = (String) this.session.get(C_WEB_SESSION_ID);
		
		if (sessionId == null) {
			sessionId = ServletActionContext.getRequest().getSession().getId();
			this.session.put(C_WEB_SESSION_ID, sessionId);
		}
		
		return sessionId;
	}
	
	
    /**
     * 
     * @return
     */
    public Map<String, String> getModelMap() {
		return this.modelMap;
	}
	
    /**
     * 
     * @param giftCode
     */
    public void setGiftCode(String giftCode) {
		this.giftCode = giftCode;
	}
	
    /**
     * 
     * @param mode
     */
    public void setMode(String mode) {
		this.mode = mode;
	}
	
    /**
     * 
     * @param term
     */
    public void setTerm(String term) {
		this.term = term;
	}

}
