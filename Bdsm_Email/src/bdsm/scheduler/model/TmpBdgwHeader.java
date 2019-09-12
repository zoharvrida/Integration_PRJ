package bdsm.scheduler.model;

public class TmpBdgwHeader extends TmpBdgwParent implements java.io.Serializable {
	private String profileName;
	private String businessDate;
	private String interfaceType;
	private String GLNo;
	private String customerCenter;
	private String applicationCode;
	private String accountNo;
	private String transactionCurrency;
	private String amount;
	private String localCurrencyAmountOfTransaction;
	private String amountFee;
	private String description1;
	private String description2;
	private String status;
	private String rejectCode;
	private String feeProcessingIndicator;
	private String description3;
	
	
	public TmpBdgwHeader() {
		this.setRecId(0);
		this.setRecordType("00");
		this.setRecordName("Header");
		this.setParentRecordId(0L);
	}
	
	
	public String getProfileName() {
		return profileName;
	}
	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}
	
	public String getBusinessDate() {
		return businessDate;
	}
	public void setBusinessDate(String businessDate) {
		this.businessDate = businessDate;
	}
	
	public String getInterfaceType() {
		return interfaceType;
	}
	public void setInterfaceType(String interfaceType) {
		this.interfaceType = interfaceType;
	}
	
	public String getGLNo() {
		return GLNo;
	}
	public void setGLNo(String GLNo) {
		this.GLNo = GLNo;
	}
	
	public String getCustomerCenter() {
		return customerCenter;
	}
	public void setCustomerCenter(String customerCenter) {
		this.customerCenter = customerCenter;
	}
	
	public String getApplicationCode() {
		return applicationCode;
	}
	public void setApplicationCode(String applicationCode) {
		this.applicationCode = applicationCode;
	}
	
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	
	public String getTransactionCurrency() {
		return transactionCurrency;
	}
	public void setTransactionCurrency(String transactionCurrency) {
		this.transactionCurrency = transactionCurrency;
	}
	
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	
	public String getLocalCurrencyAmountOfTransaction() {
		return localCurrencyAmountOfTransaction;
	}
	public void setLocalCurrencyAmountOfTransaction(
			String localCurrencyAmountOfTransaction) {
		this.localCurrencyAmountOfTransaction = localCurrencyAmountOfTransaction;
	}
	
	public String getAmountFee() {
		return amountFee;
	}
	public void setAmountFee(String amountFee) {
		this.amountFee = amountFee;
	}
	
	public String getDescription1() {
		return description1;
	}
	public void setDescription1(String description1) {
		this.description1 = description1;
	}
	
	public String getDescription2() {
		return description2;
	}
	public void setDescription2(String description2) {
		this.description2 = description2;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getRejectCode() {
		return rejectCode;
	}
	public void setRejectCode(String rejectCode) {
		this.rejectCode = rejectCode;
	}
	
	public String getFeeProcessingIndicator() {
		return feeProcessingIndicator;
	}
	public void setFeeProcessingIndicator(String feeProcessingIndicator) {
		this.feeProcessingIndicator = feeProcessingIndicator;
	}
	
	public String getDescription3() {
		return description3;
	}
	public void setDescription3(String description3) {
		this.description3 = description3;
	}
	
	
	/* Hibernate to Database properties */
	
	protected Character getFeeProcessingIndicatorDB() {
		return (this.feeProcessingIndicator == null)? null: this.feeProcessingIndicator.charAt(0);
	}
	protected void setFeeProcessingIndicatorDB(Character feeProcessingIndicator) {
		this.feeProcessingIndicator = (feeProcessingIndicator == null)? null: feeProcessingIndicator.toString();
	}
	
}
