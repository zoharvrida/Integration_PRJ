/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.fcr.model;

import java.util.Date;

/**
 *
 * @author v00022309
 */
public class PmTxnLog {
    private PmTxnLogPK compositeId;
    private Integer codOrgBrn;
    private Date datTxn;
    private Integer codTxnCcy;
    private Integer amtTxnLcy;
    private Integer amtTxnTcy;
    private Integer amtTxnAcy;
    private String codNetId;
    private String refNetworkNo;
    private String txtTxnDesc;
    private String codAccNo;
    private Date datPost;
    
    
    /**
     * @return the codOrgBrn
     */
    public Integer getCodOrgBrn() {
        return codOrgBrn;
    }

    /**
     * @param codOrgBrn the codOrgBrn to set
     */
    public void setCodOrgBrn(Integer codOrgBrn) {
        this.codOrgBrn = codOrgBrn;
    }

    /**
     * @return the compositeId
     */
    public PmTxnLogPK getCompositeId() {
        return compositeId;
    }

    /**
     * @param compositeId the compositeId to set
     */
    public void setCompositeId(PmTxnLogPK compositeId) {
        this.compositeId = compositeId;
    }

    /**
     * @return the datTxn
     */
    public Date getDatTxn() {
        return datTxn;
    }

    /**
     * @param datTxn the datTxn to set
     */
    public void setDatTxn(Date datTxn) {
        this.datTxn = datTxn;
    }

    /**
     * @return the codTxnCcy
     */
    public Integer getCodTxnCcy() {
        return codTxnCcy;
    }

    /**
     * @param codTxnCcy the codTxnCcy to set
     */
    public void setCodTxnCcy(Integer codTxnCcy) {
        this.codTxnCcy = codTxnCcy;
    }

    /**
     * @return the amtTxnLcy
     */
    public Integer getAmtTxnLcy() {
        return amtTxnLcy;
    }

    /**
     * @param amtTxnLcy the amtTxnLcy to set
     */
    public void setAmtTxnLcy(Integer amtTxnLcy) {
        this.amtTxnLcy = amtTxnLcy;
    }

    /**
     * @return the amtTxnTcy
     */
    public Integer getAmtTxnTcy() {
        return amtTxnTcy;
    }

    /**
     * @param amtTxnTcy the amtTxnTcy to set
     */
    public void setAmtTxnTcy(Integer amtTxnTcy) {
        this.amtTxnTcy = amtTxnTcy;
    }

    /**
     * @return the codNetId
     */
    public String getCodNetId() {
        return codNetId;
    }

    /**
     * @param codNetId the codNetId to set
     */
    public void setCodNetId(String codNetId) {
        this.codNetId = codNetId;
    }

    /**
     * @return the refNetNo
     */
    public String getRefNetworkNo() {
        return refNetworkNo;
    }

    /**
     * @param refNetNo the refNetNo to set
     */
    public void setRefNetworkNo(String refNetNo) {
        this.refNetworkNo = refNetNo;
    }

    /**
     * @return the txtTxnDesc
     */
    public String getTxtTxnDesc() {
        return txtTxnDesc;
    }

    /**
     * @param txtTxnDesc the txtTxnDesc to set
     */
    public void setTxtTxnDesc(String txtTxnDesc) {
        this.txtTxnDesc = txtTxnDesc;
    }

    /**
     * @return the codAccNo
     */
    public String getCodAccNo() {
        return codAccNo;
    }

    /**
     * @param codAccNo the codAccNo to set
     */
    public void setCodAccNo(String codAccNo) {
        this.codAccNo = codAccNo;
    }

    /**
     * @return the datPost
     */
    public Date getDatPost() {
        return datPost;
    }

    /**
     * @param datPost the datPost to set
     */
    public void setDatPost(Date datPost) {
        this.datPost = datPost;
    }

    /**
     * @return the amtTxnAcy
     */
    public Integer getAmtTxnAcy() {
        return amtTxnAcy;
    }

    /**
     * @param amtTxnAcy the amtTxnAcy to set
     */
    public void setAmtTxnAcy(Integer amtTxnAcy) {
        this.amtTxnAcy = amtTxnAcy;
    }
    
    
}
