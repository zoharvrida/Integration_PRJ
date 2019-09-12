package bdsm.model;

import java.io.Serializable;

import bdsm.model.BaseModel;

public class HostSknngInOutCreditDetail extends BaseModel implements Serializable{
	
	private HostSknngInOutCreditPK compositeId;
	
	//private Integer parentRecordId;
	private String tipeRecord;
	private String kodeDKE;
	
	private String identitasPesertaPengirimAsal ;
	private String sandiKotaAsal ;
	private String senderName;
	private String sourceAccount ;
	private String alamatNasabahPengirim;
	private String jenisNasabahPengirim;
	private String statusPendudukNasabahPengirim;
	private String identitasNasabahPengirim;
	private String identitasPesertaPenerimaPenerus;
	private String identitasPesertaPenerimaAkhir;
	private String sandiKotaTujuan;
	private String namaNasabahPenerima;
	private String destinationAccount;
	private String alamatNasabahPenerima;
	private String jenisNasabahPenerima;
	private String statusPendudukNasabahPenerima;
	private String identitasNasabahPenerima;
	private String jenisTransaksi;
	private String jenisSaranaTransaksi;
	private String nominal;
	private String nomorUrut;
	private String nomorReferensi;
	private String nomorReferensiTrasaksiAsal;
	private String bebanBiaya;
	private String keterangan;
	private String sor;
	private String message;
	
	private String type;
	private String extractStatus;
	
	
	
	public HostSknngInOutCreditPK getCompositeId() {
		return compositeId;
	}
	public void setCompositeId(HostSknngInOutCreditPK compositeId) {
		this.compositeId = compositeId;
	}
	/*public Integer getParentRecordId() {
		return parentRecordId;
	}
	public void setParentRecordId(Integer parentRecordId) {
		this.parentRecordId = parentRecordId;
	}*/
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
	public String getIdentitasPesertaPengirimAsal() {
		return identitasPesertaPengirimAsal;
	}
	public void setIdentitasPesertaPengirimAsal(String identitasPesertaPengirimAsal) {
		this.identitasPesertaPengirimAsal = identitasPesertaPengirimAsal;
	}
	public String getSandiKotaAsal() {
		return sandiKotaAsal;
	}
	public void setSandiKotaAsal(String sandiKotaAsal) {
		this.sandiKotaAsal = sandiKotaAsal;
	}
	public String getSenderName() {
		return senderName;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	public String getSourceAccount() {
		return sourceAccount;
	}
	public void setSourceAccount(String sourceAccount) {
		this.sourceAccount = sourceAccount;
	}
	public String getAlamatNasabahPengirim() {
		return alamatNasabahPengirim;
	}
	public void setAlamatNasabahPengirim(String alamatNasabahPengirim) {
		this.alamatNasabahPengirim = alamatNasabahPengirim;
	}
	public String getJenisNasabahPengirim() {
		return jenisNasabahPengirim;
	}
	public void setJenisNasabahPengirim(String jenisNasabahPengirim) {
		this.jenisNasabahPengirim = jenisNasabahPengirim;
	}
	public String getStatusPendudukNasabahPengirim() {
		return statusPendudukNasabahPengirim;
	}
	public void setStatusPendudukNasabahPengirim(
			String statusPendudukNasabahPengirim) {
		this.statusPendudukNasabahPengirim = statusPendudukNasabahPengirim;
	}
	public String getIdentitasNasabahPengirim() {
		return identitasNasabahPengirim;
	}
	public void setIdentitasNasabahPengirim(String identitasNasabahPengirim) {
		this.identitasNasabahPengirim = identitasNasabahPengirim;
	}
	public String getIdentitasPesertaPenerimaPenerus() {
		return identitasPesertaPenerimaPenerus;
	}
	public void setIdentitasPesertaPenerimaPenerus(
			String identitasPesertaPenerimaPenerus) {
		this.identitasPesertaPenerimaPenerus = identitasPesertaPenerimaPenerus;
	}
	public String getIdentitasPesertaPenerimaAkhir() {
		return identitasPesertaPenerimaAkhir;
	}
	public void setIdentitasPesertaPenerimaAkhir(
			String identitasPesertaPenerimaAkhir) {
		this.identitasPesertaPenerimaAkhir = identitasPesertaPenerimaAkhir;
	}
	public String getSandiKotaTujuan() {
		return sandiKotaTujuan;
	}
	public void setSandiKotaTujuan(String sandiKotaTujuan) {
		this.sandiKotaTujuan = sandiKotaTujuan;
	}
	public String getNamaNasabahPenerima() {
		return namaNasabahPenerima;
	}
	public void setNamaNasabahPenerima(String namaNasabahPenerima) {
		this.namaNasabahPenerima = namaNasabahPenerima;
	}
	public String getDestinationAccount() {
		return destinationAccount;
	}
	public void setDestinationAccount(String destinationAccount) {
		this.destinationAccount = destinationAccount;
	}
	public String getAlamatNasabahPenerima() {
		return alamatNasabahPenerima;
	}
	public void setAlamatNasabahPenerima(String alamatNasabahPenerima) {
		this.alamatNasabahPenerima = alamatNasabahPenerima;
	}
	public String getJenisNasabahPenerima() {
		return jenisNasabahPenerima;
	}
	public void setJenisNasabahPenerima(String jenisNasabahPenerima) {
		this.jenisNasabahPenerima = jenisNasabahPenerima;
	}
	public String getStatusPendudukNasabahPenerima() {
		return statusPendudukNasabahPenerima;
	}
	public void setStatusPendudukNasabahPenerima(
			String statusPendudukNasabahPenerima) {
		this.statusPendudukNasabahPenerima = statusPendudukNasabahPenerima;
	}
	public String getIdentitasNasabahPenerima() {
		return identitasNasabahPenerima;
	}
	public void setIdentitasNasabahPenerima(String identitasNasabahPenerima) {
		this.identitasNasabahPenerima = identitasNasabahPenerima;
	}
	public String getJenisTransaksi() {
		return jenisTransaksi;
	}
	public void setJenisTransaksi(String jenisTransaksi) {
		this.jenisTransaksi = jenisTransaksi;
	}
	public String getJenisSaranaTransaksi() {
		return jenisSaranaTransaksi;
	}
	public void setJenisSaranaTransaksi(String jenisSaranaTransaksi) {
		this.jenisSaranaTransaksi = jenisSaranaTransaksi;
	}
	 
	public String getNominal() {
		return nominal;
	}
	public void setNominal(String nominal) {
		this.nominal = nominal;
	}
	public String getNomorUrut() {
		return nomorUrut;
	}
	public void setNomorUrut(String nomorUrut) {
		this.nomorUrut = nomorUrut;
	}
	public String getNomorReferensi() {
		return nomorReferensi;
	}
	public void setNomorReferensi(String nomorReferensi) {
		this.nomorReferensi = nomorReferensi;
	}
	public String getNomorReferensiTrasaksiAsal() {
		return nomorReferensiTrasaksiAsal;
	}
	public void setNomorReferensiTrasaksiAsal(String nomorReferensiTrasaksiAsal) {
		this.nomorReferensiTrasaksiAsal = nomorReferensiTrasaksiAsal;
	}
	public String getBebanBiaya() {
		return bebanBiaya;
	}
	public void setBebanBiaya(String bebanBiaya) {
		this.bebanBiaya = bebanBiaya;
	}
	public String getSor() {
		return sor;
	}
	public void setSor(String sor) {
		this.sor = sor;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
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
	 
    /**
     * @return the keterangan
     */
    public String getKeterangan() {
        return keterangan;
    }

    /**
     * @param keterangan the keterangan to set
     */
    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
	 
	
	

}
