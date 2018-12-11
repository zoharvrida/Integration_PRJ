package bdsm.service;


import org.hibernate.Session;

import bdsm.model.SknNgInOutDebitBulkDKE;
import bdsm.model.SknNgInOutDebitBulkDetail;
import bdsm.model.SknNgInOutDebitBulkFooter;
import bdsm.model.SknNgInOutDebitBulkHeader;
import bdsm.model.SknNgPK;


/**
 * @author v00019372
 */
public class SknNgInwardDebitBulkService extends SknNgDebitBulkService {
    /**
     * 
     */
    public static final String TYPE_IN = "I";
	
	
    /**
     * 
     * @param header
     * @return
     */
    public String formatFromHeader(SknNgInOutDebitBulkHeader header) {
		StringBuilder sb = new StringBuilder();
		
		header.setTipeRecord(formatWithG2Format(sb, header.getTipeRecord(), "TIPE_RECORD"));
		header.setKodeDKE(formatWithG2Format(sb, header.getKodeDKE(), "KODE_DKE"));
		header.setBatchId(formatWithG2Format(sb, header.getBatchId(), "BATCH_ID"));
		header.setJamTanggalMessage(formatWithG2Format(sb, header.getJamTanggalMessage(), "JAM_TANGGAL_MESSAGE"));
		header.setJumlahRecordsDKE(formatWithG2Format(sb, header.getJumlahRecordsDKE(), "JUMLAH_RECORDS_DKE"));
		header.setTotalNominalDKE(formatWithG2Format(sb, header.getTotalNominalDKE(), "TOTAL_NOMINAL_DKE"));
		header.setTanggalBatch(formatWithG2Format(sb, header.getTanggalBatch(), "TANGGAL_BATCH"));
		header.setJenisSetelmen(formatWithG2Format(sb, header.getJenisSetelmen(), "JENIS_SETELMEN"));
		header.setIdentitasPesertaPengirim(formatWithG2Format(sb, header.getIdentitasPesertaPengirim(), "IDENTITAS_PESERTA_PENGIRIM"));
		header.setSandiKotaPengirim(formatWithG2Format(sb, header.getSandiKotaPengirim(), "SANDI_KOTA_PENGIRIM"));
		
		return sb.toString();
	}
	
    /**
     * 
     * @param footer
     * @return
     */
    public String formatFromFooter(SknNgInOutDebitBulkFooter footer) {
		StringBuilder sb = new StringBuilder();
		
		footer.setTipeRecord(formatWithG2Format(sb, footer.getTipeRecord(), "TIPE_RECORD"));
		footer.setKodeDKE(formatWithG2Format(sb, footer.getKodeDKE(), "KODE_DKE"));
		footer.setCRC(formatWithG2Format(sb, footer.getCRC(), "CRC"));
		
		return sb.toString();
	}
	
	
    /**
     * 
     * @param line
     * @param batchNo
     * @param recordId
     * @return
     */
    public SknNgInOutDebitBulkHeader parseToHeader(String line, String batchNo, Integer recordId) {
		int ctrColumn[] = {0};
		SknNgInOutDebitBulkHeader header = new SknNgInOutDebitBulkHeader();
		
		header.setCompositeId(new SknNgPK(batchNo, recordId));
		header.setType(TYPE_IN);
		header.setTipeRecord(parseWithG2Format(line, "TIPE_RECORD", ctrColumn));
		header.setKodeDKE(parseWithG2Format(line, "KODE_DKE", ctrColumn));
		header.setBatchId(parseWithG2Format(line, "BATCH_ID", ctrColumn));
		header.setJamTanggalMessage(parseWithG2Format(line, "JAM_TANGGAL_MESSAGE", ctrColumn));
		header.setJumlahRecordsDKE(parseWithG2Format(line, "JUMLAH_RECORDS_DKE", ctrColumn));
		header.setTotalNominalDKE(parseWithG2Format(line, "TOTAL_NOMINAL_DKE", ctrColumn));
		header.setTanggalBatch(parseWithG2Format(line, "TANGGAL_BATCH", ctrColumn));
		header.setJenisSetelmen(parseWithG2Format(line, "JENIS_SETELMEN", ctrColumn));
		header.setIdentitasPesertaPengirim(parseWithG2Format(line, "IDENTITAS_PESERTA_PENGIRIM", ctrColumn));
		header.setSandiKotaPengirim(parseWithG2Format(line, "SANDI_KOTA_PENGIRIM", ctrColumn));
		
		return header;
	}
	
    /**
     * 
     * @param line
     * @param batchNo
     * @param recordId
     * @return
     */
    public SknNgInOutDebitBulkDKE parseToDKE(String line, String batchNo, Integer recordId) {
		int ctrColumn[] = {0};
		SknNgInOutDebitBulkDKE DKE = new SknNgInOutDebitBulkDKE();
		
		DKE.setCompositeId(new SknNgPK(batchNo, recordId));
		DKE.setTipeRecord(parseWithG2Format(line, "TIPE_RECORD", ctrColumn));
		DKE.setKodeDKE(parseWithG2Format(line, "KODE_DKE", ctrColumn));
		DKE.setNomorUrutDKE(parseWithG2Format(line, "NOMOR_URUT_DKE", ctrColumn));
		DKE.setIdentitasPesertaPengirimAsal(parseWithG2Format(line, "IDENTITAS_PESERTA_PENGIRIM_ASAL", ctrColumn));
		DKE.setSandiKotaAsal(parseWithG2Format(line, "SANDI_KOTA_ASAL", ctrColumn));
		DKE.setIdentitasPesertaPenerimaPenerus(parseWithG2Format(line, "IDENTITAS_PESERTA_PENERIMA_PENERUS", ctrColumn));
		DKE.setIdentitasPesertaPenerimaAkhir(parseWithG2Format(line, "IDENTITAS_PESERTA_PENERIMA_AKHIR", ctrColumn));
		DKE.setJumlahRecordsRincian(parseWithG2Format(line, "JUMLAH_RECORDS_RINCIAN", ctrColumn));
		DKE.setTotalNominalRincian(parseWithG2Format(line, "TOTAL_NOMINAL_RINCIAN", ctrColumn));
		DKE.setNamaNasabahPenagih(parseWithG2Format(line, "NAMA_NASABAH_PENAGIH", ctrColumn));
		DKE.setNomorRekeningNasabahPenagih(parseWithG2Format(line, "NOMOR_REKENING_NASABAH_PENAGIH", ctrColumn));
		DKE.setJenisUsahaNasabahPenagih(parseWithG2Format(line, "JENIS_USAHA_NASABAH_PENAGIH", ctrColumn));
		DKE.setJenisNasabahPenagih(parseWithG2Format(line, "JENIS_NASABAH_PENAGIH", ctrColumn));
		DKE.setStatusPendudukNasabahPenagih(parseWithG2Format(line, "STATUS_PENDUDUK_NASABAH_PENAGIH", ctrColumn));
		DKE.setNomorReferensiDKE(parseWithG2Format(line, "NOMOR_REFERENSI_DKE", ctrColumn));
		DKE.setBebanBiaya(parseWithG2Format(line, "BEBAN_BIAYA", ctrColumn));
		DKE.setJenisTransaksi(parseWithG2Format(line, "JENIS_TRANSAKSI", ctrColumn));
		DKE.setSOR(parseWithG2Format(line, "SOR", ctrColumn));
		
		return DKE;
	}
	
    /**
     * 
     * @param line
     * @param batchNo
     * @param recordId
     * @return
     */
    public SknNgInOutDebitBulkDetail parseToDetail(String line, String batchNo, Integer recordId) {
		int ctrColumn[] = {0};
		SknNgInOutDebitBulkDetail detail = new SknNgInOutDebitBulkDetail();
		
		detail.setCompositeId(new SknNgPK(batchNo, recordId));
		detail.setTipeRecord(parseWithG2Format(line, "TIPE_RECORD", ctrColumn));
		detail.setKodeDKE(parseWithG2Format(line, "KODE_DKE", ctrColumn));
		detail.setNomorUrutTransaksi(parseWithG2Format(line, "NOMOR_URUT_TRANSAKSI", ctrColumn));
		detail.setNamaNasabahTertagih(parseWithG2Format(line, "NAMA_NASABAH_TERTAGIH", ctrColumn));
		detail.setNomorRekeningNasabahTertagih(parseWithG2Format(line, "NOMOR_REKENING_NASABAH_TERTAGIH", ctrColumn));
		detail.setJenisNasabahTertagih(parseWithG2Format(line, "JENIS_NASABAH_TERTAGIH", ctrColumn));
		detail.setStatusPendudukNasabahTertagih(parseWithG2Format(line, "STATUS_PENDUDUK_NASABAH_TERTAGIH", ctrColumn));
		detail.setNomorIdPelanggan(parseWithG2Format(line, "NOMOR_ID_PELANGGAN", ctrColumn));
		detail.setNomorReferensi(parseWithG2Format(line, "NOMOR_REFERENSI", ctrColumn));
		detail.setNominal(parseWithG2Format(line, "NOMINAL", ctrColumn));
		
		return detail;
	}
	
    /**
     * 
     * @param line
     * @param batchNo
     * @param recordId
     * @return
     */
    public SknNgInOutDebitBulkFooter parseToFooter(String line, String batchNo, Integer recordId) {
		int ctrColumn[] = {0};
		SknNgInOutDebitBulkFooter footer = new SknNgInOutDebitBulkFooter();
		
		footer.setCompositeId(new SknNgPK(batchNo, recordId));
		footer.setTipeRecord(parseWithG2Format(line, "TIPE_RECORD", ctrColumn));
		footer.setKodeDKE(parseWithG2Format(line, "KODE_DKE", ctrColumn));
		footer.setCRC(parseWithG2Format(line, "CRC", ctrColumn));
		
		return footer;
	}
	
	
    /**
     * 
     * @param session
     * @param BIC
     * @return
     */
    public String getResultOfRequestInwardWSToSPK(Session session, String BIC) {
		return (new SknNgWSProcessInwardDebitBulk(session, BIC).requestInwardWSToSPK());
	}

	
	
    /**
     * 
     */
    protected class SknNgWSProcessInwardDebitBulk extends SknNgInwardWSProcess {
        /**
         * 
         * @param session
         * @param BIC
         */
        public SknNgWSProcessInwardDebitBulk(Session session, String BIC) {
			super(session, BIC, BIC);
		}

        /**
         * 
         * @return
         */
        @Override
		public String getWebServiceOperationName() {
			return "getDKEPenyerahanInwardBulk";
		}
	}
	
}
