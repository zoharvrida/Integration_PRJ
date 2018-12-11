/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

import bdsm.model.BaseModel;
import java.util.Date;

/**
 *
 * @author v00019722
 */
public class BOKprLebih extends BaseModel{
    private String IDBatch;
    private String lnAcct;
    private String chAcct;
    private Integer Benefit;
    private Date datProcess;
    private String flg_auth;
    private String debiturName;
    private String comment;

    /**
     * @return the lnAcct
     */
    public String getLnAcct() {
        return lnAcct;
    }

    /**
     * @param lnAcct the lnAcct to set
     */
    public void setLnAcct(String lnAcct) {
        this.lnAcct = lnAcct;
    }

    /**
     * @return the chAcct
     */
    public String getChAcct() {
        return chAcct;
    }

    /**
     * @param chAcct the chAcct to set
     */
    public void setChAcct(String chAcct) {
        this.chAcct = chAcct;
    }

    /**
     * @return the Benefit
     */
    public Integer getBenefit() {
        return Benefit;
    }

    /**
     * @param Benefit the Benefit to set
     */
    public void setBenefit(Integer Benefit) {
        this.Benefit = Benefit;
    }

    /**
     * @return the datProcess
     */
    public Date getDatProcess() {
        return datProcess;
    }

    /**
     * @param datProcess the datProcess to set
     */
    public void setDatProcess(Date datProcess) {
        this.datProcess = datProcess;
    }

    /**
     * @return the flg_auth
     */
    public String getFlg_auth() {
        return flg_auth;
    }

    /**
     * @param flg_auth the flg_auth to set
     */
    public void setFlg_auth(String flg_auth) {
        this.flg_auth = flg_auth;
    }

    /**
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * @return the IDBatch
     */
    public String getIDBatch() {
        return IDBatch;
    }

    /**
     * @param IDBatch the IDBatch to set
     */
    public void setIDBatch(String IDBatch) {
        this.IDBatch = IDBatch;
    }

    /**
     * @return the debiturName
     */
    public String getDebiturName() {
        return debiturName;
    }

    /**
     * @param debiturName the debiturName to set
     */
    public void setDebiturName(String debiturName) {
        this.debiturName = debiturName;
    }
}
