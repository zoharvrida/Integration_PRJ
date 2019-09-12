package bdsm.scheduler.model;


/**
*
* @author v00019372
*/
@SuppressWarnings("serial")
public class TmpIncDrNotePK implements java.io.Serializable {
	private String fileId;
	private Long recordId;
	
	
	public TmpIncDrNotePK() {}
	
	public TmpIncDrNotePK(String fileId, Long recordId) {
		this.fileId = fileId;
		this.recordId = recordId;
	}
	

	public String getFileId() {
		return this.fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public Long getRecordId() {
		return this.recordId;
	}
	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}
	
	
    @Override
    public String toString() {
    	StringBuilder sb = new StringBuilder(this.getClass().getSimpleName())
    		.append("{")
    		.append("fileId=").append(this.fileId)
    		.append(",recordId=").append(this.recordId)
    		.append("}");
    	
    	return sb.toString();
    }
	
}
