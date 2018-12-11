package bdsm.scheduler.model;

/**
 * 
 * @author bdsm
 */
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
	
	
    /**
     * 
     */
    public TmpBdgwHeader() {
		this.setRecId(0);
		this.setRecordType("00");
		this.setRecordName("Header");
		this.setParentRecordId(0L);
	}
	
	
    /**
     * 
     * @return
     */
    public String getProfileName() {
		return profileName;
	}
    /**
     * 
     * @param profileName
     */
    public void setProfileName(String profileName) {
		this.profileName = profileName;
	}
	
    /**
     * 
     * @return
     */
    public String getBusinessDate() {
		return businessDate;
	}
    /**
     * 
     * @param businessDate
     */
    public void setBusinessDate(String businessDate) {
		this.businessDate = businessDate;
	}
	
    /**
     * 
     * @return
     */
    public String getInterfaceType() {
		return interfaceType;
	}
    /**
     * 
     * @param interfaceType
     */
    public void setInterfaceType(String interfaceType) {
		this.interfaceType = interfaceType;
	}
	
    /**
     * 
     * @return
     */
    public String getGLNo() {
		return GLNo;
	}
    /**
     * 
     * @param GLNo
     */
    public void setGLNo(String GLNo) {
		this.GLNo = GLNo;
	}
	
    /**
     * 
     * @return
     */
    public String getCustomerCenter() {
		return customerCenter;
	}
    /**
     * 
     * @param customerCenter
     */
    public void setCustomerCenter(String customerCenter) {
		this.customerCenter = customerCenter;
	}
	
    /**
     * 
     * @return
     */
    public String getApplicationCode() {
		return applicationCode;
	}
    /**
     * 
     * @param applicationCode
     */
    public void setApplicationCode(String applicationCode) {
		this.applicationCode = applicationCode;
	}
	
    /**
     * 
     * @return
     */
    public String getAccountNo() {
		return accountNo;
	}
    /**
     * 
     * @param accountNo
     */
    public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	
    /**
     * 
     * @return
     */
    public String getTransactionCurrency() {
		return transactionCurrency;
	}
    /**
     * 
     * @param transactionCurrency
     */
    public void setTransactionCurrency(String transactionCurrency) {
		this.transactionCurrency = transactionCurrency;
	}
	
    /**
     * 
     * @return
     */
    public String getAmount() {
		return amount;
	}
    /**
     * 
     * @param amount
     */
    public void setAmount(String amount) {
		this.amount = amount;
	}
	
    /**
     * 
     * @return
     */
    public String getLocalCurrencyAmountOfTransaction() {
		return localCurrencyAmountOfTransaction;
	}
    /**
     * 
     * @param localCurrencyAmountOfTransaction
     */
    public void setLocalCurrencyAmountOfTransaction(
			String localCurrencyAmountOfTransaction) {
		this.localCurrencyAmountOfTransaction = localCurrencyAmountOfTransaction;
	}
	
    /**
     * 
     * @return
     */
    public String getAmountFee() {
		return amountFee;
	}
    /**
     * 
     * @param amountFee
     */
    public void setAmountFee(String amountFee) {
		this.amountFee = amountFee;
	}
	
    /**
     * 
     * @return
     */
    public String getDescription1() {
		return description1;
	}
    /**
     * 
     * @param description1
     */
    public void setDescription1(String description1) {
		this.description1 = description1;
	}
	
    /**
     * 
     * @return
     */
    public String getDescription2() {
		return description2;
	}
    /**
     * 
     * @param description2
     */
    public void setDescription2(String description2) {
		this.description2 = description2;
	}
	
    /**
     * 
     * @return
     */
    public String getStatus() {
		return status;
	}
    /**
     * 
     * @param status
     */
    public void setStatus(String status) {
		this.status = status;
	}
	
    /**
     * 
     * @return
     */
    public String getRejectCode() {
		return rejectCode;
	}
    /**
     * 
     * @param rejectCode
     */
    public void setRejectCode(String rejectCode) {
		this.rejectCode = rejectCode;
	}
	
    /**
     * 
     * @return
     */
    public String getFeeProcessingIndicator() {
		return feeProcessingIndicator;
	}
    /**
     * 
     * @param feeProcessingIndicator
     */
    public void setFeeProcessingIndicator(String feeProcessingIndicator) {
		this.feeProcessingIndicator = feeProcessingIndicator;
	}
	
    /**
     * 
     * @return
     */
    public String getDescription3() {
		return description3;
	}
    /**
     * 
     * @param description3
     */
    public void setDescription3(String description3) {
		this.description3 = description3;
	}
	
	
	/* Hibernate to Database properties */
	
    /**
     * 
     * @return
     */
    protected Character getFeeProcessingIndicatorDB() {
		return (this.feeProcessingIndicator == null)? null: this.feeProcessingIndicator.charAt(0);
	}
    /**
     * 
     * @param feeProcessingIndicator
     */
    protected void setFeeProcessingIndicatorDB(Character feeProcessingIndicator) {
		this.feeProcessingIndicator = (feeProcessingIndicator == null)? null: feeProcessingIndicator.toString();
	}
	
}
