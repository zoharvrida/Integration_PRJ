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
public class TmpMaintenanceRrm01 extends BaseModel{
    private TmpMaintenanceRrm01PK compositeId;
    private String cmd;
    private String namAlias;
    private String namMother;
    private String golPemilik;
    private String statusPemilik;
    private String branchLocation;
    private String sandiData;
    private String relBank;
    private String relConnBank;
    private String flagStatus;
    private String statusReason;

    /**
     * 
     * @return
     */
    public TmpMaintenanceRrm01PK getCompositeId() {
        return compositeId;
    }

    /**
     * 
     * @param compositeId
     */
    public void setCompositeId(TmpMaintenanceRrm01PK compositeId) {
        this.compositeId = compositeId;
    }

    /**
     * 
     * @return
     */
    public String getCmd() {
        return cmd;
    }

    /**
     * 
     * @param cmd
     */
    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    /**
     * 
     * @return
     */
    public String getNamAlias() {
        return namAlias;
    }

    /**
     * 
     * @param namAlias
     */
    public void setNamAlias(String namAlias) {
        this.namAlias = namAlias;
    }

    /**
     * 
     * @return
     */
    public String getNamMother() {
        return namMother;
    }

    /**
     * 
     * @param namMother
     */
    public void setNamMother(String namMother) {
        this.namMother = namMother;
    }

    /**
     * 
     * @return
     */
    public String getGolPemilik() {
        return golPemilik;
    }

    /**
     * 
     * @param golPemilik
     */
    public void setGolPemilik(String golPemilik) {
        this.golPemilik = golPemilik;
    }

    /**
     * 
     * @return
     */
    public String getStatusPemilik() {
        return statusPemilik;
    }

    /**
     * 
     * @param statusPemilik
     */
    public void setStatusPemilik(String statusPemilik) {
        this.statusPemilik = statusPemilik;
    }

    /**
     * 
     * @return
     */
    public String getBranchLocation() {
        return branchLocation;
    }

    /**
     * 
     * @param branchLocation
     */
    public void setBranchLocation(String branchLocation) {
        this.branchLocation = branchLocation;
    }

    /**
     * 
     * @return
     */
    public String getSandiData() {
        return sandiData;
    }

    /**
     * 
     * @param sandiData
     */
    public void setSandiData(String sandiData) {
        this.sandiData = sandiData;
    }

    /**
     * 
     * @return
     */
    public String getRelBank() {
        return relBank;
    }

    /**
     * 
     * @param relBank
     */
    public void setRelBank(String relBank) {
        this.relBank = relBank;
    }

    /**
     * 
     * @return
     */
    public String getRelConnBank() {
        return relConnBank;
    }

    /**
     * 
     * @param relConnBank
     */
    public void setRelConnBank(String relConnBank) {
        this.relConnBank = relConnBank;
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
    
    
}
