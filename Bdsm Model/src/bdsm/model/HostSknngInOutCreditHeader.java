/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;

/**
 *
 * @author NCBS
 */
public class HostSknngInOutCreditHeader extends BaseModel {
	private HostSknngInOutHFPK compositeId;
	private String tipeRecord;
	private String kodeDKE;
	private String batchId;
	private String jamTanggalMessage;
	private Integer jumlahRecords;
	private String totalNominal;
	private String tanggalBatch;
	private String jenisSetelmen;
	private String identitasPesertaPengirim;
	private String sandiKotaPengirim;
	private String type;
	private String extractStatus;
	private String message;
    private String flgReject;
	
	
	public HostSknngInOutHFPK getCompositeId() {
		return compositeId;
	}
	public void setCompositeId(HostSknngInOutHFPK compositeId) {
		this.compositeId = compositeId;
	}

	public String getTipeRecord() {
		return tipeRecord;
	}
	public void setTipeRecord(String tipeRecord) {
		this.tipeRecord = tipeRecord;
	}
	public String getKodeDKE() {
		return kodeDKE;
	}
	public void setKodeDKE(String kodeDKE) {
		this.kodeDKE = kodeDKE;
	}
	public String getBatchId() {
		return batchId;
	}
	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
	public String getJamTanggalMessage() {
		return jamTanggalMessage;
	}
	public void setJamTanggalMessage(String jamTanggalMessage) {
		this.jamTanggalMessage = jamTanggalMessage;
	}
	 
	public int getJumlahRecords() {
		return jumlahRecords;
	}
	public void setJumlahRecords(int jumlahRecords) {
		this.jumlahRecords = jumlahRecords;
	}
	 
	public String getTotalNominal() {
		return totalNominal;
	}
	public void setTotalNominal(String totalNominal) {
		this.totalNominal = totalNominal;
	}
	public String getTanggalBatch() {
		return tanggalBatch;
	}
	public void setTanggalBatch(String tanggalBatch) {
            this.tanggalBatch = tanggalBatch;
	}
	public String getJenisSetelmen() {
		return jenisSetelmen;
	}
	public void setJenisSetelmen(String jenisSetelmen) {
		this.jenisSetelmen = jenisSetelmen;
	}
	public String getIdentitasPesertaPengirim() {
		return identitasPesertaPengirim;
	}
	public void setIdentitasPesertaPengirim(String identitasPesertaPengirim) {
		this.identitasPesertaPengirim = identitasPesertaPengirim;
	}
	public String getSandiKotaPengirim() {
		return sandiKotaPengirim;
	}
	public void setSandiKotaPengirim(String sandiKotaPengirim) {
		this.sandiKotaPengirim = sandiKotaPengirim;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getExtractStatus() {
		return extractStatus;
	}
	public void setExtractStatus(String extractStatus) {
		this.extractStatus = extractStatus;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

    /**
     * @return the flgReject
     */
    public String getFlgReject() {
        return flgReject;
    }

    /**
     * @param flgReject the flgReject to set
     */
    public void setFlgReject(String flgReject) {
        this.flgReject = flgReject;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

