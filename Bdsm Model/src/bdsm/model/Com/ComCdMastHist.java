/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model.Com;

import bdsm.model.BaseModel;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 *
 * @author SDM
 */
public class ComCdMastHist extends BaseModel {
    private ComCdMastHistPK pk;
    private Integer idBranch;
    private Timestamp dtmRequest;
    private String codCcy;
    private String idVendor;
    private String idCust;
    private String status;
    private BigDecimal totalAmtReq;
    private BigDecimal totalAmtConf;
	private java.util.Set<ComCdDtls> detailsHist;


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(this.getClass().getSimpleName())
                .append("{")
                .append(",idBranch=").append(this.idBranch)
                .append(",dtmRequest=").append(this.dtmRequest)
                .append(",codCcy=").append(this.codCcy)
                .append(",idVendor=").append(this.idVendor)
                .append(",idCust=").append(this.idCust)
                .append(",status=").append(this.status)
                .append(",totalAmtReq=").append(this.totalAmtReq)
                .append(",totalAmtConf=").append(this.totalAmtConf)
                .append("}");

        return sb.toString();
    }

    /**
     * @return the pk
     */
    public ComCdMastHistPK getPk() {
        return pk;
    }

    /**
     * @param pk the pk to set
     */
    public void setPk(ComCdMastHistPK pk) {
        this.pk = pk;
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
	 * @return the detailsHist
	 */
	public java.util.Set<ComCdDtls> getDetailsHist() {
		return detailsHist;
	}

	/**
	 * @param detailsHist the detailsHist to set
	 */
	public void setDetailsHist(java.util.Set<ComCdDtls> detailsHist) {
		this.detailsHist = detailsHist;
	}
}
