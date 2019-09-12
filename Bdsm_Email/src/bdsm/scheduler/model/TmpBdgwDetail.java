package bdsm.scheduler.model;

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
	
	
	public TmpBdgwDetail() {
		this.setRecId(1);
		this.setRecordType("01");
		this.setRecordName("Detail");
	}
	
	
	public String getTransactionCode() {
		return transactionCode;
	}
	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
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
	
	public String getTransactionAmount() {
		return transactionAmount;
	}
	public void setTransactionAmount(String transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	
	public String getTransactionCurrency() {
		return transactionCurrency;
	}
	public void setTransactionCurrency(String transactionCurrency) {
		this.transactionCurrency = transactionCurrency;
	}
	
	public String getLocalCurrencyAmountOfTransaction() {
		return localCurrencyAmountOfTransaction;
	}
	public void setLocalCurrencyAmountOfTransaction(
			String localCurrencyAmountOfTransaction) {
		this.localCurrencyAmountOfTransaction = localCurrencyAmountOfTransaction;
	}
	
	public String getForeignExchangeRate() {
		return foreignExchangeRate;
	}
	public void setForeignExchangeRate(String foreignExchangeRate) {
		this.foreignExchangeRate = foreignExchangeRate;
	}
	
	public String getCustomerCenter() {
		return customerCenter;
	}
	public void setCustomerCenter(String customerCenter) {
		this.customerCenter = customerCenter;
	}
	
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
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
	
	public String getAmountFee() {
		return amountFee;
	}
	public void setAmountFee(String amountFee) {
		this.amountFee = amountFee;
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
	
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	
}
