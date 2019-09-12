package bdsm.service;


import bdsm.model.SknNgInOutDebitDetail;
import bdsm.model.SknNgInOutDebitFooter;
import bdsm.model.SknNgInOutDebitHeader;
import bdsm.model.SknNgWSAuditTrailBatch;
import bdsm.model.SknNgWSAuditTrailDetail;
import bdsm.model.SknNgWSAuditTrailHeader;


/**
 * @author v00019372
 */
public class SknNgOutwardDebitService extends SknNgDebitService {
    /**
     * 
     */
    public static final String TYPE_OUT = "O";
    /**
     * 
     */
    public static final Byte SKNNG_OUTWARD_DEBIT_WS = Byte.valueOf((byte) 2);

	
	/* === [Begin] SknNg In Out === */
    /**
     * 
     * @param modelHeader
     * @return
     */
    public String formatFromInOutHeader(SknNgInOutDebitHeader modelHeader) {
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
		
		return sb.toString();
	}
	
    /**
     * 
     * @param modelDetail
     * @return
     */
    public String formatFromInOutDetail(SknNgInOutDebitDetail modelDetail) {
		StringBuilder sb = new StringBuilder();
		
		modelDetail.setTipeRecord(formatWithG2Format(sb, modelDetail.getTipeRecord(), "TIPE_RECORD"));
		modelDetail.setKodeDke(formatWithG2Format(sb, modelDetail.getKodeDke(), "KODE_DKE"));
		modelDetail.setIdentitasPesertaPenerima(formatWithG2Format(sb, modelDetail.getIdentitasPesertaPenerima(), "IDENTITAS_PESERTA_PENERIMA"));
		modelDetail.setSandiKotaPenerbit(formatWithG2Format(sb, modelDetail.getSandiKotaPenerbit(), "SANDI_KOTA_PENERBIT"));
		modelDetail.setNamaNasabahPemegang(formatWithG2Format(sb, modelDetail.getNamaNasabahPemegang(), "NAMA_NASABAH_PEMEGANG"));
		modelDetail.setDestCreditAccount(formatWithG2Format(sb, modelDetail.getDestCreditAccount(), "DEST_CREDIT_ACCOUNT"));
		modelDetail.setNomorIdentitasPemegang(formatWithG2Format(sb, modelDetail.getNomorIdentitasPemegang(), "NOMOR_IDENTITAS_PEMEGANG"));
		modelDetail.setDebitAccount(formatWithG2Format(sb, modelDetail.getDebitAccount(), "DEBIT_ACCOUNT"));
		modelDetail.setJenisTransaksi(formatWithG2Format(sb, modelDetail.getJenisTransaksi(), "JENIS_TRANSAKSI"));
		modelDetail.setNomorWarkat(formatWithG2Format(sb, modelDetail.getNomorWarkat(), "NOMOR_WARKAT"));
		modelDetail.setNominal(formatWithG2Format(sb, formatNominal(modelDetail.getNominal()), "NOMINAL"));
		modelDetail.setNomorUrut(formatWithG2Format(sb, modelDetail.getNomorUrut(), "NOMOR_URUT"));
		modelDetail.setNomorReferensi(formatWithG2Format(sb, modelDetail.getNomorReferensi(), "NOMOR_REFERENSI"));
		modelDetail.setBebanBiaya(formatWithG2Format(sb, modelDetail.getBebanBiaya(), "BEBAN_BIAYA"));
		modelDetail.setSor(formatWithG2Format(sb, modelDetail.getSor(), "SOR"));
		
		return sb.toString();
	}
	
    /**
     * 
     * @param modelFooter
     * @param CRC
     * @return
     */
    public String formatFromInOutFooter(SknNgInOutDebitFooter modelFooter, int CRC) {
		StringBuilder sb = new StringBuilder();
		
		modelFooter.setTipeRecord(formatWithG2Format(sb, modelFooter.getTipeRecord(), "TIPE_RECORD"));
		modelFooter.setKodeDke(formatWithG2Format(sb, modelFooter.getKodeDke(), "KODE_DKE"));
		modelFooter.setCrc(formatWithG2Format(sb, String.valueOf(CRC), "CRC"));
		
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
		batch.setType(SKNNG_OUTWARD_DEBIT_WS);
		batch.setFilenameFixin(filenameFixin);
		batch.setBatchNoOriginal(batchNoOriginal);
		batch.setFilenameOriginal(filenameOriginal);
		
		return batch;
	}
	
	
    /**
     * 
     * @param line
     * @param batchNo
     * @param recordId
     * @return
     */
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
	
    /**
     * 
     * @param line
     * @param batchNo
     * @param recordId
     * @param header
     * @return
     */
    public SknNgWSAuditTrailDetail parseToWSAuditTrailDetail(String line, String batchNo, Integer recordId, SknNgWSAuditTrailHeader header) {
		int ctrColumn[] = new int[] { posG2AfterCommon };
		
		SknNgWSAuditTrailDetail detail = new SknNgWSAuditTrailDetail(batchNo, recordId);
		detail.setParentRecordId(header.getCompositeId().getRecordId());
		skipWithG2Format("IDENTITAS_PESERTA_PENERIMA", ctrColumn);
		skipWithG2Format("SANDI_KOTA_PENERBIT", ctrColumn);
		skipWithG2Format("NAMA_NASABAH_PEMEGANG", ctrColumn);
		skipWithG2Format("DEST_CREDIT_ACCOUNT", ctrColumn);
		skipWithG2Format("NOMOR_IDENTITAS_PEMEGANG", ctrColumn);
		skipWithG2Format("DEBIT_ACCOUNT", ctrColumn);
		skipWithG2Format("JENIS_TRANSAKSI", ctrColumn);
		skipWithG2Format("NOMOR_WARKAT", ctrColumn);
		skipWithG2Format("NOMINAL", ctrColumn);
		skipWithG2Format("NOMOR_URUT", ctrColumn);
		detail.setNomorReferensi(parseWithG2Format(line, "NOMOR_REFERENSI", ctrColumn));
		skipWithG2Format("BEBAN_BIAYA", ctrColumn);
		detail.setSOR(parseWithG2Format(line, "SOR", ctrColumn));
		
		return detail;
	}
	
    /**
     * 
     * @param line
     * @return
     */
    public String parseToTheCRCValue(String line) {
		int ctrColumn[] = new int[] { posG2AfterCommon };
		
		return parseWithG2Format(line, "CRC", ctrColumn);
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
		new SknNgWSProcessOutwardDebit(
				this,
				"SknNgWSProcess_OutwardDebit" + "_" +  parentRecordId,
				batchNo,
				data, 
				new java.util.ArrayList<Integer>(listRecordId)
		).start();
	}
	
	
	
	
	
	
    /**
     * 
     */
    protected class SknNgWSProcessOutwardDebit extends SknNgWSProcess {
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
         * @param listRowNo
         * @throws Exception
         */
        public SknNgWSProcessOutwardDebit(SknNgService parent, String name, String batchNo, String data, java.util.List<Integer> listRowNo) throws Exception {
			super(parent, name, batchNo, data, listRowNo);
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
				skipWithG2Format("IDENTITAS_PESERTA_PENERIMA", ctrColumn);
				skipWithG2Format("SANDI_KOTA_PENERBIT", ctrColumn);
				skipWithG2Format("NAMA_NASABAH_PEMEGANG", ctrColumn);
				skipWithG2Format("DEST_CREDIT_ACCOUNT", ctrColumn);
				skipWithG2Format("NOMOR_IDENTITAS_PEMEGANG", ctrColumn);
				skipWithG2Format("DEBIT_ACCOUNT", ctrColumn);
				skipWithG2Format("JENIS_TRANSAKSI", ctrColumn);
				skipWithG2Format("NOMOR_WARKAT", ctrColumn);
				
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
			return "sendDKEPenyerahanIndividual";
		}
	}
	
}
