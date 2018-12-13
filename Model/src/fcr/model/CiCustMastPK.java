package bdsm.fcr.model;


/**
 * 
 * @author v00020841
 */
@SuppressWarnings("serial")
public class CiCustMastPK implements java.io.Serializable {
	private Integer codCustId;
	private String flgMntStatus;
	private Integer codEntityVpd;
	
	
	public CiCustMastPK() {}
	
	public CiCustMastPK(Integer codCustId, String flgMntStatus, Integer codEntityVpd) {
		this.codCustId = codCustId;
		this.flgMntStatus = flgMntStatus;
		this.codEntityVpd = codEntityVpd;
	}
	
	
	public Integer getCodCustId() {
		return this.codCustId;
	}
	public void setCodCustId(Integer codCustId) {
		this.codCustId = codCustId;
	}
	
	public String getFlgMntStatus() {
		return this.flgMntStatus;
	}
	public void setFlgMntStatus(String flgMntStatus) {
		this.flgMntStatus = flgMntStatus;
	}
	
	public Integer getCodEntityVpd() {
		return this.codEntityVpd;
	}
	public void setCodEntityVpd(Integer codEntityVpd) {
		this.codEntityVpd = codEntityVpd;
	}
	
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer(this.getClass().getSimpleName())
			.append("{")
			.append("codCustId=").append(this.codCustId)
			.append(",flgMntStatus=").append(this.flgMntStatus)
			.append(",codEntityVpd=").append(this.codEntityVpd)
			.append("}");
		
		return sb.toString();
	}
	
}
