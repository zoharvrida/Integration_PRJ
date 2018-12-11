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
	
	
    /**
     * 
     * @return
     */
    public TmpKabAcctPK getCompositeId() {
		return this.compositeId;
	}
    /**
     * 
     * @param compositeId
     */
    public void setCompositeId(TmpKabAcctPK compositeId) {
		this.compositeId = compositeId;
	}
	
    /**
     * 
     * @return
     */
    public String getTaxFlag() {
		return this.taxFlag;
	}
    /**
     * 
     * @param taxFlag
     */
    public void setTaxFlag(String taxFlag) {
		this.taxFlag = taxFlag;
	}

    /**
     * 
     * @return
     */
    public String getCif() {
		return this.cif;
	}
    /**
     * 
     * @param cif
     */
    public void setCif(String cif) {
		this.cif = cif;
	}
	
    /**
     * 
     * @return
     */
    public String getCurrency() {
		return this.currency;
	}
    /**
     * 
     * @param currency
     */
    public void setCurrency(String currency) {
		this.currency = currency;
	}
	
    /**
     * 
     * @return
     */
    public String getFlagAccount() {
		return this.flagAccount;
	}
    /**
     * 
     * @param flagAccount
     */
    public void setFlagAccount(String flagAccount) {
		this.flagAccount = flagAccount;
	}
	
    /**
     * 
     * @return
     */
    public java.util.Date getDateEffStart() {
		return this.dateEffStart;
	}
    /**
     * 
     * @param dateEffStart
     */
    public void setDateEffStart(java.util.Date dateEffStart) {
		this.dateEffStart = dateEffStart;
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

}
