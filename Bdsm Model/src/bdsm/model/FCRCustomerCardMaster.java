package bdsm.model;


public class FCRCustomerCardMaster extends BaseModel {
	private String cardNo;
	private String maintenanceStatus;
	private Integer entityVPD;
	private String customerId;
	private String customerNameEmbossed;
	private java.util.Date issueDate;
	private java.util.Date expiryDate;
	
	public FCRCustomerCardMaster() {}
	
	public FCRCustomerCardMaster(String cardNo, String maintenanceStatus, Integer entityVPD) {
		this.cardNo = cardNo;
		this.maintenanceStatus = maintenanceStatus;
		this.entityVPD = entityVPD;
	}
	
	
	public String getCardNo() {
		return this.cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	
	public String getMaintenanceStatus() {
		return this.maintenanceStatus;
	}
	public void setMaintenanceStatus(String maintenanceStatus) {
		this.maintenanceStatus = maintenanceStatus;
	}
	
	public Integer getEntityVPD() {
		return this.entityVPD;
	}
	public void setEntityVPD(Integer entityVPD) {
		this.entityVPD = entityVPD;
	}
	
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
	public String getCustomerNameEmbossed() {
		return this.customerNameEmbossed;
	}
	public void setCustomerNameEmbossed(String customerNameEmbossed) {
		this.customerNameEmbossed = customerNameEmbossed;
	}
	
	public java.util.Date getIssueDate() {
		return this.issueDate;
	}
	public void setIssueDate(java.util.Date issueDate) {
		this.issueDate = issueDate;
	}
	
	public java.util.Date getExpiryDate() {
		return this.expiryDate;
	}
	public void setExpiryDate(java.util.Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	
	
	/* Hibernate to Database properties */
	
	protected Character getMaintenanceStatusDB() {
		return ((this.maintenanceStatus == null)? null: this.maintenanceStatus.charAt(0));
	}
	protected void setMaintenanceStatusDB(Character maintenanceStatusDB) {
		this.maintenanceStatus = (maintenanceStatusDB == null)? null: maintenanceStatusDB.toString();
	}
	
}
