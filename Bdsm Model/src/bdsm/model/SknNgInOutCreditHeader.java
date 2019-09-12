package bdsm.model;


/**
 * @author v00019372
 */
@SuppressWarnings("serial")
public class SknNgInOutCreditHeader extends SknNgInOutCreditParent implements java.io.Serializable {
	private String batchId;
	private String jamTanggalMessage;
	private String jumlahRecords;
	private String totalNominal;
	private String tanggalBatch;
	private String jenisSettlement;
	private String identitasPesertaPengirimPenerus;
	private String sandiKotaAsal;
	private Boolean approved;
	private String status;
	private String comments;
	
	
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
	
	public String getTotalNominal() {
		return this.totalNominal;
	}
	public void setTotalNominal(String totalNominal) {
		this.totalNominal = totalNominal;
	}
	
	public String getTanggalBatch() {
		return this.tanggalBatch;
	}
	public void setTanggalBatch(String tanggalBatch) {
		this.tanggalBatch = tanggalBatch;
	}
	
	public String getJenisSettlement() {
		return this.jenisSettlement;
	}
	public void setJenisSettlement(String jenisSettlement) {
		this.jenisSettlement = jenisSettlement;
	}
	
	public String getIdentitasPesertaPengirimPenerus() {
		return this.identitasPesertaPengirimPenerus;
	}
	public void setIdentitasPesertaPengirimPenerus(
			String identitasPesertaPengirimPenerus) {
		this.identitasPesertaPengirimPenerus = identitasPesertaPengirimPenerus;
	}
	
	public String getSandiKotaAsal() {
		return this.sandiKotaAsal;
	}
	public void setSandiKotaAsal(String sandiKotaAsal) {
		this.sandiKotaAsal = sandiKotaAsal;
	}
	
	public Boolean getApproved() {
		return this.approved;
	}
	public void setApproved(Boolean approved) {
		this.approved = approved;
	}
	
	public String getComments() {
		return this.comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	
	/* Hibernate to Database properties */
	protected Character getFlagRejectDB() {
		return (this.approved == null)? null: Character.valueOf((this.approved)? 'F': 'T');
	}
	protected void setFlagRejectDB(Character flagRejectDB) {
		this.approved = (flagRejectDB == null)? null: Boolean.valueOf(flagRejectDB.equals('F'));
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(this.getClass().getSimpleName())
			.append("{")
			.append(super.toString())
			.append(",batchId=").append(this.batchId)
			.append(",jamTanggalMessage=").append(this.jamTanggalMessage)
			.append(",jumlahRecords=").append(this.jumlahRecords)
			.append(",totalNominal=").append(this.totalNominal)
			.append(",tanggalBatch=").append(this.tanggalBatch)
			.append(",jenisSettlement=").append(this.jenisSettlement)
			.append(",identitasPesertaPengirimPenerus=").append(this.identitasPesertaPengirimPenerus)
			.append(",sandiKotaAsal=").append(this.sandiKotaAsal)
			.append("}");
		
		return sb.toString();
	}
	
}
