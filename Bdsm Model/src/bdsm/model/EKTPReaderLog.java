package bdsm.model;


/**
 * @author v00019372
 */
public class EKTPReaderLog {
	private String idUser;
	private Integer codCCBranch;
	private String deviceName;
	private String deviceIP;
	private Byte requestType;
	private java.sql.Clob data;
	private java.sql.Timestamp dtmLog;
	
	
	public String getIdUser() {
		return this.idUser;
	}
	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}
	
	public Integer getCodCCBranch() {
		return this.codCCBranch;
	}
	public void setCodCCBranch(Integer codCCBranch) {
		this.codCCBranch = codCCBranch;
	}
	
	public String getDeviceName() {
		return this.deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	
	public String getDeviceIP() {
		return this.deviceIP;
	}
	public void setDeviceIP(String deviceIP) {
		this.deviceIP = deviceIP;
	}
	
	public Byte getRequestType() {
		return this.requestType;
	}
	public void setRequestType(Byte requestType) {
		this.requestType = requestType;
	}
	
	public java.sql.Clob getData() {
		return this.data;
	}
	public void setData(java.sql.Clob data) {
		this.data = data;
	}
	
	public java.sql.Timestamp getDtmLog() {
		return this.dtmLog;
	}
	public void setDtmLog(java.sql.Timestamp dtmLog) {
		this.dtmLog = dtmLog;
	}
	
	
	@Override
	public String toString() {
    	StringBuilder sb = new StringBuilder(this.getClass().getSimpleName())
			.append("{")
			.append(",idUser=").append(this.idUser)
			.append(",codCCBranch=").append(this.codCCBranch)
			.append(",deviceName=").append(this.deviceName)
			.append(",deviceIP=").append(this.deviceIP)
			.append(",requestType=").append(this.requestType)
			.append(",data=").append(this.data)
			.append(",dtmLog=").append(this.dtmLog)
			.append("}");
    	
		return sb.toString();
	}

}
