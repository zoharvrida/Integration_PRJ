package bdsm.model;

public class HostSknngInOutCreditPK implements java.io.Serializable {

    private String batchNo;
    private Integer recordId;
    private Integer parentRecordId;

    public String getBatchNo() {
        return this.batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public Integer getRecordId() {
        return this.recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public Integer getParentRecordId() {
        return parentRecordId;
    }

    public void setParentRecordId(Integer parentRecordId) {
        this.parentRecordId = parentRecordId;
    }
}
