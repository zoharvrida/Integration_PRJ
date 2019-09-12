package bdsm.scheduler.model;

import java.math.BigDecimal;


public class TmpVADetail extends bdsm.model.BaseModel {
	private TmpVAPK id;
	private String transactionCode;
	private String applicationCode;
	private String accountNo;
	private BigDecimal transactionAmount;
	private String transactionCurrency;
	private BigDecimal localCurrencyAmountOfTransaction;
	private BigDecimal foreignExchangeRate;
	private String customerCenter;
	private String referenceNo;
	private String description1;
	private String description2;
	private BigDecimal amountFee;
	private String status;
	private String rejectCode;
	private String shortName;
	private String virtualAccountNo;
	private String accountNoToTransfer;
	private String flagTransaction;
	private String reason;
	private Integer rowNoHeader;
	private Integer lengthInFile;
	private Long BDGWRecordId;
	
	
	public TmpVADetail() {}
	
	public TmpVADetail(String batchNo, int rowNo) {
		this.setId(new TmpVAPK(batchNo, rowNo));
	}
	
	
	public TmpVAPK getId() {
		return id;
	}
	public void setId(TmpVAPK id) {
		this.id = id;
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
	
	public BigDecimal getTransactionAmount() {
		return transactionAmount;
	}
	public void setTransactionAmount(BigDecimal transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	
	public String getTransactionCurrency() {
		return transactionCurrency;
	}
	public void setTransactionCurrency(String transactionCurrency) {
		this.transactionCurrency = transactionCurrency;
	}
	
	public BigDecimal getLocalCurrencyAmountOfTransaction() {
		return localCurrencyAmountOfTransaction;
	}
	public void setLocalCurrencyAmountOfTransaction(BigDecimal localCurrencyAmountOfTransaction) {
		this.localCurrencyAmountOfTransaction = localCurrencyAmountOfTransaction;
	}
	
	public BigDecimal getForeignExchangeRate() {
		return foreignExchangeRate;
	}
	public void setForeignExchangeRate(BigDecimal foreignExchangeRate) {
		this.foreignExchangeRate = foreignExchangeRate;
	}
	
	public String getCustomerCenter() {
		return customerCenter;
	}
	public void setCustomerCenter(String customerCenter) {
		this.customerCenter = customerCenter;
	}
	
	public String getReferenceNo() {
		return referenceNo;
	}
	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
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
	
	public BigDecimal getAmountFee() {
		return amountFee;
	}
	public void setAmountFee(BigDecimal amountFee) {
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
	
	public String getVirtualAccountNo() {
		return virtualAccountNo;
	}
	public void setVirtualAccountNo(String virtualAccountNo) {
		this.virtualAccountNo = virtualAccountNo;
	}
	
	public String getAccountNoToTransfer() {
		return accountNoToTransfer;
	}
	public void setAccountNoToTransfer(String accountNoToTransfer) {
		this.accountNoToTransfer = accountNoToTransfer;
	}
	
	public String getFlagTransaction() {
		return flagTransaction;
	}
	public void setFlagTransaction(String flagTransaction) {
		this.flagTransaction = flagTransaction;
	}
	
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public Integer getRowNoHeader() {
		return rowNoHeader;
	}
	public void setRowNoHeader(Integer rowNoHeader) {
		this.rowNoHeader = rowNoHeader;
	}
	
	public Integer getLengthInFile() {
		return lengthInFile;
	}
	public void setLengthInFile(Integer lengthInFile) {
		this.lengthInFile = lengthInFile;
	}
	
	public Long getBDGWRecordId() {
		return BDGWRecordId;
	}
	public void setBDGWRecordId(Long BDGWRecordId) {
		this.BDGWRecordId = BDGWRecordId;
	}
	
	
	public String getBatchNo() {
		return this.getId().getBatchNo();
	}
	
	public int getRowNo() {
		return this.getId().getRowNo();
	}
	
	
	/* Hibernate to Database properties */
	
	protected Character getStatusDB() {
		return (this.status == null)? null: this.status.charAt(0);
	}
	protected void setStatusDB(Character status) {
		this.status = (status == null)? null: status.toString();
	}
	
	protected Character getFlagTransactionDB() {
		return (this.flagTransaction == null)? null: this.flagTransaction.charAt(0);
	}
	protected void setFlagTransactionDB(Character flagTransaction) {
		this.flagTransaction = (flagTransaction == null)? null: flagTransaction.toString();
	}
	
}
