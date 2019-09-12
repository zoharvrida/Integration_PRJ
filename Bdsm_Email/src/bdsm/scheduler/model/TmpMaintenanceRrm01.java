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

    public TmpMaintenanceRrm01PK getCompositeId() {
        return compositeId;
    }

    public void setCompositeId(TmpMaintenanceRrm01PK compositeId) {
        this.compositeId = compositeId;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getNamAlias() {
        return namAlias;
    }

    public void setNamAlias(String namAlias) {
        this.namAlias = namAlias;
    }

    public String getNamMother() {
        return namMother;
    }

    public void setNamMother(String namMother) {
        this.namMother = namMother;
    }

    public String getGolPemilik() {
        return golPemilik;
    }

    public void setGolPemilik(String golPemilik) {
        this.golPemilik = golPemilik;
    }

    public String getStatusPemilik() {
        return statusPemilik;
    }

    public void setStatusPemilik(String statusPemilik) {
        this.statusPemilik = statusPemilik;
    }

    public String getBranchLocation() {
        return branchLocation;
    }

    public void setBranchLocation(String branchLocation) {
        this.branchLocation = branchLocation;
    }

    public String getSandiData() {
        return sandiData;
    }

    public void setSandiData(String sandiData) {
        this.sandiData = sandiData;
    }

    public String getRelBank() {
        return relBank;
    }

    public void setRelBank(String relBank) {
        this.relBank = relBank;
    }

    public String getRelConnBank() {
        return relConnBank;
    }

    public void setRelConnBank(String relConnBank) {
        this.relConnBank = relConnBank;
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
    
    
}
