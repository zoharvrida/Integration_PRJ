package bdsm.model;


/**
 * @author v00019372
 */
@SuppressWarnings("serial")
public class SknNgInOutReturBulkHeader extends SknNgInOutReturBulkParent implements java.io.Serializable {
	private String type;
	private String batchId;
	private String jamTanggalMessage;
	private String jumlahRecordsDKE;
	private String totalNominalDKE;
	private String tanggalBatch;
	private String jenisSetelmen;
	private String identitasPesertaPengirim;
	private String sandiKotaPengirim;
	private String batchIdOriginal;
	private String identitasPesertaPengirimOriginal;
	private String FCRBatchNo;
	private String status;
	private String rejectCode;
	private String comments;
	private String recordStatus;
	
	
	public String getType() {
		return this.type;
	}
	public void setType(String type) {
		this.type = type;
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
	
	public String getJumlahRecordsDKE() {
		return this.jumlahRecordsDKE;
	}
	public void setJumlahRecordsDKE(String jumlahRecordsDKE) {
		this.jumlahRecordsDKE = jumlahRecordsDKE;
	}
	
	public String getTotalNominalDKE() {
		return this.totalNominalDKE;
	}
	public void setTotalNominalDKE(String totalNominalDKE) {
		this.totalNominalDKE = totalNominalDKE;
	}
	
	public String getTanggalBatch() {
		return this.tanggalBatch;
	}
	public void setTanggalBatch(String tanggalBatch) {
		this.tanggalBatch = tanggalBatch;
	}
	
	public String getJenisSetelmen() {
		return this.jenisSetelmen;
	}
	public void setJenisSetelmen(String jenisSetelmen) {
		this.jenisSetelmen = jenisSetelmen;
	}
	
	public String getIdentitasPesertaPengirim() {
		return this.identitasPesertaPengirim;
	}
	public void setIdentitasPesertaPengirim(String identitasPesertaPengirim) {
		this.identitasPesertaPengirim = identitasPesertaPengirim;
	}
	
	public String getSandiKotaPengirim() {
		return this.sandiKotaPengirim;
	}
	public void setSandiKotaPengirim(String sandiKotaPengirim) {
		this.sandiKotaPengirim = sandiKotaPengirim;
	}
	
	public String getBatchIdOriginal() {
		return this.batchIdOriginal;
	}
	public void setBatchIdOriginal(String batchIdOriginal) {
		this.batchIdOriginal = batchIdOriginal;
	}
	
	public String getIdentitasPesertaPengirimOriginal() {
		return this.identitasPesertaPengirimOriginal;
	}
	public void setIdentitasPesertaPengirimOriginal(String identitasPesertaPengirimOriginal) {
		this.identitasPesertaPengirimOriginal = identitasPesertaPengirimOriginal;
	}
	
	public String getFCRBatchNo() {
		return this.FCRBatchNo;
	}
	public void setFCRBatchNo(String FCRBatchNo) {
		this.FCRBatchNo = FCRBatchNo;
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

	
	
	/* Hibernate to Database properties */
	
	protected Character getTypeDB() {
		return (this.type == null)? null: this.type.charAt(0);
	}
	protected void setTypeDB(Character type) {
		this.type = (type == null)? null: type.toString();
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(this.getClass().getSimpleName())
			.append("{").append(super.toString())
			.append(",type=").append(this.type)
			.append(",batchId=").append(this.batchId)
			.append(",jamTanggalMessage=").append(this.jamTanggalMessage)
			.append(",jumlahRecordsDKE=").append(this.jumlahRecordsDKE)
			.append(",totalNominalDKE=").append(this.totalNominalDKE)
			.append(",tanggalBatch=").append(this.tanggalBatch)
			.append(",jenisSetelmen=").append(this.jenisSetelmen)
			.append(",identitasPesertaPengirim=").append(this.identitasPesertaPengirim)
			.append(",sandiKotaPengirim=").append(this.sandiKotaPengirim)
			.append(",batchIdOriginal=").append(this.batchIdOriginal)
			.append(",identitasPesertaPengirimOriginal=").append(this.identitasPesertaPengirimOriginal)
			.append(",FCRBatchNo=").append(this.FCRBatchNo)
			.append(",status=").append(this.status)
			.append(",rejectCode=").append(this.rejectCode)
    		.append(",comments=").append(this.comments)
    		.append(",recordStatus=").append(this.recordStatus)
			.append("}");

		return sb.toString();
	}

}
