package bdsm.fcr.model;


/**
 * @author v00019372
 */
@SuppressWarnings("serial")
public class StRoutingNoDtlsPK implements java.io.Serializable {
	private Integer codRoutingNo;
	private String flgMntStatus;
	private Integer codEntityVPD;
	
	
	public StRoutingNoDtlsPK() {}
	
	public StRoutingNoDtlsPK(Integer codRoutingNo, String flgMntStatus, Integer codEntityVPD) {
		this.codRoutingNo = codRoutingNo;
		this.flgMntStatus = flgMntStatus;
		this.codEntityVPD = codEntityVPD;
	}


	public Integer getCodRoutingNo() {
		return this.codRoutingNo;
	}
	public void setCodRoutingNo(Integer codRoutingNo) {
		this.codRoutingNo = codRoutingNo;
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
			.append("codRoutingNo=").append(this.codRoutingNo)
			.append(",flgMntStatus=").append(this.flgMntStatus)
			.append(",codEntityVPD=").append(this.codEntityVPD)
			.append("}");
	
		return sb.toString();
	}

}
