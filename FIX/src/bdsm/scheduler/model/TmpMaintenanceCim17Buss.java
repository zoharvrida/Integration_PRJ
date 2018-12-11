/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

import bdsm.model.BaseModel;
import java.util.Date;

/**
 *
 * @author v00020841
 */
public class TmpMaintenanceCim17Buss extends BaseModel {

    private TmpMaintenanceCim17BussPK compositeId;
    private String internalCreditRating;
    private String bussLisenceNo;
    private Date bussComencementDat;
    private Date expiryDate;
    private String bankName1;
    private String bankName2;
    private String natureOfBuss;
    private String flagStatus;
    private String statusReason;

    /**
     * @return the compositeId
     */
    public TmpMaintenanceCim17BussPK getCompositeId() {
        return compositeId;
    }

    /**
     * @param compositeId the compositeId to set
     */
    public void setCompositeId(TmpMaintenanceCim17BussPK compositeId) {
        this.compositeId = compositeId;
    }

    /**
     * @return the internalCreditRating
     */
    public String getInternalCreditRating() {
        return internalCreditRating;
    }

    /**
     * @param internalCreditRating the internalCreditRating to set
     */
    public void setInternalCreditRating(String internalCreditRating) {
        this.internalCreditRating = internalCreditRating;
    }

    /**
     * @return the bussLisenceNo
     */
    public String getBussLisenceNo() {
        return bussLisenceNo;
    }

    /**
     * @param bussLisenceNo the bussLisenceNo to set
     */
    public void setBussLisenceNo(String bussLisenceNo) {
        this.bussLisenceNo = bussLisenceNo;
    }

    /**
     * @return the bussComencementDat
     */
    public Date getBussComencementDat() {
        return bussComencementDat;
    }

    /**
     * @param bussComencementDat the bussComencementDat to set
     */
    public void setBussComencementDat(Date bussComencementDat) {
        this.bussComencementDat = bussComencementDat;
    }

    /**
     * @return the expiryDate
     */
    public Date getExpiryDate() {
        return expiryDate;
    }

    /**
     * @param expiryDate the expiryDate to set
     */
    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    /**
     * @return the bankName1
     */
    public String getBankName1() {
        return bankName1;
    }

    /**
     * @param bankName1 the bankName1 to set
     */
    public void setBankName1(String bankName1) {
        this.bankName1 = bankName1;
    }

    /**
     * @return the bankName2
     */
    public String getBankName2() {
        return bankName2;
    }

    /**
     * @param bankName2 the bankName2 to set
     */
    public void setBankName2(String bankName2) {
        this.bankName2 = bankName2;
    }

    /**
     * @return the natureOfBuss
     */
    public String getNatureOfBuss() {
        return natureOfBuss;
    }

    /**
     * @param natureOfBuss the natureOfBuss to set
     */
    public void setNatureOfBuss(String natureOfBuss) {
        this.natureOfBuss = natureOfBuss;
    }

    /**
     * @return the flagStatus
     */
    public String getFlagStatus() {
        return flagStatus;
    }

    /**
     * @param flagStatus the flagStatus to set
     */
    public void setFlagStatus(String flagStatus) {
        this.flagStatus = flagStatus;
    }

    /**
     * @return the statusReason
     */
    public String getStatusReason() {
        return statusReason;
    }

    /**
     * @param statusReason the statusReason to set
     */
    public void setStatusReason(String statusReason) {
        this.statusReason = statusReason;
    }

  
   
   
}
