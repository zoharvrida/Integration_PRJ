/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

import bdsm.model.BaseModel;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author v00020841
 */
public class TmpMpFeeMDR extends BaseModel{
    private TmpMpFeeMDRPK compositeId;
    private Date datUpload;
    private Integer period;
    private String mdrLimit;
    private BigDecimal maxLimitAmount;
    private String flagStatus;
    private String statusReason;

    /**
     * 
     * @return
     */
    public TmpMpFeeMDRPK getCompositeId() {
		return compositeId;
	}

    /**
     * 
     * @param compositeId
     */
    public void setCompositeId(TmpMpFeeMDRPK compositeId) {
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
    public BigDecimal getMaxLimitAmount() {
		return maxLimitAmount;
	}

    /**
     * 
     * @param maxLimitAmount
     */
    public void setMaxLimitAmount(BigDecimal maxLimitAmount) {
		this.maxLimitAmount = maxLimitAmount;
	}

    /**
     * 
     * @return
     */
    public String getMdrLimit() {
		return mdrLimit;
	}

    /**
     * 
     * @param mdrLimit
     */
    public void setMdrLimit(String mdrLimit) {
		this.mdrLimit = mdrLimit;
	}

    /**
     * 
     * @return
     */
    public Integer getPeriod() {
		return period;
	}

    /**
     * 
     * @param period
     */
    public void setPeriod(Integer period) {
		this.period = period;
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

	
}
