package bdsm.model;


/**
 * @author v00019372
 */
@SuppressWarnings("serial")
public class BillerStandingInstruction extends BaseModel implements java.io.Serializable {
	public static final String PERIODIC_MONTHLY = "M";
	public static final String PERIODIC_ON_DEMAND = "D";
	public static final String NOMINAL_TYPE_FIX = "F";
	public static final String NOMINAL_TYPE_MAXIMUM = "M"; 
	
	
	private String id;
	private String accountNo;
	private String billingNo;
	private String billerName;
	private String billerAccountNo;
	private String billerBankName;
	private String billingPurpose;
	private String paymentPeriodicType;
	private Integer paymentMinDate;
	private Integer paymentMaxDate;
	private String nominalType;
	private java.math.BigDecimal nominal;
	private java.util.Date validUntil;
	private String flagStatus;
	private java.util.Date lastDebited;
	private String requester;
	private String approver;
	
	
	public String getId() {
		return this.id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getAccountNo() {
		return this.accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getBillingNo() {
		return this.billingNo;
	}
	public void setBillingNo(String billingNo) {
		this.billingNo = billingNo;
	}
	
	public String getBillerName() {
		return this.billerName;
	}
	public void setBillerName(String billerName) {
		this.billerName = billerName;
	}
	
	public String getBillerAccountNo() {
		return this.billerAccountNo;
	}
	public void setBillerAccountNo(String billerAccountNo) {
		this.billerAccountNo = billerAccountNo;
	}
	
	public String getBillerBankName() {
		return this.billerBankName;
	}
	public void setBillerBankName(String billerBankName) {
		this.billerBankName = billerBankName;
	}
	
	public String getBillingPurpose() {
		return this.billingPurpose;
	}
	public void setBillingPurpose(String billingPurpose) {
		this.billingPurpose = billingPurpose;
	}
	
	public String getPaymentPeriodicType() {
		return this.paymentPeriodicType;
	}
	public void setPaymentPeriodicType(String paymentPeriodicType) {
		this.paymentPeriodicType = paymentPeriodicType;
	}
	
	public Integer getPaymentMinDate() {
		return this.paymentMinDate;
	}
	public void setPaymentMinDate(Integer paymentMinDate) {
		this.paymentMinDate = paymentMinDate;
	}
	
	public Integer getPaymentMaxDate() {
		return this.paymentMaxDate;
	}
	public void setPaymentMaxDate(Integer paymentMaxDate) {
		this.paymentMaxDate = paymentMaxDate;
	}
	
	public String getNominalType() {
		return this.nominalType;
	}
	public void setNominalType(String nominalType) {
		this.nominalType = nominalType;
	}
	
	public java.math.BigDecimal getNominal() {
		return this.nominal;
	}
	public void setNominal(java.math.BigDecimal nominal) {
		this.nominal = nominal;
	}
	
	public java.util.Date getValidUntil() {
		return this.validUntil;
	}
	public void setValidUntil(java.util.Date validUntil) {
		this.validUntil = validUntil;
	}

	public String getFlagStatus() {
		return this.flagStatus;
	}
	public void setFlagStatus(String flagStatus) {
		this.flagStatus = flagStatus;
	}

	public java.util.Date getLastDebited() {
		return this.lastDebited;
	}
	public void setLastDebited(java.util.Date lastDebited) {
		this.lastDebited = lastDebited;
	}

	public String getRequester() {
		return this.requester;
	}
	public void setRequester(String requester) {
		this.setIdUpdatedBy(requester);
	}

	public String getApprover() {
		return this.approver;
	}
	public void setApprover(String approver) {
		this.setIdUpdatedSpv(approver);
	}
	
	
	
	/* Hibernate to Database properties */

	protected Character getPaymentPeriodicTypeDB() {
		return ((this.paymentPeriodicType == null)? null: this.paymentPeriodicType.charAt(0));
	}
	protected void setPaymentPeriodicTypeDB(Character paymentPeriodicTypeDB) {
		this.paymentPeriodicType = (paymentPeriodicTypeDB == null)? null: paymentPeriodicTypeDB.toString();
	}

	protected Character getNominalTypeDB() {
		return ((this.nominalType == null)? null: this.nominalType.charAt(0));
	}
	protected void setNominalTypeDB(Character nominalTypeDB) {
		this.nominalType = (nominalTypeDB == null)? null: nominalTypeDB.toString();
	}

	protected Character getFlagStatusDB() {
		return ((this.flagStatus == null)? null: this.flagStatus.charAt(0));
	}
	protected void setFlagStatusDB(Character flagStatusDB) {
		this.flagStatus = (flagStatusDB == null)? null: flagStatusDB.toString();
	}



	@Override
	public void setIdUpdatedBy(String idUpdatedBy) {
		super.setIdUpdatedBy(idUpdatedBy);
		this.requester = idUpdatedBy;
	}
	
	@Override
	public void setIdUpdatedSpv(String idUpdatedSpv) {
		super.setIdUpdatedSpv(idUpdatedSpv);
		this.approver = idUpdatedSpv;
	}



	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(this.getClass().getSimpleName())
			.append("{")
			.append("id=").append(this.id)
			.append(",accountNo=").append(this.accountNo)
			.append(",billingNo=").append(this.billingNo)
			.append(",billerName=").append(this.billerName)
			.append(",billerAccountNo=").append(this.billerAccountNo)
			.append(",billerBankName=").append(this.billerBankName)
			.append(",billingPurpose=").append(this.billingPurpose)
			.append(",paymentPeriodicType=").append(this.paymentPeriodicType)
			.append(",paymentMinDate=").append(this.paymentMinDate)
			.append(",paymentMaxDate=").append(this.paymentMaxDate)
			.append(",nominalType=").append(this.nominalType)
			.append(",nominal=").append(this.nominal)
			.append(",validUntil=").append(this.validUntil)
			.append(",flagStatus=").append(this.flagStatus)
			.append(",lastDebited=").append(this.lastDebited)
			.append("}");
	
		return sb.toString();
	}	
}
