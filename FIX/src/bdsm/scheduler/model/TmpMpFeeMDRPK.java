/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

/**
 *
 * @author v00020841
 */
public class TmpMpFeeMDRPK implements java.io.Serializable {

    private Integer recordId;
    private String batchNo;
    private String noAccount;

    /**
     * 
     * @return
     */
    public String getBatchNo() {
		return batchNo;
	}

    /**
     * 
     * @param batchNo
     */
    public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

    /**
     * 
     * @return
     */
    public String getNoAccount() {
		return noAccount;
	}

    /**
     * 
     * @param noAccount
     */
    public void setNoAccount(String noAccount) {
		this.noAccount = noAccount;
	}

    /**
     * 
     * @return
     */
    public Integer getRecordId() {
		return recordId;
	}

    /**
     * 
     * @param recordId
     */
    public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}
    
	
}
