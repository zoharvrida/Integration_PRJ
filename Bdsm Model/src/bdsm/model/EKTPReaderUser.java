package bdsm.model;


/**
 * @author v00019372
 */
@SuppressWarnings("serial")
public class EKTPReaderUser extends BaseModel {
	private String idUser;
	private Integer codeCCBranch;
	private String deviceName;
	private String role;
	private String password;
	
	
	public EKTPReaderUser() {}
	
	public EKTPReaderUser(String idUser, Integer codeCCBranch, String deviceName) {
		this.idUser = idUser;
		this.codeCCBranch = codeCCBranch;
		this.deviceName = deviceName;
	}
	
	
	public String getIdUser() {
		return this.idUser;
	}
	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}
	
	public Integer getCodeCCBranch() {
		return this.codeCCBranch;
	}
	public void setCodeCCBranch(Integer codeCCBranch) {
		this.codeCCBranch = codeCCBranch;
	}
	
	public String getDeviceName() {
		return this.deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	
	public String getRole() {
		return this.role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
	public String getPassword() {
		return this.password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	@Override
	public String toString() {
    	StringBuilder sb = new StringBuilder(this.getClass().getSimpleName())
			.append("{")
			.append("idUser=").append(this.idUser)
			.append(",codeCCBranch=").append(this.codeCCBranch)
			.append(",deviceName=").append(this.deviceName)
			.append(",role=").append(this.role)
			.append("}");
    	
		return sb.toString();
	}
	
}
