package bdsm.fcr.model;


/**
 * 
 * @author v00019372
 */
@SuppressWarnings("serial")
public class BaCcyCodePK implements java.io.Serializable {
	private Integer codCcy;
	private String flgMntStatus;
	private Integer codEntityVPD;
	
	
	public BaCcyCodePK() {}
	
	public BaCcyCodePK(Integer codCcy, String flgMntStatus, Integer codEntityVPD) {
		this.codCcy = codCcy;
		this.flgMntStatus = flgMntStatus;
		this.codEntityVPD = codEntityVPD;
	}


	public Integer getCodCcy() {
		return this.codCcy;
	}
	public void setCodCcy(Integer codCcy) {
		this.codCcy = codCcy;
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
	
}
