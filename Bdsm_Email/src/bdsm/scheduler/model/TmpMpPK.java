package bdsm.scheduler.model;

public class TmpMpPK implements java.io.Serializable {
    private Integer recordId;
    private String batchNo;

    public Integer getRecordId() {
        return recordId;
    }
    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public String getBatchNo() {
        return batchNo;
    }
    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }
}
