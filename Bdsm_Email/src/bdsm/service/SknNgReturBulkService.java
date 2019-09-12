package bdsm.service;


import bdsm.model.SknNgInOutReturBulkDKE;
import bdsm.model.SknNgInOutReturBulkDetail;
import bdsm.model.SknNgInOutReturBulkFooter;
import bdsm.model.SknNgInOutReturBulkHeader;


/**
 * @author v00019372
 */
public abstract class SknNgReturBulkService extends SknNgBulkService {
	public static final String CODE_DKE = "7";
	
	protected static String FORMAT_FILENAME = "sknng_in_out_retur_bulk.properties";
	protected static int POS_G2_AFTER_COMMON;
	protected static int LENGTH_G2_HEADER, LENGTH_G2_DKE, LENGTH_G2_DETAIL, LENGTH_G2_FOOTER;
	protected static java.util.Map<String, Object[]> formatReturG2;
	
	private static org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(SknNgReturBulkService.class);
	
	
	static {
		int ctrColumn[] = {0};
		
		try {
			loadDebitG2Format();
		}
		catch (Exception ex) {
			LOGGER.error(ex, ex);
		}
		
		
		/* common */
		skipWithG2Format("TIPE_RECORD", ctrColumn);
		skipWithG2Format("KODE_DKE", ctrColumn);
		POS_G2_AFTER_COMMON = ctrColumn[0];
		
		/* header */
		ctrColumn[0] = POS_G2_AFTER_COMMON;
		skipWithG2Format("BATCH_ID", ctrColumn);
		skipWithG2Format("JAM_TANGGAL_MESSAGE", ctrColumn);
		skipWithG2Format("JUMLAH_RECORDS_DKE", ctrColumn);
		skipWithG2Format("TOTAL_NOMINAL_DKE", ctrColumn);
		skipWithG2Format("TANGGAL_BATCH", ctrColumn);
		skipWithG2Format("JENIS_SETELMEN", ctrColumn);
		skipWithG2Format("IDENTITAS_PESERTA_PENGIRIM", ctrColumn);
		skipWithG2Format("SANDI_KOTA_PENGIRIM", ctrColumn);
		skipWithG2Format("BATCH_ID_ORIGINAL", ctrColumn);
		skipWithG2Format("IDENTITAS_PESERTA_PENGIRIM_ORIGINAL", ctrColumn);
		LENGTH_G2_HEADER = ctrColumn[0];
		
		/* DKE */
		ctrColumn[0] = POS_G2_AFTER_COMMON;
		skipWithG2Format("NOMOR_URUT_DKE", ctrColumn);
		skipWithG2Format("NOMOR_REFERENSI_DKE", ctrColumn);
		skipWithG2Format("IDENTITAS_PESERTA_PENGIRIM_ASAL_ORIGINAL", ctrColumn);
		skipWithG2Format("SANDI_KOTA_ASAL_ORIGINAL", ctrColumn);
		skipWithG2Format("IDENTITAS_PESERTA_PENERIMA_PENERUS_ORIGINAL", ctrColumn);
		skipWithG2Format("IDENTITAS_PESERTA_PENERIMA_AKHIR_ORIGINAL", ctrColumn);
		skipWithG2Format("JUMLAH_RECORDS_RINCIAN", ctrColumn);
		skipWithG2Format("TOTAL_NOMINAL_RINCIAN", ctrColumn);
		skipWithG2Format("NAMA_NASABAH_PENAGIH", ctrColumn);
		skipWithG2Format("NOMOR_REKENING_NASABAH_PENAGIH", ctrColumn);
		skipWithG2Format("JENIS_USAHA_NASABAH_PENAGIH", ctrColumn);
		skipWithG2Format("JENIS_NASABAH_PENAGIH", ctrColumn);
		skipWithG2Format("STATUS_PENDUDUK_NASABAH_PENAGIH", ctrColumn);
		skipWithG2Format("NOMOR_REFERENSI_DKE_ORIGINAL", ctrColumn);
		skipWithG2Format("BEBAN_BIAYA", ctrColumn);
		skipWithG2Format("JENIS_TRANSAKSI", ctrColumn);
		skipWithG2Format("SOR", ctrColumn);
		LENGTH_G2_DKE = ctrColumn[0];
		
		/* detail */
		ctrColumn[0] = POS_G2_AFTER_COMMON;
		skipWithG2Format("NOMOR_URUT_TRANSAKSI", ctrColumn);
		skipWithG2Format("NAMA_NASABAH_TERTAGIH", ctrColumn);
		skipWithG2Format("NOMOR_REKENING_NASABAH_TERTAGIH", ctrColumn);
		skipWithG2Format("JENIS_NASABAH_TERTAGIH", ctrColumn);
		skipWithG2Format("STATUS_PENDUDUK_NASABAH_TERTAGIH", ctrColumn);
		skipWithG2Format("NOMOR_ID_PELANGGAN", ctrColumn);
		skipWithG2Format("NOMOR_REFERENSI", ctrColumn);
		skipWithG2Format("NOMINAL", ctrColumn);
		skipWithG2Format("ALASAN_PENOLAKAN", ctrColumn);
		skipWithG2Format("NOMOR_URUT_TRANSAKSI_ORIGINAL", ctrColumn);
		LENGTH_G2_DETAIL = ctrColumn[0];
		
		/* footer */
		ctrColumn[0] = POS_G2_AFTER_COMMON;
		skipWithG2Format("CRC", ctrColumn);
		LENGTH_G2_FOOTER = ctrColumn[0];
	}
	
	
	
	@Override
	public int getLenG2Header() {
		return LENGTH_G2_HEADER;
	}

	@Override
	public int getLenG2DKE() {
		return LENGTH_G2_DKE;
	}

	@Override
	public int getLenG2Detail() {
		return LENGTH_G2_DETAIL;
	}

	@Override
	public int getLenG2Footer() {
		return LENGTH_G2_FOOTER;
	}
	
	
	
	public String formatFromHeader(SknNgInOutReturBulkHeader header) {
		StringBuilder sb = new StringBuilder();
		
		header.setTipeRecord(formatWithG2Format(sb, header.getTipeRecord(), "TIPE_RECORD"));
		header.setKodeDKE(formatWithG2Format(sb, header.getKodeDKE(), "KODE_DKE"));
		header.setBatchId(formatWithG2Format(sb, header.getBatchId(), "BATCH_ID"));
		header.setJamTanggalMessage(formatWithG2Format(sb, header.getJamTanggalMessage(), "JAM_TANGGAL_MESSAGE"));
		header.setJumlahRecordsDKE(formatWithG2Format(sb, header.getJumlahRecordsDKE(), "JUMLAH_RECORDS_DKE"));
		header.setTotalNominalDKE(formatWithG2Format(sb, formatNominal(header.getTotalNominalDKE()), "TOTAL_NOMINAL_DKE"));
		header.setTanggalBatch(formatWithG2Format(sb, header.getTanggalBatch(), "TANGGAL_BATCH"));
		header.setJenisSetelmen(formatWithG2Format(sb, header.getJenisSetelmen(), "JENIS_SETELMEN"));
		header.setIdentitasPesertaPengirim(formatWithG2Format(sb, header.getIdentitasPesertaPengirim(), "IDENTITAS_PESERTA_PENGIRIM"));
		header.setSandiKotaPengirim(formatWithG2Format(sb, header.getSandiKotaPengirim(), "SANDI_KOTA_PENGIRIM"));
		header.setBatchIdOriginal(formatWithG2Format(sb, header.getBatchIdOriginal(), "BATCH_ID_ORIGINAL"));
		header.setIdentitasPesertaPengirimOriginal(formatWithG2Format(sb, header.getIdentitasPesertaPengirimOriginal(), "IDENTITAS_PESERTA_PENGIRIM_ORIGINAL"));
		
		return sb.toString();
	}
	
	public String formatFromDKE(SknNgInOutReturBulkDKE dke) {
		StringBuilder sb = new StringBuilder();
		
		dke.setTipeRecord(formatWithG2Format(sb, dke.getTipeRecord(), "TIPE_RECORD"));
		dke.setKodeDKE(formatWithG2Format(sb, dke.getKodeDKE(), "KODE_DKE"));
		dke.setNomorUrutDKE(formatWithG2Format(sb, dke.getNomorUrutDKE(), "NOMOR_URUT_DKE"));
		dke.setNomorReferensiDKE(formatWithG2Format(sb, dke.getNomorReferensiDKE(), "NOMOR_REFERENSI_DKE"));
		dke.setIdentitasPesertaPengirimAsalOriginal(formatWithG2Format(sb, dke.getIdentitasPesertaPengirimAsalOriginal(), "IDENTITAS_PESERTA_PENGIRIM_ASAL_ORIGINAL"));
		dke.setSandiKotaAsalOriginal(formatWithG2Format(sb, dke.getSandiKotaAsalOriginal(), "SANDI_KOTA_ASAL_ORIGINAL"));
		dke.setIdentitasPesertaPenerimaPenerusOriginal(formatWithG2Format(sb, dke.getIdentitasPesertaPenerimaPenerusOriginal(), "IDENTITAS_PESERTA_PENERIMA_PENERUS_ORIGINAL"));
		dke.setIdentitasPesertaPenerimaAkhirOriginal(formatWithG2Format(sb, dke.getIdentitasPesertaPenerimaAkhirOriginal(), "IDENTITAS_PESERTA_PENERIMA_AKHIR_ORIGINAL"));
		dke.setJumlahRecordsRincian(formatWithG2Format(sb, dke.getJumlahRecordsRincian(), "JUMLAH_RECORDS_RINCIAN"));
		dke.setTotalNominalRincian(formatWithG2Format(sb, formatNominal(dke.getTotalNominalRincian()), "TOTAL_NOMINAL_RINCIAN"));
		dke.setNamaNasabahPenagih(formatWithG2Format(sb, dke.getNamaNasabahPenagih(), "NAMA_NASABAH_PENAGIH"));
		dke.setNomorRekeningNasabahPenagih(formatWithG2Format(sb, dke.getNomorRekeningNasabahPenagih(), "NOMOR_REKENING_NASABAH_PENAGIH"));
		dke.setJenisUsahaNasabahPenagih(formatWithG2Format(sb, dke.getJenisUsahaNasabahPenagih(), "JENIS_USAHA_NASABAH_PENAGIH"));
		dke.setJenisNasabahPenagih(formatWithG2Format(sb, dke.getJenisNasabahPenagih(), "JENIS_NASABAH_PENAGIH"));
		dke.setStatusPendudukNasabahPenagih(formatWithG2Format(sb, dke.getStatusPendudukNasabahPenagih(), "STATUS_PENDUDUK_NASABAH_PENAGIH"));
		dke.setNomorReferensiDKEOriginal(formatWithG2Format(sb, dke.getNomorReferensiDKEOriginal(), "NOMOR_REFERENSI_DKE_ORIGINAL"));
		dke.setBebanBiaya(formatWithG2Format(sb, dke.getBebanBiaya(), "BEBAN_BIAYA"));
		dke.setJenisTransaksi(formatWithG2Format(sb, dke.getJenisTransaksi(), "JENIS_TRANSAKSI"));
		dke.setSOR(formatWithG2Format(sb, dke.getSOR(), "SOR"));
		
		return sb.toString();
	}
	
	public String formatFromDetail(SknNgInOutReturBulkDetail detail) {
		StringBuilder sb = new StringBuilder();
		
		detail.setTipeRecord(formatWithG2Format(sb, detail.getTipeRecord(), "TIPE_RECORD"));
		detail.setKodeDKE(formatWithG2Format(sb, detail.getKodeDKE(), "KODE_DKE"));
		detail.setNomorUrutTransaksi(formatWithG2Format(sb, detail.getNomorUrutTransaksi(), "NOMOR_URUT_TRANSAKSI"));
		detail.setNamaNasabahTertagih(formatWithG2Format(sb, detail.getNamaNasabahTertagih(), "NAMA_NASABAH_TERTAGIH"));
		detail.setNomorRekeningNasabahTertagih(formatWithG2Format(sb, detail.getNomorRekeningNasabahTertagih(), "NOMOR_REKENING_NASABAH_TERTAGIH"));
		detail.setJenisNasabahTertagih(formatWithG2Format(sb, detail.getJenisNasabahTertagih(), "JENIS_NASABAH_TERTAGIH"));
		detail.setStatusPendudukNasabahTertagih(formatWithG2Format(sb, detail.getStatusPendudukNasabahTertagih(), "STATUS_PENDUDUK_NASABAH_TERTAGIH"));
		detail.setNomorIdPelanggan(formatWithG2Format(sb, detail.getNomorIdPelanggan(), "NOMOR_ID_PELANGGAN"));
		detail.setNomorReferensi(formatWithG2Format(sb, detail.getNomorReferensi(), "NOMOR_REFERENSI"));
		detail.setNominal(formatWithG2Format(sb, formatNominal(detail.getNominal()), "NOMINAL"));
		detail.setAlasanPenolakan(formatWithG2Format(sb, detail.getAlasanPenolakan(), "ALASAN_PENOLAKAN"));
		detail.setNomorUrutTransaksiOriginal(formatWithG2Format(sb, detail.getNomorUrutTransaksiOriginal(), "NOMOR_URUT_TRANSAKSI_ORIGINAL"));
		
		return sb.toString();
	}
	
	public String formatFromFooter(SknNgInOutReturBulkFooter footer) {
		StringBuilder sb = new StringBuilder();
		
		footer.setTipeRecord(formatWithG2Format(sb, footer.getTipeRecord(), "TIPE_RECORD"));
		footer.setKodeDKE(formatWithG2Format(sb, footer.getKodeDKE(), "KODE_DKE"));
		footer.setCRC(formatWithG2Format(sb, footer.getCRC(), "CRC"));
		
		return sb.toString();
	}
	
	
	protected static synchronized void loadDebitG2Format() throws Exception {
		if (formatReturG2 == null)
			formatReturG2 = loadFixLengthFormat(FORMAT_FILENAME);
	}
	
	protected static String formatWithG2Format(StringBuilder sb, Object input, String formatKey) {
		return formatSKN(formatReturG2, sb, input, formatKey);
	}
	
	protected static String parseWithG2Format(String input, String formatKey, int[] position) {
		return parseSKN(formatReturG2, input, formatKey, position);
	}
	
	protected static int skipWithG2Format(String formatKey, int[] position) {
		return skipSKN(formatReturG2, formatKey, position);
	}
	
}
