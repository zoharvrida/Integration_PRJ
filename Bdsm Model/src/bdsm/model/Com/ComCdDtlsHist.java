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
public class ComCdDtlsHist extends BaseModel {
	private Integer seqId;
    private String txnId;
	private String dtmLog;
    private String txnDtlsId;
    private String denomId;
    private Integer piece;
    private BigDecimal amount;

    /**
     * @return the txnDtlsId
     */
    public String getTxnDtlsId() {
        return txnDtlsId;
    }

    /**
     * @param txnDtlsId the txnDtlsId to set
     */
    public void setTxnDtlsId(String txnDtlsId) {
        this.txnDtlsId = txnDtlsId;
    }

    /**
     * @return the denomId
     */
    public String getDenomId() {
        return denomId;
    }

    /**
     * @param denomId the denomId to set
     */
    public void setDenomId(String denomId) {
        this.denomId = denomId;
    }

    /**
     * @return the piece
     */
    public Integer getPiece() {
        return piece;
    }

    /**
     * @param piece the piece to set
     */
    public void setPiece(Integer piece) {
        this.piece = piece;
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
	 * @return the dtmLog
	 */
	public String getDtmLog() {
		return dtmLog;
	}

	/**
	 * @param dtmLog the dtmLog to set
	 */
	public void setDtmLog(String dtmLog) {
		this.dtmLog = dtmLog;
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
