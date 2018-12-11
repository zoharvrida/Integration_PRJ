/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

import bdsm.model.BaseModel;
import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author v00020841
 */
public class TmpMaintenanceCif extends BaseModel{
    
    private TmpMaintenanceCifPK compositeId;
    //CIM09
    private String salutation;
    private String incomeTaxNo;
    private String employeeId;
    private Integer reasonVerifed;
    private String address1MA;
    private String address2MA;
    private String address3MA;
    private String cityMA;
    private String stateMA;
    private String countryMA;
    private String postalCodeMA;
    private String telexMA;
    private String phoneOfficeMA;
    private String phoneResMA;
    private String faxNoMA;
    private String emailInternetRA; 
    private String emailMobileRA; 
    private String flgStatus;
    private String statusReason;
    private String codLastMntMakerid;
    private String codLastMntChkrid;
    private Timestamp datLastMnt;
    private String statusIC;
    private String signTypIC;
    private String ethnicIC;
    private String countryResidenIC;
    private String sexIC;
    private Integer profesCodeIC;
    private String designationIC;
    private String nationalityIC;
    private String educationIC;
    private Integer maritalStatus; 
    private Integer noOfSpouseIC; 
    private Integer businesTypeCC;
    private String typCompanyCC;
    private Date datRegisteredCC;
    private String registrationNoCC; 
    private String signatoresName1CC;
    private String signatoresName2CC;
    private String signatoresName3CC;
    private String signatoresName4CC;
    private String signatoresName5CC;
    private String signatoresDesignation1CC;
    private String signatoresDesignation2CC;
    private String signatoresDesignation3CC;
    private String signatoresDesignation4CC;
    private String signatoresDesignation5CC;
    private String dirPartner1CC;
    private String dirPartner2CC;
    private String dirPartner3CC;
    private String dirPartner4CC;
    private String dirPartner5CC;
    //CIM11
    private Integer tenurED;
    private String nameED;
    private String address1ED;
    private String address2ED;
    private String address3ED;
    private String cityED;
    private String stateED;
    private String countryED;
    private String postalCodeED;
    private String mobileED;        
    private String emailED;
    private String phoneED;
    private String telexNoED;       
    private String faxNoED;       
    private Integer numOfYearsED;
    private Integer retriementAgeED;
    private String parentNamePD;       
    private String parenticPD; 
    private Integer occupationPD;
    private String designationPD;
    private Date parentBODPD;
    private String spouseDetailPD;
    private String address1PD;
    private String address2PD;
    private String address3PD;
    private String phoneNoPD;
    //CIM17
    private String typeCompCO;
    private String placeInCorpCO;
    private Integer noPartnerCO;
    private String parentCompanyCO;
    private String apexHoldingCompany;
    private String contactPersonCO;
    private String contacPersonDesgnCO;
    private String internalCreditRatingCO;
    private String businesLicenseNoCO;
    private Date businesCommencementDateCO;
    private Date businesLicenseExpiryDateCO;
    private String bankName1CO;
    private String bankName2CO;
    private String natureOfBusinesCO;
    private String majorShareHolder1;
    private String majorShareHolder2;
    private String majorShareHolder3;
    private String majorShareHolder4;
    private String majorShareHolder5;
    private String majorShareHolder6;
    private String majorShareHolder7;
    private String majorShareHolder8;
    private String majorShareHolder9;
    private String majorShareHolder10;
    private String relationShip1;
    private String relationShip2;
    private String relationShip3;
    private String relationShip4;
    private String relationShip5;
    private String relationShip6;
    private String relationShip7;
    private String relationShip8;
    private String relationShip9;
    private String relationShip10;
    

    /**
     * 
     * @return
     */
    public TmpMaintenanceCifPK getCompositeId() {
        return compositeId;
    }

    /**
     * 
     * @param compositeId
     */
    public void setCompositeId(TmpMaintenanceCifPK compositeId) {
        this.compositeId = compositeId;
    }

    /**
     * 
     * @return
     */
    public String getSalutation() {
        return salutation;
    }

    /**
     * 
     * @param salutation
     */
    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    /**
     * 
     * @return
     */
    public String getIncomeTaxNo() {
        return incomeTaxNo;
    }

    /**
     * 
     * @param incomeTaxNo
     */
    public void setIncomeTaxNo(String incomeTaxNo) {
        this.incomeTaxNo = incomeTaxNo;
    }

    /**
     * 
     * @return
     */
    public String getEmployeeId() {
        return employeeId;
    }

    /**
     * 
     * @param employeeId
     */
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * 
     * @return
     */
    public Integer getReasonVerifed() {
        return reasonVerifed;
    }

    /**
     * 
     * @param reasonVerifed
     */
    public void setReasonVerifed(Integer reasonVerifed) {
        this.reasonVerifed = reasonVerifed;
    }

    /**
     * 
     * @return
     */
    public String getAddress1MA() {
        return address1MA;
    }

    /**
     * 
     * @param address1MA
     */
    public void setAddress1MA(String address1MA) {
        this.address1MA = address1MA;
    }

    /**
     * 
     * @return
     */
    public String getAddress2MA() {
        return address2MA;
    }

    /**
     * 
     * @param address2MA
     */
    public void setAddress2MA(String address2MA) {
        this.address2MA = address2MA;
    }

    /**
     * 
     * @return
     */
    public String getAddress3MA() {
        return address3MA;
    }

    /**
     * 
     * @param address3MA
     */
    public void setAddress3MA(String address3MA) {
        this.address3MA = address3MA;
    }

    /**
     * 
     * @return
     */
    public String getCityMA() {
        return cityMA;
    }

    /**
     * 
     * @param cityMA
     */
    public void setCityMA(String cityMA) {
        this.cityMA = cityMA;
    }

    /**
     * 
     * @return
     */
    public String getStateMA() {
        return stateMA;
    }

    /**
     * 
     * @param stateMA
     */
    public void setStateMA(String stateMA) {
        this.stateMA = stateMA;
    }

    /**
     * 
     * @return
     */
    public String getCountryMA() {
        return countryMA;
    }

    /**
     * 
     * @param countryMA
     */
    public void setCountryMA(String countryMA) {
        this.countryMA = countryMA;
    }

    /**
     * 
     * @return
     */
    public String getPostalCodeMA() {
        return postalCodeMA;
    }

    /**
     * 
     * @param postalCodeMA
     */
    public void setPostalCodeMA(String postalCodeMA) {
        this.postalCodeMA = postalCodeMA;
    }

    /**
     * 
     * @return
     */
    public String getTelexMA() {
        return telexMA;
    }

    /**
     * 
     * @param telexMA
     */
    public void setTelexMA(String telexMA) {
        this.telexMA = telexMA;
    }

    /**
     * 
     * @return
     */
    public String getPhoneOfficeMA() {
        return phoneOfficeMA;
    }

    /**
     * 
     * @param phoneOfficeMA
     */
    public void setPhoneOfficeMA(String phoneOfficeMA) {
        this.phoneOfficeMA = phoneOfficeMA;
    }

    /**
     * 
     * @return
     */
    public String getPhoneResMA() {
        return phoneResMA;
    }

    /**
     * 
     * @param phoneResMA
     */
    public void setPhoneResMA(String phoneResMA) {
        this.phoneResMA = phoneResMA;
    }

    /**
     * 
     * @return
     */
    public String getFaxNoMA() {
        return faxNoMA;
    }

    /**
     * 
     * @param faxNoMA
     */
    public void setFaxNoMA(String faxNoMA) {
        this.faxNoMA = faxNoMA;
    }

    /**
     * 
     * @return
     */
    public String getFlgStatus() {
        return flgStatus;
    }

    /**
     * 
     * @param flgStatus
     */
    public void setFlgStatus(String flgStatus) {
        this.flgStatus = flgStatus;
    }

    /**
     * 
     * @return
     */
    public String getStatusReason() {
        return statusReason;
    }

    /**
     * 
     * @param statusReason
     */
    public void setStatusReason(String statusReason) {
        this.statusReason = statusReason;
    }

    /**
     * 
     * @return
     */
    public String getCodLastMntMakerid() {
        return codLastMntMakerid;
    }

    /**
     * 
     * @param codLastMntMakerid
     */
    public void setCodLastMntMakerid(String codLastMntMakerid) {
        this.codLastMntMakerid = codLastMntMakerid;
    }

    /**
     * 
     * @return
     */
    public String getCodLastMntChkrid() {
        return codLastMntChkrid;
    }

    /**
     * 
     * @param codLastMntChkrid
     */
    public void setCodLastMntChkrid(String codLastMntChkrid) {
        this.codLastMntChkrid = codLastMntChkrid;
    }

    /**
     * 
     * @return
     */
    public Timestamp getDatLastMnt() {
        return datLastMnt;
    }

    /**
     * 
     * @param datLastMnt
     */
    public void setDatLastMnt(Timestamp datLastMnt) {
        this.datLastMnt = datLastMnt;
    }
    
    /**
     * 
     * @return
     */
    public String getSignTypIC() {
        return signTypIC;
    }

    /**
     * 
     * @param signTypIC
     */
    public void setSignTypIC(String signTypIC) {
        this.signTypIC = signTypIC;
    }

    /**
     * 
     * @return
     */
    public String getEthnicIC() {
        return ethnicIC;
    }

    /**
     * 
     * @param ethnicIC
     */
    public void setEthnicIC(String ethnicIC) {
        this.ethnicIC = ethnicIC;
    }

    /**
     * 
     * @return
     */
    public String getCountryResidenIC() {
        return countryResidenIC;
    }

    /**
     * 
     * @param countryResidenIC
     */
    public void setCountryResidenIC(String countryResidenIC) {
        this.countryResidenIC = countryResidenIC;
    }

    /**
     * 
     * @return
     */
    public String getSexIC() {
        return sexIC;
    }

    /**
     * 
     * @param sexIC
     */
    public void setSexIC(String sexIC) {
        this.sexIC = sexIC;
    }

    /**
     * 
     * @return
     */
    public Integer getProfesCodeIC() {
        return profesCodeIC;
    }

    /**
     * 
     * @param profesCodeIC
     */
    public void setProfesCodeIC(Integer profesCodeIC) {
        this.profesCodeIC = profesCodeIC;
    }

    /**
     * 
     * @return
     */
    public String getNationalityIC() {
        return nationalityIC;
    }

    /**
     * 
     * @param nationalityIC
     */
    public void setNationalityIC(String nationalityIC) {
        this.nationalityIC = nationalityIC;
    }

    /**
     * 
     * @return
     */
    public String getEducationIC() {
        return educationIC;
    }

    /**
     * 
     * @param educationIC
     */
    public void setEducationIC(String educationIC) {
        this.educationIC = educationIC;
    }

    /**
     * 
     * @return
     */
    public Integer getMaritalStatus() {
        return maritalStatus;
    }

    /**
     * 
     * @param maritalStatus
     */
    public void setMaritalStatus(Integer maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    /**
     * 
     * @return
     */
    public Integer getNoOfSpouseIC() {
        return noOfSpouseIC;
    }

    /**
     * 
     * @param noOfSpouseIC
     */
    public void setNoOfSpouseIC(Integer noOfSpouseIC) {
        this.noOfSpouseIC = noOfSpouseIC;
    }

    /**
     * 
     * @return
     */
    public Integer getBusinesTypeCC() {
        return businesTypeCC;
    }

    /**
     * 
     * @param businesTypeCC
     */
    public void setBusinesTypeCC(Integer businesTypeCC) {
        this.businesTypeCC = businesTypeCC;
    }

    /**
     * 
     * @return
     */
    public String getTypCompanyCC() {
        return typCompanyCC;
    }

    /**
     * 
     * @param typCompanyCC
     */
    public void setTypCompanyCC(String typCompanyCC) {
        this.typCompanyCC = typCompanyCC;
    }

    /**
     * 
     * @return
     */
    public Date getDatRegisteredCC() {
        return datRegisteredCC;
    }

    /**
     * 
     * @param datRegisteredCC
     */
    public void setDatRegisteredCC(Date datRegisteredCC) {
        this.datRegisteredCC = datRegisteredCC;
    }

    /**
     * 
     * @return
     */
    public String getRegistrationNoCC() {
        return registrationNoCC;
    }

    /**
     * 
     * @param registrationNoCC
     */
    public void setRegistrationNoCC(String registrationNoCC) {
        this.registrationNoCC = registrationNoCC;
    }

    /**
     * 
     * @return
     */
    public String getSignatoresName1CC() {
        return signatoresName1CC;
    }

    /**
     * 
     * @param signatoresName1CC
     */
    public void setSignatoresName1CC(String signatoresName1CC) {
        this.signatoresName1CC = signatoresName1CC;
    }

    /**
     * 
     * @return
     */
    public String getSignatoresName2CC() {
        return signatoresName2CC;
    }

    /**
     * 
     * @param signatoresName2CC
     */
    public void setSignatoresName2CC(String signatoresName2CC) {
        this.signatoresName2CC = signatoresName2CC;
    }

    /**
     * 
     * @return
     */
    public String getSignatoresName3CC() {
        return signatoresName3CC;
    }

    /**
     * 
     * @param signatoresName3CC
     */
    public void setSignatoresName3CC(String signatoresName3CC) {
        this.signatoresName3CC = signatoresName3CC;
    }

    /**
     * 
     * @return
     */
    public String getSignatoresName4CC() {
        return signatoresName4CC;
    }

    /**
     * 
     * @param signatoresName4CC
     */
    public void setSignatoresName4CC(String signatoresName4CC) {
        this.signatoresName4CC = signatoresName4CC;
    }

    /**
     * 
     * @return
     */
    public String getSignatoresName5CC() {
        return signatoresName5CC;
    }

    /**
     * 
     * @param signatoresName5CC
     */
    public void setSignatoresName5CC(String signatoresName5CC) {
        this.signatoresName5CC = signatoresName5CC;
    }

    /**
     * 
     * @return
     */
    public String getSignatoresDesignation1CC() {
        return signatoresDesignation1CC;
    }

    /**
     * 
     * @param signatoresDesignation1CC
     */
    public void setSignatoresDesignation1CC(String signatoresDesignation1CC) {
        this.signatoresDesignation1CC = signatoresDesignation1CC;
    }

    /**
     * 
     * @return
     */
    public String getSignatoresDesignation2CC() {
        return signatoresDesignation2CC;
    }

    /**
     * 
     * @param signatoresDesignation2CC
     */
    public void setSignatoresDesignation2CC(String signatoresDesignation2CC) {
        this.signatoresDesignation2CC = signatoresDesignation2CC;
    }

    /**
     * 
     * @return
     */
    public String getSignatoresDesignation3CC() {
        return signatoresDesignation3CC;
    }

    /**
     * 
     * @param signatoresDesignation3CC
     */
    public void setSignatoresDesignation3CC(String signatoresDesignation3CC) {
        this.signatoresDesignation3CC = signatoresDesignation3CC;
    }

    /**
     * 
     * @return
     */
    public String getSignatoresDesignation4CC() {
        return signatoresDesignation4CC;
    }

    /**
     * 
     * @param signatoresDesignation4CC
     */
    public void setSignatoresDesignation4CC(String signatoresDesignation4CC) {
        this.signatoresDesignation4CC = signatoresDesignation4CC;
    }

    /**
     * 
     * @return
     */
    public String getSignatoresDesignation5CC() {
        return signatoresDesignation5CC;
    }

    /**
     * 
     * @param signatoresDesignation5CC
     */
    public void setSignatoresDesignation5CC(String signatoresDesignation5CC) {
        this.signatoresDesignation5CC = signatoresDesignation5CC;
    }

    /**
     * 
     * @return
     */
    public String getDirPartner1CC() {
        return dirPartner1CC;
    }

    /**
     * 
     * @param dirPartner1CC
     */
    public void setDirPartner1CC(String dirPartner1CC) {
        this.dirPartner1CC = dirPartner1CC;
    }

    /**
     * 
     * @return
     */
    public String getDirPartner2CC() {
        return dirPartner2CC;
    }

    /**
     * 
     * @param dirPartner2CC
     */
    public void setDirPartner2CC(String dirPartner2CC) {
        this.dirPartner2CC = dirPartner2CC;
    }

    /**
     * 
     * @return
     */
    public String getDirPartner3CC() {
        return dirPartner3CC;
    }

    /**
     * 
     * @param dirPartner3CC
     */
    public void setDirPartner3CC(String dirPartner3CC) {
        this.dirPartner3CC = dirPartner3CC;
    }

    /**
     * 
     * @return
     */
    public String getDirPartner4CC() {
        return dirPartner4CC;
    }

    /**
     * 
     * @param dirPartner4CC
     */
    public void setDirPartner4CC(String dirPartner4CC) {
        this.dirPartner4CC = dirPartner4CC;
    }

    /**
     * 
     * @return
     */
    public String getDirPartner5CC() {
        return dirPartner5CC;
    }

    /**
     * 
     * @param dirPartner5CC
     */
    public void setDirPartner5CC(String dirPartner5CC) {
        this.dirPartner5CC = dirPartner5CC;
    }
    
    /**
     * 
     * @return
     */
    public String getEmailInternetRA() {
        return emailInternetRA;
    }

    /**
     * 
     * @param emailInternetRA
     */
    public void setEmailInternetRA(String emailInternetRA) {
        this.emailInternetRA = emailInternetRA;
    }

    /**
     * 
     * @return
     */
    public String getEmailMobileRA() {
        return emailMobileRA;
    }

    /**
     * 
     * @param emailMobileRA
     */
    public void setEmailMobileRA(String emailMobileRA) {
        this.emailMobileRA = emailMobileRA;
    }

    /**
     * 
     * @return
     */
    public String getDesignationIC() {
        return designationIC;
    }

    /**
     * 
     * @param designationIC
     */
    public void setDesignationIC(String designationIC) {
        this.designationIC = designationIC;
    }

    /**
     * 
     * @return
     */
    public String getStatusIC() {
        return statusIC;
    }

    /**
     * 
     * @param statusIC
     */
    public void setStatusIC(String statusIC) {
        this.statusIC = statusIC;
    }

    /**
     * 
     * @return
     */
    public Integer getTenurED() {
        return tenurED;
    }

    /**
     * 
     * @param tenurED
     */
    public void setTenurED(Integer tenurED) {
        this.tenurED = tenurED;
    }

    /**
     * 
     * @return
     */
    public String getNameED() {
        return nameED;
    }

    /**
     * 
     * @param nameED
     */
    public void setNameED(String nameED) {
        this.nameED = nameED;
    }

    /**
     * 
     * @return
     */
    public String getAddress1ED() {
        return address1ED;
    }

    /**
     * 
     * @param address1ED
     */
    public void setAddress1ED(String address1ED) {
        this.address1ED = address1ED;
    }

    /**
     * 
     * @return
     */
    public String getAddress2ED() {
        return address2ED;
    }

    /**
     * 
     * @param address2ED
     */
    public void setAddress2ED(String address2ED) {
        this.address2ED = address2ED;
    }

    /**
     * 
     * @return
     */
    public String getAddress3ED() {
        return address3ED;
    }

    /**
     * 
     * @param address3ED
     */
    public void setAddress3ED(String address3ED) {
        this.address3ED = address3ED;
    }

    /**
     * 
     * @return
     */
    public String getCityED() {
        return cityED;
    }

    /**
     * 
     * @param cityED
     */
    public void setCityED(String cityED) {
        this.cityED = cityED;
    }

    /**
     * 
     * @return
     */
    public String getStateED() {
        return stateED;
    }

    /**
     * 
     * @param stateED
     */
    public void setStateED(String stateED) {
        this.stateED = stateED;
    }

    /**
     * 
     * @return
     */
    public String getCountryED() {
        return countryED;
    }

    /**
     * 
     * @param countryED
     */
    public void setCountryED(String countryED) {
        this.countryED = countryED;
    }

    /**
     * 
     * @return
     */
    public String getPostalCodeED() {
        return postalCodeED;
    }

    /**
     * 
     * @param postalCodeED
     */
    public void setPostalCodeED(String postalCodeED) {
        this.postalCodeED = postalCodeED;
    }

    /**
     * 
     * @return
     */
    public String getMobileED() {
        return mobileED;
    }

    /**
     * 
     * @param mobileED
     */
    public void setMobileED(String mobileED) {
        this.mobileED = mobileED;
    }

    /**
     * 
     * @return
     */
    public String getEmailED() {
        return emailED;
    }

    /**
     * 
     * @param emailED
     */
    public void setEmailED(String emailED) {
        this.emailED = emailED;
    }

    /**
     * 
     * @return
     */
    public String getPhoneED() {
        return phoneED;
    }

    /**
     * 
     * @param phoneED
     */
    public void setPhoneED(String phoneED) {
        this.phoneED = phoneED;
    }

    /**
     * 
     * @return
     */
    public String getTelexNoED() {
        return telexNoED;
    }

    /**
     * 
     * @param telexNoED
     */
    public void setTelexNoED(String telexNoED) {
        this.telexNoED = telexNoED;
    }

    /**
     * 
     * @return
     */
    public String getFaxNoED() {
        return faxNoED;
    }

    /**
     * 
     * @param faxNoED
     */
    public void setFaxNoED(String faxNoED) {
        this.faxNoED = faxNoED;
    }

    /**
     * 
     * @return
     */
    public Integer getNumOfYearsED() {
        return numOfYearsED;
    }

    /**
     * 
     * @param numOfYearsED
     */
    public void setNumOfYearsED(Integer numOfYearsED) {
        this.numOfYearsED = numOfYearsED;
    }

    /**
     * 
     * @return
     */
    public Integer getRetriementAgeED() {
        return retriementAgeED;
    }

    /**
     * 
     * @param retriementAgeED
     */
    public void setRetriementAgeED(Integer retriementAgeED) {
        this.retriementAgeED = retriementAgeED;
    }

    /**
     * 
     * @return
     */
    public String getParentNamePD() {
        return parentNamePD;
    }

    /**
     * 
     * @param parentNamePD
     */
    public void setParentNamePD(String parentNamePD) {
        this.parentNamePD = parentNamePD;
    }

    /**
     * 
     * @return
     */
    public String getParenticPD() {
        return parenticPD;
    }

    /**
     * 
     * @param parenticPD
     */
    public void setParenticPD(String parenticPD) {
        this.parenticPD = parenticPD;
    }

    /**
     * 
     * @return
     */
    public Integer getOccupationPD() {
        return occupationPD;
    }

    /**
     * 
     * @param occupationPD
     */
    public void setOccupationPD(Integer occupationPD) {
        this.occupationPD = occupationPD;
    }

    /**
     * 
     * @return
     */
    public String getDesignationPD() {
        return designationPD;
    }

    /**
     * 
     * @param designationPD
     */
    public void setDesignationPD(String designationPD) {
        this.designationPD = designationPD;
    }

    /**
     * 
     * @return
     */
    public Date getParentBODPD() {
        return parentBODPD;
    }

    /**
     * 
     * @param parentBODPD
     */
    public void setParentBODPD(Date parentBODPD) {
        this.parentBODPD = parentBODPD;
    }

    /**
     * 
     * @return
     */
    public String getSpouseDetailPD() {
        return spouseDetailPD;
    }

    /**
     * 
     * @param spouseDetailPD
     */
    public void setSpouseDetailPD(String spouseDetailPD) {
        this.spouseDetailPD = spouseDetailPD;
    }

    /**
     * 
     * @return
     */
    public String getAddress1PD() {
        return address1PD;
    }

    /**
     * 
     * @param address1PD
     */
    public void setAddress1PD(String address1PD) {
        this.address1PD = address1PD;
    }

    /**
     * 
     * @return
     */
    public String getAddress2PD() {
        return address2PD;
    }

    /**
     * 
     * @param address2PD
     */
    public void setAddress2PD(String address2PD) {
        this.address2PD = address2PD;
    }

    /**
     * 
     * @return
     */
    public String getAddress3PD() {
        return address3PD;
    }

    /**
     * 
     * @param address3PD
     */
    public void setAddress3PD(String address3PD) {
        this.address3PD = address3PD;
    }

    /**
     * 
     * @return
     */
    public String getPhoneNoPD() {
        return phoneNoPD;
    }

    /**
     * 
     * @param phoneNoPD
     */
    public void setPhoneNoPD(String phoneNoPD) {
        this.phoneNoPD = phoneNoPD;
    }
    
    /**
     * 
     * @return
     */
    public String getTypeCompCO() {
        return typeCompCO;
    }

    /**
     * 
     * @param typeCompCO
     */
    public void setTypeCompCO(String typeCompCO) {
        this.typeCompCO = typeCompCO;
    }

    /**
     * 
     * @return
     */
    public String getPlaceInCorpCO() {
        return placeInCorpCO;
    }

    /**
     * 
     * @param placeInCorpCO
     */
    public void setPlaceInCorpCO(String placeInCorpCO) {
        this.placeInCorpCO = placeInCorpCO;
    }

    /**
     * 
     * @return
     */
    public Integer getNoPartnerCO() {
        return noPartnerCO;
    }

    /**
     * 
     * @param noPartnerCO
     */
    public void setNoPartnerCO(Integer noPartnerCO) {
        this.noPartnerCO = noPartnerCO;
    }

    /**
     * 
     * @return
     */
    public String getParentCompanyCO() {
        return parentCompanyCO;
    }

    /**
     * 
     * @param parentCompanyCO
     */
    public void setParentCompanyCO(String parentCompanyCO) {
        this.parentCompanyCO = parentCompanyCO;
    }

    /**
     * 
     * @return
     */
    public String getApexHoldingCompany() {
        return apexHoldingCompany;
    }

    /**
     * 
     * @param apexHoldingCompany
     */
    public void setApexHoldingCompany(String apexHoldingCompany) {
        this.apexHoldingCompany = apexHoldingCompany;
    }

    /**
     * 
     * @return
     */
    public String getContactPersonCO() {
        return contactPersonCO;
    }

    /**
     * 
     * @param contactPersonCO
     */
    public void setContactPersonCO(String contactPersonCO) {
        this.contactPersonCO = contactPersonCO;
    }

    /**
     * 
     * @return
     */
    public String getContacPersonDesgnCO() {
        return contacPersonDesgnCO;
    }

    /**
     * 
     * @param contacPersonDesgnCO
     */
    public void setContacPersonDesgnCO(String contacPersonDesgnCO) {
        this.contacPersonDesgnCO = contacPersonDesgnCO;
    }

    /**
     * 
     * @return
     */
    public String getInternalCreditRatingCO() {
        return internalCreditRatingCO;
    }

    /**
     * 
     * @param internalCreditRatingCO
     */
    public void setInternalCreditRatingCO(String internalCreditRatingCO) {
        this.internalCreditRatingCO = internalCreditRatingCO;
    }

    /**
     * 
     * @return
     */
    public String getBusinesLicenseNoCO() {
        return businesLicenseNoCO;
    }

    /**
     * 
     * @param businesLicenseNoCO
     */
    public void setBusinesLicenseNoCO(String businesLicenseNoCO) {
        this.businesLicenseNoCO = businesLicenseNoCO;
    }

    /**
     * 
     * @return
     */
    public Date getBusinesCommencementDateCO() {
        return businesCommencementDateCO;
    }

    /**
     * 
     * @param businesCommencementDateCO
     */
    public void setBusinesCommencementDateCO(Date businesCommencementDateCO) {
        this.businesCommencementDateCO = businesCommencementDateCO;
    }

    /**
     * 
     * @return
     */
    public Date getBusinesLicenseExpiryDateCO() {
        return businesLicenseExpiryDateCO;
    }

    /**
     * 
     * @param businesLicenseExpiryDateCO
     */
    public void setBusinesLicenseExpiryDateCO(Date businesLicenseExpiryDateCO) {
        this.businesLicenseExpiryDateCO = businesLicenseExpiryDateCO;
    }

    /**
     * 
     * @return
     */
    public String getBankName1CO() {
        return bankName1CO;
    }

    /**
     * 
     * @param bankName1CO
     */
    public void setBankName1CO(String bankName1CO) {
        this.bankName1CO = bankName1CO;
    }

    /**
     * 
     * @return
     */
    public String getBankName2CO() {
        return bankName2CO;
    }

    /**
     * 
     * @param bankName2CO
     */
    public void setBankName2CO(String bankName2CO) {
        this.bankName2CO = bankName2CO;
    }

    /**
     * 
     * @return
     */
    public String getNatureOfBusinesCO() {
        return natureOfBusinesCO;
    }

    /**
     * 
     * @param natureOfBusinesCO
     */
    public void setNatureOfBusinesCO(String natureOfBusinesCO) {
        this.natureOfBusinesCO = natureOfBusinesCO;
    }

    /**
     * 
     * @return
     */
    public String getMajorShareHolder1() {
        return majorShareHolder1;
    }

    /**
     * 
     * @param majorShareHolder1
     */
    public void setMajorShareHolder1(String majorShareHolder1) {
        this.majorShareHolder1 = majorShareHolder1;
    }

    /**
     * 
     * @return
     */
    public String getMajorShareHolder2() {
        return majorShareHolder2;
    }

    /**
     * 
     * @param majorShareHolder2
     */
    public void setMajorShareHolder2(String majorShareHolder2) {
        this.majorShareHolder2 = majorShareHolder2;
    }

    /**
     * 
     * @return
     */
    public String getMajorShareHolder3() {
        return majorShareHolder3;
    }

    /**
     * 
     * @param majorShareHolder3
     */
    public void setMajorShareHolder3(String majorShareHolder3) {
        this.majorShareHolder3 = majorShareHolder3;
    }

    /**
     * 
     * @return
     */
    public String getMajorShareHolder4() {
        return majorShareHolder4;
    }

    /**
     * 
     * @param majorShareHolder4
     */
    public void setMajorShareHolder4(String majorShareHolder4) {
        this.majorShareHolder4 = majorShareHolder4;
    }

    /**
     * 
     * @return
     */
    public String getMajorShareHolder5() {
        return majorShareHolder5;
    }

    /**
     * 
     * @param majorShareHolder5
     */
    public void setMajorShareHolder5(String majorShareHolder5) {
        this.majorShareHolder5 = majorShareHolder5;
    }

    /**
     * 
     * @return
     */
    public String getMajorShareHolder6() {
        return majorShareHolder6;
    }

    /**
     * 
     * @param majorShareHolder6
     */
    public void setMajorShareHolder6(String majorShareHolder6) {
        this.majorShareHolder6 = majorShareHolder6;
    }

    /**
     * 
     * @return
     */
    public String getMajorShareHolder7() {
        return majorShareHolder7;
    }

    /**
     * 
     * @param majorShareHolder7
     */
    public void setMajorShareHolder7(String majorShareHolder7) {
        this.majorShareHolder7 = majorShareHolder7;
    }

    /**
     * 
     * @return
     */
    public String getMajorShareHolder8() {
        return majorShareHolder8;
    }

    /**
     * 
     * @param majorShareHolder8
     */
    public void setMajorShareHolder8(String majorShareHolder8) {
        this.majorShareHolder8 = majorShareHolder8;
    }

    /**
     * 
     * @return
     */
    public String getMajorShareHolder9() {
        return majorShareHolder9;
    }

    /**
     * 
     * @param majorShareHolder9
     */
    public void setMajorShareHolder9(String majorShareHolder9) {
        this.majorShareHolder9 = majorShareHolder9;
    }

    /**
     * 
     * @return
     */
    public String getMajorShareHolder10() {
        return majorShareHolder10;
    }

    /**
     * 
     * @param majorShareHolder10
     */
    public void setMajorShareHolder10(String majorShareHolder10) {
        this.majorShareHolder10 = majorShareHolder10;
    }

    /**
     * 
     * @return
     */
    public String getRelationShip1() {
        return relationShip1;
    }

    /**
     * 
     * @param relationShip1
     */
    public void setRelationShip1(String relationShip1) {
        this.relationShip1 = relationShip1;
    }

    /**
     * 
     * @return
     */
    public String getRelationShip2() {
        return relationShip2;
    }

    /**
     * 
     * @param relationShip2
     */
    public void setRelationShip2(String relationShip2) {
        this.relationShip2 = relationShip2;
    }

    /**
     * 
     * @return
     */
    public String getRelationShip3() {
        return relationShip3;
    }

    /**
     * 
     * @param relationShip3
     */
    public void setRelationShip3(String relationShip3) {
        this.relationShip3 = relationShip3;
    }

    /**
     * 
     * @return
     */
    public String getRelationShip4() {
        return relationShip4;
    }

    /**
     * 
     * @param relationShip4
     */
    public void setRelationShip4(String relationShip4) {
        this.relationShip4 = relationShip4;
    }

    /**
     * 
     * @return
     */
    public String getRelationShip5() {
        return relationShip5;
    }

    /**
     * 
     * @param relationShip5
     */
    public void setRelationShip5(String relationShip5) {
        this.relationShip5 = relationShip5;
    }

    /**
     * 
     * @return
     */
    public String getRelationShip6() {
        return relationShip6;
    }

    /**
     * 
     * @param relationShip6
     */
    public void setRelationShip6(String relationShip6) {
        this.relationShip6 = relationShip6;
    }

    /**
     * 
     * @return
     */
    public String getRelationShip7() {
        return relationShip7;
    }

    /**
     * 
     * @param relationShip7
     */
    public void setRelationShip7(String relationShip7) {
        this.relationShip7 = relationShip7;
    }

    /**
     * 
     * @return
     */
    public String getRelationShip8() {
        return relationShip8;
    }

    /**
     * 
     * @param relationShip8
     */
    public void setRelationShip8(String relationShip8) {
        this.relationShip8 = relationShip8;
    }

    /**
     * 
     * @return
     */
    public String getRelationShip9() {
        return relationShip9;
    }

    /**
     * 
     * @param relationShip9
     */
    public void setRelationShip9(String relationShip9) {
        this.relationShip9 = relationShip9;
    }

    /**
     * 
     * @return
     */
    public String getRelationShip10() {
        return relationShip10;
    }

    /**
     * 
     * @param relationShip10
     */
    public void setRelationShip10(String relationShip10) {
        this.relationShip10 = relationShip10;
    }
    
    
    
}
