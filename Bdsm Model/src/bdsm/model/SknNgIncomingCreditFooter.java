package bdsm.model;

public class SknNgIncomingCreditFooter extends BaseModel{
	
	private SknNgIncomingCreditPK compositeId;
	private Integer TotalRec;
	private String checkSum;
	private String recordType;
	private String recordName;
	private String data;
	private Integer length;
	private String comments;
	private String recordStatus;
	private Integer codEntityVpd;
	
	/*public int getRecordId() {
		return recordId;
	}
	public void setRecordId(int recordId) {
		this.recordId = recordId;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}*/
	public String getCheckSum() {
		return checkSum;
	}
	public void setCheckSum(String checkSum) {
		this.checkSum = checkSum;
	}
	public String getRecordType() {
		return recordType;
	}
	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}
	public String getRecordName() {
		return recordName;
	}
	public void setRecordName(String recordName) {
		this.recordName = recordName;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public Integer getLength() {
		return length;
	}
	public void setLength(Integer length) {
		this.length = length;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getRecordStatus() {
		return recordStatus;
	}
	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}
	public Integer getCodEntityVpd() {
		return codEntityVpd;
	}
	public void setCodEntityVpd(Integer codEntityVpd) {
		this.codEntityVpd = codEntityVpd;
	}

	/**
	 * @return the compositeId
	 */
	public SknNgIncomingCreditPK getCompositeId() {
		return compositeId;
	}

	/**
	 * @param compositeId the compositeId to set
	 */
	public void setCompositeId(SknNgIncomingCreditPK compositeId) {
		this.compositeId = compositeId;
	}

	/**
	 * @return the TotalRec
	 */
	public Integer getTotalRec() {
		return TotalRec;
	}

	/**
	 * @param TotalRec the TotalRec to set
	 */
	public void setTotalRec(Integer TotalRec) {
		this.TotalRec = TotalRec;
	}
	
	
	
	
	
}
