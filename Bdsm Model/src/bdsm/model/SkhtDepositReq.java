package bdsm.model;


import java.math.BigDecimal;
import java.util.Date;


/**
 * @author v00019372
 */
@SuppressWarnings("serial")
public class SkhtDepositReq extends BaseModel {
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
	private String occupationCode;
	private Integer educationCode;
	private String maritalStatus;
	private String fatherName;
	private Integer cifNumber;
	
	
	protected static String stringMax(String input, int max) {
		if ((input != null) && (input.length() > max))
			return input.substring(0, max);
		else
			return input;
	}
	
	
	public String getRefNo() {
		return this.refNo;
	}
	public void setRefNo(String refNo) {
		this.refNo = stringMax(refNo, 40);
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
		this.jamaahName = stringMax(jamaahName, 40);
	}
	
	public String getNIK() {
		return this.NIK;
	}
	public void setNIK(String NIK) {
		this.NIK = stringMax(NIK, 20);
	}
	
	public String getBirthPlace() {
		return this.birthPlace;
	}
	public void setBirthPlace(String birthPlace) {
		this.birthPlace = stringMax(birthPlace, 25);
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
		this.address = stringMax(address, 25);
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
		this.villageName = stringMax(villageName, 25);
	}
	
	public String getDistrictName() {
		return this.districtName;
	}
	public void setDistrictName(String districtName) {
		this.districtName = stringMax(districtName, 25);
	}
	
	public String getCityName() {
		return this.cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = stringMax(cityName, 25);
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
		this.acctNo = stringMax(acctNo, 19);
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
		this.acctVA = stringMax(acctVA, 25);
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
	
	public String getOccupationCode() {
		return this.occupationCode;
	}
	public void setOccupationCode(String occupationCode) {
		this.occupationCode = occupationCode;
	}
	
	public Integer getEducationCode() {
		return this.educationCode;
	}
	public void setEducationCode(Integer educationCode) {
		this.educationCode = educationCode;
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
		this.fatherName = stringMax(fatherName, 40);
	}
	
	public Integer getCifNumber() {
		return this.cifNumber;
	}
	public void setCifNumber(Integer cifNumber) {
		this.cifNumber = cifNumber;
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
	
	protected Character getOccupationCodeDB() {
		return ((this.occupationCode == null)? null: this.occupationCode.charAt(0));
	}
	protected void setOccupationCodeDB(Character occupationCodeDB) {
		this.occupationCode = (occupationCodeDB == null)? null: occupationCodeDB.toString();
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
			.append(",occupationCode=").append(this.occupationCode)
			.append(",educationCode=").append(this.educationCode)
			.append(",maritalStatus=").append(this.maritalStatus)
			.append(",fatherName=").append(this.fatherName)
			.append(",cifNumber=").append(this.cifNumber)
			.append("}");
	
		return sb.toString();
	}
	
}
