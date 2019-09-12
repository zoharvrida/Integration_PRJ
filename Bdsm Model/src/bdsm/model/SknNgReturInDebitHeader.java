package bdsm.model;


/**
 * 
 * @author v00019372
 */
@SuppressWarnings("serial")
public class SknNgReturInDebitHeader extends SknNgReturInDebitParent implements java.io.Serializable {
	private String batchId;
	private String jamTanggalMessage;
	private String jumlahRecords;
	private String totalNominal;
	private String tanggalBatch;
	private String jenisSetelmen;
	private String identitasPesertaPengirim;
	private String sandiKotaPengirim;
	private String batchIdOriginal;
	private String identitasPesertaPengirimOriginal;
	
	
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
	public void setIdentitasPesertaPengirimOriginal(
			String identitasPesertaPengirimOriginal) {
		this.identitasPesertaPengirimOriginal = identitasPesertaPengirimOriginal;
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
			.append(",jenisSetelmen=").append(this.jenisSetelmen)
			.append(",identitasPesertaPengirim=").append(this.identitasPesertaPengirim)
			.append(",sandiKotaPengirim=").append(this.sandiKotaPengirim)
			.append(",batchIdOriginal=").append(this.batchIdOriginal)
			.append(",identitasPesertaPengirimOriginal=").append(this.identitasPesertaPengirimOriginal)
			.append("}");
		
		return sb.toString();
	}

}
