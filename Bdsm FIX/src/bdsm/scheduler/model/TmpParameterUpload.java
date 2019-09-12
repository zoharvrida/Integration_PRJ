/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;


/**
 * 
 * @author v00019237
 */
public class TmpParameterUpload extends bdsm.model.BaseModel implements java.io.Serializable {
	private String batchNo;
	private String codeParam;
	private String oldValue;
	private String value;
	private String newValue;
	private String typeParameter;
	private String command;
	private String flagStatus;
	private String statusReason;

	
    /**
     * 
     * @return
     */
    public String getBatchNo() {
		return this.batchNo;
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
    public String getCodeParam() {
		return codeParam;
	}
    /**
     * 
     * @param codeParam
     */
    public void setCodeParam(String codeParam) {
		this.codeParam = codeParam;
	}
	
    /**
     * 
     * @return
     */
    public String getOldValue() {
		return this.oldValue;
	}
    /**
     * 
     * @param oldValue
     */
    public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}
	
    /**
     * 
     * @return
     */
    public String getValue() {
		return this.value;
	}
    /**
     * 
     * @param value
     */
    public void setValue(String value) {
		this.value = value;
	}
	
    /**
     * 
     * @return
     */
    public String getNewValue() {
		return this.newValue;
	}
    /**
     * 
     * @param newValue
     */
    public void setNewValue(String newValue) {
		this.newValue = newValue;
	}
	
    /**
     * 
     * @return
     */
    public String getTypeParameter() {
		return this.typeParameter;
	}
    /**
     * 
     * @param typeParameter
     */
    public void setTypeParameter(String typeParameter) {
		this.typeParameter = typeParameter;
	}

    /**
     * 
     * @return
     */
    public String getCommand() {
		return this.command;
	}
    /**
     * 
     * @param command
     */
    public void setCommand(String command) {
		this.command = command;
	}

    /**
     * 
     * @return
     */
    public String getFlagStatus() {
		return this.flagStatus;
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
		return this.statusReason;
	}
    /**
     * 
     * @param statusReason
     */
    public void setStatusReason(String statusReason) {
		this.statusReason = statusReason;
	}
	
	
	/* Hibernate to Database properties */
	
    /**
     * 
     * @return
     */
    protected Character getTypeParameterDB() {
		return ((this.typeParameter == null)? null: this.typeParameter.charAt(0));
	}
    /**
     * 
     * @param typeParameter
     */
    protected void setTypeParameterDB(Character typeParameter) {
		this.typeParameter = (typeParameter == null)? null: typeParameter.toString();
	}
	
    /**
     * 
     * @return
     */
    protected Character getCommandDB() {
		return ((this.command == null)? null: this.command.charAt(0));
	}
    /**
     * 
     * @param command
     */
    protected void setCommandDB(Character command) {
		this.command = (command == null)? null: command.toString();
	}
	
    /**
     * 
     * @return
     */
    protected Character getFlagStatusDB() {
		return ((this.flagStatus == null)? null: this.flagStatus.charAt(0));
	}
    /**
     * 
     * @param flagStatus
     */
    protected void setFlagStatusDB(Character flagStatus) {
		this.flagStatus = (flagStatus == null)? null: flagStatus.toString();
	}
}
