package bdsm.scheduler.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * @author bdsm
 */
public class TmpVAHeader extends bdsm.model.BaseModel implements java.io.Serializable {
	private TmpVAPK id;
	private String profileName;
	private Date businessDate;
	private String interfaceType;
	private String GLNo;
	private String customerCenter;
	private String applicationCode;
	private String accountNo;
	private String transactionCurrency;
	private BigDecimal amount;
	private BigDecimal localCurrencyAmountOfTransaction;
	private BigDecimal amountFee;
	private String description1;
	private String description2;
	private String status;
	private String rejectCode;
	private String referenceNo;
	private Character x;	
	private Date dtmProcessStart;
	private Date dtmProcessEnd;
	private Integer lengthInFile;
	private Long BDGWRecordId;
	private String checksum;
	
	
    /**
     * 
     */
    public TmpVAHeader() {}
	
    /**
     * 
     * @param batchNo
     * @param rowNo
     */
    public TmpVAHeader(String batchNo, int rowNo) {
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
    public Date getBusinessDate() {
		return businessDate;
	}
    /**
     * 
     * @param businessDate
     */
    public void setBusinessDate(Date businessDate) {
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
    public BigDecimal getAmount() {
		return amount;
	}
    /**
     * 
     * @param amount
     */
    public void setAmount(BigDecimal amount) {
		this.amount = amount;
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
    public Character getX() {
		return x;
	}
    /**
     * 
     * @param x
     */
    public void setX(Character x) {
		this.x = x;
	}
	
    /**
     * 
     * @return
     */
    public Date getDtmProcessStart() {
		return dtmProcessStart;
	}
    /**
     * 
     * @param dtmProcessStart
     */
    public void setDtmProcessStart(Date dtmProcessStart) {
		this.dtmProcessStart = dtmProcessStart;
	}
	
    /**
     * 
     * @return
     */
    public Date getDtmProcessEnd() {
		return dtmProcessEnd;
	}
    /**
     * 
     * @param dtmProcessEnd
     */
    public void setDtmProcessEnd(Date dtmProcessEnd) {
		this.dtmProcessEnd = dtmProcessEnd;
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
    public String getChecksum() {
		return this.checksum;
	}
	
    /**
     * 
     * @param checksum
     */
    public void setChecksum(String checksum) {
		this.checksum = checksum;
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

}
