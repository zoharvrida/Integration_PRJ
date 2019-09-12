/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author user
 */
public class MfcNoBook extends BaseModel {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); 
    private MfcNoBookPK compositeId;
    private String typAcct;
    private Date dtPost;
    private String strDtPost;
    private Date dtValue;
    private Timestamp dtmTxn;
    private String ccyTxn;
    private Double amtTxn;
    private Double ratFcyIdr;
    private Double amtTxnLcy;
    private Double ratUsdIdr;
    private Double amtTxnUsd;
    private String txnDesc;
    private Integer txnBranch;
    private String status;
    private String idTxn;
    
    
    /**
     * @return the compositeId
     */
    public MfcNoBookPK getCompositeId() {
        return compositeId;
    }

    @Override
    public String toString() {
        return "MfcNoBook{" + "dateFormat=" + dateFormat + ", compositeId=" + compositeId + ", typAcct=" + typAcct + ", dtPost=" + dtPost + ", strDtPost=" + strDtPost + ", dtValue=" + dtValue + ", dtmTxn=" + dtmTxn + ", ccyTxn=" + ccyTxn + ", amtTxn=" + amtTxn + ", ratFcyIdr=" + ratFcyIdr + ", amtTxnLcy=" + amtTxnLcy + ", ratUsdIdr=" + ratUsdIdr + ", amtTxnUsd=" + amtTxnUsd + ", txnDesc=" + txnDesc + ", txnBranch=" + txnBranch + ", status=" + status + ", idTxn=" + idTxn + '}';
    }
    
    /**
     * @param compositeId the compositeId to set
     */
    public void setCompositeId(MfcNoBookPK compositeId) {
        this.compositeId = compositeId;
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
    * @return the strDtPost
    */
    public String getStrDtPost() {
        return strDtPost;
    }

    /**
     * @param strDtPost the strDtPost to set
     */
    public void setStrDtPost(String strDtPost) throws ParseException {
        this.dtPost = dateFormat.parse(strDtPost);
    }
    
    /**
    * @return the dtValue
    */
    public Date getDtValue() {
        return dtValue;
    }

    /**
     * @param dtValue the dtValue to set
     */
    public void setDtValue(Date dtValue) {
        this.dtValue = dtValue;
    }
    
    /**
    * @return the dtmTxn
    */
    public Timestamp getDtmTxn() {
        return dtmTxn;
    }

    /**
     * @param dtmTxn the dtmTxn to set
     */
    public void setDtmTxn(Timestamp dtmTxn) {
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
    * @return the ratFcyIdr
    */
    public Double getRatFcyIdr() {
        return ratFcyIdr;
    }

    /**
     * @param ratFcyIdr the ratFcyIdr to set
     */
    public void setRatFcyIdr(Double ratFcyIdr) {
        this.ratFcyIdr = ratFcyIdr;
    }
    
    /**
    * @return the amtTxnLcy
    */
    public Double getAmtTxnLcy() {
        return amtTxnLcy;
    }

    /**
     * @param amtTxnLcy the amtTxnLcy to set
     */
    public void setAmtTxnLcy(Double amtTxnLcy) {
        this.amtTxnLcy = amtTxnLcy;
    }
    
    /**
    * @return the ratUsdIdr
    */
    public Double getRatUsdIdr() {
        return ratUsdIdr;
    }

    /**
     * @param ratUsdIdr the ratUsdIdr to set
     */
    public void setRatUsdIdr(Double ratUsdIdr) {
        this.ratUsdIdr = ratUsdIdr;
    }
    
    /**
    * @return the amtTxnUsd
    */
    public Double getAmtTxnUsd() {
        return amtTxnUsd;
    }

    /**
     * @param amtTxnUsd the amtTxnUsd to set
     */
    public void setAmtTxnUsd(Double amtTxnUsd) {
        this.amtTxnUsd = amtTxnUsd;
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
    public Integer getTxnBranch() {
        return txnBranch;
    }

    /**
     * @param txnBranch the txnBranch to set
     */
    public void setTxnBranch(Integer txnBranch) {
        this.txnBranch = txnBranch;
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
}