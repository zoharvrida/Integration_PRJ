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
    public String getCodAcctNo() {
        return codAcctNo;
    }

    /**
     * 
     * @param codAcctNo
     */
    public void setCodAcctNo(String codAcctNo) {
        this.codAcctNo = codAcctNo;
    }

    /**
     * 
     * @return
     */
    public String getCodModule() {
        return codModule;
    }

    /**
     * 
     * @param codModule
     */
    public void setCodModule(String codModule) {
        this.codModule = codModule;
    }

    /**
     * 
     * @return
     */
    public Integer getCodProd() {
        return codProd;
    }

    /**
     * 
     * @param codProd
     */
    public void setCodProd(Integer codProd) {
        this.codProd = codProd;
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
    public String getMoveRdAccounts() {
        return moveRdAccounts;
    }

    /**
     * 
     * @param moveRdAccounts
     */
    public void setMoveRdAccounts(String moveRdAccounts) {
        this.moveRdAccounts = moveRdAccounts;
    }

    /**
     * 
     * @return
     */
    public String getMoveCollateral() {
        return moveCollateral;
    }

    /**
     * 
     * @param moveCollateral
     */
    public void setMoveCollateral(String moveCollateral) {
        this.moveCollateral = moveCollateral;
    }

}
