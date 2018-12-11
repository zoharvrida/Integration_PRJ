package bdsm.scheduler.processor;


import static bdsm.util.EncryptionUtil.getAES;
import static bdsm.util.EncryptionUtil.hashSHA256;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import com.google.gson.Gson;

import bdsm.fcr.model.SPAJ;
import bdsm.fcr.model.SPAJReference;
import bdsm.scheduler.MapKey;
import bdsm.scheduler.dao.FixSchedulerXtractDao;
import bdsm.scheduler.dao.FixTemplateMasterDao;
import bdsm.scheduler.exception.FIXException;
import bdsm.scheduler.model.FixSchedulerXtract;
import bdsm.scheduler.model.FixTemplateMaster;
import bdsm.scheduler.model.FixTemplateMasterPK;
import bdsm.util.BdsmUtil;
import bdsmhost.dao.ParameterDao;
import bdsmhost.fcr.dao.BaBankMastDAO;
import bdsmhost.fcr.dao.SPAJDAO;
import bdsmhost.fcr.dao.SPAJReferenceDAO;


/**
 * 
 * @author v00019372
 *
 */
public class PrimajagaManulifeRequestSubmitWorker extends BaseProcessor {
	private static Map<String, Long> lockMap = new HashMap<String, Long>(0);

    /**
     * 
     * @param context
     */
    public PrimajagaManulifeRequestSubmitWorker(Map<String, ? extends Object> context) {
		super(context);
	}
	

    /**
     * 
     * @return
     * @throws Exception
     */
    @Override
	@SuppressWarnings("unchecked")
	protected boolean doExecute() throws Exception {
		BaBankMastDAO bbmDAO = new BaBankMastDAO(this.session);
		SPAJDAO spajDAO = new SPAJDAO(this.session);
		SPAJReferenceDAO spajRefDAO = new SPAJReferenceDAO(this.session);
		FixSchedulerXtractDao fsxDAO = new FixSchedulerXtractDao(this.session);
		FixTemplateMasterDao ftmDAO = new FixTemplateMasterDao(this.session); 
		SPAJ spaj;
		SPAJReference spajReference;
		Gson gson = new Gson();
		Map<String, Object> mapJson;
		String accountNo = (String) this.context.get(MapKey.param1);
		
		if (lockMap.containsKey(accountNo) == false)
			lockMap.put(accountNo, Long.valueOf(accountNo));
		
		Long lockObject = lockMap.get(accountNo);
		synchronized(lockObject) {
			spajReference = spajRefDAO.getByReferenceNo(accountNo);
			
			if (this.context.get(MapKey.param2) == null) { // Request SUBA to Manulife
				ParameterDao paramDAO = new ParameterDao(this.session);
				
				String keyAuthToken = paramDAO.get("MDLWR_PRIMAJAGA_MANULIFE_AUTH_TOKEN_KEY").getStrVal();
				Map<String, String> mapMDLWR = BdsmUtil.parseKeyAndValueToMap(paramDAO.get("MDLWR_PRIMAJAGA_MANULIFE_SUBA_WS").getStrVal());
				String url = mapMDLWR.get("URL");
				String username = mapMDLWR.get("username");
				String password = getAES(mapMDLWR.get("password"), BdsmUtil.rightPad("MDLWR", 16, '@'), Cipher.DECRYPT_MODE);
				int pos = url.lastIndexOf("/");
				spaj = spajDAO.getByAccountNo(accountNo);
				
				if ((spaj == null) || ((spajReference == null) && (Integer.valueOf(spaj.getStatusCode().trim()) != 0)))
					throw new FIXException("You must print SPAJ first!!!");
				else if (((spajReference.getOutStatus() == null) && (spaj.getRefNo1() == null)) 
						|| ((spajReference.getOutStatus() == "1") && (Integer.valueOf(spaj.getStatusCode()) != 1)))
					throw new FIXException("Still waiting for the status from DMS or still not success from DMS");
				else if ((spajReference.getOutStatus() != null) && (spaj.getStatusCode() != null)) {
					if ((spajReference.getOutStatus().trim().equals("4")) && (Integer.valueOf(spaj.getStatusCode().trim()) == 1))
						throw new FIXException("Already approved by Manulife");
					else if ((spajReference.getOutStatus().trim().equals("3")) && (Integer.valueOf(spaj.getStatusCode().trim()) == 0))
						throw new FIXException("Waiting for approval status from Manulife");
				}
				
				
				if (bbmDAO.get().getBusinessDate().compareTo(spaj.getDatAcctOpening()) != 0)
					throw new FIXException("Account opening date must be today");
				
				String jsonData = gson.toJson(buildWSData(spaj, spajDAO.generateId(), keyAuthToken));
				
				spajReference.setOutData(jsonData);
				spajReference.setOutStatus("2");
				spajReference.setOutDesc1("SUBA Request");
				spajReference.setDtmCreated(new java.sql.Timestamp(System.currentTimeMillis()));
				spaj.setStatusCode("3000");
				spaj.setTxtDesc1("Application already submitted to Manulife");
				this.commitTransaction();
				
				
				this.getLogger().debug("username: " + username + "\npassword: " + password);
				HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(username, password);
				Client client = ClientBuilder.newClient().register(feature);
				
				Response response = client
					.target(url.substring(0, pos))
					.path(url.substring(pos))
					.request(MediaType.APPLICATION_JSON_TYPE)
					.accept(MediaType.APPLICATION_JSON_TYPE)
					.post(Entity.json(jsonData));
				
				if ((response.getStatus() == Response.Status.OK.getStatusCode()) && 
						isEqualMediaType(response.getMediaType(), MediaType.APPLICATION_JSON_TYPE)) {
					String strJson = response.readEntity(String.class);
					spajReference.setInData(strJson);
					this.commitTransaction();
					
					mapJson = gson.fromJson(strJson, Map.class);
					Map<String, Object> sar = (Map<String, Object>) ((mapJson.get("SubmitApplicationResult") == null)? mapJson.get("SubmitApplicationResultDanamon"): mapJson.get("SubmitApplicationResult"));
					Map<String, Object> errors;
					List<Map<String, Object>> lstErrors;
					
					spajReference.setInStatus((String) sar.get("ResponseCode"));
					spaj.setStatusCode(spajReference.getInStatus());
					
					
					if (((errors = (Map<String, Object>) mapJson.get("Errors")) != null)
							&& ((lstErrors = (List<Map<String, Object>>) errors.get("ErrorList")) != null) && (lstErrors.size() > 0)
						)
					{
						int i = 0;
						while ((i <= lstErrors.size() - 1) && (i < 2)) {
							Map<String, Object> map = lstErrors.get(i);
							String defaultLanguage = (String) map.get("DEFAULT_LANGUAGE");
							String errorDesc = (String) ((Map<String, Object>) map.get("ErrorMessages")).get(defaultLanguage);
							
							if (i == 0)
								spajReference.setInDesc1(errorDesc);
							else
								spajReference.setInDesc2(errorDesc);
							
							spaj.setTxtDesc1(spajReference.getInDesc1());
							spaj.setTxtDesc2(spajReference.getInDesc2());
							
							i++;
						}
					}
					else {
						spajReference.setInDesc1((String) sar.get("ResponseDescription"));
						spaj.setTxtDesc1(spajReference.getInDesc1());
					}
					
					spajReference.setOutStatus("3");
					spajReference.setOutDesc1("SUBA Response");
					
					spajDAO.update(spaj);
					spajRefDAO.update(spajReference);
					this.commitTransaction();
					
					
					FixSchedulerXtract fsx = fsxDAO.get("PJ_PPA_MANULIFE_CEK_STATUS");
					FixTemplateMasterPK pk = new FixTemplateMasterPK();
					pk.setIdTemplate(fsx.getFixSchedulerPK().getIdTemplate());
					FixTemplateMaster ftm = ftmDAO.get(pk);
					
					Map<String, Object> mapReport = new HashMap<String, Object>(0);
					Map<String, Object> mapReportParam = new HashMap<String, Object>(this.context);
					
					mapReport.put(MapKey.reportId, ftm.getRptFileTemplate());
					mapReport.put(MapKey.reportFileName, mapReportParam.remove(MapKey.reportFileName));
					mapReport.put(MapKey.reportFormat, mapReportParam.remove(MapKey.reportFormat));
					mapReport.put(MapKey.session, mapReportParam.remove(MapKey.session));
					mapReportParam.put(MapKey.param1, spaj.getCodAcctNo());
					mapReportParam.remove(MapKey.idScheduler);
					mapReportParam.remove(MapKey.javaClass);
					mapReportParam.remove(MapKey.typeFix);
					mapReport.put(MapKey.reportParam, mapReportParam);
					
					JasperGenRpt jgr = new JasperGenRpt(mapReport);
					jgr.generateReport();
				}
				else
					throw new Exception(response.getStatus() + " - " + Response.Status.fromStatusCode(response.getStatus()).getReasonPhrase());
			}
			else {
				String inData = spajReference.getInData();
				mapJson = gson.fromJson(inData, Map.class);
				spaj = spajDAO.getByAccountNo(accountNo);
				
				if (this.context.get(MapKey.param2).toString().toUpperCase().startsWith("MLI")) { // Update from Manulife 
					if (accountNo.equals(mapJson.get("spaj_no"))) {
						if (mapJson.containsKey("policy_no"))
							spaj.setCodPolicyNo(mapJson.get("policy_no").toString());
						if (mapJson.containsKey("user_ref_no"))
							spaj.setRefNo3(mapJson.get("user_ref_no").toString());
						if (mapJson.containsKey("appication_result"))
							spajReference.setInStatus(mapJson.get("appication_result").toString());
						if (mapJson.containsKey("application_result"))
							spajReference.setInStatus(mapJson.get("application_result").toString());
						if (mapJson.containsKey("application_description1"))
							spajReference.setInDesc1(mapJson.get("application_description1").toString());
						
						if (mapJson.containsKey("application_description2"))
							spajReference.setInDesc2(mapJson.get("application_description2").toString());
						else
							spajReference.setInDesc2(null);
						
						
						spaj.setStatusCode(spajReference.getInStatus());
						spaj.setTxtDesc1(spajReference.getInDesc1());
						spaj.setTxtDesc2(spajReference.getInDesc2());
						spajReference.setOutStatus("4");
						spajReference.setOutDesc1("UAPS Response");
						spajReference.setLocked(Boolean.FALSE);
						
						spajDAO.update(spaj);
						spajRefDAO.update(spajReference);
					}
				}
				else if (this.context.get(MapKey.param2).toString().toUpperCase().startsWith("DMS")) { // insert into spaj_reference_history
					SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
					Date param3 = null;
					boolean isMayNotUpdateSPAJ = this.isMayNotUpdateSPAJ(spaj, spajReference);
					
					spajReference.setLocked(Boolean.FALSE);
					
					if (mapJson.containsKey("user_ref_no"))
						spaj.setRefNo1(mapJson.get("user_ref_no").toString());
					
					if (isMayNotUpdateSPAJ) { // if there is already process to and from Manulife
						param3 = sdf.parse(this.context.get(MapKey.param3).toString());
						String[] inDataAndStatusOri = spajRefDAO.getInDataFromSPAJReferenceHistory(accountNo, param3);
						spajReference.setInData(inDataAndStatusOri[0]);
						spajReference.setInStatus(inDataAndStatusOri[1]);
						
						spajReference = new SPAJReference(spajReference.getReferenceNo());
						spajReference.setInData(inData);
						spajReference.setDtmUpdated(new java.sql.Timestamp(System.currentTimeMillis()));
					}
					
					if (accountNo.equals(mapJson.get("cod_acct_no"))) {
						if (mapJson.containsKey("appication_result"))
							spajReference.setInStatus(mapJson.get("appication_result").toString());
						
						spajReference.setInDesc2(null);
						if (mapJson.containsKey("status")) {
							spajReference.setInDesc1(mapJson.get("status").toString());
							spajReference.setInStatus(("SUCCESS".equalsIgnoreCase(spajReference.getInDesc1()))? "0001": "0002");
						}
						
						spajReference.setOutStatus("1");
						spajReference.setOutDesc1("DMS Response");
					}
					
					if (isMayNotUpdateSPAJ) { // if there is already process to and from Manulife
						spajRefDAO.insertSPAJReferenceHistory(spajReference);
						spajRefDAO.deleteSPAJReferenceHistory(accountNo, param3);
					}
					else {
						spaj.setStatusCode(spajReference.getInStatus());
						spaj.setTxtDesc1(spajReference.getInDesc1());
						spaj.setTxtDesc2(spajReference.getInDesc2());
						
						spajDAO.update(spaj);
						spajRefDAO.update(spajReference);
					}
				}
			}
		}
		lockMap.remove(lockObject);
		
		return true;
	}
	
	
	private boolean isMayNotUpdateSPAJ(SPAJ spaj, SPAJReference spajReference) {
		return (StringUtils.isNotBlank(spajReference.getOutStatus()) 
				&& (
					((spajReference.getOutStatus().trim().equals("3")) && (Integer.valueOf(spaj.getStatusCode()) == 0))
					||
					((spajReference.getOutStatus().trim().equals("4")) && (Integer.valueOf(spaj.getStatusCode()) == 1))
				) 
			);
	}
	
	private Map<String, Object> buildWSData(SPAJ spaj, String requestId, String keyAuthToken) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Map<String, Object> dataMap = new LinkedHashMap<String, Object>(0);
		Map<String, Object> map;
		List<Object> list;
		
		spaj.setRefNo2(requestId);
		
		dataMap.put("AuthenticationToken", "");
		dataMap.put("RequestID", requestId);
		dataMap.put("ApplicationNumber", BdsmUtil.leftPad(this.getValue(spaj.getCodAcctNo()).toString(), 12, '0'));
		dataMap.put("CIF", bdsm.util.BdsmUtil.leftPad(this.getValue(spaj.getCodCustId().toString()).toString(), 10, '0'));
		
		map = createNewMap(dataMap, "eAppEmployeeDataDanamon");
		map.put("EmployeeID", this.getValue(spaj.getCodUserId()));
		map.put("EmployeeName", this.getValue(spaj.getNamUser()));
		map.put("EmployeeBranchCode", this.getValue(spaj.getCodCcBrn().toString()));
		map.put("DealerCode", this.getValue(spaj.getCodDealerNo().toString()));
		
		map = createNewMap(dataMap, "eAppInsuredDataDanamon");
		map.put("FullName", this.getValue(spaj.getNamCustFull()));
		map.put("DateOfBirth", this.getValue(sdf.format(spaj.getDatOfBirth())));
		map.put("PlaceOfBirth", this.getValue(spaj.getTxtBirthPlace()));
		map.put("Gender", this.getValue(spaj.getCodGender()));
		map.put("IdentityNumber", this.getValue(spaj.getCodNatlId()));
		map.put("IdentityNumberExpiryDate", this.getValue(spaj.getDatExpiryNatlId()));
		map.put("Email", this.getValue(spaj.getRefEmail()));
		map.put("MaritalStatus", this.getValue(spaj.getCodMarsts()));
		map.put("Nationality", this.getValue(spaj.getTxtCustNatnlty()));
		map.put("IdRegisteredAddress1", this.getValue(spaj.getTxtPermAdrAdd1()));
		map.put("IdRegisteredAddress2", this.getValue(spaj.getTxtPermAdrAdd2(), String.class, "-"));
		map.put("IdRegisteredAddress3", this.getValue(spaj.getTxtPermAdrAdd3(), String.class, "-"));
		map.put("IdRegisteredAddressCity", this.getValue(spaj.getNamPermAdrCity()));
		map.put("IdRegisteredAddressZipCode", this.getValue(spaj.getTxtPermAdrZip()));
		map.put("IdRegisteredAddressCountry", this.getValue(spaj.getNamPermAdrCntry()));
		map.put("ResidenceAddress1", this.getValue(spaj.getTxtMailAdd1()));
		map.put("ResidenceAddress2", this.getValue(spaj.getTxtMailAdd2(), String.class, "-"));
		map.put("ResidenceAddress3", this.getValue(spaj.getTxtMailAdd3(), String.class, "-"));
		map.put("ResidenceAddressCity", this.getValue(spaj.getNamMailCity()));
		map.put("ResidenceAddressZipCode", this.getValue(spaj.getTxtMailZip()));
		map.put("ResidenceAddressCountry", this.getValue(spaj.getNamMailCntry()));
		map.put("HomePhoneCountryCode", this.getValue(spaj.getRefPhnCntry()));
		map.put("HomePhoneAreaCode", this.getValue(spaj.getRefPhnArea()));
		map.put("HomePhoneNumber", this.getValue(spaj.getRefCustPhone()));
		map.put("CellPhoneNumber", this.getValue(spaj.getRefMobileNo()));
		
		list = createNewList(dataMap, "eAppInsurabilityQuestionDataDanamon");
		String[] arrQ = {spaj.getCodQstn1(), spaj.getCodQstn2()};
		for (int i=1; i<=arrQ.length; i++) {
			map = createNewMap(list);
			map.put("QuestionNumber", String.valueOf(i));
			map.put("SequenceNumber", "1");
			map.put("Answer", this.getValue(arrQ[i-1]));
		}
		
		map = createNewMap(dataMap, "eAppPaymentDataDanamon");
		map.put("Currency", this.getValue(spaj.getCodRemitCcy()));
		map.put("BankName", this.getValue(spaj.getNamBank()));
		map.put("BankBranch", this.getValue(spaj.getCodCcBrn().toString()));
		map.put("BankCountry", this.getValue(spaj.getNamBankCntry()));
		map.put("NameOnAccount", this.getValue(spaj.getNamRemitAcct()));
		map.put("AccountNumber", this.getValue(spaj.getCodRemitAcct()));
		
		map = createNewMap(dataMap, "eAppPlanInfoDanamon");
		map.put("ProductCode", this.getValue(spaj.getCodProd().toString()));
		map.put("FaceAmount", this.getValue(spaj.getAmtMntlyBenefit(), BigDecimal.class));
		map.put("Premium", this.getValue(spaj.getAmtInstal(), BigDecimal.class));
		map.put("Period", this.getValue(spaj.getCodTerm(), Integer.class));
		
		list = createNewList(dataMap, "eAppBeneficiaryOwnerDanamon");
		
		String[][] arrQ2 = new String[][] {(new String[] {spaj.getTxtBenefRecipient(), spaj.getCodRel()})};
		for (int i=1; i<=arrQ2.length; i++) {
			map = createNewMap(list);
			map.put("FullName", this.getValue(arrQ2[i-1][0]));
			map.put("BeneficiaryRelationshipToInsured", this.getValue(arrQ2[i-1][1]));
		}
		
		@SuppressWarnings("unchecked")
		StringBuilder sb = new StringBuilder(hashSHA256(keyAuthToken))
			.append(dataMap.get("ApplicationNumber").toString())
			.append(dataMap.get("CIF").toString())
			.append(((Map<String, Object>)dataMap.get("eAppInsuredDataDanamon")).get("DateOfBirth").toString())
			.append(((Map<String, Object>)dataMap.get("eAppInsuredDataDanamon")).get("IdentityNumber").toString())
			.append(((Map<String, Object>)dataMap.get("eAppPaymentDataDanamon")).get("AccountNumber").toString())
			.append(dataMap.get("RequestID").toString())
			;
		dataMap.put("AuthenticationToken", hashSHA256(sb.toString()));
		
		return dataMap;
	}
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static Map<String, Object> createNewMap(Object parent, Object... id) {
		Map<String, Object> map = new LinkedHashMap<String, Object>(0);
		if (parent instanceof Map)
			((Map) parent).put(id[0], map);
		else if (parent instanceof List)
			((List) parent).add(map);
		
		return map; 
	}
	
	private static List<Object> createNewList(Map<String, Object> parentMap, String id) {
		List<Object> list = new ArrayList<Object>(0);
		parentMap.put(id, list);
		
		return list; 
	}
	
	private static boolean isEqualMediaType(MediaType m1, MediaType m2) {
		if ((m1 == null) && (m2 == null))
			return true;
		else if ((m1 == null) || (m2 == null))
			return false;
		else
			return ((m1.getType().equals(m2.getType())) && (m1.getSubtype().equals(m2.getSubtype())));
	}
	
	private Object getValue(Object input) {
		return this.getValue(input, String.class, null);
	}
	
	@SuppressWarnings("rawtypes")
	private Object getValue(Object input, Class c) {
		return this.getValue(input, c, null);
	}
	
	@SuppressWarnings("rawtypes") 
	private Object getValue(Object input, Class c, Object valueIfNull) {
		if (input == null) {
			if (valueIfNull != null) return valueIfNull;
			else if (c == String.class) return "";
			else if (Number.class.isAssignableFrom(c)) return 0;
		}
		
		if (input instanceof String)
			return input.toString().trim();
		
		return input;
	}
	
    /**
     * 
     */
    protected void commitTransaction() {
		this.tx.commit();
		this.tx = this.session.beginTransaction();
	}

}
