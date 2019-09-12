package bdsm.model;


/**
 * @author v00019372
 */
@SuppressWarnings("serial")
public class SknNgWSAuditTrailDetail extends BaseModel {
	private SknNgPK compositeId;
	private Integer parentRecordId;
	private String nomorReferensi;
	private String SOR;
	private String status;
	private String rejectCode;
	
	
	public SknNgWSAuditTrailDetail() {}
	
	public SknNgWSAuditTrailDetail(String batchNo, Integer recordId) {
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
	
	public String getNomorReferensi() {
		return this.nomorReferensi;
	}
	public void setNomorReferensi(String nomorReferensi) {
		this.nomorReferensi = nomorReferensi;
	}
	
	public String getSOR() {
		return this.SOR;
	}
	public void setSOR(String sOR) {
		SOR = sOR;
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
    		.append(",nomorReferensi=").append(this.nomorReferensi)
    		.append(",SOR=").append(this.SOR)
    		.append(",status=").append(this.status)
    		.append(",rejectCode=").append(this.rejectCode)
    		.append("}");
    	
    	return sb.toString();
    }
}
