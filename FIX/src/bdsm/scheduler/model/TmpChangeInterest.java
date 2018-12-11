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
public class TmpChangeInterest extends BaseModel{
    
    private TmpChangeInterestPK compositeId;
    private Date datUpload;
    private String contractNumber;
    private Double variance;
    private Date datEfective;
    private String amandement;
    private Date datAmandement;
    private String rateCode;
    private String flagStatus;
    private String statusReason;
    private String efective;
    private String vEffRate;
    private Double efectiveRate;
    private String component;

// <property column="DAT_UPLOAD"      name="datUpload"    type="date"/>
    /**
     * 
     * @return
     */
    public TmpChangeInterestPK getCompositeId() {
        return compositeId;
    }

    /**
     * 
     * @param compositeId
     */
    public void setCompositeId(TmpChangeInterestPK compositeId) {
        this.compositeId = compositeId;
    }

    /**
     * 
     * @return
     */
    public Date getDatUpload() {
        return datUpload;
    }

    /**
     * 
     * @param datUpload
     */
    public void setDatUpload(Date datUpload) {
        this.datUpload = datUpload;
    }

    /**
     * 
     * @return
     */
    public String getContractNumber() {
        return contractNumber;
    }

    /**
     * 
     * @param contractNumber
     */
    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    /**
     * 
     * @return
     */
    public Double getVariance() {
        return variance;
    }

    /**
     * 
     * @param variance
     */
    public void setVariance(Double variance) {
        this.variance = variance;
    }

    /**
     * 
     * @return
     */
    public Date getDatEfective() {
        return datEfective;
    }

    /**
     * 
     * @param datEfective
     */
    public void setDatEfective(Date datEfective) {
        this.datEfective = datEfective;
    }

    /**
     * 
     * @return
     */
    public String getFlagStatus() {
        return flagStatus;
    }

    /**
     * 
     * @param flagStatus
     */
    public void setFlagStatus(String flagStatus) {
        this.flagStatus = flagStatus;
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
    public String getEfective() {
        return efective;
    }

    /**
     * 
     * @param efective
     */
    public void setEfective(String efective) {
        this.efective = efective;
    }

   
    /**
     * @return the rateCode
     */
    public String getRateCode() {
        return rateCode;
    }

    /**
     * @param rateCode the rateCode to set
     */
    public void setRateCode(String rateCode) {
        this.rateCode = rateCode;
    }

    /**
     * @return the amandement
     */
    public String getAmandement() {
        return amandement;
    }

    /**
     * @param amandement the amandement to set
     */
    public void setAmandement(String amandement) {
        this.amandement = amandement;
    }

    /**
     * @return the datAmandement
     */
    public Date getDatAmandement() {
        return datAmandement;
    }

    /**
     * @param datAmandement the datAmandement to set
     */
    public void setDatAmandement(Date datAmandement) {
        this.datAmandement = datAmandement;
    }

    /**
     * @return the vEffRate
     */
    public String getvEffRate() {
        return vEffRate;
    }

    /**
     * @param vEffRate the vEffRate to set
     */
    public void setvEffRate(String vEffRate) {
        this.vEffRate = vEffRate;
    }

   

    /**
     * @return the component
     */
    public String getComponent() {
        return component;
    }

    /**
     * @param component the component to set
     */
    public void setComponent(String component) {
        this.component = component;
    }

    /**
     * @return the efectiveRate
     */
    public Double getEfectiveRate() {
        return efectiveRate;
    }

    /**
     * @param efectiveRate the efectiveRate to set
     */
    public void setEfectiveRate(Double efectiveRate) {
        this.efectiveRate = efectiveRate;
    }

    
}
