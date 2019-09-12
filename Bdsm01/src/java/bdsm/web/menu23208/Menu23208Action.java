package bdsm.web.menu23208;


import static bdsm.web.Constant.C_WEB_SESSION_ID;

import java.util.List;
import java.util.Map;

import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;

import bdsm.util.BdsmUtil;


/**
 * @author v00019372
 */
@SuppressWarnings("serial")
public class Menu23208Action extends bdsm.web.BaseContentAction {
	private java.util.Map<String, String> mapFilename;
	private String batchNo;
	private String recordId;
	private String batchNoWS;
	
	
    /**
     * 
     * @return
     */
    @Override
	@SuppressWarnings("unchecked")
	public String doInput() {
		Map<String, String> params = new java.util.HashMap<String, String>();
		
		params.put("methodName", "listFilename");
		params.put("branchNo", this.session.get("cdBranch").toString());
		params.put("status", "R");
		params.put(C_WEB_SESSION_ID, this.getSessionId());
		
		params.put("token", BdsmUtil.generateToken(this.getTokenKey(), this.getTzToken()));
		String result = bdsm.util.HttpUtil.request(this.getBdsmHost() + "SknNgReturInwardDebit_callMethod", params);
		this.getLogger().debug(result);
		
		Map<String, ? extends Object> mapResult = null;
		try {
			mapResult = (Map<String, ? extends Object>) JSONUtil.deserialize(result);
			List<? extends Object> listAuditBatch = (List<? extends Object>) mapResult.get("listAuditBatch");
			
			mapFilename = new java.util.LinkedHashMap<String, String>(listAuditBatch.size());
			for (Object o : listAuditBatch) {
				Map<String, ? extends Object> m = (Map<String, ? extends Object>) o;
				if (mapFilename.containsKey(m.get("batchNoOriginal")) == false)
					mapFilename.put(m.get("batchNoOriginal").toString(), m.get("filenameOriginal").toString());
			}
		}
		catch (JSONException je) {
			this.getLogger().error(je, je);
		}
		
		return super.doInput();
	}
	
    /**
     * 
     * @return
     */
    @Override
	public String exec() {
		/* Update to Original Table and Resend to SPK Web Service */
		Map<String, String> params = new java.util.HashMap<String, String>();
		
		params.put("methodName", "sendToSPKWebService");
		params.put("useTransaction", "true");
		params.put("batchNo", this.batchNo);
		params.put("recordId", this.recordId);
		params.put("batchNoWS", this.batchNoWS);
		params.put(C_WEB_SESSION_ID, this.getSessionId());
		
		params.put("token", BdsmUtil.generateToken(this.getTokenKey(), this.getTzToken()));
		bdsm.util.HttpUtil.request(this.getBdsmHost() + "SknNgReturInwardDebit_callMethod", params);
		
		return "successSendWS";
	}

    /**
     * 
     * @return
     */
    @Override
	public String doAdd() {
		throw new UnsupportedOperationException("Not supported yet.");
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
		throw new UnsupportedOperationException("Not supported yet.");
	}


    /**
     * 
     * @return
     */
    public java.util.Map<String, String> getMapFilename() {
		return this.mapFilename;
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
     * @param recordId
     */
    public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

    /**
     * 
     * @param batchNoWS
     */
    public void setBatchNoWS(String batchNoWS) {
		this.batchNoWS = batchNoWS;
	}
}
