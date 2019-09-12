/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.web.menu29402;

import bdsm.fcr.model.PmTxnLog;
import bdsm.model.FixQXtract;
import bdsm.model.MCRCurrencyRate;
import bdsm.model.MCRTrxTable;
import bdsm.web.Constant;
import bdsm.web.DownloadContentAction;
import bdsm.web.ModelDrivenBaseContentAction;
import com.opensymphony.xwork2.ActionSupport;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import org.apache.struts2.json.JSONException;

/**
 *
 * @author v00022309
 */
public class Menu29402Action extends ModelDrivenBaseContentAction<Object> {

    private PmTxnLog pmTxnLog;
    private MCRCurrencyRate mcrCurrencyRate;
    private MCRTrxTable mcrData;
    private String refNetworkNo;
    private String nameCurrency;
    private String currencyCode;
    private BigDecimal sellRate;
    private String codAccNo;
    private Integer amtTxnLcy;
    private Integer amtTxnTcy;
    private Integer amtTxnAcy;
    private BigDecimal destRate;
    private String destAmount;
    private String saveVal;
    private String flag;
    private String fred;
    private String codAcctTitle;
    
    /**
     * 
     * @return
     */
    @Override
    public String exec() {
        DownloadContentAction dc = new DownloadContentAction(getServletRequest(), session, getBdsmHost(), getTokenKey(), getTzToken(), "29",
                "IDSCHEDULER", "ADMIN", "ADMIN", "TEMPLATE1", "cdBranch", "dtBusiness", "NAMTEMPLATE",
                "Calculator After", new FixQXtract(), "DTMrEQUEST");
        dc.exec();
        return "downloadpdf";
    }

    /**
     * 
     * @return
     */
    public final String inquiryRef() {
        getLogger().info("[Begin] inquiryRef");
        try {
            if (isValidSession()) {
                return this.inquiryRef_();
            } else {
                return logout();
            }
        } catch (Throwable e) {
            getLogger().fatal(e, e);
            return ActionSupport.ERROR;
        } finally {
            getLogger().info("[ End ] inquiryRef()");
        }
    }

    private String inquiryRef_() {
        getLogger().info("Ref Number : " + getRefNetworkNo());

        Map<String, String> requestMap = new HashMap<String, String>();
        requestMap.put("methodName", "retriveValueTrx");
        requestMap.put("refNetworkNo", this.getRefNetworkNo().toString());
        requestMap.put("codOrgBrn", this.session.get(Constant.C_CODEBRANCH).toString());
        try {
            Map<String, ? extends Object> resultMap = this.callHostHTTPRequest("MCRCalcUSDequivalent", "callMethod", requestMap);
            getLogger().debug("Map : " + resultMap);

            this.pmTxnLog = new PmTxnLog();
            copyMapValueToObject((Map<String, ? extends Object>) resultMap.get("pmtxn"), this.pmTxnLog);
            try {
                this.nameCurrency = resultMap.get("nameCurrency").toString();
                this.flag = resultMap.get("flag").toString();
                this.currencyCode = (resultMap.get("currencyCode") == null)? "": resultMap.get("currencyCode").toString();
                this.destAmount = (resultMap.get("destAmount") == null)? "": resultMap.get("destAmount").toString();
                this.codAcctTitle = (resultMap.get("codAcctTitle") == null)? "": resultMap.get("codAcctTitle").toString();
                BigDecimal sellret = new BigDecimal((resultMap.get("sellRate") == null)? "": resultMap.get("sellRate").toString()); //condition one line
                this.destRate = sellret;
            } catch (Exception er) {
                //this.nameCurrency = "";
                //this.flag = "";
                this.currencyCode = "";
                this.destAmount = "";
                this.destRate = null;
            }

        } catch (JSONException je) {
            this.getLogger().error(je, je);
            return ERROR;
        } catch (Exception ex) {
            this.getLogger().error(ex, ex);
            return ERROR;
        }

        return "trxRef";
    }

    /**
     * 
     * @return
     */
    public final String retriveRateDestination() {
        getLogger().info("[Begin] retriveRateDestination()");
        try {
            if (isValidSession()) {
                return this.retriveRateDestination_();
            } else {
                return logout();
            }
        } catch (Throwable e) {
            getLogger().fatal(e, e);
            return ActionSupport.ERROR;
        } finally {
            getLogger().info("[ End ] retriveRateDestination()");
        }
    }

    private String retriveRateDestination_() {
        getLogger().debug("refNetworkNo : " + getRefNetworkNo());
        getLogger().debug("Currency Code : " + getCurrencyCode());

        Map<String, String> requestMap = new HashMap<String, String>();
        requestMap.put("methodName", "retriveValueDestinationRate");
        requestMap.put("currencyCode", this.getCurrencyCode().toString());
        requestMap.put("refNetworkNo", this.getRefNetworkNo().toString());

        try {
            Map<String, ? extends Object> resultMap = this.callHostHTTPRequest("MCRCalcUSDequivalent", "callMethod", requestMap);
            getLogger().debug("Map : " + resultMap);
            try{
                this.mcrCurrencyRate = new MCRCurrencyRate();
                BigDecimal selret = new BigDecimal((resultMap.get("sellRate") == null)? "": resultMap.get("sellRate").toString());
                getLogger().info("sel : " + selret.toString());
                this.sellRate = selret;
            }catch(Exception er){
                this.sellRate = null;
            }
        } catch (Exception ex) {
            this.getLogger().error(ex, ex);
            return ERROR;
        }
        return "destRate";
    }

    /**
     * 
     * @return
     */
    @Override
    public String doAdd() {
        getLogger().debug("ref network no : " + getRefNetworkNo());
        getLogger().debug("acc no : " + getCodAccNo());
        getLogger().debug("origial currency : " + getNameCurrency());
        getLogger().debug("Amount Acy : " + getAmtTxnAcy());
        getLogger().debug("Amount Tcy : " + getAmtTxnTcy());
        getLogger().debug("Currency Destination : " + getCurrencyCode());
        getLogger().debug("Destination Amount : " + getDestAmount());
        getLogger().debug("Destination Rate : " + getSellRate());
        getLogger().debug("Acct Tittle :" + getCodAcctTitle());

        getLogger().debug("flagRed : " + getFred());
        if ("1".equalsIgnoreCase(getFred())) {        
            return exec();
        } else {
            Map<String, String> requestMap = this.createParameterMapFromHTTPRequest();
            requestMap.put("methodName", "saveValueCalcAfter");
            requestMap.put("useTransaction", "true");
            requestMap.put("codOrgBrn", this.session.get(Constant.C_CODEBRANCH).toString());
            requestMap.put("idUser", this.session.get(Constant.C_IDUSER).toString());

            try {
                Map<String, ? extends Object> resultMap = this.callHostHTTPRequest("MCRCalcUSDequivalent", "callMethod", requestMap);
                getLogger().debug("MapSave : " + resultMap);

                this.addActionMessage(this.getText("success.0"));

            } catch (Exception ex) {
                this.getLogger().error(ex, ex);
                return ERROR;
            }
            return SUCCESS;
        }



        
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

//    public String rtvRate(){
//        Map mapModel = new HashMap();
//        List Model = new ArrayList();
//        Map maps = new HashMap();
//        getLogger().info("ccyCode :>" + getCurrencyCode());
//        
//        MCRCalcBeforeServices mcrbefore = new MCRCalcBeforeServices(session, this.getBdsmHost(), this.getTokenKey(), this.getTzToken(), this.getServletRequest());
//        mapModel = mcrbefore.getRateDestination(getCurrencyCode());
//        
//        getLogger().info("mapModel :>" + mapModel);
//        
//        Model = (List) mapModel.get("modelList");
//        getLogger().info("model :>" + Model);
//        try {
//            maps = (Map) Model.get(0);
//            this.setSellRate(maps.get("0").toString());
//        } catch (Exception e) {
//            getLogger().info("sell rate :" + getSellRate());
//        }
//
//        
//        return "rateDes";
//    }
    /**
     * @return the trxRefNo
     */
    public String getRefNetworkNo() {
        return refNetworkNo;
    }

    /**
     * @param refNetworkNo 
     */
    public void setRefNetworkNo(String refNetworkNo) {
        this.refNetworkNo = refNetworkNo;
    }

    /**
     * @return the pmTxnLog
     */
    public PmTxnLog getPmTxnLog() {
        return pmTxnLog;
    }

    /**
     * @param pmTxnLog the pmTxnLog to set
     */
    public void setPmTxnLog(PmTxnLog pmTxnLog) {
        this.pmTxnLog = pmTxnLog;
    }

    /**
     * @return the nameCurrency
     */
    public String getNameCurrency() {
        return nameCurrency;
    }

    /**
     * @param nameCurrency the nameCurrency to set
     */
    public void setNameCurrency(String nameCurrency) {
        this.nameCurrency = nameCurrency;
    }

    /**
     * 
     * @return
     */
    public String getCurrencyCode() {
        return currencyCode;
    }

    /**
     * 
     * @param currencyCode
     */
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    /**
     * 
     * @return
     */
    public BigDecimal getSellRate() {
        return sellRate;
    }

    /**
     * 
     * @param sellRate
     */
    public void setSellRate(BigDecimal sellRate) {
        this.sellRate = sellRate;
    }

    /**
     * 
     * @return
     */
    public Object getModel() {
        return null;
    }

    /**
     * @return the codAccNo
     */
    public String getCodAccNo() {
        return codAccNo;
    }

    /**
     * @param codAccNo the codAccNo to set
     */
    public void setCodAccNo(String codAccNo) {
        this.codAccNo = codAccNo;
    }

    /**
     * @return the amtTxnLcy
     */
    public Integer getAmtTxnAcy() {
        return amtTxnAcy;
    }

    /**
     * @param amtTxnAcy 
     */
    public void setAmtTxnAcy(Integer amtTxnAcy) {
        this.amtTxnAcy = amtTxnAcy;
    }

    /**
     * @return the amtTxnTcy
     */
    public Integer getAmtTxnTcy() {
        return amtTxnTcy;
    }

    /**
     * @param amtTxnTcy the amtTxnTcy to set
     */
    public void setAmtTxnTcy(Integer amtTxnTcy) {
        this.amtTxnTcy = amtTxnTcy;
    }

    /**
     * @return the destAmount
     */
    public String getDestAmount() {
        return destAmount;
    }

    /**
     * @param destAmount the destAmount to set
     */
    public void setDestAmount(String destAmount) {
        this.destAmount = destAmount;
    }

    /**
     * @return the mcrtrxtable
     */
    public MCRTrxTable getMcrData() {
        return mcrData;
    }

    /**
     * @param mcrData 
     */
    public void setMcrData(MCRTrxTable mcrData) {
        this.mcrData = mcrData;
    }

    /**
     * @return the saveVal
     */
    public String getSaveVal() {
        return saveVal;
    }

    /**
     * @param saveVal the saveVal to set
     */
    public void setSaveVal(String saveVal) {
        this.saveVal = saveVal;
    }

    /**
     * @return the flag
     */
    public String getFlag() {
        return flag;
    }

    /**
     * @param flag the flag to set
     */
    public void setFlag(String flag) {
        this.flag = flag;
    }

    /**
     * @return the destRate
     */
    public BigDecimal getDestRate() {
        return destRate;
    }

    /**
     * @param destRate the destRate to set
     */
    public void setDestRate(BigDecimal destRate) {
        this.destRate = destRate;
    }

    /**
     * @return the amtTxnLcy
     */
    public Integer getAmtTxnLcy() {
        return amtTxnLcy;
    }

    /**
     * @param amtTxnLcy the amtTxnLcy to set
     */
    public void setAmtTxnLcy(Integer amtTxnLcy) {
        this.amtTxnLcy = amtTxnLcy;
    }

    /**
     * @return the fred
     */
    public String getFred() {
        return fred;
    }

    /**
     * @param fred the fred to set
     */
    public void setFred(String fred) {
        this.fred = fred;
    }

    /**
     * @return the codAcctTitle
     */
    public String getCodAcctTitle() {
        return codAcctTitle;
    }

    /**
     * @param codAcctTitle the codAcctTitle to set
     */
    public void setCodAcctTitle(String codAcctTitle) {
        this.codAcctTitle = codAcctTitle;
    }

    
}
