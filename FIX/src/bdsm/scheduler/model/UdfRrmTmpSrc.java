/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

import bdsm.model.BaseModel;

/**
 *
 * @author v00020801
 */
public class UdfRrmTmpSrc extends BaseModel {

    private String codCustId;
    private String codFieldTag;
    private String codTask;
    private String fieldValue;
    private String oldValue;
    private String cmd;
    private String statusReason;
    private String flgStatus;
    private String noBatch;
	 private String flagCod;
    private Integer id;

    /**
     * @return the codCustId
     */
    public String getCodCustId() {
        return codCustId;
    }

    /**
     * @param codCustId the codCustId to set
     */
    public void setCodCustId(String codCustId) {
        this.codCustId = codCustId;
    }

    /**
     * @return the codFieldTag
     */
    public String getCodFieldTag() {
        return codFieldTag;
    }

    /**
     * @param codFieldTag the codFieldTag to set
     */
    public void setCodFieldTag(String codFieldTag) {
        this.codFieldTag = codFieldTag;
    }

    /**
     * @return the codTask
     */
    public String getCodTask() {
        return codTask;
    }

    /**
     * @param codTask the codTask to set
     */
    public void setCodTask(String codTask) {
        this.codTask = codTask;
    }

    /**
     * @return the fieldValue
     */
    public String getFieldValue() {
        return fieldValue;
    }

    /**
     * @param fieldValue the fieldValue to set
     */
    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    /**
     * @return the oldValue
     */
    public String getOldValue() {
        return oldValue;
    }

    /**
     * @param oldValue the oldValue to set
     */
    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    /**
     * @return the cmd
     */
    public String getCmd() {
        return cmd;
    }

    /**
     * @param cmd the cmd to set
     */
    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    /**
     * @return the statusReason
     */
    public String getStatusReason() {
        return statusReason;
    }

    /**
     * @param statusReason the statusReason to set
     */
    public void setStatusReason(String statusReason) {
        this.statusReason = statusReason;
    }



    /**
     * @return the flgStatus
     */
    public String getFlgStatus() {
        return flgStatus;
    }

    /**
     * @param flgStatus the flgStatus to set
     */
    public void setFlgStatus(String flgStatus) {
        this.flgStatus = flgStatus;
    }

    /**
     * @return the noBatch
     */
    public String getNoBatch() {
        return noBatch;
    }

    /**
     * @param noBatch the noBatch to set
     */
    public void setNoBatch(String noBatch) {
        this.noBatch = noBatch;
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
	 * @return the flagCod
	 */
	public String getFlagCod() {
		return flagCod;
	}

	/**
	 * @param flagCod the flagCod to set
	 */
	public void setFlagCod(String flagCod) {
		this.flagCod = flagCod;
	}

   


}
