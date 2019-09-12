/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author v00019722
 */
public class ScreenOpening extends BaseModel implements Serializable {
    // CIF Mapping Part

    private String recType;
    private String sandiJenisRekLBU17;
    private String sandiJenisBankLain;
    private String applicationID;
    private Integer recId;
    private String batchId;
    private Integer cifNumber;
    private String category;
    private String cifType;
    private String staff;
    private Integer branchCode;
    private String branchCodeName;
    private Integer accessCode;
    private String nik; // Rep EKTP
    private String idcardType;
    private String idcardTDesc;
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
    private String permaTownCityKTP;
    private String permaState;
    private String permaStateDesc;
    private String permaStateKTP;
    private String permaCountry;
    private String permaCountryDesc;
    private String highRiskpermaCountry;
    private String highRiskpCode;
    private String mailAddrs1;
    private String mailAddrs2;
    private String mailAddrs3;
    private String maillingCode;
    private String maillingState;
    private String maillingStateDesc;
    private String maillingStateKTP;
    private String maillingCountry;
    private String maillingCountryDesc;
    private String maillingTownCity;
    private String maillingTownCityDesc;
    private String maillingTownCityKTP;
    private String maillingZipCode;
    private String highRiskmaillingCountry;
    private String highRiskmCode;
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
    private String residencePhoneNo;
    private String residencePhoneNoContry;
    private String residencePhoneNoArea;
    private String residencePhoneNoExt;
    private String residenceFax;
    private String mobilePhone;
    private String mobilePhone2;
    private String lastEdu; // rep EKTP
    private String lastEduDesc; // rep EKTP
    private String lastEduKTP; // rep EKTP
    private String religion; // Rep EKTP
    private String religionDesc;
    private String religionKTP;
    private String emailAddress;
    private String nationality;
    private String nationalityDesc;
    private String highRisknationality;
    private String highRisknationalityCode;
    private String countryOfResidence;
    private String countryOfResidenceDesc;
    private String highRiskcOR;
    private String highRiskcORCode;
    private String gender;
    private String genderDesc;
    private String marStat; // rep EKTP
    private String marStatDesc;
    private String marStatKTP;
    private String incomeTaxNo;
    private String aoBusiness;
    private String aoOperation;
    private String aoBusinessDesc;
    private String aoOperationDesc;
    private Integer businessType;
    private String businessTypeDesc;
    private String highRiskbusiness;
    private String highRiskbusinessCode;
    private String profession; // rep EKTP
    private String professionDesc;
    private String professionKTP;
    private Integer professionCode;
    private String highRiskprofession;
    private String highRiskprofessionCode;
    private String spouseName;
    private String spouseJobTitle;
    private String spouseEmployer;
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
    private String employementDetails;
    private String employerName;
    private String employerPhoneNo;
    private String employerPhoneNoCountry;
    private String employerPhoneNoArea;
    private String employerPhoneNoExt;
    private Integer lob;
    private String lobDesc;
    private String employeeId;
    private String jobTitle;
    private String debtType;
    private String debtStatus;
    private String branchLocationCode;
    private String districtCode;
    private String employeICCode;
    private String conWithBank;
    private String residenceType;
    private String residenceTDesc;
    private String signType;
    private Integer noOfDependant;
    //UDF
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
    private Integer expectedLimit;
    private String expectedLimitDesc;
    private Integer monthLyIncome;
    private String monthLyIncomeDesc;
    private String icexpiryDate;
    private Integer homeStatus;
    private String homeStatusDesc;
    private String smsNotifCust;
    private String citizenType;
    private String greenCard;
    private String greenCardDesc;
    private String alamatAS;
    private String alamatASDesc;
    private String badanAS;
    private String badanASDesc;
    private String echannel;
    private String echannelDesc;
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
    private String stateIndicator;
    private String channelId;
    private String hrFlag;
    private String tipePenduduk;
    private String echannelFlg;
    private String stateIndicatorDesc;
    private String reasonForVerification;
    private String golPemilikLBUBaru;
    private String jabatan;
    private String flgIctype;
    private String productBundling;
    private String productBundlingDesc;
    private String sandiDatiII;
    private String sandiDatiIIDesc;
    private String branchlocCodes;
    private String branchlocCodesDesc;
    // Account
    private Integer custId;
    private String acctNo;
    private String relation;
    private String regLuckyFlag;
    private Integer branchCodeCasa;
    private String branchCodeCasaDesc;
    private Integer accessCodeCasa;
    private Integer lobCasa;
    private String lobCasaDesc;
    private Integer productCode;
    private String additionalRemarks;
    private String aoBusinessCasaDesc;
    private String aoBusinessCasa;
    private String productCodeDesc;
    private String accountOpenDate;
    private String aoOperationCasa;
    private Date accountOpenDateConvertion;
    private String aoOperationCasaDesc;
    private String persetujuanDataDesc;
    private String persetujuanData;
    private String currentAccBiCode;
    private String savingAccBiCode;
    private String otherAccBiCode;
    private String tdAccBiCode;
    private String additionalField1Casa;
    private String additionalField2Casa;
    private String additionalField3Casa;
    private String additionalField4Casa;
    private String additionalField5Casa;
    private String additionalField6Casa;
    private String additionalField7Casa;
    private String additionalField8Casa;
    private String additionalField9Casa;
    private String additionalField10Casa;
    private String sandiTabLBU14Lama;
    private String sandiTabLBUBaru;
    private String sifatRekTabunganLBUBaru;
    private String acctTitle;
    private String tujuanPembukaan;
    private String tujuanPembukaanDesc;
    private String sumberDana;
    private String sumberDanaDesc;
    private String menuAccount;
    private String term;
    private String instalmentAmount;
    private String smsEmailNotifAcct;
    private String sandiJenisLbu17;
    private String cdParam;
    private String cdParamAcct;
    private String comments;
    private String commentsCasa;
    private Boolean flagCIF;
    private String flgProcess;
    private String codUserID;
    private String codSpvID;
    private String status;
    private String source;
    private String recStat;
    private String listFieldTag;
    private String screenField;
    private String extUser;
    private String procStatus;
    private Timestamp dtmProc;
    private String dtmProcConverter;
    private Timestamp dtmFinishProc;
    private String dtmFinishConverter;
    private String dtmStartConverter;
    private String recComment;
    private Timestamp dtmCreated;
    private String acctOB;
    private String acctOBDesc;
    private String remarks4;
    private String remarks5;
    private String pemilikStatus;
    private String sandiJenisTabunganLBU;
    private String makerID;
    private String serialNO;

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
     * @return the recId
     */
    public Integer getRecId() {
        return recId;
    }

    /**
     * @param recId the recId to set
     */
    public void setRecId(Integer recId) {
        this.recId = recId;
    }

    /**
     * @return the batchId
     */
    public String getBatchId() {
        return batchId;
    }

    /**
     * @param batchId the batchId to set
     */
    public void setBatchId(String batchId) {
        this.batchId = batchId;
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
     * @return the maillingZipCode
     */
    public String getMaillingZipCode() {
        return maillingZipCode;
    }

    /**
     * @param maillingZipCode the maillingZipCode to set
     */
    public void setMaillingZipCode(String maillingZipCode) {
        this.maillingZipCode = maillingZipCode;
    }

    /**
     * @return the highRiskmaillingCountry
     */
    public String getHighRiskmaillingCountry() {
        return highRiskmaillingCountry;
    }

    /**
     * @param highRiskmaillingCountry the highRiskmaillingCountry to set
     */
    public void setHighRiskmaillingCountry(String highRiskmaillingCountry) {
        this.highRiskmaillingCountry = highRiskmaillingCountry;
    }

    /**
     * @return the highRiskmCode
     */
    public String getHighRiskmCode() {
        return highRiskmCode;
    }

    /**
     * @param highRiskmCode the highRiskmCode to set
     */
    public void setHighRiskmCode(String highRiskmCode) {
        this.highRiskmCode = highRiskmCode;
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
     * @return the highRiskpermaCountry
     */
    public String getHighRiskpermaCountry() {
        return highRiskpermaCountry;
    }

    /**
     * @param highRiskpermaCountry the highRiskpermaCountry to set
     */
    public void setHighRiskpermaCountry(String highRiskpermaCountry) {
        this.highRiskpermaCountry = highRiskpermaCountry;
    }

    /**
     * @return the highRiskpCode
     */
    public String getHighRiskpCode() {
        return highRiskpCode;
    }

    /**
     * @param highRiskpCode the highRiskpCode to set
     */
    public void setHighRiskpCode(String highRiskpCode) {
        this.highRiskpCode = highRiskpCode;
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
     * @return the stateIndicator
     */
    public String getStateIndicator() {
        return stateIndicator;
    }

    /**
     * @param stateIndicator the stateIndicator to set
     */
    public void setStateIndicator(String stateIndicator) {
        this.stateIndicator = stateIndicator;
    }

    /**
     * @return the stateIndicatorDesc
     */
    public String getStateIndicatorDesc() {
        return stateIndicatorDesc;
    }

    /**
     * @param stateIndicatorDesc the stateIndicatorDesc to set
     */
    public void setStateIndicatorDesc(String stateIndicatorDesc) {
        this.stateIndicatorDesc = stateIndicatorDesc;
    }

    /**
     * @return the profession
     */
    public String getProfession() {
        return profession;
    }

    /**
     * @param profession the profession to set
     */
    public void setProfession(String profession) {
        this.profession = profession;
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
     * @return the jabatan
     */
    public String getJabatan() {
        return jabatan;
    }

    /**
     * @param jabatan the jabatan to set
     */
    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
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
     * @return the residenceTDesc
     */
    public String getResidenceTDesc() {
        return residenceTDesc;
    }

    /**
     * @param residenceTDesc the residenceTDesc to set
     */
    public void setResidenceTDesc(String residenceTDesc) {
        this.residenceTDesc = residenceTDesc;
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
     * @return the registrationNo
     */
    public String getRegistrationNo() {
        return registrationNo;
    }

    /**
     * @param registrationNo the registrationNo to set
     */
    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    /**
     * @return the registrationDate
     */
    public String getRegistrationDate() {
        return registrationDate;
    }

    /**
     * @param registrationDate the registrationDate to set
     */
    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    /**
     * @return the businessCommendate
     */
    public String getBusinessCommendate() {
        return businessCommendate;
    }

    /**
     * @param businessCommendate the businessCommendate to set
     */
    public void setBusinessCommendate(String businessCommendate) {
        this.businessCommendate = businessCommendate;
    }

    /**
     * @return the businessLicenseNo
     */
    public String getBusinessLicenseNo() {
        return businessLicenseNo;
    }

    /**
     * @param businessLicenseNo the businessLicenseNo to set
     */
    public void setBusinessLicenseNo(String businessLicenseNo) {
        this.businessLicenseNo = businessLicenseNo;
    }

    /**
     * @return the placeOfInc
     */
    public String getPlaceOfInc() {
        return placeOfInc;
    }

    /**
     * @param placeOfInc the placeOfInc to set
     */
    public void setPlaceOfInc(String placeOfInc) {
        this.placeOfInc = placeOfInc;
    }

    /**
     * @return the additionalField1
     */
    public String getAdditionalField1() {
        return additionalField1;
    }

    /**
     * @param additionalField1 the additionalField1 to set
     */
    public void setAdditionalField1(String additionalField1) {
        this.additionalField1 = additionalField1;
    }

    /**
     * @return the additionalField2
     */
    public String getAdditionalField2() {
        return additionalField2;
    }

    /**
     * @param additionalField2 the additionalField2 to set
     */
    public void setAdditionalField2(String additionalField2) {
        this.additionalField2 = additionalField2;
    }

    /**
     * @return the additionalField3
     */
    public String getAdditionalField3() {
        return additionalField3;
    }

    /**
     * @param additionalField3 the additionalField3 to set
     */
    public void setAdditionalField3(String additionalField3) {
        this.additionalField3 = additionalField3;
    }

    /**
     * @return the additionalField4
     */
    public String getAdditionalField4() {
        return additionalField4;
    }

    /**
     * @param additionalField4 the additionalField4 to set
     */
    public void setAdditionalField4(String additionalField4) {
        this.additionalField4 = additionalField4;
    }

    /**
     * @return the additionalField5
     */
    public String getAdditionalField5() {
        return additionalField5;
    }

    /**
     * @param additionalField5 the additionalField5 to set
     */
    public void setAdditionalField5(String additionalField5) {
        this.additionalField5 = additionalField5;
    }

    /**
     * @return the additionalField6
     */
    public String getAdditionalField6() {
        return additionalField6;
    }

    /**
     * @param additionalField6 the additionalField6 to set
     */
    public void setAdditionalField6(String additionalField6) {
        this.additionalField6 = additionalField6;
    }

    /**
     * @return the additionalField7
     */
    public String getAdditionalField7() {
        return additionalField7;
    }

    /**
     * @param additionalField7 the additionalField7 to set
     */
    public void setAdditionalField7(String additionalField7) {
        this.additionalField7 = additionalField7;
    }

    /**
     * @return the additionalField8
     */
    public String getAdditionalField8() {
        return additionalField8;
    }

    /**
     * @param additionalField8 the additionalField8 to set
     */
    public void setAdditionalField8(String additionalField8) {
        this.additionalField8 = additionalField8;
    }

    /**
     * @return the additionalField9
     */
    public String getAdditionalField9() {
        return additionalField9;
    }

    /**
     * @param additionalField9 the additionalField9 to set
     */
    public void setAdditionalField9(String additionalField9) {
        this.additionalField9 = additionalField9;
    }

    /**
     * @return the additionalField10
     */
    public String getAdditionalField10() {
        return additionalField10;
    }

    /**
     * @param additionalField10 the additionalField10 to set
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
     * @return the regLuckyFlag
     */
    public String getRegLuckyFlag() {
        return regLuckyFlag;
    }

    /**
     * @param regLuckyFlag the regLuckyFlag to set
     */
    public void setRegLuckyFlag(String regLuckyFlag) {
        this.regLuckyFlag = regLuckyFlag;
    }

    /**
     * @return the branchCodeCasa
     */
    public Integer getBranchCodeCasa() {
        return branchCodeCasa;
    }

    /**
     * @param branchCodeCasa the branchCodeCasa to set
     */
    public void setBranchCodeCasa(Integer branchCodeCasa) {
        this.branchCodeCasa = branchCodeCasa;
    }

    /**
     * @return the branchCodeCasaDesc
     */
    public String getBranchCodeCasaDesc() {
        return branchCodeCasaDesc;
    }

    /**
     * @param branchCodeCasaDesc the branchCodeCasaDesc to set
     */
    public void setBranchCodeCasaDesc(String branchCodeCasaDesc) {
        this.branchCodeCasaDesc = branchCodeCasaDesc;
    }

    /**
     * @return the accessCodeCasa
     */
    public Integer getAccessCodeCasa() {
        return accessCodeCasa;
    }

    /**
     * @param accessCodeCasa the accessCodeCasa to set
     */
    public void setAccessCodeCasa(Integer accessCodeCasa) {
        this.accessCodeCasa = accessCodeCasa;
    }

    /**
     * @return the productCode
     */
    public Integer getProductCode() {
        return productCode;
    }

    /**
     * @param productCode the productCode to set
     */
    public void setProductCode(Integer productCode) {
        this.productCode = productCode;
    }

    /**
     * @return the productCodeDesc
     */
    public String getProductCodeDesc() {
        return productCodeDesc;
    }

    /**
     * @param productCodeDesc the productCodeDesc to set
     */
    public void setProductCodeDesc(String productCodeDesc) {
        this.productCodeDesc = productCodeDesc;
    }

    /**
     * @return the custId
     */
    public Integer getCustId() {
        return custId;
    }

    /**
     * @param custId the custId to set
     */
    public void setCustId(Integer custId) {
        this.custId = custId;
    }

    /**
     * @return the acctNo
     */
    public String getAcctNo() {
        return acctNo;
    }

    /**
     * @param acctNo the acctNo to set
     */
    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo;
    }

    /**
     * @return the accountOpenDate
     */
    public String getAccountOpenDate() {
        return accountOpenDate;
    }

    /**
     * @param accountOpenDate the accountOpenDate to set
     */
    public void setAccountOpenDate(String accountOpenDate) {
        this.accountOpenDate = accountOpenDate;
    }

    /**
     * @return the accountOpenDateConvertion
     */
    public Date getAccountOpenDateConvertion() {
        return accountOpenDateConvertion;
    }

    /**
     * @param accountOpenDateConvertion the accountOpenDateConvertion to set
     */
    public void setAccountOpenDateConvertion(Date accountOpenDateConvertion) {
        this.accountOpenDateConvertion = accountOpenDateConvertion;
    }

    /**
     * @return the additionalRemarks
     */
    public String getAdditionalRemarks() {
        return additionalRemarks;
    }

    /**
     * @param additionalRemarks the additionalRemarks to set
     */
    public void setAdditionalRemarks(String additionalRemarks) {
        this.additionalRemarks = additionalRemarks;
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
     * @return the persetujuanDataDesc
     */
    public String getPersetujuanDataDesc() {
        return persetujuanDataDesc;
    }

    /**
     * @param persetujuanDataDesc the persetujuanDataDesc to set
     */
    public void setPersetujuanDataDesc(String persetujuanDataDesc) {
        this.persetujuanDataDesc = persetujuanDataDesc;
    }

    /**
     * @return the persetujuanData
     */
    public String getPersetujuanData() {
        return persetujuanData;
    }

    /**
     * @param persetujuanData the persetujuanData to set
     */
    public void setPersetujuanData(String persetujuanData) {
        this.persetujuanData = persetujuanData;
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
     * @return the acctTitle
     */
    public String getAcctTitle() {
        return acctTitle;
    }

    /**
     * @param acctTitle the acctTitle to set
     */
    public void setAcctTitle(String acctTitle) {
        this.acctTitle = acctTitle;
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
     * @return the channelId
     */
    public String getChannelId() {
        return channelId;
    }

    /**
     * @param channelId the channelId to set
     */
    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    /**
     * @return the hrFlag
     */
    public String getHrFlag() {
        return hrFlag;
    }

    /**
     * @param hrFlag the hrFlag to set
     */
    public void setHrFlag(String hrFlag) {
        this.hrFlag = hrFlag;
    }

    /**
     * @return the tipePenduduk
     */
    public String getTipePenduduk() {
        return tipePenduduk;
    }

    /**
     * @param tipePenduduk the tipePenduduk to set
     */
    public void setTipePenduduk(String tipePenduduk) {
        this.tipePenduduk = tipePenduduk;
    }

    /**
     * @return the echannelFlg
     */
    public String getEchannelFlg() {
        return echannelFlg;
    }

    /**
     * @param echannelFlg the echannelFlg to set
     */
    public void setEchannelFlg(String echannelFlg) {
        this.echannelFlg = echannelFlg;
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
     * @return the golPemilikLBUBaru
     */
    public String getGolPemilikLBUBaru() {
        return golPemilikLBUBaru;
    }

    /**
     * @param golPemilikLBUBaru the golPemilikLBUBaru to set
     */
    public void setGolPemilikLBUBaru(String golPemilikLBUBaru) {
        this.golPemilikLBUBaru = golPemilikLBUBaru;
    }

    /**
     * @return the lobCasa
     */
    public Integer getLobCasa() {
        return lobCasa;
    }

    /**
     * @param lobCasa the lobCasa to set
     */
    public void setLobCasa(Integer lobCasa) {
        this.lobCasa = lobCasa;
    }

    /**
     * @return the lobCasaDesc
     */
    public String getLobCasaDesc() {
        return lobCasaDesc;
    }

    /**
     * @param lobCasaDesc the lobCasaDesc to set
     */
    public void setLobCasaDesc(String lobCasaDesc) {
        this.lobCasaDesc = lobCasaDesc;
    }

    /**
     * @return the aoBusinessCasa
     */
    public String getAoBusinessCasa() {
        return aoBusinessCasa;
    }

    /**
     * @param aoBusinessCasa the aoBusinessCasa to set
     */
    public void setAoBusinessCasa(String aoBusinessCasa) {
        this.aoBusinessCasa = aoBusinessCasa;
    }

    /**
     * @return the aoBusinessCasaDesc
     */
    public String getAoBusinessCasaDesc() {
        return aoBusinessCasaDesc;
    }

    /**
     * @param aoBusinessCasaDesc the aoBusinessCasaDesc to set
     */
    public void setAoBusinessCasaDesc(String aoBusinessCasaDesc) {
        this.aoBusinessCasaDesc = aoBusinessCasaDesc;
    }

    /**
     * @return the aoOperationCasa
     */
    public String getAoOperationCasa() {
        return aoOperationCasa;
    }

    /**
     * @param aoOperationCasa the aoOperationCasa to set
     */
    public void setAoOperationCasa(String aoOperationCasa) {
        this.aoOperationCasa = aoOperationCasa;
    }

    /**
     * @return the aoOperationCasaDesc
     */
    public String getAoOperationCasaDesc() {
        return aoOperationCasaDesc;
    }

    /**
     * @param aoOperationCasaDesc the aoOperationCasaDesc to set
     */
    public void setAoOperationCasaDesc(String aoOperationCasaDesc) {
        this.aoOperationCasaDesc = aoOperationCasaDesc;
    }

    /**
     * @return the tujuanPembukaan
     */
    public String getTujuanPembukaan() {
        return tujuanPembukaan;
    }

    /**
     * @param tujuanPembukaan the tujuanPembukaan to set
     */
    public void setTujuanPembukaan(String tujuanPembukaan) {
        this.tujuanPembukaan = tujuanPembukaan;
    }

    /**
     * @return the tujuanPembukaanDesc
     */
    public String getTujuanPembukaanDesc() {
        return tujuanPembukaanDesc;
    }

    /**
     * @param tujuanPembukaanDesc the tujuanPembukaanDesc to set
     */
    public void setTujuanPembukaanDesc(String tujuanPembukaanDesc) {
        this.tujuanPembukaanDesc = tujuanPembukaanDesc;
    }

    /**
     * @return the sumberDana
     */
    public String getSumberDana() {
        return sumberDana;
    }

    /**
     * @param sumberDana the sumberDana to set
     */
    public void setSumberDana(String sumberDana) {
        this.sumberDana = sumberDana;
    }

    /**
     * @return the sumberDanaDesc
     */
    public String getSumberDanaDesc() {
        return sumberDanaDesc;
    }

    /**
     * @param sumberDanaDesc the sumberDanaDesc to set
     */
    public void setSumberDanaDesc(String sumberDanaDesc) {
        this.sumberDanaDesc = sumberDanaDesc;
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
     * @return the menuAccount
     */
    public String getMenuAccount() {
        return menuAccount;
    }

    /**
     * @param menuAccount the menuAccount to set
     */
    public void setMenuAccount(String menuAccount) {
        this.menuAccount = menuAccount;
    }

    /**
     * @return the term
     */
    public String getTerm() {
        return term;
    }

    /**
     * @param term the term to set
     */
    public void setTerm(String term) {
        this.term = term;
    }

    /**
     * @return the instalmentAmount
     */
    public String getInstalmentAmount() {
        return instalmentAmount;
    }

    /**
     * @param instalmentAmount the instalmentAmount to set
     */
    public void setInstalmentAmount(String instalmentAmount) {
        this.instalmentAmount = instalmentAmount;
    }

    /**
     * @return the highRisknationalityCode
     */
    public String getHighRisknationalityCode() {
        return highRisknationalityCode;
    }

    /**
     * @param highRisknationalityCode the highRisknationalityCode to set
     */
    public void setHighRisknationalityCode(String highRisknationalityCode) {
        this.highRisknationalityCode = highRisknationalityCode;
    }

    /**
     * @return the highRisknationality
     */
    public String getHighRisknationality() {
        return highRisknationality;
    }

    /**
     * @param highRisknationality the highRisknationality to set
     */
    public void setHighRisknationality(String highRisknationality) {
        this.highRisknationality = highRisknationality;
    }

    /**
     * @return the highRiskcOR
     */
    public String getHighRiskcOR() {
        return highRiskcOR;
    }

    /**
     * @param highRiskcOR the highRiskcOR to set
     */
    public void setHighRiskcOR(String highRiskcOR) {
        this.highRiskcOR = highRiskcOR;
    }

    /**
     * @return the highRiskcORCode
     */
    public String getHighRiskcORCode() {
        return highRiskcORCode;
    }

    /**
     * @param highRiskcORCode the highRiskcORCode to set
     */
    public void setHighRiskcORCode(String highRiskcORCode) {
        this.highRiskcORCode = highRiskcORCode;
    }

    /**
     * @return the echannel
     */
    public String getEchannel() {
        return echannel;
    }

    /**
     * @param echannel the echannel to set
     */
    public void setEchannel(String echannel) {
        this.echannel = echannel;
    }

    /**
     * @return the echannelDesc
     */
    public String getEchannelDesc() {
        return echannelDesc;
    }

    /**
     * @param echannelDesc the echannelDesc to set
     */
    public void setEchannelDesc(String echannelDesc) {
        this.echannelDesc = echannelDesc;
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
     * @return the highRiskbusiness
     */
    public String getHighRiskbusiness() {
        return highRiskbusiness;
    }

    /**
     * @param highRiskbusiness the highRiskbusiness to set
     */
    public void setHighRiskbusiness(String highRiskbusiness) {
        this.highRiskbusiness = highRiskbusiness;
    }

    /**
     * @return the highRiskbusinessCode
     */
    public String getHighRiskbusinessCode() {
        return highRiskbusinessCode;
    }

    /**
     * @param highRiskbusinessCode the highRiskbusinessCode to set
     */
    public void setHighRiskbusinessCode(String highRiskbusinessCode) {
        this.highRiskbusinessCode = highRiskbusinessCode;
    }

    /**
     * @return the highRiskprofession
     */
    public String getHighRiskprofession() {
        return highRiskprofession;
    }

    /**
     * @param highRiskprofession the highRiskprofession to set
     */
    public void setHighRiskprofession(String highRiskprofession) {
        this.highRiskprofession = highRiskprofession;
    }

    /**
     * @return the highRiskprofessionCode
     */
    public String getHighRiskprofessionCode() {
        return highRiskprofessionCode;
    }

    /**
     * @param highRiskprofessionCode the highRiskprofessionCode to set
     */
    public void setHighRiskprofessionCode(String highRiskprofessionCode) {
        this.highRiskprofessionCode = highRiskprofessionCode;
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
     * @return the religionKTP
     */
    public String getReligionKTP() {
        return religionKTP;
    }

    /**
     * @param religionKTP the religionKTP to set
     */
    public void setReligionKTP(String religionKTP) {
        this.religionKTP = religionKTP;
    }

    /**
     * @return the maillingStateKTP
     */
    public String getMaillingStateKTP() {
        return maillingStateKTP;
    }

    /**
     * @param maillingStateKTP the maillingStateKTP to set
     */
    public void setMaillingStateKTP(String maillingStateKTP) {
        this.maillingStateKTP = maillingStateKTP;
    }

    /**
     * @return the maillingTownCityKTP
     */
    public String getMaillingTownCityKTP() {
        return maillingTownCityKTP;
    }

    /**
     * @param maillingTownCityKTP the maillingTownCityKTP to set
     */
    public void setMaillingTownCityKTP(String maillingTownCityKTP) {
        this.maillingTownCityKTP = maillingTownCityKTP;
    }

    /**
     * @return the permaTownCityKTP
     */
    public String getPermaTownCityKTP() {
        return permaTownCityKTP;
    }

    /**
     * @param permaTownCityKTP the permaTownCityKTP to set
     */
    public void setPermaTownCityKTP(String permaTownCityKTP) {
        this.permaTownCityKTP = permaTownCityKTP;
    }

    /**
     * @return the permaStateKTP
     */
    public String getPermaStateKTP() {
        return permaStateKTP;
    }

    /**
     * @param permaStateKTP the permaStateKTP to set
     */
    public void setPermaStateKTP(String permaStateKTP) {
        this.permaStateKTP = permaStateKTP;
    }

    /**
     * @return the lastEduKTP
     */
    public String getLastEduKTP() {
        return lastEduKTP;
    }

    /**
     * @param lastEduKTP the lastEduKTP to set
     */
    public void setLastEduKTP(String lastEduKTP) {
        this.lastEduKTP = lastEduKTP;
    }

    /**
     * @return the marStatKTP
     */
    public String getMarStatKTP() {
        return marStatKTP;
    }

    /**
     * @param marStatKTP the marStatKTP to set
     */
    public void setMarStatKTP(String marStatKTP) {
        this.marStatKTP = marStatKTP;
    }

    /**
     * @return the professionKTP
     */
    public String getProfessionKTP() {
        return professionKTP;
    }

    /**
     * @param professionKTP the professionKTP to set
     */
    public void setProfessionKTP(String professionKTP) {
        this.professionKTP = professionKTP;
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
     * @return the sandiTabLBU14Lama
     */
    public String getSandiTabLBU14Lama() {
        return sandiTabLBU14Lama;
    }

    /**
     * @param sandiTabLBU14Lama the sandiTabLBU14Lama to set
     */
    public void setSandiTabLBU14Lama(String sandiTabLBU14Lama) {
        this.sandiTabLBU14Lama = sandiTabLBU14Lama;
    }

    /**
     * @return the sandiTabLBUBaru
     */
    public String getSandiTabLBUBaru() {
        return sandiTabLBUBaru;
    }

    /**
     * @param sandiTabLBUBaru the sandiTabLBUBaru to set
     */
    public void setSandiTabLBUBaru(String sandiTabLBUBaru) {
        this.sandiTabLBUBaru = sandiTabLBUBaru;
    }

    /**
     * @return the sifatRekTabunganLBUBaru
     */
    public String getSifatRekTabunganLBUBaru() {
        return sifatRekTabunganLBUBaru;
    }

    /**
     * @param sifatRekTabunganLBUBaru the sifatRekTabunganLBUBaru to set
     */
    public void setSifatRekTabunganLBUBaru(String sifatRekTabunganLBUBaru) {
        this.sifatRekTabunganLBUBaru = sifatRekTabunganLBUBaru;
    }

    /**
     * @return the smsEmailNotifAcct
     */
    public String getSmsEmailNotifAcct() {
        return smsEmailNotifAcct;
    }

    /**
     * @param smsEmailNotifAcct the smsEmailNotifAcct to set
     */
    public void setSmsEmailNotifAcct(String smsEmailNotifAcct) {
        this.smsEmailNotifAcct = smsEmailNotifAcct;
    }

    /**
     * @return the sandiJenisLbu17
     */
    public String getSandiJenisLbu17() {
        return sandiJenisLbu17;
    }

    /**
     * @param sandiJenisLbu17 the sandiJenisLbu17 to set
     */
    public void setSandiJenisLbu17(String sandiJenisLbu17) {
        this.sandiJenisLbu17 = sandiJenisLbu17;
    }

    /**
     * @return the cdParam
     */
    public String getCdParam() {
        return cdParam;
    }

    /**
     * @param cdParam the cdParam to set
     */
    public void setCdParam(String cdParam) {
        this.cdParam = cdParam;
    }

    /**
     * @return the flagCIF
     */
    public Boolean getFlagCIF() {
        return flagCIF;
    }

    /**
     * @param flagCIF the flagCIF to set
     */
    public void setFlagCIF(Boolean flagCIF) {
        this.flagCIF = flagCIF;
    }

    /**
     * @return the cdParamAcct
     */
    public String getCdParamAcct() {
        return cdParamAcct;
    }

    /**
     * @param cdParamAcct the cdParamAcct to set
     */
    public void setCdParamAcct(String cdParamAcct) {
        this.cdParamAcct = cdParamAcct;
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
     * @return the codSpvID
     */
    public String getCodSpvID() {
        return codSpvID;
    }

    /**
     * @param codSpvID the codSpvID to set
     */
    public void setCodSpvID(String codSpvID) {
        this.codSpvID = codSpvID;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the source
     */
    public String getSource() {
        return source;
    }

    /**
     * @param source the source to set
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * @return the recStat
     */
    public String getRecStat() {
        return recStat;
    }

    /**
     * @param recStat the recStat to set
     */
    public void setRecStat(String recStat) {
        this.recStat = recStat;
    }

    /**
     * @return the listFieldTag
     */
    public String getListFieldTag() {
        return listFieldTag;
    }

    /**
     * @param listFieldTag the listFieldTag to set
     */
    public void setListFieldTag(String listFieldTag) {
        this.listFieldTag = listFieldTag;
    }

    /**
     * @return the screenField
     */
    public String getScreenField() {
        return screenField;
    }

    /**
     * @param screenField the screenField to set
     */
    public void setScreenField(String screenField) {
        this.screenField = screenField;
    }

    /**
     * @return the sandiJenisRekLBU17
     */
    public String getSandiJenisRekLBU17() {
        return sandiJenisRekLBU17;
    }

    /**
     * @param sandiJenisRekLBU17 the sandiJenisRekLBU17 to set
     */
    public void setSandiJenisRekLBU17(String sandiJenisRekLBU17) {
        this.sandiJenisRekLBU17 = sandiJenisRekLBU17;
    }

    /**
     * @return the sandiJenisBankLain
     */
    public String getSandiJenisBankLain() {
        return sandiJenisBankLain;
    }

    /**
     * @param sandiJenisBankLain the sandiJenisBankLain to set
     */
    public void setSandiJenisBankLain(String sandiJenisBankLain) {
        this.sandiJenisBankLain = sandiJenisBankLain;
    }

    /**
     * @return the extUser
     */
    public String getExtUser() {
        return extUser;
    }

    /**
     * @param extUser the extUser to set
     */
    public void setExtUser(String extUser) {
        this.extUser = extUser;
    }

    /**
     * @return the procStatus
     */
    public String getProcStatus() {
        return procStatus;
    }

    /**
     * @param procStatus the procStatus to set
     */
    public void setProcStatus(String procStatus) {
        this.procStatus = procStatus;
    }

    /**
     * @return the dtmProc
     */
    public Timestamp getDtmProc() {
        return dtmProc;
    }

    /**
     * @param dtmProc the dtmProc to set
     */
    public void setDtmProc(Timestamp dtmProc) {
        this.dtmProc = dtmProc;
        setDtmProcConverter(null);
    }

    /**
     * @return the dtmFinishProc
     */
    public Timestamp getDtmFinishProc() {
        return dtmFinishProc;
    }

    /**
     * @param dtmFinishProc the dtmFinishProc to set
     */
    public void setDtmFinishProc(Timestamp dtmFinishProc) {
        this.dtmFinishProc = dtmFinishProc;
        setDtmFinishConverter(null);
    }

    /**
     * @return the commentsCasa
     */
    public String getCommentsCasa() {
        return commentsCasa;
    }

    /**
     * @param commentsCasa the commentsCasa to set
     */
    public void setCommentsCasa(String commentsCasa) {
        this.commentsCasa = commentsCasa;
    }

    /**
     * @return the recType
     */
    public String getRecType() {
        return recType;
    }

    /**
     * @param recType the recType to set
     */
    public void setRecType(String recType) {
        this.recType = recType;
    }

    /**
     * @return the recComment
     */
    public String getRecComment() {
        return recComment;
    }

    /**
     * @param recComment the recComment to set
     */
    public void setRecComment(String recComment) {
        this.recComment = recComment;
    }

    /**
     * @return the dtmProcConverter
     */
    public String getDtmProcConverter() {
        return dtmProcConverter;
    }

    /**
     * @param dtmProcConverter the dtmProcConverter to set
     */
    public void setDtmProcConverter(String dtmProcConverter) {
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yy HH.mm.ss.SSS");
        if (this.dtmProc != null) {
            Date dtmPrcConv = this.dtmProc;

            if (dtmProcConverter == null) {
                this.dtmProcConverter = df.format(dtmPrcConv);
                //this.dtmProc = null;
            } else {
                this.dtmProcConverter = dtmProcConverter;
            }
        } else {
            this.dtmProcConverter = dtmProcConverter;
        }
    }

    /**
     * @return the dtmFinishConverter
     */
    public String getDtmFinishConverter() {
        return dtmFinishConverter;
    }

    /**
     * @param dtmFinishConverter the dtmFinishConverter to set
     */
    public void setDtmFinishConverter(String dtmFinishConverter) {
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yy HH.mm.ss.SSS");
        if (this.dtmFinishProc != null) {
            Date dtmFinConv = this.dtmFinishProc;
            if (dtmFinishConverter == null) {
                this.dtmFinishConverter = df.format(dtmFinConv);
            } else {
                this.dtmFinishConverter = dtmFinishConverter;
            }
        } else {
            this.dtmFinishConverter = dtmFinishConverter;
        }
    }

    @Override
    public void setDtmCreated(Timestamp dtmCreated) {
        this.dtmCreated = dtmCreated;
        this.setDtmStartConverter(null);
    }

    /**
     * @return the dtmStartConverter
     */
    public String getDtmStartConverter() {
        return dtmStartConverter;
    }

    /**
     * @param dtmStartConverter the dtmStartConverter to set
     */
    public void setDtmStartConverter(String dtmStartConverter) {
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yy HH.mm.ss.SSS");
        if (getDtmCreated() != null) {
            Date dtmStartConv = getDtmCreated();
            if (dtmStartConverter == null) {
                this.dtmStartConverter = df.format(dtmStartConv);
            } else {
                this.dtmStartConverter = dtmStartConverter;
            }
        } else {
            this.dtmStartConverter = dtmStartConverter;
        }
    }

    /**
     * @return the dtmCreated
     */
    @Override
    public Timestamp getDtmCreated() {
        return dtmCreated;
    }

    public String getProductBundlingDesc() {
        return productBundlingDesc;
    }

    public void setProductBundlingDesc(String productBundlingDesc) {
        this.productBundlingDesc = productBundlingDesc;
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

    /**
     * @return the acctOB
     */
    public String getAcctOB() {
        return acctOB;
    }

    /**
     * @param acctOB the acctOB to set
     */
    public void setAcctOB(String acctOB) {
        this.acctOB = acctOB;
    }

    /**
     * @return the acctOBDesc
     */
    public String getAcctOBDesc() {
        return acctOBDesc;
    }

    /**
     * @param acctOBDesc the acctOBDesc to set
     */
    public void setAcctOBDesc(String acctOBDesc) {
        this.acctOBDesc = acctOBDesc;
    }

    /**
     * @return the remarks4
     */
    public String getRemarks4() {
        return remarks4;
    }

    /**
     * @param remarks4 the remarks4 to set
     */
    public void setRemarks4(String remarks4) {
        this.remarks4 = remarks4;
    }

    /**
     * @return the remarks5
     */
    public String getRemarks5() {
        return remarks5;
    }

    /**
     * @param remarks5 the remarks5 to set
     */
    public void setRemarks5(String remarks5) {
        this.remarks5 = remarks5;
    }

    /**
     * @return the remarks5
     */
    public String getSerialNO() {
        return serialNO;
    }

    /**
     * @param remarks5 the remarks5 to set
     */
    public void setSerialNO(String serialNO) {
        this.serialNO = serialNO;
    }

    /**
     * @return the remarks5
     */
    public String getMakerID() {
        return makerID;
    }

    /**
     * @param remarks5 the remarks5 to set
     */
    public void setMakerID(String makerID) {
        this.makerID = makerID;
    }

    /**
     * @return the pemilikStatus
     */
    public String getPemilikStatus() {
        return pemilikStatus;
    }

    /**
     * @param pemilikStatus the pemilikStatus to set
     */
    public void setPemilikStatus(String pemilikStatus) {
        this.pemilikStatus = pemilikStatus;
    }

    /**
     * @return the sandiJenisTabunganLBU
     */
    public String getSandiJenisTabunganLBU() {
        return sandiJenisTabunganLBU;
    }

    /**
     * @param sandiJenisTabunganLBU the sandiJenisTabunganLBU to set
     */
    public void setSandiJenisTabunganLBU(String sandiJenisTabunganLBU) {
        this.sandiJenisTabunganLBU = sandiJenisTabunganLBU;
    }

    public String getCurrentAccBiCode() {
        return currentAccBiCode;
    }

    public void setCurrentAccBiCode(String currentAccBiCode) {
        this.currentAccBiCode = currentAccBiCode;
    }

    public String getSavingAccBiCode() {
        return savingAccBiCode;
    }

    public void setSavingAccBiCode(String savingAccBiCode) {
        this.savingAccBiCode = savingAccBiCode;
    }

    public String getOtherAccBiCode() {
        return otherAccBiCode;
    }

    public void setOtherAccBiCode(String otherAccBiCode) {
        this.otherAccBiCode = otherAccBiCode;
    }

    public String getTdAccBiCode() {
        return tdAccBiCode;
    }

    public void setTdAccBiCode(String tdAccBiCode) {
        this.tdAccBiCode = tdAccBiCode;
    }

    public String getAdditionalField1Casa() {
        return additionalField1Casa;
    }

    public void setAdditionalField1Casa(String additionalField1Casa) {
        this.additionalField1Casa = additionalField1Casa;
    }

    public String getAdditionalField2Casa() {
        return additionalField2Casa;
    }

    public void setAdditionalField2Casa(String additionalField2Casa) {
        this.additionalField2Casa = additionalField2Casa;
    }

    public String getAdditionalField3Casa() {
        return additionalField3Casa;
    }

    public void setAdditionalField3Casa(String additionalField3Casa) {
        this.additionalField3Casa = additionalField3Casa;
    }

    public String getAdditionalField4Casa() {
        return additionalField4Casa;
    }

    public void setAdditionalField4Casa(String additionalField4Casa) {
        this.additionalField4Casa = additionalField4Casa;
    }

    public String getAdditionalField5Casa() {
        return additionalField5Casa;
    }

    public void setAdditionalField5Casa(String additionalField5Casa) {
        this.additionalField5Casa = additionalField5Casa;
    }

    public String getAdditionalField6Casa() {
        return additionalField6Casa;
    }

    public void setAdditionalField6Casa(String additionalField6Casa) {
        this.additionalField6Casa = additionalField6Casa;
    }

    public String getAdditionalField7Casa() {
        return additionalField7Casa;
    }

    public void setAdditionalField7Casa(String additionalField7Casa) {
        this.additionalField7Casa = additionalField7Casa;
    }

    public String getAdditionalField8Casa() {
        return additionalField8Casa;
    }

    public void setAdditionalField8Casa(String additionalField8Casa) {
        this.additionalField8Casa = additionalField8Casa;
    }

    public String getAdditionalField9Casa() {
        return additionalField9Casa;
    }

    public void setAdditionalField9Casa(String additionalField9Casa) {
        this.additionalField9Casa = additionalField9Casa;
    }

    public String getAdditionalField10Casa() {
        return additionalField10Casa;
    }

    public void setAdditionalField10Casa(String additionalField10Casa) {
        this.additionalField10Casa = additionalField10Casa;
    }
}
