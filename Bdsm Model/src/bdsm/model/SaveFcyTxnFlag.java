/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;

import java.math.BigDecimal;

/**
 *
 * @author v00013493
 */
public class SaveFcyTxnFlag {
    private Integer noCif;
    private Integer period;
    private String ccyUd;
    private String noAcct;
    private String refTxn;
    private String typMsg;
    private String noUd;
    private String ccyTxn;
    private BigDecimal amtTxn;
    private BigDecimal amtTxnUsd;
    private String flgJoin;
    private String idMaintainedBy;
    private String idMaintainedSpv;
    private String category;
    private String typUD;
    private String typTrx;

    @Override
    public String toString() {
        return "SaveFcyTxnFlag{" + "noCif=" + noCif + ", period=" + period + ", ccyUd=" + ccyUd + ", noAcct=" + noAcct + ", refTxn=" + refTxn + ", typMsg=" + typMsg + ", noUd=" + noUd + ", ccyTxn=" + ccyTxn + ", amtTxn=" + amtTxn + ", amtTxnUsd=" + amtTxnUsd + ", flgJoin=" + flgJoin + ", idMaintainedBy=" + idMaintainedBy + ", idMaintainedSpv=" + idMaintainedSpv + ", category=" + category + ", typUD=" + typUD + ", typTrx=" + typTrx + '}';
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
    public BigDecimal getAmtTxn() {
        return amtTxn;
    }

    /**
     * @param amtTxn the amtTxn to set
     */
    public void setAmtTxn(BigDecimal amtTxn) {
        this.amtTxn = amtTxn;
    }
    
    /**
    * @return the amtTxnUsd
    */
    public BigDecimal getAmtTxnUsd() {
        return amtTxnUsd;
    }

    /**
     * @param amtTxnUsd the amtTxnUsd to set
     */
    public void setAmtTxnUsd(BigDecimal amtTxnUsd) {
        this.amtTxnUsd = amtTxnUsd;
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

    /**
     * @return the flgJoin
     */
    public String getFlgJoin() {
        return flgJoin;
    }

    /**
     * @param flgJoin the flgJoin to set
     */
    public void setFlgJoin(String flgJoin) {
        this.flgJoin = flgJoin;
    }

    /**
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * @return the typUD
     */
    public String getTypUD() {
        return typUD;
    }

    /**
     * @param typUD the typUD to set
     */
    public void setTypUD(String typUD) {
        this.typUD = typUD;
    }

    /**
     * @return the typTrx
     */
    public String getTypTrx() {
        return typTrx;
    }

    /**
     * @param typTrx the typTrx to set
     */
    public void setTypTrx(String typTrx) {
        this.typTrx = typTrx;
    }
}
