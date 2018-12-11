package bdsm.scheduler.model;

import bdsm.model.BaseModel;
	
/**
 * 
 * @author bdsm
 */
public class TmpSknngInOutCreditHeader extends BaseModel implements java.io.Serializable{
	
	private TmpSknngInoutHFPK compositeId;
	private String tipeRecord;
	private String kodeDKE;
	private String batchId;
	private String jamTanggalMessage;
	private String jumlahRecords;
	private String totalNominal;
	private String tanggalBatch;
	private String jenisSetelmen;
	private String identitasPesertaPengirim;
	private String sandiKotaPengirim;
	private String type;
	private String extractStatus;
	private String message;
    private String flgReject;
	
	
    /**
     * 
     * @return
     */
    public TmpSknngInoutHFPK getCompositeId() {
		return compositeId;
	}
    /**
     * 
     * @param compositeId
     */
    public void setCompositeId(TmpSknngInoutHFPK compositeId) {
		this.compositeId = compositeId;
	}

    /**
     * 
     * @return
     */
    public String getTipeRecord() {
		return tipeRecord;
	}
    /**
     * 
     * @param tipeRecord
     */
    public void setTipeRecord(String tipeRecord) {
		this.tipeRecord = tipeRecord;
	}
    /**
     * 
     * @return
     */
    public String getKodeDKE() {
		return kodeDKE;
	}
    /**
     * 
     * @param kodeDKE
     */
    public void setKodeDKE(String kodeDKE) {
		this.kodeDKE = kodeDKE;
	}
    /**
     * 
     * @return
     */
    public String getBatchId() {
		return batchId;
	}
    /**
     * 
     * @param batchId
     */
    public void setBatchId(String batchId) {
		this.batchId = batchId;
	}
    /**
     * 
     * @return
     */
    public String getJamTanggalMessage() {
		return jamTanggalMessage;
	}
    /**
     * 
     * @param jamTanggalMessage
     */
    public void setJamTanggalMessage(String jamTanggalMessage) {
		this.jamTanggalMessage = jamTanggalMessage;
	}
	 
    /**
     * 
     * @return
     */
    public String getJumlahRecords() {
		return jumlahRecords;
	}
    /**
     * 
     * @param jumlahRecords
     */
    public void setJumlahRecords(String jumlahRecords) {
		this.jumlahRecords = jumlahRecords;
	}
	 
    /**
     * 
     * @return
     */
    public String getTotalNominal() {
		return totalNominal;
	}
    /**
     * 
     * @param totalNominal
     */
    public void setTotalNominal(String totalNominal) {
		this.totalNominal = totalNominal;
	}
    /**
     * 
     * @return
     */
    public String getTanggalBatch() {
		return tanggalBatch;
	}
    /**
     * 
     * @param tanggalBatch
     */
    public void setTanggalBatch(String tanggalBatch) {
            this.tanggalBatch = tanggalBatch;
	}
    /**
     * 
     * @return
     */
    public String getJenisSetelmen() {
		return jenisSetelmen;
	}
    /**
     * 
     * @param jenisSetelmen
     */
    public void setJenisSetelmen(String jenisSetelmen) {
		this.jenisSetelmen = jenisSetelmen;
	}
    /**
     * 
     * @return
     */
    public String getIdentitasPesertaPengirim() {
		return identitasPesertaPengirim;
	}
    /**
     * 
     * @param identitasPesertaPengirim
     */
    public void setIdentitasPesertaPengirim(String identitasPesertaPengirim) {
		this.identitasPesertaPengirim = identitasPesertaPengirim;
	}
    /**
     * 
     * @return
     */
    public String getSandiKotaPengirim() {
		return sandiKotaPengirim;
	}
    /**
     * 
     * @param sandiKotaPengirim
     */
    public void setSandiKotaPengirim(String sandiKotaPengirim) {
		this.sandiKotaPengirim = sandiKotaPengirim;
	}
    /**
     * 
     * @return
     */
    public String getType() {
		return type;
	}
    /**
     * 
     * @param type
     */
    public void setType(String type) {
		this.type = type;
	}
    /**
     * 
     * @return
     */
    public String getExtractStatus() {
		return extractStatus;
	}
    /**
     * 
     * @param extractStatus
     */
    public void setExtractStatus(String extractStatus) {
		this.extractStatus = extractStatus;
	}
    /**
     * 
     * @return
     */
    public String getMessage() {
		return message;
	}
    /**
     * 
     * @param message
     */
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
