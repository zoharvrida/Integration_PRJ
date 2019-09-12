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

	public TmpMpFeeMDRPK getCompositeId() {
		return compositeId;
	}

	public void setCompositeId(TmpMpFeeMDRPK compositeId) {
		this.compositeId = compositeId;
	}

	public Date getDatUpload() {
		return datUpload;
	}

	public void setDatUpload(Date datUpload) {
		this.datUpload = datUpload;
	}

	public String getFlagStatus() {
		return flagStatus;
	}

	public void setFlagStatus(String flagStatus) {
		this.flagStatus = flagStatus;
	}

	public BigDecimal getMaxLimitAmount() {
		return maxLimitAmount;
	}

	public void setMaxLimitAmount(BigDecimal maxLimitAmount) {
		this.maxLimitAmount = maxLimitAmount;
	}

	public String getMdrLimit() {
		return mdrLimit;
	}

	public void setMdrLimit(String mdrLimit) {
		this.mdrLimit = mdrLimit;
	}

	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}

	public String getStatusReason() {
		return statusReason;
	}

	public void setStatusReason(String statusReason) {
		this.statusReason = statusReason;
	}

	
}
