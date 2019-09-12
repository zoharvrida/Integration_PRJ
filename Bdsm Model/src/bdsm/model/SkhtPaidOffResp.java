package bdsm.model;


import java.math.BigDecimal;


/**
 * @author v00019372
 */
@SuppressWarnings("serial")
public class SkhtPaidOffResp extends BaseModel {
	private String refNo;
	private String hajiType;
	private String jamaahName;
	private String acctNo;
	private Integer currencyCode;
	private BigDecimal finalDeposit;
	private Integer bpsBranchCode;
	private String responseCode;
	private String responseDesc;
	private String validationNo;
	private String embarkasi;
	private String kloter;
	private Long portionNo;
	private BigDecimal bpihCost;
	private BigDecimal usdExchange;
	private BigDecimal bpihInIDR;
	private BigDecimal residualCost;
	private String flgPaidOff;
	private String delayYear;
	private Integer PIHKCode;
	private String PIHKName;
	private java.util.Date trxDate;
	private BigDecimal initialDeposit;
	private String gender;
	private String fatherName;
	private String birthPlace;
	private java.util.Date birthDate;
	private Integer delayYearHIJ;
	private String address;
	private String villageName;
	private String districtName;
	private String cityName;
	private String provinceName;
	private Integer postalCode;
	private String bankName;
	private String branchName;
	private String branchAddress;
	private Integer ageInYear;
	private Integer ageInMonth;
	private Integer yearPaidOffMasehi;
	private String acctVA;
	
	
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
	
	public String getJamaahName() {
		return this.jamaahName;
	}
	public void setJamaahName(String jamaahName) {
		this.jamaahName = jamaahName;
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
	
	public BigDecimal getFinalDeposit() {
		return this.finalDeposit;
	}
	public void setFinalDeposit(BigDecimal finalDeposit) {
		this.finalDeposit = finalDeposit;
	}
	
	public Integer getBpsBranchCode() {
		return this.bpsBranchCode;
	}
	public void setBpsBranchCode(Integer bpsBranchCode) {
		this.bpsBranchCode = bpsBranchCode;
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
	
	public String getEmbarkasi() {
		return this.embarkasi;
	}
	public void setEmbarkasi(String embarkasi) {
		this.embarkasi = embarkasi;
	}
	
	public String getKloter() {
		return this.kloter;
	}
	public void setKloter(String kloter) {
		this.kloter = kloter;
	}
	
	public Long getPortionNo() {
		return this.portionNo;
	}
	public void setPortionNo(Long portionNo) {
		this.portionNo = portionNo;
	}
	
	public BigDecimal getBPIHCost() {
		return this.bpihCost;
	}
	public void setBPIHCost(BigDecimal BPIHCost) {
		this.bpihCost = BPIHCost;
	}
	
	public BigDecimal getUSDExchange() {
		return this.usdExchange;
	}
	public void setUSDExchange(BigDecimal USDExchange) {
		this.usdExchange = USDExchange;
	}
	
	public BigDecimal getBPIHInIDR() {
		return this.bpihInIDR;
	}
	public void setBPIHInIDR(BigDecimal BPIHInIDR) {
		this.bpihInIDR = BPIHInIDR;
	}
	
	public BigDecimal getResidualCost() {
		return this.residualCost;
	}
	public void setResidualCost(BigDecimal residualCost) {
		this.residualCost = residualCost;
	}
	
	public String getFlgPaidOff() {
		return this.flgPaidOff;
	}
	public void setFlgPaidOff(String flgPaidOff) {
		this.flgPaidOff = flgPaidOff;
	}
	
	public String getDelayYear() {
		return this.delayYear;
	}
	public void setDelayYear(String delayYear) {
		this.delayYear = delayYear;
	}
	
	public Integer getPIHKCode() {
		return this.PIHKCode;
	}
	public void setPIHKCode(Integer PIHKCode) {
		this.PIHKCode = PIHKCode;
	}
	
	public String getPIHKName() {
		return this.PIHKName;
	}
	public void setPIHKName(String PIHKName) {
		this.PIHKName = PIHKName;
	}
	
	public java.util.Date getTrxDate() {
		return this.trxDate;
	}
	public void setTrxDate(java.util.Date trxDate) {
		this.trxDate = trxDate;
	}
	
	public BigDecimal getInitialDeposit() {
		return this.initialDeposit;
	}
	public void setInitialDeposit(BigDecimal initialDeposit) {
		this.initialDeposit = initialDeposit;
	}
	
	public String getGender() {
		return this.gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public String getFatherName() {
		return this.fatherName;
	}
	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}
	
	public String getBirthPlace() {
		return this.birthPlace;
	}
	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}
	
	public java.util.Date getBirthDate() {
		return this.birthDate;
	}
	public void setBirthDate(java.util.Date birthDate) {
		this.birthDate = birthDate;
	}
	
	public Integer getDelayYearHIJ() {
		return this.delayYearHIJ;
	}
	public void setDelayYearHIJ(Integer delayYearHIJ) {
		this.delayYearHIJ = delayYearHIJ;
	}
	
	public String getAddress() {
		return this.address;
	}
	public void setAddress(String address) {
		this.address = address;
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
	
	public String getProvinceName() {
		return this.provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	
	public Integer getPostalCode() {
		return this.postalCode;
	}
	public void setPostalCode(Integer postalCode) {
		this.postalCode = postalCode;
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
	
	public Integer getYearPaidOffMasehi() {
		return this.yearPaidOffMasehi;
	}
	public void setYearPaidOffMasehi(Integer yearPaidOffMasehi) {
		this.yearPaidOffMasehi = yearPaidOffMasehi;
	}
	
	public String getAcctVA() {
		return this.acctVA;
	}
	public void setAcctVA(String acctVA) {
		this.acctVA = acctVA;
	}
	
	
	/* Hibernate to Database properties */
	protected Character getHajiTypeDB() {
		return ((this.hajiType == null)? null: this.hajiType.charAt(0));
	}
	protected void setHajiTypeDB(Character hajiTypeDB) {
		this.hajiType = (hajiTypeDB == null)? null: hajiTypeDB.toString();
	}
	
	protected Character getFlgPaidOffDB() {
		return ((this.flgPaidOff == null)? null: this.flgPaidOff.charAt(0));
	}
	protected void setFlgPaidOffDB(Character flgPaidOffDB) {
		this.flgPaidOff = (flgPaidOffDB == null)? null: flgPaidOffDB.toString();
	}
	
	protected Character getGenderDB() {
		return ((this.gender == null)? null: this.gender.charAt(0));
	}
	protected void setGenderDB(Character genderDB) {
		this.gender = (genderDB == null)? null: genderDB.toString();
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(this.getClass().getSimpleName())
			.append("{")
			.append("refNo=").append(this.refNo)
			.append(",hajiType=").append(this.hajiType)
			.append(",jamaahName=").append(this.jamaahName)
			.append(",acctNo=").append(this.acctNo)
			.append(",currencyCode=").append(this.currencyCode)
			.append(",finalDeposit=").append(this.finalDeposit)
			.append(",bpsBranchCode=").append(this.bpsBranchCode)
			.append(",responseCode=").append(this.responseCode)
			.append(",responseDesc=").append(this.responseDesc)
			.append(",validationNo=").append(this.validationNo)
			.append(",embarkasi=").append(this.embarkasi)
			.append(",kloter=").append(this.kloter)
			.append(",portionNo=").append(this.portionNo)
			.append(",BPIHCost=").append(this.bpihCost)
			.append(",USDExchange=").append(this.usdExchange)
			.append(",BPIHInIDR=").append(this.bpihInIDR)
			.append(",residualCost=").append(this.residualCost)
			.append(",flgPaidOff=").append(this.flgPaidOff)
			.append(",delayYear=").append(this.delayYear)
			.append(",PIHKCode=").append(this.PIHKCode)
			.append(",PIHKName=").append(this.PIHKName)
			.append(",trxDate=").append(this.trxDate)
			.append(",initialDeposit=").append(this.initialDeposit)
			.append(",gender=").append(this.gender)
			.append(",fatherName=").append(this.fatherName)
			.append(",birthPlace=").append(this.birthPlace)
			.append(",birthDate=").append(this.birthDate)
			.append(",delayYearHIJ=").append(this.delayYearHIJ)
			.append(",address=").append(this.address)
			.append(",villageName=").append(this.villageName)
			.append(",districtName=").append(this.districtName)
			.append(",cityName=").append(this.cityName)
			.append(",provinceName=").append(this.provinceName)
			.append(",postalCode=").append(this.postalCode)
			.append(",bankName=").append(this.bankName)
			.append(",branchName=").append(this.branchName)
			.append(",branchAddress=").append(this.branchAddress)
			.append(",ageInYear=").append(this.ageInYear)
			.append(",ageInMonth=").append(this.ageInMonth)
			.append(",yearPaidOffMasehi=").append(this.yearPaidOffMasehi)
			.append(",acctVA=").append(this.acctVA)
			.append("}");
			
		return sb.toString();
	}
}
