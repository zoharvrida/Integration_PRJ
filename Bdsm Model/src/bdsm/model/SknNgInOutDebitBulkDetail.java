package bdsm.model;


/**
 * @author v00019372
 */
@SuppressWarnings("serial")
public class SknNgInOutDebitBulkDetail extends SknNgInOutDebitBulkParent implements java.io.Serializable {
	private Integer parentRecordId;
	private String nomorUrutTransaksi;
	private String namaNasabahTertagih;
	private String nomorRekeningNasabahTertagih;
	private String jenisNasabahTertagih;
	private String statusPendudukNasabahTertagih;
	private String nomorIdPelanggan;
	private String nomorReferensi;
	private String nominal;
	private Long FCRRecordId;
	private String status;
	private String rejectCode;
	private String comments;
	private String recordStatus;
	private Boolean retur;
	
	
	
	public Integer getParentRecordId() {
		return this.parentRecordId;
	}
	public void setParentRecordId(Integer parentRecordId) {
		this.parentRecordId = parentRecordId;
	}
	
	public String getNomorUrutTransaksi() {
		return this.nomorUrutTransaksi;
	}
	public void setNomorUrutTransaksi(String nomorUrutTransaksi) {
		this.nomorUrutTransaksi = nomorUrutTransaksi;
	}
	
	public String getNamaNasabahTertagih() {
		return this.namaNasabahTertagih;
	}
	public void setNamaNasabahTertagih(String namaNasabahTertagih) {
		this.namaNasabahTertagih = namaNasabahTertagih;
	}
	
	public String getNomorRekeningNasabahTertagih() {
		return this.nomorRekeningNasabahTertagih;
	}
	public void setNomorRekeningNasabahTertagih(String nomorRekeningNasabahTertagih) {
		this.nomorRekeningNasabahTertagih = nomorRekeningNasabahTertagih;
	}
	
	public String getJenisNasabahTertagih() {
		return this.jenisNasabahTertagih;
	}
	public void setJenisNasabahTertagih(String jenisNasabahTertagih) {
		this.jenisNasabahTertagih = jenisNasabahTertagih;
	}
	
	public String getStatusPendudukNasabahTertagih() {
		return statusPendudukNasabahTertagih;
	}
	public void setStatusPendudukNasabahTertagih(String statusPendudukNasabahTertagih) {
		this.statusPendudukNasabahTertagih = statusPendudukNasabahTertagih;
	}
	
	public String getNomorIdPelanggan() {
		return this.nomorIdPelanggan;
	}
	public void setNomorIdPelanggan(String nomorIdPelanggan) {
		this.nomorIdPelanggan = nomorIdPelanggan;
	}
	
	public String getNomorReferensi() {
		return this.nomorReferensi;
	}
	public void setNomorReferensi(String nomorReferensi) {
		this.nomorReferensi = nomorReferensi;
	}
	
	public String getNominal() {
		return this.nominal;
	}
	public void setNominal(String nominal) {
		this.nominal = nominal;
	}
	
	public Long getFCRRecordId() {
		return this.FCRRecordId;
	}
	public void setFCRRecordId(Long FCRRecordId) {
		this.FCRRecordId = FCRRecordId;
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
	
	public Boolean isRetur() {
		return this.retur;
	}
	public void setRetur(Boolean retur) {
		this.retur = retur;
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(this.getClass().getSimpleName())
			.append("{").append(super.toString())
			.append(",parentRecordId=").append(this.parentRecordId)
			.append(",nomorUrutTransaksi=").append(this.nomorUrutTransaksi)
			.append(",namaNasabahTertagih=").append(this.namaNasabahTertagih)
			.append(",nomorRekeningNasabahTertagih=").append(this.nomorRekeningNasabahTertagih)
			.append(",jenisNasabahTertagih=").append(this.jenisNasabahTertagih)
			.append(",statusPendudukNasabahTertagih=").append(this.statusPendudukNasabahTertagih)
			.append(",nomorIdPelanggan=").append(this.nomorIdPelanggan)
			.append(",nomorReferensi=").append(this.nomorReferensi)
			.append(",nominal=").append(this.nominal)
			.append(",FCRRecordId=").append(this.FCRRecordId)
			.append(",status=").append(this.status)
			.append(",rejectCode=").append(this.rejectCode)
			.append(",comments=").append(this.comments)
			.append(",recordStatus=").append(this.recordStatus)
			.append(",retur=").append(this.retur)
			.append("}");

		return sb.toString();
	}
	
}
