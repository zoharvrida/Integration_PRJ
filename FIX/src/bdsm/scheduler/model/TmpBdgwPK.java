package bdsm.scheduler.model;

/**
 * 
 * @author bdsm
 */
public class TmpBdgwPK implements java.io.Serializable {
	private Long recordId;
	private String fileId;
	
	
    /**
     * 
     */
    public TmpBdgwPK() {}
	
    /**
     * 
     * @param recordId
     * @param fileId
     */
    public TmpBdgwPK(Long recordId, String fileId) {
		this.recordId = recordId;
		this.fileId = fileId;
	}
	
	
    /**
     * 
     * @return
     */
    public Long getRecordId() {
		return recordId;
	}
    /**
     * 
     * @param recordId
     */
    public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}
	
    /**
     * 
     * @return
     */
    public String getFileId() {
		return fileId;
	}
    /**
     * 
     * @param fileId
     */
    public void setFileId(String fileId) {
		this.fileId = fileId;
	}

}
