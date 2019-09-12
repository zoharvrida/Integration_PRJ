package bdsm.dialog;


import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.json.JSONUtil;

import bdsm.util.BdsmUtil;
import bdsm.util.HttpUtil;


/**
 * @author v00019372
 */
@SuppressWarnings("serial")
public class EKTPReaderAuthAction extends AuthAction implements SessionAware {
	private Map<String, Object> session;
	private String deviceIP;
	private String divName;
	private int authorized;
	
    /**
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
	public String execute() {
		String return_ = super.execute();
		this.authorized = this.isAuthorized();
		
		try {
			if (return_.equals(SUCCESS) && (this.isAuthorized() == 1)) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("methodName", "checkValidSupervisorDevice");
		        map.put("idUser", this.getIdUser());
		        map.put("deviceIP", this.deviceIP);
		        map.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));
		        
		        String result = HttpUtil.request(getBdsmHost() + "EKTPReader_callMethod.action", map);
		        Map<String, Object> resultMap = (Map<String, Object>) JSONUtil.deserialize(result);
		        
		        if (StringUtils.isNotBlank((String) resultMap.get("EKTPUser"))) {
		        	this.session.put("EKTPSpvUser", resultMap.get("EKTPUser"));
		        	this.session.put("EKTPSpvPassword", resultMap.get("EKTPPassword"));
		        }
		        else {
		        	this.authorized = 3;
		        	this.addActionError(this.getIdUser() + " is not supervisor device");
		        	
		        	return INPUT;
		        }
		        	
			}
		}
		catch (Exception ex) {
			this.session.remove("EKTPSpvUser");
        	this.session.remove("EKTPSpvPassword");
        	this.authorized = 2;
		}
		
		return return_;
	}
	
	
	
    /**
     * 
     * @param session
     */
    @Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	
    /**
     * 
     * @param deviceIP
     */
    public void setDeviceIP(String deviceIP) {
		this.deviceIP = deviceIP;
	}
	
    /**
     * 
     * @param divName
     */
    public void setDivName(String divName) {
		this.divName = divName;
	}
	
    /**
     * 
     * @return
     */
    public int getAuthorized() {
		return this.authorized;
	}
	
    /**
     * 
     * @return
     */
    public String getAction() {
		return "";
	}

}
