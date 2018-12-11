package bdsm.fcr.model;


/**
 * @author v00020800
 */
@SuppressWarnings("serial")
public class BDSMBICloseRatePK implements java.io.Serializable {
	private String ccyNamShrt;
	private java.util.Date BICloseDate;


	public String getCcyNamShrt() {
		return this.ccyNamShrt;
	}
	public void setCcyNamShrt(String ccyNamShrt) {
		this.ccyNamShrt = ccyNamShrt;
	}

	public java.util.Date getBICloseDate() {
		return this.BICloseDate;
	}
	public void setBICloseDate(java.util.Date BICloseDate) {
		this.BICloseDate = BICloseDate;
	}
	
	
	public java.util.Date getDateEff() {
		return this.getBICloseDate();
	}
	public void setDateEff(java.util.Date dateEff) {
		this.setBICloseDate(dateEff);
	}

}