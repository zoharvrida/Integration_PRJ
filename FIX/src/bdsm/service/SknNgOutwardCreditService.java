package bdsm.service;


import bdsm.model.SknNgInOutCreditDetail;
import bdsm.model.SknNgInOutCreditFooter;
import bdsm.model.SknNgInOutCreditHeader;
import bdsm.model.SknNgPK;
import bdsm.model.SknNgWSAuditTrailBatch;
import bdsm.model.SknNgWSAuditTrailDetail;
import bdsm.model.SknNgWSAuditTrailHeader;


/**
 * @author v00019372
 */
public class SknNgOutwardCreditService extends SknNgCreditService {
    /**
     * 
     */
    public static final String TYPE_OUT = "O";
    /**
     * 
     */
    public static final Byte SKNNG_OUTWARD_CREDIT_WS = Byte.valueOf((byte) 1);
	
	
	
	/* === [Begin] SknNg In Out === */
    /**
     * 
     * @param line
     * @param batchNo
     * @param recordId
     * @return
     */
    public SknNgInOutCreditHeader parseToInOutHeader(String line, String batchNo, Integer recordId) {
		int ctrColumn[] = {0};
		SknNgInOutCreditHeader header = new SknNgInOutCreditHeader();
		
		header.setCompositeId(new SknNgPK(batchNo, recordId));
		header.setType(TYPE_OUT);
		header.setTipeRecord(parseWithG2Format(line, "TIPE_RECORD", ctrColumn));
		header.setKodeDke(parseWithG2Format(line, "KODE_DKE", ctrColumn));
		header.setBatchId(parseWithG2Format(line, "BATCH_ID", ctrColumn));
		header.setJamTanggalMessage(parseWithG2Format(line, "JAM_TANGGAL_MESSAGE", ctrColumn));
		header.setJumlahRecords(parseWithG2Format(line, "JUMLAH_RECORDS", ctrColumn));
		header.setTotalNominal(parseWithG2Format(line, "TOTAL_NOMINAL", ctrColumn));
		header.setTanggalBatch(parseWithG2Format(line, "TANGGAL_BATCH", ctrColumn));
		header.setJenisSettlement(parseWithG2Format(line, "JENIS_SETTLEMENT", ctrColumn));
		header.setIdentitasPesertaPengirimPenerus(parseWithG2Format(line, "IDENTITAS_PESERTA_PENGIRIM_PENERUS", ctrColumn));
		header.setSandiKotaAsal(parseWithG2Format(line, "SANDI_KOTA_ASAL", ctrColumn));
		
		return header;
	}
	
    /**
     * 
     * @param line
     * @param batchNo
     * @param recordId
     * @param header
     * @return
     */
    public SknNgInOutCreditDetail parseToInOutDetail(String line, String batchNo, Integer recordId, SknNgInOutCreditHeader header) {
		int ctrColumn[] = {0};
		SknNgInOutCreditDetail detail = new SknNgInOutCreditDetail();
		
		detail.setCompositeId(new SknNgPK(batchNo, recordId));
		detail.setType(TYPE_OUT);
		detail.setParentRecordId(header.getCompositeId().getRecordId());
		detail.setTipeRecord(parseWithG2Format(line, "TIPE_RECORD", ctrColumn));
		detail.setKodeDke(parseWithG2Format(line, "KODE_DKE", ctrColumn));
		detail.setIdentitasPesertaPengirimAsal(parseWithG2Format(line, "IDENTITAS_PESERTA_PENGIRIM_ASAL", ctrColumn));
		detail.setSandiKotaAsal(parseWithG2Format(line, "SANDI_KOTA_ASAL", ctrColumn));
		detail.setSenderName(parseWithG2Format(line, "SENDER_NAME", ctrColumn));
		detail.setSourceAccount(parseWithG2Format(line, "SOURCE_ACCOUNT", ctrColumn));
		detail.setAlamatNasabahPengirim(parseWithG2Format(line, "ALAMAT_NASABAH_PENGIRIM", ctrColumn));
		detail.setJenisNasabahPengirim(parseWithG2Format(line, "JENIS_NASABAH_PENGIRIM", ctrColumn));
		detail.setStatusKependudukanNasabahPengirim(parseWithG2Format(line, "STATUS_KEPENDUDUKAN_NASABAH_PENGIRIM", ctrColumn));
		detail.setNomorIdentitasNasabahPengirim(parseWithG2Format(line, "NOMOR_IDENTITAS_NASABAH_PENGIRIM", ctrColumn));
		detail.setIdentitasPesertaPenerimaPenerus(parseWithG2Format(line, "IDENTITAS_PESERTA_PENERIMA_PENERUS", ctrColumn));
		detail.setIdentitasPesertaPenerimaAkhir(parseWithG2Format(line, "IDENTITAS_PESERTA_PENERIMA_AKHIR", ctrColumn));
		detail.setSandiKotaTujuan(parseWithG2Format(line, "SANDI_KOTA_TUJUAN", ctrColumn));
		detail.setNamaNasabahPenerima(parseWithG2Format(line, "NAMA_NASABAH_PENERIMA", ctrColumn));
		detail.setDestinationAccount(parseWithG2Format(line, "DESTINATION_ACCOUNT", ctrColumn));
		detail.setAlamatNasabahPenerima(parseWithG2Format(line, "ALAMAT_NASABAH_PENERIMA", ctrColumn));
		detail.setJenisNasabahPenerima(parseWithG2Format(line, "JENIS_NASABAH_PENERIMA", ctrColumn));
		detail.setStatusKependudukanNasabahPenerima(parseWithG2Format(line, "STATUS_KEPENDUDUKAN_NASABAH_PENERIMA", ctrColumn));
		detail.setNomorIdentitasNasabahPenerima(parseWithG2Format(line, "NOMOR_IDENTITAS_NASABAH_PENERIMA", ctrColumn));
		detail.setJenisTransaksi(parseWithG2Format(line, "JENIS_TRANSAKSI", ctrColumn));
		detail.setJenisSaranaTransaksi(parseWithG2Format(line, "JENIS_SARANA_TRANSAKSI", ctrColumn));
		detail.setNominal(parseWithG2Format(line, "NOMINAL", ctrColumn));
		detail.setNomorUrut(parseWithG2Format(line, "NOMOR_URUT", ctrColumn));
		detail.setNomorReferensi(parseWithG2Format(line, "NOMOR_REFERENSI", ctrColumn));
		detail.setNomorReferensiTransaksiAsal(parseWithG2Format(line, "NOMOR_REFERENSI_TRANSAKSI_ASAL", ctrColumn));
		detail.setBebanBiaya(parseWithG2Format(line, "BEBAN_BIAYA", ctrColumn));
		detail.setKeterangan(parseWithG2Format(line, "KETERANGAN", ctrColumn));
		detail.setSOR(parseWithG2Format(line, "SOR", ctrColumn));
		detail.setPeriodeKonfirmasi(parseWithG2Format(line, "PERIODE_KONFIRMASI", ctrColumn));
		
		return detail;
	}
	
    /**
     * 
     * @param line
     * @param batchNo
     * @param recordId
     * @param header
     * @return
     */
    public SknNgInOutCreditFooter parseToInOutFooter(String line, String batchNo, Integer recordId, SknNgInOutCreditHeader header) {
		int ctrColumn[] = {0};
		SknNgInOutCreditFooter footer = new SknNgInOutCreditFooter();
		
		footer.setCompositeId(new SknNgPK(batchNo, recordId));
		footer.setType(TYPE_OUT);
		footer.setParentRecordId(header.getCompositeId().getRecordId());
		footer.setTipeRecord(parseWithG2Format(line, "TIPE_RECORD", ctrColumn));
		footer.setKodeDke(parseWithG2Format(line, "KODE_DKE", ctrColumn));
		footer.setCRC(parseWithG2Format(line, "CRC", ctrColumn).trim());
		
		return footer;
	}
	
	
	
    /**
     * 
     * @param modelHeader
     * @return
     */
    public String formatFromInOutHeader(SknNgInOutCreditHeader modelHeader) {
		StringBuilder sb = new StringBuilder();
		
		modelHeader.setTipeRecord(formatWithG2Format(sb, modelHeader.getTipeRecord(), "TIPE_RECORD"));
		modelHeader.setKodeDke(formatWithG2Format(sb, modelHeader.getKodeDke(), "KODE_DKE"));
		modelHeader.setBatchId(formatWithG2Format(sb, modelHeader.getBatchId(), "BATCH_ID"));
		modelHeader.setJamTanggalMessage(formatWithG2Format(sb, modelHeader.getJamTanggalMessage(), "JAM_TANGGAL_MESSAGE"));
		modelHeader.setJumlahRecords(formatWithG2Format(sb, modelHeader.getJumlahRecords(), "JUMLAH_RECORDS"));
		modelHeader.setTotalNominal(formatWithG2Format(sb, formatNominal(modelHeader.getTotalNominal()), "TOTAL_NOMINAL"));
		modelHeader.setTanggalBatch(formatWithG2Format(sb, modelHeader.getTanggalBatch(), "TANGGAL_BATCH"));
		modelHeader.setJenisSettlement(formatWithG2Format(sb, modelHeader.getJenisSettlement(), "JENIS_SETTLEMENT"));
		modelHeader.setIdentitasPesertaPengirimPenerus(formatWithG2Format(sb, modelHeader.getIdentitasPesertaPengirimPenerus(), "IDENTITAS_PESERTA_PENGIRIM_PENERUS"));
		modelHeader.setSandiKotaAsal(formatWithG2Format(sb, modelHeader.getSandiKotaAsal(), "SANDI_KOTA_ASAL"));
		
		return sb.toString();
	}
	
    /**
     * 
     * @param modelDetail
     * @return
     */
    public String formatFromInOutDetail(SknNgInOutCreditDetail modelDetail) {
		StringBuilder sb = new StringBuilder();
		
		modelDetail.setTipeRecord(formatWithG2Format(sb, modelDetail.getTipeRecord(), "TIPE_RECORD"));
		modelDetail.setKodeDke(formatWithG2Format(sb, modelDetail.getKodeDke(), "KODE_DKE"));
		modelDetail.setIdentitasPesertaPengirimAsal(formatWithG2Format(sb, modelDetail.getIdentitasPesertaPengirimAsal(), "IDENTITAS_PESERTA_PENGIRIM_ASAL"));
		modelDetail.setSandiKotaAsal(formatWithG2Format(sb, modelDetail.getSandiKotaAsal(), "SANDI_KOTA_ASAL_DETAIL"));
		modelDetail.setSenderName(formatWithG2Format(sb, modelDetail.getSenderName(), "SENDER_NAME"));
		modelDetail.setSourceAccount(formatWithG2Format(sb, modelDetail.getSourceAccount(), "SOURCE_ACCOUNT"));
		modelDetail.setAlamatNasabahPengirim(formatWithG2Format(sb, modelDetail.getAlamatNasabahPengirim(), "ALAMAT_NASABAH_PENGIRIM"));
		modelDetail.setJenisNasabahPengirim(formatWithG2Format(sb, modelDetail.getJenisNasabahPengirim(), "JENIS_NASABAH_PENGIRIM"));
		modelDetail.setStatusKependudukanNasabahPengirim(formatWithG2Format(sb, modelDetail.getStatusKependudukanNasabahPengirim(), "STATUS_KEPENDUDUKAN_NASABAH_PENGIRIM"));
		modelDetail.setNomorIdentitasNasabahPengirim(formatWithG2Format(sb, modelDetail.getNomorIdentitasNasabahPengirim(), "NOMOR_IDENTITAS_NASABAH_PENGIRIM"));
		modelDetail.setIdentitasPesertaPenerimaPenerus(formatWithG2Format(sb, modelDetail.getIdentitasPesertaPenerimaPenerus(), "IDENTITAS_PESERTA_PENERIMA_PENERUS"));
		modelDetail.setIdentitasPesertaPenerimaAkhir(formatWithG2Format(sb, modelDetail.getIdentitasPesertaPenerimaAkhir(), "IDENTITAS_PESERTA_PENERIMA_AKHIR"));
		modelDetail.setSandiKotaTujuan(formatWithG2Format(sb, modelDetail.getSandiKotaTujuan(), "SANDI_KOTA_TUJUAN"));
		modelDetail.setNamaNasabahPenerima(formatWithG2Format(sb, modelDetail.getNamaNasabahPenerima(), "NAMA_NASABAH_PENERIMA"));
		modelDetail.setDestinationAccount(formatWithG2Format(sb, modelDetail.getDestinationAccount(), "DESTINATION_ACCOUNT"));
		modelDetail.setAlamatNasabahPenerima(formatWithG2Format(sb, modelDetail.getAlamatNasabahPenerima(), "ALAMAT_NASABAH_PENERIMA"));
		modelDetail.setJenisNasabahPenerima(formatWithG2Format(sb, modelDetail.getJenisNasabahPenerima(), "JENIS_NASABAH_PENERIMA"));
		modelDetail.setStatusKependudukanNasabahPenerima(formatWithG2Format(sb, modelDetail.getStatusKependudukanNasabahPenerima(), "STATUS_KEPENDUDUKAN_NASABAH_PENERIMA"));
		modelDetail.setNomorIdentitasNasabahPenerima(formatWithG2Format(sb, modelDetail.getNomorIdentitasNasabahPenerima(), "NOMOR_IDENTITAS_NASABAH_PENERIMA"));
		modelDetail.setJenisTransaksi(formatWithG2Format(sb, modelDetail.getJenisTransaksi(), "JENIS_TRANSAKSI"));
		modelDetail.setJenisSaranaTransaksi(formatWithG2Format(sb, modelDetail.getJenisSaranaTransaksi(), "JENIS_SARANA_TRANSAKSI"));
		modelDetail.setNominal(formatWithG2Format(sb, modelDetail.getNominal(), "NOMINAL"));
		modelDetail.setNomorUrut(formatWithG2Format(sb, modelDetail.getNomorUrut(), "NOMOR_URUT"));
		modelDetail.setNomorReferensi(formatWithG2Format(sb, modelDetail.getNomorReferensi(), "NOMOR_REFERENSI"));
		modelDetail.setNomorReferensiTransaksiAsal(formatWithG2Format(sb, modelDetail.getNomorReferensiTransaksiAsal(), "NOMOR_REFERENSI_TRANSAKSI_ASAL"));
		modelDetail.setBebanBiaya(formatWithG2Format(sb, modelDetail.getBebanBiaya(), "BEBAN_BIAYA"));
		modelDetail.setKeterangan(formatWithG2Format(sb, modelDetail.getKeterangan(), "KETERANGAN"));
		modelDetail.setSOR(formatWithG2Format(sb, modelDetail.getSOR(), "SOR"));
		modelDetail.setPeriodeKonfirmasi(formatWithG2Format(sb, modelDetail.getPeriodeKonfirmasi(), "PERIODE_KONFIRMASI"));
		
		return sb.toString();
	}
	
    /**
     * 
     * @param modelFooter
     * @param CRC
     * @return
     */
    public String formatFromInOutFooter(SknNgInOutCreditFooter modelFooter, int CRC) {
		StringBuilder sb = new StringBuilder();
		
		modelFooter.setTipeRecord(formatWithG2Format(sb, modelFooter.getTipeRecord(), "TIPE_RECORD"));
		modelFooter.setKodeDke(formatWithG2Format(sb, modelFooter.getKodeDke(), "KODE_DKE"));
		modelFooter.setCRC(formatWithG2Format(sb, String.valueOf(CRC), "CRC"));
		
		return sb.toString();
	}
	/* === [End] SknNg In Out === */
	
	
	/* === [Begin] SknNg WS Audit Trail === */
    /**
     * 
     * @param batchNo
     * @param filenameFixin
     * @param batchNoOriginal
     * @param filenameOriginal
     * @return
     */
    public SknNgWSAuditTrailBatch createWSAuditTrailBatch(String batchNo, String filenameFixin, String batchNoOriginal, String filenameOriginal) {
		SknNgWSAuditTrailBatch batch = new SknNgWSAuditTrailBatch(batchNo);
		batch.setType(SKNNG_OUTWARD_CREDIT_WS);
		batch.setFilenameFixin(filenameFixin);
		batch.setBatchNoOriginal(batchNoOriginal);
		batch.setFilenameOriginal(filenameOriginal);
		
		return batch;
	}
	
	
    /**
     * 
     * @param headerInOut
     * @param batchNo
     * @param sbResult
     * @return
     */
    public SknNgWSAuditTrailHeader formatToWSAuditTrailHeader(SknNgInOutCreditHeader headerInOut, String batchNo, StringBuilder sbResult) {
		SknNgWSAuditTrailHeader header = new SknNgWSAuditTrailHeader(batchNo, headerInOut.getCompositeId().getRecordId());
		
		formatWithG2Format(sbResult, headerInOut.getTipeRecord(), "TIPE_RECORD");
		formatWithG2Format(sbResult, headerInOut.getKodeDke(), "KODE_DKE");
		header.setBatchId(formatWithG2Format(sbResult, headerInOut.getBatchId(), "BATCH_ID"));
		header.setJamTanggalMessage(formatWithG2Format(sbResult, headerInOut.getJamTanggalMessage(), "JAM_TANGGAL_MESSAGE"));
		header.setJumlahRecords(formatWithG2Format(sbResult, headerInOut.getJumlahRecords(), "JUMLAH_RECORDS"));
		formatWithG2Format(sbResult, headerInOut.getTotalNominal(), "TOTAL_NOMINAL");
		header.setTanggalBatch(formatWithG2Format(sbResult, headerInOut.getTanggalBatch(), "TANGGAL_BATCH"));
		formatWithG2Format(sbResult, headerInOut.getJenisSettlement(), "JENIS_SETTLEMENT");
		header.setIdentitasPesertaPengirim(formatWithG2Format(sbResult, headerInOut.getIdentitasPesertaPengirimPenerus(), "IDENTITAS_PESERTA_PENGIRIM_PENERUS"));
		formatWithG2Format(sbResult, headerInOut.getSandiKotaAsal(), "SANDI_KOTA_ASAL");
		
		return header;
	}
	
    /**
     * 
     * @param headerInOut
     * @param detailInOut
     * @param batchNo
     * @param sbResult
     * @return
     */
    public SknNgWSAuditTrailDetail formatToWSAuditTrailDetail(SknNgInOutCreditHeader headerInOut, SknNgInOutCreditDetail detailInOut, String batchNo, StringBuilder sbResult) {
		SknNgWSAuditTrailDetail detail = new SknNgWSAuditTrailDetail(batchNo, detailInOut.getCompositeId().getRecordId());
		detail.setParentRecordId(headerInOut.getCompositeId().getRecordId());
		
		formatWithG2Format(sbResult, detailInOut.getTipeRecord(), "TIPE_RECORD");
		formatWithG2Format(sbResult, detailInOut.getKodeDke(), "KODE_DKE");
		formatWithG2Format(sbResult, detailInOut.getIdentitasPesertaPengirimAsal(), "IDENTITAS_PESERTA_PENGIRIM_ASAL");
		formatWithG2Format(sbResult, detailInOut.getSandiKotaAsal(), "SANDI_KOTA_ASAL");
		formatWithG2Format(sbResult, detailInOut.getSenderName(), "SENDER_NAME");
		formatWithG2Format(sbResult, detailInOut.getSourceAccount(), "SOURCE_ACCOUNT");
		formatWithG2Format(sbResult, detailInOut.getAlamatNasabahPengirim(), "ALAMAT_NASABAH_PENGIRIM");
		formatWithG2Format(sbResult, detailInOut.getJenisNasabahPengirim(), "JENIS_NASABAH_PENGIRIM");
		formatWithG2Format(sbResult, detailInOut.getStatusKependudukanNasabahPengirim(), "STATUS_KEPENDUDUKAN_NASABAH_PENGIRIM");
		formatWithG2Format(sbResult, detailInOut.getNomorIdentitasNasabahPengirim(), "NOMOR_IDENTITAS_NASABAH_PENGIRIM");
		formatWithG2Format(sbResult, detailInOut.getIdentitasPesertaPenerimaPenerus(), "IDENTITAS_PESERTA_PENERIMA_PENERUS");
		formatWithG2Format(sbResult, detailInOut.getIdentitasPesertaPenerimaAkhir(), "IDENTITAS_PESERTA_PENERIMA_AKHIR");
		formatWithG2Format(sbResult, detailInOut.getSandiKotaTujuan(), "SANDI_KOTA_TUJUAN");
		formatWithG2Format(sbResult, detailInOut.getNamaNasabahPenerima(), "NAMA_NASABAH_PENERIMA");
		formatWithG2Format(sbResult, detailInOut.getDestinationAccount(), "DESTINATION_ACCOUNT");
		formatWithG2Format(sbResult, detailInOut.getAlamatNasabahPenerima(), "ALAMAT_NASABAH_PENERIMA");
		formatWithG2Format(sbResult, detailInOut.getJenisNasabahPenerima(), "JENIS_NASABAH_PENERIMA");
		formatWithG2Format(sbResult, detailInOut.getStatusKependudukanNasabahPenerima(), "STATUS_KEPENDUDUKAN_NASABAH_PENERIMA");
		formatWithG2Format(sbResult, detailInOut.getNomorIdentitasNasabahPenerima(), "NOMOR_IDENTITAS_NASABAH_PENERIMA");
		formatWithG2Format(sbResult, detailInOut.getJenisTransaksi(), "JENIS_TRANSAKSI");
		formatWithG2Format(sbResult, detailInOut.getJenisSaranaTransaksi(), "JENIS_SARANA_TRANSAKSI");
		formatWithG2Format(sbResult, detailInOut.getNominal(), "NOMINAL");
		formatWithG2Format(sbResult, detailInOut.getNomorUrut(), "NOMOR_URUT");
		detail.setNomorReferensi(formatWithG2Format(sbResult, detailInOut.getNomorReferensi(), "NOMOR_REFERENSI"));
		formatWithG2Format(sbResult, detailInOut.getNomorReferensiTransaksiAsal(), "NOMOR_REFERENSI_TRANSAKSI_ASAL");
		formatWithG2Format(sbResult, detailInOut.getBebanBiaya(), "BEBAN_BIAYA");
		formatWithG2Format(sbResult, detailInOut.getKeterangan(), "KETERANGAN");
		detail.setSOR(formatWithG2Format(sbResult, detailInOut.getSOR(), "SOR"));
		formatWithG2Format(sbResult, detailInOut.getPeriodeKonfirmasi(), "PERIODE_KONFIRMASI");
		
		return detail;
	}
	
    /**
     * 
     * @param footerInOut
     * @param CRC
     * @param sbResult
     * @return
     */
    public String formatToCRCValue(SknNgInOutCreditFooter footerInOut, int CRC, StringBuilder sbResult) {
		StringBuilder sbTemp = new StringBuilder();
		
		formatWithG2Format(sbResult, footerInOut.getTipeRecord(), "TIPE_RECORD");
		formatWithG2Format(sbResult, footerInOut.getKodeDke(), "KODE_DKE");
		formatWithG2Format(sbTemp, Integer.valueOf(CRC), "CRC");
		
		if (sbResult != null)
			sbResult.append(sbTemp);
		
		return sbTemp.toString();
	}
	/* === [End] SknNg WS Audit Trail === */
	
	
    /**
     * 
     * @param batchNo
     * @param parentRecordId
     * @param data
     * @param listRecordId
     * @throws Exception
     */
    public void createProcessAndRun(String batchNo, Integer parentRecordId, String data, java.util.List<Integer> listRecordId) throws Exception {
		new SknNgWSProcessOutwardCredit(
				this,
				"SknNgWSProcess_OutwardCredit" + "_" +  parentRecordId,
				batchNo,
				data, 
				new java.util.ArrayList<Integer>(listRecordId)
		).start();
	}
	
	
	
    /**
     * 
     */
    protected class SknNgWSProcessOutwardCredit extends SknNgWSProcess {
		private Integer posHeaderJumlahRecords, lenHeaderJumlahRecords;
		private Integer posHeaderTotalNominal, lenHeaderTotalNominal;
		private Integer posDetailNominal, lenDetailNominal;
		private Integer posFooterCRC, lenFooterCRC;
		
		
        /**
         * 
         * @param parent
         * @param name
         * @param batchNo
         * @param data
         * @param arrRowNo
         * @throws Exception
         */
        public SknNgWSProcessOutwardCredit(SknNgService parent, String name, String batchNo, String data, java.util.List<Integer> arrRowNo) throws Exception {
			super(parent, name, batchNo, data, arrRowNo);
		}
		
		
        /**
         * 
         * @return
         */
        @Override
		public int getPositionHeaderJumlahRecords() {
			if (this.posHeaderJumlahRecords == null) {
				/* Find Total Records position */
				int ctrColumn[] = new int[1];
				ctrColumn[0] = posG2AfterCommon;
				skipWithG2Format("BATCH_ID", ctrColumn);
				skipWithG2Format("JAM_TANGGAL_MESSAGE", ctrColumn);
				
				this.posHeaderJumlahRecords = Integer.valueOf(ctrColumn[0]);
				this.lenHeaderJumlahRecords = Integer.valueOf(skipWithG2Format("JUMLAH_RECORDS", ctrColumn));
			}
			
			return this.posHeaderJumlahRecords.intValue();
		}

        /**
         * 
         * @return
         */
        @Override
		public int getLengthHeaderJumlahRecords() {
			if (this.lenHeaderJumlahRecords == null)
				this.getPositionHeaderJumlahRecords();
			
			return this.lenHeaderJumlahRecords.intValue();
		}
		
        /**
         * 
         * @param jumlahRecords
         * @return
         */
        @Override
		public String formatHeaderJumlahRecordsWithG2Format(int jumlahRecords) {
			return formatWithG2Format(null, Integer.valueOf(jumlahRecords), "JUMLAH_RECORDS");
		}


        /**
         * 
         * @return
         */
        @Override
		public int getPositionHeaderTotalNominal() {
			if (this.posHeaderTotalNominal == null) {
				/* Find Total Records position */
				int ctrColumn[] = new int[1];
				ctrColumn[0] = posG2AfterCommon;
				skipWithG2Format("BATCH_ID", ctrColumn);
				skipWithG2Format("JAM_TANGGAL_MESSAGE", ctrColumn);
				skipWithG2Format("JUMLAH_RECORDS", ctrColumn);
				
				this.posHeaderTotalNominal = Integer.valueOf(ctrColumn[0]);
				this.lenHeaderTotalNominal = Integer.valueOf(skipWithG2Format("TOTAL_NOMINAL", ctrColumn));
			}
			
			return this.posHeaderTotalNominal.intValue();
		}

        /**
         * 
         * @return
         */
        @Override
		public int getLengthHeaderTotalNominal() {
			if (this.lenHeaderTotalNominal == null)
				this.getPositionHeaderTotalNominal();
			
			return this.lenHeaderTotalNominal;
		}
		
        /**
         * 
         * @param totalNominal
         * @return
         */
        @Override
		public String formatHeaderTotalNominalWithG2Format(String totalNominal) {
			return formatWithG2Format(null, totalNominal, "TOTAL_NOMINAL");
		}
		
		
        /**
         * 
         * @return
         */
        @Override
		public int getPositionDetailNominal() {
			if (this.posDetailNominal == null) {
				/* Find nominal position */
				int ctrColumn[] = new int[1];
				ctrColumn[0] = posG2AfterCommon;
				skipWithG2Format("IDENTITAS_PESERTA_PENGIRIM_ASAL", ctrColumn);
				skipWithG2Format("SANDI_KOTA_ASAL", ctrColumn);
				skipWithG2Format("SENDER_NAME", ctrColumn);
				skipWithG2Format("SOURCE_ACCOUNT", ctrColumn);
				skipWithG2Format("ALAMAT_NASABAH_PENGIRIM", ctrColumn);
				skipWithG2Format("JENIS_NASABAH_PENGIRIM", ctrColumn);
				skipWithG2Format("STATUS_KEPENDUDUKAN_NASABAH_PENGIRIM", ctrColumn);
				skipWithG2Format("NOMOR_IDENTITAS_NASABAH_PENGIRIM", ctrColumn);
				skipWithG2Format("IDENTITAS_PESERTA_PENERIMA_PENERUS", ctrColumn);
				skipWithG2Format("IDENTITAS_PESERTA_PENERIMA_AKHIR", ctrColumn);
				skipWithG2Format("SANDI_KOTA_TUJUAN", ctrColumn);
				skipWithG2Format("NAMA_NASABAH_PENERIMA", ctrColumn);
				skipWithG2Format("DESTINATION_ACCOUNT", ctrColumn);
				skipWithG2Format("ALAMAT_NASABAH_PENERIMA", ctrColumn);
				skipWithG2Format("JENIS_NASABAH_PENERIMA", ctrColumn);
				skipWithG2Format("STATUS_KEPENDUDUKAN_NASABAH_PENERIMA", ctrColumn);
				skipWithG2Format("NOMOR_IDENTITAS_NASABAH_PENERIMA", ctrColumn);
				skipWithG2Format("JENIS_TRANSAKSI", ctrColumn);
				skipWithG2Format("JENIS_SARANA_TRANSAKSI", ctrColumn);
				
				this.posDetailNominal = Integer.valueOf(ctrColumn[0]);
				this.lenDetailNominal = Integer.valueOf(skipWithG2Format("NOMINAL", ctrColumn));
			}
			
			return this.posDetailNominal.intValue();	
		}

        /**
         * 
         * @return
         */
        @Override
		public int getLengthDetailNominal() {
			if (this.lenDetailNominal == null)
				this.getPositionDetailNominal();
			
			return this.lenDetailNominal.intValue();
		}
		

        /**
         * 
         * @return
         */
        @Override
		public int getPositionFooterCRC() {
			if (this.posFooterCRC == null) {
				/* Find CRC position */
				int[] ctrColumn = new int[1];
				ctrColumn[0] = posG2AfterCommon;
				this.posFooterCRC = Integer.valueOf(ctrColumn[0]);
				this.lenFooterCRC = Integer.valueOf(skipWithG2Format("CRC", ctrColumn));
			}
			
			return this.posFooterCRC.intValue();
		}

        /**
         * 
         * @return
         */
        @Override
		public int getLengthFooterCRC() {
			if (this.lenFooterCRC == null)
				this.getPositionFooterCRC();
			
			return this.lenFooterCRC;
		}
		
        /**
         * 
         * @param CRC
         * @return
         */
        @Override
		public String formatCRCWithG2Format(int CRC) {
			return formatWithG2Format(null, Integer.valueOf(CRC), "CRC");
		}
		
        /**
         * 
         * @return
         */
        @Override
		public String getWebServiceOperationName() {
			return "sendDKEKreditIndividual";
		}
	}
	
}