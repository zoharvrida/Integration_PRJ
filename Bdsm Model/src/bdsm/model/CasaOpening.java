/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author v00019722
 */
public class CasaOpening extends BaseModel implements Serializable {

    private String applicationID;
    private Integer custId;
    private String acctNo;
    private Date accountOpenDateConvertion;
    private String accountOpenDate;
    private Integer accessCode;
    private Integer productCode;
    private String productCodeDesc;
    private Integer branchCodeCasa;
    private String branchCodeCasaDesc;
    private Integer lob;
	private String stateIndicator;
    private String stateIndicatorDesc;
    private String lobDesc;
    private String regLuckyFlag;   
    private String additionalRemarks;
    private String aoBusiness;
    private String aoOperation;
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
    private String comments;
    private String acctTitle;
    private String sandiTabLBU14Lama;
    private String sandiTabLBUBaru;
	/*# Revision : Add field casaopening
# Change Date : 21-Januari-2016   
# Changer : v00020800
# Begin 1 */ 
	    private String sandiJenisRekLBU17;
	    private String sandiJenisBankLain;
		/*End 1*/
    private String sifatRekTabunganLBUBaru;
    private String tujuanPembukaan;
    private String sumberDana;
    private String tujuanPembukaanDesc;
    private String sumberDanaDesc;
    private String sumberDanaLain;
    private String branchCodeName;
    private String aoBusinessDesc;
    private String aoOperationDesc;
    private String echannel;
    private String echannelDesc;
    private String relation;
    private String menuAccount;
    private String term;
    private String instalmentAmount;  
    private String flgProcess; 
    private String codUserID;
    private String acctOB;
    private String acctOBDesc;
    private String remarks4;
    private String remarks5;
    private String pemilikStatus;
    private String sandiJenisTabunganLBU;
	private String makerID;
	private String serialNO;

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
     * @return the regLuckyFlag
     */
    public String getRegLuckyFlag() {
        return regLuckyFlag;
    }

    /**
     * @param regLuckyFlag the regLuckyFlag to set
     */
    public void setRegLuckyFlag(String RegLuckyFlag) {
        this.regLuckyFlag = RegLuckyFlag;
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
     * @return the additionalRemarks
     */
    public String getAdditionalRemarks() {
        return additionalRemarks;
    }

    /**
     * @param additionalRemarks the additionalRemarks to set
     */
    public void setAdditionalRemarks(String AdditionalRemarks) {
        this.additionalRemarks = AdditionalRemarks;
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
     * @return the currentAccBiCode
     */
    public String getCurrentAccBiCode() {
        return currentAccBiCode;
    }

    /**
     * @param currentAccBiCode the currentAccBiCode to set
     */
    public void setCurrentAccBiCode(String CurrentAccBiCode) {
        this.currentAccBiCode = CurrentAccBiCode;
    }

    /**
     * @return the savingAccBiCode
     */
    public String getSavingAccBiCode() {
        return savingAccBiCode;
    }

    /**
     * @param savingAccBiCode the savingAccBiCode to set
     */
    public void setSavingAccBiCode(String SavingAccBiCode) {
        this.savingAccBiCode = SavingAccBiCode;
    }

    /**
     * @return the otherAccBiCode
     */
    public String getOtherAccBiCode() {
        return otherAccBiCode;
    }

    /**
     * @param otherAccBiCode the otherAccBiCode to set
     */
    public void setOtherAccBiCode(String OtherAccBiCode) {
        this.otherAccBiCode = OtherAccBiCode;
    }

    /**
     * @return the tdAccBiCode
     */
    public String getTdAccBiCode() {
        return tdAccBiCode;
    }

    /**
     * @param tdAccBiCode the tdAccBiCode to set
     */
    public void setTdAccBiCode(String TdAccBiCode) {
        this.tdAccBiCode = TdAccBiCode;
    }

    /**
     * @return the additionalField1Casa
     */
    public String getAdditionalField1Casa() {
        return additionalField1Casa;
    }

    /**
     * @param additionalField1Casa the additionalField1Casa to set
     */
    public void setAdditionalField1Casa(String AdditionalField1Casa) {
        this.additionalField1Casa = AdditionalField1Casa;
    }

    /**
     * @return the additionalField2Casa
     */
    public String getAdditionalField2Casa() {
        return additionalField2Casa;
    }

    /**
     * @param additionalField2Casa the additionalField2Casa to set
     */
    public void setAdditionalField2Casa(String AdditionalField2Casa) {
        this.additionalField2Casa = AdditionalField2Casa;
    }

    /**
     * @return the additionalField3Casa
     */
    public String getAdditionalField3Casa() {
        return additionalField3Casa;
    }

    /**
     * @param additionalField3Casa the additionalField3Casa to set
     */
    public void setAdditionalField3Casa(String AdditionalField3Casa) {
        this.additionalField3Casa = AdditionalField3Casa;
    }

    /**
     * @return the additionalField4Casa
     */
    public String getAdditionalField4Casa() {
        return additionalField4Casa;
    }

    /**
     * @param additionalField4Casa the additionalField4Casa to set
     */
    public void setAdditionalField4Casa(String AdditionalField4Casa) {
        this.additionalField4Casa = AdditionalField4Casa;
    }

    /**
     * @return the additionalField5Casa
     */
    public String getAdditionalField5Casa() {
        return additionalField5Casa;
    }

    /**
     * @param additionalField5Casa the additionalField5Casa to set
     */
    public void setAdditionalField5Casa(String AdditionalField5Casa) {
        this.additionalField5Casa = AdditionalField5Casa;
    }

    /**
     * @return the additionalField6Casa
     */
    public String getAdditionalField6Casa() {
        return additionalField6Casa;
    }

    /**
     * @param additionalField6Casa the additionalField6Casa to set
     */
    public void setAdditionalField6Casa(String AdditionalField6Casa) {
        this.additionalField6Casa = AdditionalField6Casa;
    }

    /**
     * @return the additionalField7Casa
     */
    public String getAdditionalField7Casa() {
        return additionalField7Casa;
    }

    /**
     * @param additionalField7Casa the additionalField7Casa to set
     */
    public void setAdditionalField7Casa(String AdditionalField7Casa) {
        this.additionalField7Casa = AdditionalField7Casa;
    }

    /**
     * @return the additionalField8Casa
     */
    public String getAdditionalField8Casa() {
        return additionalField8Casa;
    }

    /**
     * @param additionalField8Casa the additionalField8Casa to set
     */
    public void setAdditionalField8Casa(String AdditionalField8Casa) {
        this.additionalField8Casa = AdditionalField8Casa;
    }

    /**
     * @return the additionalField9Casa
     */
    public String getAdditionalField9Casa() {
        return additionalField9Casa;
    }

    /**
     * @param additionalField9Casa the additionalField9Casa to set
     */
    public void setAdditionalField9Casa(String AdditionalField9Casa) {
        this.additionalField9Casa = AdditionalField9Casa;
    }

    /**
     * @return the additionalField10Casa
     */
    public String getAdditionalField10Casa() {
        return additionalField10Casa;
    }

    /**
     * @param additionalField10Casa the additionalField10Casa to set
     */
    public void setAdditionalField10Casa(String AdditionalField10Casa) {
        this.additionalField10Casa = AdditionalField10Casa;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(this.getClass().getSimpleName()).append("{").append("acctNo=").append(this.acctNo).append(",acctTitle=").append(this.acctTitle).append(",custId=").append(this.custId).append(",productCode=").append(this.productCode).append("}");
        return sb.toString();
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
/*# Revision : Add field casaopening
# Change Date : 21-Januari-2016   
# Changer : v00020800
# Begin 2 */ 
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
}
