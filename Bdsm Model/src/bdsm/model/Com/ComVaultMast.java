/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model.Com;

import bdsm.model.BaseModel;
import java.math.BigDecimal;

/**
 *
 * @author SDM
 */
public class ComVaultMast extends ComCDMastParent {
    private String txnId;
    private Integer idBranch;
    private String codCcy;
    private String idVendor;
    private BigDecimal amount;
    private String status;

    /**
     * @return the txnId
     */
    public String getTxnId() {
        return txnId;
    }

    /**
     * @param txnId the txnId to set
     */
    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    /**
     * @return the idBranch
     */
    public Integer getIdBranch() {
        return idBranch;
    }

    /**
     * @param idBranch the idBranch to set
     */
    public void setIdBranch(Integer idBranch) {
        this.idBranch = idBranch;
    }

    /**
     * @return the codCcy
     */
    public String getCodCcy() {
        return codCcy;
    }

    /**
     * @param codCcy the codCcy to set
     */
    public void setCodCcy(String codCcy) {
        this.codCcy = codCcy;
    }

    /**
     * @return the idVendor
     */
    public String getIdVendor() {
        return idVendor;
    }

    /**
     * @param idVendor the idVendor to set
     */
    public void setIdVendor(String idVendor) {
        this.idVendor = idVendor;
    }

    /**
     * @return the amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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
}
