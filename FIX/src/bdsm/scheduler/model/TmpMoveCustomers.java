/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

import bdsm.model.BaseModel;

/**
 *
 * @author v00020841
 */
public class TmpMoveCustomers extends BaseModel{
    private Integer id;
    private String batchNo;
    private Integer codCustId;
    private Integer codCurBrn;
    private Integer codXferBrn;
    private String flagStatus;
    private String statusReason;
    private String moveCard;

    /**
     * 
     * @return
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * @return
     */
    public String getBatchNo() {
        return batchNo;
    }

    /**
     * 
     * @param batchNo
     */
    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    /**
     * 
     * @return
     */
    public Integer getCodCustId() {
        return codCustId;
    }

    /**
     * 
     * @param codCustId
     */
    public void setCodCustId(Integer codCustId) {
        this.codCustId = codCustId;
    }

    /**
     * 
     * @return
     */
    public Integer getCodCurBrn() {
        return codCurBrn;
    }

    /**
     * 
     * @param codCurBrn
     */
    public void setCodCurBrn(Integer codCurBrn) {
        this.codCurBrn = codCurBrn;
    }

    /**
     * 
     * @return
     */
    public Integer getCodXferBrn() {
        return codXferBrn;
    }

    /**
     * 
     * @param codXferBrn
     */
    public void setCodXferBrn(Integer codXferBrn) {
        this.codXferBrn = codXferBrn;
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
    public String getMoveCard() {
        return moveCard;
    }

    /**
     * 
     * @param moveCard
     */
    public void setMoveCard(String moveCard) {
        this.moveCard = moveCard;
    }

    
}
