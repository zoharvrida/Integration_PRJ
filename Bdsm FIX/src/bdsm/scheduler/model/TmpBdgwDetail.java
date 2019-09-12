package bdsm.scheduler.model;

/**
 * 
 * @author bdsm
 */
public class TmpBdgwDetail extends TmpBdgwParent implements java.io.Serializable {
	private String transactionCode;
	private String applicationCode;
	private String accountNo;
	private String transactionAmount;
	private String transactionCurrency;
	private String localCurrencyAmountOfTransaction;
	private String foreignExchangeRate;
	private String customerCenter;
	private String reference;
	private String description1;
	private String description2;
	private String amountFee;
	private String status;
	private String rejectCode;
	private String shortName;
	
	
    /**
     * 
     */
    public TmpBdgwDetail() {
		this.setRecId(1);
		this.setRecordType("01");
		this.setRecordName("Detail");
	}
	
	
    /**
     * 
     * @return
     */
    public String getTransactionCode() {
		return transactionCode;
	}
    /**
     * 
     * @param transactionCode
     */
    public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
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
    public String getTransactionAmount() {
		return transactionAmount;
	}
    /**
     * 
     * @param transactionAmount
     */
    public void setTransactionAmount(String transactionAmount) {
		this.transactionAmount = transactionAmount;
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
    public String getForeignExchangeRate() {
		return foreignExchangeRate;
	}
    /**
     * 
     * @param foreignExchangeRate
     */
    public void setForeignExchangeRate(String foreignExchangeRate) {
		this.foreignExchangeRate = foreignExchangeRate;
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
    public String getReference() {
		return reference;
	}
    /**
     * 
     * @param reference
     */
    public void setReference(String reference) {
		this.reference = reference;
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
    public String getShortName() {
		return shortName;
	}
    /**
     * 
     * @param shortName
     */
    public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	
}
