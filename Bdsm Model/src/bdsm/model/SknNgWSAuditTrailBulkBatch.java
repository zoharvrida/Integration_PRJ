package bdsm.model;


/**
 * @author v00019372
 */
@SuppressWarnings("serial")
public class SknNgWSAuditTrailBulkBatch extends BaseModel {
	private String batchNo;
	private Byte type;
	private String filenameFixin;
	private String batchNoOriginal;
	private String filenameOriginal;
	
	
	public SknNgWSAuditTrailBulkBatch() {}
	
	public SknNgWSAuditTrailBulkBatch(String batchNo) {
		this.batchNo = batchNo;
	}
	
	
	public String getBatchNo() {
		return this.batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	
	public Byte getType() {
		return this.type;
	}
	public void setType(Byte type) {
		this.type = type;
	}
	
	public String getFilenameFixin() {
		return this.filenameFixin;
	}
	public void setFilenameFixin(String filenameFixin) {
		this.filenameFixin = filenameFixin;
	}
	
	public String getBatchNoOriginal() {
		return this.batchNoOriginal;
	}
	public void setBatchNoOriginal(String batchNoOriginal) {
		this.batchNoOriginal = batchNoOriginal;
	}
	
	public String getFilenameOriginal() {
		return this.filenameOriginal;
	}
	public void setFilenameOriginal(String filenameOriginal) {
		this.filenameOriginal = filenameOriginal;
	}
	
	
    @Override
    public String toString() {
    	StringBuilder sb = new StringBuilder(this.getClass().getSimpleName())
    		.append("{")
    		.append("batchNo=").append(this.batchNo)
    		.append(",type=").append(this.type)
    		.append(",filenameFixin=").append(this.filenameFixin)
    		.append(",batchNoOriginal=").append(this.batchNoOriginal)
    		.append(",filenameOriginal=").append(this.filenameOriginal)
    		.append(",dtmCreated=").append(this.getDtmCreated())
    		.append("}");
    	
    	return sb.toString();
    }
}
