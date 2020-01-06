package bdsm.web.menu32101;

import bdsm.model.ETaxBillingInfo;
import bdsm.model.ETaxInquiryBillingResp;
import bdsm.model.EtaxPrint;
import bdsm.util.BdsmUtil;
import java.util.Map;

import org.apache.struts2.json.JSONUtil;

import bdsm.util.ClassConverterUtil;
import bdsm.web.Constant;
import bdsm.web.ModelDrivenBaseContentAction;
import static com.opensymphony.xwork2.Action.ERROR;
import static com.opensymphony.xwork2.Action.INPUT;
import static com.opensymphony.xwork2.Action.SUCCESS;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 *
 * @author v00022171
 */
@SuppressWarnings("serial")
public class Menu32101Action extends ModelDrivenBaseContentAction<Object> {

    private static final String SUCCESSMessage = "TXT_ETAX_SUCC";
    private static final String FAILEDMessage = "TXT_ETAX_FAIL";
    private static final String SUCCPrintMessage = "TXT_ETAX_PRINT_SUCC";
    private static final String FAILPrintMessage = "TXT_ETAX_PRINT_FAIL";
    private static final String FAILEDMessageInq = "TXT_ETAX_INQUIRY_FAIL";
    private static final String SUCCMessageInq = "TXT_ETAX_INQUIRY_SUCC";
    private String state;
    private String contentData;

    private String billingId;
    private int paymentType;
    private String amount;
    private ETaxInquiryBillingResp etax;
    private String noAccount;
    private String typeAccount;
    private String reinqStatus;

    public Menu32101Action() {
    }

    @SuppressWarnings("unchecked")
    @Override
    public String doInput() {
        getLogger().info("[Begin] doInput()");
        try {
            setState("0");
        } finally {
            this.getLogger().info("[ End ] doInput()");
        }
        return INPUT;
    }

    /**
     * Inquiry Billing Action
     *
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

    /**
     * Inquiry Account Action
     *
     * @return
     */
    public final String inquiryAccount() {
        getLogger().info("[Begin] inquiryAccount()");
        try {
            if (isValidSession()) {
                this.getLogger().info("Account No : " + this.getNoAccount());
                this.getLogger().info("Account Type : " + this.getTypeAccount());
                if("GL".equalsIgnoreCase(this.getTypeAccount())) {
                    return this.inquiryGLAccount_();
                } else if("CASA".equalsIgnoreCase(this.getTypeAccount())) {
                    return this.inquiryCASAAccount_();
                } else {
                    this.getLogger().warn("Invalid Account Type!");
                    return ERROR;
                }
            } else {
                return logout();
            }
        } catch (Throwable e) {
            this.getLogger().fatal(e, e);
            return ERROR;
        } finally {
            this.getLogger().info("[ End ] inquiryAccount()");
        }
    }

    private String inquiryBilling_() {
        this.getLogger().info("ID Billing : " + this.getBillingId());
        this.getLogger().info("Payment Type : " + this.getPaymentType());

        Map<String, String> requestMap = this.createParameterMapFromHTTPRequest();
        requestMap.put("methodName", "inquiryBilling");
        requestMap.put("billingId", this.getBillingId());
        requestMap.put("paymentType", String.valueOf(this.paymentType));
        requestMap.put("codeBranchGl", this.session.get(Constant.C_CODEBRANCH).toString());

        try {
            Map<String, ? extends Object> resultMap = this.callHostHTTPRequest("ETAX", "callMethod", requestMap);
            this.getLogger().debug("Result Map: " + resultMap);
            Map viewData = (Map) resultMap.get("etax");
            if(viewData == null) {
                viewData = new HashMap();
            }
            Map billingInfo = null;
            if (viewData.containsKey("billingInfo")) {
                billingInfo = (Map) viewData.get("billingInfo");
            }
            Map objData = (Map) resultMap.get("objData");
            this.getLogger().debug("viewData: " + viewData);

            this.etax = new ETaxInquiryBillingResp();
            ClassConverterUtil.MapToClass(viewData, this.etax);
            if (billingInfo != null) {
                ETaxBillingInfo info = new ETaxBillingInfo();
                ClassConverterUtil.MapToClass(billingInfo, info);
                this.etax.setBillingInfo(info);
            }
            etax.setExchangeRate(1);
            
            BigDecimal amount = etax.getAmount();
            double rate = etax.getExchangeRate();
            if(amount != null) {
                BigDecimal amountLCE = amount.multiply(BigDecimal.valueOf(rate));
                etax.setNominalLCE(amountLCE);
                etax.setCreditNominalLCE(amountLCE);
            }
            if(objData.containsKey("kppnAccountNo")) {
                this.etax.setKppnAccountNo((String) objData.get("kppnAccountNo"));
            }
            if(objData.containsKey("kppnAccountCcyCode")) {
                this.etax.setKppnAccountCcyCode((Long) objData.get("kppnAccountCcyCode"));
            }
            if(objData.containsKey("kppnAccountCcyName")) {
                this.etax.setKppnAccountCcyName((String) objData.get("kppnAccountCcyName"));
            }
            if(objData.containsKey("kppnAccountName")) {
                this.etax.setKppnAccountName((String) objData.get("kppnAccountName"));
            }
            this.etax.setCashBranchGL(String.valueOf(session.get("cdBranch")));
            
            //setAmount(etax.getAmount());
            this.getLogger().debug("etax : " + this.etax);
            this.getLogger().debug("etax response time: " + this.etax.getResponseTimeString());
            this.setContentData(JSONUtil.serialize(this.etax));
        } catch (Exception er) {
            this.getLogger().debug("Error Inquiry Billing: " + er, er);
        }
        return SUCCESS;
    }

    private String inquiryCASAAccount_() {
        Map<String, String> requestMap = this.createParameterMapFromHTTPRequest();
        requestMap.put("methodName", "get");
        requestMap.put("model.compositeId.codAcctNo", this.getNoAccount());
        requestMap.put("model.compositeId.flgMntStatus", "A");
        requestMap.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));
        try {
            Map<String, ? extends Object> resultMap = this.callHostHTTPRequest("fcrChAcctMast", "callMethod", requestMap);
            this.getLogger().debug("Result Map: " + resultMap);
            Map viewData = (Map) resultMap.get("model");
            if(viewData == null) {
                viewData = new HashMap();
            }
            viewData.put("type", this.getTypeAccount());
            this.getLogger().debug("viewData: " + viewData);
            this.setContentData(JSONUtil.serialize(viewData));
        } catch (Exception er) {
            this.getLogger().debug("Error Inquiry CASA Account: " + er, er);
        }
        return "inquiryAcct";
    }

    private String inquiryGLAccount_() {
        Map<String, String> requestMap = this.createParameterMapFromHTTPRequest();
        requestMap.put("methodName", "inquiryGLAccount");
        requestMap.put("glNo", getNoAccount());
        requestMap.put("branchCode", "7101");
        requestMap.put("currencyCode", "360");
        requestMap.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));
        try {
            Map<String, ? extends Object> resultMap = this.callHostHTTPRequest("ETAX", "callMethod", requestMap);
            this.getLogger().debug("Result Map: " + resultMap);
            Map viewData = (Map) resultMap.get("glMaster");
            if(viewData == null) {
                viewData = new HashMap();
            }
            viewData.put("type", this.getTypeAccount());
            this.getLogger().debug("viewData: " + viewData);
            this.setContentData(JSONUtil.serialize(viewData));
        } catch (Exception er) {
            this.getLogger().debug("Error Inquiry GL Account: " + er, er);
        }
        return "inquiryAcct";
    }
    
    /**
     * ReInquiry Billing Action
     *
     * @return
     */
    public final String reInquiryBilling() {
        getLogger().info("[Begin] reInquiryBilling()");
        try {
            if (isValidSession()) {
                return this.reInquiryBilling_();
            } else {
                return logout();
            }
        } catch (Throwable e) {
            this.getLogger().fatal(e, e);
            return ERROR;
        } finally {
            this.getLogger().info("[ End ] reInquiryBilling()");
        }
    }
    
    private String reInquiryBilling_() {
        this.getLogger().info("ID Billing : " + this.getBillingId());

        Map<String, String> requestMap = this.createParameterMapFromHTTPRequest();
        requestMap.put("methodName", "reInquiryBilling");
        requestMap.put("billingId", this.getBillingId());
        requestMap.put("codeBranchGl", this.session.get(Constant.C_CODEBRANCH).toString());

        try {
            Map<String, ? extends Object> resultMap = this.callHostHTTPRequest("ETAX", "callMethod", requestMap);
            this.getLogger().debug("Result Map: " + resultMap);
            Map viewData = (Map) resultMap.get("reInquiryResp");
            if(viewData == null) {
                viewData = new HashMap();
            }
            Map billingInfo = null;
            if (viewData.containsKey("billingInfo")) {
                billingInfo = (Map) viewData.get("billingInfo");
            }
            
            Map printInfo = null;
            if (viewData.containsKey("etaxPrint")) {
                printInfo = (Map) viewData.get("etaxPrint");
            }
            Map objData = (Map) resultMap.get("objData");
            this.getLogger().debug("viewData: " + viewData);

            this.etax = new ETaxInquiryBillingResp();
            ClassConverterUtil.MapToClass(viewData, this.etax);
            
            if (billingInfo != null) {
                ETaxBillingInfo info = new ETaxBillingInfo();
                ClassConverterUtil.MapToClass(billingInfo, info);
                this.etax.setBillingInfo(info);
            }
            if (printInfo != null) {
                EtaxPrint info = new EtaxPrint();
                ClassConverterUtil.MapToClass(printInfo, info);
                this.etax.setEtaxPrint(info);
            }
            etax.setExchangeRate(1);
            
            BigDecimal amount = etax.getAmount();
            double rate = etax.getExchangeRate();
            if(amount != null) {
                BigDecimal amountLCE = amount.multiply(BigDecimal.valueOf(rate));
                etax.setNominalLCE(amountLCE);
                etax.setCreditNominalLCE(amountLCE);
            }
            if(objData.containsKey("kppnAccountNo")) {
                this.etax.setKppnAccountNo((String) objData.get("kppnAccountNo"));
            }
            if(objData.containsKey("kppnAccountCcyCode")) {
                this.etax.setKppnAccountCcyCode((Long) objData.get("kppnAccountCcyCode"));
            }
            if(objData.containsKey("kppnAccountCcyName")) {
                this.etax.setKppnAccountCcyName((String) objData.get("kppnAccountCcyName"));
            }
            if(objData.containsKey("kppnAccountName")) {
                this.etax.setKppnAccountName((String) objData.get("kppnAccountName"));
            }
            this.etax.setCashBranchGL(String.valueOf(session.get("cdBranch")));
            
            if(this.etax.getResponseDesc().equalsIgnoreCase("SUCCESS")){
                setReinqStatus("1");
            }else{
                setReinqStatus("0");
            }
            //setAmount(etax.getAmount());
            this.getLogger().debug("etax : " + this.etax);
            this.getLogger().debug("etax response time: " + this.etax.getResponseTimeString());
            this.setContentData(JSONUtil.serialize(this.etax));
        } catch (Exception er) {
            this.getLogger().debug("Error ReInquiry Billing: " + er, er);
        }
        return "reinquiry";
    }
    
    @Override
    public String exec() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @SuppressWarnings("unchecked")
    @Override
    public String doAdd() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String doEdit() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String doDelete() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getState() {
        getLogger().debug("GET STATE: " + this.state);
        return this.state;
    }

    public void setState(String state) {
        getLogger().debug("SET STATE: " + state);
        this.state = state;
    }

    public String getContentData() {
        return this.contentData;
    }

    public void setContentData(String contentData) {
        this.contentData = contentData;
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

    public String getAmount() {
        getLogger().debug("GET AMOUNT: " + amount);
        return amount;
    }

    public void setAmount(String amount) {
        getLogger().debug("SET AMOUNT: " + amount);
        this.amount = amount;
    }
    private List _favouriteCities;

    public List getDefaultFavouriteCities() {
        List<String> list = new ArrayList<String>();
        list.add("boston");
        list.add("rome");
        return list;
    }

    public Map getAvailableCities() {
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put("boston", "Boston");
        map.put("new york", "New York");
        map.put("london", "London");
        map.put("rome", "Rome");
        return map;
    }

    public List getFavouriteCities() {
        return _favouriteCities;
    }

    public void setFavouriteCities(List favouriteCities) {
        this._favouriteCities = favouriteCities;
    }

    public String getNoAccount() {
        return noAccount;
    }

    public void setNoAccount(String noAccount) {
        this.noAccount = noAccount;
    }

    public String getTypeAccount() {
        return typeAccount;
    }

    public void setTypeAccount(String typeAccount) {
        this.typeAccount = typeAccount;
    }

    /**
     * @return the reinqStatus
     */
    public String getReinqStatus() {
        return reinqStatus;
    }

    /**
     * @param reinqStatus the reinqStatus to set
     */
    public void setReinqStatus(String reinqStatus) {
        this.reinqStatus = reinqStatus;
    }
    

}
