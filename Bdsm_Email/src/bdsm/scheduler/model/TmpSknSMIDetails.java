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
public class TmpSknSMIDetails extends BaseModel {
    private TmpSKNSMIpkDtls pk;
    private String tipe_record;
    private String Clearing_Date;
    private String InstNumber;
    private String receivingRouteCode;
    private String accountNo;
    private String trxCode;
    private String Amount;
    private String senderRouteCode;
    private String SOR;
    private String LOB;
    private String branchCode;
    private String subBranch;
    private String RejectCode;
    private Integer orderNo;
    private String parentID;
    private String message;
	private String extractStatus;

    /**
     * @return the pk
     */
    public TmpSKNSMIpkDtls getPk() {
        return pk;
    }

    /**
     * @param pk the pk to set
     */
    public void setPk(TmpSKNSMIpkDtls pk) {
        this.pk = pk;
    }

    /**
     * @return the tipe_record
     */
    public String getTipe_record() {
        return tipe_record;
    }

    /**
     * @param tipe_record the tipe_record to set
     */
    public void setTipe_record(String tipe_record) {
        this.tipe_record = tipe_record;
    }

    /**
     * @return the Clearing_Date
     */
    public String getClearing_Date() {
        return Clearing_Date;
    }

    /**
     * @param Clearing_Date the Clearing_Date to set
     */
    public void setClearing_Date(String Clearing_Date) {
        this.Clearing_Date = Clearing_Date;
    }

    /**
     * @return the InstNumber
     */
    public String getInstNumber() {
        return InstNumber;
    }

    /**
     * @param InstNumber the InstNumber to set
     */
    public void setInstNumber(String InstNumber) {
        this.InstNumber = InstNumber;
    }

    /**
     * @return the receivingRouteCode
     */
    public String getReceivingRouteCode() {
        return receivingRouteCode;
    }

    /**
     * @param receivingRouteCode the receivingRouteCode to set
     */
    public void setReceivingRouteCode(String receivingRouteCode) {
        this.receivingRouteCode = receivingRouteCode;
    }

    /**
     * @return the accountNo
     */
    public String getAccountNo() {
        return accountNo;
    }

    /**
     * @param accountNo the accountNo to set
     */
    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    /**
     * @return the trxCode
     */
    public String getTrxCode() {
        return trxCode;
    }

    /**
     * @param trxCode the trxCode to set
     */
    public void setTrxCode(String trxCode) {
        this.trxCode = trxCode;
    }

    /**
     * @return the Amount
     */
    public String getAmount() {
        return Amount;
    }

    /**
     * @param Amount the Amount to set
     */
    public void setAmount(String Amount) {
        this.Amount = Amount;
    }

    /**
     * @return the senderRouteCode
     */
    public String getSenderRouteCode() {
        return senderRouteCode;
    }

    /**
     * @param senderRouteCode the senderRouteCode to set
     */
    public void setSenderRouteCode(String senderRouteCode) {
        this.senderRouteCode = senderRouteCode;
    }

    /**
     * @return the SOR
     */
    public String getSOR() {
        return SOR;
    }

    /**
     * @param SOR the SOR to set
     */
    public void setSOR(String SOR) {
        this.SOR = SOR;
    }

    /**
     * @return the LOB
     */
    public String getLOB() {
        return LOB;
    }

    /**
     * @param LOB the LOB to set
     */
    public void setLOB(String LOB) {
        this.LOB = LOB;
    }

    /**
     * @return the branchCode
     */
    public String getBranchCode() {
        return branchCode;
    }

    /**
     * @param branchCode the branchCode to set
     */
    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    /**
     * @return the subBranch
     */
    public String getSubBranch() {
        return subBranch;
    }

    /**
     * @param subBranch the subBranch to set
     */
    public void setSubBranch(String subBranch) {
        this.subBranch = subBranch;
    }

    /**
     * @return the RejectCode
     */
    public String getRejectCode() {
        return RejectCode;
    }

    /**
     * @param RejectCode the RejectCode to set
     */
    public void setRejectCode(String RejectCode) {
        this.RejectCode = RejectCode;
    }

    /**
     * @return the orderNo
     */
    public Integer getOrderNo() {
        return orderNo;
    }

    /**
     * @param orderNo the orderNo to set
     */
    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * @return the parentID
     */
    public String getParentID() {
        return parentID;
    }

    /**
     * @param parentID the parentID to set
     */
    public void setParentID(String parentID) {
        this.parentID = parentID;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

	/**
	 * @return the extractStatus
	 */
	public String getExtractStatus() {
		return extractStatus;
	}

	/**
	 * @param extractStatus the extractStatus to set
	 */
	public void setExtractStatus(String extractStatus) {
		this.extractStatus = extractStatus;
	}
}
