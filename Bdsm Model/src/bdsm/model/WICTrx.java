package bdsm.model;


/**
 * 
 * @author v00019372
 */
@SuppressWarnings("serial")
public class WICTrx extends BaseModel {
	private Long trxNo;
	private Integer WICNo;
	private String refNo;
	private String type;
	private java.sql.Timestamp dateTime;
	private Integer fastPath;
	private Long CIFNo;
	private String accountNo;
	private Integer currencyCode;
	private java.math.BigDecimal amount;
	private String debitOrCredit;
	private String narrative;
	private Integer branch;
	private String tellerId;
	private String requester;
	private String approver;
	private String flagStatus;
	
	
	public WICTrx() {}
	
	public WICTrx(Integer WICNo, String refNo) {
		this.WICNo = WICNo;
		this.refNo = refNo;
	}
	
	
	public Long getTrxNo() {
		return this.trxNo;
	}
	public void setTrxNo(Long trxNo) {
		this.trxNo = trxNo;
	}
	
	public Integer getWICNo() {
		return this.WICNo;
	}
	public void setWICNo(Integer wICNo) {
		WICNo = wICNo;
	}
	
	public String getRefNo() {
		return this.refNo;
	}
	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}
	
	public String getType() {
		return this.type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public java.sql.Timestamp getDateTime() {
		return this.dateTime;
	}
	public void setDateTime(java.sql.Timestamp dateTime) {
		this.dateTime = dateTime;
	}
	
	public Integer getFastPath() {
		return this.fastPath;
	}
	public void setFastPath(Integer fastPath) {
		this.fastPath = fastPath;
	}
	
	public Long getCIFNo() {
		return this.CIFNo;
	}
	public void setCIFNo(Long CIFNo) {
		this.CIFNo = CIFNo;
	}
	
	public String getAccountNo() {
		return this.accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	
	public Integer getCurrencyCode() {
		return this.currencyCode;
	}
	public void setCurrencyCode(Integer currencyCode) {
		this.currencyCode = currencyCode;
	}
	
	public java.math.BigDecimal getAmount() {
		return this.amount;
	}
	public void setAmount(java.math.BigDecimal amount) {
		this.amount = amount;
	}
	
	public String getDebitOrCredit() {
		return this.debitOrCredit;
	}
	public void setDebitOrCredit(String debitOrCredit) {
		this.debitOrCredit = debitOrCredit;
	}
	
	public String getNarrative() {
		return this.narrative;
	}
	public void setNarrative(String narrative) {
		this.narrative = narrative;
	}
	
	
	public Integer getBranch() {
		return this.branch;
	}
	public void setBranch(Integer branch) {
		this.branch = branch;
	}
	
	public String getTellerId() {
		return this.tellerId;
	}
	public void setTellerId(String tellerId) {
		this.tellerId = tellerId;
	}
	
	public String getRequester() {
		return this.requester;
	}
	public void setRequester(String requester) {
		this.requester = requester;
	}
	
	public String getApprover() {
		return this.approver;
	}
	public void setApprover(String approver) {
		this.approver = approver;
	}
	
	public String getFlagStatus() {
		return this.flagStatus;
	}
	public void setFlagStatus(String flagStatus) {
		this.flagStatus = flagStatus;
	}
	
	
	/* Hibernate to Database properties */
	
	protected Character getDebitOrCreditDB() {
		return ((this.debitOrCredit == null)? null: this.debitOrCredit.charAt(0));
	}
	protected void setDebitOrCreditDB(Character debitOrCredit) {
		this.debitOrCredit = (debitOrCredit == null)? null: debitOrCredit.toString();
	}
	
	protected Character getFlagStatusDB() {
		return ((this.flagStatus == null)? null: this.flagStatus.charAt(0));
	}
	protected void setFlagStatusDB(Character flagStatus) {
		this.flagStatus = (flagStatus == null)? null: flagStatus.toString();
	}

	
}
