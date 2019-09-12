package bdsm.util;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author bdsm
 */
public class StatusDefinition {

    private static final String[] OK_WORDS = {"ok"};
    private static final String[] NOT_OK_WORDS = {"not ok","notok"};
    public static final String PROCESS = "P";
    public static final String DONE = "D";
    public static final String REQUEST = "Q";
    public static final String ERROR = "E";
    public static final String REJECT = "R";
    public static final String IN = "I";
    public static final String XTRACT = "X";
    public static final String UNAUTHORIZED = "U";
    public static final String AUTHORIZED = "A";
    public static final String REJECTED = "R";
    public static final String RESPONSE = "S";
    public static final String IGNORED = "G";
    public static final String TRUE = "T";
    public static final String FALSE = "F";
    public static final String CHARACTERONLY = "^[a-z A-Z ].*";
    public static final String ALPHANUMERIC = "^[a-z A-Z 0-9].*";
    public static final String NUMERICONLY = "^[0-9].*";
    public static final String PHONENUMBER = "^[0-9\\-\\+].*";
    public static final String EMAILONLY = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    public static final String patternJoin="yyMMddHHmmss";
	public static final String Patternyyyymmdd = "yyyy-MM-dd";
	public static final String patternddMMyyyy = "dd/MM/yyyy";
	public static final String patterndd_MM_yyyy = "dd-MM-yyyy";
    public static final String PatternTimestamp = "dd/MM/yyyy HH24:mm:ss";
    public static final String convPatternTime = "yyyy-MM-dd HH:mm:ss";
    public static final String jasperTimetable = "MM/dd/yyyy hh:mm:ss a";
    public static final String patternDay = "EEEE";
    public static final String Skndate = "yyMMdd";
	public static final String sknDategen1 = "ddMMyyyy";
        public static final String AmortTxnDate = "yyyyMMdd";
    public static final String SknTanggalMessage = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    public static final String weekday = "Sunday,Monday,Tuesday,Wednesday,Thursday,Friday,Saturday";
    public static final String dateCalPattern = "d";
    public static final String monthCalPattern = "M";
    public static final String yearCalPattern = "yyyy";
    public static final String ActiveDay = "1";
    public static final String idMappingReport = "9";
    public static final int LOGSCHEDULER = 3;
    public static final int EMAILSCHEDULER = 3;
    public static final int SCREENSCHEDULER = 3;
    public static final int DDFSCHEDULER = 43;
    public static final String SUCCESS = "SUCCESS";
    public static final String ERR = "ERROR";
    public static final String Headers = "0";
    public static final String Details = "1";
	    public static final String Footers = "9";
        public static final String dateBirthConvert= "ddMMyyyy";

    public static final String YES = "Y";
    public static final String NO = "N";
    public static final Integer Hour = 3600;
    public static final Integer Days = 3600*24;
    public static final Integer Kabisat = 366 * 24 * 60 * 60;
    public static final Integer NoKabisat = 366 * 24 * 60 * 60;
    public static final Integer deviasi = 40;
    public static final Integer maxDay = 6;
	public static final String multiEXT = "MULTI";
    // Parameter for PPF Loan
    public static final String PPFLoanDate = "yyyyMMdd";
    public static final String PPFKPRDate = "dd/MM/yyyy";
    public static final String InterestWaiveflag = "N";
    public static final String PenaltyWaiveflag = "N";
    public static final String PMIwaiveFlag = "N";
    public static final String accountFlag = "2";
    public static final String reschmode = "T";
    public static final String InterestDFlag = "N";
    public static final String refuser = "1";
	// Parameter Amort Hold
	public static final String AmortEarmark = "15";
	public static final String AmortEarmarkReason = "6";
    public static final String AmortNegateAcctStat = "1,5";

    // Parameter for SMS
    public static final String delimiter= ";";
    public static final String Runs = "R";
    public static final String idle = "I";
    public static final String Populate = "POPULATE";
    public static final String Direct = "STRAIGHT"; 

    //Parameter Amount Hold
    public static final String AmtHldHeader = "0";
    public static final String AmtHldDetail = "1";
    public static final String AmtHldFooter = "9";

	// parameter for general Persister
    public static final String formatString = "STRING";
    public static final String formatInteger = "INTEGER";
    public static final String formatMap = "MAP";
	
    // Fieldparameter spesific IdentityNumber
    public static final Integer customSMSParam = 16;
    public static final Integer routeforParameter = 19;
    // Purging
    public static final String datePurging = "DP";
    private static Integer quotaSync;
    public static String getOkWords(String words) {
        for (int i=0;i<OK_WORDS.length;i++){
            if (words.equalsIgnoreCase(OK_WORDS[i]))
                return words;
        }
        return "";
    }

    public static String getNotOkWords(String words) {
        for (int i=0;i<NOT_OK_WORDS.length;i++){
            if (words.equalsIgnoreCase(NOT_OK_WORDS[i]))
                return words;
        }        
        return "";
    }

    /**
     * @return the quotaSync
     */
    public static Integer getQuotaSync() {
        return quotaSync;
    }

    /**
     * @param aQuotaSync the quotaSync to set
     */
    public static synchronized void setQuotaSync(Integer aQuotaSync) {
        quotaSync = aQuotaSync;
    }
}
