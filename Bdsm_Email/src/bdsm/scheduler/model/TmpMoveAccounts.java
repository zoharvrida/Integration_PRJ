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
public class TmpMoveAccounts extends BaseModel{
    private Integer id;
    private String batchNo;
    private String codAcctNo;
    private String codModule;
    private Integer codProd;
    private Integer codCurBrn;
    private Integer codXferBrn;
    private String flagStatus;
    private String statusReason;
    private String moveRdAccounts;
    private String moveCollateral;

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

    public String getCodAcctNo() {
        return codAcctNo;
    }

    public void setCodAcctNo(String codAcctNo) {
        this.codAcctNo = codAcctNo;
    }

    public String getCodModule() {
        return codModule;
    }

    public void setCodModule(String codModule) {
        this.codModule = codModule;
    }

    public Integer getCodProd() {
        return codProd;
    }

    public void setCodProd(Integer codProd) {
        this.codProd = codProd;
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

    public String getMoveRdAccounts() {
        return moveRdAccounts;
    }

    public void setMoveRdAccounts(String moveRdAccounts) {
        this.moveRdAccounts = moveRdAccounts;
    }

    public String getMoveCollateral() {
        return moveCollateral;
    }

    public void setMoveCollateral(String moveCollateral) {
        this.moveCollateral = moveCollateral;
    }

}