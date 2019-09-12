package bdsm.scheduler.model;

import java.math.BigDecimal;
import java.util.Date;

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
	
	
	public TmpVAHeader() {}
	
	public TmpVAHeader(String batchNo, int rowNo) {
		this.setId(new TmpVAPK(batchNo, rowNo));
	}
	
	
	public TmpVAPK getId() {
		return id;
	}
	public void setId(TmpVAPK id) {
		this.id = id;
	}
	
	public String getProfileName() {
		return profileName;
	}
	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}
	
	public Date getBusinessDate() {
		return businessDate;
	}
	public void setBusinessDate(Date businessDate) {
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
	
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public BigDecimal getLocalCurrencyAmountOfTransaction() {
		return localCurrencyAmountOfTransaction;
	}
	public void setLocalCurrencyAmountOfTransaction(BigDecimal localCurrencyAmountOfTransaction) {
		this.localCurrencyAmountOfTransaction = localCurrencyAmountOfTransaction;
	}
	
	public BigDecimal getAmountFee() {
		return amountFee;
	}
	public void setAmountFee(BigDecimal amountFee) {
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
	
	public String getReferenceNo() {
		return referenceNo;
	}
	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}
	
	public Character getX() {
		return x;
	}
	public void setX(Character x) {
		this.x = x;
	}
	
	public Date getDtmProcessStart() {
		return dtmProcessStart;
	}
	public void setDtmProcessStart(Date dtmProcessStart) {
		this.dtmProcessStart = dtmProcessStart;
	}
	
	public Date getDtmProcessEnd() {
		return dtmProcessEnd;
	}
	public void setDtmProcessEnd(Date dtmProcessEnd) {
		this.dtmProcessEnd = dtmProcessEnd;
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
	
	public String getChecksum() {
		return this.checksum;
	}
	
	public void setChecksum(String checksum) {
		this.checksum = checksum;
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

}
