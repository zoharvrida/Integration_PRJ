/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;

/**
 *
 * @author v00013493
 */
public class FcyTxnManInputCIF {
    private Integer noCif;
    private Integer period;
    private String namCustFull;
    private Double amtAvailUsd;
    
    /**
     * @return the noCif
     */
    public Integer getNoCif() {
        return noCif;
    }

    /**
     * @param noCif the noCif to set
     */
    public void setNoCif(Integer noCif) {
        this.noCif = noCif;
    }    
    
    /**
     * @return the period
     */
    public Integer getPeriod() {
        return period;
    }

    /**
     * @param period the period to set
     */
    public void setPeriod(Integer period) {
        this.period = period;
    }    
    
    /**
     * @return the namCustFull
     */
    public String getNamCustFull() {
        return namCustFull;
    }

    /**
     * @param namCustFull the namCustFull to set
     */
    public void setNamCustFull(String namCustFull) {
        this.namCustFull = namCustFull;
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
}
