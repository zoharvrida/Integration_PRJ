/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;

import java.util.Date;

/**
 *
 * @author v00013493
 */
public class FcyTxnSummLimUD {
    private String noAcct;
    private Integer codCust;
    private String namCustFull;
    private String codAcctCustRel;
    private String noUd;
    private String ccyUd;
    private Double amtLimit;
    private Double amtAvail;
    private Integer cdBranch;
    private Date dtExpiry;
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
     * @return the noUd
     */
    public String getNoUd() {
        return noUd;
    }

    /**
     * @param noUd the noUd to set
     */
    public void setNoUd(String noUd) {
        this.noUd = noUd;
    }
    
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
