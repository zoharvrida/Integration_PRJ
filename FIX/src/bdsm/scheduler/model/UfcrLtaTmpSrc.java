/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

import bdsm.model.BaseModel;

/**
 *
 * @author NCBS
 */
public class UfcrLtaTmpSrc extends BaseModel{

    private String macroName;
    private String macroValue;
    private String macroValueOld;
    private String status;
    private String statusreason;
    private String nobatch;
    private Integer id;

    /**
     * @return the macroName
     */
    public String getMacroName() {
        return macroName;
    }

    /**
     * @param macroName the macroName to set
     */
    public void setMacroName(String macroName) {
        this.macroName = macroName;
    }

    /**
     * @return the macroValue
     */
    public String getMacroValue() {
        return macroValue;
    }

    /**
     * @param macroValue the macroValue to set
     */
    public void setMacroValue(String macroValue) {
        this.macroValue = macroValue;
    }


    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the statusreason
     */
    public String getStatusreason() {
        return statusreason;
    }

    /**
     * @param statusreason the statusreason to set
     */
    public void setStatusreason(String statusreason) {
        this.statusreason = statusreason;
    }

    /**
     * @return the nobatch
     */
    public String getNobatch() {
        return nobatch;
    }

    /**
     * @param nobatch the nobatch to set
     */
    public void setNobatch(String nobatch) {
        this.nobatch = nobatch;
    }

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
     * @return the macroValueOld
     */
    public String getMacroValueOld() {
        return macroValueOld;
    }

    /**
     * @param macroValueOld the macroValueOld to set
     */
    public void setMacroValueOld(String macroValueOld) {
        this.macroValueOld = macroValueOld;
    }
}
