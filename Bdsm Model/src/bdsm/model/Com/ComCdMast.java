/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model.Com;

import bdsm.model.BaseModel;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 *
 * @author SDM
 */
public class ComCdMast extends BaseModel implements Serializable {
    private String txnId;
	private Integer seqId;
    private Integer idBranch;
    private Timestamp dtmRequest;
    private String codCcy;
    private String idVendor;
    private String idCust;
    private String status;
    private BigDecimal totalAmtReq;
    private BigDecimal totalAmtConf;
    private java.util.Set<ComCdDtls> details;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(this.getClass().getSimpleName())
                .append("{")
                .append(",idBranch=").append(this.txnId)
                .append(",idBranch=").append(this.idBranch)
                .append(",dtmRequest=").append(this.dtmRequest)
                .append(",codCcy=").append(this.codCcy)
                .append(",idVendor=").append(this.idVendor)
                .append(",idCust=").append(this.idCust)
                .append(",status=").append(this.status)
                .append(",totalAmtReq=").append(this.totalAmtReq)
                .append(",totalAmtConf=").append(this.totalAmtConf)
                .append(",SET=").append(this.details)
                .append("}");

        return sb.toString();
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
     * @return the dtmRequest
     */
    public Timestamp getDtmRequest() {
        return dtmRequest;
    }

    /**
     * @param dtmRequest the dtmRequest to set
     */
    public void setDtmRequest(Timestamp dtmRequest) {
        this.dtmRequest = dtmRequest;
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
     * @return the idCust
     */
    public String getIdCust() {
        return idCust;
    }

    /**
     * @param idCust the idCust to set
     */
    public void setIdCust(String idCust) {
        this.idCust = idCust;
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
     * @return the totalAmtReq
     */
    public BigDecimal getTotalAmtReq() {
        return totalAmtReq;
    }

    /**
     * @param totalAmtReq the totalAmtReq to set
     */
    public void setTotalAmtReq(BigDecimal totalAmtReq) {
        this.totalAmtReq = totalAmtReq;
    }

    /**
     * @return the totalAmtConf
     */
    public BigDecimal getTotalAmtConf() {
        return totalAmtConf;
    }

    /**
     * @param totalAmtConf the totalAmtConf to set
     */
    public void setTotalAmtConf(BigDecimal totalAmtConf) {
        this.totalAmtConf = totalAmtConf;
    }

    /**
     * @return the details
     */
    public java.util.Set<ComCdDtls> getDetails() {
        return details;
    }

    /**
     * @param details the details to set
     */
    public void setDetails(java.util.Set<ComCdDtls> details) {
        this.details = details;
    }

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
	 * @return the seqId
	 */
	public Integer getSeqId() {
		return seqId;
	}

	/**
	 * @param seqId the seqId to set
	 */
	public void setSeqId(Integer seqId) {
		this.seqId = seqId;
	}
}
