package bdsm.model;


/**
 * @author v00019372
 */
@SuppressWarnings("serial")
public class SknNgInOutReturBulkDKE extends SknNgInOutReturBulkParent implements java.io.Serializable {
	private Integer parentRecordId;
	private String nomorUrutDKE;
	private String nomorReferensiDKE;
	private String identitasPesertaPengirimAsalOriginal;
	private String sandiKotaAsalOriginal;
	private String identitasPesertaPenerimaPenerusOriginal;
	private String identitasPesertaPenerimaAkhirOriginal;
	private String jumlahRecordsRincian;
	private String totalNominalRincian;
	private String namaNasabahPenagih;
	private String nomorRekeningNasabahPenagih;
	private String jenisUsahaNasabahPenagih;
	private String jenisNasabahPenagih;
	private String statusPendudukNasabahPenagih;
	private String nomorReferensiDKEOriginal;
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
	
	public String getNomorReferensiDKE() {
		return this.nomorReferensiDKE;
	}
	public void setNomorReferensiDKE(String nomorReferensiDKE) {
		this.nomorReferensiDKE = nomorReferensiDKE;
	}
	
	public String getIdentitasPesertaPengirimAsalOriginal() {
		return this.identitasPesertaPengirimAsalOriginal;
	}
	public void setIdentitasPesertaPengirimAsalOriginal(String identitasPesertaPengirimAsalOriginal) {
		this.identitasPesertaPengirimAsalOriginal = identitasPesertaPengirimAsalOriginal;
	}
	
	public String getSandiKotaAsalOriginal() {
		return this.sandiKotaAsalOriginal;
	}
	public void setSandiKotaAsalOriginal(String sandiKotaAsalOriginal) {
		this.sandiKotaAsalOriginal = sandiKotaAsalOriginal;
	}
	
	public String getIdentitasPesertaPenerimaPenerusOriginal() {
		return this.identitasPesertaPenerimaPenerusOriginal;
	}
	public void setIdentitasPesertaPenerimaPenerusOriginal(String identitasPesertaPenerimaPenerusOriginal) {
		this.identitasPesertaPenerimaPenerusOriginal = identitasPesertaPenerimaPenerusOriginal;
	}
	
	public String getIdentitasPesertaPenerimaAkhirOriginal() {
		return this.identitasPesertaPenerimaAkhirOriginal;
	}
	public void setIdentitasPesertaPenerimaAkhirOriginal(String identitasPesertaPenerimaAkhirOriginal) {
		this.identitasPesertaPenerimaAkhirOriginal = identitasPesertaPenerimaAkhirOriginal;
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
	
	public String getNomorReferensiDKEOriginal() {
		return this.nomorReferensiDKEOriginal;
	}
	public void setNomorReferensiDKEOriginal(String nomorReferensiDKEOriginal) {
		this.nomorReferensiDKEOriginal = nomorReferensiDKEOriginal;
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
			.append(",nomorReferensiDKE=").append(this.nomorReferensiDKE)
			.append(",identitasPesertaPengirimAsalOriginal=").append(this.identitasPesertaPengirimAsalOriginal)
			.append(",sandiKotaAsalOriginal=").append(this.sandiKotaAsalOriginal)
			.append(",identitasPesertaPenerimaPenerusOriginal=").append(this.identitasPesertaPenerimaPenerusOriginal)
			.append(",identitasPesertaPenerimaAkhirOriginal=").append(this.identitasPesertaPenerimaAkhirOriginal)
			.append(",jumlahRecordsRincian=").append(this.jumlahRecordsRincian)
			.append(",totalNominalRincian=").append(this.totalNominalRincian)
			.append(",namaNasabahPenagih=").append(this.namaNasabahPenagih)
			.append(",nomorRekeningNasabahPenagih=").append(this.nomorRekeningNasabahPenagih)
			.append(",jenisUsahaNasabahPenagih=").append(this.jenisUsahaNasabahPenagih)
			.append(",jenisNasabahPenagih=").append(this.jenisNasabahPenagih)
			.append(",statusPendudukNasabahPenagih=").append(this.statusPendudukNasabahPenagih)
			.append(",nomorReferensiDKEOriginal=").append(this.nomorReferensiDKEOriginal)
			.append(",bebanBiaya=").append(this.bebanBiaya)
			.append(",jenisTransaksi=").append(this.jenisTransaksi)
			.append(",SOR=").append(this.SOR)
			.append(",valid=").append(this.valid)
			.append("}");

		return sb.toString();
	}
	
}
