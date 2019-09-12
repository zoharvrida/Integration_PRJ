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
 * @author v00013493
 */
public class FcyTxnManInput {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); 
    private Integer noCif;
    private Integer period;
    private String noAcct;
    private String refTxn;
    private Date dtmTxn;
    private String strDtmTxn;
    private String ccyTxn;
    private Double amtTxn;
    private Double ratFcyIdr;
    private Double amtTxnLcy;
    private Double ratUsdIdr;
    private Double amtTxnUsd;
    private String idTxn;
    private String txnDesc;
    private String noUd;
    private Integer txnBranch;
    private String idMaintainedBy;
    private String idMaintainedSpv;
    
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
     * @param dtmTxn the dtmTxn to set
     */
    public void setDtmTxn(String dtmTxn) throws ParseException {
        this.dtmTxn = dateFormat.parse(dtmTxn);
    }

    /**
    * @return the strDtmTxn
    */
    public String getStrDtmTxn() {
        return strDtmTxn;
    }

    /**
     * @param strDtmTxn the strDtmTxn to set
     */
    public void setStrDtmTxn(String strDtmTxn) throws ParseException {
        this.strDtmTxn = strDtmTxn;
        this.dtmTxn = dateFormat.parse(strDtmTxn);
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
     * @return the idMaintainedBy
     */
    public String getIdMaintainedBy() {
        return idMaintainedBy;
    }

    /**
     * @param idMaintainedBy the idMaintainedBy to set
     */
    public void setIdMaintainedBy(String idMaintainedBy) {
        this.idMaintainedBy = idMaintainedBy;
    }

    /**
     * @return the idMaintainedSpv
     */
    public String getIdMaintainedSpv() {
        return idMaintainedSpv;
    }

    /**
     * @param idMaintainedSpv the idMaintainedSpv to set
     */
    public void setIdMaintainedSpv(String idMaintainedSpv) {
        this.idMaintainedSpv = idMaintainedSpv;
    }
}
