package bdsm.model;


import java.util.Date;


/**
 * @author v00019372
 */
@SuppressWarnings("serial")
public class SknNgWSAuditTrailHeader extends BaseModel {
	private SknNgPK compositeId;
	private String batchId;
	private String jamTanggalMessage;
	private String jumlahRecords;
	private String tanggalBatch;
	private String identitasPesertaPengirim;
	private String CRC;
	private String status;
	private String rejectCode;
	private Date dtmStart;
	private Date dtmFinish;
	private String flagError;
	
	
	public SknNgWSAuditTrailHeader() {}
	
	public SknNgWSAuditTrailHeader(String batchNo, Integer recordId) {
		this.compositeId = new SknNgPK(batchNo, recordId);
	}
	
	
	public SknNgPK getCompositeId() {
		return this.compositeId;
	}
	public void setCompositeId(SknNgPK compositeId) {
		this.compositeId = compositeId;
	}
	
	public String getBatchId() {
		return this.batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	
	public String getJamTanggalMessage() {
		return this.jamTanggalMessage;
	}
	public void setJamTanggalMessage(String jamTanggalMessage) {
		this.jamTanggalMessage = jamTanggalMessage;
	}
	
	public String getJumlahRecords() {
		return this.jumlahRecords;
	}
	public void setJumlahRecords(String jumlahRecords) {
		this.jumlahRecords = jumlahRecords;
	}
	
	public String getTanggalBatch() {
		return this.tanggalBatch;
	}
	public void setTanggalBatch(String tanggalBatch) {
		this.tanggalBatch = tanggalBatch;
	}
	
	public String getIdentitasPesertaPengirim() {
		return this.identitasPesertaPengirim;
	}
	public void setIdentitasPesertaPengirim(String identitasPesertaPengirim) {
		this.identitasPesertaPengirim = identitasPesertaPengirim;
	}
	
	public String getCRC() {
		return this.CRC;
	}
	public void setCRC(String CRC) {
		this.CRC = CRC;
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
	
	public Date getDtmStart() {
		return this.dtmStart;
	}
	public void setDtmStart(Date dtmStart) {
		this.dtmStart = dtmStart;
	}
	
	public Date getDtmFinish() {
		return this.dtmFinish;
	}
	public void setDtmFinish(Date dtmFinish) {
		this.dtmFinish = dtmFinish;
	}
	
	public String getFlagError() {
		return this.flagError;
	}
	public void setFlagError(String flagError) {
		this.flagError = flagError;
	}
	
	
	/* Hibernate to Database properties */
	protected Character getFlagErrorDB() {
		return (this.flagError == null)? null: this.flagError.charAt(0);
	}
	protected void setFlagErrorDB(Character flagError) {
		this.flagError = (flagError == null)? null: flagError.toString();
	}
	
	
    @Override
    public String toString() {
    	StringBuilder sb = new StringBuilder(this.getClass().getSimpleName())
    		.append("{")
    		.append("compositeId=").append(this.compositeId)
    		.append(",batchId=").append(this.batchId)
    		.append(",jamTanggalMessage=").append(this.jamTanggalMessage)
    		.append(",jumlahRecords=").append(this.jumlahRecords)
    		.append(",tanggalBatch=").append(this.tanggalBatch)
    		.append(",identitasPesertaPengirim=").append(this.identitasPesertaPengirim)
    		.append(",CRC=").append(this.CRC)
    		.append(",status=").append(this.status)
    		.append(",rejectCode=").append(this.rejectCode)
    		.append(",dtmStart=").append(this.dtmStart)
    		.append(",dtmFinish=").append(this.dtmFinish)
    		.append(",flagError=").append(this.flagError)
    		.append("}");
    	
    	return sb.toString();
    }
	
}
