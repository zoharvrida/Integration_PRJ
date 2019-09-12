package bdsm.service;


/**
 * @author v00019372
 */
public abstract class SknNgCreditService extends SknNgService {
    /**
     * 
     */
    public static final String CODE_DKE = "1";
	
    /**
     * 
     */
    protected static String FORMAT_IN_OUT_CREDIT_G2_FILENAME = "sknng_in_out_credit.properties";
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
    protected static java.util.Map<String, Object[]> formatInOutCreditG2;
	
	private static org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(SknNgCreditService.class);
	
	
	static {
		int ctrColumn[] = {0};
		
		try {
			loadInOutCreditG2Format();
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
		skipWithG2Format("JENIS_SETTLEMENT", ctrColumn);
		skipWithG2Format("IDENTITAS_PESERTA_PENGIRIM_PENERUS", ctrColumn);
		skipWithG2Format("SANDI_KOTA_ASAL", ctrColumn);
		lenG2Header = ctrColumn[0];
		
		/* detail */
		ctrColumn[0] = posG2AfterCommon;
		skipWithG2Format("IDENTITAS_PESERTA_PENGIRIM_ASAL", ctrColumn);
		skipWithG2Format("SANDI_KOTA_ASAL_DETAIL", ctrColumn);
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
		skipWithG2Format("NOMINAL", ctrColumn);
		skipWithG2Format("NOMOR_URUT", ctrColumn);
		skipWithG2Format("NOMOR_REFERENSI", ctrColumn);
		skipWithG2Format("NOMOR_REFERENSI_TRANSAKSI_ASAL", ctrColumn);
		skipWithG2Format("BEBAN_BIAYA", ctrColumn);
		skipWithG2Format("KETERANGAN", ctrColumn);
		skipWithG2Format("SOR", ctrColumn);
		skipWithG2Format("PERIODE_KONFIRMASI", ctrColumn);
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
    protected static void setFormatInOutCreditG2Filename(String formatFilename) {
		FORMAT_IN_OUT_CREDIT_G2_FILENAME = formatFilename;
	}
	
    /**
     * 
     * @throws Exception
     */
    protected static synchronized void loadInOutCreditG2Format() throws Exception {
		if (formatInOutCreditG2 == null)
			formatInOutCreditG2 = loadFixLengthFormat(FORMAT_IN_OUT_CREDIT_G2_FILENAME);
	}
	
	
    /**
     * 
     * @param sb
     * @param input
     * @param formatKey
     * @return
     */
    protected static String formatWithG2Format(StringBuilder sb, Object input, String formatKey) {
		return formatSKN(formatInOutCreditG2, sb, input, formatKey);
	}
	
    /**
     * 
     * @param input
     * @param formatKey
     * @param position
     * @return
     */
    protected static String parseWithG2Format(String input, String formatKey, int[] position) {
		return parseSKN(formatInOutCreditG2, input, formatKey, position);
	}
	
    /**
     * 
     * @param formatKey
     * @param position
     * @return
     */
    protected static int skipWithG2Format(String formatKey, int[] position) {
		return skipSKN(formatInOutCreditG2, formatKey, position);
	}

}
