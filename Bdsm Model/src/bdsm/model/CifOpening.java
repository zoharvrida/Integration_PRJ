/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;

import java.io.Serializable;

/**
 *
 * @author v00019722
 */
@SuppressWarnings("serial")
public class CifOpening extends BaseModel implements Serializable {

    private String applicationID;
    private Integer cifNumber;
    private String category;
    private String cifType;
 
    private String staff;
    private Integer accessCode;
    private Integer branchCode;
    private String branchCodeName;
    
    private String nik; // rep EKTP
    private String idcardTDesc;
    private String idcardType;
    private String salutation;
    private String salutationDesc;
    private String fullName;
    private String custFirstName;
    private String custMidName;
    private String custLastName;
    private String shortName;
    private String permaAddress1;
    private String permaAddress2;
    private String permaAddress3;
    private String permaZipCode;
    private String permaTownCity;
    private String permaTownCityDesc;
    private String permaState;
    private String permaStateDesc;
    private String permaCountry;
    private String permaCountryDesc;
    private String residencePhoneNo;
    private String residencePhoneNoContry;
    private String residencePhoneNoArea;
    private String residencePhoneNoExt;
    private String residenceFax;
    private String mailAddrs1;
    private String mailAddrs2;
    private String mailAddrs3;
    private String maillingCode;
    private String maillingTownCity;
    private String maillingTownCityDesc;
    private String maillingState;
    private String maillingStateDesc;
    private String maillingCountry;
    private String maillingCountryDesc;
    private String holdAddress1;
    private String holdAddress2;
    private String holdAddress3;
    private String holdZipCode;
    private String holdTownCity;
    private String holdState;
    private String holdCountry;
    private String holdTownCityDesc;
    private String holdStateDesc;
    private String holdCountryDesc;
    private String holdPhoneNo;
    private String holdPhoneCountry;
    private String holdPhoneArea;
    private String holdPhoneExt;   
    private String birthDate;
    private String mobilePhone;
    private String mobilePhone2;
    private String lastEdu; // rep EKTP
    private String lastEduDesc; // rep EKTP
    private String emailAddress;
    private String religion; // rep EKTP
    private String religionDesc; // rep EKTP
    private String nationality;
    private String nationalityDesc;
    private String countryOfResidence;
    private String countryOfResidenceDesc;
    private String gender;
    private String genderDesc;
    private String marStat; // rep EKTP
    private String marStatDesc; // rep EKTP
    private String incomeTaxNo;
    private String aoBusiness;
    private String aoBusinessDesc;
    private String aoOperation;
    private String aoOperationDesc;
    private Integer businessType;
    private String businessTypeDesc;
    private Integer professionCode;
    private String professionDesc;
    private String spouseName;
    private String spouseEmployer;
    private String spouseJobTitle;
    private String occupation;
    private String occupationDesc;
    private String employerAddress1;
    private String employerAddress2;
    private String employerAddress3;
    private String employerZipCode;
    private String employerTownCity;
    private String employerTownCityDesc;
    private String employerState;
    private String employerCountry;
    private String employerStateDesc;
    private String employerCountryDesc;
    private String employerName;
    private String employerPhoneNo;
    private String employerPhoneNoCountry;
    private String employerPhoneNoArea;
    private String employerPhoneNoExt;
    private String employementDetails;
    private String employeeId;
    private Integer lob;
    private String lobDesc;
    private String jobTitle;
    private String debtType;
    private String debtStatus;
    private String branchLocationCode;
    private String districtCode;
    private String employeICCode;
    private String conWithBank;
    private String residenceType;
    private String signType;
    // UDF
    private Integer noOfDependant;
    private Integer dataTransXtractFlag;
    private String dataTransXtractFlagDesc;
    private String communication;
    private String communicationDesc;
    private String namMother; // rep EKTP
    private String birthPlace; // rep EKTP
    private String sumberDanaLain;
    private String aliasName;
    private String reliwthBank;
    private String ownershipStat;
    private String golPemilikLBUlama;
    private String sandiPerushLHBULama;
    private String sandiDatiII;
    private String sandiDatiIIDesc;
    private String branchlocCodes;
    private String branchlocCodesDesc;
    private Integer expectedLimit;
    private String expectedLimitDesc;
    private Integer monthLyIncome;
    private String monthLyIncomeDesc;
    private String icexpiryDate;
    private Integer homeStatus;
    private String homeStatusDesc;
    private String smsNotifCust;
    private String citizenType;
    private String relation;
    private String badanAS;
    private String badanASDesc;
    private String productBundling;
    private String productBundlingDesc;
    private String greenCard;
    private String greenCardDesc;
    private String alamatAS;
    private String alamatASDesc;   
    private String fatca;
    private String fatcaDesc;
    private String tin;
    private String registrationNo;
    private String registrationDate;
    private String businessCommendate;
    private String businessLicenseNo;
    private String placeOfInc;
    private String additionalField1;
    private String additionalField2;
    private String additionalField3;
    private String additionalField4;
    private String additionalField5;
    private String additionalField6;
    private String additionalField7;
    private String additionalField8;
    private String additionalField9;
    private String additionalField10;   
    private String typeOfCompany;  
    private String comments;
    private String flgProcess;    
    private String flgIctype;
    private String channelID;
    private String codUserID;
    private String reasonForVerification;
	private Integer incr;
    /**
     * @return the applicationID
     */
    public String getApplicationID() {
        return applicationID;
    }

    /**
     * @param applicationID the applicationID to set
     */
    public void setApplicationID(String applicationID) {
        this.applicationID = applicationID;
    }

    /**
     * @return the cifNumber
     */
    public Integer getCifNumber() {
        return cifNumber;
    }

    /**
     * @param cifNumber the cifNumber to set
     */
    public void setCifNumber(Integer cifNumber) {
        this.cifNumber = cifNumber;
    }

    /**
     * @return the nik
     */
    public String getNik() {
        return nik;
    }

    /**
     * @param nik the nik to set
     */
    public void setNik(String nik) {
        this.nik = nik;
    }

    /**
     * @return the fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * @param fullName the fullName to set
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * @return the birthDate
     */
    public String getBirthDate() {
        return birthDate;
    }

    /**
     * @param birthDate the birthDate to set
     */
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * @return the religion
     */
    public String getReligion() {
        return religion;
    }

    /**
     * @param religion the religion to set
     */
    public void setReligion(String religion) {
        this.religion = religion;
    }

    /**
     * @return the religionDesc
     */
    public String getReligionDesc() {
        return religionDesc;
    }

    /**
     * @param religionDesc the religionDesc to set
     */
    public void setReligionDesc(String religionDesc) {
        this.religionDesc = religionDesc;
    }

    /**
     * @return the branchCode
     */
    public Integer getBranchCode() {
        return branchCode;
    }

    /**
     * @param branchCode the branchCode to set
     */
    public void setBranchCode(Integer branchCode) {
        this.branchCode = branchCode;
    }

    /**
     * @return the branchCodeName
     */
    public String getBranchCodeName() {
        return branchCodeName;
    }

    /**
     * @param branchCodeName the branchCodeName to set
     */
    public void setBranchCodeName(String branchCodeName) {
        this.branchCodeName = branchCodeName;
    }

    /**
     * @return the accessCode
     */
    public Integer getAccessCode() {
        return accessCode;
    }

    /**
     * @param accessCode the accessCode to set
     */
    public void setAccessCode(Integer accessCode) {
        this.accessCode = accessCode;
    }

    /**
     * @return the lob
     */
    public Integer getLob() {
        return lob;
    }

    /**
     * @param lob the lob to set
     */
    public void setLob(Integer lob) {
        this.lob = lob;
    }

    /**
     * @return the lobDesc
     */
    public String getLobDesc() {
        return lobDesc;
    }

    /**
     * @param lobDesc the lobDesc to set
     */
    public void setLobDesc(String lobDesc) {
        this.lobDesc = lobDesc;
    }

    /**
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * @return the cifType
     */
    public String getCifType() {
        return cifType;
    }

    /**
     * @param cifType the cifType to set
     */
    public void setCifType(String cifType) {
        this.cifType = cifType;
    }

    /**
     * @return the shortName
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * @param shortName the shortName to set
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    /**
     * @return the salutation
     */
    public String getSalutation() {
        return salutation;
    }

    /**
     * @param salutation the salutation to set
     */
    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    /**
     * @return the salutationDesc
     */
    public String getSalutationDesc() {
        return salutationDesc;
    }

    /**
     * @param salutationDesc the salutationDesc to set
     */
    public void setSalutationDesc(String salutationDesc) {
        this.salutationDesc = salutationDesc;
    }

    /**
     * @return the custFirstName
     */
    public String getCustFirstName() {
        return custFirstName;
    }

    /**
     * @param custFirstName the custFirstName to set
     */
    public void setCustFirstName(String custFirstName) {
        this.custFirstName = custFirstName;
    }

    /**
     * @return the custMidName
     */
    public String getCustMidName() {
        return custMidName;
    }

    /**
     * @param custMidName the custMidName to set
     */
    public void setCustMidName(String custMidName) {
        this.custMidName = custMidName;
    }

    /**
     * @return the custLastName
     */
    public String getCustLastName() {
        return custLastName;
    }

    /**
     * @param custLastName the custLastName to set
     */
    public void setCustLastName(String custLastName) {
        this.custLastName = custLastName;
    }

    /**
     * @return the spouseJobTitle
     */
    public String getSpouseJobTitle() {
        return spouseJobTitle;
    }

    /**
     * @param spouseJobTitle the spouseJobTitle to set
     */
    public void setSpouseJobTitle(String spouseJobTitle) {
        this.spouseJobTitle = spouseJobTitle;
    }

    /**
     * @return the mailAddrs1
     */
    public String getMailAddrs1() {
        return mailAddrs1;
    }

    /**
     * @param mailAddrs1 the mailAddrs1 to set
     */
    public void setMailAddrs1(String mailAddrs1) {
        this.mailAddrs1 = mailAddrs1;
    }

    /**
     * @return the mailAddrs2
     */
    public String getMailAddrs2() {
        return mailAddrs2;
    }

    /**
     * @param mailAddrs2 the mailAddrs2 to set
     */
    public void setMailAddrs2(String mailAddrs2) {
        this.mailAddrs2 = mailAddrs2;
    }

    /**
     * @return the mailAddrs3
     */
    public String getMailAddrs3() {
        return mailAddrs3;
    }

    /**
     * @param mailAddrs3 the mailAddrs3 to set
     */
    public void setMailAddrs3(String mailAddrs3) {
        this.mailAddrs3 = mailAddrs3;
    }

 
    /**
     * @return the maillingTownCity
     */
    public String getMaillingTownCity() {
        return maillingTownCity;
    }

    /**
     * @param maillingTownCity the maillingTownCity to set
     */
    public void setMaillingTownCity(String maillingTownCity) {
        this.maillingTownCity = maillingTownCity;
    }

    /**
     * @return the maillingTownCityDesc
     */
    public String getMaillingTownCityDesc() {
        return maillingTownCityDesc;
    }

    /**
     * @param maillingTownCityDesc the maillingTownCityDesc to set
     */
    public void setMaillingTownCityDesc(String maillingTownCityDesc) {
        this.maillingTownCityDesc = maillingTownCityDesc;
    }

    /**
     * @return the maillingState
     */
    public String getMaillingState() {
        return maillingState;
    }

    /**
     * @param maillingState the maillingState to set
     */
    public void setMaillingState(String maillingState) {
        this.maillingState = maillingState;
    }

    /**
     * @return the maillingStateDesc
     */
    public String getMaillingStateDesc() {
        return maillingStateDesc;
    }

    /**
     * @param maillingStateDesc the maillingStateDesc to set
     */
    public void setMaillingStateDesc(String maillingStateDesc) {
        this.maillingStateDesc = maillingStateDesc;
    }

    /**
     * @return the maillingCountry
     */
    public String getMaillingCountry() {
        return maillingCountry;
    }

    /**
     * @param maillingCountry the maillingCountry to set
     */
    public void setMaillingCountry(String maillingCountry) {
        this.maillingCountry = maillingCountry;
    }

    /**
     * @return the maillingCountryDesc
     */
    public String getMaillingCountryDesc() {
        return maillingCountryDesc;
    }

    /**
     * @param maillingCountryDesc the maillingCountryDesc to set
     */
    public void setMaillingCountryDesc(String maillingCountryDesc) {
        this.maillingCountryDesc = maillingCountryDesc;
    }

    /**
     * @return the permaAddress1
     */
    public String getPermaAddress1() {
        return permaAddress1;
    }

    /**
     * @param permaAddress1 the permaAddress1 to set
     */
    public void setPermaAddress1(String permaAddress1) {
        this.permaAddress1 = permaAddress1;
    }

    /**
     * @return the permaAddress2
     */
    public String getPermaAddress2() {
        return permaAddress2;
    }

    /**
     * @param permaAddress2 the permaAddress2 to set
     */
    public void setPermaAddress2(String permaAddress2) {
        this.permaAddress2 = permaAddress2;
    }

    /**
     * @return the permaAddress3
     */
    public String getPermaAddress3() {
        return permaAddress3;
    }

    /**
     * @param permaAddress3 the permaAddress3 to set
     */
    public void setPermaAddress3(String permaAddress3) {
        this.permaAddress3 = permaAddress3;
    }

    /**
     * @return the permaZipCode
     */
    public String getPermaZipCode() {
        return permaZipCode;
    }

    /**
     * @param permaZipCode the permaZipCode to set
     */
    public void setPermaZipCode(String permaZipCode) {
        this.permaZipCode = permaZipCode;
    }

    /**
     * @return the permaTownCity
     */
    public String getPermaTownCity() {
        return permaTownCity;
    }

    /**
     * @param permaTownCity the permaTownCity to set
     */
    public void setPermaTownCity(String permaTownCity) {
        this.permaTownCity = permaTownCity;
    }

    /**
     * @return the expectedLimit
     */
    public Integer getExpectedLimit() {
        return expectedLimit;
    }

    /**
     * @param expectedLimit the expectedLimit to set
     */
    public void setExpectedLimit(Integer expectedLimit) {
        this.expectedLimit = expectedLimit;
    }

    /**
     * @return the expectedLimitDesc
     */
    public String getExpectedLimitDesc() {
        return expectedLimitDesc;
    }

    /**
     * @param expectedLimitDesc the expectedLimitDesc to set
     */
    public void setExpectedLimitDesc(String expectedLimitDesc) {
        this.expectedLimitDesc = expectedLimitDesc;
    }

    /**
     * @return the permaTownCityDesc
     */
    public String getPermaTownCityDesc() {
        return permaTownCityDesc;
    }

    /**
     * @param permaTownCityDesc the permaTownCityDesc to set
     */
    public void setPermaTownCityDesc(String permaTownCityDesc) {
        this.permaTownCityDesc = permaTownCityDesc;
    }

    /**
     * @return the relation
     */
    public String getRelation() {
        return relation;
    }

    /**
     * @param relation the relation to set
     */
    public void setRelation(String relation) {
        this.relation = relation;
    }

    /**
     * @return the permaState
     */
    public String getPermaState() {
        return permaState;
    }

    /**
     * @param permaState the permaState to set
     */
    public void setPermaState(String permaState) {
        this.permaState = permaState;
    }

    /**
     * @return the permaStateDesc
     */
    public String getPermaStateDesc() {
        return permaStateDesc;
    }

    /**
     * @param permaStateDesc the permaStateDesc to set
     */
    public void setPermaStateDesc(String permaStateDesc) {
        this.permaStateDesc = permaStateDesc;
    }

    /**
     * @return the permaCountry
     */
    public String getPermaCountry() {
        return permaCountry;
    }

    /**
     * @param permaCountry the permaCountry to set
     */
    public void setPermaCountry(String permaCountry) {
        this.permaCountry = permaCountry;
    }

    /**
     * @return the permaCountryDesc
     */
    public String getPermaCountryDesc() {
        return permaCountryDesc;
    }

    /**
     * @param permaCountryDesc the permaCountryDesc to set
     */
    public void setPermaCountryDesc(String permaCountryDesc) {
        this.permaCountryDesc = permaCountryDesc;
    }

    /**
     * @return the employerName
     */
    public String getEmployerName() {
        return employerName;
    }

    /**
     * @param employerName the employerName to set
     */
    public void setEmployerName(String employerName) {
        this.employerName = employerName;
    }

    /**
     * @return the employerAddress1
     */
    public String getEmployerAddress1() {
        return employerAddress1;
    }

    /**
     * @param employerAddress1 the employerAddress1 to set
     */
    public void setEmployerAddress1(String employerAddress1) {
        this.employerAddress1 = employerAddress1;
    }

    /**
     * @return the employerAddress2
     */
    public String getEmployerAddress2() {
        return employerAddress2;
    }

    /**
     * @param employerAddress2 the employerAddress2 to set
     */
    public void setEmployerAddress2(String employerAddress2) {
        this.employerAddress2 = employerAddress2;
    }

    /**
     * @return the employerAddress3
     */
    public String getEmployerAddress3() {
        return employerAddress3;
    }

    /**
     * @param employerAddress3 the employerAddress3 to set
     */
    public void setEmployerAddress3(String employerAddress3) {
        this.employerAddress3 = employerAddress3;
    }

    /**
     * @return the employerZipCode
     */
    public String getEmployerZipCode() {
        return employerZipCode;
    }

    /**
     * @param employerZipCode the employerZipCode to set
     */
    public void setEmployerZipCode(String employerZipCode) {
        this.employerZipCode = employerZipCode;
    }

    /**
     * @return the employerTownCity
     */
    public String getEmployerTownCity() {
        return employerTownCity;
    }

    /**
     * @param employerTownCity the employerTownCity to set
     */
    public void setEmployerTownCity(String employerTownCity) {
        this.employerTownCity = employerTownCity;
    }

    /**
     * @return the employerTownCityDesc
     */
    public String getEmployerTownCityDesc() {
        return employerTownCityDesc;
    }

    /**
     * @param employerTownCityDesc the employerTownCityDesc to set
     */
    public void setEmployerTownCityDesc(String employerTownCityDesc) {
        this.employerTownCityDesc = employerTownCityDesc;
    }

    /**
     * @return the employerStateDesc
     */
    public String getEmployerStateDesc() {
        return employerStateDesc;
    }

    /**
     * @param employerStateDesc the employerStateDesc to set
     */
    public void setEmployerStateDesc(String employerStateDesc) {
        this.employerStateDesc = employerStateDesc;
    }

    /**
     * @return the employerCountryDesc
     */
    public String getEmployerCountryDesc() {
        return employerCountryDesc;
    }

    /**
     * @param employerCountryDesc the employerCountryDesc to set
     */
    public void setEmployerCountryDesc(String employerCountryDesc) {
        this.employerCountryDesc = employerCountryDesc;
    }

    /**
     * @return the genderDesc
     */
    public String getGenderDesc() {
        return genderDesc;
    }

    /**
     * @param genderDesc the genderDesc to set
     */
    public void setGenderDesc(String genderDesc) {
        this.genderDesc = genderDesc;
    }

    /**
     * @return the businessTypeDesc
     */
    public String getBusinessTypeDesc() {
        return businessTypeDesc;
    }

    /**
     * @param businessTypeDesc the businessTypeDesc to set
     */
    public void setBusinessTypeDesc(String businessTypeDesc) {
        this.businessTypeDesc = businessTypeDesc;
    }

    /**
     * @return the employerState
     */
    public String getEmployerState() {
        return employerState;
    }

    /**
     * @param employerState the employerState to set
     */
    public void setEmployerState(String employerState) {
        this.employerState = employerState;
    }

    /**
     * @return the employerCountry
     */
    public String getEmployerCountry() {
        return employerCountry;
    }

    /**
     * @param employerCountry the employerCountry to set
     */
    public void setEmployerCountry(String employerCountry) {
        this.employerCountry = employerCountry;
    }

    /**
     * @return the employementDetails
     */
    public String getEmployementDetails() {
        return employementDetails;
    }

    /**
     * @param employementDetails the employementDetails to set
     */
    public void setEmployementDetails(String employementDetails) {
        this.employementDetails = employementDetails;
    }

    /**
     * @return the holdAddress1
     */
    public String getHoldAddress1() {
        return holdAddress1;
    }

    /**
     * @param holdAddress1 the holdAddress1 to set
     */
    public void setHoldAddress1(String holdAddress1) {
        this.holdAddress1 = holdAddress1;
    }

    /**
     * @return the holdAddress2
     */
    public String getHoldAddress2() {
        return holdAddress2;
    }

    /**
     * @param holdAddress2 the holdAddress2 to set
     */
    public void setHoldAddress2(String holdAddress2) {
        this.holdAddress2 = holdAddress2;
    }

    /**
     * @return the holdAddress3
     */
    public String getHoldAddress3() {
        return holdAddress3;
    }

    /**
     * @param holdAddress3 the holdAddress3 to set
     */
    public void setHoldAddress3(String holdAddress3) {
        this.holdAddress3 = holdAddress3;
    }

    /**
     * @return the holdZipCode
     */
    public String getHoldZipCode() {
        return holdZipCode;
    }

    /**
     * @param holdZipCode the holdZipCode to set
     */
    public void setHoldZipCode(String holdZipCode) {
        this.holdZipCode = holdZipCode;
    }

    /**
     * @return the holdTownCity
     */
    public String getHoldTownCity() {
        return holdTownCity;
    }

    /**
     * @param holdTownCity the holdTownCity to set
     */
    public void setHoldTownCity(String holdTownCity) {
        this.holdTownCity = holdTownCity;
    }

    /**
     * @return the holdState
     */
    public String getHoldState() {
        return holdState;
    }

    /**
     * @param holdState the holdState to set
     */
    public void setHoldState(String holdState) {
        this.holdState = holdState;
    }

    /**
     * @return the holdCountry
     */
    public String getHoldCountry() {
        return holdCountry;
    }

    /**
     * @param holdCountry the holdCountry to set
     */
    public void setHoldCountry(String holdCountry) {
        this.holdCountry = holdCountry;
    }

    /**
     * @return the nationality
     */
    public String getNationality() {
        return nationality;
    }

    /**
     * @param nationality the nationality to set
     */
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    /**
     * @return the nationalityDesc
     */
    public String getNationalityDesc() {
        return nationalityDesc;
    }

    /**
     * @param nationalityDesc the nationalityDesc to set
     */
    public void setNationalityDesc(String nationalityDesc) {
        this.nationalityDesc = nationalityDesc;
    }

    /**
     * @return the countryOfResidence
     */
    public String getCountryOfResidence() {
        return countryOfResidence;
    }

    /**
     * @param countryOfResidence the countryOfResidence to set
     */
    public void setCountryOfResidence(String countryOfResidence) {
        this.countryOfResidence = countryOfResidence;
    }

    /**
     * @return the countryOfResidenceDesc
     */
    public String getCountryOfResidenceDesc() {
        return countryOfResidenceDesc;
    }

    /**
     * @param countryOfResidenceDesc the countryOfResidenceDesc to set
     */
    public void setCountryOfResidenceDesc(String countryOfResidenceDesc) {
        this.countryOfResidenceDesc = countryOfResidenceDesc;
    }

    /**
     * @return the residencePhoneNo
     */
    public String getResidencePhoneNo() {
        return residencePhoneNo;
    }

    /**
     * @param residencePhoneNo the residencePhoneNo to set
     */
    public void setResidencePhoneNo(String residencePhoneNo) {
        this.residencePhoneNo = residencePhoneNo;
    }

    /**
     * @return the mobilePhone
     */
    public String getMobilePhone() {
        return mobilePhone;
    }

    /**
     * @param mobilePhone the mobilePhone to set
     */
    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    /**
     * @return the lastEdu
     */
    public String getLastEdu() {
        return lastEdu;
    }

    /**
     * @param lastEdu the lastEdu to set
     */
    public void setLastEdu(String lastEdu) {
        this.lastEdu = lastEdu;
    }

    /**
     * @return the lastEduDesc
     */
    public String getLastEduDesc() {
        return lastEduDesc;
    }

    /**
     * @param lastEduDesc the lastEduDesc to set
     */
    public void setLastEduDesc(String lastEduDesc) {
        this.lastEduDesc = lastEduDesc;
    }

    /**
     * @return the emailAddress
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * @param emailAddress the emailAddress to set
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * @return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * @return the marStat
     */
    public String getMarStat() {
        return marStat;
    }

    /**
     * @param marStat the marStat to set
     */
    public void setMarStat(String marStat) {
        this.marStat = marStat;
    }

    /**
     * @return the marStatDesc
     */
    public String getMarStatDesc() {
        return marStatDesc;
    }

    /**
     * @param marStatDesc the marStatDesc to set
     */
    public void setMarStatDesc(String marStatDesc) {
        this.marStatDesc = marStatDesc;
    }

    /**
     * @return the staff
     */
    public String getStaff() {
        return staff;
    }

    /**
     * @param staff the staff to set
     */
    public void setStaff(String staff) {
        this.staff = staff;
    }

    /**
     * @return the employeeId
     */
    public String getEmployeeId() {
        return employeeId;
    }

    /**
     * @param employeeId the employeeId to set
     */
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * @return the businessType
     */
    public Integer getBusinessType() {
        return businessType;
    }

    /**
     * @param businessType the businessType to set
     */
    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    /**
     * @return the professionCode
     */
    public Integer getProfessionCode() {
        return professionCode;
    }

    /**
     * @param professionCode the professionCode to set
     */
    public void setProfessionCode(Integer professionCode) {
        this.professionCode = professionCode;
    }

    /**
     * @return the jobTitle
     */
    public String getJobTitle() {
        return jobTitle;
    }

    /**
     * @param jobTitle the jobTitle to set
     */
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    /**
     * @return the aoBusiness
     */
    public String getAoBusiness() {
        return aoBusiness;
    }

    /**
     * @param aoBusiness the aoBusiness to set
     */
    public void setAoBusiness(String aoBusiness) {
        this.aoBusiness = aoBusiness;
    }

    /**
     * @return the aoBusinessDesc
     */
    public String getAoBusinessDesc() {
        return aoBusinessDesc;
    }

    /**
     * @param aoBusinessDesc the aoBusinessDesc to set
     */
    public void setAoBusinessDesc(String aoBusinessDesc) {
        this.aoBusinessDesc = aoBusinessDesc;
    }

    /**
     * @return the aoOperation
     */
    public String getAoOperation() {
        return aoOperation;
    }

    /**
     * @param aoOperation the aoOperation to set
     */
    public void setAoOperation(String aoOperation) {
        this.aoOperation = aoOperation;
    }

    /**
     * @return the aoOperationDesc
     */
    public String getAoOperationDesc() {
        return aoOperationDesc;
    }

    /**
     * @param aoOperationDesc the aoOperationDesc to set
     */
    public void setAoOperationDesc(String aoOperationDesc) {
        this.aoOperationDesc = aoOperationDesc;
    }

    /**
     * @return the aliasName
     */
    public String getAliasName() {
        return aliasName;
    }

    /**
     * @param aliasName the aliasName to set
     */
    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    /**
     * @return the namMother
     */
    public String getNamMother() {
        return namMother;
    }

    /**
     * @param namMother the namMother to set
     */
    public void setNamMother(String namMother) {
        this.namMother = namMother;
    }

    /**
     * @return the debtType
     */
    public String getDebtType() {
        return debtType;
    }

    /**
     * @param debtType the debtType to set
     */
    public void setDebtType(String debtType) {
        this.debtType = debtType;
    }

    /**
     * @return the debtStatus
     */
    public String getDebtStatus() {
        return debtStatus;
    }

    /**
     * @param debtStatus the debtStatus to set
     */
    public void setDebtStatus(String debtStatus) {
        this.debtStatus = debtStatus;
    }

    /**
     * @return the branchLocationCode
     */
    public String getBranchLocationCode() {
        return branchLocationCode;
    }

    /**
     * @param branchLocationCode the branchLocationCode to set
     */
    public void setBranchLocationCode(String branchLocationCode) {
        this.branchLocationCode = branchLocationCode;
    }

    /**
     * @return the districtCode
     */
    public String getDistrictCode() {
        return districtCode;
    }

    /**
     * @param districtCode the districtCode to set
     */
    public void setDistrictCode(String districtCode) {
        this.districtCode = districtCode;
    }

    /**
     * @return the employeICCode
     */
    public String getEmployeICCode() {
        return employeICCode;
    }

    /**
     * @param employeICCode the employeICCode to set
     */
    public void setEmployeICCode(String employeICCode) {
        this.employeICCode = employeICCode;
    }

    /**
     * @return the reliwthBank
     */
    public String getReliwthBank() {
        return reliwthBank;
    }

    /**
     * @param reliwthBank the reliwthBank to set
     */
    public void setReliwthBank(String reliwthBank) {
        this.reliwthBank = reliwthBank;
    }

    /**
     * @return the conWithBank
     */
    public String getConWithBank() {
        return conWithBank;
    }

    /**
     * @param conWithBank the conWithBank to set
     */
    public void setConWithBank(String conWithBank) {
        this.conWithBank = conWithBank;
    }

    /**
     * @return the residenceType
     */
    public String getResidenceType() {
        return residenceType;
    }

    /**
     * @param residenceType the residenceType to set
     */
    public void setResidenceType(String residenceType) {
        this.residenceType = residenceType;
    }

    /**
     * @return the idcardTDesc
     */
    public String getIdcardTDesc() {
        return idcardTDesc;
    }

    /**
     * @param idcardTDesc the idcardTDesc to set
     */
    public void setIdcardTDesc(String idcardTDesc) {
        this.idcardTDesc = idcardTDesc;
    }

    /**
     * @return the idcardType
     */
    public String getIdcardType() {
        return idcardType;
    }

    /**
     * @param idcardType the idcardType to set
     */
    public void setIdcardType(String idcardType) {
        this.idcardType = idcardType;
    }

    /**
     * @return the birthPlace
     */
    public String getBirthPlace() {
        return birthPlace;
    }

    /**
     * @param birthPlace the birthPlace to set
     */
    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    /**
     * @return the monthLyIncome
     */
    public Integer getMonthLyIncome() {
        return monthLyIncome;
    }

    /**
     * @param monthLyIncome the monthLyIncome to set
     */
    public void setMonthLyIncome(Integer monthLyIncome) {
        this.monthLyIncome = monthLyIncome;
    }

    /**
     * @return the monthLyIncomeDesc
     */
    public String getMonthLyIncomeDesc() {
        return monthLyIncomeDesc;
    }

    /**
     * @param monthLyIncomeDesc the monthLyIncomeDesc to set
     */
    public void setMonthLyIncomeDesc(String monthLyIncomeDesc) {
        this.monthLyIncomeDesc = monthLyIncomeDesc;
    }

    /**
     * @return the dataTransXtractFlag
     */
    public Integer getDataTransXtractFlag() {
        return dataTransXtractFlag;
    }

    /**
     * @param dataTransXtractFlag the dataTransXtractFlag to set
     */
    public void setDataTransXtractFlag(Integer dataTransXtractFlag) {
        this.dataTransXtractFlag = dataTransXtractFlag;
    }

    /**
     * @return the dataTransXtractFlagDesc
     */
    public String getDataTransXtractFlagDesc() {
        return dataTransXtractFlagDesc;
    }

    /**
     * @param dataTransXtractFlagDesc the dataTransXtractFlagDesc to set
     */
    public void setDataTransXtractFlagDesc(String dataTransXtractFlagDesc) {
        this.dataTransXtractFlagDesc = dataTransXtractFlagDesc;
    }

    /**
     * @return the communication
     */
    public String getCommunication() {
        return communication;
    }

    /**
     * @param communication the communication to set
     */
    public void setCommunication(String communication) {
        this.communication = communication;
    }

    /**
     * @return the communicationDesc
     */
    public String getCommunicationDesc() {
        return communicationDesc;
    }

    /**
     * @param communicationDesc the communicationDesc to set
     */
    public void setCommunicationDesc(String communicationDesc) {
        this.communicationDesc = communicationDesc;
    }

    /**
     * @return the homeStatus
     */
    public Integer getHomeStatus() {
        return homeStatus;
    }

    /**
     * @param homeStatus the homeStatus to set
     */
    public void setHomeStatus(Integer homeStatus) {
        this.homeStatus = homeStatus;
    }

    /**
     * @return the homeStatusDesc
     */
    public String getHomeStatusDesc() {
        return homeStatusDesc;
    }

    /**
     * @param homeStatusDesc the homeStatusDesc to set
     */
    public void setHomeStatusDesc(String homeStatusDesc) {
        this.homeStatusDesc = homeStatusDesc;
    }

    /**
     * @return the noOfDependant
     */
    public Integer getNoOfDependant() {
        return noOfDependant;
    }

    /**
     * @param noOfDependant the noOfDependant to set
     */
    public void setNoOfDependant(Integer noOfDependant) {
        this.noOfDependant = noOfDependant;
    }

    /**
     * @return the occupation
     */
    public String getOccupation() {
        return occupation;
    }

    /**
     * @param occupation the occupation to set
     */
    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    /**
     * @return the occupationDesc
     */
    public String getOccupationDesc() {
        return occupationDesc;
    }

    /**
     * @param occupationDesc the occupationDesc to set
     */
    public void setOccupationDesc(String occupationDesc) {
        this.occupationDesc = occupationDesc;
    }

    /**
     * @return the incomeTaxNo
     */
    public String getIncomeTaxNo() {
        return incomeTaxNo;
    }

    /**
     * @param incomeTaxNo the incomeTaxNo to set
     */
    public void setIncomeTaxNo(String incomeTaxNo) {
        this.incomeTaxNo = incomeTaxNo;
    }

    /**
     * @return the RegistrationNo
     */
    public String getRegistrationNo() {
        return registrationNo;
    }

    /**
     * @param RegistrationNo the RegistrationNo to set
     */
    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    /**
     * @return the RegistrationDate
     */
    public String getRegistrationDate() {
        return registrationDate;
    }

    /**
     * @param RegistrationDate the RegistrationDate to set
     */
    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    /**
     * @return the BusinessCommendate
     */
    public String getBusinessCommendate() {
        return businessCommendate;
    }

    /**
     * @param BusinessCommendate the BusinessCommendate to set
     */
    public void setBusinessCommendate(String businessCommendate) {
        this.businessCommendate = businessCommendate;
    }

    /**
     * @return the BusinessLicenseNo
     */
    public String getBusinessLicenseNo() {
        return businessLicenseNo;
    }

    /**
     * @param BusinessLicenseNo the BusinessLicenseNo to set
     */
    public void setBusinessLicenseNo(String businessLicenseNo) {
        this.businessLicenseNo = businessLicenseNo;
    }

    /**
     * @return the PlaceOfInc
     */
    public String getPlaceOfInc() {
        return placeOfInc;
    }

    /**
     * @param PlaceOfInc the PlaceOfInc to set
     */
    public void setPlaceOfInc(String placeOfInc) {
        this.placeOfInc = placeOfInc;
    }

    /**
     * @return the AdditionalField1
     */
    public String getAdditionalField1() {
        return additionalField1;
    }

    /**
     * @param AdditionalField1 the AdditionalField1 to set
     */
    public void setAdditionalField1(String additionalField1) {
        this.additionalField1 = additionalField1;
    }

    /**
     * @return the AdditionalField2
     */
    public String getAdditionalField2() {
        return additionalField2;
    }

    /**
     * @param AdditionalField2 the AdditionalField2 to set
     */
    public void setAdditionalField2(String additionalField2) {
        this.additionalField2 = additionalField2;
    }

    /**
     * @return the AdditionalField3
     */
    public String getAdditionalField3() {
        return additionalField3;
    }

    /**
     * @param AdditionalField3 the AdditionalField3 to set
     */
    public void setAdditionalField3(String additionalField3) {
        this.additionalField3 = additionalField3;
    }

    /**
     * @return the AdditionalField4
     */
    public String getAdditionalField4() {
        return additionalField4;
    }

    /**
     * @param AdditionalField4 the AdditionalField4 to set
     */
    public void setAdditionalField4(String additionalField4) {
        this.additionalField4 = additionalField4;
    }

    /**
     * @return the AdditionalField5
     */
    public String getAdditionalField5() {
        return additionalField5;
    }

    /**
     * @param AdditionalField5 the AdditionalField5 to set
     */
    public void setAdditionalField5(String additionalField5) {
        this.additionalField5 = additionalField5;
    }

    /**
     * @return the AdditionalField6
     */
    public String getAdditionalField6() {
        return additionalField6;
    }

    /**
     * @param AdditionalField6 the AdditionalField6 to set
     */
    public void setAdditionalField6(String additionalField6) {
        this.additionalField6 = additionalField6;
    }

    /**
     * @return the AdditionalField7
     */
    public String getAdditionalField7() {
        return additionalField7;
    }

    /**
     * @param AdditionalField7 the AdditionalField7 to set
     */
    public void setAdditionalField7(String additionalField7) {
        this.additionalField7 = additionalField7;
    }

    /**
     * @return the AdditionalField8
     */
    public String getAdditionalField8() {
        return additionalField8;
    }

    /**
     * @param AdditionalField8 the AdditionalField8 to set
     */
    public void setAdditionalField8(String additionalField8) {
        this.additionalField8 = additionalField8;
    }

    /**
     * @return the AdditionalField9
     */
    public String getAdditionalField9() {
        return additionalField9;
    }

    /**
     * @param AdditionalField9 the AdditionalField9 to set
     */
    public void setAdditionalField9(String additionalField9) {
        this.additionalField9 = additionalField9;
    }

    /**
     * @return the AdditionalField10
     */
    public String getAdditionalField10() {
        return additionalField10;
    }

    /**
     * @param AdditionalField10 the AdditionalField10 to set
     */
    public void setAdditionalField10(String additionalField10) {
        this.additionalField10 = additionalField10;
    }

    /**
     * @return the comments
     */
    public String getComments() {
        return comments;
    }

    /**
     * @param comments the comments to set
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    /**
     * @return the flgProcess
     */
    public String getFlgProcess() {
        return flgProcess;
    }

    /**
     * @param flgProcess the flgProcess to set
     */
    public void setFlgProcess(String flgProcess) {
        this.flgProcess = flgProcess;
    }

    /**
     * @return the typeOfCompany
     */
    public String getTypeOfCompany() {
        return typeOfCompany;
    }

    /**
     * @param typeOfCompany the typeOfCompany to set
     */
    public void setTypeOfCompany(String typeOfCompany) {
        this.typeOfCompany = typeOfCompany;
    }

    /**
     * @return the greenCard
     */
    public String getGreenCard() {
        return greenCard;
    }

    /**
     * @param greenCard the greenCard to set
     */
    public void setGreenCard(String greenCard) {
        this.greenCard = greenCard;
    }

    /**
     * @return the greenCardDesc
     */
    public String getGreenCardDesc() {
        return greenCardDesc;
    }

    /**
     * @param greenCardDesc the greenCardDesc to set
     */
    public void setGreenCardDesc(String greenCardDesc) {
        this.greenCardDesc = greenCardDesc;
    }

    /**
     * @return the alamatAS
     */
    public String getAlamatAS() {
        return alamatAS;
    }

    /**
     * @param alamatAS the alamatAS to set
     */
    public void setAlamatAS(String alamatAS) {
        this.alamatAS = alamatAS;
    }

    /**
     * @return the alamatASDesc
     */
    public String getAlamatASDesc() {
        return alamatASDesc;
    }

    /**
     * @param alamatASDesc the alamatASDesc to set
     */
    public void setAlamatASDesc(String alamatASDesc) {
        this.alamatASDesc = alamatASDesc;
    }

    /**
     * @return the badanAS
     */
    public String getBadanAS() {
        return badanAS;
    }

    /**
     * @param badanAS the badanAS to set
     */
    public void setBadanAS(String badanAS) {
        this.badanAS = badanAS;
    }

    /**
     * @return the badanASDesc
     */
    public String getBadanASDesc() {
        return badanASDesc;
    }

    /**
     * @param badanASDesc the badanASDesc to set
     */
    public void setBadanASDesc(String badanASDesc) {
        this.badanASDesc = badanASDesc;
    }

    /**
     * @return the fatca
     */
    public String getFatca() {
        return fatca;
    }

    /**
     * @param fatca the fatca to set
     */
    public void setFatca(String fatca) {
        this.fatca = fatca;
    }

    /**
     * @return the fatcaDesc
     */
    public String getFatcaDesc() {
        return fatcaDesc;
    }

    /**
     * @param fatcaDesc the fatcaDesc to set
     */
    public void setFatcaDesc(String fatcaDesc) {
        this.fatcaDesc = fatcaDesc;
    }

    /**
     * @return the tin
     */
    public String getTin() {
        return tin;
    }

    /**
     * @param tin the tin to set
     */
    public void setTin(String tin) {
        this.tin = tin;
    }

    /**
     * @return the flgIctype
     */
    public String getFlgIctype() {
        return flgIctype;
    }

    /**
     * @param flgIctype the flgIctype to set
     */
    public void setFlgIctype(String flgIctype) {
        this.flgIctype = flgIctype;
    }

    /**
     * @return the icexpiryDate
     */
    public String getIcexpiryDate() {
        return icexpiryDate;
    }

    /**
     * @param icexpiryDate the icexpiryDate to set
     */
    public void setIcexpiryDate(String icexpiryDate) {
        this.icexpiryDate = icexpiryDate;
    }

    /**
     * @return the spouseEmployer
     */
    public String getSpouseEmployer() {
        return spouseEmployer;
    }

    /**
     * @param spouseEmployer the spouseEmployer to set
     */
    public void setSpouseEmployer(String spouseEmployer) {
        this.spouseEmployer = spouseEmployer;
    }

    /**
     * @return the spouseName
     */
    public String getSpouseName() {
        return spouseName;
    }

    /**
     * @param spouseName the spouseName to set
     */
    public void setSpouseName(String spouseName) {
        this.spouseName = spouseName;
    }

    /**
     * @return the employerPhoneNo
     */
    public String getEmployerPhoneNo() {
        return employerPhoneNo;
    }

    /**
     * @param employerPhoneNo the employerPhoneNo to set
     */
    public void setEmployerPhoneNo(String employerPhoneNo) {
        this.employerPhoneNo = employerPhoneNo;
    }

    /**
     * @return the employerPhoneNoCountry
     */
    public String getEmployerPhoneNoCountry() {
        return employerPhoneNoCountry;
    }

    /**
     * @param employerPhoneNoCountry the employerPhoneNoCountry to set
     */
    public void setEmployerPhoneNoCountry(String employerPhoneNoCountry) {
        this.employerPhoneNoCountry = employerPhoneNoCountry;
    }

    /**
     * @return the employerPhoneNoArea
     */
    public String getEmployerPhoneNoArea() {
        return employerPhoneNoArea;
    }

    /**
     * @param employerPhoneNoArea the employerPhoneNoArea to set
     */
    public void setEmployerPhoneNoArea(String employerPhoneNoArea) {
        this.employerPhoneNoArea = employerPhoneNoArea;
    }

    /**
     * @return the employerPhoneNoExt
     */
    public String getEmployerPhoneNoExt() {
        return employerPhoneNoExt;
    }

    /**
     * @param employerPhoneNoExt the employerPhoneNoExt to set
     */
    public void setEmployerPhoneNoExt(String employerPhoneNoExt) {
        this.employerPhoneNoExt = employerPhoneNoExt;
    }

    /**
     * @return the residencePhoneNoContry
     */
    public String getResidencePhoneNoContry() {
        return residencePhoneNoContry;
    }

    /**
     * @param residencePhoneNoContry the residencePhoneNoContry to set
     */
    public void setResidencePhoneNoContry(String residencePhoneNoContry) {
        this.residencePhoneNoContry = residencePhoneNoContry;
    }

    /**
     * @return the residencePhoneNoArea
     */
    public String getResidencePhoneNoArea() {
        return residencePhoneNoArea;
    }

    /**
     * @param residencePhoneNoArea the residencePhoneNoArea to set
     */
    public void setResidencePhoneNoArea(String residencePhoneNoArea) {
        this.residencePhoneNoArea = residencePhoneNoArea;
    }

    /**
     * @return the residencePhoneNoExt
     */
    public String getResidencePhoneNoExt() {
        return residencePhoneNoExt;
    }

    /**
     * @param residencePhoneNoExt the residencePhoneNoExt to set
     */
    public void setResidencePhoneNoExt(String residencePhoneNoExt) {
        this.residencePhoneNoExt = residencePhoneNoExt;
    }

    /**
     * @return the professionDesc
     */
    public String getProfessionDesc() {
        return professionDesc;
    }

    /**
     * @param professionDesc the professionDesc to set
     */
    public void setProfessionDesc(String professionDesc) {
        this.professionDesc = professionDesc;
    }

    /**
     * @return the residenceFax
     */
    public String getResidenceFax() {
        return residenceFax;
    }

    /**
     * @param residenceFax the residenceFax to set
     */
    public void setResidenceFax(String residenceFax) {
        this.residenceFax = residenceFax;
    }

    /**
     * @return the holdTownCityDesc
     */
    public String getHoldTownCityDesc() {
        return holdTownCityDesc;
    }

    /**
     * @param holdTownCityDesc the holdTownCityDesc to set
     */
    public void setHoldTownCityDesc(String holdTownCityDesc) {
        this.holdTownCityDesc = holdTownCityDesc;
    }

    /**
     * @return the holdStateDesc
     */
    public String getHoldStateDesc() {
        return holdStateDesc;
    }

    /**
     * @param holdStateDesc the holdStateDesc to set
     */
    public void setHoldStateDesc(String holdStateDesc) {
        this.holdStateDesc = holdStateDesc;
    }

    /**
     * @return the holdCountryDesc
     */
    public String getHoldCountryDesc() {
        return holdCountryDesc;
    }

    /**
     * @param holdCountryDesc the holdCountryDesc to set
     */
    public void setHoldCountryDesc(String holdCountryDesc) {
        this.holdCountryDesc = holdCountryDesc;
    }

    /**
     * @return the holdPhoneNo
     */
    public String getHoldPhoneNo() {
        return holdPhoneNo;
    }

    /**
     * @param holdPhoneNo the holdPhoneNo to set
     */
    public void setHoldPhoneNo(String holdPhoneNo) {
        this.holdPhoneNo = holdPhoneNo;
    }

    /**
     * @return the holdPhoneCountry
     */
    public String getHoldPhoneCountry() {
        return holdPhoneCountry;
    }

    /**
     * @param holdPhoneCountry the holdPhoneCountry to set
     */
    public void setHoldPhoneCountry(String holdPhoneCountry) {
        this.holdPhoneCountry = holdPhoneCountry;
    }

    /**
     * @return the holdPhoneArea
     */
    public String getHoldPhoneArea() {
        return holdPhoneArea;
    }

    /**
     * @param holdPhoneArea the holdPhoneArea to set
     */
    public void setHoldPhoneArea(String holdPhoneArea) {
        this.holdPhoneArea = holdPhoneArea;
    }

    /**
     * @return the holdPhoneExt
     */
    public String getHoldPhoneExt() {
        return holdPhoneExt;
    }

    /**
     * @param holdPhoneExt the holdPhoneExt to set
     */
    public void setHoldPhoneExt(String holdPhoneExt) {
        this.holdPhoneExt = holdPhoneExt;
    }

    /**
     * @return the signType
     */
    public String getSignType() {
        return signType;
    }

    /**
     * @param signType the signType to set
     */
    public void setSignType(String signType) {
        this.signType = signType;
    }

    /**
     * @return the sumberDanaLain
     */
    public String getSumberDanaLain() {
        return sumberDanaLain;
    }

    /**
     * @param sumberDanaLain the sumberDanaLain to set
     */
    public void setSumberDanaLain(String sumberDanaLain) {
        this.sumberDanaLain = sumberDanaLain;
    }

    /**
     * @return the ownershipStat
     */
    public String getOwnershipStat() {
        return ownershipStat;
    }

    /**
     * @param ownershipStat the ownershipStat to set
     */
    public void setOwnershipStat(String ownershipStat) {
        this.ownershipStat = ownershipStat;
    }

    /**
     * @return the golPemilikLBUlama
     */
    public String getGolPemilikLBUlama() {
        return golPemilikLBUlama;
    }

    /**
     * @param golPemilikLBUlama the golPemilikLBUlama to set
     */
    public void setGolPemilikLBUlama(String golPemilikLBUlama) {
        this.golPemilikLBUlama = golPemilikLBUlama;
    }

    /**
     * @return the sandiPerushLHBULama
     */
    public String getSandiPerushLHBULama() {
        return sandiPerushLHBULama;
    }

    /**
     * @param sandiPerushLHBULama the sandiPerushLHBULama to set
     */
    public void setSandiPerushLHBULama(String sandiPerushLHBULama) {
        this.sandiPerushLHBULama = sandiPerushLHBULama;
    }

    /**
     * @return the sandiDatiII
     */
    public String getSandiDatiII() {
        return sandiDatiII;
    }

    /**
     * @param sandiDatiII the sandiDatiII to set
     */
    public void setSandiDatiII(String sandiDatiII) {
        this.sandiDatiII = sandiDatiII;
    }

    /**
     * @return the smsNotifCust
     */
    public String getSmsNotifCust() {
        return smsNotifCust;
    }

    /**
     * @param smsNotifCust the smsNotifCust to set
     */
    public void setSmsNotifCust(String smsNotifCust) {
        this.smsNotifCust = smsNotifCust;
    }

    /**
     * @return the citizenType
     */
    public String getCitizenType() {
        return citizenType;
    }

    /**
     * @param citizenType the citizenType to set
     */
    public void setCitizenType(String citizenType) {
        this.citizenType = citizenType;
    }

    /**
     * @return the channelID
     */
    public String getChannelID() {
        return channelID;
    }

    /**
     * @param channelID the channelID to set
     */
    public void setChannelID(String channelID) {
        this.channelID = channelID;
    }
	/**
	 * @return the reasonForVerification
	 */
	public String getReasonForVerification() {
		return reasonForVerification;
	}

	/**
	 * @param reasonForVerification the reasonForVerification to set
	 */
	public void setReasonForVerification(String reasonForVerification) {
		this.reasonForVerification = reasonForVerification;
	}

	/**
	 * @return the incr
	 */
	public Integer getIncr() {
		return incr;
	}

	/**
	 * @param incr the incr to set
	 */
	public void setIncr(Integer incr) {
		this.incr = incr;
	}
    /**
     * @return the codUserID
     */
    public String getCodUserID() {
        return codUserID;
    }

    /**
     * @param codUserID the codUserID to set
     */
    public void setCodUserID(String codUserID) {
        this.codUserID = codUserID;
    }

	/**
	 * @return the maillingCode
	 */
	public String getMaillingCode() {
		return maillingCode;
	}

	/**
	 * @param maillingCode the maillingCode to set
	 */
	public void setMaillingCode(String maillingCode) {
		this.maillingCode = maillingCode;
	}

    /**
     * @return the mobilePhone2
     */
    public String getMobilePhone2() {
        return mobilePhone2;
    }

    /**
     * @param mobilePhone2 the mobilePhone2 to set
     */
    public void setMobilePhone2(String mobilePhone2) {
        this.mobilePhone2 = mobilePhone2;
    }

    /**
     * @return the productBundling
     */
    public String getProductBundling() {
        return productBundling;
    }

    /**
     * @param productBundling the productBundling to set
     */
    public void setProductBundling(String productBundling) {
        this.productBundling = productBundling;
    }

    /**
     * @return the productBundlingDesc
     */
    public String getProductBundlingDesc() {
        return productBundlingDesc;
    }

    /**
     * @param productBundlingDesc the productBundlingDesc to set
     */
    public void setProductBundlingDesc(String productBundlingDesc) {
        this.productBundlingDesc = productBundlingDesc;
    }

    /**
     * @return the sandiDatiIIDesc
     */
    public String getSandiDatiIIDesc() {
        return sandiDatiIIDesc;
    }

    /**
     * @param sandiDatiIIDesc the sandiDatiIIDesc to set
     */
    public void setSandiDatiIIDesc(String sandiDatiIIDesc) {
        this.sandiDatiIIDesc = sandiDatiIIDesc;
    }

    /**
     * @return the branchlocCodes
     */
    public String getBranchlocCodes() {
        return branchlocCodes;
    }

    /**
     * @param branchlocCodes the branchlocCodes to set
     */
    public void setBranchlocCodes(String branchlocCodes) {
        this.branchlocCodes = branchlocCodes;
    }

    /**
     * @return the branchlocCodesDesc
     */
    public String getBranchlocCodesDesc() {
        return branchlocCodesDesc;
    }

    /**
     * @param branchlocCodesDesc the branchlocCodesDesc to set
     */
    public void setBranchlocCodesDesc(String branchlocCodesDesc) {
        this.branchlocCodesDesc = branchlocCodesDesc;
    }
    
}
