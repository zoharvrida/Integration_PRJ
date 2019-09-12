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

	
	public String getBatchNo() {
		return this.batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getCodeParam() {
		return codeParam;
	}
	public void setCodeParam(String codeParam) {
		this.codeParam = codeParam;
	}
	
	public String getOldValue() {
		return this.oldValue;
	}
	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}
	
	public String getValue() {
		return this.value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getNewValue() {
		return this.newValue;
	}
	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}
	
	public String getTypeParameter() {
		return this.typeParameter;
	}
	public void setTypeParameter(String typeParameter) {
		this.typeParameter = typeParameter;
	}

	public String getCommand() {
		return this.command;
	}
	public void setCommand(String command) {
		this.command = command;
	}

	public String getFlagStatus() {
		return this.flagStatus;
	}
	public void setFlagStatus(String flagStatus) {
		this.flagStatus = flagStatus;
	}

	public String getStatusReason() {
		return this.statusReason;
	}
	public void setStatusReason(String statusReason) {
		this.statusReason = statusReason;
	}
	
	
	/* Hibernate to Database properties */
	
	protected Character getTypeParameterDB() {
		return ((this.typeParameter == null)? null: this.typeParameter.charAt(0));
	}
	protected void setTypeParameterDB(Character typeParameter) {
		this.typeParameter = (typeParameter == null)? null: typeParameter.toString();
	}
	
	protected Character getCommandDB() {
		return ((this.command == null)? null: this.command.charAt(0));
	}
	protected void setCommandDB(Character command) {
		this.command = (command == null)? null: command.toString();
	}
	
	protected Character getFlagStatusDB() {
		return ((this.flagStatus == null)? null: this.flagStatus.charAt(0));
	}
	protected void setFlagStatusDB(Character flagStatus) {
		this.flagStatus = (flagStatus == null)? null: flagStatus.toString();
	}
}
