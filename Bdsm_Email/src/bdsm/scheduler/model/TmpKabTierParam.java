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
	

	public TmpKabTierParamPK getCompositeId() {
		return this.compositeId;
	}
	public void setCompositeId(TmpKabTierParamPK compositeId) {
		this.compositeId = compositeId;
	}
	
	public double getKabLdr1() {
		return this.kabLdr1;
	}
	public void setKabLdr1(double kabLdr1) {
		this.kabLdr1 = kabLdr1;
	}

	public double getKabLdr2() {
		return this.kabLdr2;
	}
	public void setKabLdr2(double kabLdr2) {
		this.kabLdr2 = kabLdr2;
	}

	public double getKabLdr3() {
		return this.kabLdr3;
	}
	public void setKabLdr3(double kabLdr3) {
		this.kabLdr3 = kabLdr3;
	}

	public double getKabLdr4() {
		return this.kabLdr4;
	}
	public void setKabLdr4(double kabLdr4) {
		this.kabLdr4 = kabLdr4;
	}

	public double getKabLdr5() {
		return this.kabLdr5;
	}
	public void setKabLdr5(double kabLdr5) {
		this.kabLdr5 = kabLdr5;
	}

	public Date getEffDate() {
		return this.effDate;
	}
	public void setEffDate(Date effDate) {
		this.effDate = effDate;
	}
	public void setEffDate(String effDate) throws ParseException {
		this.effDate = DATE_FORMATTER.parse(effDate);
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
