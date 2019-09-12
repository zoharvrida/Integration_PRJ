package bdsm.fcr.model;


/**
 * @author v00019372
 */
@SuppressWarnings("serial")
public class PmFinInstDirMastPK implements java.io.Serializable {
	private String codFinInstId;
	private String flgMntStatus;
	private Integer codEntityVPD;
	
	
	public PmFinInstDirMastPK() {}
	
	public PmFinInstDirMastPK(String codFinInstId, String flgMntStatus, Integer codEntityVPD) {
		this.codFinInstId = codFinInstId;
		this.flgMntStatus = flgMntStatus;
		this.codEntityVPD = codEntityVPD;
	}


	public String getCodFinInstId() {
		return this.codFinInstId;
	}
	public void setCodFinInstId(String codFinInstId) {
		this.codFinInstId = codFinInstId;
	}

	public String getFlgMntStatus() {
		return this.flgMntStatus;
	}
	public void setFlgMntStatus(String flgMntStatus) {
		this.flgMntStatus = flgMntStatus;
	}
	
	public Integer getCodEntityVPD() {
		return this.codEntityVPD;
	}
	public void setCodEntityVPD(Integer codEntityVPD) {
		this.codEntityVPD = codEntityVPD;
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(this.getClass().getSimpleName())
			.append("{")
			.append("codFinInstId=").append(this.codFinInstId)
			.append(",flgMntStatus=").append(this.flgMntStatus)
			.append(",codEntityVPD=").append(this.codEntityVPD)
			.append("}");
	
		return sb.toString();
	}
	
}
