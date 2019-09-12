package bdsm.model;


/**
 * @author v00019372
 */
@SuppressWarnings("serial")
public class SknNgInOutCreditDetail extends SknNgInOutCreditParent implements java.io.Serializable {
	private String identitasPesertaPengirimAsal;
	private String sandiKotaAsal;
	private String senderName;
	private String sourceAccount;
	private String alamatNasabahPengirim;
	private String jenisNasabahPengirim;
	private String statusKependudukanNasabahPengirim;
	private String nomorIdentitasNasabahPengirim;
	private String identitasPesertaPenerimaPenerus;
	private String identitasPesertaPenerimaAkhir;
	private String sandiKotaTujuan;
	private String namaNasabahPenerima;
	private String destinationAccount;
	private String alamatNasabahPenerima;
	private String jenisNasabahPenerima;
	private String statusKependudukanNasabahPenerima;
	private String nomorIdentitasNasabahPenerima;
	private String jenisTransaksi;
	private String jenisSaranaTransaksi;
	private String nominal;
	private String nomorUrut;
	private String nomorReferensi;
	private String nomorReferensiTransaksiAsal;
	private String bebanBiaya;
	private String keterangan;
	private String SOR;
	private String periodeKonfirmasi;
	private Integer parentRecordId;
	
	
	public String getIdentitasPesertaPengirimAsal() {
		return this.identitasPesertaPengirimAsal;
	}
	public void setIdentitasPesertaPengirimAsal(String identitasPesertaPengirimAsal) {
		this.identitasPesertaPengirimAsal = identitasPesertaPengirimAsal;
	}
	
	public String getSandiKotaAsal() {
		return this.sandiKotaAsal;
	}
	public void setSandiKotaAsal(String sandiKotaAsal) {
		this.sandiKotaAsal = sandiKotaAsal;
	}
	
	public String getSenderName() {
		return this.senderName;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	
	public String getSourceAccount() {
		return this.sourceAccount;
	}
	public void setSourceAccount(String sourceAccount) {
		this.sourceAccount = sourceAccount;
	}
	
	public String getAlamatNasabahPengirim() {
		return this.alamatNasabahPengirim;
	}
	public void setAlamatNasabahPengirim(String alamatNasabahPengirim) {
		this.alamatNasabahPengirim = alamatNasabahPengirim;
	}
	
	public String getJenisNasabahPengirim() {
		return this.jenisNasabahPengirim;
	}
	public void setJenisNasabahPengirim(String jenisNasabahPengirim) {
		this.jenisNasabahPengirim = jenisNasabahPengirim;
	}
	
	public String getStatusKependudukanNasabahPengirim() {
		return this.statusKependudukanNasabahPengirim;
	}
	public void setStatusKependudukanNasabahPengirim(String statusKependudukanNasabahPengirim) {
		this.statusKependudukanNasabahPengirim = statusKependudukanNasabahPengirim;
	}
	
	public String getNomorIdentitasNasabahPengirim() {
		return this.nomorIdentitasNasabahPengirim;
	}
	public void setNomorIdentitasNasabahPengirim(String nomorIdentitasNasabahPengirim) {
		this.nomorIdentitasNasabahPengirim = nomorIdentitasNasabahPengirim;
	}
	
	public String getIdentitasPesertaPenerimaPenerus() {
		return this.identitasPesertaPenerimaPenerus;
	}
	public void setIdentitasPesertaPenerimaPenerus(String identitasPesertaPenerimaPenerus) {
		this.identitasPesertaPenerimaPenerus = identitasPesertaPenerimaPenerus;
	}
	
	public String getIdentitasPesertaPenerimaAkhir() {
		return this.identitasPesertaPenerimaAkhir;
	}
	public void setIdentitasPesertaPenerimaAkhir(String identitasPesertaPenerimaAkhir) {
		this.identitasPesertaPenerimaAkhir = identitasPesertaPenerimaAkhir;
	}
	
	public String getSandiKotaTujuan() {
		return this.sandiKotaTujuan;
	}
	public void setSandiKotaTujuan(String sandiKotaTujuan) {
		this.sandiKotaTujuan = sandiKotaTujuan;
	}
	
	public String getNamaNasabahPenerima() {
		return this.namaNasabahPenerima;
	}
	public void setNamaNasabahPenerima(String namaNasabahPenerima) {
		this.namaNasabahPenerima = namaNasabahPenerima;
	}
	
	public String getDestinationAccount() {
		return this.destinationAccount;
	}
	public void setDestinationAccount(String destinationAccount) {
		this.destinationAccount = destinationAccount;
	}
	
	public String getAlamatNasabahPenerima() {
		return this.alamatNasabahPenerima;
	}
	public void setAlamatNasabahPenerima(String alamatNasabahPenerima) {
		this.alamatNasabahPenerima = alamatNasabahPenerima;
	}
	
	public String getJenisNasabahPenerima() {
		return this.jenisNasabahPenerima;
	}
	public void setJenisNasabahPenerima(String jenisNasabahPenerima) {
		this.jenisNasabahPenerima = jenisNasabahPenerima;
	}
	
	public String getStatusKependudukanNasabahPenerima() {
		return this.statusKependudukanNasabahPenerima;
	}
	public void setStatusKependudukanNasabahPenerima(String statusKependudukanNasabahPenerima) {
		this.statusKependudukanNasabahPenerima = statusKependudukanNasabahPenerima;
	}
	
	public String getNomorIdentitasNasabahPenerima() {
		return this.nomorIdentitasNasabahPenerima;
	}
	public void setNomorIdentitasNasabahPenerima(String nomorIdentitasNasabahPenerima) {
		this.nomorIdentitasNasabahPenerima = nomorIdentitasNasabahPenerima;
	}
	
	public String getJenisTransaksi() {
		return this.jenisTransaksi;
	}
	public void setJenisTransaksi(String jenisTransaksi) {
		this.jenisTransaksi = jenisTransaksi;
	}
	
	public String getJenisSaranaTransaksi() {
		return this.jenisSaranaTransaksi;
	}
	public void setJenisSaranaTransaksi(String jenisSaranaTransaksi) {
		this.jenisSaranaTransaksi = jenisSaranaTransaksi;
	}
	
	public String getNominal() {
		return this.nominal;
	}
	public void setNominal(String nominal) {
		this.nominal = nominal;
	}
	
	public String getNomorUrut() {
		return this.nomorUrut;
	}
	public void setNomorUrut(String nomorUrut) {
		this.nomorUrut = nomorUrut;
	}
	
	public String getNomorReferensi() {
		return this.nomorReferensi;
	}
	public void setNomorReferensi(String nomorReferensi) {
		this.nomorReferensi = nomorReferensi;
	}
	
	public String getNomorReferensiTransaksiAsal() {
		return this.nomorReferensiTransaksiAsal;
	}
	public void setNomorReferensiTransaksiAsal(String nomorReferensiTransaksiAsal) {
		this.nomorReferensiTransaksiAsal = nomorReferensiTransaksiAsal;
	}
	
	public String getBebanBiaya() {
		return this.bebanBiaya;
	}
	public void setBebanBiaya(String bebanBiaya) {
		this.bebanBiaya = bebanBiaya;
	}
	
	public String getKeterangan() {
		return this.keterangan;
	}
	public void setKeterangan(String keterangan) {
		this.keterangan = keterangan;
	}
	
	public String getSOR() {
		return this.SOR;
	}
	public void setSOR(String sOR) {
		SOR = sOR;
	}
	
	public String getPeriodeKonfirmasi() {
		return this.periodeKonfirmasi;
	}
	public void setPeriodeKonfirmasi(String periodeKonfirmasi) {
		this.periodeKonfirmasi = periodeKonfirmasi;
	}
	
	public Integer getParentRecordId() {
		return parentRecordId;
	}
	public void setParentRecordId(Integer parentRecordId) {
		this.parentRecordId = parentRecordId;
	}
	
	
	
	@Override
	public String toString() {
    	StringBuilder sb = new StringBuilder(this.getClass().getSimpleName())
			.append("{")
			.append(super.toString())
			.append(",identitasPesertaPengirimAsal=").append(this.identitasPesertaPengirimAsal)
			.append(",sandiKotaAsal=").append(this.sandiKotaAsal)
			.append(",senderName=").append(this.senderName)
			.append(",sourceAccount=").append(this.sourceAccount)
			.append(",alamatNasabahPengirim=").append(this.alamatNasabahPengirim)
			.append(",jenisNasabahPengirim=").append(this.jenisNasabahPengirim)
			.append(",statusKependudukanNasabahPengirim=").append(this.statusKependudukanNasabahPengirim)
			.append(",nomorIdentitasNasabahPengirim=").append(this.nomorIdentitasNasabahPengirim)
			.append(",identitasPesertaPenerimaPenerus=").append(this.identitasPesertaPenerimaPenerus)
			.append(",identitasPesertaPenerimaAkhir=").append(this.identitasPesertaPenerimaAkhir)
			.append(",sandiKotaTujuan=").append(this.sandiKotaTujuan)
			.append(",namaNasabahPenerima=").append(this.namaNasabahPenerima)
			.append(",destinationAccount=").append(this.destinationAccount)
			.append(",alamatNasabahPenerima=").append(this.alamatNasabahPenerima)
			.append(",jenisNasabahPenerima=").append(this.jenisNasabahPenerima)
			.append(",statusKependudukanNasabahPenerima=").append(this.statusKependudukanNasabahPenerima)
			.append(",nomorIdentitasNasabahPenerima=").append(this.nomorIdentitasNasabahPenerima)
			.append(",jenisTransaksi=").append(this.jenisTransaksi)
			.append(",jenisSaranaTransaksi=").append(this.jenisSaranaTransaksi)
			.append(",nominal=").append(this.nominal)
			.append(",nomorUrut=").append(this.nomorUrut)
			.append(",nomorReferensi=").append(this.nomorReferensi)
			.append(",nomorReferensiTransaksiAsal=").append(this.nomorReferensiTransaksiAsal)
			.append(",bebanBiaya=").append(this.bebanBiaya)
			.append(",keterangan=").append(this.keterangan)
			.append(",SOR=").append(this.SOR)
			.append(",periodeKonfirmasi=").append(this.periodeKonfirmasi)
			.append(",parentRecordId=").append(this.parentRecordId)
			.append("}");
    	
		return sb.toString();
	}

}
