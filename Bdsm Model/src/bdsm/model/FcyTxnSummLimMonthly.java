/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;

/**
 *
 * @author v00013493
 */
public class FcyTxnSummLimMonthly {
    private String noAcct;
    private Integer codCust;
    private String namCustFull;
    private String codAcctCustRel;
    private Double amtAvailUsd;
    private String action;
    private Integer slavail;
    private Integer period;
    private String flgAcct;

    /**
     * @return the noAcct
     */
    public String getNoAcct() {
        return noAcct;
    }

    /**
     * @param noAcct the noAcct to set
     */
    public void setNoAcct(String noAcct) {
        this.noAcct = noAcct;
    }
    
    /**
     * @return the codCust
     */
    public Integer getCodCust() {
        return codCust;
    }

    /**
     * @param codCust the codCust to set
     */
    public void setCodCust(Integer codCust) {
        this.codCust = codCust;
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
     * @return the codAcctCustRel
     */
    public String getCodAcctCustRel() {
        return codAcctCustRel;
    }

    /**
     * @param codAcctCustRel the codAcctCustRel to set
     */
    public void setCodAcctCustRel(String codAcctCustRel) {
        this.codAcctCustRel = codAcctCustRel;
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
     * @return the action
     */
    public String getAction() {
        return action;
    }

    /**
     * @param action the action to set
     */
    public void setAction(String action) {
        this.action = action;
    }
    
    /**
     * @return the slavail
     */
    public Integer getSlavail() {
        return slavail;
    }

    /**
     * @param slavail the slavail to set
     */
    public void setSlavail(Integer slavail) {
        this.slavail = slavail;
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
     * @return the flgAcct
     */
    public String getFlgAcct() {
        return flgAcct;
    }

    /**
     * @param flgAcct the flgAcct to set
     */
    public void setFlgAcct(String flgAcct) {
        this.flgAcct = flgAcct;
    }
}
