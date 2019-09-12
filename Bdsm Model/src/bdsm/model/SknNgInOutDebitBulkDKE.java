package bdsm.model;


/**
 * @author v00019372
 */
@SuppressWarnings("serial")
public class SknNgInOutDebitBulkDKE extends SknNgInOutDebitBulkParent implements java.io.Serializable {
	private Integer parentRecordId;
	private String nomorUrutDKE;
	private String identitasPesertaPengirimAsal;
	private String sandiKotaAsal;
	private String identitasPesertaPenerimaPenerus;
	private String identitasPesertaPenerimaAkhir;
	private String jumlahRecordsRincian;
	private String totalNominalRincian;
	private String namaNasabahPenagih;
	private String nomorRekeningNasabahPenagih;
	private String jenisUsahaNasabahPenagih;
	private String jenisNasabahPenagih;
	private String statusPendudukNasabahPenagih;
	private String nomorReferensiDKE;
	private String bebanBiaya;
	private String jenisTransaksi;
	private String SOR;
	private Boolean valid;
	
	
	public Integer getParentRecordId() {
		return this.parentRecordId;
	}
	public void setParentRecordId(Integer parentRecordId) {
		this.parentRecordId = parentRecordId;
	}
	
	public String getNomorUrutDKE() {
		return this.nomorUrutDKE;
	}
	public void setNomorUrutDKE(String nomorUrutDKE) {
		this.nomorUrutDKE = nomorUrutDKE;
	}
	
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
	
	public String getJumlahRecordsRincian() {
		return this.jumlahRecordsRincian;
	}
	public void setJumlahRecordsRincian(String jumlahRecordsRincian) {
		this.jumlahRecordsRincian = jumlahRecordsRincian;
	}
	
	public String getTotalNominalRincian() {
		return this.totalNominalRincian;
	}
	public void setTotalNominalRincian(String totalNominalRincian) {
		this.totalNominalRincian = totalNominalRincian;
	}
	
	public String getNamaNasabahPenagih() {
		return this.namaNasabahPenagih;
	}
	public void setNamaNasabahPenagih(String namaNasabahPenagih) {
		this.namaNasabahPenagih = namaNasabahPenagih;
	}
	
	public String getNomorRekeningNasabahPenagih() {
		return this.nomorRekeningNasabahPenagih;
	}
	public void setNomorRekeningNasabahPenagih(String nomorRekeningNasabahPenagih) {
		this.nomorRekeningNasabahPenagih = nomorRekeningNasabahPenagih;
	}
	
	public String getJenisUsahaNasabahPenagih() {
		return this.jenisUsahaNasabahPenagih;
	}
	public void setJenisUsahaNasabahPenagih(String jenisUsahaNasabahPenagih) {
		this.jenisUsahaNasabahPenagih = jenisUsahaNasabahPenagih;
	}
	
	public String getJenisNasabahPenagih() {
		return this.jenisNasabahPenagih;
	}
	public void setJenisNasabahPenagih(String jenisNasabahPenagih) {
		this.jenisNasabahPenagih = jenisNasabahPenagih;
	}
	
	public String getStatusPendudukNasabahPenagih() {
		return this.statusPendudukNasabahPenagih;
	}
	public void setStatusPendudukNasabahPenagih(String statusPendudukNasabahPenagih) {
		this.statusPendudukNasabahPenagih = statusPendudukNasabahPenagih;
	}
	
	public String getNomorReferensiDKE() {
		return this.nomorReferensiDKE;
	}
	public void setNomorReferensiDKE(String nomorReferensiDKE) {
		this.nomorReferensiDKE = nomorReferensiDKE;
	}
	
	public String getBebanBiaya() {
		return this.bebanBiaya;
	}
	public void setBebanBiaya(String bebanBiaya) {
		this.bebanBiaya = bebanBiaya;
	}
	
	public String getJenisTransaksi() {
		return this.jenisTransaksi;
	}
	public void setJenisTransaksi(String jenisTransaksi) {
		this.jenisTransaksi = jenisTransaksi;
	}
	
	public String getSOR() {
		return this.SOR;
	}
	public void setSOR(String sOR) {
		SOR = sOR;
	}
	
	public Boolean isValid() {
		return this.valid;
	}
	public void setValid(Boolean valid) {
		this.valid = valid;
	}
	
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(this.getClass().getSimpleName())
			.append("{").append(super.toString())
			.append(",parentRecordId=").append(this.parentRecordId)
			.append(",nomorUrutDKE=").append(this.nomorUrutDKE)
			.append(",identitasPesertaPengirimAsal=").append(this.identitasPesertaPengirimAsal)
			.append(",sandiKotaAsal=").append(this.sandiKotaAsal)
			.append(",identitasPesertaPenerimaPenerus=").append(this.identitasPesertaPenerimaPenerus)
			.append(",identitasPesertaPenerimaAkhir=").append(this.identitasPesertaPenerimaAkhir)
			.append(",jumlahRecordsRincian=").append(this.jumlahRecordsRincian)
			.append(",totalNominalRincian=").append(this.totalNominalRincian)
			.append(",namaNasabahPenagih=").append(this.namaNasabahPenagih)
			.append(",nomorRekeningNasabahPenagih=").append(this.nomorRekeningNasabahPenagih)
			.append(",jenisUsahaNasabahPenagih=").append(this.jenisUsahaNasabahPenagih)
			.append(",jenisNasabahPenagih=").append(this.jenisNasabahPenagih)
			.append(",statusPendudukNasabahPenagih=").append(this.statusPendudukNasabahPenagih)
			.append(",nomorReferensiDKE=").append(this.nomorReferensiDKE)
			.append(",bebanBiaya=").append(this.bebanBiaya)
			.append(",jenisTransaksi=").append(this.jenisTransaksi)
			.append(",SOR=").append(this.SOR)
			.append(",valid=").append(this.valid)
			.append("}");

		return sb.toString();
	}
	
}
