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
public class FcyTxnSummPendingTxn {
    private String noAcct;
    private Date dtmTxn;
    private Date dtPost;
    private String refTxn;
    private String idTxn;
    private String ccyTxn;
    private Double amtTxn;
    private Integer txnBranch;
    
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
     * @return the dtmTxn
     */
    public Date getDtmTxn() {
        return dtmTxn;
    }

    /**
     * @param dtmTxn the dtmTxn to set
     */
    public void setDtmTxn(Date dtmTxn) {
        this.dtmTxn = dtmTxn;
    }
    
    /**
     * @return the dtPost
     */
    public Date getDtPost() {
        return dtPost;
    }

    /**
     * @param dtPost the dtPost to set
     */
    public void setDtPost(Date dtPost) {
        this.dtPost = dtPost;
    }

    /**
     * @return the refTxn
     */
    public String getRefTxn() {
        return refTxn;
    }

    /**
     * @param refTxn the refTxn to set
     */
    public void setRefTxn(String refTxn) {
        this.refTxn = refTxn;
    }
    
    /**
     * @return the idTxn
     */
    public String getIdTxn() {
        return idTxn;
    }

    /**
     * @param idTxn the idTxn to set
     */
    public void setIdTxn(String idTxn) {
        this.idTxn = idTxn;
    }
    
    /**
     * @return the ccyTxn
     */
    public String getCcyTxn() {
        return ccyTxn;
    }

    /**
     * @param ccyTxn the ccyTxn to set
     */
    public void setCcyTxn(String ccyTxn) {
        this.ccyTxn = ccyTxn;
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
    
    /**
     * @return the txnBranch
     */
    public Integer getTxnBranch() {
        return txnBranch;
    }

    /**
     * @param txnBranch the txnBranch to set
     */
    public void setTxnBranch(Integer txnBranch) {
        this.txnBranch = txnBranch;
    }
}
