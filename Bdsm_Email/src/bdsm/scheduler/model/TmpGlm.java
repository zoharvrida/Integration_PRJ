/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bdsm.scheduler.model;

/**
 *
 * @author V00020654
 */



public class TmpGlm extends bdsm.model.BaseModel{
    
    private Integer id;
    private String batchNo;
    private String codGLAcct;
    private Integer codCCBrn;
    private Integer codLob;
    private String flagStatus;
    private String reason;
   
    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the batchNo
     */
    public String getBatchNo() {
        return batchNo;
    }

    /**
     * @param batchNo the batchNo to set
     */
    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    /**
     * @return the codGLAcct
     */
    public String getCodGLAcct() {
        return codGLAcct;
    }

    /**
     * @param codGLAcct the codGLAcct to set
     */
    public void setCodGLAcct(String codGLAcct) {
        this.codGLAcct = codGLAcct;
    }

    /**
     * @return the codCCBrn
     */
    public Integer getCodCCBrn() {
        return codCCBrn;
    }

    /**
     * @param codCCBrn the codCCBrn to set
     */
    public void setCodCCBrn(Integer codCCBrn) {
        this.codCCBrn = codCCBrn;
    }

    /**
     * @return the codLob
     */
    public Integer getCodLob() {
        return codLob;
    }

    /**
     * @param codLob the codLob to set
     */
    public void setCodLob(Integer codLob) {
        this.codLob = codLob;
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
     * @return the reason
     */
    public String getReason() {
        return reason;
    }

    /**
     * @param reason the reason to set
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * @return the idCreateBy
     */
   
   
}
