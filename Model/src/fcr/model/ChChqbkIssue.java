/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.fcr.model;

import bdsm.model.BaseModel;
import java.util.Date;

/**
 *
 * @author NCBS
 */
public class ChChqbkIssue extends BaseModel{
	private ChChqbkIssuePK compositeId;
	private String chqkStat;
	private Integer chqkSerial;
	private Date datchqkIssue;
	private String chqkPaid;

	/**
	 * @return the compositeId
	 */
	public ChChqbkIssuePK getCompositeId() {
		return compositeId;
	}

	/**
	 * @param compositeId the compositeId to set
	 */
	public void setCompositeId(ChChqbkIssuePK compositeId) {
		this.compositeId = compositeId;
	}

	/**
	 * @return the chqkStat
	 */
	public String getChqkStat() {
		return chqkStat;
	}

	/**
	 * @param chqkStat the chqkStat to set
	 */
	public void setChqkStat(String chqkStat) {
		this.chqkStat = chqkStat;
	}

	/**
	 * @return the chqkPaid
	 */
	public String getChqkPaid() {
		return chqkPaid;
	}

	/**
	 * @param chqkPaid the chqkPaid to set
	 */
	public void setChqkPaid(String chqkPaid) {
		this.chqkPaid = chqkPaid;
	}

	/**
	 * @return the chqkSerial
	 */
	public Integer getChqkSerial() {
		return chqkSerial;
	}

	/**
	 * @param chqkSerial the chqkSerial to set
	 */
	public void setChqkSerial(Integer chqkSerial) {
		this.chqkSerial = chqkSerial;
	}

    /**
     * @return the datchqkIssue
     */
    public Date getDatchqkIssue() {
        return datchqkIssue;
    }

    /**
     * @param datchqkIssue the datchqkIssue to set
     */
    public void setDatchqkIssue(Date datchqkIssue) {
        this.datchqkIssue = datchqkIssue;
    }
}
