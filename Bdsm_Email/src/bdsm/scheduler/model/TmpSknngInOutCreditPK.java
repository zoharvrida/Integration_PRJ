package bdsm.scheduler.model;

public class TmpSknngInOutCreditPK implements java.io.Serializable {
	
	private String batchNo;
    private Integer recordId;
	private Integer parentRecordId;
	private Integer recordbyParent;

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
