package bdsm.service;


import static bdsm.util.EncryptionUtil.getAES;
import static bdsm.util.EncryptionUtil.hashSHA256;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.hibernate.Session;
import org.hibernate.Transaction;

import bdsm.model.SkhtDepositReq;
import bdsm.model.SkhtDepositResp;
import bdsm.model.SkhtPaidOffInqReq;
import bdsm.model.SkhtPaidOffInqResp;
import bdsm.model.SkhtPaidOffReq;
import bdsm.model.SkhtPaidOffResp;
import bdsm.model.SkhtPrintDep;
import bdsm.model.SkhtPrintPaid;
import bdsm.model.SkhtPrintReq;
import bdsm.model.SkhtWSAuditTrail;
import bdsm.util.BdsmUtil;
import bdsm.util.HibernateUtil;
import bdsm.util.oracle.DateUtility;
import bdsmhost.dao.ParameterDao;
import bdsmhost.dao.SkhtWSAuditTrailDAO;
import bdsmhost.dao.TransactionParameterDao;
import bdsmhost.fcr.dao.BaCcyCodeDAO;

import com.google.gson.Gson;


/**
 * @author v00019372
 */
public class SiskohatService {
	private final static Logger LOGGER = Logger.getLogger(SiskohatService.class);
	private BaCcyCodeDAO ccyDAO;
	private Date settlementDate;
	private String[] BPKHAccounts;
	private String URL;
	private String username;
	private String password;
	private String authKey;
	private boolean checkSettlementDate = false;
	private DecimalFormat decF = new DecimalFormat("#");
	private SimpleDateFormat datF1 = new SimpleDateFormat("yyMMdd");
	private SimpleDateFormat datF2 = new SimpleDateFormat("ddMMyyyy");
	
	
    /**
     * 
     * @param session
     * @throws Exception
     */
    public SiskohatService(Session session) throws Exception {
		this.ccyDAO = new BaCcyCodeDAO(session);
		ParameterDao paramDAO = new ParameterDao(session);
		TransactionParameterDao trxParamDAO = new TransactionParameterDao(session);
		
		String strSettlementDate = paramDAO.get("SISKOHAT.SETTLEMENT_DATE").getStrVal();
		
		this.checkSettlementDate = ("Y".equalsIgnoreCase(paramDAO.get("SISKOHAT.CHECK_SETTLEMENT_DATE").getStrVal()));
		this.settlementDate = StringUtils.isBlank(strSettlementDate)? null: this.datF1.parse(paramDAO.get("SISKOHAT.SETTLEMENT_DATE").getStrVal());
		
		Map<String, ? extends Object> WS = BdsmUtil.parseKeyAndValueToMap(paramDAO.get("MDLWR_SISKOHAT_WS").getStrVal());
		this.URL = WS.get("URL").toString();
		this.username = WS.get("username").toString();
		this.password = getAES(WS.get("password").toString(), BdsmUtil.rightPad("MDLWR", 16, '@'), Cipher.DECRYPT_MODE);
		this.authKey = paramDAO.get("MDLWR_SISKOHAT_AUTH_TOKEN_KEY").getStrVal();
		
		this.BPKHAccounts = trxParamDAO.getByModuleName("SISKOHAT").getAccountNo().split(";");
		LOGGER.debug("Siskohat GL: " + java.util.Arrays.asList(this.BPKHAccounts));
	}
	
		
    /**
     * 
     * @param request
     * @return
     * @throws Exception
     */
    public SkhtDepositResp depositRequest(SkhtDepositReq request) throws Exception {
		Map<String, Object> mapData = this.buildInitialData(request, "HAJI_STR_AWAL", "depositRequest_");
		LOGGER.debug("Siskohat Request: " + request.toString());
		LOGGER.debug("Siskohat mapData: " + mapData);
		Map<String, Object> result;
		
		try {
			LOGGER.debug("LOGGER requestWebService: " + mapData);
			result = this.requestWebService(mapData, "setoranawal");
			// For Testing Only -- branch Jakarta 
			if (";811;814;61;7101;".indexOf(";" + Integer.parseInt(mapData.get("kode_cabang").toString()) + ";") > -1)
				result = dataDummy1(mapData);
			LOGGER.debug("LOGGER responWebService: " + result);
		}
		catch (RuntimeException ex) {
			result = new LinkedHashMap<String, Object>(0);
			result.put("code_status", "99999");
			result.put("desc_status", "BDSM - " + ex.getMessage());
		}
		
		SkhtDepositResp resp = new SkhtDepositResp();
		resp.setRefNo(result.get("user_ref_no").toString());
		resp.setResponseCode(result.get("code_status").toString());
		resp.setResponseDesc(result.get("desc_status").toString());
		
		if (Integer.parseInt(resp.getResponseCode()) == 0) {
			resp.setAcctNo(result.get("acct_no_from").toString());
			resp.setInitialDeposit(this.parseToBigDecimal(result.get("amount").toString()));
			resp.setCurrencyCode(Integer.parseInt(result.get("currency").toString()));
			resp.setJamaahName(result.get("nama_jamaah").toString());
			resp.setHajiType(result.get("jenis_haji").toString());
			resp.setNIK(result.get("no_id").toString());
			resp.setBirthPlace(result.get("tempat_lahir").toString());
			resp.setBirthDate(this.datF2.parse(result.get("tanggal_lahir").toString()));
			resp.setGender(result.get("jenis_kelamin").toString());
			resp.setAddress(result.get("alamat").toString());
			resp.setPostalCode(Integer.parseInt(result.get("kode_pos").toString()));
			resp.setVillageName(result.get("nama_desa").toString());
			resp.setDistrictName(result.get("nama_kecamatan").toString());
			resp.setCityName(result.get("nama_kabupaten").toString());
			resp.setProvinceCode(Integer.parseInt(result.get("kode_provinsi").toString()));
			resp.setCityCode(Integer.parseInt(result.get("kode_kabupaten").toString()));
			resp.setOccupationCode((result.get("kode_pekerjaan").toString()));
			resp.setEducationCode(Integer.parseInt(result.get("kode_pendidikan").toString()));
			resp.setMaritalStatus(result.get("status_pernikahan").toString());
			resp.setFatherName(result.get("nama_ayah").toString());
			resp.setBpsBranchCode(Integer.parseInt(result.get("kode_cabang").toString()));
			resp.setValidationNo(result.get("nomor_validasi").toString());
			resp.setDepartureYear(result.get("tahun_berangkat").toString());
			resp.setEmbarkasi(result.get("embarkasi").toString());
			resp.setKloter(result.get("kloter").toString());
			resp.setAcctVA(result.get("virtual_account").toString());
		}
		
		return resp;
	}
	
    /**
     * 
     * @param request
     * @return
     * @throws Exception
     */
    public SkhtPrintDep depositPrintRequest(SkhtPrintReq request) throws Exception {
		Map<String, Object> mapData = this.buildInitialData(request, "HAJI_CTK_AWAL", "depositPrintRequest_");
		Map<String, Object> result;
		
		try {
			LOGGER.debug("LOGGER requestWebService: " + mapData);
			result = this.requestWebService(mapData, "cetaksetoranawal");
			// For Testing Only -- branch Jakarta
			if (";811;814;61;7101;".indexOf(";" + Integer.parseInt(mapData.get("kode_cabang").toString()) + ";") > -1)
				result = dataDummy2(mapData);
			LOGGER.debug("LOGGER responWebService: " + result);
		}
		catch (Exception ex) {
			result = new LinkedHashMap<String, Object>(0);
			result.put("code_status", "99999");
			result.put("desc_status", "BDSM - " + ex.getMessage());
		}
		
		SkhtPrintDep resp = new SkhtPrintDep();
		resp.setRefNo(result.get("user_ref_no").toString());
		resp.setResponseCode(result.get("code_status").toString());
		resp.setResponseDesc(result.get("desc_status").toString());
		
		if (Integer.parseInt(resp.getResponseCode().toString()) == 0) {
			resp.setAcctNo(result.get("acct_no_from").toString());
			resp.setHajiType(result.get("jenis_haji").toString());
			resp.setTrxDate(this.datF2.parse(result.get("tanggal_transaksi").toString()));
			resp.setValidationNo(result.get("nomor_validasi").toString());
			resp.setBpsBranchCode(Integer.valueOf(result.get("kode_cabang").toString()));
			resp.setJamaahName(result.get("nama_jamaah").toString());
			resp.setNIK(result.get("no_id").toString());
			resp.setBirthPlace(result.get("tempat_lahir").toString());
			resp.setBirthDate(this.datF2.parse(result.get("tanggal_lahir").toString()));
			resp.setAddress(result.get("alamat").toString());
			resp.setPostalCode(Integer.valueOf(result.get("kode_pos").toString()));
			resp.setVillageName(result.get("nama_desa").toString());
			resp.setDistrictName(result.get("nama_kecamatan").toString());
			resp.setCityName(result.get("nama_kabupaten").toString());
			resp.setProvinceName(result.get("nama_provinsi").toString());
			resp.setOccupation(result.get("pekerjaan").toString());
			resp.setEducation(result.get("pendidikan").toString());
			resp.setGender(result.get("jenis_kelamin").toString());
			resp.setAgeInYear(Integer.parseInt(result.get("umur_tahun").toString()));
			resp.setAgeInMonth(Integer.parseInt(result.get("umur_bulan").toString()));
			resp.setBankName(result.get("nama_bank").toString());
			resp.setBranchName(result.get("nama_cabang").toString());
			resp.setBranchAddress(result.get("alamat_cabang").toString());
			//resp.setAcctVA(result.get("no_rekening").toString());
			resp.setCurrencyCode(Integer.parseInt(result.get("mata_uang").toString()));
			resp.setInitialDeposit(this.parseToBigDecimal(result.get("nilai_setoran_awal").toString()));
			resp.setAcctVA(result.get("virtual_account").toString());
		}
		
		return resp;
	}
	
    /**
     * 
     * @param request
     * @return
     * @throws Exception
     */
    public SkhtPaidOffInqResp paidOffInquiryRequest(SkhtPaidOffInqReq request) throws Exception {
		Map<String, Object> mapData = this.buildInitialData(request, "HAJI_INQ_LUNAS", "paidOffInquiryRequest_");
		Map<String, Object> result;
		
		try {
			LOGGER.debug("LOGGER requestWebService: " + mapData);
			result = this.requestWebService(mapData, "inquirypelunasan");
			LOGGER.debug("LOGGER responWebService: " + result);
			//if (!result.get("code_status").equals("000000")) {
				result = dataDummy3(mapData);
			//}
		}
		catch (Exception ex) {
			result = new LinkedHashMap<String, Object>(0);
			result.put("code_status", "99999");
			result.put("desc_status", "BDSM - " + ex.getMessage());
			LOGGER.debug("Siskohat result exception: " + ex, ex);
			result = dataDummy3(mapData);
		}
		
		SkhtPaidOffInqResp resp = new SkhtPaidOffInqResp();
		resp.setRefNo(result.get("user_ref_no").toString());
		resp.setResponseCode(result.get("code_status").toString());
		resp.setResponseDesc(result.get("desc_status").toString());
		
		if (Integer.parseInt(resp.getResponseCode()) == 0) {
			resp.setAcctNo(result.get("acct_no_from").toString());
			resp.setPortionNo(Long.valueOf(result.get("nomor_porsi").toString()));
			resp.setBpsBranchCode(Integer.parseInt(result.get("kode_cabang").toString()));
			resp.setJamaahName(result.get("nama_jamaah").toString());
			resp.setEmbarkasi(result.get("embarkasi").toString());
			resp.setCurrencyCode(Integer.valueOf(result.get("mata_uang").toString()));
			//resp.set(result.get("nilai_setoran_awal").toString());
			resp.setBPIHCost(this.parseToBigDecimal(result.get("biaya_bpih").toString(), 840));
			resp.setUSDExchange(this.parseToBigDecimal(result.get("kurs_dollar").toString()));
			//resp.setBPIHInIDR(this.parseToBigDecimal(result.get("bpih_rupiah").toString()));
			resp.setResidualCost(this.parseToBigDecimal(result.get("sisa_lunas").toString()));
			resp.setBpihCost(resp.getBPIHCost().toString());
			resp.setBpihInIDR(this.parseToBigDecimal(result.get("bpih_rupiah").toString()).toString());
			resp.setUsdExchange(resp.getUSDExchange().toString());
			resp.setInitialDeposit(this.parseToBigDecimal(result.get("nilai_setoran_awal").toString()));
			resp.setFlgPaidOff(result.get("flag_tunda").toString());
			resp.setDelayYear(result.get("tahun_tunda").toString());
			resp.setHajiType(result.get("jenis_haji").toString());
			resp.setPIHKCode(Integer.parseInt(result.get("kode_pihk").toString()));
			resp.setPIHKName(result.get("nama_pihk").toString());
			LOGGER.debug("LOGGER resp : " + resp);
		}
		return resp;
	}
	
    /**
     * 
     * @param request
     * @return
     * @throws Exception
     */
    public SkhtPaidOffResp paidOffRequest(SkhtPaidOffReq request) throws Exception {
		Map<String, Object> mapData = this.buildInitialData(request, "HAJI_LUNAS", "paidOffRequest_");
		Map<String, Object> result;
		
		try {
			LOGGER.debug("LOGGER requestWebService: " + mapData);
			result = this.requestWebService(mapData, "pelunasan");
			LOGGER.debug("LOGGER responWebService: " + result);
			result = dataDummy4(mapData);
		}
		catch (Exception ex) {
			result = new LinkedHashMap<String, Object>(0);
			result.put("code_status", "99999");
			result.put("desc_status", "BDSM - " + ex.getMessage());
			LOGGER.debug("Siskohat result: " + ex, ex);
			result = dataDummy4(mapData);
		}
		
		SkhtPaidOffResp resp = new SkhtPaidOffResp();
		resp.setRefNo(result.get("user_ref_no").toString());
		resp.setResponseCode(result.get("code_status").toString());
		resp.setResponseDesc(result.get("desc_status").toString());
		
		if (Integer.parseInt(resp.getResponseCode()) == 0) {
			resp.setAcctNo(result.get("acct_no_from").toString());
			resp.setFinalDeposit(this.parseToBigDecimal(result.get("amount").toString()));
			resp.setCurrencyCode(Integer.parseInt(result.get("currency").toString()));
			resp.setPortionNo(Long.valueOf(result.get("nomor_porsi").toString()));
			resp.setJamaahName(result.get("nama_jamaah").toString());
			resp.setEmbarkasi(result.get("embarkasi").toString());
			resp.setCurrencyCode(Integer.valueOf(result.get("mata_uang").toString()));
			resp.setInitialDeposit(this.parseToBigDecimal(result.get("nilai_setoran_awal").toString()));
			resp.setBPIHCost(this.parseToBigDecimal(result.get("biaya_bpih").toString(), 840));
			resp.setUSDExchange(this.parseToBigDecimal(result.get("kurs_dollar").toString()));
			resp.setBPIHInIDR(this.parseToBigDecimal(result.get("bpih_rupiah").toString()));
			resp.setResidualCost(this.parseToBigDecimal(result.get("sisa_lunas").toString()));
			resp.setFlgPaidOff(result.get("flag_tunda").toString());
			resp.setDelayYear(result.get("tahun_tunda").toString());
			resp.setHajiType(result.get("jenis_haji").toString());
			resp.setPIHKCode(Integer.parseInt(result.get("kode_pihk").toString()));
			resp.setPIHKName(result.get("nama_pihk").toString());
			resp.setBpsBranchCode(Integer.valueOf(result.get("kode_cabang").toString()));
			resp.setGender(result.get("jenis_kelamin").toString());
			resp.setFatherName(result.get("nama_ortu").toString());
			resp.setBirthPlace(result.get("tempat_lahir").toString());
			resp.setBirthDate(this.datF2.parse(result.get("tanggal_lahir").toString()));
			resp.setAddress(result.get("alamat").toString());
			resp.setVillageName(result.get("nama_desa").toString());
			resp.setDistrictName(result.get("nama_kecamatan").toString());
			resp.setCityName(result.get("nama_kabupaten").toString());
			resp.setProvinceName(result.get("nama_provinsi").toString());
			resp.setPostalCode(Integer.parseInt(result.get("kode_pos").toString()));
			resp.setBankName(result.get("nama_bank").toString());
			resp.setBranchName(result.get("nama_cabang").toString());
			resp.setBranchAddress(result.get("alamat_cabang").toString());
			resp.setAgeInYear(Integer.parseInt(result.get("umur_tahun").toString()));
			resp.setAgeInMonth(Integer.parseInt(result.get("umur_bulan").toString()));
			resp.setYearPaidOffMasehi(Integer.parseInt(result.get("tahun_lunas_masehi").toString()));
		}
		
		return resp;
	}
	
    /**
     * 
     * @param request
     * @return
     * @throws Exception
     */
    public SkhtPrintPaid paidOffPrintRequest(SkhtPrintReq request) throws Exception {
		Map<String, Object> mapData = this.buildInitialData(request, "HAJI_CTK_LUNAS", "paidOffPrintRequest_");
		Map<String, Object> result;
		
		try {
			LOGGER.debug("LOGGER requestWebService: " + mapData);
			result = this.requestWebService(mapData, "cetakpelunasan");
			LOGGER.debug("LOGGER responWebService: " + result);
			result = dataDummy5(mapData);
		}
		catch (Exception ex) {
			result = new LinkedHashMap<String, Object>(0);
			result.put("code_status", "99999");
			result.put("desc_status", "BDSM - " + ex.getMessage());
			LOGGER.debug("Siskohat result: " + ex, ex);
			result = dataDummy5(mapData);
		}
		
		
		SkhtPrintPaid resp = new SkhtPrintPaid();
		resp.setRefNo(result.get("user_ref_no").toString());
		resp.setResponseCode(result.get("code_status").toString());
		resp.setResponseDesc(result.get("desc_status").toString());
		
		if (Integer.parseInt(resp.getResponseCode()) == 0) {
			resp.setAcctNo(result.get("acct_no_from").toString());
			resp.setTrxDate(this.datF1.parse(result.get("settlement_date").toString()));
			resp.setHajiType(result.get("jenis_haji").toString());
			resp.setPortionNo(Integer.valueOf(result.get("nomor_porsi").toString()));
			resp.setBpsBranchCode(Integer.valueOf(result.get("kode_cabang").toString()));
			resp.setJamaahName(result.get("nama_jamaah").toString());
			resp.setGender(result.get("jenis_kelamin").toString());
			resp.setFatherName(result.get("nama_ortu").toString());
			resp.setBirthPlace(result.get("tempat_lahir").toString());
			resp.setBirthDate(this.datF2.parse(result.get("tanggal_lahir").toString()));
			resp.setEmbarkasi(result.get("embarkasi").toString());
			resp.setCurrencyCode(Integer.parseInt(result.get("mata_uang").toString()));
			resp.setBPIHCost(this.parseToBigDecimal(result.get("biaya_bpih").toString(), 840));
			resp.setBPIHInIDR(this.parseToBigDecimal(result.get("bpih_rupiah").toString()));
			resp.setResidualCost(this.parseToBigDecimal(result.get("sisa_lunas").toString()));
			resp.setFlgPaidOff(Integer.parseInt(result.get("flag_tunda").toString()));
			resp.setDelayYear(Integer.parseInt(result.get("tahun_tunda").toString()));
			resp.setPaidOffHIJJYear(Integer.parseInt(result.get("tahun_lunas_hijriah").toString()));
			resp.setPIHKCode(Integer.parseInt(result.get("kode_pihk").toString()));
			resp.setPIHKName(result.get("nama_pihk").toString());
			resp.setAddress(result.get("alamat").toString());
			resp.setVillageName(result.get("nama_desa").toString());
			resp.setDistrictName(result.get("nama_kecamatan").toString());
			resp.setCityName(result.get("nama_kabupaten").toString());
			resp.setProvinceName(result.get("nama_provinsi").toString());
			resp.setPostalCode(Integer.parseInt(result.get("kode_pos").toString()));
			resp.setBankName(result.get("nama_bank").toString());
			resp.setBranchName(result.get("nama_cabang").toString());
			resp.setBranchAddress(result.get("alamat_cabang").toString());
			resp.setAgeInYear(Integer.parseInt(result.get("umur_tahun").toString()));
			resp.setAgeInMonth(Integer.parseInt(result.get("umur_bulan").toString()));
			resp.setPaidOffMasehiYear(Integer.parseInt(result.get("tahun_lunas_masehi").toString()));
			resp.setChecksum(result.get("checksum").toString());
			String paidOffDate = result.get("tanggal_lunas").toString();
			resp.setPaidOffYear(Integer.parseInt(paidOffDate.substring(paidOffDate.length() - 4)));
			resp.setInitialDeposit(this.parseToBigDecimal(result.get("nilai_setoran_awal").toString()));
			resp.setUSDExchange(this.parseToBigDecimal(result.get("usd_rate").toString()));
		}
		
		return resp;
	}
	
	
    /**
     * 
     * @param accountNo
     * @return
     */
    public String generateRefNo(String accountNo) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("ddMMyyyyHHmmss");
		return (accountNo + dateFormatter.format(new Date()));
	}
	
	@SuppressWarnings({ "unchecked" })
	private Map<String, Object> requestWebService(Map<String, ? extends Object> requestData, String pathName) {
		SkhtWSAuditTrail out;
		SkhtWSAuditTrail in;
		Map<String, Object> mapResult = null;
		Gson gson = new Gson();
		String jsonRequest = gson.toJson(requestData);
		
		LOGGER.debug("Web Service request: " + jsonRequest);
		try {
			// insert WS out
			out = new SkhtWSAuditTrail();
			out.setRefNo(requestData.get("user_ref_no").toString());
			out.setProfileName("SISKOHAT");
			out.setMethodName(pathName);
			out.setTypeReq("O");
			out.setContent(jsonRequest);
			(new SaveData(new SkhtWSAuditTrailDAO(null), out)).start();
			LOGGER.debug("OUT result: " + out.toString());
			HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(this.username, this.password);
			Client client = ClientBuilder.newClient().register(feature);
			
			Response response = client.target(URL).path(pathName).request(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON_TYPE).post(Entity.json(jsonRequest));
				
			if ((response.getStatus() == Response.Status.OK.getStatusCode())
					&& isEqualMediaType(response.getMediaType(), MediaType.APPLICATION_JSON_TYPE)) {
				
				String jsonResponse = response.readEntity(String.class);
				mapResult = (Map<String, Object>) gson.fromJson(jsonResponse, Map.class);
				
				LOGGER.debug("Web Service response: " + jsonResponse);
				
				in = new SkhtWSAuditTrail();
				try {
					in.setRefNo(mapResult.get("user_ref_no").toString());
				}
				catch (Exception e) {
					LOGGER.debug("reff NOT fOUND: " + e, e);
					in.setRefNo(out.getRefNo());
				}
				in.setProfileName("SISKOHAT");
				in.setMethodName(pathName);
				in.setTypeReq("I");
				in.setContent(jsonResponse);
				(new SaveData(new SkhtWSAuditTrailDAO(null), in)).start();
			}
			else {
				throw new RuntimeException(response.getStatus() + " - " + Response.Status.fromStatusCode(response.getStatus()).getReasonPhrase());
			}
		}
		catch (RuntimeException ex) {
			LOGGER.debug("Exception result: " + ex, ex);
			throw ex;
		}
		
		return mapResult;
	}
	
	private String getSettlementDate() throws Exception {
		Date date = new Date();
		date = org.apache.commons.lang.time.DateUtils.truncate(date, java.util.Calendar.DAY_OF_MONTH);
		if ((this.checkSettlementDate) && (this.settlementDate != null) && (DateUtility.getDateDiff(date, this.settlementDate)[0] > 0))
			throw new bdsm.scheduler.exception.FIXException("Already cut off from Siskohat");
		
		return this.datF1.format((this.settlementDate!=null)? this.settlementDate: date);
	}
	
	@SuppressWarnings("rawtypes")
	private Map<String, Object> buildInitialData(Object input, String middlewareServiceCode, String specificDataMethod) throws Exception {
		String refNo, accountNo, branchCode;
		Class[] bc = new Class[0];
		Object[] bo = new Object[0];
		Method method;
		
		try {
			method = input.getClass().getMethod("getRefNo", bc);
			refNo = method.invoke(input, bo).toString();
		}
		catch (Exception ex) {
			refNo = "";
		}
		
		try {
			method = input.getClass().getMethod("getAcctNo", bc);
			accountNo = method.invoke(input, bo).toString();
		}
		catch (Exception ex) {
			accountNo = "";
		}
		
		try {
			method = input.getClass().getMethod("getBpsBranchCode", bc);
			branchCode = method.invoke(input, bo).toString();
		}
		catch (Exception ex) {
			branchCode = "";
		}
		branchCode = this.formatNumericString(branchCode, 6);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Map<String, Object> mapData = new LinkedHashMap<String, Object>(0);
		mapData.put("channel_id", "BDSM");
		mapData.put("bin_no", this.formatNumericString("0", 4));
		mapData.put("user_ref_no", refNo);
		mapData.put("request_time", sdf.format(new Date()));
		mapData.put("service_code", middlewareServiceCode);
		
		StringBuilder sb = new StringBuilder(mapData.get("bin_no").toString())
			.append(hashSHA256(this.authKey))
			.append(mapData.get("request_time"))
			.append(mapData.get("user_ref_no"))
			.append(mapData.get("service_code"))
			.append(accountNo)
			.append(branchCode)
			;
		mapData.put("auth_token", hashSHA256(sb.toString()));
		
		mapData.put("acct_no_from", accountNo);
		
		try {
			method = this.getClass().getDeclaredMethod(specificDataMethod, new Class[] {Map.class, Object.class});
			method.invoke(this, mapData, input);
		}
		catch (java.lang.reflect.InvocationTargetException ex) {
			Throwable t = ex;
			while (t.getCause() != null)
				t = ex.getCause();
			
			throw new bdsm.scheduler.exception.FIXException(t.getMessage());
		}
		
		mapData.put("kode_cabang", branchCode);
		
		return mapData;
	}
	
	
	
	private static boolean isEqualMediaType(MediaType m1, MediaType m2) {
		if ((m1 == null) && (m2 == null))
			return true;
		else if ((m1 == null) || (m2 == null))
			return false;
		else
			return ((m1.getType().equals(m2.getType())) && (m1.getSubtype().equals(m2.getSubtype())));
	}
	
	private BigDecimal parseToBigDecimal(String input) throws Exception {
		return this.parseToBigDecimal(input, 360);
	}
	
	private BigDecimal parseToBigDecimal(String input, Integer currencyCode) throws Exception {
		int dec = this.ccyDAO.getByCcyCod(currencyCode).getCtrCcyDec();
		BigDecimal tmp = new BigDecimal(this.decF.parse(input).toString());
		
		return tmp.divide(BigDecimal.TEN.pow(dec));
	}
	
	private String formatFromBigDecimal(BigDecimal input) {
		return this.formatFromBigDecimal(input, 360);
	}
	
	private String formatFromBigDecimal(BigDecimal input, Integer currencyCode) {
		int dec = this.ccyDAO.getByCcyCod(currencyCode).getCtrCcyDec();
		
		return this.formatNumericString(this.decF.format(input.multiply(BigDecimal.TEN.pow(dec))), 13);
	}
	
	private String formatNumericString(String input, int length) {
		return BdsmUtil.leftPad(input, length, '0');
	}
	
	
    /**
     * 
     * @param mapData
     * @param input
     * @throws Exception
     */
    protected void depositRequest_(Map<String, Object> mapData, Object input) throws Exception {
		SkhtDepositReq request = (SkhtDepositReq) input;  
		mapData.put("acct_no_to", this.BPKHAccounts[0]);
		mapData.put("amount", this.formatFromBigDecimal(request.getInitialDeposit()));
		mapData.put("currency", request.getCurrencyCode().toString());
		mapData.put("settlement_date", this.getSettlementDate());
		mapData.put("nama_jamaah", request.getJamaahName());
		mapData.put("jenis_haji", request.getHajiType());
		mapData.put("no_id", request.getNIK());
		mapData.put("tempat_lahir", request.getBirthPlace());
		mapData.put("tanggal_lahir", this.datF2.format(request.getBirthDate()));
		mapData.put("jenis_kelamin", request.getGender());
		mapData.put("alamat", request.getAddress());
		mapData.put("kode_pos", this.formatNumericString(request.getPostalCode().toString(), 5));
		mapData.put("nama_desa", request.getVillageName());
		mapData.put("nama_kecamatan", request.getDistrictName());
		mapData.put("nama_kabupaten", request.getCityName());
		mapData.put("kode_provinsi", request.getProvinceCode().toString());
		mapData.put("kode_kabupaten", request.getCityCode().toString());
		mapData.put("kode_pekerjaan", request.getOccupationCode().toString());
		mapData.put("kode_pendidikan", request.getEducationCode().toString());
		mapData.put("status_pernikahan", request.getMaritalStatus());
		mapData.put("nama_ayah", request.getFatherName());

	}
	
    /**
     * 
     * @param mapData
     * @param input
     * @throws Exception
     */
    protected void depositPrintRequest_(Map<String, Object> mapData, Object input) throws Exception {
		SkhtPrintReq request = (SkhtPrintReq) input;
		mapData.put("settlement_date", this.getSettlementDate());
		mapData.put("jenis_haji", request.getHajiType());
		mapData.put("tanggal_transaksi", this.datF2.format(new Date()));
		mapData.put("nomor_validasi", request.getValidationNo());
	}
	
    /**
     * 
     * @param mapData
     * @param input
     * @throws Exception
     */
    protected void paidOffInquiryRequest_(Map<String, Object> mapData, Object input) throws Exception {
		SkhtPaidOffInqReq request = (SkhtPaidOffInqReq) input;
		mapData.put("nomor_porsi", this.formatNumericString(request.getPortionNo().toString(), 10));
	}
	
    /**
     * 
     * @param mapData
     * @param input
     * @throws Exception
     */
    protected void paidOffRequest_(Map<String, Object> mapData, Object input) throws Exception {
		SkhtPaidOffReq request = (SkhtPaidOffReq) input;
		mapData.put("acct_no_to", this.BPKHAccounts[1]);
		mapData.put("amount", this.formatFromBigDecimal(request.getFinalDeposit()));
		mapData.put("currency", request.getCurrencyCode().toString());
		mapData.put("settlement_date", this.getSettlementDate());
		mapData.put("nomor_porsi", this.formatNumericString(request.getPortionNo().toString(), 10));
		mapData.put("nama_jamaah", request.getJamaahName());
		mapData.put("embarkasi", request.getEmbarkasi());
		mapData.put("mata_uang", request.getCurrencyCode().toString());
		mapData.put("nilai_setoran_awal", this.formatFromBigDecimal(request.getInitialDeposit()));
		mapData.put("biaya_bpih", this.formatFromBigDecimal(request.getBPIHCost(), 840));
		mapData.put("kurs_dollar", this.formatFromBigDecimal(request.getUSDExchange()));
		mapData.put("bpih_rupiah", this.formatFromBigDecimal(request.getBPIHInIDR()));
		mapData.put("sisa_lunas", this.formatFromBigDecimal(request.getResidualCost()));
		mapData.put("flag_tunda", request.getFlgPaidOff());
		mapData.put("tahun_tunda", request.getDelayYear());
		mapData.put("jenis_haji", request.getHajiType());
		mapData.put("kode_pihk", this.formatNumericString(request.getPIHKCode().toString(), 4));
		mapData.put("nama_pihk", request.getPIHKName());
	}
	
    /**
     * 
     * @param mapData
     * @param input
     * @throws Exception
     */
    protected void paidOffPrintRequest_(Map<String, Object> mapData, Object input) throws Exception {
		SkhtPrintReq request = (SkhtPrintReq) input;
		mapData.put("acct_no_from", request.getAcctNo());
		mapData.put("settlement_date", this.getSettlementDate());
		mapData.put("jenis_haji", request.getHajiType());
		mapData.put("tanggal_transaksi", this.datF2.format(new Date()));
		mapData.put("nomor_porsi", this.formatNumericString(request.getPortionNo().toString(), 10));
		mapData.put("kode_cabang", this.formatNumericString(request.getBpsBranchCode().toString(), 6));
	}
	
	
	private Map<String, Object> dataDummy1(Map<String, Object> mapData){
		Map<String, Object> result = new java.util.HashMap<String, Object>(0);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			result.put("channel_id", mapData.get("channel_id"));
			result.put("bin_no", mapData.get("bin_no"));
			result.put("user_ref_no", mapData.get("user_ref_no"));
			result.put("response_time", sdf.format(new Date()));
			result.put("service_code", mapData.get("service_code"));
			result.put("auth_token", mapData.get("auth_token"));
			result.put("code_status", "000000");
			result.put("desc_status", "Successfull");
			result.put("acct_no_from", mapData.get("acct_no_from"));
			result.put("acct_no_to", mapData.get("acct_no_to"));
			result.put("amount", mapData.get("amount"));
			result.put("currency", mapData.get("currency"));
			result.put("settlement_date", mapData.get("settlement_date"));
			result.put("nama_jamaah", mapData.get("nama_jamaah"));
			result.put("jenis_haji", mapData.get("jenis_haji"));
			result.put("no_id", mapData.get("no_id"));
			result.put("tempat_lahir", mapData.get("tempat_lahir"));
			result.put("tanggal_lahir", mapData.get("tanggal_lahir"));
			result.put("jenis_kelamin", mapData.get("jenis_kelamin"));
			result.put("alamat", mapData.get("alamat"));
			result.put("kode_pos", mapData.get("kode_pos"));
			result.put("nama_desa", mapData.get("nama_desa"));
			result.put("nama_kecamatan", mapData.get("nama_kecamatan"));
			result.put("nama_kabupaten", mapData.get("nama_kabupaten"));
			result.put("kode_provinsi", mapData.get("kode_provinsi"));
			result.put("kode_kabupaten", mapData.get("kode_kabupaten"));
			result.put("kode_pekerjaan", mapData.get("kode_pekerjaan"));
			result.put("kode_pendidikan", mapData.get("kode_pendidikan"));
			result.put("status_pernikahan", mapData.get("status_pernikahan"));
			result.put("nama_ayah", mapData.get("nama_ayah"));
			result.put("kode_cabang", mapData.get("kode_cabang"));
			result.put("nomor_validasi", "01118053000013564019");
			result.put("tahun_berangkat", "");
			result.put("embarkasi", "");
			result.put("kloter", "");
			result.put("virtual_account", ".........................");
		return result;
	}
	
	private Map<String, Object> dataDummy2(Map<String, Object> mapData) {
		Map<String, Object> result = new java.util.HashMap<String, Object>(0);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		result.put("channel_id", mapData.get("channel_id"));
		result.put("bin_no", mapData.get("bin_no"));
		result.put("user_ref_no", mapData.get("user_ref_no"));
		result.put("response_time", sdf.format(new Date()));
		result.put("service_code", mapData.get("service_code"));
		result.put("auth_token", mapData.get("auth_token"));
		result.put("code_status", "000000");
		result.put("desc_status", "SUCCESS");
		result.put("acct_no_from", mapData.get("acct_no_from"));
		result.put("settlement_date", mapData.get("settlement_date"));
		result.put("jenis_haji", mapData.get("jenis_haji"));
		result.put("tanggal_transaksi", mapData.get("tanggal_transaksi"));
		result.put("nomor_validasi", mapData.get("nomor_validasi"));
		result.put("kode_cabang", mapData.get("kode_cabang"));
		result.put("nama_jamaah", "budi");
		result.put("no_id", "3456345230023434");
		result.put("tempat_lahir", "Jakarta");
		result.put("tanggal_lahir", "02041957");
		result.put("alamat", "Jl Tondano no 10");
		result.put("kode_pos", "13256");
		result.put("nama_desa", "Jelambar");
		result.put("nama_kecamatan", "Tambora");
		result.put("nama_kabupaten", "Jakarta");
		result.put("nama_provinsi", "DKI Jakarta");
		result.put("pekerjaan", "Wirausaha");
		result.put("pendidikan", "SMA");
		result.put("jenis_kelamin", "1");
		result.put("umur_tahun", "61");
		result.put("umur_bulan", "0");
		result.put("nama_bank", "DANAMON");
		result.put("nama_cabang", "KEBON SIRIH");
		result.put("alamat_cabang", "Jl Kebon Sirih no 15");
		result.put("no_rekening", "003542453459");
		result.put("mata_uang", "360");
		result.put("nilai_setoran_awal", "2500000000");
		result.put("virtual_account", "536342342343767867");
		
		return result;
	}
	
	private Map<String, Object> dataDummy3(Map<String, Object> mapData) {
		Map<String, Object> result = new java.util.HashMap<String, Object>(0);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		result.put("channel_id", mapData.get("channel_id"));
		result.put("bin_no", mapData.get("bin_no"));
		result.put("user_ref_no", mapData.get("user_ref_no"));
		result.put("response_time", sdf.format(new Date()));
		result.put("service_code", mapData.get("service_code"));
		result.put("auth_token", mapData.get("auth_token"));
		result.put("code_status", "000000");
		result.put("desc_status", "SUCCESS");
		result.put("acct_no_from", mapData.get("acct_no_from"));
		result.put("nomor_porsi", mapData.get("nomor_porsi"));
		result.put("kode_cabang", mapData.get("kode_cabang"));
		result.put("nama_jamaah", "SUBAIDAH");
		result.put("embarkasi", "JKG");
		result.put("mata_uang", "360");
		result.put("nilai_setoran_awal", "0002500000000");
		result.put("biaya_bpih", "0003500000000");
		result.put("kurs_dollar", "0000000100");
		result.put("bpih_rupiah", "0003500000000");
		result.put("sisa_lunas", "0001000000000");
		result.put("flag_tunda", "2");
		result.put("tahun_tunda", "0000");
		result.put("jenis_haji", "B");
		result.put("kode_pihk", "0000");
		result.put("nama_pihk", "");
		
		return result;
	}
	
	private Map<String, Object> dataDummy4(Map<String, Object> mapData) {
		Map<String, Object> result = new java.util.HashMap<String, Object>(0);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		result.put("channel_id", mapData.get("channel_id"));
		result.put("bin_no", mapData.get("bin_no"));
		result.put("user_ref_no", mapData.get("user_ref_no"));
		result.put("response_time", sdf.format(new Date()));
		result.put("service_code", mapData.get("service_code"));
		result.put("auth_token", mapData.get("auth_token"));
		result.put("code_status", "000000");
		result.put("desc_status", "SUCCESS");
		result.put("acct_no_from", mapData.get("acct_no_from"));
		result.put("acct_no_to", mapData.get("acct_no_to"));
		result.put("amount", mapData.get("amount"));
		result.put("currency", mapData.get("currency"));
		result.put("settlement_date", mapData.get("settlement_date"));
		result.put("nomor_porsi", mapData.get("nomor_porsi"));
		result.put("nama_jamaah", mapData.get("nama_jamaah"));
		result.put("embarkasi", mapData.get("embarkasi"));
		result.put("mata_uang", mapData.get("mata_uang"));
		result.put("nilai_setoran_awal", mapData.get("nilai_setoran_awal"));
		result.put("biaya_bpih", mapData.get("biaya_bpih"));
		result.put("kurs_dollar", mapData.get("kurs_dollar"));
		result.put("bpih_rupiah", mapData.get("bpih_rupiah"));
		result.put("sisa_lunas", mapData.get("sisa_lunas"));
		result.put("flag_tunda", mapData.get("flag_tunda"));
		result.put("tahun_tunda", mapData.get("tahun_tunda"));
		result.put("jenis_haji", mapData.get("jenis_haji"));
		result.put("kode_pihk", mapData.get("kode_pihk"));
		result.put("nama_pihk", mapData.get("nama_pihk"));
		result.put("kode_cabang", mapData.get("kode_cabang"));
		result.put("jenis_kelamin", "1");
		result.put("nama_ortu", "Agus");
		result.put("tempat_lahir", "Jakarta");
		result.put("tanggal_lahir", "02041957");
		result.put("alamat", "Jl Tondano no 10");
		result.put("nama_desa", "Jelambar");
		result.put("nama_kecamatan", "Tambora");
		result.put("nama_kabupaten", "Jakarta");
		result.put("nama_provinsi", "DKI Jakarta");
		result.put("kode_pos", "13256");
		result.put("nama_bank", "DANAMON");
		result.put("nama_cabang", "KEBON SIRIH");
		result.put("alamat_cabang", "Jl Kebon Sirih no 15");
		result.put("umur_tahun", "61");
		result.put("umur_bulan", "0");
		result.put("tahun_lunas_masehi", "2018");
		result.put("checksum", "CE114E4501D2F4E2DCEA3E17B546F339");
		
		return result;
	}
	
	private Map<String, Object> dataDummy5(Map<String, Object> mapData) {
		Map<String, Object> result = new java.util.HashMap<String, Object>(0);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		result.put("channel_id", mapData.get("channel_id"));
		result.put("bin_no", mapData.get("bin_no"));
		result.put("user_ref_no", mapData.get("user_ref_no"));
		result.put("response_time", sdf.format(new Date()));
		result.put("service_code", mapData.get("service_code"));
		result.put("auth_token", mapData.get("auth_token"));
		result.put("code_status", "000000");
		result.put("desc_status", "SUCCESS");
		result.put("acct_no_from", mapData.get("acct_no_from"));
		result.put("settlement_date", mapData.get("settlement_date"));
		result.put("jenis_haji", mapData.get("jenis_haji"));
		result.put("nomor_porsi", mapData.get("nomor_porsi"));
		result.put("kode_cabang", mapData.get("kode_cabang"));
		result.put("nama_jamaah", "Budi");
		result.put("jenis_kelamin", "1");
		result.put("nama_ortu", "Agus");
		result.put("tempat_lahir", "Jakarta");
		result.put("tanggal_lahir", "02041957");
		result.put("embarkasi", "CGK");
		result.put("mata_uang", "360");
		result.put("biaya_bpih", "2900");
		result.put("bpih_rupiah", "39150000");
		result.put("sisa_lunas", "10150000");
		result.put("flag_tunda", "1");
		result.put("tahun_tunda", "2019");
		result.put("tahun_lunas_hijriah", "1438");
		result.put("kode_pihk", "1234");
		result.put("nama_pihk", "ALHIJAZ");
		result.put("alamat", "Jl Tondano no 10");
		result.put("nama_desa", "Jelambar");
		result.put("nama_kecamatan", "Tambora");
		result.put("nama_kabupaten", "Jakarta");
		result.put("nama_provinsi", "DKI Jakarta");
		result.put("kode_pos", "13256");
		result.put("nama_bank", "DANAMON");
		result.put("nama_cabang", "KEBON SIRIH");
		result.put("alamat_cabang", "Jl Kebon Sirih no 15");
		result.put("umur_tahun", "61");
		result.put("umur_bulan", "0");
		result.put("tahun_lunas_masehi", "2018");
		result.put("checksum", "CE114E4501D2F4E2DCEA3E17B546F339");
		result.put("tanggal_lunas", "22042018");
		result.put("nilai_setoran_awal", "25000000");
		result.put("usd_rate", "13500");
		
		return result;
	}
	
	
	class SaveData extends Thread {
		private final Logger LOGGER = Logger.getLogger(SaveData.class);
		private bdsmhost.dao.BaseDao dao;
		private bdsm.model.BaseModel model;
		
		public SaveData(bdsmhost.dao.BaseDao dao, bdsm.model.BaseModel model) {
			this.dao = dao;
			this.model = model;
		}
		
		@Override
		public void run() {
			Session session = HibernateUtil.getSession();
			Transaction trx = session.beginTransaction();
			
			try {
				this.dao.setSession(session);
				this.dao.insert(this.model);
			}
			catch (Exception e) {
				LOGGER.debug("INSERT FAILED : " + e, e);
			}
			
			trx.commit();
			HibernateUtil.closeSession(session);
		}
	}
	
}
