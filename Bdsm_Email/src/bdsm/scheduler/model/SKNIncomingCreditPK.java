/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

/**
 *
 * @author v00019722
 */
public class SKNIncomingCreditPK implements java.io.Serializable {
    private String batchNo;
    private Integer RecordId;

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
     * @return the RecordId
     */
    public Integer getRecordId() {
        return RecordId;
    }

    /**
     * @param RecordId the RecordId to set
     */
    public void setRecordId(Integer RecordId) {
        this.RecordId = RecordId;
    }
}
