package bdsm.scheduler.model;

/**
 * 
 * @author bdsm
 */
public class TmpSknngInOutCreditPK implements java.io.Serializable {
	
	private String batchNo;
    private Integer recordId;
	private Integer parentRecordId;
	private Integer recordbyParent;

    /**
     * 
     * @return
     */
    public String getBatchNo() {
        return this.batchNo;
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
        return this.recordId;
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
    public Integer getParentRecordId() {
		return parentRecordId;
	}
    /**
     * 
     * @param parentRecordId
     */
    public void setParentRecordId(Integer parentRecordId) {
		this.parentRecordId = parentRecordId;
	}

	/**
	 * @return the recordbyParent
	 */
	public Integer getRecordbyParent() {
		return recordbyParent;
	}

	/**
	 * @param recordbyParent the recordbyParent to set
	 */
	public void setRecordbyParent(Integer recordbyParent) {
		this.recordbyParent = recordbyParent;
	}
}
