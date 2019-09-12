/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author user
 */
public class MfcUdMaster extends BaseModel {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); 
    private MfcUdMasterPK compositeId;
    private String typeUD;
    private String payeeName;
    private String payeeCountry;
    private String tradingProduct;
    private Date dtIssued;
    private Date dtExpiry;
    private String ccyUd;
    private Double amtLimit;
    private Double amtAvail;
    private Integer cdBranch;
    private String status;
    private String remarks;
    private Double amtLimitUsd;
    private Double amtAvailUsd;
    private Double ratFcyLim;
    private Double ratUsdLim;

    @Override
    public String toString() {
        return "MfcUdMaster{" + "dateFormat=" + dateFormat + ", compositeId=" + compositeId + ", typeUD=" + typeUD + ", payeeName=" + payeeName + ", payeeCountry=" + payeeCountry + ", tradingProduct=" + tradingProduct + ", dtIssued=" + dtIssued + ", dtExpiry=" + dtExpiry + ", ccyUd=" + ccyUd + ", amtLimit=" + amtLimit + ", amtAvail=" + amtAvail + ", cdBranch=" + cdBranch + ", status=" + status + ", remarks=" + remarks + ", amtLimitUsd=" + amtLimitUsd + ", amtAvailUsd=" + amtAvailUsd + ", ratFcyLim=" + ratFcyLim + ", ratUsdLim=" + ratUsdLim + '}';
    }
    
    
    /**
     * @return the compositeId
     */
    public MfcUdMasterPK getCompositeId() {
        return compositeId;
    }
    
    /**
     * @param compositeId the compositeId to set
     */
    public void setCompositeId(MfcUdMasterPK compositeId) {
        this.compositeId = compositeId;
    }
    
    /**
     * @return the typeUD
     */
    public String getTypeUD() {
        return this.typeUD;
    }
    
    /**
     * @param typeUD the typeUD to set
     */
    public void setTypeUD(String typeUD) {
        this.typeUD = typeUD;
    }
    
    /**
     * @return the payeeName
     */
    public String getPayeeName() {
        return this.payeeName;
    }
    
    /**
     * @param payeeName the payeeName to set
     */
    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }
    
    /**
     * @return the payeeCountry
     */
    public String getPayeeCountry() {
        return this.payeeCountry;
    }
    
    /**
     * @param payeeCountry the payeeCountry to set
     */
    public void setPayeeCountry(String payeeCountry) {
        this.payeeCountry = payeeCountry;
    }
    
    /**
     * @return the tradingProduct
     */
    public String getTradingProduct() {
        return this.tradingProduct;
    }
    
    /**
     * @param tradingProduct the tradingProduct to set
     */
    public void setTradingProduct(String tradingProduct) {
        this.tradingProduct = tradingProduct;
    }
    
    /**
     * @return the dtIssued
     */
    public Date getDtIssued() {
        return this.dtIssued;
    }
    
    /**
     * @param dtIssued the dtIssued to set
     */
    public void setDtIssued(Date dtIssued) {
        this.dtIssued = dtIssued;
    }
    
    /**
    * @return the dtExpiry
    */
    public Date getDtExpiry() {
        return dtExpiry;
    }

    /**
     * @param dtExpiry the dtExpiry to set
     */
    public void setDtExpiry(Date dtExpiry) {
        this.dtExpiry = dtExpiry;
    }

    /**
     * @param dtExpiry the dtExpiry to set
     */
    public void setDtExpiry(String dtExpiry)  throws ParseException{
        this.dtExpiry = dateFormat.parse(dtExpiry);
    }
    
    /**
     * @param dtExpiry the dtExpiry to set
     */
    
    public void setDtExpiry(String[] dtExpiry) throws ParseException {
        if (dtExpiry == null || dtExpiry.length==0) return;
        this.dtExpiry = dateFormat.parse(dtExpiry[0]);
    }

    /**
     * @param dtExpiry the dtExpiry to set
     */
    /*
    public void setDtExpiry(List<String> dtExpiry) throws ParseException {
        if (dtExpiry == null || dtExpiry.size()==0) return;
        this.dtExpiry = dateFormat.parse(dtExpiry.get(0));
    }
    */
    /**
    * @return the ccyUd
    */
    public String getCcyUd() {
        return ccyUd;
    }

    /**
     * @param ccyUd the ccyUd to set
     */
    public void setCcyUd(String ccyUd) {
        this.ccyUd = ccyUd;
    }
    
    /**
    * @return the amtLimit
    */
    public Double getAmtLimit() {
        return amtLimit;
    }

    /**
     * @param amtLimit the amtLimit to set
     */
    public void setAmtLimit(Double amtLimit) {
        this.amtLimit = amtLimit;
    }

    /**
    * @return the amtAvail
    */
    public Double getAmtAvail() {
        return amtAvail;
    }

    /**
     * @param amtAvail the amtAvail to set
     */
    public void setAmtAvail(Double amtAvail) {
        this.amtAvail = amtAvail;
    }

    /**
    * @return the cdBranch
    */
    public Integer getCdBranch() {
        return cdBranch;
    }

    /**
     * @param cdBranch the cdBranch to set
     */
    public void setCdBranch(Integer cdBranch) {
        this.cdBranch = cdBranch;
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
    * @return the remarks
    */
    public String getRemarks() {
        return remarks;
    }

    /**
     * @param remarks the remarks to set
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * @return the amtLimitUsd
     */
    public Double getAmtLimitUsd() {
        return amtLimitUsd;
    }

    /**
     * @param amtLimitUsd the amtLimitUsd to set
     */
    public void setAmtLimitUsd(Double amtLimitUsd) {
        this.amtLimitUsd = amtLimitUsd;
    }

    /**
     * @return the amtAvailUsd
     */
    public Double getAmtAvailUsd() {
        return amtAvailUsd;
    }

    /**
     * @param amtAvailUsd the amtAvailUsd to set
     */
    public void setAmtAvailUsd(Double amtAvailUsd) {
        this.amtAvailUsd = amtAvailUsd;
    }

    /**
     * @return the ratFcyLim
     */
    public Double getRatFcyLim() {
        return ratFcyLim;
    }

    /**
     * @param ratFcyLim the ratFcyLim to set
     */
    public void setRatFcyLim(Double ratFcyLim) {
        this.ratFcyLim = ratFcyLim;
    }

    /**
     * @return the ratUsdLim
     */
    public Double getRatUsdLim() {
        return ratUsdLim;
    }

    /**
     * @param ratUsdLim the ratUsdLim to set
     */
    public void setRatUsdLim(Double ratUsdLim) {
        this.ratUsdLim = ratUsdLim;
    }
    
    
    
    @Override
    public boolean equals(Object obj) {
    	if (obj instanceof MfcUdMaster) {
    		MfcUdMaster other = (MfcUdMaster) obj;
    		return (
    			this.compositeId.getNoUd().equals(other.compositeId.getNoUd())
    			&& this.typeUD.equals(other.typeUD)
    			&& this.payeeName.equals(other.payeeName)
    			&& this.payeeCountry.equals(other.payeeCountry)
    			&& this.tradingProduct.equals(other.tradingProduct)
    			&& this.dtIssued.equals(other.dtIssued)
    		);
    	}
    	
    	return false;
    }

    
}
