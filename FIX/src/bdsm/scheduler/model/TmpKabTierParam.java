/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * 
 * @author v00019237
 */
public class TmpKabTierParam extends bdsm.model.BaseModel {
	private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("dd/MM/yyyy");
	private TmpKabTierParamPK compositeId;
	private double kabLdr1;
	private double kabLdr2;
	private double kabLdr3;
	private double kabLdr4;
	private double kabLdr5;
	private String command;
	private Date effDate;
	private String flagStatus;
	private String statusReason;
	

    /**
     * 
     * @return
     */
    public TmpKabTierParamPK getCompositeId() {
		return this.compositeId;
	}
    /**
     * 
     * @param compositeId
     */
    public void setCompositeId(TmpKabTierParamPK compositeId) {
		this.compositeId = compositeId;
	}
	
    /**
     * 
     * @return
     */
    public double getKabLdr1() {
		return this.kabLdr1;
	}
    /**
     * 
     * @param kabLdr1
     */
    public void setKabLdr1(double kabLdr1) {
		this.kabLdr1 = kabLdr1;
	}

    /**
     * 
     * @return
     */
    public double getKabLdr2() {
		return this.kabLdr2;
	}
    /**
     * 
     * @param kabLdr2
     */
    public void setKabLdr2(double kabLdr2) {
		this.kabLdr2 = kabLdr2;
	}

    /**
     * 
     * @return
     */
    public double getKabLdr3() {
		return this.kabLdr3;
	}
    /**
     * 
     * @param kabLdr3
     */
    public void setKabLdr3(double kabLdr3) {
		this.kabLdr3 = kabLdr3;
	}

    /**
     * 
     * @return
     */
    public double getKabLdr4() {
		return this.kabLdr4;
	}
    /**
     * 
     * @param kabLdr4
     */
    public void setKabLdr4(double kabLdr4) {
		this.kabLdr4 = kabLdr4;
	}

    /**
     * 
     * @return
     */
    public double getKabLdr5() {
		return this.kabLdr5;
	}
    /**
     * 
     * @param kabLdr5
     */
    public void setKabLdr5(double kabLdr5) {
		this.kabLdr5 = kabLdr5;
	}

    /**
     * 
     * @return
     */
    public Date getEffDate() {
		return this.effDate;
	}
    /**
     * 
     * @param effDate
     */
    public void setEffDate(Date effDate) {
		this.effDate = effDate;
	}
    /**
     * 
     * @param effDate
     * @throws ParseException
     */
    public void setEffDate(String effDate) throws ParseException {
		this.effDate = DATE_FORMATTER.parse(effDate);
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
