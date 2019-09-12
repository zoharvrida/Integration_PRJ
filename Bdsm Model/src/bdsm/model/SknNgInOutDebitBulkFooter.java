package bdsm.model;


/**
 * @author v00019372
 */
@SuppressWarnings("serial")
public class SknNgInOutDebitBulkFooter extends SknNgInOutDebitBulkParent implements java.io.Serializable {
	private Integer parentRecordId;
	private String CRC;
	private String comments;
	private String recordStatus;
	
	
	public Integer getParentRecordId() {
		return this.parentRecordId;
	}
	public void setParentRecordId(Integer parentRecordId) {
		this.parentRecordId = parentRecordId;
	}
	
	public String getCRC() {
		return this.CRC;
	}
	public void setCRC(String cRC) {
		CRC = cRC;
	}
	
	public String getComments() {
		return this.comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	public String getRecordStatus() {
		return this.recordStatus;
	}
	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(this.getClass().getSimpleName())
			.append("{").append(super.toString())
			.append(",parentRecordId=").append(this.parentRecordId)
			.append(",CRC=").append(this.CRC)
			.append(",comments=").append(this.comments)
			.append(",recordStatus=").append(this.recordStatus)
			.append("}");

		return sb.toString();
	}
	
}
