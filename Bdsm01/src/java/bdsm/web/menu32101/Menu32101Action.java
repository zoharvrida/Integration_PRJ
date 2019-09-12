package bdsm.web.menu32101;

import bdsm.model.ETaxBillingInfo;
import bdsm.model.ETaxInquiryBillingResp;
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
import static com.opensymphony.xwork2.Action.ERROR;
import static com.opensymphony.xwork2.Action.INPUT;
import static com.opensymphony.xwork2.Action.SUCCESS;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author v00022171
 */
@SuppressWarnings("serial")
public class Menu32101Action extends ModelDrivenBaseContentAction<Object> {

    private SkhtViewDataPelunasan svdp;
    private static final String SUCCESSMessage = "TXT_ETAX_SUCC";
    private static final String FAILEDMessage = "TXT_ETAX_FAIL";
    private static final String SUCCPrintMessage = "TXT_ETAX_PRINT_SUCC";
    private static final String FAILPrintMessage = "TXT_ETAX_PRINT_FAIL";
    private static final String FAILEDMessageInq = "TXT_ETAX_INQUIRY_FAIL";
    private static final String SUCCMessageInq = "TXT_ETAX_INQUIRY_SUCC";
    private String acctNoInq;
    private String acctNoTo;
    private ScreenMsg scrMsg;
    private String noPorsi;
    private String hajiType;
    private String state;
    private String keys;
    private String balance;
    private String contentData;
    private String codFlg;
    
    private String billingId;
    private int paymentType;
    private BigDecimal amount;
    /*
    // ETax Begin
    private String npwp;
    private String userId;
    private String branchCode;
    private String wpName;
    private String currency;
    private Date trxTime;
    private BigDecimal nominal;
    private String wpAddress;
    // default - empty
    private ETaxBillingInfo billingInfo;
    // ETax End
    */
    private ETaxInquiryBillingResp etax;

    public Menu32101Action() {
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public String doInput() {
        /*
        Map<String, String> requestMap = new HashMap<String, String>();
        requestMap.put("methodName", "checkCutOff");

        try {
            Map<String, ? extends Object> resultMap = this.callHostHTTPRequest("SKHT", "callMethod", requestMap);
            Map<String, ? extends Object> objData = (Map<String, ? extends Object>) resultMap.get("objData");

            if (objData.get("cutoff") != null) {
                this.getServletRequest().setAttribute("resultType", "cutoff");
                return "result";
            }
        } catch (Exception ex) {
            this.getLogger().error(ex, ex);
            return ERROR;
        }*/
        ETaxBillingInfo _info = new ETaxBillingInfo();
        _info.setBillingId("abc");
        //setBillingInfo(_info);

        return INPUT;
    }

    /**
     * Inquiry Billing Action
     * @return 
     */
    public final String inquiryBilling() {
        getLogger().info("[Begin] inquiryBilling()");
        try {
            if (isValidSession()) {
                return this.inquiryBilling_();
            } else {
                return logout();
            }
        } catch (Throwable e) {
            this.getLogger().fatal(e, e);
            return ERROR;
        } finally {
            this.getLogger().info("[ End ] inquiryBilling()");
        }
    }

    private String inquiryBilling_() {
        this.getLogger().info("ID Billing : " + this.getBillingId());
        this.getLogger().info("Payment Type : " + this.getPaymentType());
        
        Map<String, String> requestMap = this.createParameterMapFromHTTPRequest();
        requestMap.put("methodName", "inquiryBilling");
        requestMap.put("billingId", this.getBillingId());
        requestMap.put("paymentType", String.valueOf(this.paymentType));

        try {
            Map<String, ? extends Object> resultMap = this.callHostHTTPRequest("ETAX", "callMethod", requestMap);
            this.getLogger().debug("Result Map: " + resultMap);
            Map viewData = (Map) resultMap.get("inquiryResp");
            Map billingInfo = null;
            if(viewData.containsKey("billingInfo")) {
                billingInfo = (Map) viewData.get("billingInfo");
            }
            this.getLogger().debug("viewData: " + viewData);

            this.etax = new ETaxInquiryBillingResp();
            ClassConverterUtil.MapToClass(viewData, this.etax);
            if(billingInfo != null) {
                ETaxBillingInfo info = new ETaxBillingInfo();
                ClassConverterUtil.MapToClass(billingInfo, info);
                this.etax.setBillingInfo(info);
            }
            setAmount(etax.getAmount());
            this.getLogger().debug("etax : " + this.etax);
            this.getLogger().debug("etax response time: " + this.etax.getResponseTimeString());
            this.setContentData(JSONUtil.serialize(this.etax));

            setState("1");
        } catch (Exception er) {
            this.getLogger().debug("Error Inquiry Billing: " + er, er);
        }
        return SUCCESS;
    }

    @Override
    public String exec() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

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
                if (errorCode.startsWith("skht.")) {
                    errorCode = "31300." + errorCode;
                }

                this.scrMsg = new ScreenMsg(this.getText(errorCode));
            } else {
                SkhtPaidOffResp paidresp = new SkhtPaidOffResp();
                String responseCode = null;
                try {
                    ClassConverterUtil.MapToClass(skhtPaidResp, paidresp);
                    this.getLogger().debug("Hasil Pelunasan 12: " + paidresp);
                    responseCode = (String) skhtPaidResp.get("responseCode");
                } catch (Exception e) {
                    this.getLogger().debug("Hasil In: " + e, e);
                    this.getLogger().debug("Hasil aaaaa: " + skhtPaidResp.get("responseCode"));
                }
                this.getLogger().debug("Hasil Respon Code1: " + responseCode);
                this.session.put("SKHT", "CREATED");

                if ((responseCode != null) && (Integer.parseInt(responseCode) == 0)) {
                    this.state = "2";
                    this.scrMsg = this.getScreenMsgresp(getSUCCESSMessage());
                } else if (responseCode == null) {
                    this.scrMsg = this.getScreenMsgresp(getFAILEDMessage());
                } else {
                    this.scrMsg = new ScreenMsg(paidresp.getResponseCode() + " " + paidresp.getResponseDesc());
                }
            }

        } catch (Exception ex) {
            this.getLogger().error(ex, ex);
            this.scrMsg = this.getScreenMsgresp(getFAILEDMessage());
            this.getServletRequest().setAttribute("SKHT_MESSAGE", this.scrMsg.getMessage());

            return ERROR;
        }
        getLogger().info("Value Screen Message22222 " + this.scrMsg.toString());

        this.getServletRequest().setAttribute("SKHT_MESSAGE", this.scrMsg.getMessage());
        return SUCCESS;
    }

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

    public final String reqPrint() {
        getLogger().info("[Begin] reqPrint()");
        try {
            if (isValidSession()) {
                return this.reqPrint_();
            } else {
                return logout();
            }
        } catch (Throwable e) {
            this.getLogger().fatal(e, e);
            return ERROR;
        } finally {
            this.getLogger().info("[ End ] reqPrint()");
        }
    }

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
                if (errorCode.startsWith("skht.")) {
                    errorCode = "31300." + errorCode;
                }

                this.scrMsg = new ScreenMsg(this.getText(errorCode));
                this.contentData = "{}";
            } else {
                Map<String, ? extends Object> skhtPrintResp = ((Map<String, Object>) resultMap.get("skpPrint"));
                ClassConverterUtil.MapToClass(skhtPrintResp, skpPrint);

                responseCode = skpPrint.getResponseCode();
                try {
                    this.contentData = JSONUtil.serialize(skpPrint);
                    this.getLogger().debug("content_data print:" + this.contentData);
                } catch (JSONException jSONException) {
                    this.getLogger().info("FAILED to Serialize: " + jSONException, jSONException);
                }

                strData = (Map<String, String>) resultMap.get("strData");
                if ((strData != null) && (strData.size() > 0)) {
                    this.putToStrData("strData", JSONUtil.serialize(strData));
                }
            }
        } catch (Exception ex) {
            this.getLogger().error(ex, ex);
            this.session.put("SKHT", "PRINTFAILED");
            this.scrMsg = this.getScreenMsgresp(getFAILPrintMessage());
            return ERROR;
        }

        Integer messageType = 2;
        if (responseCode == null) {
            if (this.scrMsg == null) {
                this.scrMsg = this.getScreenMsgresp(FAILPrintMessage);
            }
        } else if (Integer.valueOf(responseCode) == 0) {
            this.state = "3";
            this.scrMsg = this.getScreenMsgresp(SUCCPrintMessage);
            messageType = 3;
        } else {
            this.scrMsg = new ScreenMsg(skpPrint.getResponseCode() + " " + skpPrint.getResponseDesc());
        }

        this.setMessagePopup(messageType, this.scrMsg.getMessage());
        this.getServletRequest().setAttribute("resultType", "printAkhir");
        return "result";
    }

    @Override
    public String doEdit() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String doDelete() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getAcctNoInq() {
        return this.acctNoInq;
    }

    public void setAcctNoInq(String acctNoInq) {
        this.acctNoInq = acctNoInq;
    }

    public SkhtViewDataPelunasan getSvdp() {
        return this.svdp;
    }

    public void setSvdp(SkhtViewDataPelunasan svdp) {
        this.svdp = svdp;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getHajiType() {
        return this.hajiType;
    }

    public void setHajiType(String hajiType) {
        this.hajiType = hajiType;
    }

    public String getContentData() {
        return this.contentData;
    }

    public void setContentData(String contentData) {
        this.contentData = contentData;
    }

    public String getBalance() {
        return this.balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public ScreenMsg getScrMsg() {
        return this.scrMsg;
    }

    public void setScrMsg(ScreenMsg scrMsg) {
        this.scrMsg = scrMsg;
    }

    public String getKeys() {
        return this.keys;
    }

    public void setKeys(String keys) {
        this.keys = keys;
    }

    public String getAcctNoTo() {
        return this.acctNoTo;
    }

    public void setAcctNoTo(String acctNoTo) {
        this.acctNoTo = acctNoTo;
    }

    public String getNoPorsi() {
        return this.noPorsi;
    }

    public void setNoPorsi(String noPorsi) {
        this.noPorsi = noPorsi;
    }

    public String getCodFlg() {
        return this.codFlg;
    }

    public void setCodFlg(String codFlg) {
        this.codFlg = codFlg;
    }

    public static String getSUCCESSMessage() {
        return SUCCESSMessage;
    }

    public static String getFAILEDMessage() {
        return FAILEDMessage;
    }

    public static String getSUCCPrintMessage() {
        return SUCCPrintMessage;
    }

    public static String getFAILPrintMessage() {
        return FAILPrintMessage;
    }

    public static String getFAILEDMessageInq() {
        return FAILEDMessageInq;
    }

    public static String getSUCCMessageInq() {
        return SUCCMessageInq;
    }

    @Override
    public Object getModel() {
        return null;
    }

    /**
     * @return the billingId
     */
    public String getBillingId() {
        return billingId;
    }

    /**
     * @param billingId the billingId to set
     */
    public void setBillingId(String billingId) {
        this.billingId = billingId;
    }

//    /**
//     * @return the npwp
//     */
//    public String getNpwp() {
//        return npwp;
//    }
//
//    /**
//     * @param npwp the npwp to set
//     */
//    public void setNpwp(String npwp) {
//        this.npwp = npwp;
//    }
//
//    /**
//     * @return the userId
//     */
//    public String getUserId() {
//        return userId;
//    }
//
//    /**
//     * @param userId the userId to set
//     */
//    public void setUserId(String userId) {
//        this.userId = userId;
//    }
//
//    /**
//     * @return the branchCode
//     */
//    public String getBranchCode() {
//        return branchCode;
//    }
//
//    /**
//     * @param branchCode the branchCode to set
//     */
//    public void setBranchCode(String branchCode) {
//        this.branchCode = branchCode;
//    }
//
//    /**
//     * @return the wpName
//     */
//    public String getWpName() {
//        return wpName;
//    }
//
//    /**
//     * @param wpName the wpName to set
//     */
//    public void setWpName(String wpName) {
//        this.wpName = wpName;
//    }
//
//    /**
//     * @return the currency
//     */
//    public String getCurrency() {
//        return currency;
//    }
//
//    /**
//     * @param currency the currency to set
//     */
//    public void setCurrency(String currency) {
//        this.currency = currency;
//    }
//
//    /**
//     * @return the trxTime
//     */
//    public Date getTrxTime() {
//        return trxTime;
//    }
//
//    /**
//     * @param trxTime the trxTime to set
//     */
//    public void setTrxTime(Date trxTime) {
//        this.trxTime = trxTime;
//    }
//
//    /**
//     * @return the nominal
//     */
//    public BigDecimal getNominal() {
//        return nominal;
//    }
//
//    /**
//     * @param nominal the nominal to set
//     */
//    public void setNominal(BigDecimal nominal) {
//        this.nominal = nominal;
//    }
//
//    /**
//     * @return the wpAddress
//     */
//    public String getWpAddress() {
//        return wpAddress;
//    }
//
//    /**
//     * @param wpAddress the wpAddress to set
//     */
//    public void setWpAddress(String wpAddress) {
//        this.wpAddress = wpAddress;
//    }

    public int getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(int paymentType) {
        this.paymentType = paymentType;
    }

    public ETaxInquiryBillingResp getEtax() {
        return etax;
    }

    public void setEtax(ETaxInquiryBillingResp etax) {
        this.etax = etax;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
//
//    public ETaxBillingInfo getBillingInfo() {
//        return billingInfo;
//    }
//
//    public void setBillingInfo(ETaxBillingInfo billingInfo) {
//        this.billingInfo = billingInfo;
//    }
    
    
}
