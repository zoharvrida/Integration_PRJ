package bdsm.scheduler.model;

import java.math.BigDecimal;


/**
 * 
 * @author bdsm
 */
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
	
	
    /**
     * 
     */
    public TmpVADetail() {}
	
    /**
     * 
     * @param batchNo
     * @param rowNo
     */
    public TmpVADetail(String batchNo, int rowNo) {
		this.setId(new TmpVAPK(batchNo, rowNo));
	}
	
	
    /**
     * 
     * @return
     */
    public TmpVAPK getId() {
		return id;
	}
    /**
     * 
     * @param id
     */
    public void setId(TmpVAPK id) {
		this.id = id;
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
    public BigDecimal getTransactionAmount() {
		return transactionAmount;
	}
    /**
     * 
     * @param transactionAmount
     */
    public void setTransactionAmount(BigDecimal transactionAmount) {
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
    public BigDecimal getLocalCurrencyAmountOfTransaction() {
		return localCurrencyAmountOfTransaction;
	}
    /**
     * 
     * @param localCurrencyAmountOfTransaction
     */
    public void setLocalCurrencyAmountOfTransaction(BigDecimal localCurrencyAmountOfTransaction) {
		this.localCurrencyAmountOfTransaction = localCurrencyAmountOfTransaction;
	}
	
    /**
     * 
     * @return
     */
    public BigDecimal getForeignExchangeRate() {
		return foreignExchangeRate;
	}
    /**
     * 
     * @param foreignExchangeRate
     */
    public void setForeignExchangeRate(BigDecimal foreignExchangeRate) {
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
    public String getReferenceNo() {
		return referenceNo;
	}
    /**
     * 
     * @param referenceNo
     */
    public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
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
    public BigDecimal getAmountFee() {
		return amountFee;
	}
    /**
     * 
     * @param amountFee
     */
    public void setAmountFee(BigDecimal amountFee) {
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
	
    /**
     * 
     * @return
     */
    public String getVirtualAccountNo() {
		return virtualAccountNo;
	}
    /**
     * 
     * @param virtualAccountNo
     */
    public void setVirtualAccountNo(String virtualAccountNo) {
		this.virtualAccountNo = virtualAccountNo;
	}
	
    /**
     * 
     * @return
     */
    public String getAccountNoToTransfer() {
		return accountNoToTransfer;
	}
    /**
     * 
     * @param accountNoToTransfer
     */
    public void setAccountNoToTransfer(String accountNoToTransfer) {
		this.accountNoToTransfer = accountNoToTransfer;
	}
	
    /**
     * 
     * @return
     */
    public String getFlagTransaction() {
		return flagTransaction;
	}
    /**
     * 
     * @param flagTransaction
     */
    public void setFlagTransaction(String flagTransaction) {
		this.flagTransaction = flagTransaction;
	}
	
    /**
     * 
     * @return
     */
    public String getReason() {
		return reason;
	}
    /**
     * 
     * @param reason
     */
    public void setReason(String reason) {
		this.reason = reason;
	}
	
    /**
     * 
     * @return
     */
    public Integer getRowNoHeader() {
		return rowNoHeader;
	}
    /**
     * 
     * @param rowNoHeader
     */
    public void setRowNoHeader(Integer rowNoHeader) {
		this.rowNoHeader = rowNoHeader;
	}
	
    /**
     * 
     * @return
     */
    public Integer getLengthInFile() {
		return lengthInFile;
	}
    /**
     * 
     * @param lengthInFile
     */
    public void setLengthInFile(Integer lengthInFile) {
		this.lengthInFile = lengthInFile;
	}
	
    /**
     * 
     * @return
     */
    public Long getBDGWRecordId() {
		return BDGWRecordId;
	}
    /**
     * 
     * @param BDGWRecordId
     */
    public void setBDGWRecordId(Long BDGWRecordId) {
		this.BDGWRecordId = BDGWRecordId;
	}
	
	
    /**
     * 
     * @return
     */
    public String getBatchNo() {
		return this.getId().getBatchNo();
	}
	
    /**
     * 
     * @return
     */
    public int getRowNo() {
		return this.getId().getRowNo();
	}
	
	
	/* Hibernate to Database properties */
	
    /**
     * 
     * @return
     */
    protected Character getStatusDB() {
		return (this.status == null)? null: this.status.charAt(0);
	}
    /**
     * 
     * @param status
     */
    protected void setStatusDB(Character status) {
		this.status = (status == null)? null: status.toString();
	}
	
    /**
     * 
     * @return
     */
    protected Character getFlagTransactionDB() {
		return (this.flagTransaction == null)? null: this.flagTransaction.charAt(0);
	}
    /**
     * 
     * @param flagTransaction
     */
    protected void setFlagTransactionDB(Character flagTransaction) {
		this.flagTransaction = (flagTransaction == null)? null: flagTransaction.toString();
	}
	
}
