/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

/**
 *
 * @author v00020800
 */
public class TmpPmFinInstDirMastPK implements java.io.Serializable{

     private String batchNo;
        private Integer recordId;        
    private Integer codFinInstId;
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
	 * @return the codFinInstId
	 */
	public Integer getCodFinInstId() {
		return codFinInstId;
	}

	/**
	 * @param codFinInstId the codFinInstId to set
	 */
	public void setCodFinInstId(Integer codFinInstId) {
		this.codFinInstId = codFinInstId;
	}  
}
