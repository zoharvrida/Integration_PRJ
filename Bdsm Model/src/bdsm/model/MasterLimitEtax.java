/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author 00030663
 */
public class MasterLimitEtax implements Serializable {

    private String idUser;
    private String idTemplate;
    private Integer sumLimit;
    private String flgMntStatus;
    private String idCreatedBy;
    private String idCreatedBySpv;
    private Date dtmCreated;
    private Date dtmCreatedSpv;
    private String errCode;
    private String errDesc;

    /**
     * @return the idUser
     */
    public String getIdUser() {
        return idUser;
    }

    /**
     * @param idUser the idUser to set
     */
    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    /**
     * @return the idTemplate
     */
    public String getIdTemplate() {
        return idTemplate;
    }

    /**
     * @param idTemplate the idTemplate to set
     */
    public void setIdTemplate(String idTemplate) {
        this.idTemplate = idTemplate;
    }

    /**
     * @return the sumLimit
     */
    public Integer getSumLimit() {
        return sumLimit;
    }

    /**
     * @param sumLimit the sumLimit to set
     */
    public void setSumLimit(Integer sumLimit) {
        this.sumLimit = sumLimit;
    }

    /**
     * @return the flgMntStatus
     */
    public String getFlgMntStatus() {
        return flgMntStatus;
    }

    /**
     * @param flgMntStatus the flgMntStatus to set
     */
    public void setFlgMntStatus(String flgMntStatus) {
        this.flgMntStatus = flgMntStatus;
    }

    /**
     * @return the idCreatedBy
     */
    public String getIdCreatedBy() {
        return idCreatedBy;
    }

    /**
     * @param idCreatedBy the idCreatedBy to set
     */
    public void setIdCreatedBy(String idCreatedBy) {
        this.idCreatedBy = idCreatedBy;
    }

    /**
     * @return the idCreatedBySpv
     */
    public String getIdCreatedBySpv() {
        return idCreatedBySpv;
    }

    /**
     * @param idCreatedBySpv the idCreatedBySpv to set
     */
    public void setIdCreatedBySpv(String idCreatedBySpv) {
        this.idCreatedBySpv = idCreatedBySpv;
    }

    /**
     * @return the dtmCreated
     */
    public Date getDtmCreated() {
        return dtmCreated;
    }

    /**
     * @param dtmCreated the dtmCreated to set
     */
    public void setDtmCreated(Date dtmCreated) {
        this.dtmCreated = dtmCreated;
    }

    /**
     * @return the dtmCreatedSpv
     */
    public Date getDtmCreatedSpv() {
        return dtmCreatedSpv;
    }

    /**
     * @param dtmCreatedSpv the dtmCreatedSpv to set
     */
    public void setDtmCreatedSpv(Date dtmCreatedSpv) {
        this.dtmCreatedSpv = dtmCreatedSpv;
    }

    /**
     * @return the errCode
     */
    public String getErrCode() {
        return errCode;
    }

    /**
     * @param errCode the errCode to set
     */
    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    /**
     * @return the errDesc
     */
    public String getErrDesc() {
        return errDesc;
    }

    /**
     * @param errDesc the errDesc to set
     */
    public void setErrDesc(String errDesc) {
        this.errDesc = errDesc;
    }
}
