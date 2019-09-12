package bdsm.service;


/**
 * @author v00019372
 */
public abstract class SknNgDebitService extends SknNgService {
    /**
     * 
     */
    public static final String CODE_DKE = "2";
	
    /**
     * 
     */
    protected static String FORMAT_IN_OUT_DEBIT_G2_FILENAME = "sknng_in_out_debit.properties";
    /**
     * 
     */
    protected static String FORMAT_IN_DEBIT_G1_FILENAME = "skn_in_debit.properties";
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
    /**
     * 
     */
    protected static java.util.Map<String, Object[]> formatInDebitG1, formatInOutDebitG2;
	
	private static org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(SknNgDebitService.class); 
	
	
	static {
		int ctrColumn[] = {0};
		
		try {
			loadInOutDebitG2Format();
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
		lenG2Header = ctrColumn[0];
		
		/* detail */
		ctrColumn[0] = posG2AfterCommon;
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
		skipWithG2Format("NOMOR_REFERENSI", ctrColumn);
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
    protected static void setFormatInDebitG1Filename(String formatFilename) {
		FORMAT_IN_DEBIT_G1_FILENAME = formatFilename;
	}
	
    /**
     * 
     * @param formatFilename
     */
    protected static void setFormatInOutDebitG2Filename(String formatFilename) {
		FORMAT_IN_OUT_DEBIT_G2_FILENAME = formatFilename;
	}
	
    /**
     * 
     * @throws Exception
     */
    protected static synchronized void loadInDebitG1Format() throws Exception {
		if (formatInDebitG1 == null)
			formatInDebitG1 = loadFixLengthFormat(FORMAT_IN_DEBIT_G1_FILENAME);
	}
	
    /**
     * 
     * @throws Exception
     */
    protected static synchronized void loadInOutDebitG2Format() throws Exception {
		if (formatInOutDebitG2 == null)
			formatInOutDebitG2 = loadFixLengthFormat(FORMAT_IN_OUT_DEBIT_G2_FILENAME);
	}
	
    /**
     * 
     * @param sb
     * @param input
     * @param formatKey
     * @return
     */
    protected static String formatWithG2Format(StringBuilder sb, Object input, String formatKey) {
		return formatSKN(formatInOutDebitG2, sb, input, formatKey);
	}
	
    /**
     * 
     * @param input
     * @param formatKey
     * @param position
     * @return
     */
    protected static String parseWithG2Format(String input, String formatKey, int[] position) {
		return parseSKN(formatInOutDebitG2, input, formatKey, position);
	}
	
    /**
     * 
     * @param formatKey
     * @param position
     * @return
     */
    protected static int skipWithG2Format(String formatKey, int[] position) {
		return skipSKN(formatInOutDebitG2, formatKey, position);
	}
	
    /**
     * 
     * @param sb
     * @param input
     * @param formatKey
     * @return
     */
    protected static String formatWithG1Format(StringBuilder sb, Object input, String formatKey) {
		return formatSKN(formatInDebitG1, sb, input, formatKey);
	}
	
}
