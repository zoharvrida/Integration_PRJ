package bdsm.service;


/**
 * @author v00019372
 */
public class SknNgReturDebitService extends SknNgService {
    /**
     * 
     */
    public static final String CODE_DKE = "3";
	
    /**
     * 
     */
    protected static String FORMAT_RETUR_IN_DEBIT_G2_FILENAME = "sknng_retur_in_debit.properties";
    /**
     * 
     */
    protected static int posG2AfterCommon;
    /**
     * 
     */
    /**
     * 
     */
    /**
     * 
     */
    protected static int lenG2Header, lenG2Detail, lenG2Footer;
    /**
     * 
     */
    protected static java.util.Map<String, Object[]> formatReturInDebitG2;
	
	private static org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(SknNgReturDebitService.class);
	
	
	static {
		int ctrColumn[] = {0};
		
		try {
			loadReturInDebitG2Format();
		}
		catch (Exception ex) {
			LOGGER.error(ex, ex);
		}
		
		
		/* common */
		skipWithG2Format("TIPE_RECORD", ctrColumn);
		skipWithG2Format("KODE_DKE", ctrColumn);
		posG2AfterCommon = ctrColumn[0];
		
		/* header */
		ctrColumn[0] = posG2AfterCommon;
		skipWithG2Format("BATCH_ID", ctrColumn);
		skipWithG2Format("JAM_TANGGAL_MESSAGE", ctrColumn);
		skipWithG2Format("JUMLAH_RECORDS", ctrColumn);
		skipWithG2Format("TOTAL_NOMINAL", ctrColumn);
		skipWithG2Format("TANGGAL_BATCH", ctrColumn);
		skipWithG2Format("JENIS_SETELMEN", ctrColumn);
		skipWithG2Format("IDENTITAS_PESERTA_PENGIRIM", ctrColumn);
		skipWithG2Format("SANDI_KOTA_PENGIRIM", ctrColumn);
		skipWithG2Format("BATCH_ID_ORI", ctrColumn);
		skipWithG2Format("IDENTITAS_PESERTA_PENGIRIM_ORI", ctrColumn);
		lenG2Header = ctrColumn[0];
		
		/* detail */
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
		skipWithG2Format("NOMINAL", ctrColumn);
		skipWithG2Format("ALASAN_PENOLAKAN", ctrColumn);
		skipWithG2Format("NO_URUT", ctrColumn);
		skipWithG2Format("NO_REFERENSI", ctrColumn);
		skipWithG2Format("NO_REFERENSI_ORIGINAL", ctrColumn);
		skipWithG2Format("BEBAN_BIAYA", ctrColumn);
		skipWithG2Format("SOR", ctrColumn);
		lenG2Detail = ctrColumn[0];
		
		/* footer */
		ctrColumn[0] = posG2AfterCommon;
		skipWithG2Format("CRC", ctrColumn);
		lenG2Footer = ctrColumn[0];
	}
	
	
	
    /**
     * 
     * @return
     */
    @Override
	public int getLenG2Header() {
		return lenG2Header;
	}
	
    /**
     * 
     * @return
     */
    @Override
	public int getLenG2Detail() {
		return lenG2Detail;
	}
	
    /**
     * 
     * @return
     */
    @Override
	public int getLenG2Footer() {
		return lenG2Footer;
	}
	
	
	
    /**
     * 
     * @param formatFilename
     */
    protected static void setFormatReturInDebitG2Filename(String formatFilename) {
		FORMAT_RETUR_IN_DEBIT_G2_FILENAME = formatFilename;
	}
	
    /**
     * 
     * @throws Exception
     */
    protected static synchronized void loadReturInDebitG2Format() throws Exception {
		if (formatReturInDebitG2 == null)
			formatReturInDebitG2 = loadFixLengthFormat(FORMAT_RETUR_IN_DEBIT_G2_FILENAME);
	}
	
    /**
     * 
     * @param sb
     * @param input
     * @param formatKey
     * @return
     */
    protected static String formatWithG2Format(StringBuilder sb, Object input, String formatKey) {
		return formatSKN(formatReturInDebitG2, sb, input, formatKey);
	}
	
    /**
     * 
     * @param input
     * @param formatKey
     * @param position
     * @return
     */
    protected static String parseWithG2Format(String input, String formatKey, int[] position) {
		return parseSKN(formatReturInDebitG2, input, formatKey, position);
	}
	
    /**
     * 
     * @param formatKey
     * @param position
     * @return
     */
    protected static int skipWithG2Format(String formatKey, int[] position) {
		return skipSKN(formatReturInDebitG2, formatKey, position);
	}
	
}
