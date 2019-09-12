/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model.Com;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author SDM
 */
public class ComCdMastHistPK implements Serializable {
	private Integer seqId;
    private String txnId;
    private Timestamp dtmLog;

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
    public Timestamp getDtmLog() {
        return dtmLog;
    }

    /**
     * @param dtmLog the dtmLog to set
     */
    public void setDtmLog(Timestamp dtmLog) {
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
