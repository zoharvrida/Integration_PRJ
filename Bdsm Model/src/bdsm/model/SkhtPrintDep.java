package bdsm.model;


import java.math.BigDecimal;
import java.util.Date;


/**
 * @author v00019372
 */
@SuppressWarnings("serial")
public class SkhtPrintDep extends BaseModel implements java.io.Serializable {
	private String refNo;
	private String hajiType;
	private Date trxDate;
	private String jamaahName;
	private String NIK;
	private String birthPlace;
	private Date birthDate;
	private String address;
	private Integer postalCode;
	private String villageName;
	private String districtName;
	private String cityName;
	private String gender;
	private String acctNo;
	private Integer currencyCode;
	private BigDecimal initialDeposit;
	private String acctVA;
	private Integer bpsBranchCode;
	private Integer provinceCode;
	private Integer cityCode;
	private String education;
	private String maritalStatus;
	private String fatherName;
	private String responseCode;
	private String responseDesc;
	private String validationNo;
	private String provinceName;
	private String occupation;
	private Integer ageInYear;
	private Integer ageInMonth;
	private String bankName;
	private String branchName;
	private String branchAddress;
	
	
	public String getRefNo() {
		return this.refNo;
	}
	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}
	
	public String getHajiType() {
		return this.hajiType;
	}
	public void setHajiType(String hajiType) {
		this.hajiType = hajiType;
	}
	
	public Date getTrxDate() {
		return this.trxDate;
	}
	public void setTrxDate(Date trxDate) {
		this.trxDate = trxDate;
	}
	
	public String getJamaahName() {
		return this.jamaahName;
	}
	public void setJamaahName(String jamaahName) {
		this.jamaahName = jamaahName;
	}
	
	public String getNIK() {
		return this.NIK;
	}
	public void setNIK(String nIK) {
		NIK = nIK;
	}
	
	public String getBirthPlace() {
		return this.birthPlace;
	}
	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}
	
	public Date getBirthDate() {
		return this.birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	
	public String getAddress() {
		return this.address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public Integer getPostalCode() {
		return this.postalCode;
	}
	public void setPostalCode(Integer postalCode) {
		this.postalCode = postalCode;
	}
	
	public String getVillageName() {
		return this.villageName;
	}
	public void setVillageName(String villageName) {
		this.villageName = villageName;
	}
	
	public String getDistrictName() {
		return this.districtName;
	}
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	
	public String getCityName() {
		return this.cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	public String getGender() {
		return this.gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public String getAcctNo() {
		return this.acctNo;
	}
	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}
	
	public Integer getCurrencyCode() {
		return this.currencyCode;
	}
	public void setCurrencyCode(Integer currencyCode) {
		this.currencyCode = currencyCode;
	}
	
	public BigDecimal getInitialDeposit() {
		return this.initialDeposit;
	}
	public void setInitialDeposit(BigDecimal initialDeposit) {
		this.initialDeposit = initialDeposit;
	}
	
	public String getAcctVA() {
		return this.acctVA;
	}
	public void setAcctVA(String acctVA) {
		this.acctVA = acctVA;
	}
	
	public Integer getBpsBranchCode() {
		return this.bpsBranchCode;
	}
	public void setBpsBranchCode(Integer bpsBranchCode) {
		this.bpsBranchCode = bpsBranchCode;
	}
	
	public Integer getProvinceCode() {
		return this.provinceCode;
	}
	public void setProvinceCode(Integer provinceCode) {
		this.provinceCode = provinceCode;
	}
	
	public Integer getCityCode() {
		return this.cityCode;
	}
	public void setCityCode(Integer cityCode) {
		this.cityCode = cityCode;
	}
	
	public String getEducation() {
		return this.education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	
	public String getMaritalStatus() {
		return this.maritalStatus;
	}
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	
	public String getFatherName() {
		return this.fatherName;
	}
	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}
	
	public String getResponseCode() {
		return this.responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	
	public String getResponseDesc() {
		return this.responseDesc;
	}
	public void setResponseDesc(String responseDesc) {
		this.responseDesc = responseDesc;
	}
	
	public String getValidationNo() {
		return this.validationNo;
	}
	public void setValidationNo(String validationNo) {
		this.validationNo = validationNo;
	}
	
	public String getProvinceName() {
		return this.provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	
	public String getOccupation() {
		return this.occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	
	public Integer getAgeInYear() {
		return this.ageInYear;
	}
	public void setAgeInYear(Integer ageInYear) {
		this.ageInYear = ageInYear;
	}
	
	public Integer getAgeInMonth() {
		return this.ageInMonth;
	}
	public void setAgeInMonth(Integer ageInMonth) {
		this.ageInMonth = ageInMonth;
	}
	
	public String getBankName() {
		return this.bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	public String getBranchName() {
		return this.branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	
	public String getBranchAddress() {
		return this.branchAddress;
	}
	public void setBranchAddress(String branchAddress) {
		this.branchAddress = branchAddress;
	}
	
	
	/* Hibernate to Database properties */
	protected Character getHajiTypeDB() {
		return ((this.hajiType == null)? null: this.hajiType.charAt(0));
	}
	protected void setHajiTypeDB(Character hajiTypeDB) {
		this.hajiType = (hajiTypeDB == null)? null: hajiTypeDB.toString();
	}
	
	protected Character getGenderDB() {
		return ((this.gender == null)? null: this.gender.charAt(0));
	}
	protected void setGenderDB(Character genderDB) {
		this.gender = (genderDB == null)? null: genderDB.toString();
	}
	
	protected Character getMaritalStatusDB() {
		return ((this.maritalStatus == null)? null: this.maritalStatus.charAt(0));
	}
	protected void setMaritalStatusDB(Character maritalStatusDB) {
		this.maritalStatus = (maritalStatusDB == null)? null: maritalStatusDB.toString();
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(this.getClass().getSimpleName())
			.append("{")
			.append("refNo=").append(this.refNo)
			.append(",hajiType=").append(this.hajiType)
			.append(",trxDate=").append(this.trxDate)
			.append(",jamaahName=").append(this.jamaahName)
			.append(",NIK=").append(this.NIK)
			.append(",birthPlace=").append(this.birthPlace)
			.append(",birthDate=").append(this.birthDate)
			.append(",address=").append(this.address)
			.append(",postalCode=").append(this.postalCode)
			.append(",villageName=").append(this.villageName)
			.append(",districtName=").append(this.districtName)
			.append(",cityName=").append(this.cityName)
			.append(",gender=").append(this.gender)
			.append(",acctNo=").append(this.acctNo)
			.append(",currencyCode=").append(this.currencyCode)
			.append(",initialDeposit=").append(this.initialDeposit)
			.append(",acctVA=").append(this.acctVA)
			.append(",bpsBranchCode=").append(this.bpsBranchCode)
			.append(",provinceCode=").append(this.provinceCode)
			.append(",cityCode=").append(this.cityCode)
			.append(",education=").append(this.education)
			.append(",maritalStatus=").append(this.maritalStatus)
			.append(",fatherName=").append(this.fatherName)
			.append(",responseCode=").append(this.responseCode)
			.append(",responseDesc=").append(this.responseDesc)
			.append(",validationNo=").append(this.validationNo)
			.append(",provinceName=").append(this.provinceName)
			.append(",occupation=").append(this.occupation)
			.append(",ageInYear=").append(this.ageInYear)
			.append(",ageInMonth=").append(this.ageInMonth)
			.append(",bankName=").append(this.bankName)
			.append(",branchName=").append(this.branchName)
			.append(",branchAddress=").append(this.branchAddress)
			.append("}");
			
		return sb.toString();
	}
	
}
