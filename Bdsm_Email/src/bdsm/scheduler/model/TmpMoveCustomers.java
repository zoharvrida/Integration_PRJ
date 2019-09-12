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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public Integer getCodCustId() {
        return codCustId;
    }

    public void setCodCustId(Integer codCustId) {
        this.codCustId = codCustId;
    }

    public Integer getCodCurBrn() {
        return codCurBrn;
    }

    public void setCodCurBrn(Integer codCurBrn) {
        this.codCurBrn = codCurBrn;
    }

    public Integer getCodXferBrn() {
        return codXferBrn;
    }

    public void setCodXferBrn(Integer codXferBrn) {
        this.codXferBrn = codXferBrn;
    }

    public String getFlagStatus() {
        return flagStatus;
    }

    public void setFlagStatus(String flagStatus) {
        this.flagStatus = flagStatus;
    }
    
    public String getStatusReason() {
        return statusReason;
    }

    public void setStatusReason(String statusReason) {
        this.statusReason = statusReason;
    }
    
    public String getMoveCard() {
        return moveCard;
    }

    public void setMoveCard(String moveCard) {
        this.moveCard = moveCard;
    }

    
}
