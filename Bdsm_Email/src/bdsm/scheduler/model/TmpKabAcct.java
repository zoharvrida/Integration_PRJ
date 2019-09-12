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
public class TmpKabAcct extends bdsm.model.BaseModel implements java.io.Serializable {
	private TmpKabAcctPK compositeId;
	private String taxFlag;
	private String cif;
	private String currency;
	private String flagAccount;
	private java.util.Date dateEffStart;
	private String command;
	private String flagStatus;
	private String statusReason;
	
	
	public TmpKabAcctPK getCompositeId() {
		return this.compositeId;
	}
	public void setCompositeId(TmpKabAcctPK compositeId) {
		this.compositeId = compositeId;
	}
	
	public String getTaxFlag() {
		return this.taxFlag;
	}
	public void setTaxFlag(String taxFlag) {
		this.taxFlag = taxFlag;
	}

	public String getCif() {
		return this.cif;
	}
	public void setCif(String cif) {
		this.cif = cif;
	}
	
	public String getCurrency() {
		return this.currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	public String getFlagAccount() {
		return this.flagAccount;
	}
	public void setFlagAccount(String flagAccount) {
		this.flagAccount = flagAccount;
	}
	
	public java.util.Date getDateEffStart() {
		return this.dateEffStart;
	}
	public void setDateEffStart(java.util.Date dateEffStart) {
		this.dateEffStart = dateEffStart;
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

}
