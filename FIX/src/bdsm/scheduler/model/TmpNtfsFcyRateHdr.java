/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

import bdsm.model.BaseModel;

/**
 *
 * @author bdsm
 */
public class TmpNtfsFcyRateHdr extends BaseModel {

	private int id;
    private String BdsmBatch;
    private String Identifier;
    private String BatchId;
    private String RespCode;

    /**
     * @return the BdsmBatch
     */
    public String getBdsmBatch() {
        return BdsmBatch;
    }

    /**
     * @param BdsmBatch the BdsmBatch to set
     */
    public void setBdsmBatch(String BdsmBatch) {
        this.BdsmBatch = BdsmBatch;
    }

    /**
     * @return the Identifier
     */
    public String getIdentifier() {
        return Identifier;
    }

    /**
     * @param Identifier the Identifier to set
     */
    public void setIdentifier(String Identifier) {
        this.Identifier = Identifier;
    }

    /**
     * @return the BatchId
     */
    public String getBatchId() {
        return BatchId;
    }

    /**
     * @param BatchId the BatchId to set
     */
    public void setBatchId(String BatchId) {
        this.BatchId = BatchId;
    }

    /**
     * @return the RespCode
     */
    public String getRespCode() {
        return RespCode;
    }

    /**
     * @param RespCode the RespCode to set
     */
    public void setRespCode(String RespCode) {
        this.RespCode = RespCode;
    }

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
}
