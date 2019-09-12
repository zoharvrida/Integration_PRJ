package bdsm.model;


/**
 * @author v00019372
 */
@SuppressWarnings("serial")
public class SknNgWSAuditTrailBulkDetail extends BaseModel {
	private SknNgPK compositeId;
	private Integer parentRecordId;
	private String nomorUrutTransaksi;
	private String status;
	private String rejectCode;
	
	
	public SknNgWSAuditTrailBulkDetail() {}
	
	public SknNgWSAuditTrailBulkDetail(String batchNo, Integer recordId) {
		this.compositeId = new SknNgPK(batchNo, recordId);
	}
	
	
	public SknNgPK getCompositeId() {
		return this.compositeId;
	}
	public void setCompositeId(SknNgPK compositeId) {
		this.compositeId = compositeId;
	}
	
	public Integer getParentRecordId() {
		return this.parentRecordId;
	}
	public void setParentRecordId(Integer parentRecordId) {
		this.parentRecordId = parentRecordId;
	}
	
	public String getNomorUrutTransaksi() {
		return this.nomorUrutTransaksi;
	}
	public void setNomorUrutTransaksi(String nomorUrutTransaksi) {
		this.nomorUrutTransaksi = nomorUrutTransaksi;
	}
	
	public String getStatus() {
		return this.status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getRejectCode() {
		return this.rejectCode;
	}
	public void setRejectCode(String rejectCode) {
		this.rejectCode = rejectCode;
	}
	
	
    @Override
    public String toString() {
    	StringBuilder sb = new StringBuilder(this.getClass().getSimpleName())
    		.append("{")
    		.append("compositeId=").append(this.compositeId)
    		.append(",parentRecordId=").append(this.parentRecordId)
    		.append(",nomorUrutTransaksi=").append(this.nomorUrutTransaksi)
    		.append(",status=").append(this.status)
    		.append(",rejectCode=").append(this.rejectCode)
    		.append("}");
    	
    	return sb.toString();
    }
}
