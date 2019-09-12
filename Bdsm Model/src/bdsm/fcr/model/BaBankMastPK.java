package bdsm.fcr.model;


/**
*
* @author v00019372
*/
@SuppressWarnings("serial")
public class BaBankMastPK implements java.io.Serializable {
	private Integer codBank;
	private String flgMntStatus;
	private Integer codEntityVPD;
	
	
	public BaBankMastPK() {}
	
	public BaBankMastPK(Integer codBank, String flgMntStatus, Integer codEntityVPD) {
		this.codBank = codBank;
		this.flgMntStatus = flgMntStatus;
		this.codEntityVPD = codEntityVPD;
	}


	public Integer getCodBank() {
		return this.codBank;
	}
	public void setCodBank(Integer codBank) {
		this.codBank = codBank;
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
