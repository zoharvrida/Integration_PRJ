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
public class TmpNtfsFcyRateFtr extends BaseModel{

	private int id;
    private String bdsmBatch;
    private String identifier;
    private Integer counter;

    /**
     * @return the bdsmBatch
     */
    public String getBdsmBatch() {
        return bdsmBatch;
    }

    /**
     * @param bdsmBatch the bdsmBatch to set
     */
    public void setBdsmBatch(String bdsmBatch) {
        this.bdsmBatch = bdsmBatch;
    }

    /**
     * @return the identifier
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * @param identifier the identifier to set
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /**
     * @return the counter
     */
    public Integer getCounter() {
        return counter;
    }

    /**
     * @param counter the counter to set
     */
    public void setCounter(Integer counter) {
        this.counter = counter;
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
