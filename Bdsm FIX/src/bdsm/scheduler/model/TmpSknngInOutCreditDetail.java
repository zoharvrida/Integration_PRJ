package bdsm.scheduler.model;


/**
 * 
 * @author bdsm
 */
@SuppressWarnings("serial")
public class TmpSknngInOutCreditDetail extends bdsm.model.BaseModel implements java.io.Serializable {
	private TmpSknngInOutCreditPK compositeId;
	
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
	private String sor;
	private String SORToFCR;
	private String message;
	
	private String type;
	private String extractStatus;
	private String flgAuth;
	private String Keterangan;
	
	
	
    /**
     * 
     * @return
     */
    public TmpSknngInOutCreditPK getCompositeId() {
		return compositeId;
	}
    /**
     * 
     * @param compositeId
     */
    public void setCompositeId(TmpSknngInOutCreditPK compositeId) {
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
    public String getIdentitasPesertaPengirimAsal() {
		return identitasPesertaPengirimAsal;
	}
    /**
     * 
     * @param identitasPesertaPengirimAsal
     */
    public void setIdentitasPesertaPengirimAsal(String identitasPesertaPengirimAsal) {
		this.identitasPesertaPengirimAsal = identitasPesertaPengirimAsal;
	}
	
    /**
     * 
     * @return
     */
    public String getSandiKotaAsal() {
		return sandiKotaAsal;
	}
    /**
     * 
     * @param sandiKotaAsal
     */
    public void setSandiKotaAsal(String sandiKotaAsal) {
		this.sandiKotaAsal = sandiKotaAsal;
	}
	
    /**
     * 
     * @return
     */
    public String getSenderName() {
		return senderName;
	}
    /**
     * 
     * @param senderName
     */
    public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	
    /**
     * 
     * @return
     */
    public String getSourceAccount() {
		return sourceAccount;
	}
    /**
     * 
     * @param sourceAccount
     */
    public void setSourceAccount(String sourceAccount) {
		this.sourceAccount = sourceAccount;
	}
	
    /**
     * 
     * @return
     */
    public String getAlamatNasabahPengirim() {
		return alamatNasabahPengirim;
	}
    /**
     * 
     * @param alamatNasabahPengirim
     */
    public void setAlamatNasabahPengirim(String alamatNasabahPengirim) {
		this.alamatNasabahPengirim = alamatNasabahPengirim;
	}
	
    /**
     * 
     * @return
     */
    public String getJenisNasabahPengirim() {
		return jenisNasabahPengirim;
	}
    /**
     * 
     * @param jenisNasabahPengirim
     */
    public void setJenisNasabahPengirim(String jenisNasabahPengirim) {
		this.jenisNasabahPengirim = jenisNasabahPengirim;
	}
	
    /**
     * 
     * @return
     */
    public String getStatusPendudukNasabahPengirim() {
		return statusPendudukNasabahPengirim;
	}
    /**
     * 
     * @param statusPendudukNasabahPengirim
     */
    public void setStatusPendudukNasabahPengirim(String statusPendudukNasabahPengirim) {
		this.statusPendudukNasabahPengirim = statusPendudukNasabahPengirim;
	}
	
    /**
     * 
     * @return
     */
    public String getIdentitasNasabahPengirim() {
		return identitasNasabahPengirim;
	}
    /**
     * 
     * @param identitasNasabahPengirim
     */
    public void setIdentitasNasabahPengirim(String identitasNasabahPengirim) {
		this.identitasNasabahPengirim = identitasNasabahPengirim;
	}
	
    /**
     * 
     * @return
     */
    public String getIdentitasPesertaPenerimaPenerus() {
		return identitasPesertaPenerimaPenerus;
	}
    /**
     * 
     * @param identitasPesertaPenerimaPenerus
     */
    public void setIdentitasPesertaPenerimaPenerus(String identitasPesertaPenerimaPenerus) {
		this.identitasPesertaPenerimaPenerus = identitasPesertaPenerimaPenerus;
	}
	
    /**
     * 
     * @return
     */
    public String getIdentitasPesertaPenerimaAkhir() {
		return identitasPesertaPenerimaAkhir;
	}
    /**
     * 
     * @param identitasPesertaPenerimaAkhir
     */
    public void setIdentitasPesertaPenerimaAkhir(String identitasPesertaPenerimaAkhir) {
		this.identitasPesertaPenerimaAkhir = identitasPesertaPenerimaAkhir;
	}
	
    /**
     * 
     * @return
     */
    public String getSandiKotaTujuan() {
		return sandiKotaTujuan;
	}
    /**
     * 
     * @param sandiKotaTujuan
     */
    public void setSandiKotaTujuan(String sandiKotaTujuan) {
		this.sandiKotaTujuan = sandiKotaTujuan;
	}
	
    /**
     * 
     * @return
     */
    public String getNamaNasabahPenerima() {
		return namaNasabahPenerima;
	}
    /**
     * 
     * @param namaNasabahPenerima
     */
    public void setNamaNasabahPenerima(String namaNasabahPenerima) {
		this.namaNasabahPenerima = namaNasabahPenerima;
	}
	
    /**
     * 
     * @return
     */
    public String getDestinationAccount() {
		return destinationAccount;
	}
    /**
     * 
     * @param destinationAccount
     */
    public void setDestinationAccount(String destinationAccount) {
		this.destinationAccount = destinationAccount;
	}
	
    /**
     * 
     * @return
     */
    public String getAlamatNasabahPenerima() {
		return alamatNasabahPenerima;
	}
    /**
     * 
     * @param alamatNasabahPenerima
     */
    public void setAlamatNasabahPenerima(String alamatNasabahPenerima) {
		this.alamatNasabahPenerima = alamatNasabahPenerima;
	}
	
    /**
     * 
     * @return
     */
    public String getJenisNasabahPenerima() {
		return jenisNasabahPenerima;
	}
    /**
     * 
     * @param jenisNasabahPenerima
     */
    public void setJenisNasabahPenerima(String jenisNasabahPenerima) {
		this.jenisNasabahPenerima = jenisNasabahPenerima;
	}
	
    /**
     * 
     * @return
     */
    public String getStatusPendudukNasabahPenerima() {
		return statusPendudukNasabahPenerima;
	}
    /**
     * 
     * @param statusPendudukNasabahPenerima
     */
    public void setStatusPendudukNasabahPenerima(String statusPendudukNasabahPenerima) {
		this.statusPendudukNasabahPenerima = statusPendudukNasabahPenerima;
	}
	
    /**
     * 
     * @return
     */
    public String getIdentitasNasabahPenerima() {
		return identitasNasabahPenerima;
	}
    /**
     * 
     * @param identitasNasabahPenerima
     */
    public void setIdentitasNasabahPenerima(String identitasNasabahPenerima) {
		this.identitasNasabahPenerima = identitasNasabahPenerima;
	}
	
    /**
     * 
     * @return
     */
    public String getJenisTransaksi() {
		return jenisTransaksi;
	}
    /**
     * 
     * @param jenisTransaksi
     */
    public void setJenisTransaksi(String jenisTransaksi) {
		this.jenisTransaksi = jenisTransaksi;
	}
	
    /**
     * 
     * @return
     */
    public String getJenisSaranaTransaksi() {
		return jenisSaranaTransaksi;
	}
    /**
     * 
     * @param jenisSaranaTransaksi
     */
    public void setJenisSaranaTransaksi(String jenisSaranaTransaksi) {
		this.jenisSaranaTransaksi = jenisSaranaTransaksi;
	}
	 
    /**
     * 
     * @return
     */
    public String getNominal() {
		return nominal;
	}
    /**
     * 
     * @param nominal
     */
    public void setNominal(String nominal) {
		this.nominal = nominal;
	}
	
    /**
     * 
     * @return
     */
    public String getNomorUrut() {
		return nomorUrut;
	}
    /**
     * 
     * @param nomorUrut
     */
    public void setNomorUrut(String nomorUrut) {
		this.nomorUrut = nomorUrut;
	}
	
    /**
     * 
     * @return
     */
    public String getNomorReferensi() {
		return nomorReferensi;
	}
    /**
     * 
     * @param nomorReferensi
     */
    public void setNomorReferensi(String nomorReferensi) {
		this.nomorReferensi = nomorReferensi;
	}
	
    /**
     * 
     * @return
     */
    public String getNomorReferensiTrasaksiAsal() {
		return nomorReferensiTrasaksiAsal;
	}
    /**
     * 
     * @param nomorReferensiTrasaksiAsal
     */
    public void setNomorReferensiTrasaksiAsal(String nomorReferensiTrasaksiAsal) {
		this.nomorReferensiTrasaksiAsal = nomorReferensiTrasaksiAsal;
	}
	
    /**
     * 
     * @return
     */
    public String getBebanBiaya() {
		return bebanBiaya;
	}
    /**
     * 
     * @param bebanBiaya
     */
    public void setBebanBiaya(String bebanBiaya) {
		this.bebanBiaya = bebanBiaya;
	}
	
    /**
     * 
     * @return
     */
    public String getSor() {
		return sor;
	}
    /**
     * 
     * @param sor
     */
    public void setSor(String sor) {
		this.sor = sor;
	}
	
    /**
     * 
     * @return
     */
    public String getSORToFCR() {
		return SORToFCR;
	}
    /**
     * 
     * @param SORToFCR
     */
    public void setSORToFCR(String SORToFCR) {
		this.SORToFCR = SORToFCR;
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
    public String getFlgAuth() {
		return flgAuth;
	}
    /**
     * 
     * @param flgAuth
     */
    public void setFlgAuth(String flgAuth) {
		this.flgAuth = flgAuth;
	}
	
    /**
     * 
     * @return
     */
    public String getKeterangan() {
		return Keterangan;
	}
    /**
     * 
     * @param Keterangan
     */
    public void setKeterangan(String Keterangan) {
		this.Keterangan = Keterangan;
	}

}
