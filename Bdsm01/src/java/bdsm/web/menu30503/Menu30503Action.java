package bdsm.web.menu30503;


import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.struts2.json.JSONUtil;


/**
 * @author v00019372
 */
@SuppressWarnings("serial")
public class Menu30503Action extends bdsm.web.BaseContentAction {
	private String baseResourceURI;
	private String baseProtocols;
	private Map<String, String> mapIP;
	private String action;
	private String deviceIP;
	private String log;

	private String EKTPOprUser;
	private String EKTPOprPassword;



	
    /**
     * 
     * @return
     */
    @Override
	@SuppressWarnings("unchecked")
	public String exec() {
		if (this.action.equals("OPR")) {
			try {
				Map<String, String> requestMap = new HashMap<String, String>();
				requestMap.put("methodName", "checkValidOperatorDevice");
				requestMap.put("idUser", this.session.get("idUser").toString());
				requestMap.put("deviceIP", this.deviceIP);
				requestMap.put("token", bdsm.util.BdsmUtil.generateToken(this.getTokenKey(), this.getTzToken()));
	
				String result = bdsm.util.HttpUtil.request(this.getBdsmHost() + "EKTPReader_callMethod.action", requestMap);
				Map<String, Object> resultMap = (Map<String, Object>) JSONUtil.deserialize(result);
				
				this.EKTPOprUser = (String) resultMap.get("EKTPUser");
				this.EKTPOprPassword = (String) resultMap.get("EKTPPassword");
			}
			catch (Exception ex) {
				return ERROR;
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
	public String doInput() {
		this.action = INPUT;
		
		try {
			Map<String, String> requestMap = new HashMap<String, String>();
			requestMap.put("methodName", "listOfIP");
			requestMap.put("idUser", this.session.get("idUser").toString());
			requestMap.put("token", bdsm.util.BdsmUtil.generateToken(this.getTokenKey(), this.getTzToken()));
	
			String result = bdsm.util.HttpUtil.request(this.getBdsmHost() + "EKTPReader_callMethod.action", requestMap);
			Map<String, Object> resultMap = (Map<String, Object>) JSONUtil.deserialize(result);
			
			List<List<String>> list = (List<List<String>>) resultMap.get("modelList");
			this.baseResourceURI = (String) resultMap.get("baseResourceURI");
			this.baseProtocols = (String) resultMap.get("baseProtocols");
			
			if ((list == null) || (list.size() == 0))
				throw new Exception("restricted");
			
			this.mapIP = new LinkedHashMap<String, String>(0);
			for (List<String> l : list)
				this.mapIP.put(l.get(0), l.get(1));
		}
		catch (Exception ex) {
			if (ex.getMessage().equals("restricted"))
				return "restricted";
			
			return ERROR;
		}
		 		
		return super.doInput();
	}

    /**
     * 
     * @return
     */
    @Override
	public String doAdd() {
		// Add reading log
		try {
			StringTokenizer st = new StringTokenizer(this.log, "^~");
			String[] value = new String[st.countTokens()];
			int i = 0;
			
			while (st.hasMoreTokens())
				value[i++] = st.nextToken();
			
			Map<String, String> requestMap = new HashMap<String, String>();
			requestMap.put("methodName", "saveLog");
			requestMap.put("idUser", this.session.get("idUser").toString());
			requestMap.put("dataLog.codCCBranch", this.session.get("cdBranch").toString());
			requestMap.put("dataLog.deviceName", value[0]);
			requestMap.put("dataLog.deviceIP", value[1]);
			requestMap.put("dataLog.requestType", value[2]);
			requestMap.put("dataLog.data", value[3]);
			requestMap.put("useTransaction", "true");
			requestMap.put("token", bdsm.util.BdsmUtil.generateToken(this.getTokenKey(), this.getTzToken()));
	
			bdsm.util.HttpUtil.request(this.getBdsmHost() + "EKTPReader_callMethod", requestMap);
		}
		catch (Exception ex) {
			return ERROR;
		}
		
		return "blank";
	}

    /**
     * 
     * @return
     */
    @Override
	public String doEdit() {
		throw new UnsupportedOperationException("Not yet implemented!!!");
	}

    /**
     * 
     * @return
     */
    @Override
	public String doDelete() {
		throw new UnsupportedOperationException("Not yet implemented!!!");
	}
	
	
	
    /**
     * 
     * @return
     */
    public String getEKTPOprUser() {
		return (this.action.equals("OPR")? (String) this.EKTPOprUser: null);
	}
	
    /**
     * 
     * @return
     */
    public String getEKTPOprPassword() {
		return (this.action.equals("OPR")? (String) this.EKTPOprPassword: null);
	}
	
    /**
     * 
     * @return
     */
    public String getEKTPSpvUser() {
		return (this.action.equals("SPV")? (String) this.session.get("EKTPSpvUser"): null);
	}
	
    /**
     * 
     * @return
     */
    public String getEKTPSpvPassword() {
		return (this.action.equals("SPV")? (String) this.session.get("EKTPSpvPassword"): null);
	}
	
	
    /**
     * 
     * @return
     */
    public String getBaseResourceURI() {
		return this.baseResourceURI;
	}

    /**
     * 
     * @return
     */
    public String getBaseProtocols() {
		return this.baseProtocols;
	}
	
    /**
     * 
     * @return
     */
    public Map<String, String> getMapIP() {
		return (this.action.equals(INPUT)? this.mapIP: null);
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
     * @param deviceIP
     */
    public void setDeviceIP(String deviceIP) {
		this.deviceIP = deviceIP;
	}
	
    /**
     * 
     * @param log
     */
    public void setLog(String log) {
		this.log = log;
	}

}
