/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model.Com;

import java.io.Serializable;

/**
 *
 * @author SDM
 */
public class TmpComTrxPK implements Serializable {
    private String userId;
    private Integer idDenom;
    private String codCcy;
    private String txnStatus;
    private String recStatus;
    private String txnType;
    private String txnId;
    private Integer txnSeq;

    public TmpComTrxPK(String userid,String codccy,String txnstatus, String txnid, String txntype,Integer txnSeq){
        this.userId = userid;
        this.codCcy = codccy;
        this.txnStatus = txnstatus;
        this.txnId = txnid;
        this.txnType = txntype;
        this.txnSeq = txnSeq;
    }

    public TmpComTrxPK(String userid,String codccy,String txnstatus, String txnid, String txntype){
        this.userId = userid;
        this.codCcy = codccy;
        this.txnStatus = txnstatus;
        this.txnId = txnid;
        this.txnType = txntype;
    }

    public TmpComTrxPK() {
        //To change body of generated methods, choose Tools | Templates.
    }
    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return the idDenom
     */
    public Integer getIdDenom() {
        return idDenom;
    }

    /**
     * @param idDenom the idDenom to set
     */
    public void setIdDenom(Integer idDenom) {
        this.idDenom = idDenom;
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
     * @return the txnStatus
     */
    public String getTxnStatus() {
        return txnStatus;
    }

    /**
     * @param txnStatus the txnStatus to set
     */
    public void setTxnStatus(String txnStatus) {
        this.txnStatus = txnStatus;
    }

    /**
     * @return the recStatus
     */
    public String getRecStatus() {
        return recStatus;
    }

    /**
     * @param recStatus the recStatus to set
     */
    public void setRecStatus(String recStatus) {
        this.recStatus = recStatus;
    }

    /**
     * @return the txnType
     */
    public String getTxnType() {
        return txnType;
    }

    /**
     * @param txnType the txnType to set
     */
    public void setTxnType(String txnType) {
        this.txnType = txnType;
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
	 * @return the txnSeq
	 */
	public Integer getTxnSeq() {
		return txnSeq;
	}

	/**
	 * @param txnSeq the txnSeq to set
	 */
	public void setTxnSeq(Integer txnSeq) {
		this.txnSeq = txnSeq;
	}
}
