package bdsm.fcr.model;


/**
 * @author v00019372
 */
@SuppressWarnings("serial")
public class BaCityMasterPK implements java.io.Serializable {
	private String cityCode;
	private String flgMntStatus;
	private Integer codEntityVPD;
	
	
	public BaCityMasterPK() {}
	
	public BaCityMasterPK(String cityCode, String flgMntStatus, Integer codEntityVPD) {
		this.cityCode = cityCode;
		this.flgMntStatus = flgMntStatus;
		this.codEntityVPD = codEntityVPD;
	}


	public String getCityCode() {
		return this.cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
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
			.append("cityCode=").append(this.cityCode)
			.append(",flgMntStatus=").append(this.flgMntStatus)
			.append(",codEntityVPD=").append(this.codEntityVPD)
			.append("}");
	
		return sb.toString();
	}
}
