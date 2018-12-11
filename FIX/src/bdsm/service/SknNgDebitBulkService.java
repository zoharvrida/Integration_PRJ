package bdsm.service;


/**
 * @author v00019372
 */
public abstract class SknNgDebitBulkService extends SknNgBulkService {
    /**
     * 
     */
    public static final String CODE_DKE = "6";
	
    /**
     * 
     */
    protected static String FORMAT_FILENAME = "sknng_in_out_debit_bulk.properties";
    /**
     * 
     */
    protected static int POS_G2_AFTER_COMMON;
    /**
     * 
     */
    /**
     * 
     */
    /**
     * 
     */
    /**
     * 
     */
    protected static int LENGTH_G2_HEADER, LENGTH_G2_DKE, LENGTH_G2_DETAIL, LENGTH_G2_FOOTER;
    /**
     * 
     */
    protected static java.util.Map<String, Object[]> formatDebitG2;
	
	private static org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(SknNgDebitBulkService.class);
	
	
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
		LENGTH_G2_HEADER = ctrColumn[0];
		
		/* DKE */
		ctrColumn[0] = POS_G2_AFTER_COMMON;
		skipWithG2Format("NOMOR_URUT_DKE", ctrColumn);
		skipWithG2Format("IDENTITAS_PESERTA_PENGIRIM_ASAL", ctrColumn);
		skipWithG2Format("SANDI_KOTA_ASAL", ctrColumn);
		skipWithG2Format("IDENTITAS_PESERTA_PENERIMA_PENERUS", ctrColumn);
		skipWithG2Format("IDENTITAS_PESERTA_PENERIMA_AKHIR", ctrColumn);
		skipWithG2Format("JUMLAH_RECORDS_RINCIAN", ctrColumn);
		skipWithG2Format("TOTAL_NOMINAL_RINCIAN", ctrColumn);
		skipWithG2Format("NAMA_NASABAH_PENAGIH", ctrColumn);
		skipWithG2Format("NOMOR_REKENING_NASABAH_PENAGIH", ctrColumn);
		skipWithG2Format("JENIS_USAHA_NASABAH_PENAGIH", ctrColumn);
		skipWithG2Format("JENIS_NASABAH_PENAGIH", ctrColumn);
		skipWithG2Format("STATUS_PENDUDUK_NASABAH_PENAGIH", ctrColumn);
		skipWithG2Format("NOMOR_REFERENSI_DKE", ctrColumn);
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
		LENGTH_G2_DETAIL = ctrColumn[0];
		
		/* footer */
		ctrColumn[0] = POS_G2_AFTER_COMMON;
		skipWithG2Format("CRC", ctrColumn);
		LENGTH_G2_FOOTER = ctrColumn[0];
	}
	
	
	
    /**
     * 
     * @return
     */
    @Override
	public int getLenG2Header() {
		return LENGTH_G2_DETAIL;
	}
	
    /**
     * 
     * @return
     */
    @Override
	public int getLenG2DKE() {
		return LENGTH_G2_DKE;
	}

    /**
     * 
     * @return
     */
    @Override
	public int getLenG2Detail() {
		return LENGTH_G2_DETAIL;
	}

    /**
     * 
     * @return
     */
    @Override
	public int getLenG2Footer() {
		return LENGTH_G2_FOOTER;
	}
	
	
	
    /**
     * 
     * @throws Exception
     */
    protected static synchronized void loadDebitG2Format() throws Exception {
		if (formatDebitG2 == null)
			formatDebitG2 = loadFixLengthFormat(FORMAT_FILENAME);
	}
	
    /**
     * 
     * @param sb
     * @param input
     * @param formatKey
     * @return
     */
    protected static String formatWithG2Format(StringBuilder sb, Object input, String formatKey) {
		return formatSKN(formatDebitG2, sb, input, formatKey);
	}
	
    /**
     * 
     * @param input
     * @param formatKey
     * @param position
     * @return
     */
    protected static String parseWithG2Format(String input, String formatKey, int[] position) {
		return parseSKN(formatDebitG2, input, formatKey, position);
	}
	
    /**
     * 
     * @param formatKey
     * @param position
     * @return
     */
    protected static int skipWithG2Format(String formatKey, int[] position) {
		return skipSKN(formatDebitG2, formatKey, position);
	}

}
