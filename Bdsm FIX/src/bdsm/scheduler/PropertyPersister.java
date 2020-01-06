package bdsm.scheduler;


import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.crypto.Cipher;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import bdsm.model.Parameter;
import bdsm.util.BdsmUtil;
import bdsm.util.EncryptionUtil;
import bdsm.util.HibernateUtil;
import bdsm.util.oracle.DateUtility;
import bdsmhost.dao.ParameterDao;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * 
 * @author bdsm
 */
public final class PropertyPersister {
	private static Logger LOGGER = Logger.getLogger(PropertyPersister.class);
	
    /**
     * 
     */
    public static String url;
    /**
     * 
     */
    public static String username;// FIX.mailAddr
    /**
     * 
     */
    public static int mailSleep;// FIX.mailSleep
    /**
     * 
     */
    public static int mailTimeout; // FIX.mailTimeout
    /**
     * 
     */
    public static int fileSleep;// FIX.fileSleep
    /**
     * 
     */
    public static int fileTimeout; // FIX.fileTimeout
    /**
     * 
     */
    public static int fixInPeriod; // FIX.fixInPeriod
    /**
     * 
     */
    public static int fixResPeriod; // FIX.fixResPeriod
    /**
     * 
     */
    public static String password;// FIX.mailPwd
    /**
     * 
     */
    public static String dirFixHome; // FIX.dirHome
    /**
     * 
     */
    public static String dirFixIn;// FIX.dirIn
    /**
     * 
     */
    public static String dirResp; // FIX.dirResp
    /**
     * 
     */
    public static String dirFixInProc;// FIX.dirInProc
    /**
     * 
     */
    public static String dirFixInOk;// FIX.dirInOk
    /**
     * 
     */
    public static String dirFixInErr;// FIX.dirInErr
    /**
     * 
     */
    public static String dirFixOut;// FIX.dirOut
    /**
     * 
     */
    public static String dirFixOutOk;// FIX.dirOutOk
    /**
     * 
     */
    public static String dirFixOutErr;// FIX.dirOutErr
    /**
     * 
     */
    public static String dirFixInTemp; // FIX.dirINTemp
    /**
     * 
     */
    public static String adminEmail;// FIX.adminemail
    /**
     * 
     */
    public static int maxProcess;// FIX.maxProcess
    /**
     * 
     */
    public static String dirLogFix;// FIX.dirLog
    /**
     * 
     */
    public static String dirLogApp;// FIX.dirLog
    /**
     * 
     */
    public static String dirLogWeb;// FIX.dirLog
    /**
     * 
     */
    public static String reportDirTemp;
    /**
     * 
     */
    public static String BDSM_URL_WEB_MODULE; // BDSM.URL.WEB_MODULE
    /**
     * 
     */
    public static String CMD_PERL; // CMD.PERL
    /**
     * 
     */
    public static String CMD_SHELL; // CMD.SHELL
    /**
     * 
     */
    public static int EOD_BOD_SchedulerXtractID;// EOD_BOD.SX_ID
    /**
     * 
     */
    public static Map<String, String> binDummy = new java.util.HashMap<String, String>();// VA.BIN_DUMMY
    /**
     * 
     */
    public static int codEntityVPD;// FCR.COD_ENTITY_VPD
    /**
     * 
     */
    public static final Integer fieldparamnumber = 19;
	// Email Body
    /**
     * 
     */
    public static String emailApproval;
    /**
     * 
     */
    public static String emailDone;
    /**
     * 
     */
    public static String emailRejected;

	// DDF Definition
    /**
     * 
     */
    public static String codXf;
    /**
     * 
     */
    public static String branchCode;
    /**
     * 
     */
    public static String fileType;
    /**
     * 
     */
    public static String pattern;
    /**
     * 
     */
    public static String fileName;
    /**
     * 
     */
    public static String makerID;
    /**
     * 
     */
    public static String days;

    /**
     * 
     */
    public static final String tagTujuanRek = "TXT_747";
    /**
     * 
     */
    public static final String tagSumberDanaRek = "TXT_748";
    /**
     * 
     */
    public static final String tagEmailStatement = "TXT_857";
    /**
     * 
     */
    public static final String tagTXNPerbulan = "TXT_757";

	// DDF Default Properties
    /**
     * 
     */
    public static final String DEFAULT_PASS = "Y";
    /**
     * 
     */
    public static final String DEFAULT_OWNER = "SOW";
    /**
     * 
     */
    public static final String DEFAULT_SUMBERDANA = "1";
    /**
     * 
     */
    public static final String DEFAULT_TRX = "01";
    /**
     * 
     */
    public static final String DEFAULT_REPLACEMENT = "null";
    /**
     * 
     */
    public static final String PERSONALPAJAK = "A";
    /**
     * 
     */
    public static final String PERSONALNONPAJAK = "B";

	// Amort Parameter
    /**
     * 
     */
    public static String AMORTTXNPARAM;
    /**
     * 
     */
    public static Integer AMORT_EOM_MAX_PER_BATCH;

	// SKN param
    /**
     * 
     */
    public static Integer FILESIZEMAX;
    /**
     * 
     */
    public static String SKNNG_FIXIN_FOLDER;
    /**
     * 
     */
    public static Integer SKN_CENTER_BRANCH_CODE_CONVENT;
    /**
     * 
     */
    public static Integer SKN_CENTER_BRANCH_CODE_SYARIAH;
    /**
     * 
     */
    public static Map<String, String> SKNNG_SPK_WS_CONVENT; 
    /**
     * 
     */
    public static Map<String, String> SKNNG_SPK_WS_SYARIAH;
    /**
     * 
     */
    public static boolean SKNNG_SPK_WS_IS_ENCRYPT;
    /**
     * 
     */
    public static Map<String, String> SKNNG_SPK_WS_ENCRYPTION;
    /**
     * 
     */
    public static Integer SKNNG_SPK_WS_DELAY_BETWEEN_THREAD;
    /**
     * 
     */
    public static String SKNNG_SPK_WS_TESTING_ENVIRONMENT;
    /**
     * 
     */
    public static Date SKNNG_SPK_WS_TESTING_DATE;
    /**
     * 
     */
    public static String CITYCODE;

	// SMS param
    /**
     * 
     */
    public static Integer FILEFETCH;
    /**
     * 
     */
    public static String ParamString;

	// KTP param
    /**
     * 
     */
    public static Integer KTPFETCH;
    /**
     * 
     */
    public static String KTPHeaders;
    /**
     * 
     */
    public static String KTPparamString;
    /**
     * 
     */
    public static String KTPFooters;
    /**
     * 
     */
    public static Integer KKFETCH;
    /**
     * 
     */
    public static String KKparamString;
    /**
     * 
     */
    public static String KKHeaders;
    /**
     * 
     */
    public static String EKTP_READER_BASE_RESOURCE_URI; // EKTP.READER.BASE_RESOURCE_URI
    /**
     * 
     */
    public static Map<String, String> EKTP_READER_BASE_PROTOCOLS; // EKTP.READER.BASE_PROTOCOLS
    /**
     * 
     */
    public static Integer KTPMAX;

	// MBM param
    /**
     * 
     */
    public static int quotaMax; // kuota mbm MVACCT
    /**
     * 
     */
    public static String schedulerDay;

	// AML param
    /**
     * 
     */
    public static String paramCifAml;
	// Digital Form param
    /**
     * 
     */
    public static String DFPattern;

	// SMS PHASE3 PARAM
    /**
     * 
     */
    public static String SMSNSF;
    /**
     * 
     */
    public static String SMSDormant;
    /**
     * 
     */
    public static String SMSAutoDebet;
    /**
     * 
     */
    public static String SMSIncoming;
    /**
     * 
     */
    public static String SMSSPS;
    /**
     * 
     */
    public static String SMSDHN;
    /**
     * 
     */
    public static String SMSTDARO;
    /**
     * 
     */
    public static String SMSReturn;
	//public static String SMSACOP;

	// General Properties
    /**
     * 
     */
    public static String generalParam;
    /**
     * 
     */
    public static String realNameParam;
    /**
     * 
     */
    public static Integer realIntegerParam;
    /**
     * 
     */
    public static Map realMapParam;

	// Casa Opening
    /**
     * 
     */
    public static Map<String, String> casaMap = new java.util.HashMap<String, String>();// SAVING account
    /**
     * 
     */
    public static Map<String, String> casaDefMap = new java.util.HashMap<String, String>();// SAVING account Default Value CIF
    /**
     * 
     */
    public static Map<String, String> casaDefAcctMap = new java.util.HashMap<String, String>();// SAVING account Default Value Acct
    /**
     * 
     */
    public static Map<String, String> casaMapDefKTP = new java.util.HashMap<String, String>();
    /**
     * 
     */
    public static Map<String, String> casaMapDefIB = new java.util.HashMap<String, String>();
    /**
     * 
     */
    public static Map<String, String> ektpStreaming = new java.util.HashMap<String, String>();
    /**
     * 
     */
    public static Map<String, String> ekkStreaming = new java.util.HashMap<String, String>();
    /**
     * 
     */
    public static String singleScreenprodExclude;
    /**
     * 
     */
    public static String taxAmnesty;

	// BIG BILL
    /**
     * 
     */
    public static String BIGBILL_PERL; // BIGBILL.PERL
    /**
     * 
     */
    public static Integer BIGBILL_MAX_TO_MIDDLEWARE; // BIGBILL.MaxToMiddleware
    /**
     * 
     */
    public static Integer BIGBILL_RETRY_TO_MIDDLEWARE; // BIGBILL.RetryToMiddleware
    /**
     * 
     */
    public static Integer BIGBILL_MAX_RECORDS_REGISTRATION; // BIGBILL.MaxRecordsRegistration
    /**
     * 
     */
    public static Integer BIGBILL_MAX_RECORDS_PAYMENT; // BIGBILL.MaxRecordsPayment

	// WIC
    /**
     * 
     */
    public static String WIC_TXN_MNEMONIC; // WIC.TXN_MNEMONIC

	// LLD
    /**
     * 
     */
    public static int lldAmount;//LLD_LIMIT_AMOUNT

    /**
     * 
     */
    public static int maxDataUPDCIF;
    /**
     * 
     */
    public static Integer maxRetry;
    /**
     * 
     */
    public static String RESTRICTION_HOUR;
    /**
     * 
     */
    public static String LLDDummy;
    /**
     * 
     */
    public static String LLDLainlain;
	
	// SISKOHAT
    /**
     * 
     */
    public static Map<String, String> sKHTAge;

     //Timeout
    /**
     * 
     */
    public static Integer timeoutHttp;
    
    public static String cashGlAccountNo;
    
    public static String glAccountNo;
    
    public static String userId;
    
    public static String codAcctNo;
    
    public static Map<String, String> ccyCode = new java.util.HashMap<String, String>();
    
	static {
		System.setProperty("javax.xml.parsers.SAXParserFactory",
				"com.sun.org.apache.xerces.internal.jaxp.SAXParserFactoryImpl");
		
		// Load data
		PropertyPersister.refreshParameter(null);

		emailApproval = "Dear Sir/Madam,<br/>"
				+ "<br/>"
				+ "Your approval is required for the attached file to be processed further. <br/>"
				+ "<br/>" + "Please reply this email with:<br/>"
				+ "<b>Ok</b>, if you approve the file to be processed, or<br/>"
				+ "<b>Not ok</b>, if otherwise.<br/>" + "<br/>"
				+ "Thanks & regards,<br/>" + "- BDSM -";
		emailDone = "Dear Sir/Madam,<br/>" + "<br/>"
				+ "Process Upload LBU CODE Done. <br/>" + "<br/>"
				+ "Thanks & regards,<br/>" + "- BDSM -";
		emailRejected = "Dear Sir/Madam,<br/>"
				+ "<br/>"
				+ "Your requested process to upload LBU CODE has been Rejected by Supervisor. <br/>"
				+ "<br/>" + "Thanks & regards,<br/>" + "- BDSM -";

	}

    /**
     * 
     * @param currentSession
     * @param idScheduler
     * @param typfix
     * @return
     */
    public static List<Map<Object,Map<String,Object>>> getRealParam(Session currentSession, Integer idScheduler, String typfix) {
		ParameterDao parameterDao = new ParameterDao(currentSession);
		List<Parameter> list = parameterDao.getList();
		List aliasing = parameterDao.getFieldParameter(idScheduler, typfix, StatusDefinition.routeforParameter);
		List <Map<Object,Map<String,Object>>> mapResult = new ArrayList<Map<Object, Map<String, Object>>>();
		
		Map tempMap = (Map) aliasing.get(0);
		LOGGER.info(tempMap);
		
		generalParam = tempMap.get("fieldName").toString();
		String format = tempMap.get("formatData").toString();
		
		if(StatusDefinition.formatString.equalsIgnoreCase(format)){
			realNameParam = getValue(generalParam, list).toString();
			Map stringMap = new HashMap();
			Map realParam = new HashMap();
			realParam.put("1", realNameParam);
			stringMap.put(StatusDefinition.formatString, realParam);
			mapResult.add(stringMap);
		} else if(StatusDefinition.formatInteger.equalsIgnoreCase(format)){
			realIntegerParam = Integer.parseInt(getValue(generalParam, list).toString());
			Map stringMap = new HashMap();
			Map realParam = new HashMap();
			realParam.put("1", realIntegerParam);
			stringMap.put(StatusDefinition.formatInteger,realParam);
			mapResult.add(stringMap);
		} else if(StatusDefinition.formatMap.equalsIgnoreCase(format)){
			realMapParam = parseKeyAndValueToMap(getValue(generalParam, list).toString());
			Map stringMap = new HashMap();
			stringMap.put(StatusDefinition.formatMap, realMapParam);
			mapResult.add(stringMap);
		}
		LOGGER.debug(mapResult);
		return mapResult;
	}


    /**
     * 
     * @param currentSession
     */
    public static void refreshParameter(Session currentSession) {
		Session session = (currentSession == null)? HibernateUtil.getSession(): currentSession;
		
		ParameterDao parameterDao = new ParameterDao(session) {
			@SuppressWarnings({ "unchecked", "unused" })
			public List<String> getRefreshClasses() {
				org.hibernate.Query query = this.getSession().createSQLQuery("SELECT class_name FROM bdsm_refresh_class");
				return ((List<String>) query.list());
			}
		};
		
		
		List<Parameter> list = parameterDao.getList();

		url = getValue("FIX.url", list).toString();
		username = getValue("FIX.mailAddr", list).toString();
		try {
			password = EncryptionUtil.getAES(
							getValue("FIX.mailPwd", list).toString(),
							(getValue("FIX.mailAddr", list).toString() + "@@@@@@@@@@@@@@@@").substring(0, 16), 
							Cipher.DECRYPT_MODE
						);
		}
		catch (Exception ex) {
			LOGGER.error("Error Decrypt Email Password : " + ex, ex);
		}
		LOGGER.debug("url : " + url);
		LOGGER.debug("username : " + username);
		LOGGER.debug("password : " + password);
		
		adminEmail = getValue("FIX.adminemail", list).toString();
		maxProcess = Integer.parseInt(getValue("FIX.maxProcess", list).toString());
		mailSleep = Integer.parseInt(getValue("FIX.mailSleep", list).toString());
		mailTimeout = Integer.parseInt(getValue("FIX.mailTimeout", list).toString());
		fileSleep = Integer.parseInt(getValue("FIX.fileSleep", list).toString());
		fileTimeout = Integer.parseInt(getValue("FIX.fileTimeout", list).toString());
		dirFixHome = getValue("FIX.dirHome", list).toString();
		dirResp = getValue("FIX.dirResp", list).toString();
		dirFixIn = getValue("FIX.dirIn", list).toString();
		dirFixInProc = getValue("FIX.dirInProc", list).toString();
		dirFixInOk = getValue("FIX.dirInOk", list).toString();
		dirFixInErr = getValue("FIX.dirInErr", list).toString();
		dirFixOut = getValue("FIX.dirOut", list).toString();
		dirFixOutErr = getValue("FIX.dirOutErr", list).toString();
		dirFixOutOk = getValue("FIX.dirOutOk", list).toString();
		reportDirTemp = getValue("FIX.reportDirTemp", list).toString();
		dirLogFix = getValue("FIX.dirLogFix", list).toString();
		dirLogWeb = getValue("FIX.dirLogWeb", list).toString();
		dirLogApp = getValue("FIX.dirLogApp", list).toString();
		BDSM_URL_WEB_MODULE = getValue("BDSM.URL.WEB_MODULE", list).toString();
		CMD_PERL = getValue("CMD.PERL", list).toString();
		CMD_SHELL = getValue("CMD.SHELL", list).toString();
		EOD_BOD_SchedulerXtractID = Integer.parseInt(getValue("EOD_BOD.SX_ID", list).toString());
		binDummy = parseKeyAndValueToMap(getValue("VA.BIN_DUMMY", list).toString());
		codEntityVPD = (Integer) getValue("FCR.COD_ENTITY_VPD", list);
		codXf = getValue("DDF.codXf", list).toString();
		branchCode = getValue("DDF.branchCode", list).toString();
		fileType = getValue("DDF.fileType", list).toString();
		pattern = getValue("DDF.pattern", list).toString();
		fileName = getValue("DDF.fileName", list).toString();
		makerID = getValue("DDF.makerID", list).toString();
		days = getValue("DDF.Days", list).toString();
		quotaMax = (Integer)getValue("MBM.QuotaMax", list);
		schedulerDay = getValue("MBM.SchedulerDay", list).toString();
		AMORTTXNPARAM = getValue("AMORT.TXN.Param", list).toString();
		AMORT_EOM_MAX_PER_BATCH = (Integer) getValue("AMORT.EOM.MAX/BATCH", list);
		dirFixInTemp = getValue("FIX.dirINTemp", list).toString();
		FILESIZEMAX = Integer.parseInt(getValue("SKN.FileSize.Max", list).toString());
		SKNNG_FIXIN_FOLDER = getValue("SKNNG.FIXIN_FOLDER", list).toString();
		maxDataUPDCIF = (Integer)getValue("UPDCIF.MaxData", list);

		try {
			SKN_CENTER_BRANCH_CODE_CONVENT = Integer.valueOf(getValue("SKN.CENTER_BRANCH_CODE_CONVENT", list).toString()); 
			SKN_CENTER_BRANCH_CODE_SYARIAH = Integer.valueOf(getValue("SKN.CENTER_BRANCH_CODE_SYARIAH", list).toString());
			SKNNG_SPK_WS_CONVENT = parseKeyAndValueToMap(getValue("SKNNG.SPK_WS_CONVENT", list).toString());
			SKNNG_SPK_WS_CONVENT.put("auth", EncryptionUtil.getAES(SKNNG_SPK_WS_CONVENT.get("auth"), BdsmUtil.rightPad("CONVENT", 16, '@'), Cipher.DECRYPT_MODE));
			SKNNG_SPK_WS_SYARIAH = parseKeyAndValueToMap(getValue("SKNNG.SPK_WS_SYARIAH", list).toString());
			SKNNG_SPK_WS_SYARIAH.put("auth", EncryptionUtil.getAES(SKNNG_SPK_WS_SYARIAH.get("auth"), BdsmUtil.rightPad("SYARIAH", 16, '@'), Cipher.DECRYPT_MODE));
			SKNNG_SPK_WS_ENCRYPTION = parseKeyAndValueToMap(getValue("SKNNG.SPK_WS_ENC", list).toString());
			SKNNG_SPK_WS_ENCRYPTION.put("key", EncryptionUtil.getAES(SKNNG_SPK_WS_ENCRYPTION.get("key"), BdsmUtil.rightPad(SKNNG_SPK_WS_ENCRYPTION.get("algo"), 16, '@'), Cipher.DECRYPT_MODE));
			SKNNG_SPK_WS_DELAY_BETWEEN_THREAD = (Integer) getValue("SKNNG.SPK_WS_DELAY_BETWEEN_THREAD", list); // in milliseconds
			SKNNG_SPK_WS_TESTING_ENVIRONMENT = (String) getValue("SKNNG.SPK_WS_TESTING_ENVIRONMENT", list);
			
			synchronized(DateUtility.DATE_FORMAT_YYYYMMDD) {
				SKNNG_SPK_WS_TESTING_DATE = StringUtils.isNotBlank((String) getValue("SKNNG.SPK_WS_TESTING_DATE", list))? 
						DateUtility.DATE_FORMAT_YYYYMMDD.parse(getValue("SKNNG.SPK_WS_TESTING_DATE", list).toString()): 
						null; // format yyyyMMdd
			}
		}
		catch (Exception ex) {
			LOGGER.error(ex, ex);
		}

		SKNNG_SPK_WS_IS_ENCRYPT = StatusDefinition.YES.equalsIgnoreCase((String) getValue("SKNNG.SPK_WS_ENC_FLG", list));
		CITYCODE = getValue("SKN.cityCode", list).toString();
		FILEFETCH = Integer.parseInt(getValue("SMS.FileFetch.Max", list).toString());
		ParamString = getValue("SMS.Java.Param",list).toString();
		paramCifAml = getValue("CIFAML.Java.Param", list).toString();
		KTPFETCH = Integer.parseInt(getValue("EKTP.FileFetch.Max", list).toString());
		KTPparamString = getValue("EKTP.Java.Param",list).toString();
		KTPHeaders = getValue("EKTP.Java.Header",list).toString();
		DFPattern = getValue("DDF.JAVA.pattern",list).toString();
		KKFETCH = Integer.parseInt(getValue("KK.FileFetch.Max", list).toString());
		KKparamString = getValue("KK.Java.Param",list).toString();
		KKHeaders = getValue("KK.Java.Header",list).toString();
		EKTP_READER_BASE_RESOURCE_URI = getValue("EKTP.READER.BASE_RESOURCE_URI", list).toString();
		EKTP_READER_BASE_PROTOCOLS = parseKeyAndValueToMap(getValue("EKTP.READER.BASE_PROTOCOLS", list).toString());

		SMSNSF = getValue("SMS.NSF.Param",list).toString();
		SMSDormant = getValue("SMS.Dormant.Param",list).toString();
		SMSAutoDebet = getValue("SMS.Autodebet.Param",list).toString();
		SMSIncoming = getValue("SMS.Incoming.Param",list).toString();
		SMSSPS = getValue("SMS.SPS.Param",list).toString();
		SMSDHN = getValue("SMS.DHN.Param",list).toString();
		SMSTDARO = getValue("SMS.TDARO.Param",list).toString();
		SMSReturn = getValue("SMS.Return.Param",list).toString();
		RESTRICTION_HOUR = getValue("BI.Midrate.Hour",list).toString();
		//SMSADIRA = getValue("SMS.ADIRA.Param",list).toString();
		casaMap = parseKeyAndValueToMap(getValue("CASAOP.Cif.query.Param", list).toString());
		casaDefMap = parseKeyAndValueToMap(getValue("CASAOP.Saving.Cust.Param", list).toString());
		casaDefAcctMap = parseKeyAndValueToMap(getValue("CASAOP.Saving.Acct.Param", list).toString());
		casaMapDefKTP = parseKeyAndValueToMap(getValue("CASAOP.Saving.Cust.DefParam", list).toString());
		casaMapDefIB = parseKeyAndValueToMap(getValue("DIBSAC.Saving.Cust.DefParam", list).toString());
		singleScreenprodExclude = getValue("CASAOP.sscreen.Prodexclude",list).toString();
		maxRetry = Integer.parseInt(getValue("CASAOP.maxRetry", list).toString());
		taxAmnesty = getValue("TAX.Amnesty.Param", list).toString();
		//SMSACOP = getValue("SMS.ACOP.Param", list).toString();

		BIGBILL_PERL = getValue("BIGBILL.PERL",list).toString();
		BIGBILL_MAX_TO_MIDDLEWARE = (Integer) getValue("BIGBILL.MaxToMiddleware", list);
		BIGBILL_RETRY_TO_MIDDLEWARE = (Integer) getValue("BIGBILL.RetryToMiddleware", list);
		BIGBILL_MAX_RECORDS_REGISTRATION = (Integer) getValue("BIGBILL.MaxRecordsRegistration", list); 
		BIGBILL_MAX_RECORDS_PAYMENT = (Integer) getValue("BIGBILL.MaxRecordsPayment", list);

		WIC_TXN_MNEMONIC = getValue("WIC.TXN_MNEMONIC", list).toString();
		ektpStreaming = parseKeyAndValueToMap(getValue("EKTP.Streaming", list).toString());
		ekkStreaming = parseKeyAndValueToMap(getValue("EKK.Streaming", list).toString());
		LOGGER.debug("successfull all record complete");

		lldAmount = Integer.parseInt(getValue("LLD_LIMIT_AMOUNT", list).toString());
		LLDDummy = getValue("LLD_UD_DUMMY",list).toString();
		LLDLainlain = getValue("LLD_LAIN_LAIN",list).toString();
		KTPMAX = Integer.parseInt(getValue("ktpMaxQuery", list).toString());
		sKHTAge = parseKeyAndValueToMap(getValue("SISKOHAT.Haji.Age", list).toString());
        timeoutHttp = Integer.parseInt(getValue("TIMEOUT_HTTP",list).toString());
        cashGlAccountNo = getValue("MPN.CashGlAccountNo",list).toString();
        glAccountNo = getValue("MPN.GlAccountNo",list).toString();
        userId = getValue("MPN.UserId",list).toString();
        codAcctNo = getValue("MPN.RekKoran.CodAcctNo",list).toString();
        ccyCode = parseKeyAndValueToMap(getValue("MPN.CcyCode", list).toString());
                
		/* Refresh Classes that need to be refreshed */
		try {
			ClassLoader CL = Thread.currentThread().getContextClassLoader();
			
			java.lang.reflect.Method m = parameterDao.getClass().getMethod("getRefreshClasses", new Class[0]);
			@SuppressWarnings("unchecked") List<String> listRefreshClasses = (List<String>) m.invoke(parameterDao, new Object[0]);
			LOGGER.info("listReloadClasses: " + listRefreshClasses);
			
			@SuppressWarnings("rawtypes") Class c;
			for (String s : listRefreshClasses) {
				c = checkClassIsLoaded(s, CL);
				LOGGER.info(s + " - " + (c != null));
				
				if (c != null) {
					java.lang.reflect.Method[] arrMethod = c.getDeclaredMethods();
					for (java.lang.reflect.Method m_ : arrMethod) {
						if ((java.lang.reflect.Modifier.isPublic(m_.getModifiers())) && (java.lang.reflect.Modifier.isStatic(m_.getModifiers())))
							if ((m_.getName().startsWith("refresh")) 
									&& (m_.getParameterTypes().length == 0)
									&& (m_.getReturnType() == Void.TYPE)
									) {
								LOGGER.info("call " + c.getSimpleName() + "." + m_.getName() + "() method");
								m_.invoke(null, new Object[0]);
							}
					}
					Class.forName(s, true, c.getClassLoader());
				}
			}
		}
		catch (Exception ex) {
			LOGGER.error("Error reload BDSM Reload Classes", ex);
		}
		
		if (currentSession == null)
			HibernateUtil.closeSession(session);
		else ;// the caller must responsible to close currentSession
		
	}

	private static Object getValue(String cdParam, List<Parameter> list) {
		for (Parameter p : list) {
			if (p.getCdParam().equals(cdParam)) {
				if ("S".equalsIgnoreCase(p.getTypParam()))
					return p.getStrVal();
				else if ("N".equalsIgnoreCase(p.getTypParam()))
					return p.getValue();
			}
		}
		return null;
	}

    /**
     * 
     * @param input
     * @return
     */
    public static Map<String, String> parseKeyAndValueToMap(String input) {
		String[] arr = input
							.replaceAll("[ ]*;[ ]*", ";")
							.replaceAll("[ ]*=[ ]*", "=")
							.split(";");
		
		
		List<String> l = new java.util.ArrayList<String>(java.util.Arrays.asList(arr));
		Map<String, String> result;
		String s;
		
		// Eliminate zero length string
		for (int i = 0; i < l.size(); i++) {
			s = l.get(i);
			if (s.trim().length() == 0)
				l.remove(i--);
		}
		
		result = new java.util.HashMap<String, String>(l.size());
		// Put to map
		for (String s2 : l) {
			arr = s2.split("=", 2);
			
			switch (arr.length) {
			case 1:
				if (arr[0].trim().length() > 0)
					result.put(arr[0], "");
				break;
			case 2:
				if ((arr[0].trim().length() > 0) && (arr[1].trim().length() > 0))
					result.put(arr[0], arr[1]);
			}
		}
		
		return result;
	}


	@SuppressWarnings("rawtypes")
	private static Class checkClassIsLoaded(String className, ClassLoader classLoader) throws Exception {
		Class result = null;
		
		while ((result == null) && (classLoader != null)) {
			java.lang.reflect.Field f = ClassLoader.class.getDeclaredField("classes");
			f.setAccessible(true);
			
			java.util.Vector classes = (java.util.Vector) f.get(classLoader);
			
			for (java.util.Iterator it=classes.iterator(); it.hasNext(); ) {
				Class c = (Class) it.next();
				if (c.getName().equals(className)) {
					result = c;
					break;
				}
			}
			
			classLoader = classLoader.getParent();
		}
		
		return result;
	}
	
}
