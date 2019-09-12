package bdsm.scheduler.model;

/**
 * 
 * @author bdsm
 */
public class TmpMpPK implements java.io.Serializable {
    private Integer recordId;
    private String batchNo;

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
}
