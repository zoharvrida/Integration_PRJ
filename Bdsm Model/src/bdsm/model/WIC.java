package bdsm.model;


/**
 * 
 * @author v00019372
 */
@SuppressWarnings("serial")
public class WIC extends BaseModel implements java.io.Serializable {
	private Integer WICNo;
	private Integer customerType;
	private String idType;
	private String idNumber;
	private java.util.Date idExpiredDate;
	private String name;
	private java.util.Date birthDate;
	private String birthPlace;
	private String gender;
	private Integer maritalStatus;
	private String address1;
	private String address2;
	private String address3;
	private String postalCode;
	private String city;
	private String state;
	private String country;
	private String citizenship;
	private Integer occupation;
	private String notes;
	private Boolean residentialEqualIdentity = Boolean.FALSE;
	private String residentialAddress1;
	private String residentialAddress2;
	private String residentialAddress3;
	private String residentialPostalCode;
	private String residentialCity;
	private String residentialState;
	private String residentialCountry;
	private String businessType;
	private String sourceOfFunds;
	private String sourceOfFundsOthers;
	private String trxPurpose;
	private String jobTitle;
	private String officeName;
	private String officeAddress1;
	private String officeAddress2;
	private String officeAddress3;
	private String homePhoneNo;
	private String officePhoneNo;
	private String mobilePhoneNo;
	private Integer incomePerMonthType;
	private String instanceRepresented;
	private String accountRepresented;
	private String authLetterNo;
	private String accountBranch;
	private String highRiskWIC;
	private String highRiskCountry;
	private Integer branch;
	private String requester;
	private String approver;
	private String flagStatus;
	
	
	public Integer getWICNo() {
		return this.WICNo;
	}
	public void setWICNo(Integer wICNo) {
		this.WICNo = wICNo;
	}
	
	public Integer getCustomerType() {
		return this.customerType;
	}
	public void setCustomerType(Integer customerType) {
		this.customerType = customerType;
	}
	
	public String getIdType() {
		return this.idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	
	public String getIdNumber() {
		return this.idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	public java.util.Date getIdExpiredDate() {
		return this.idExpiredDate;
	}
	public void setIdExpiredDate(java.util.Date idExpiredDate) {
		this.idExpiredDate = idExpiredDate;
	}
	
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public java.util.Date getBirthDate() {
		return this.birthDate;
	}
	public void setBirthDate(java.util.Date birthDate) {
		this.birthDate = birthDate;
	}
	
	public String getBirthPlace() {
		return this.birthPlace;
	}
	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}
	
	public String getGender() {
		return this.gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public Integer getMaritalStatus() {
		return this.maritalStatus;
	}
	public void setMaritalStatus(Integer maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	
	public String getAddress1() {
		return this.address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	
	public String getAddress2() {
		return this.address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	
	public String getAddress3() {
		return this.address3;
	}
	public void setAddress3(String address3) {
		this.address3 = address3;
	}
	
	public String getPostalCode() {
		return this.postalCode;
	}
	
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	
	public String getCity() {
		return this.city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	public String getState() {
		return this.state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	public String getCountry() {
		return this.country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getCitizenship() {
		return this.citizenship;
	}
	public void setCitizenship(String citizenship) {
		this.citizenship = citizenship;
	}
	
	public Integer getOccupation() {
		return this.occupation;
	}
	public void setOccupation(Integer occupation) {
		this.occupation = occupation;
	}
	
	public String getNotes() {
		return this.notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	public Boolean getResidentialEqualIdentity() {
		return (this.residentialEqualIdentity == null)? Boolean.FALSE: this.residentialEqualIdentity;
	}
	public void setResidentialEqualIdentity(Boolean residentialEqualIdentity) {
		this.residentialEqualIdentity = residentialEqualIdentity;
	}
	
	public String getResidentialAddress1() {
		return this.residentialAddress1;
	}
	public void setResidentialAddress1(String residentialAddress1) {
		this.residentialAddress1 = residentialAddress1;
	}
	
	public String getResidentialAddress2() {
		return this.residentialAddress2;
	}
	public void setResidentialAddress2(String residentialAddress2) {
		this.residentialAddress2 = residentialAddress2;
	}
	
	public String getResidentialAddress3() {
		return this.residentialAddress3;
	}
	public void setResidentialAddress3(String residentialAddress3) {
		this.residentialAddress3 = residentialAddress3;
	}
	
	public String getResidentialPostalCode() {
		return this.residentialPostalCode;
	}
	public void setResidentialPostalCode(String residentialPostalCode) {
		this.residentialPostalCode = residentialPostalCode;
	}
	
	
	public String getResidentialCity() {
		return this.residentialCity;
	}
	public void setResidentialCity(String residentialCity) {
		this.residentialCity = residentialCity;
	}
	
	public String getResidentialState() {
		return this.residentialState;
	}
	public void setResidentialState(String residentialState) {
		this.residentialState = residentialState;
	}
	
	public String getResidentialCountry() {
		return this.residentialCountry;
	}
	public void setResidentialCountry(String residentialCountry) {
		this.residentialCountry = residentialCountry;
	}
	
	public String getBusinessType() {
		return this.businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	
	public String getSourceOfFunds() {
		return this.sourceOfFunds;
	}
	public void setSourceOfFunds(String sourceOfFunds) {
		this.sourceOfFunds = sourceOfFunds;
	}
	
	public String getSourceOfFundsOthers() {
		return this.sourceOfFundsOthers;
	}
	public void setSourceOfFundsOthers(String sourceOfFundsOthers) {
		this.sourceOfFundsOthers = sourceOfFundsOthers;
	}
	
	public String getTrxPurpose() {
		return this.trxPurpose;
	}
	public void setTrxPurpose(String trxPurpose) {
		this.trxPurpose = trxPurpose;
	}
	
	public String getJobTitle() {
		return this.jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	
	public String getOfficeName() {
		return this.officeName;
	}
	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}
	
	public String getOfficeAddress1() {
		return this.officeAddress1;
	}
	public void setOfficeAddress1(String officeAddress1) {
		this.officeAddress1 = officeAddress1;
	}
	
	public String getOfficeAddress2() {
		return this.officeAddress2;
	}
	public void setOfficeAddress2(String officeAddress2) {
		this.officeAddress2 = officeAddress2;
	}
	
	public String getOfficeAddress3() {
		return this.officeAddress3;
	}
	public void setOfficeAddress3(String officeAddress3) {
		this.officeAddress3 = officeAddress3;
	}
	
	public String getHomePhoneNo() {
		return this.homePhoneNo;
	}
	public void setHomePhoneNo(String homePhoneNo) {
		this.homePhoneNo = homePhoneNo;
	}
	
	public String getOfficePhoneNo() {
		return this.officePhoneNo;
	}
	public void setOfficePhoneNo(String officePhoneNo) {
		this.officePhoneNo = officePhoneNo;
	}
	
	public String getMobilePhoneNo() {
		return this.mobilePhoneNo;
	}
	public void setMobilePhoneNo(String mobilePhoneNo) {
		this.mobilePhoneNo = mobilePhoneNo;
	}
	
	public Integer getIncomePerMonthType() {
		return this.incomePerMonthType;
	}
	public void setIncomePerMonthType(Integer incomePerMonthType) {
		this.incomePerMonthType = incomePerMonthType;
	}
	
	public String getInstanceRepresented() {
		return this.instanceRepresented;
	}
	public void setInstanceRepresented(String instanceRepresented) {
		this.instanceRepresented = instanceRepresented;
	}
	
	public String getAccountRepresented() {
		return this.accountRepresented;
	}
	public void setAccountRepresented(String accountRepresented) {
		this.accountRepresented = accountRepresented;
	}
	
	public String getAuthLetterNo() {
		return this.authLetterNo;
	}
	public void setAuthLetterNo(String authLetterNo) {
		this.authLetterNo = authLetterNo;
	}
	
	public String getAccountBranch() {
		return this.accountBranch;
	}
	public void setAccountBranch(String accountBranch) {
		this.accountBranch = accountBranch;
	}
	
	public String getHighRiskWIC() {
		return this.highRiskWIC;
	}
	public void setHighRiskWIC(String highRiskWIC) {
		this.highRiskWIC = highRiskWIC;
	}
	
	public String getHighRiskCountry() {
		return this.highRiskCountry;
	}
	public void setHighRiskCountry(String highRiskCountry) {
		this.highRiskCountry = highRiskCountry;
	}
	
	public Integer getBranch() {
		return this.branch;
	}
	public void setBranch(Integer branch) {
		this.branch = branch;
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
	
	protected Character getGenderDB() {
		return ((this.gender == null)? null: this.gender.charAt(0));
	}
	protected void setGenderDB(Character gender) {
		this.gender = (gender == null)? null: gender.toString();
	}
	
	protected Character getFlagStatusDB() {
		return ((this.flagStatus == null)? null: this.flagStatus.charAt(0));
	}
	protected void setFlagStatusDB(Character flagStatus) {
		this.flagStatus = (flagStatus == null)? null: flagStatus.toString();
	}
	
}
