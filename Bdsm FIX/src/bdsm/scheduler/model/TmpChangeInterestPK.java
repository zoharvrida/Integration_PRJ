/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

/**
 *
 * @author v00020841
 */
public class TmpChangeInterestPK implements java.io.Serializable {

    private String batchNo;
    private Integer recordId;
    
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
