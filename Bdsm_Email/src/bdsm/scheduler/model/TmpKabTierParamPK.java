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
public class TmpKabTierParamPK implements java.io.Serializable {
	private String batchNo;
	private String currency;
	private double lendingRate;

	
	public String getBatchNo() {
		return this.batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getCurrency() {
		return this.currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public double getLendingRate() {
		return this.lendingRate;
	}
	public void setLendingRate(double lendingRate) {
		this.lendingRate = lendingRate;
	}
}
