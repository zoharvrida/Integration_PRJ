/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;

import java.sql.Date;

/**
 *
 * @author 00030663
 */
public class MasterLimitEtax {   
    private String idUser;
    private String idTemplate;
    private Integer sumLimit;
    private String flgMntStatus;
    private String idCreatedBy;
    private String idCreatedBySpv;
    private Date dtmCreated;
    private Date dtmCreatedSpv; 
    private String ErrCode;
    private String ErrDesc;

    /**
     * @return the namBank
     */
    
    public String getErrCode() {
        return ErrCode;
    }

    /**
     * @param namBank the namBank to set
     */
    public void setErrCode(String ErrCode) {
        this.ErrCode = ErrCode;
    }
    
    public String getErrDesc() {
        return ErrDesc;
    }

    /**
     * @param namBank the namBank to set
     */
    public void setErrDesc(String ErrDesc) {
        this.ErrDesc = ErrDesc;
    }
    
    public String getidUser() {
        return idUser;
    }

    /**
     * @param namBank the namBank to set
     */
    public void setidUser(String idUser) {
        this.idUser = idUser;
    }

    /**
     * @return the namBankShrt
     */
    public String getidTemplate() {
        return idTemplate;
    }

    /**
     * @param namBankShrt the namBankShrt to set
     */
    public void setidTemplate(String idTemplate) {
        this.idTemplate = idTemplate;
    }
    
    /**
     * @return the namBankShrt
     */
    public Integer getsumLimit() {
        return sumLimit;
    }

    /**
     * @param namBankShrt the namBankShrt to set
     */
    public void setsumLimit(Integer sumLimit) {
        this.sumLimit = sumLimit;
    }
    
     /**
     * @return the flgmntstatus
     */
    public String getflgMntStatus() {
        return flgMntStatus;
    }

    /**
     * @param namBankShrt the flgmntstatus to set
     */
    public void setflgMntStatus(String flgMntStatus) {
        this.flgMntStatus = flgMntStatus;
    }
    
     /**
     * @return the flgmntstatus
     */
    public String getidCreatedBy() {
        return idCreatedBy;
    }

    /**
     * @param namBankShrt the namBankShrt to set
     */
    public void setidCreatedBy(String idCreatedBy) {
        this.idCreatedBy = idCreatedBy;
    }
    
    /**
     * @return the flgmntstatus
     */
    public String getidCreatedBySpv() {
        return idCreatedBySpv;
    }

    /**
     * @param namBankShrt the namBankShrt to set
     */
    public void setidCreatedBySpv(String idCreatedBySpv) {
        this.idCreatedBySpv = idCreatedBySpv;
    }

    /**
     * @return the datLastProcess
     */
    public Date getdtmCreated() {
        return dtmCreated;
    }

    /**
     * @param datLastProcess the datLastProcess to set
     */
    public void setdtmCreated(Date dtmCreated) {
        this.dtmCreated = dtmCreated;
    }

    /**
     * @return the datProcess
     */
    public Date getdtmCreatedSpv() {
        return dtmCreatedSpv;
    }

    /**
     * @param datProcess the datProcess to set
     */
    public void setdtmCreatedSpv(Date dtmCreatedSpv) {
        this.dtmCreatedSpv = dtmCreatedSpv;
    }   
}
