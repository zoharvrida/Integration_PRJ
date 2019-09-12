package bdsm.web.menu31302;


import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.interceptor.validation.SkipValidation;
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;

import bdsm.model.ScreenMsg;
import bdsm.model.SkhtPaidOffResp;
import bdsm.model.SkhtPrintPaid;
import bdsm.model.SkhtViewDataPelunasan;
import bdsm.util.ClassConverterUtil;
import bdsm.web.Constant;
import bdsm.web.ModelDrivenBaseContentAction;
import bdsm.web.ScreenMsgService;


/**
 *
 * @author v00022171
 */
@SuppressWarnings("serial")
public class Menu31302Action extends ModelDrivenBaseContentAction<Object> {
	private SkhtViewDataPelunasan svdp;
	private static final String SUCCESSMessage = "TXT_SKHT_SUCC";
	private static final String FAILEDMessage = "TXT_SKHT_FAIL";
	private static final String SUCCPrintMessage = "TXT_SKHT_PRINT_SUCC";
	private static final String FAILPrintMessage = "TXT_SKHT_PRINT_FAIL";
	private static final String FAILEDMessageInq = "TXT_SKHT_INQUIRY_FAIL";
	private static final String SUCCMessageInq = "TXT_SKHT_INQUIRY_SUCC";
	private String acctNoInq;
	private String acctNoTo;
	private ScreenMsg scrMsg;
	private String noPorsi;
	private String hajiType;
	private String state = "0";
	private String keys;
	private String balance;
	private String contentData;
	private String codFlg;
	
	
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
    public final String inquiryAcctNo() {
		this.getLogger().info("[Begin] inquiryAcctNo()");
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
	
	@SuppressWarnings("unchecked")
	private String inquiryAcctNo_() {
		this.getLogger().info("Account Number Inqacct: " + getAcctNoInq());
		this.getLogger().info("Nomor Porsi : " + this.getNoPorsi());
		
		Map<String, String> requestMap = new HashMap<String, String>();
		requestMap.put("methodName", "retrievePaidOffViewData");
		requestMap.put("useTransaction", "true");
		requestMap.put("sktPad.portionNo", this.getNoPorsi());
		requestMap.put("sktPad.acctNo", this.getAcctNoInq());
		requestMap.put("sktPad.bpsBranchCode", this.session.get(Constant.C_CODEBRANCH).toString());
		requestMap.put("sktResp.acctNo", this.getAcctNoInq());
		requestMap.put("strData.mode", this.getStrData().get("mode"));	  
		requestMap.put("svdp.hajiType", this.hajiType);
		String jsonStatus;
		String errorCode;
		
		try {
			Map<String, ? extends Object> resultMap = this.callHostHTTPRequest("SKHT", "callMethod", requestMap);
			this.getLogger().debug("Map 1: " + resultMap);
			jsonStatus = resultMap.get("jsonStatus").toString();
			errorCode = (String) resultMap.get("errorCode");
			
			if ((ERROR.equals(jsonStatus)) && (errorCode != null)) {
				if (errorCode.startsWith("skht."))
					errorCode = "31300." + errorCode;
				this.getLogger().debug("Error in Web : " + errorCode);
				this.getServletRequest().setAttribute("SKHT_MESSAGE", this.getText(errorCode));
			}
			else {
				this.svdp = new SkhtViewDataPelunasan();
				Map<String, ? extends Object> viewData = (Map<String, Object>) resultMap.get("svdp");
				this.getLogger().debug("viewData pelunasan: " + viewData);
				
				this.getLogger().info("Pekerjaan: " + this.svdp.getOccupation());
				
				if (resultMap.get("objData") != null)
					this.setObjData((Map<String, Object>) resultMap.get("objData"));
				if (resultMap.get("strData") != null)
					this.setStrData((Map<String, String>) resultMap.get("strData"));
				
				ClassConverterUtil.MapToClass(viewData, this.svdp);
				this.getLogger().info("SVDP: " + this.svdp);
				
				this.setContentData(JSONUtil.serialize(this.svdp));
				this.putToStrData("strData", JSONUtil.serialize(this.getStrData()));
				this.session.put("SKHT", "NEW");
			}
			this.state = "1";
			
		}
		catch (Exception er) {
			this.getLogger().error(er, er);
			this.session.put("SKHT_Inqury", "FAILED");
			setScrMsg(getScreenMsgresp(getFAILEDMessageInq()));
			return ERROR;
		}
		this.session.put("SKHT", "INQUIRY");
		
		this.getServletRequest().setAttribute("resultType", "acctNo");
		return "result";
	}
	
    /**
     * 
     * @return
     */
    public final String inquiryBalance() {
		this.getLogger().info("[Begin] inquiryBalance()");
		try {
			if (isValidSession())
				return this.inquiryBalNo_();
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
		this.getLogger().info("Account Number : " + this.getAcctNoInq());
		this.getLogger().info("codOrgBrnLunass : " + this.session.get(Constant.C_CODEBRANCH).toString());
		this.getLogger().info("BalanceLunassss : " + this.getBalance());
		this.getLogger().info("CODFLG : " + this.getCodFlg());
		
		Map<String, String> requestMap = this.createParameterMapFromHTTPRequest();
		if ("P".equalsIgnoreCase(this.getCodFlg().toString()))
		   this.inquiryAcctNo(); 
		
		requestMap.put("methodName", "compareBalance");
		requestMap.put("acctNo", this.getAcctNoInq());
		requestMap.put("codOrgBrn", this.session.get(Constant.C_CODEBRANCH).toString());
		requestMap.put("balance", this.getBalance());
		requestMap.put("codFlg", this.getCodFlg());
		
		try {
			Map<String, ? extends Object> resultMap = this.callHostHTTPRequest("SKHT", "callMethod", requestMap);
			this.getLogger().debug("Map: " + resultMap);
			
			this.setBalance(resultMap.get("keys").toString());
			this.getLogger().debug("Balance: " + this.getBalance());
			
			if (this.getBalance().equals("VALIDATE")) {
				this.scrMsg = getScreenMsgresp(getSUCCMessageInq());
				this.getLogger().debug("Balance validated");
				this.contentData = (JSONUtil.serialize(this.svdp));
			}
			else
				this.scrMsg = getScreenMsgresp(getFAILEDMessageInq());
			
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
    @SuppressWarnings("unchecked")
	@Override
	public String doAdd() {
		Map<String, String> requestMap = this.createParameterMapFromHTTPRequest();	   
		requestMap.put("methodName", "postingPaidOff");
		requestMap.put("useTransaction", "true");
		requestMap.put("acctNo", svdp.getAcctNoInq());
		requestMap.put("strData.idMaintainedBy", this.session.get(Constant.C_IDUSER).toString());
		requestMap.put("codOrgBrn", this.session.get(Constant.C_CODEBRANCH).toString());
		this.session.put("SKHT", "POST");
		
		this.getLogger().debug("Account Number Posting: " + this.getAcctNoInq());
		this.getLogger().debug("check SVDPPPP before: " + this.svdp);
		this.getLogger().debug("requestMap: " + requestMap);
		
		try {
			Map<String, ? extends Object> resultMap = this.callHostHTTPRequest("SKHT", "callMethod", requestMap);
			this.getLogger().debug("Map Pelunasan 1: " + resultMap);
			
			Map<String, ? extends Object> skhtPaidResp = ((Map<String, Object>) resultMap.get("paidresp"));
			String jsonStatus = resultMap.get("jsonStatus").toString();
			String errorCode = (String) resultMap.get("errorCode");
			
			if ((ERROR.equals(jsonStatus)) && (errorCode != null)) {
				if (errorCode.startsWith("skht."))
					errorCode = "31300." + errorCode;
				
				this.scrMsg = new ScreenMsg(this.getText(errorCode));
			}
			else {
				SkhtPaidOffResp paidresp = new SkhtPaidOffResp();
				String responseCode = null;
				try {
					ClassConverterUtil.MapToClass(skhtPaidResp, paidresp);
					this.getLogger().debug("Hasil Pelunasan 12: " + paidresp);
					responseCode = (String) skhtPaidResp.get("responseCode");
				}
				catch (Exception e) {
					this.getLogger().debug("Hasil In: " + e, e);
					this.getLogger().debug("Hasil aaaaa: " + skhtPaidResp.get("responseCode"));
				}
				this.getLogger().debug("Hasil Respon Code1: " + responseCode);
				this.session.put("SKHT", "CREATED");
				
				if ((responseCode != null) && (Integer.parseInt(responseCode) == 0)) {
					this.state = "2";
					this.scrMsg = this.getScreenMsgresp(getSUCCESSMessage());
				}
				else if (responseCode == null)
					this.scrMsg = this.getScreenMsgresp(getFAILEDMessage());
				else
					this.scrMsg = new ScreenMsg(paidresp.getResponseCode() + " " + paidresp.getResponseDesc());
			}
			
		}
		catch (Exception ex) {
			this.getLogger().error(ex, ex);
			this.scrMsg = this.getScreenMsgresp(getFAILEDMessage());
			this.getServletRequest().setAttribute("SKHT_MESSAGE", this.scrMsg.getMessage());
			
			return ERROR;
		}
		getLogger().info("Value Screen Message22222 " + this.scrMsg.toString());
		
		this.getServletRequest().setAttribute("SKHT_MESSAGE", this.scrMsg.getMessage());
		return SUCCESS;
	}
	
    /**
     * 
     * @param tag
     * @return
     */
    @SkipValidation
	@SuppressWarnings("rawtypes")
	public ScreenMsg getScreenMsgresp(String tag) {
		Map mapScreen = new HashMap();
		Map msgMap = new HashMap();
		ScreenMsg msg = new ScreenMsg();
		ScreenMsgService sms = new ScreenMsgService(this.getServletRequest(), session, this.getBdsmHost(), this.getTokenKey(), this.getTzToken());
		getLogger().info("[Begin] Detect Screen Message: " + tag);
		mapScreen = sms.getMessage(tag);
		if (mapScreen != null) {
			getLogger().debug("SKHT MAP: " + mapScreen);
			try {
				msgMap = (HashMap) mapScreen.get("model");
				ClassConverterUtil.MapToClass(msgMap, msg);
			} catch (Exception e) {
				getLogger().debug(e, e);
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
    public final String reqPrint() {
		getLogger().info("[Begin] reqPrint()");
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
			this.getLogger().info("[ End ] reqPrint()");
		}
	}
	
    /**
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
	public String reqPrint_() {
		Map<String, String> requestMap = this.createParameterMapFromHTTPRequest();
		requestMap.put("methodName", "printPaidOff");
		requestMap.put("useTransaction", "true");
		requestMap.put("acctNo", requestMap.get("reqPrint.acctNo"));
		requestMap.put("noPorsi", requestMap.get("reqPrint.noPorsi"));
		requestMap.put("HajiType", requestMap.get("reqPrint.hajiType"));
		requestMap.put("strData.idMaintainedBy", this.session.get(Constant.C_IDUSER).toString());
		requestMap.put("codOrgBrn", this.session.get(Constant.C_CODEBRANCH).toString());
		
		this.getLogger().info("Account Number: " + requestMap.get("acctNo"));
		this.getLogger().debug("maps print: " + requestMap);
		this.session.put("SKHT", "PRINTED");
		SkhtPrintPaid skpPrint = new SkhtPrintPaid();
		Map<String, String> strData;
		String responseCode = null;
		
		try {
			Map<String, ? extends Object> resultMap = this.callHostHTTPRequest("SKHT", "callMethod", requestMap);
			getLogger().debug("MapSave print: " + resultMap);
			
			String jsonStatus = resultMap.get("jsonStatus").toString();
			String errorCode = (String) resultMap.get("errorCode");
			
			if ((ERROR.equals(jsonStatus)) && (errorCode != null)) {
				if (errorCode.startsWith("skht."))
					errorCode = "31300." + errorCode;
				
				this.scrMsg = new ScreenMsg(this.getText(errorCode));
				this.contentData = "{}";
			}
			else {
				Map<String, ? extends Object> skhtPrintResp = ((Map<String, Object>) resultMap.get("skpPrint"));
				ClassConverterUtil.MapToClass(skhtPrintResp, skpPrint);
				
				responseCode = skpPrint.getResponseCode();
				try {
					this.contentData = JSONUtil.serialize(skpPrint);
					this.getLogger().debug("content_data print:" + this.contentData);
				}
				catch (JSONException jSONException) {
					this.getLogger().info("FAILED to Serialize: " + jSONException, jSONException);
				}
				
				strData = (Map<String, String>) resultMap.get("strData"); 
				if ((strData != null) && (strData.size() > 0))
					this.putToStrData("strData", JSONUtil.serialize(strData));
			}
		}
		catch (Exception ex) {
			this.getLogger().error(ex, ex);
			this.session.put("SKHT", "PRINTFAILED");
			this.scrMsg = this.getScreenMsgresp(getFAILPrintMessage());
			return ERROR;
		}
		
		Integer messageType = 2;
		if (responseCode == null) {
			if (this.scrMsg == null)
				this.scrMsg = this.getScreenMsgresp(FAILPrintMessage);
		}
		else if (Integer.valueOf(responseCode) == 0) {
			this.state = "3";
			this.scrMsg = this.getScreenMsgresp(SUCCPrintMessage);
			messageType = 3;
		}
		else
			this.scrMsg = new ScreenMsg(skpPrint.getResponseCode() + " " + skpPrint.getResponseDesc());
		
		this.setMessagePopup(messageType, this.scrMsg.getMessage());
		this.getServletRequest().setAttribute("resultType", "printAkhir");
		return "result";
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
    public SkhtViewDataPelunasan getSvdp() {
		return this.svdp;
	}
    /**
     * 
     * @param svdp
     */
    public void setSvdp(SkhtViewDataPelunasan svdp) {
		this.svdp = svdp;
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
    public String getKeys() {
		return this.keys;
	}
    /**
     * 
     * @param keys
     */
    public void setKeys(String keys) {
		this.keys = keys;
	}
	
    /**
     * 
     * @return
     */
    public String getAcctNoTo() {
		return this.acctNoTo;
	}
    /**
     * 
     * @param acctNoTo
     */
    public void setAcctNoTo(String acctNoTo) {
		this.acctNoTo = acctNoTo;
	}
	
    /**
     * 
     * @return
     */
    public String getNoPorsi() {
		return this.noPorsi;
	}
    /**
     * 
     * @param noPorsi
     */
    public void setNoPorsi(String noPorsi) {
		this.noPorsi = noPorsi;
	}
	
    /**
     * 
     * @return
     */
    public String getCodFlg() {
		return this.codFlg;
	}
    /**
     * 
     * @param codFlg
     */
    public void setCodFlg(String codFlg) {
		this.codFlg = codFlg;
	}
	
	
    /**
     * 
     * @return
     */
    public static String getSUCCESSMessage() {
		return SUCCESSMessage;
	}
	
    /**
     * 
     * @return
     */
    public static String getFAILEDMessage() {
		return FAILEDMessage;
	}
	
    /**
     * 
     * @return
     */
    public static String getSUCCPrintMessage() {
		return SUCCPrintMessage;
	}
    /**
     * 
     * @return
     */
    public static String getFAILPrintMessage() {
		return FAILPrintMessage;
	}
	
    /**
     * 
     * @return
     */
    public static String getFAILEDMessageInq() {
		return FAILEDMessageInq;
	}
	
    /**
     * 
     * @return
     */
    public static String getSUCCMessageInq() {
		return SUCCMessageInq;
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

