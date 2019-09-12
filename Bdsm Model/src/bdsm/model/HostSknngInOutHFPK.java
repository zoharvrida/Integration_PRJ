package bdsm.model;

public class HostSknngInOutHFPK implements java.io.Serializable{
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
