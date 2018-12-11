/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

import java.io.Serializable;

/**
 *
 * @author v00019722
 */
public class TmpchHoldMntPK implements Serializable {
    private String idBatch;
    private Integer recid;
    private Integer ParentID;

	/**
	 * @return the idBatch
	 */
	public String getIdBatch() {
		return idBatch;
	}

	/**
	 * @param idBatch the idBatch to set
	 */
	public void setIdBatch(String idBatch) {
		this.idBatch = idBatch;
	}

	/**
	 * @return the recid
	 */
	public Integer getRecid() {
		return recid;
	}

	/**
	 * @param recid the recid to set
	 */
	public void setRecid(Integer recid) {
		this.recid = recid;
	}

	/**
	 * @return the ParentID
	 */
	public Integer getParentID() {
		return ParentID;
	}

	/**
	 * @param ParentID the ParentID to set
	 */
	public void setParentID(Integer ParentID) {
		this.ParentID = ParentID;
	}
}
