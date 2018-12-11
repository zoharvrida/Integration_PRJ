/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author v00019722
 */
@SuppressWarnings("serial")
class TmpSMSTransPK implements Serializable {
    private BigDecimal cod_cust;
    private String cod_acct_no;
    private String profile;
    private BigDecimal qId;
    private String batchID;

    /**
     * @return the cod_cust
     */
    public BigDecimal getCod_cust() {
        return cod_cust;
    }

    /**
     * @param cod_cust the cod_cust to set
     */
    public void setCod_cust(BigDecimal cod_cust) {
        this.cod_cust = cod_cust;
    }

    /**
     * @return the cod_acct_no
     */
    public String getCod_acct_no() {
        return cod_acct_no;
    }

    /**
     * @param cod_acct_no the cod_acct_no to set
     */
    public void setCod_acct_no(String cod_acct_no) {
        this.cod_acct_no = cod_acct_no;
    }


    /**
     * @return the profile
     */
    public String getProfile() {
        return profile;
    }

    /**
     * @param profile the profile to set
     */
    public void setProfile(String profile) {
        this.profile = profile;
    }

    /**
     * @return the qId
     */
    public BigDecimal getqId() {
        return qId;
    }

    /**
     * @param qId the qId to set
     */
    public void setqId(BigDecimal qId) {
        this.qId = qId;
    }

    /**
     * @return the batchID
     */
    public String getBatchID() {
        return batchID;
    }

    /**
     * @param batchID the batchID to set
     */
    public void setBatchID(String batchID) {
        this.batchID = batchID;
    }
}
