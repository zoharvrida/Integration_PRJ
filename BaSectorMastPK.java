package bdsm.fcr.model;


/**
 * @author v00019372
 */
@SuppressWarnings("serial")
public class BaSectorMastPK implements java.io.Serializable {
	private Integer codClgSector;
	private String flgMntStatus;
	private Integer codEntityVPD;
	
	
	public BaSectorMastPK() {}
	
	public BaSectorMastPK(Integer codClgSector, String flgMntStatus, Integer codEntityVPD) {
		this.codClgSector = codClgSector;
		this.flgMntStatus = flgMntStatus;
		this.codEntityVPD = codEntityVPD;
	}


	public Integer getCodClgSector() {
		return this.codClgSector;
	}
	public void setCodClgSector(Integer codClgSector) {
		this.codClgSector = codClgSector;
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
			.append("codClgSector=").append(this.codClgSector)
			.append(",flgMntStatus=").append(this.flgMntStatus)
			.append(",codEntityVPD=").append(this.codEntityVPD)
			.append("}");
	
		return sb.toString();
	}
}
