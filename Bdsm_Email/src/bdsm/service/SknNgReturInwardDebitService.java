package bdsm.service;


import bdsm.model.SknNgReturInDebitDetail;
import bdsm.model.SknNgReturInDebitFooter;
import bdsm.model.SknNgReturInDebitHeader;
import bdsm.model.SknNgWSAuditTrailBatch;
import bdsm.model.SknNgWSAuditTrailDetail;
import bdsm.model.SknNgWSAuditTrailHeader;


/**
 * @author v00019372
 */
public class SknNgReturInwardDebitService extends SknNgReturDebitService {
	public static final Byte SKNNG_RETUR_INWARD_DEBIT_WS = Byte.valueOf((byte) 3);
	

	/* === [Begin] SknNg Retur In === */
	public String formatFromReturInHeader(SknNgReturInDebitHeader modelHeader) {
		StringBuilder sb = new StringBuilder();
		
		modelHeader.setTipeRecord(formatWithG2Format(sb, modelHeader.getTipeRecord(), "TIPE_RECORD"));
		modelHeader.setKodeDke(formatWithG2Format(sb, modelHeader.getKodeDke(), "KODE_DKE"));
		modelHeader.setBatchId(formatWithG2Format(sb, modelHeader.getBatchId(), "BATCH_ID"));
		modelHeader.setJamTanggalMessage(formatWithG2Format(sb, modelHeader.getJamTanggalMessage(), "JAM_TANGGAL_MESSAGE"));
		modelHeader.setJumlahRecords(formatWithG2Format(sb, modelHeader.getJumlahRecords(), "JUMLAH_RECORDS"));
		modelHeader.setTotalNominal(formatWithG2Format(sb, formatNominal(modelHeader.getTotalNominal()), "TOTAL_NOMINAL"));
		modelHeader.setTanggalBatch(formatWithG2Format(sb, modelHeader.getTanggalBatch(), "TANGGAL_BATCH"));
		modelHeader.setJenisSetelmen(formatWithG2Format(sb, modelHeader.getJenisSetelmen(), "JENIS_SETELMEN"));
		modelHeader.setIdentitasPesertaPengirim(formatWithG2Format(sb, modelHeader.getIdentitasPesertaPengirim(), "IDENTITAS_PESERTA_PENGIRIM"));
		modelHeader.setSandiKotaPengirim(formatWithG2Format(sb, modelHeader.getSandiKotaPengirim(), "SANDI_KOTA_PENGIRIM"));
		modelHeader.setBatchIdOriginal(formatWithG2Format(sb, modelHeader.getBatchIdOriginal(), "BATCH_ID_ORI"));
		modelHeader.setIdentitasPesertaPengirimOriginal(formatWithG2Format(sb, modelHeader.getIdentitasPesertaPengirimOriginal(), 
				"IDENTITAS_PESERTA_PENGIRIM_ORI"));
		
		return sb.toString();
	}
	
	public String formatFromReturInDetail(SknNgReturInDebitDetail modelDetail) {
		StringBuilder sb = new StringBuilder();
		
		modelDetail.setTipeRecord(formatWithG2Format(sb, modelDetail.getTipeRecord(), "TIPE_RECORD"));
		modelDetail.setKodeDke(formatWithG2Format(sb, modelDetail.getKodeDke(), "KODE_DKE"));
		modelDetail.setIdentitasPesertaPenerima(formatWithG2Format(sb, modelDetail.getIdentitasPesertaPenerima(), 
				"IDENTITAS_PESERTA_PENERIMA"));
		modelDetail.setSandiKotaAsal(formatWithG2Format(sb, modelDetail.getSandiKotaAsal(), "SANDI_KOTA_ASAL"));
		modelDetail.setNamaNasabahPemegang(formatWithG2Format(sb, modelDetail.getNamaNasabahPemegang(), "NAMA_NASABAH_PEMEGANG"));
		modelDetail.setNoRekeningNasabahPemegang(formatWithG2Format(sb, modelDetail.getNoRekeningNasabahPemegang(), 
				"NO_REKENING_NASABAH_PEMEGANG"));
		modelDetail.setNoIdentitasPemegang(formatWithG2Format(sb, modelDetail.getNoIdentitasPemegang(), "NO_IDENTITAS_PEMEGANG"));
		modelDetail.setNamaPenarik(formatWithG2Format(sb, modelDetail.getNamaPenarik(), "NAMA_PENARIK"));
		modelDetail.setNamaNasabahTertarik(formatWithG2Format(sb, modelDetail.getNamaNasabahTertarik(), "NAMA_NASABAH_TERTARIK"));
		modelDetail.setNoRekeningNasabahTertarik(formatWithG2Format(sb, modelDetail.getNoRekeningNasabahTertarik(), 
				"NO_REKENING_NASABAH_TERTARIK"));
		modelDetail.setAlamatNasabahTertarik(formatWithG2Format(sb, modelDetail.getAlamatNasabahTertarik(), "ALAMAT_NASABAH_TERTARIK"));
		modelDetail.setRT(formatWithG2Format(sb, modelDetail.getRT(), "RT"));
		modelDetail.setRW(formatWithG2Format(sb, modelDetail.getRW(), "RW"));
		modelDetail.setKota(formatWithG2Format(sb, modelDetail.getKota(), "KOTA"));
		modelDetail.setPropinsi(formatWithG2Format(sb, modelDetail.getPropinsi(), "PROPINSI"));
		modelDetail.setKodePos(formatWithG2Format(sb, modelDetail.getKodePos(), "KODE_POS"));
		modelDetail.setTanggalLahir(formatWithG2Format(sb, modelDetail.getTanggalLahir(), "TANGGAL_LAHIR"));
		modelDetail.setTempatLahir(formatWithG2Format(sb, modelDetail.getTempatLahir(), "TEMPAT_LAHIR"));
		modelDetail.setSandiNegaraTempatLahir(formatWithG2Format(sb, modelDetail.getSandiNegaraTempatLahir(), "SANDI_NEGARA_TEMPAT_LAHIR"));
		modelDetail.setJenisNasabahPenerima(formatWithG2Format(sb, modelDetail.getJenisNasabahPenerima(), "JENIS_NASABAH_PENERIMA"));
		modelDetail.setStatusPendudukNasabahPenerima(formatWithG2Format(sb, modelDetail.getStatusPendudukNasabahPenerima(), 
				"STATUS_PENDUDUK_NASABAH_PENERIMA"));
		modelDetail.setNoIdentitas(formatWithG2Format(sb, modelDetail.getNoIdentitas(), "NO_IDENTITAS"));
		modelDetail.setNPWP(formatWithG2Format(sb, modelDetail.getNPWP(), "NPWP"));
		modelDetail.setJenisTransaksi(formatWithG2Format(sb, modelDetail.getJenisTransaksi(), "JENIS_TRANSAKSI"));
		modelDetail.setNoWarkat(formatWithG2Format(sb, modelDetail.getNoWarkat(), "NO_WARKAT"));
		modelDetail.setNominal(formatWithG2Format(sb, formatNominal(modelDetail.getNominal()), "NOMINAL"));
		modelDetail.setAlasanPenolakan(formatWithG2Format(sb, modelDetail.getAlasanPenolakan(), "ALASAN_PENOLAKAN"));
		modelDetail.setNoUrut(formatWithG2Format(sb, modelDetail.getNoUrut(), "NO_URUT"));
		modelDetail.setNoReferensi(formatWithG2Format(sb, modelDetail.getNoReferensi(), "NO_REFERENSI"));
		modelDetail.setNoReferensiOriginal(formatWithG2Format(sb, modelDetail.getNoReferensiOriginal(), "NO_REFERENSI_ORIGINAL"));
		modelDetail.setBebanBiaya(formatWithG2Format(sb, modelDetail.getBebanBiaya(), "BEBAN_BIAYA"));
		modelDetail.setSOR(formatWithG2Format(sb, modelDetail.getSOR(), "SOR"));
		
		return sb.toString();
	}
	
	public String formatFromReturInFooter(SknNgReturInDebitFooter modelFooter, int CRC) {
		StringBuilder sb = new StringBuilder();
		
		modelFooter.setTipeRecord(formatWithG2Format(sb, modelFooter.getTipeRecord(), "TIPE_RECORD"));
		modelFooter.setKodeDke(formatWithG2Format(sb, modelFooter.getKodeDke(), "KODE_DKE"));
		modelFooter.setCRC(formatWithG2Format(sb, String.valueOf(CRC), "CRC"));
		
		return sb.toString();
	}
	/* === [ End ] SknNg Retur In === */
	
	
	
	/* === [Begin] SknNg WS Audit Trail === */
	public SknNgWSAuditTrailBatch createWSAuditTrailBatch(String batchNo, String filenameFixin, String batchNoOriginal, String filenameOriginal) {
		SknNgWSAuditTrailBatch batch = new SknNgWSAuditTrailBatch(batchNo);
		batch.setType(SKNNG_RETUR_INWARD_DEBIT_WS);
		batch.setFilenameFixin(filenameFixin);
		batch.setBatchNoOriginal(batchNoOriginal);
		batch.setFilenameOriginal(filenameOriginal);
		
		return batch;
	}
	
	
	public SknNgWSAuditTrailHeader parseToWSAuditTrailHeader(String line, String batchNo, Integer recordId) {
		int ctrColumn[] = new int[] { posG2AfterCommon };
		
		SknNgWSAuditTrailHeader header = new SknNgWSAuditTrailHeader(batchNo, recordId);
		header.setBatchId(parseWithG2Format(line, "BATCH_ID", ctrColumn));
		header.setJamTanggalMessage(parseWithG2Format(line, "JAM_TANGGAL_MESSAGE", ctrColumn));
		header.setJumlahRecords(parseWithG2Format(line, "JUMLAH_RECORDS", ctrColumn));
		skipWithG2Format("TOTAL_NOMINAL", ctrColumn);
		header.setTanggalBatch(parseWithG2Format(line, "TANGGAL_BATCH", ctrColumn));
		skipWithG2Format("JENIS_SETELMEN", ctrColumn);
		header.setIdentitasPesertaPengirim(parseWithG2Format(line, "IDENTITAS_PESERTA_PENGIRIM", ctrColumn));
		
		return header;
	}
	
	public SknNgWSAuditTrailDetail parseToWSAuditTrailDetail(String line, String batchNo, Integer recordId, SknNgWSAuditTrailHeader header) {
		int ctrColumn[] = new int[] { posG2AfterCommon };
		
		SknNgWSAuditTrailDetail detail = new SknNgWSAuditTrailDetail(batchNo, recordId);
		detail.setParentRecordId(header.getCompositeId().getRecordId());
		skipWithG2Format("IDENTITAS_PESERTA_PENERIMA", ctrColumn);
		skipWithG2Format("SANDI_KOTA_ASAL", ctrColumn);
		skipWithG2Format("NAMA_NASABAH_PEMEGANG", ctrColumn);
		skipWithG2Format("NO_REKENING_NASABAH_PEMEGANG", ctrColumn);
		skipWithG2Format("NO_IDENTITAS_PEMEGANG", ctrColumn);
		skipWithG2Format("NAMA_PENARIK", ctrColumn);
		skipWithG2Format("NAMA_NASABAH_TERTARIK", ctrColumn);
		skipWithG2Format("NO_REKENING_NASABAH_TERTARIK", ctrColumn);
		skipWithG2Format("ALAMAT_NASABAH_TERTARIK", ctrColumn);
		skipWithG2Format("RT", ctrColumn);
		skipWithG2Format("RW", ctrColumn);
		skipWithG2Format("KOTA", ctrColumn);
		skipWithG2Format("PROPINSI", ctrColumn);
		skipWithG2Format("KODE_POS", ctrColumn);
		skipWithG2Format("TANGGAL_LAHIR", ctrColumn);
		skipWithG2Format("TEMPAT_LAHIR", ctrColumn);
		skipWithG2Format("SANDI_NEGARA_TEMPAT_LAHIR", ctrColumn);
		skipWithG2Format("JENIS_NASABAH_PENERIMA", ctrColumn);
		skipWithG2Format("STATUS_PENDUDUK_NASABAH_PENERIMA", ctrColumn);
		skipWithG2Format("NO_IDENTITAS", ctrColumn);
		skipWithG2Format("NPWP", ctrColumn);
		skipWithG2Format("JENIS_TRANSAKSI", ctrColumn);
		skipWithG2Format("NO_WARKAT", ctrColumn);
		skipWithG2Format("NOMINAL", ctrColumn);
		skipWithG2Format("ALASAN_PENOLAKAN", ctrColumn);
		skipWithG2Format("NO_URUT", ctrColumn);
		detail.setNomorReferensi(parseWithG2Format(line, "NO_REFERENSI", ctrColumn));
		skipWithG2Format("NO_REFERENSI_ORIGINAL", ctrColumn);
		skipWithG2Format("BEBAN_BIAYA", ctrColumn);
		detail.setSOR(parseWithG2Format(line, "SOR", ctrColumn));
		
		return detail;
	}
	
	public String parseToTheCRCValue(String line) {
		int ctrColumn[] = new int[] { posG2AfterCommon };
		
		return parseWithG2Format(line, "CRC", ctrColumn);
	}
	/* === [End] SknNg WS Audit Trail === */
	
	
	public void createProcessAndRun(String batchNo, Integer parentRecordId, String data, java.util.List<Integer> listRecordId) throws Exception {
		new SknNgWSProcessReturInwardDebit(
				this,
				"SknNgWSProcess_ReturInwardDebit" + "_" +  parentRecordId,
				batchNo,
				data, 
				new java.util.ArrayList<Integer>(listRecordId)
		).start();
	}
	
	
	
	
	
	
	
	protected class SknNgWSProcessReturInwardDebit extends SknNgWSProcess {
		private Integer posHeaderJumlahRecords, lenHeaderJumlahRecords;
		private Integer posHeaderTotalNominal, lenHeaderTotalNominal;
		private Integer posDetailNominal, lenDetailNominal;
		private Integer posFooterCRC, lenFooterCRC;
		
		
		public SknNgWSProcessReturInwardDebit(SknNgService parent, String name, String batchNo, String data, java.util.List<Integer> listRowNo) throws Exception {
			super(parent, name, batchNo, data, listRowNo);
		}
		
		
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

		@Override
		public int getLengthHeaderJumlahRecords() {
			if (this.lenHeaderJumlahRecords == null)
				this.getPositionHeaderJumlahRecords();
			
			return this.lenHeaderJumlahRecords.intValue();
		}
		
		@Override
		public String formatHeaderJumlahRecordsWithG2Format(int jumlahRecords) {
			return formatWithG2Format(null, Integer.valueOf(jumlahRecords), "JUMLAH_RECORDS");
		}


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

		@Override
		public int getLengthHeaderTotalNominal() {
			if (this.lenHeaderTotalNominal == null)
				this.getPositionHeaderTotalNominal();
			
			return this.lenHeaderTotalNominal;
		}
		
		@Override
		public String formatHeaderTotalNominalWithG2Format(String totalNominal) {
			return formatWithG2Format(null, totalNominal, "TOTAL_NOMINAL");
		}
		
		
		@Override
		public int getPositionDetailNominal() {
			if (this.posDetailNominal == null) {
				/* Find nominal position */
				int ctrColumn[] = new int[1];
				ctrColumn[0] = posG2AfterCommon;
				skipWithG2Format("IDENTITAS_PESERTA_PENERIMA", ctrColumn);
				skipWithG2Format("SANDI_KOTA_ASAL", ctrColumn);
				skipWithG2Format("NAMA_NASABAH_PEMEGANG", ctrColumn);
				skipWithG2Format("NO_REKENING_NASABAH_PEMEGANG", ctrColumn);
				skipWithG2Format("NO_IDENTITAS_PEMEGANG", ctrColumn);
				skipWithG2Format("NAMA_PENARIK", ctrColumn);
				skipWithG2Format("NAMA_NASABAH_TERTARIK", ctrColumn);
				skipWithG2Format("NO_REKENING_NASABAH_TERTARIK", ctrColumn);
				skipWithG2Format("ALAMAT_NASABAH_TERTARIK", ctrColumn);
				skipWithG2Format("RT", ctrColumn);
				skipWithG2Format("RW", ctrColumn);
				skipWithG2Format("KOTA", ctrColumn);
				skipWithG2Format("PROPINSI", ctrColumn);
				skipWithG2Format("KODE_POS", ctrColumn);
				skipWithG2Format("TANGGAL_LAHIR", ctrColumn);
				skipWithG2Format("TEMPAT_LAHIR", ctrColumn);
				skipWithG2Format("SANDI_NEGARA_TEMPAT_LAHIR", ctrColumn);
				skipWithG2Format("JENIS_NASABAH_PENERIMA", ctrColumn);
				skipWithG2Format("STATUS_PENDUDUK_NASABAH_PENERIMA", ctrColumn);
				skipWithG2Format("NO_IDENTITAS", ctrColumn);
				skipWithG2Format("NPWP", ctrColumn);
				skipWithG2Format("JENIS_TRANSAKSI", ctrColumn);
				skipWithG2Format("NO_WARKAT", ctrColumn);
				
				this.posDetailNominal = Integer.valueOf(ctrColumn[0]);
				this.lenDetailNominal = Integer.valueOf(skipWithG2Format("NOMINAL", ctrColumn));
			}
			
			return this.posDetailNominal.intValue();	
		}

		@Override
		public int getLengthDetailNominal() {
			if (this.lenDetailNominal == null)
				this.getPositionDetailNominal();
			
			return this.lenDetailNominal.intValue();
		}
		

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

		@Override
		public int getLengthFooterCRC() {
			if (this.lenFooterCRC == null)
				this.getPositionFooterCRC();
			
			return this.lenFooterCRC;
		}
		
		@Override
		public String formatCRCWithG2Format(int CRC) {
			return formatWithG2Format(null, Integer.valueOf(CRC), "CRC");
		}
		
		@Override
		public String getWebServiceOperationName() {
			return "sendDKEPengembalianIndividual";
		}
	}
	
}
