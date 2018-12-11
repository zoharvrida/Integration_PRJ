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
    public double getLendingRate() {
		return this.lendingRate;
	}
    /**
     * 
     * @param lendingRate
     */
    public void setLendingRate(double lendingRate) {
		this.lendingRate = lendingRate;
	}
}
