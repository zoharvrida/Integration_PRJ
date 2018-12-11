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
public class TmpSKNSMIpkDtls implements Serializable{
    private String batchNo;
    private String bacthNogen2;
    private Integer recordId;

    /**
     * @return the batchNo
     */
    public String getBatchNo() {
        return batchNo;
    }

    /**
     * @param batchNo the batchNo to set
     */
    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    /**
     * @return the recordId
     */
    public Integer getRecordId() {
        return recordId;
    }

    /**
     * @param recordId the recordId to set
     */
    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

	/**
	 * @return the bacthNogen2
	 */
	public String getBacthNogen2() {
		return bacthNogen2;
	}

	/**
	 * @param bacthNogen2 the bacthNogen2 to set
	 */
	public void setBacthNogen2(String bacthNogen2) {
		this.bacthNogen2 = bacthNogen2;
	}
}
