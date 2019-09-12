package bdsm.dialog.json;


import static bdsm.web.Constant.C_WEB_SESSION_ID;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.JSONUtil;

import bdsm.util.BdsmUtil;



/**
 * @author v00019372
 */
@SuppressWarnings({"serial", "rawtypes"})
public class Dialog23206Action extends BaseDialogAction {
	private String action;
	private String batchNo;
	private String recordId;
	private String idUser;
	private Map<String, String> data;
	private String oper;

    /**
     * 
     * @return
     * @throws Exception
     */
    @Override
	@SuppressWarnings("unchecked")
	public List<? extends Object> doList() throws Exception {
		java.util.Map<String, String> params = new java.util.HashMap<String, String>();
		boolean isHeader = this.action.equalsIgnoreCase("header");
		
		params.put("methodName", isHeader? "listHeader": "listDetail");
		params.put("batchNo", this.batchNo);
		params.put("status", "R");
		params.put(C_WEB_SESSION_ID, this.getSessionId());
		
		if (isHeader == false)
			params.put("recordId", this.recordId);
		
		params.put("token", BdsmUtil.generateToken(this.getTokenKey(), this.getTzToken()));
		String result = bdsm.util.HttpUtil.request(this.getBdsmHost() + "SknNgOutwardDebit_callMethod", params);
		this.getLogger().debug(result);
		
		Map<String, ? extends Object> resultMap = (Map) JSONUtil.deserialize(result);
		
		return ((List<? extends Object>) resultMap.get(isHeader? "listHeader": "listDetail"));
	}
	
    /**
     * 
     * @return
     */
    public String doEditDetail() {
		this.getLogger().info("[Begin] doEditDetail()");
		try {
			if (this.isValidSession()) {
				if ("edit".equalsIgnoreCase(this.oper)) {
					java.util.Map<String, String> params = new java.util.HashMap<String, String>();
					params.put("batchNo", this.batchNo);
					params.put("recordId", this.recordId);
					params.put("idUser", this.idUser);
					params.put(C_WEB_SESSION_ID, this.getSessionId());
					
					if ((this.data != null) && (this.data.size() > 0)) {
						Iterator<String> it = this.data.keySet().iterator();
						while (it.hasNext()) {
							String k = it.next();
							params.put("data." + k, this.data.get(k));
						}
						
						params.put("token", BdsmUtil.generateToken(this.getTokenKey(), this.getTzToken()));
						bdsm.util.HttpUtil.request(this.getBdsmHost() + "SknNgOutwardDebit_update", params);
					}
				}
				return SUCCESS;
			}
			else
				return logout();
		}
		finally {
			this.getLogger().info("[ End ] doEditDetail()");
		}
	}
	
	
    /**
     * 
     * @return
     */
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
    public String getAction() {
		return this.action;
	}
    /**
     * 
     * @param action
     */
    public void setAction(String action) {
		this.action = action;
	}
	
    /**
     * 
     * @return
     */
    public String getBatchNo() {
		return this.batchNo;
	}
    /**
     * 
     * @param batchNo
     */
    public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	
    /**
     * 
     * @return
     */
    public String getRecordId() {
		return this.recordId;
	}
    /**
     * 
     * @param recordId
     */
    public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	
    /**
     * 
     * @return
     */
    public String getIdUser() {
		return this.idUser;
	}
    /**
     * 
     * @param idUser
     */
    public void setIdUser(String idUser) {
		this.idUser = idUser;
	}
	
    /**
     * 
     * @return
     */
    public Map<String, ? extends Object> getData() {
		return this.data;
	}
    /**
     * 
     * @param data
     */
    public void setData(Map<String, String> data) {
		this.data = data;
	}
	
    /**
     * 
     * @param oper
     */
    public void setOper(String oper) {
		this.oper = oper;
	}

}
