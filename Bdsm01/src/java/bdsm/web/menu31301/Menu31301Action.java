package bdsm.web.menu31301;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.struts2.interceptor.validation.SkipValidation;
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;

import bdsm.model.ScreenMsg;
import bdsm.model.SkhtDepositResp;
import bdsm.model.SkhtPrintDep;
import bdsm.model.SkhtPrintReq;
import bdsm.model.SkhtViewData;
import bdsm.util.ClassConverterUtil;
import bdsm.web.Constant;
import bdsm.web.ModelDrivenBaseContentAction;
import bdsm.web.ScreenMsgService;


/**
*
* @author v00022309
*/
@SuppressWarnings("serial")
public class Menu31301Action extends ModelDrivenBaseContentAction<Object> {
	private static final String SUCCESSMessage = "TXT_SKHT_SUCC";
	//private static final String FAILEDMessage = "TXT_SKHT_FAIL";
	private static final String FAILEDNullMessage = "TXT_SKHT_NULL";
	private static final String SUCCPrintMessage = "TXT_SKHT_PRINT_SUCC";
	private static final String FAILPrintMessage = "TXT_SKHT_PRINT_FAIL";
	private static final String FAILEDMessageInq = "TXT_SKHT_INQUIRY_FAIL";
	private static final String FAILEDMessageBal = "TXT_SKHT_BALANCE_FAIL";
	private static final String SUCCMessageBal = "TXT_SKHT_BALANCE_SUCC";
	
	private String acctNoInq;
	private String acctNo;
	private String nik;
	private String name;
	private String gender;
	private String maritalStatus;
	private String birthPlace;
	private String address1;
	private String address2;
	private String address3;
	private String postalCode;
	private String desaName;
	private String kecamatanName;
	private String educationName;
	private String fatherName;
	private String occupation;
	private String setoranAwal;
	private String hajiType;
	private String validasiNo;
	private String responCode;
	
	private SkhtViewData svd;
	private SkhtPrintReq reqPrint;
	private SkhtDepositResp skDepResp;
	
	private String state = "0";
	private ScreenMsg scrMsg;
	private String contentData;
	private String messagings;
	private String balance;
	
	
    /**
     * 
     */
    public Menu31301Action() {
		this.putToObjData("hajiId", "");
		this.putToObjData("cifNo", "");
	}
	
	
    /**
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
	public String doInput() {
		Map<String, String> requestMap = new HashMap<String, String>();
		requestMap.put("methodName", "checkCutOff");
		
		try {
			Map<String, ? extends Object> resultMap = this.callHostHTTPRequest("SKHT", "callMethod", requestMap);
			Map<String, ? extends Object> objData = (Map<String, ? extends Object>) resultMap.get("objData");
			
			if (objData.get("cutoff") != null) {
				this.getServletRequest().setAttribute("resultType", "cutoff");
				return "result";
			}
		}
		catch (Exception ex) {
			this.getLogger().error(ex, ex);
			return ERROR;
		}
		
		return INPUT;
	}	

    /**
     * 
     * @return
     */
    @Override
	public String exec() {
		throw new UnsupportedOperationException("Not supported yet.");
	}
	
    /**
     * 
     * @return
     */
    @Override
	@SuppressWarnings("unchecked")
	public String doAdd() {
		Map<String, String> requestMap = this.createParameterMapFromHTTPRequest();
		requestMap.put("methodName", "postingInitialDeposit");
		requestMap.put("useTransaction", "true");
		requestMap.put("acctNo", this.svd.getAcctNoInq());
		requestMap.put("strData.idMaintainedBy", this.session.get(Constant.C_IDUSER).toString());
		requestMap.put("codOrgBrn", this.session.get(Constant.C_CODEBRANCH).toString());
		
		this.session.put("SKHT", "POST");
		this.acctNoInq = this.svd.getAcctNoInq();
		Integer messageType = 2; // error as default
		
		this.getLogger().info("Account Number : " + this.svd);
		this.getLogger().debug("HTTP_pARAM :" + requestMap);
		
		try {
			Map<String, ? extends Object> resultMap = this.callHostHTTPRequest("SKHT", "callMethod", requestMap);
			if ((resultMap.get("objData") != null) && (((Map<String, Object>) resultMap.get("objData")).size() > 0))
				this.setObjData((Map<String, Object>) resultMap.get("objData"));
			
			String jsonStatus = resultMap.get("jsonStatus").toString();
			String errorCode = (String) resultMap.get("errorCode");
			
			if ((ERROR.equals(jsonStatus)) && (errorCode != null)) {
				if (errorCode.startsWith("skht."))
					errorCode = "31300." + errorCode;
				
				this.scrMsg = new ScreenMsg(this.getText(errorCode));
			}
			else {
				this.getLogger().debug("MapSave: " + resultMap);
				this.getLogger().debug("Hasil ZZZZ: " + resultMap.get("skhtDepResp"));
				Map<String, Object> skhtDepResp = (Map<String, Object>) resultMap.get("skhtDepResp");
	
				this.getLogger().debug("Hasil bbbb: " + skhtDepResp);
				this.skDepResp = new SkhtDepositResp();
				try {
					ClassConverterUtil.MapToClass(skhtDepResp, this.skDepResp);
					this.responCode = (String) skhtDepResp.get("responseCode");
				}
				catch (Exception e) {
					this.getLogger().debug("Hasil ccccc: " + e, e);
					this.getLogger().debug("Hasil aaaaa: " + skhtDepResp.get("responseCode"));
				}
				this.getLogger().debug("Hasil Respon Code: " + this.responCode);
				this.session.put("SKHT", "CREATED");
				
				if (Integer.valueOf(this.responCode) == 0) {
					this.state = "2";
					this.scrMsg = this.getScreenMsgresp(SUCCESSMessage);
					messageType = 3;
				}
				else if (this.responCode.equals(null))
					this.scrMsg = this.getScreenMsgresp(FAILEDNullMessage);
				else
					this.scrMsg = new ScreenMsg(this.skDepResp.getResponseCode() + " " + this.skDepResp.getResponseDesc());
			}
		}
		catch (Exception ex) {
			this.getLogger().error(ex, ex);
			this.scrMsg = this.getScreenMsgresp(FAILEDNullMessage);
			
			return ERROR;
		}
		
		this.getLogger().debug("Value Screen Message22222: " + this.scrMsg.toString());
		this.setMessagePopup(messageType, this.scrMsg.getMessage());
		
		return SUCCESS;
	}

    /**
     * 
     * @return
     */
    @Override
	public String doEdit() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

    /**
     * 
     * @return
     */
    @Override
	public String doDelete() {
		throw new UnsupportedOperationException("Not supported yet.");
	}
	
	
    /**
     * 
     * @return
     */
    public final String inquiryAcctNo() {
		this.getLogger().info("[Begin] inquiryAcctNo");
		try {
			if (isValidSession())
				return this.inquiryAcctNo_();
			else
				return logout();
		}
		catch (Throwable e) {
			this.getLogger().fatal(e, e);
			return ERROR;
		}
		finally {
			this.getLogger().info("[ End ] inquiryAcctNo()");
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private String inquiryAcctNo_() {
		String jsonStatus;
		String errorCode;
		
		this.getLogger().info("Account Number : " + this.acctNoInq);
		Map<String, String> requestMap = new HashMap<String, String>();
		requestMap.put("methodName", "retrieveDepositViewData");
		requestMap.put("acctNo", this.acctNoInq);
		requestMap.put("strData.mode", this.getStrData().get("mode"));
		requestMap.put("codOrgBrn", this.session.get(Constant.C_CODEBRANCH).toString());

		try {
			Map<String, ? extends Object> resultMap = this.callHostHTTPRequest("SKHT", "callMethod", requestMap);
			this.getLogger().debug("Map : " + resultMap);
			jsonStatus = resultMap.get("jsonStatus").toString();
			errorCode = (String) resultMap.get("errorCode");
			
			if ((ERROR.equals(jsonStatus)) && (errorCode != null)) {
				if (errorCode.startsWith("skht."))
					errorCode = "31300." + errorCode;
				
				this.setMessagePopup(1, this.getText(errorCode));
			}
			else {
				Map viewData = (Map) resultMap.get("svd");
				this.getLogger().debug("viewData: " + viewData);
				
				this.svd = new SkhtViewData();
				ClassConverterUtil.MapToClass(viewData, this.svd);
				this.getLogger().debug("svd : " + this.svd);
				
				if (resultMap.get("objData") != null)
					this.setObjData((Map) resultMap.get("objData"));
				if (resultMap.get("strData") != null)
					this.setStrData((Map) resultMap.get("strData"));
				
				this.session.put("SKHT", "NEW");
			}
			this.state = "1";
		}
		catch (Exception er) {
			this.getLogger().error(er, er);
			this.session.put("SKHT_Inqury", "FAILED");
			this.setMessagePopup(2, this.getScreenMsgresp(FAILEDMessageInq).getMessage());
			
			return ERROR;
		}
		
		this.session.put("SKHT", "INQUIRY");
		return SUCCESS;
	}
	
    /**
     * 
     * @return
     */
    public final String reqPrint() {
		this.getLogger().info("[Begin] Print Request");
		try {
			if (isValidSession())
				return this.reqPrint_();
			else
				return logout();
		}
		catch (Throwable e) {
			this.getLogger().fatal(e, e);
			return ERROR;
		}
		finally {
			this.getLogger().info("[ End ] Print Request");
		}
	}

    /**
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
	public String reqPrint_() {
		Map<String, String> requestMap = new HashMap<String, String>();
		SkhtPrintDep skpDep = new SkhtPrintDep();
		
		requestMap.put("methodName", "printInitialDeposit");
		requestMap.put("useTransaction", "true");
		requestMap.put("acctNo", this.reqPrint.getAcctNo());
		requestMap.put("validationNo", this.reqPrint.getValidationNo());
		requestMap.put("HajiType", this.reqPrint.getHajiType());
		requestMap.put("strData.cifNo", this.getFromStrData("cifNo"));
		requestMap.put("strData.idMaintainedBy", this.session.get(Constant.C_IDUSER).toString());
		requestMap.put("codOrgBrn", this.session.get(Constant.C_CODEBRANCH).toString());

		this.getLogger().debug("mapssss : " + requestMap);
		this.session.put("SKHT", "PRINTED");
		
		try {
			Map<String, ? extends Object> resultMap = this.callHostHTTPRequest("SKHT", "callMethod", requestMap);
			if ((resultMap.get("objData") != null) && (((Map<String, Object>) resultMap.get("objData")).size() > 0))
				this.setObjData((Map<String, Object>) resultMap.get("objData"));
			
			this.getLogger().debug("MapSave: " + resultMap);
			String jsonStatus = resultMap.get("jsonStatus").toString();
			String errorCode = (String) resultMap.get("errorCode");
			
			if ((ERROR.equals(jsonStatus)) && (errorCode != null)) {
				if (errorCode.startsWith("skht."))
					errorCode = "31300." + errorCode;
				
				this.scrMsg = new ScreenMsg(this.getText(errorCode));
				this.contentData = "{}";
			}
			else {
				Map<String, Object> skhtPrintResp = (Map<String, Object>) resultMap.get("skpDep");
				this.responCode = (String) skhtPrintResp.get("responseCode");
				ClassConverterUtil.MapToClass(skhtPrintResp, skpDep);
				
				try {
					this.contentData = (JSONUtil.serialize(skpDep));
					this.getLogger().debug("content_data: " + this.contentData);
					
					// Change yyyy-MM-ddTHH:mm:ss into dd MMMM yyyy
					Pattern pattern = Pattern.compile("\\d{4}[-]\\d{2}[-]\\d{2}T\\d{2}:\\d{2}:\\d{2}");
					Matcher matcher = pattern.matcher(this.contentData);
					DateFormat dateFormatter1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
					DateFormat dateFormatter2 = new SimpleDateFormat("dd MMMM yyyy", new java.util.Locale("in", "ID"));
					StringBuffer sb = new StringBuffer();
					
					while (matcher.find()) {
						String g = matcher.group();
						try {
							Date d = dateFormatter1.parse(g);
							String strReplace = dateFormatter2.format(d);
							
							matcher.appendReplacement(sb, strReplace);
						}
						catch (ParseException e) {}
					}
					matcher.appendTail(sb);
					this.contentData = sb.toString();
					this.getLogger().debug("content_data_replace: " + this.contentData);
					
					this.getLogger().info("FAILED to sESSION: " + this.session);
				}
				catch (JSONException jSONException) {
					this.getLogger().info("FAILED to Serialize: " + jSONException, jSONException);
				}
			}
		}
		catch (Exception ex) {
			this.getLogger().error(ex, ex);
			this.session.put("SKHT", "PRINTFAILED");
			this.setScrMsg(getScreenMsgresp(FAILPrintMessage));
			return ERROR;
		}
		
		Integer messageType = 2;
		if (this.responCode == null) {
			if (this.scrMsg == null)
				this.scrMsg = this.getScreenMsgresp(FAILEDNullMessage);
		}
		else if (Integer.valueOf(this.responCode) == 0) {
			this.state = "3";
			this.scrMsg = this.getScreenMsgresp(SUCCPrintMessage);
			messageType = 3;
		}
		else
			this.scrMsg = new ScreenMsg(skpDep.getResponseCode() + " " + skpDep.getResponseDesc());
		
		this.setMessagePopup(messageType, this.scrMsg.getMessage());
		this.getServletRequest().setAttribute("resultType", "printAwal");
		return "result";
	}
	
    /**
     * 
     * @return
     */
    public final String inquiryBalance() {
		this.getLogger().info("[Begin] inquiryBalance()");
		try {
			if (isValidSession()) {
				return this.inquiryBalNo_();
			}
			else
				return logout();
		}
		catch (Throwable e) {
			this.getLogger().fatal(e, e);
			return ERROR;
		}
		finally {
			this.getLogger().info("[ End ] inquiryBalance()");
		}
	}
	
	@SuppressWarnings("unchecked")
	private String inquiryBalNo_() {
		this.getLogger().info("Account Number balance: " + this.acctNoInq);
		Map<String, String> requestMap = new HashMap<String, String>();
		requestMap.put("methodName", "compareBalance");
		requestMap.put("acctNo", this.acctNoInq);
		requestMap.put("balance", this.setoranAwal);
		requestMap.put("codOrgBrn", this.session.get(Constant.C_CODEBRANCH).toString());

		try {
			Map<String, ? extends Object> resultMap = this.callHostHTTPRequest("SKHT", "callMethod", requestMap);
			this.getLogger().debug("Map: " + resultMap);

			this.balance = resultMap.get("keys").toString();
			this.getLogger().debug("Balance: " + this.balance);
			
			if (this.balance.equalsIgnoreCase("VALIDATE")) {
				this.scrMsg = this.getScreenMsgresp(SUCCMessageBal); 
				this.getLogger().debug("Balance is sufficient");
				this.contentData = (JSONUtil.serialize(this.svd));
			}
			else
			  this.scrMsg = this.getScreenMsgresp(FAILEDMessageBal);	

			this.state = "1";
		}
		catch (Exception er) {
			this.getLogger().debug("Error Balance: " + er, er);
		}
		
		this.getServletRequest().setAttribute("resultType", "balance");
		return "result";
	}
	
	
    /**
     * 
     * @param tag
     * @return
     */
    @SkipValidation
	@SuppressWarnings("rawtypes")
	public ScreenMsg getScreenMsgresp(String tag) {
		ScreenMsg msg = new ScreenMsg();
		ScreenMsgService sms = new ScreenMsgService(this.getServletRequest(), session, this.getBdsmHost(), this.getTokenKey(), this.getTzToken());
		this.getLogger().info("[Begin] Detect Screen Message: " + tag);
		Map mapScreen = sms.getMessage(tag);
		
		if (mapScreen != null) {
			this.getLogger().debug("SKHT MAP: " + mapScreen);
			try {
				Map msgMap = (HashMap) mapScreen.get("model");
				ClassConverterUtil.MapToClass(msgMap, msg);
			}
			catch (Exception e) {
				this.getLogger().debug(e, e);
			}
		}
		return msg;
	}
	
	private void setMessagePopup(int type, String message) {
		this.getServletRequest().setAttribute("SKHT_MESSAGE", this.getText(message));
		this.getServletRequest().setAttribute("SKHT_MESSAGE_TYPE", type);
	}
	
	
    /**
     * 
     * @return
     */
    public String getAcctNoInq() {
		return this.acctNoInq;
	}
    /**
     * 
     * @param acctNoInq
     */
    public void setAcctNoInq(String acctNoInq) {
		this.acctNoInq = acctNoInq;
	}
	
    /**
     * 
     * @return
     */
    public String getAcctNo() {
		return this.acctNo;
	}
    /**
     * 
     * @param acctNo
     */
    public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}
	
    /**
     * 
     * @return
     */
    public String getNik() {
		return this.nik;
	}
    /**
     * 
     * @param nik
     */
    public void setNik(String nik) {
		this.nik = nik;
	}
	
    /**
     * 
     * @return
     */
    public String getName() {
		return this.name;
	}
    /**
     * 
     * @param name
     */
    public void setName(String name) {
		this.name = name;
	}
	
    /**
     * 
     * @return
     */
    public String getGender() {
	   return this.gender;
	}
    /**
     * 
     * @param gender
     */
    public void setGender(String gender) {
		this.gender = gender;
	}
	
    /**
     * 
     * @return
     */
    public String getMaritalStatus() {
		return this.maritalStatus;
	}
    /**
     * 
     * @param maritalStatus
     */
    public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}
	
    /**
     * 
     * @return
     */
    public String getBirthPlace() {
		return this.birthPlace;
	}
    /**
     * 
     * @param birthPlace
     */
    public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}
	
    /**
     * 
     * @return
     */
    public String getAddress1() {
		return this.address1;
	}
    /**
     * 
     * @param address1
     */
    public void setAddress1(String address1) {
		this.address1 = address1;
	}
	
    /**
     * 
     * @return
     */
    public String getAddress2() {
		return this.address2;
	}
    /**
     * 
     * @param address2
     */
    public void setAddress2(String address2) {
		this.address2 = address2;
	}
	
    /**
     * 
     * @return
     */
    public String getAddress3() {
		return this.address3;
	}
    /**
     * 
     * @param address3
     */
    public void setAddress3(String address3) {
		this.address3 = address3;
	}
	
    /**
     * 
     * @return
     */
    public String getPostalCode() {
		return this.postalCode;
	}
    /**
     * 
     * @param postalCode
     */
    public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	
    /**
     * 
     * @return
     */
    public String getDesaName() {
		return this.desaName;
	}
    /**
     * 
     * @param desaName
     */
    public void setDesaName(String desaName) {
		this.desaName = desaName;
	}
	
    /**
     * 
     * @return
     */
    public String getKecamatanName() {
		return this.kecamatanName;
	}
    /**
     * 
     * @param kecamatanName
     */
    public void setKecamatanName(String kecamatanName) {
		this.kecamatanName = kecamatanName;
	}
	
    /**
     * 
     * @return
     */
    public String getEducationName() {
		return this.educationName;
	}
    /**
     * 
     * @param educationName
     */
    public void setEducationName(String educationName) {
		this.educationName = educationName;
	}
	
    /**
     * 
     * @return
     */
    public String getFatherName() {
		return this.fatherName;
	}
    /**
     * 
     * @param fatherName
     */
    public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}
	
    /**
     * 
     * @return
     */
    public String getOccupation() {
		return this.occupation;
	}
    /**
     * 
     * @param occupation
     */
    public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	
    /**
     * 
     * @return
     */
    public String getSetoranAwal() {
		return this.setoranAwal;
	}
    /**
     * 
     * @param setoranAwal
     */
    public void setSetoranAwal(String setoranAwal) {
		this.setoranAwal = setoranAwal;
	}
	
    /**
     * 
     * @return
     */
    public String getHajiType() {
		return this.hajiType;
	}
    /**
     * 
     * @param hajiType
     */
    public void setHajiType(String hajiType) {
		this.hajiType = hajiType;
	}
	
    /**
     * 
     * @return
     */
    public String getValidasiNo() {
	   return this.validasiNo;
	}
    /**
     * 
     * @param validasiNo
     */
    public void setValidasiNo(String validasiNo) {
		this.validasiNo = validasiNo;
	}
	
    /**
     * 
     * @return
     */
    public String getResponCode() {
		return this.responCode;
	}
    /**
     * 
     * @param responCode
     */
    public void setResponCode(String responCode) {
		this.responCode = responCode;
	}
	
	
    /**
     * 
     * @return
     */
    public SkhtViewData getSvd() {
		return this.svd;
	}
    /**
     * 
     * @param svd
     */
    public void setSvd(SkhtViewData svd) {
		this.svd = svd;
	}
	
    /**
     * 
     * @return
     */
    public SkhtPrintReq getReqPrint() {
		return this.reqPrint;
	}
    /**
     * 
     * @param reqPrint
     */
    public void setReqPrint(SkhtPrintReq reqPrint) {
		this.reqPrint = reqPrint;
	}
	
    /**
     * 
     * @return
     */
    public SkhtDepositResp getSkDepResp() {
		return this.skDepResp;
	}
    /**
     * 
     * @param skDepResp
     */
    public void setSkDepResp(SkhtDepositResp skDepResp) {
		this.skDepResp = skDepResp;
	}
	
	
    /**
     * 
     * @return
     */
    public String getState() {
		return this.state;
	}
    /**
     * 
     * @param state
     */
    public void setState(String state) {
		this.state = state;
	}
	
    /**
     * 
     * @return
     */
    public ScreenMsg getScrMsg() {
		return this.scrMsg;
	}
    /**
     * 
     * @param scrMsg
     */
    public void setScrMsg(ScreenMsg scrMsg) {
		this.scrMsg = scrMsg;
	}
	
    /**
     * 
     * @return
     */
    public String getContentData() {
		return this.contentData;
	}
    /**
     * 
     * @param contentData
     */
    public void setContentData(String contentData) {
		this.contentData = contentData;
	}
	
    /**
     * 
     * @return
     */
    public String getMessagings() {
		return this.messagings;
	}
    /**
     * 
     * @param messagings
     */
    public void setMessagings(String messagings) {
		this.messagings = messagings;
	}
	
    /**
     * 
     * @return
     */
    public String getBalance() {
		return this.balance;
	}
    /**
     * 
     * @param balance
     */
    public void setBalance(String balance) {
		this.balance = balance;
	}
	
	
    /**
     * 
     * @return
     */
    @Override
	public Object getModel() {
		return null;
	}
	
}