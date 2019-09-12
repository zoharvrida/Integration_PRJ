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
public class FcyTxnManInputUD {
    private String noUd;
    private String ccyUd;
    private Double amtLimit;
    private Double amtAvail;
    private Integer cdBranch;
    private Date dtExpiry;
    private Integer noCif;
    private Double amtTxn;
    
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
     * @return the amtTxn
     */
    public Double getAmtTxn() {
        return amtTxn;
    }

    /**
     * @param amtTxn the amtTxn to set
     */
    public void setAmtTxn(Double amtTxn) {
        this.amtTxn = amtTxn;
    }    
}
