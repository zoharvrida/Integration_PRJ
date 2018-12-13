package bdsmhost.web;


import java.util.List;
import java.util.Map;

import org.apache.struts2.json.JSONUtil;
import org.apache.struts2.json.annotations.JSON;

import bdsm.model.EKTPReaderLog;
import bdsm.model.MasterUser;
import bdsm.scheduler.PropertyPersister;
import bdsmhost.dao.EKTPReaderDeviceDAO;
import bdsmhost.dao.EKTPReaderUserDAO;
import bdsmhost.dao.MasterUserDao;


/**
 * @author v00019372
 */
@SuppressWarnings("serial")
public class EKTPReaderAction extends BaseHostAction {
	private String idUser;
	private String deviceIP;
	private String baseResourceURI;
	private String baseProtocols;
	private String EKTPUser = "";
	private String EKTPPassword = "";
	private List<Map<String, String>> modelList;
	private Map<String, String> dataLog = new java.util.HashMap<String, String>(0); 
	
	
    /**
     * 
     * @return
     */
    public String listOfIP() {
		MasterUserDao userDAO = new MasterUserDao(this.getHSession());
		MasterUser user = userDAO.get(this.idUser);
		
		if (user != null) {
			try {
				EKTPReaderDeviceDAO dao = new EKTPReaderDeviceDAO(this.getHSession());
				this.modelList = dao.listDevicesByUserId(this.idUser);
				this.baseResourceURI = PropertyPersister.EKTP_READER_BASE_RESOURCE_URI;
				this.baseProtocols = JSONUtil.serialize(PropertyPersister.EKTP_READER_BASE_PROTOCOLS);
			}
			catch (Exception ex) {
				this.getLogger().error(ex, ex);
			}
		}
		
		return SUCCESS;
	}
	
    /**
     * 
     * @return
     */
    public String checkValidOperatorDevice() {
		return this.checkValidUserDevice(1);
	}
	
    /**
     * 
     * @return
     */
    public String checkValidSupervisorDevice() {
		return this.checkValidUserDevice(2);
	}
	
	private String checkValidUserDevice(int role) {
		EKTPReaderUserDAO dao = new EKTPReaderUserDAO(this.getHSession());
		String data = (role == 1)? dao.checkValidOperatorOnDevice(this.idUser, this.deviceIP): dao.checkValidSupervisorOnDevice(this.idUser, this.deviceIP);
		
		if (data != null) {
			this.EKTPUser = this.idUser;
			this.EKTPPassword = data;
		}
		
		return SUCCESS;
	}
	
	
    /**
     * 
     * @return
     */
    public String saveLog() {
		EKTPReaderUserDAO dao = new EKTPReaderUserDAO(this.getHSession());
		EKTPReaderLog log = new EKTPReaderLog();
		
		log.setIdUser(this.idUser);
		log.setCodCCBranch(Integer.valueOf(dataLog.get("codCCBranch")));
		log.setDeviceName(dataLog.get("deviceName"));
		log.setDeviceIP(dataLog.get("deviceIP"));
		log.setRequestType(Byte.valueOf(dataLog.get("requestType")));
		log.setData(new org.hibernate.lob.ClobImpl(dataLog.get("data")));
		log.setDtmLog(new java.sql.Timestamp(new java.util.Date().getTime()));
		
		dao.saveLog(log);
		return SUCCESS;
	}
	

    /**
     * 
     * @return
     */
    @Override
	public String exec() {
		throw new UnsupportedOperationException("Not yet implemented!!!");
	}

    /**
     * 
     * @return
     */
    @Override
	public String doList() {
		throw new UnsupportedOperationException("Not yet implemented!!!");
	}

    /**
     * 
     * @return
     */
    @Override
	public String doGet() {
		throw new UnsupportedOperationException("Not yet implemented!!!");
	}

    /**
     * 
     * @return
     */
    @Override
	public String doSave() {
		throw new UnsupportedOperationException("Not yet implemented!!!");
	}

    /**
     * 
     * @return
     */
    @Override
	public String doInsert() {
		throw new UnsupportedOperationException("Not yet implemented!!!");
	}

    /**
     * 
     * @return
     */
    @Override
	public String doUpdate() {
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
     * @param idUser
     */
    public void setIdUser(String idUser) {
		this.idUser = idUser;
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
    public String getEKTPUser() {
		return this.EKTPUser;
	}
	
    /**
     * 
     * @return
     */
    public String getEKTPPassword() {
		return this.EKTPPassword;
	}
	
    /**
     * 
     * @return
     */
    public List<Map<String, String>> getModelList() {
		return this.modelList;
	}
	
    /**
     * 
     * @return
     */
    @JSON(serialize=false)
	public Map<String, String> getDataLog() {
		return this.dataLog;
	}
    /**
     * 
     * @param dataLog
     */
    public void setDataLog(Map<String, String> dataLog) {
		this.dataLog = dataLog;
	}

}
