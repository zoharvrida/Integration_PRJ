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
public class TmpSknngInoutHFPK implements Serializable {
    private String batchNo;
    private Integer parentRecordId;

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
     * @return the parentRecordId
     */
    public Integer getParentRecordId() {
        return parentRecordId;
    }

    /**
     * @param parentRecordId the parentRecordId to set
     */
    public void setParentRecordId(Integer parentRecordId) {
        this.parentRecordId = parentRecordId;
    }
}
