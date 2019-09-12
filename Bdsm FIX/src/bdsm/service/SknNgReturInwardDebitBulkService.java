package bdsm.service;


import bdsm.model.SknNgWSAuditTrailBulkBatch;
import bdsm.model.SknNgWSAuditTrailBulkDKE;
import bdsm.model.SknNgWSAuditTrailBulkDetail;
import bdsm.model.SknNgWSAuditTrailBulkHeader;


/**
 * @author v00019372
 */
public class SknNgReturInwardDebitBulkService extends SknNgReturBulkService {
    /**
     * 
     */
    public static final String TYPE_IN = "O";
	

	/* === [Begin] SknNg WS Audit Trail === */
    /**
     * 
     * @param batchNo
     * @param filenameFixin
     * @param batchNoOriginal
     * @param filenameOriginal
     * @return
     */
    public SknNgWSAuditTrailBulkBatch createWSAuditTrailBatch(String batchNo, String filenameFixin, String batchNoOriginal, String filenameOriginal) {
		SknNgWSAuditTrailBulkBatch batch = new SknNgWSAuditTrailBulkBatch(batchNo);
		batch.setType(Byte.valueOf(CODE_DKE));
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
    public SknNgWSAuditTrailBulkHeader parseToWSAuditTrailHeader(String line, String batchNo, Integer recordId) {
		int ctrColumn[] = new int[] { POS_G2_AFTER_COMMON };
		
		SknNgWSAuditTrailBulkHeader header = new SknNgWSAuditTrailBulkHeader(batchNo, recordId);
		header.setBatchId(parseWithG2Format(line, "BATCH_ID", ctrColumn));
		header.setJamTanggalMessage(parseWithG2Format(line, "JAM_TANGGAL_MESSAGE", ctrColumn));
		header.setJumlahRecords(parseWithG2Format(line, "JUMLAH_RECORDS_DKE", ctrColumn));
		skipWithG2Format("TOTAL_NOMINAL_DKE", ctrColumn);
		header.setTanggalBatch(parseWithG2Format(line, "TANGGAL_BATCH", ctrColumn));
		skipWithG2Format("JENIS_SETELMEN", ctrColumn);
		header.setIdentitasPesertaPengirim(parseWithG2Format(line, "IDENTITAS_PESERTA_PENGIRIM", ctrColumn));
		skipWithG2Format("SANDI_KOTA_PENGIRIM", ctrColumn);
		header.setBatchIdOriginal(parseWithG2Format(line, "BATCH_ID_ORIGINAL", ctrColumn));
		header.setIdentitasPesertaPengirimOriginal(parseWithG2Format(line, "IDENTITAS_PESERTA_PENGIRIM_ORIGINAL", ctrColumn));
		
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
    public SknNgWSAuditTrailBulkDKE parseToWSAuditTrailDKE(String line, String batchNo, Integer recordId, SknNgWSAuditTrailBulkHeader header) {
		int ctrColumn[] = new int[] { POS_G2_AFTER_COMMON };
		
		SknNgWSAuditTrailBulkDKE dke = new SknNgWSAuditTrailBulkDKE(batchNo, recordId);
		dke.setParentRecordId(header.getCompositeId().getRecordId());
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
		
		dke.setNomorReferensiDKE(parseWithG2Format(line, "NOMOR_REFERENSI_DKE", ctrColumn));
		
		return dke;
	}
	
    /**
     * 
     * @param line
     * @param batchNo
     * @param recordId
     * @param dke
     * @return
     */
    public SknNgWSAuditTrailBulkDetail parseToWSAuditTrailDetail(String line, String batchNo, Integer recordId, SknNgWSAuditTrailBulkDKE dke) {
		int ctrColumn[] = new int[] { POS_G2_AFTER_COMMON };
		
		SknNgWSAuditTrailBulkDetail detail = new SknNgWSAuditTrailBulkDetail(batchNo, recordId);
		detail.setParentRecordId(dke.getCompositeId().getRecordId());
		detail.setNomorUrutTransaksi(parseWithG2Format(line, "NOMOR_URUT_TRANSAKSI", ctrColumn));
		
		return detail;
	}
	
    /**
     * 
     * @param line
     * @return
     */
    public String parseToTheCRCValue(String line) {
		int ctrColumn[] = new int[] { POS_G2_AFTER_COMMON };
		
		return parseWithG2Format(line, "CRC", ctrColumn);
	}
	
	/* === [End] SknNg WS Audit Trail === */
	
	
	
    /**
     * 
     * @param batchNo
     * @param parentRecordId
     * @param data
     * @throws Exception
     */
    public void createProcessAndRun(String batchNo, Integer parentRecordId, String data) throws Exception {
		new SknNgWSProcessReturInwardDebitBulk(
				this,
				"SknNgWSProcess_ReturInwardDebitBulk" + "_" +  parentRecordId,
				batchNo,
				parentRecordId,
				data 
		).start();
	}
	
	
	
    /**
     * 
     */
    protected class SknNgWSProcessReturInwardDebitBulk extends SknNgBulkWSProcess {
		private Integer posHeaderJumlahDKEs, lenHeaderJumlahDKEs;
		private Integer posHeaderTotalNominal, lenHeaderTotalNominal;
		private Integer posDKEJumlahRecords, lenDKEJumlahRecords;
		private Integer posDKETotalNominal, lenDKETotalNominal;
		private Integer posDetailNominal, lenDetailNominal;
		private Integer posFooterCRC, lenFooterCRC;
		

        /**
         * 
         * @param parent
         * @param name
         * @param batchNo
         * @param headerRecordId
         * @param data
         * @throws Exception
         */
        public SknNgWSProcessReturInwardDebitBulk(SknNgBulkService parent, String name, String batchNo, Integer headerRecordId, String data) 
				throws Exception {
			super(parent, name, batchNo, headerRecordId, data);
		}
		

		/* Header */
        /**
         * 
         * @return
         */
        @Override
		public int getPositionHeaderJumlahDKEs() {
			if (this.posHeaderJumlahDKEs == null) {
				/* Find Total Records position */
				int ctrColumn[] = new int[1];
				ctrColumn[0] = POS_G2_AFTER_COMMON;
				skipWithG2Format("BATCH_ID", ctrColumn);
				skipWithG2Format("JAM_TANGGAL_MESSAGE", ctrColumn);
				
				this.posHeaderJumlahDKEs = Integer.valueOf(ctrColumn[0]);
				this.lenHeaderJumlahDKEs = Integer.valueOf(skipWithG2Format("JUMLAH_RECORDS_DKE", ctrColumn));
			}
			
			return this.posHeaderJumlahDKEs.intValue();
		}

        /**
         * 
         * @return
         */
        @Override
		public int getLengthHeaderJumlahDKEs() {
			if (this.lenHeaderJumlahDKEs == null)
				this.getPositionHeaderJumlahDKEs();
			
			return this.lenHeaderJumlahDKEs;
		}

        /**
         * 
         * @param jumlahRecords
         * @return
         */
        @Override
		public String formatHeaderJumlahDKEsWithG2Format(int jumlahRecords) {
			return formatWithG2Format(null, Integer.valueOf(jumlahRecords), "JUMLAH_RECORDS_DKE");
		}

        /**
         * 
         * @return
         */
        @Override
		public int getPositionHeaderTotalNominal() {
			if (this.posHeaderTotalNominal == null) {
				this.getPositionHeaderJumlahDKEs();
				
				this.posHeaderTotalNominal = this.posHeaderJumlahDKEs + this.lenHeaderJumlahDKEs;
				int ctrColumn[] = new int[] {this.posHeaderTotalNominal};
				this.lenHeaderTotalNominal = Integer.valueOf(skipWithG2Format("TOTAL_NOMINAL_DKE", ctrColumn));
			}
			
			return this.posHeaderTotalNominal;
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
			return formatWithG2Format(null, totalNominal, "TOTAL_NOMINAL_DKE");
		}


		/* DKE */
        /**
         * 
         * @return
         */
        @Override
		public int getPositionDKEJumlahRecords() {
			if (this.posDKEJumlahRecords == null) {
				/* Find Total Records position */
				int ctrColumn[] = new int[1];
				ctrColumn[0] = POS_G2_AFTER_COMMON;
				skipWithG2Format("NOMOR_URUT_DKE", ctrColumn);
				skipWithG2Format("NOMOR_REFERENSI_DKE", ctrColumn);
				skipWithG2Format("IDENTITAS_PESERTA_PENGIRIM_ASAL_ORIGINAL", ctrColumn);
				skipWithG2Format("SANDI_KOTA_ASAL_ORIGINAL", ctrColumn);
				skipWithG2Format("IDENTITAS_PESERTA_PENERIMA_PENERUS_ORIGINAL", ctrColumn);
				skipWithG2Format("IDENTITAS_PESERTA_PENERIMA_AKHIR_ORIGINAL", ctrColumn);
				
				this.posDKEJumlahRecords = Integer.valueOf(ctrColumn[0]);
				this.lenDKEJumlahRecords = Integer.valueOf(skipWithG2Format("JUMLAH_RECORDS_RINCIAN", ctrColumn));
			}
			
			return this.posDKEJumlahRecords;
		}

        /**
         * 
         * @return
         */
        @Override
		public int getLengthDKEJumlahRecords() {
			if (this.lenDKEJumlahRecords == null)
				this.getPositionDKEJumlahRecords();
			
			return this.lenDKEJumlahRecords;
		}

        /**
         * 
         * @param jumlahRecords
         * @return
         */
        @Override
		public String formatDKEJumlahRecordsWithG2Format(int jumlahRecords) {
			return formatWithG2Format(null, Integer.valueOf(jumlahRecords), "JUMLAH_RECORDS_RINCIAN");
		}

        /**
         * 
         * @return
         */
        @Override
		public int getPositionDKETotalNominal() {
			if (this.posDKETotalNominal == null) {
				this.getPositionDKEJumlahRecords();
				
				this.posDKETotalNominal = this.posDKEJumlahRecords + this.lenDKEJumlahRecords;
				int ctrColumn[] = new int[] {this.posDKETotalNominal};
				this.lenDKETotalNominal = Integer.valueOf(skipWithG2Format("TOTAL_NOMINAL_DKE", ctrColumn));
			}
			
			return this.posDKETotalNominal;
		}

        /**
         * 
         * @return
         */
        @Override
		public int getLengthDKETotalNominal() {
			if (this.lenDKETotalNominal == null)
				this.getPositionDKETotalNominal();
			
			return this.lenDKETotalNominal;
		}

        /**
         * 
         * @param totalNominal
         * @return
         */
        @Override
		public String formatDKETotalNominalWithG2Format(String totalNominal) {
			return formatWithG2Format(null, totalNominal, "TOTAL_NOMINAL_RINCIAN");
		}


		/* Detail */
        /**
         * 
         * @return
         */
        @Override
		public int getPositionDetailNominal() {
			if (this.posDetailNominal == null) {
				/* Find nominal position */
				int ctrColumn[] = new int[1];
				ctrColumn[0] = POS_G2_AFTER_COMMON;
				skipWithG2Format("NOMOR_URUT_TRANSAKSI", ctrColumn);
				skipWithG2Format("NAMA_NASABAH_TERTAGIH", ctrColumn);
				skipWithG2Format("NOMOR_REKENING_NASABAH_TERTAGIH", ctrColumn);
				skipWithG2Format("JENIS_NASABAH_TERTAGIH", ctrColumn);
				skipWithG2Format("STATUS_PENDUDUK_NASABAH_TERTAGIH", ctrColumn);
				skipWithG2Format("NOMOR_ID_PELANGGAN", ctrColumn);
				skipWithG2Format("NOMOR_REFERENSI", ctrColumn);
				
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


		/* Footer */
        /**
         * 
         * @return
         */
        @Override
		public int getPositionFooterCRC() {
			if (this.posFooterCRC == null) {
				/* Find CRC position */
				int[] ctrColumn = new int[1];
				ctrColumn[0] = POS_G2_AFTER_COMMON;
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
			return "sendDKEPengembalianBulk";
		}
		
	}
	
}
