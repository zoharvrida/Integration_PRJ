/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

import bdsm.model.BaseModel;

/**
 *
 * @author v00013493
 */
public class ExtMfcDetails extends BaseModel {
    private Long recordId;
    private String batchId;
    private String recType;
    private String noCif;
    private String noAcct;
    private String refTxn;
    private String typMsg;
    private String typAcct;
    private String dtPost;
    private String dtValue;
    private String dtmTxn;
    private String ccyTxn;
    private String amtTxn;
    private String ratFcyIdr;
    private String amtTxnLcy;
    private String txnDesc;
    private String txnBranch;
    private String noUd;
    private String udLmtAmt;
    private String udDtExp;
    private String respCode;
    private String comments;
    private String data;

    /**
     * @return the recordId
     */
    public Long getRecordId() {
        return recordId;
    }

    /**
     * @param recordId the recordId to set
     */
    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    /**
     * @return the batchId
     */
    public String getBatchId() {
        return batchId;
    }

    /**
     * @param batchId the batchId to set
     */
    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }
    
    /**
     * @return the recType
     */
    public String getRecType() {
        return recType;
    }

    /**
     * @param recType the recType to set
     */
    public void setRecType(String recType) {
        this.recType = recType;
    }
    
    /**
     * @return the noCif
     */
    public String getNoCif() {
        return noCif;
    }

    /**
     * @param noCif the noCif to set
     */
    public void setNoCif(String noCif) {
        this.noCif = noCif;
    }
    
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
     * @return the typMsg
     */
    public String getTypMsg() {
        return typMsg;
    }

    /**
     * @param typMsg the typMsg to set
     */
    public void setTypMsg(String typMsg) {
        this.typMsg = typMsg;
    }
    
    /**
     * @return the typAcct
     */
    public String getTypAcct() {
        return typAcct;
    }

    /**
     * @param typAcct the typAcct to set
     */
    public void setTypAcct(String typAcct) {
        this.typAcct = typAcct;
    }
    
    /**
     * @return the dtPost
     */
    public String getDtPost() {
        return dtPost;
    }

    /**
     * @param dtPost the dtPost to set
     */
    public void setDtPost(String dtPost) {
        this.dtPost = dtPost;
    }
    
    /**
     * @return the dtValue
     */
    public String getDtValue() {
        return dtValue;
    }

    /**
     * @param dtValue the dtValue to set
     */
    public void setDtValue(String dtValue) {
        this.dtValue = dtValue;
    }
    
    /**
     * @return the dtmTxn
     */
    public String getDtmTxn() {
        return dtmTxn;
    }

    /**
     * @param dtmTxn the dtmTxn to set
     */
    public void setDtmTxn(String dtmTxn) {
        this.dtmTxn = dtmTxn;
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
    public String getAmtTxn() {
        return amtTxn;
    }

    /**
     * @param amtTxn the amtTxn to set
     */
    public void setAmtTxn(String amtTxn) {
        this.amtTxn = amtTxn;
    }

    /**
     * @return the ratFcyIdr
     */
    public String getRatFcyIdr() {
        return ratFcyIdr;
    }

    /**
     * @param ratFcyIdr the ratFcyIdr to set
     */
    public void setRatFcyIdr(String ratFcyIdr) {
        this.ratFcyIdr = ratFcyIdr;
    }

    /**
     * @return the amtTxnLcy
     */
    public String getAmtTxnLcy() {
        return amtTxnLcy;
    }

    /**
     * @param amtTxnLcy the amtTxnLcy to set
     */
    public void setAmtTxnLcy(String amtTxnLcy) {
        this.amtTxnLcy = amtTxnLcy;
    }
    
    /**
     * @return the txnDesc
     */
    public String getTxnDesc() {
        return txnDesc;
    }

    /**
     * @param txnDesc the txnDesc to set
     */
    public void setTxnDesc(String txnDesc) {
        this.txnDesc = txnDesc;
    }
    
    /**
     * @return the txnBranch
     */
    public String getTxnBranch() {
        return txnBranch;
    }

    /**
     * @param txnBranch the txnBranch to set
     */
    public void setTxnBranch(String txnBranch) {
        this.txnBranch = txnBranch;
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
     * @return the udLmtAmt
     */
    public String getUdLmtAmt() {
        return udLmtAmt;
    }

    /**
     * @param udLmtAmt the udLmtAmt to set
     */
    public void setUdLmtAmt(String udLmtAmt) {
        this.udLmtAmt = udLmtAmt;
    }
    
    /**
     * @return the udDtExp
     */
    public String getUdDtExp() {
        return udDtExp;
    }

    /**
     * @param udDtExp the udDtExp to set
     */
    public void setUdDtExp(String udDtExp) {
        this.udDtExp = udDtExp;
    }
    
    /**
     * @return the respCode
     */
    public String getRespCode() {
        return respCode;
    }

    /**
     * @param respCode the respCode to set
     */
    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }
    
    /**
     * @return the comments
     */
    public String getComments() {
        return comments;
    }

    /**
     * @param comments the comments to set
     */
    public void setComments(String comments) {
        this.comments = comments;
    }
    
    /**
     * @return the data
     */
    public String getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(String data) {
        this.data = data;
    }
}
