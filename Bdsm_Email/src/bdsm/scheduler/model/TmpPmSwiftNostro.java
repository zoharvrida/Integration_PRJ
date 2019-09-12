/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

import bdsm.model.BaseModel;
import bdsm.model.BaseModel;

/**
 *
 * @author v00020800
 */
public class TmpPmSwiftNostro extends BaseModel{
    private TmpPmSwiftNostroPK compositeId;

    private String periodOfTime;
    private String ccy;
    private Integer priority;
    private String nostro;
    private String description;
    private String chargeBearer;
    private Integer maxNot;
    private String flagStatus;
    private String statusReason;
    private String fccProductCode;
    private String flgExclude;
    private String cmd;

    /**
     * @return the periodOfTime
     */
    public String getPeriodOfTime() {
        return periodOfTime;
    }

    /**
     * @param periodOfTime the periodOfTime to set
     */
    public void setPeriodOfTime(String periodOfTime) {
        this.periodOfTime = periodOfTime;
    }

    /**
     * @return the ccy
     */
    public String getCcy() {
        return ccy;
    }

    /**
     * @param ccy the ccy to set
     */
    public void setCcy(String ccy) {
        this.ccy = ccy;
    }

    /**
     * @return the priority
     */
    public Integer getPriority() {
        return priority;
    }

    /**
     * @param priority the priority to set
     */
    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    /**
     * @return the nostro
     */
    public String getNostro() {
        return nostro;
    }

    /**
     * @param nostro the nostro to set
     */
    public void setNostro(String nostro) {
        this.nostro = nostro;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the chargeBearer
     */
    public String getChargeBearer() {
        return chargeBearer;
    }

    /**
     * @param chargeBearer the chargeBearer to set
     */
    public void setChargeBearer(String chargeBearer) {
        this.chargeBearer = chargeBearer;
    }

    /**
     * @return the maxNot
     */
    public Integer getMaxNot() {
        return maxNot;
    }

    /**
     * @param maxNot the maxNot to set
     */
    public void setMaxNot(Integer maxNot) {
        this.maxNot = maxNot;
    }

    /**
     * @return the fccProductCode
     */
    public String getFccProductCode() {
        return fccProductCode;
    }

    /**
     * @param fccProductCode the fccProductCode to set
     */
    public void setFccProductCode(String fccProductCode) {
        this.fccProductCode = fccProductCode;
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

    /**
     * @return the compositeId
     */
    public TmpPmSwiftNostroPK getCompositeId() {
        return compositeId;
    }

    /**
     * @param compositeId the compositeId to set
     */
    public void setCompositeId(TmpPmSwiftNostroPK compositeId) {
        this.compositeId = compositeId;
    }


    /**
     * @return the flgExclude
     */
    public String getFlgExclude() {
        return flgExclude;
    }

    /**
     * @param flgExclude the flgExclude to set
     */
    public void setFlgExclude(String flgExclude) {
        this.flgExclude = flgExclude;
    }

    /**
     * @return the cmd
     */
    public String getCmd() {
        return cmd;
    }

    /**
     * @param cmd the cmd to set
     */
    public void setCmd(String cmd) {
        this.cmd = cmd;
    }
}
