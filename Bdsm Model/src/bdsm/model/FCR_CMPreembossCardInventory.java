package bdsm.model;

public class FCR_CMPreembossCardInventory extends BaseModel {
	private Integer homeBranch;
	private Integer productCode;
	private String cardNo;
	private String flagStatus;
	private java.util.Date uploadDate;
	private java.util.Date expiryDate;
	private Integer entityVPD;
	private String errorComments;
	
	
	public Integer getHomeBranch() {
		return this.homeBranch;
	}
	public void setHomeBranch(Integer homeBranch) {
		this.homeBranch = homeBranch;
	}
	
	public Integer getProductCode() {
		return this.productCode;
	}
	public void setProductCode(Integer productCode) {
		this.productCode = productCode;
	}
	
	public String getCardNo() {
		return this.cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	
	public String getFlagStatus() {
		return this.flagStatus;
	}
	public void setFlagStatus(String flagStatus) {
		this.flagStatus = flagStatus;
	}
	
	public java.util.Date getUploadDate() {
		return this.uploadDate;
	}
	public void setUploadDate(java.util.Date uploadDate) {
		this.uploadDate = uploadDate;
	}
	
	public java.util.Date getExpiryDate() {
		return this.expiryDate;
	}
	public void setExpiryDate(java.util.Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	
	public Integer getEntityVPD() {
		return entityVPD;
	}
	public void setEntityVPD(Integer entityVPD) {
		this.entityVPD = entityVPD;
	}
	
	public String getErrorComments() {
		return this.errorComments;
	}
	public void setErrorComments(String errorComments) {
		this.errorComments = errorComments;
	}
	
	
	/* Hibernate to Database properties */
	
	protected Character getFlagStatusDB() {
		return ((this.flagStatus == null)? null: this.flagStatus.charAt(0));
	}
	protected void setFlagStatusDB(Character flagStatusDB) {
		this.flagStatus = (flagStatus == null)? null: flagStatusDB.toString();
	}
	
}
