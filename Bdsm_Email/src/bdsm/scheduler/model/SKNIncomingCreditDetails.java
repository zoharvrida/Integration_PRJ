/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

import bdsm.model.BaseModel;

/**
 *
 * @author v00019722
 */
public class SKNIncomingCreditDetails extends BaseModel {
    private SKNIncomingCreditPK pk;
	private Integer parentRecid;
	private String clearingDate;
	private String referenceNo;
	private String remitterName;
	private String beneficiaryName;
	private String beneficiaryAccount;
	private String remarks;
	private String amount;
	private String receiverBankCode;
	private String senderBICode;
	private String senderSknCoordinatorCode;
	private String transactionCode;
	private String bankType;
	private String remitterResidencyStatus;
	private String remitterCitizenshipStatus;
	private String beneficiaryProvinceCode;
	private String beneficiaryCityCode;
	private String receiverSknCoordinatorCode;
	private String senderClearingCode;
	private String sor;
	private Integer length;
	private String comments;
        private String statusExtract;
		private String idgen2;
	private String jData;

    /**
     * @return the pk
     */
    public SKNIncomingCreditPK getPk() {
        return pk;
    }

    /**
     * @param pk the pk to set
     */
    public void setPk(SKNIncomingCreditPK pk) {
        this.pk = pk;
    }

    /**
     * @return the clearingDate
     */
    public String getClearingDate() {
        return clearingDate;
    }

    /**
     * @param clearingDate the clearingDate to set
     */
    public void setClearingDate(String clearingDate) {
        this.clearingDate = clearingDate;
    }

    /**
     * @return the referenceNo
     */
    public String getReferenceNo() {
        return referenceNo;
    }

    /**
     * @param referenceNo the referenceNo to set
     */
    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    /**
     * @return the remitterName
     */
    public String getRemitterName() {
        return remitterName;
    }

    /**
     * @param remitterName the remitterName to set
     */
    public void setRemitterName(String remitterName) {
        this.remitterName = remitterName;
    }

    /**
     * @return the beneficiaryName
     */
    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    /**
     * @param beneficiaryName the beneficiaryName to set
     */
    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }

    /**
     * @return the beneficiaryAccount
     */
    public String getBeneficiaryAccount() {
        return beneficiaryAccount;
    }

    /**
     * @param beneficiaryAccount the beneficiaryAccount to set
     */
    public void setBeneficiaryAccount(String beneficiaryAccount) {
        this.beneficiaryAccount = beneficiaryAccount;
    }

    /**
     * @return the remarks
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * @param remarks the remarks to set
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * @return the amount
     */
    public String getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }

    /**
     * @return the receiverBankCode
     */
    public String getReceiverBankCode() {
        return receiverBankCode;
    }

    /**
     * @param receiverBankCode the receiverBankCode to set
     */
    public void setReceiverBankCode(String receiverBankCode) {
        this.receiverBankCode = receiverBankCode;
    }

    /**
     * @return the senderBICode
     */
    public String getSenderBICode() {
        return senderBICode;
    }

    /**
     * @param senderBICode the senderBICode to set
     */
    public void setSenderBICode(String senderBICode) {
        this.senderBICode = senderBICode;
    }

    /**
     * @return the senderSknCoordinatorCode
     */
    public String getSenderSknCoordinatorCode() {
        return senderSknCoordinatorCode;
    }

    /**
     * @param senderSknCoordinatorCode the senderSknCoordinatorCode to set
     */
    public void setSenderSknCoordinatorCode(String senderSknCoordinatorCode) {
        this.senderSknCoordinatorCode = senderSknCoordinatorCode;
    }

    /**
     * @return the transactionCode
     */
    public String getTransactionCode() {
        return transactionCode;
    }

    /**
     * @param transactionCode the transactionCode to set
     */
    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }

    /**
     * @return the bankType
     */
    public String getBankType() {
        return bankType;
    }

    /**
     * @param bankType the bankType to set
     */
    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    /**
     * @return the remitterResidencyStatus
     */
    public String getRemitterResidencyStatus() {
        return remitterResidencyStatus;
    }

    /**
     * @param remitterResidencyStatus the remitterResidencyStatus to set
     */
    public void setRemitterResidencyStatus(String remitterResidencyStatus) {
        this.remitterResidencyStatus = remitterResidencyStatus;
    }

    /**
     * @return the remitterCitizenshipStatus
     */
    public String getRemitterCitizenshipStatus() {
        return remitterCitizenshipStatus;
    }

    /**
     * @param remitterCitizenshipStatus the remitterCitizenshipStatus to set
     */
    public void setRemitterCitizenshipStatus(String remitterCitizenshipStatus) {
        this.remitterCitizenshipStatus = remitterCitizenshipStatus;
    }

    /**
     * @return the beneficiaryProvinceCode
     */
    public String getBeneficiaryProvinceCode() {
        return beneficiaryProvinceCode;
    }

    /**
     * @param beneficiaryProvinceCode the beneficiaryProvinceCode to set
     */
    public void setBeneficiaryProvinceCode(String beneficiaryProvinceCode) {
        this.beneficiaryProvinceCode = beneficiaryProvinceCode;
    }

    /**
     * @return the beneficiaryCityCode
     */
    public String getBeneficiaryCityCode() {
        return beneficiaryCityCode;
    }

    /**
     * @param beneficiaryCityCode the beneficiaryCityCode to set
     */
    public void setBeneficiaryCityCode(String beneficiaryCityCode) {
        this.beneficiaryCityCode = beneficiaryCityCode;
    }

    /**
     * @return the receiverSknCoordinatorCode
     */
    public String getReceiverSknCoordinatorCode() {
        return receiverSknCoordinatorCode;
    }

    /**
     * @param receiverSknCoordinatorCode the receiverSknCoordinatorCode to set
     */
    public void setReceiverSknCoordinatorCode(String receiverSknCoordinatorCode) {
        this.receiverSknCoordinatorCode = receiverSknCoordinatorCode;
    }

    /**
     * @return the senderClearingCode
     */
    public String getSenderClearingCode() {
        return senderClearingCode;
    }

    /**
     * @param senderClearingCode the senderClearingCode to set
     */
    public void setSenderClearingCode(String senderClearingCode) {
        this.senderClearingCode = senderClearingCode;
    }

    /**
     * @return the sor
     */
    public String getSor() {
        return sor;
    }

    /**
     * @param sor the sor to set
     */
    public void setSor(String sor) {
        this.sor = sor;
    }

    /**
     * @return the length
     */
    public Integer getLength() {
        return length;
    }

    /**
     * @param length the length to set
     */
    public void setLength(Integer length) {
        this.length = length;
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
	 * @return the parentRecid
	 */
	public Integer getParentRecid() {
		return parentRecid;
	}

	/**
	 * @param parentRecid the parentRecid to set
	 */
	public void setParentRecid(Integer parentRecid) {
		this.parentRecid = parentRecid;
	}

    /**
     * @return the statusExtract
     */
    public String getStatusExtract() {
        return statusExtract;
    }

    /**
     * @param statusExtract the statusExtract to set
     */
    public void setStatusExtract(String statusExtract) {
        this.statusExtract = statusExtract;
    }

	/**
	 * @return the idgen2
	 */
	public String getIdgen2() {
		return idgen2;
	}

	/**
	 * @param idgen2 the idgen2 to set
	 */
	public void setIdgen2(String idgen2) {
		this.idgen2 = idgen2;
	}

	/**
	 * @return the jData
	 */
	public String getjData() {
		return jData;
	}

	/**
	 * @param jData the jData to set
	 */
	public void setjData(String jData) {
		this.jData = jData;
	}
}
