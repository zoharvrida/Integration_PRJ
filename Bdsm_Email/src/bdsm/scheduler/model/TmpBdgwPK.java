package bdsm.scheduler.model;

public class TmpBdgwPK implements java.io.Serializable {
	private Long recordId;
	private String fileId;
	
	
	public TmpBdgwPK() {}
	
	public TmpBdgwPK(Long recordId, String fileId) {
		this.recordId = recordId;
		this.fileId = fileId;
	}
	
	
	public Long getRecordId() {
		return recordId;
	}
	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}
	
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

}
