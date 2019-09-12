package bdsm.model;


/**
 * @author v00019372
 */
@SuppressWarnings("serial")
public class EKTPReaderDevice extends BaseModel {
	private Integer codeCCBranch;
	private String IP;
	private String name;
	private String SAUsername;
	private String SAPassword;
	private String protocol;
	
	
	public Integer getCodeCCBranch() {
		return this.codeCCBranch;
	}
	public void setCodeCCBranch(Integer codeCCBranch) {
		this.codeCCBranch = codeCCBranch;
	}
	
	public String getIP() {
		return this.IP;
	}
	public void setIP(String IP) {
		this.IP = IP;
	}
	
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSAUsername() {
		return this.SAUsername;
	}
	public void setSAUsername(String SAUsername) {
		this.SAUsername = SAUsername;
	}
	
	public String getSAPassword() {
		return this.SAPassword;
	}
	public void setSAPassword(String SAPassword) {
		this.SAPassword = SAPassword;
	}

	public String getProtocol() {
		return this.protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	
	
	@Override
	public String toString() {
    	StringBuilder sb = new StringBuilder(this.getClass().getSimpleName())
			.append("{")
			.append("codeCCBranch=").append(this.codeCCBranch)
			.append(",IP=").append(this.IP)
			.append(",name=").append(this.name)
			.append(",SAUsername=").append(this.SAUsername)
			.append(",protocol=").append(this.protocol)
			.append("}");
    	
		return sb.toString();
	}
	
}
