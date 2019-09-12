/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.web.menu20302;

import bdsm.model.ScreenMsg;
import bdsm.util.BdsmUtil;
import bdsm.util.ClassConverterUtil;
import bdsm.util.HttpUtil;
import bdsm.util.oracle.DateUtility;
import bdsm.web.BaseContentAction;
import bdsm.web.ScreenMsgService;
import com.opensymphony.xwork2.ActionSupport;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;
import org.apache.struts2.util.TokenHelper;

/**
 *
 * @author user
 */
public class Menu20302Action extends BaseContentAction {
    
    private static final String ACTION_GET_CUSTMAST = "ciCustmast_get.action";
    private static final String ACTION_INSERT = "mfcUdMaster_insert.action";
    private static final String ACTION_EDIT = "mfcUdMaster_update.action";
    private static final String ACTION_DELETE = "mfcUdMaster_delete.action";
    private static final String ACTION_GET_USDIDR_RATE = "fcrBaCcyRate_get.action";
    private static final String CALLMETHOD_ACTION = "fcyTxnManInput_callMethod.action";
    private static final String ACTION_GET_DATE = "doGetDatMidrate";
    private static final String NAM_MENU = "Underlying Document Maintenance";
    private Integer noCif;
    private String namCustFull;
    private String noUd;
    private String typeUD;
    private String txtTypeUD;
    private String payeeName;
    private String payeeCountry;
    private String tradingProduct;
    private String dtIssued;
    private String dtExpiry;
    private String ccyUd;
    private BigDecimal amtLimit;
    private String remarks;
    private Integer cdBranch;
    private String status;
    private BigDecimal ratUsdIdr;
    private BigDecimal ratFcyIdr;
    private BigDecimal usdIdrRate;
    private BigDecimal amtLimitUSD;
    private String idMaintainedBy;
    private String idMaintainedSpv;
    private String tagMessage;
    
    
    /**
     * 
     * @return
     */
    @SkipValidation
    public String inquiry() {
        getLogger().info("[Begin] inquiry()");
        
        try {
            if (isValidSession()) {
                Map<String, String> map = new HashMap<String, String>();
                String result;
                HashMap resultMap;
                
                String reqResult = null;
                String errorCode = null;
                HashMap mapModel;
                
                getLogger().debug("noCif : " + getNoCif());
                
                map.put("model.compositeId.codCustId", String.valueOf(getNoCif()));
                map.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));
                
                result = HttpUtil.request(getBdsmHost() + ACTION_GET_CUSTMAST, map);
                
                try {
                    resultMap = (HashMap) JSONUtil.deserialize(result);
                    reqResult = (String) resultMap.get("jsonStatus");
                    errorCode = (String) resultMap.get("errorCode");
                    mapModel = (HashMap) resultMap.get("model");
                    if (mapModel != null) {
                        namCustFull = (String) mapModel.get("namCustFull");
                    }
                    
                    String tokenS = TokenHelper.setToken();
                    getLogger().debug("TokenS : " + tokenS);
                    mapModel.put("struts.token.name", tokenS);
                    
                } catch (JSONException ex) {
                    getLogger().fatal(ex, ex);
                    return ActionSupport.ERROR;
                }
                
                return "inquiry";
            } else {
                return logout();
            }
        } catch (NullPointerException e) {
            setErrorMessage("CIF: " + getNoCif() + " not found");
            return ActionSupport.ERROR;
        } catch (Throwable e) {
            getLogger().fatal(e, e);
            return ActionSupport.ERROR;
        } finally {
            getLogger().info("[ End ] inquiry()");
        }
    }
    
    /**
     * 
     * @return
     */
    @SkipValidation
    public String inqRatUsdIdr() {
        Map<String, String> map = new HashMap<String, String>();
        String result;
        HashMap resultMap;
        HashMap mapModel;
        String reqResult = null;
        String errorCode = null;
        
        map.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));
        
        result = HttpUtil.request(getBdsmHost() + ACTION_GET_USDIDR_RATE, map);
        
        try {
            resultMap = (HashMap) JSONUtil.deserialize(result);
            reqResult = (String) resultMap.get("jsonStatus");
            errorCode = (String) resultMap.get("errorCode");
            mapModel = (HashMap) resultMap.get("model");
            if (mapModel != null) {
                setRatUsdIdr(new BigDecimal((Double)mapModel.get("ratCcyMid")));
            }
        } catch (JSONException ex) {
            getLogger().fatal(ex, ex);
        }
        
        return "inqRatUsdIdr";
    }
    
    /**
     * 
     * @return
     */
    @SkipValidation
    public String inqRatFcyIdr() {
        Map<String, String> map = new HashMap<String, String>();
        String result;
        HashMap resultMap;
        HashMap mapModel;
        String reqResult = null;
        String errorCode = null;
        
        map.put("modelCode.namCcyShort", ccyUd);
        map.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));
        
        result = HttpUtil.request(getBdsmHost() + ACTION_GET_USDIDR_RATE, map);
        
        try {
            resultMap = (HashMap) JSONUtil.deserialize(result);
            reqResult = (String) resultMap.get("jsonStatus");
            errorCode = (String) resultMap.get("errorCode");
            mapModel = (HashMap) resultMap.get("model");
            if (mapModel != null) {
                setRatFcyIdr(new BigDecimal(mapModel.get("ratCcyMid").toString()));
            }
        } catch (JSONException ex) {
            getLogger().fatal(ex, ex);
        }
        
        return "inqRatFcyIdr";
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
    public String doAdd() {
        Map<String, String> map = new HashMap<String, String>();
        String result;
        HashMap resultMap;
        String reqResult = null;
        String errorCode = null;
        
        getLogger().debug("noCif : " + getNoCif());
        getLogger().debug("noUd : " + getNoUd());
        getLogger().debug("typeUD :" + getTypeUD());
        getLogger().debug("payeeName :" + getPayeeName());
        getLogger().debug("payeeCountry :" + getPayeeCountry());
        getLogger().debug("tradingProduct :" + getTradingProduct());
        getLogger().debug("dtIssued :" + getDtIssued());
        getLogger().debug("dtExpiry : " + getDtExpiry());
        getLogger().debug("ccyUd : " + getCcyUd());
        getLogger().debug("amtLimit : " + getAmtLimit());
        getLogger().debug("amtAvail : " + getAmtLimit());
        getLogger().debug("remarks : " + getRemarks());
        getLogger().debug("cdBranch : " + getCdBranch());
        getLogger().debug("amtLimitUSD : " + getAmtLimitUSD());
        getLogger().debug("usdIdrRate : " + getUsdIdrRate());
        getLogger().debug("ratFcyIdr : " + getRatFcyIdr());
        getLogger().debug("idMaintainedBy : " + getIdMaintainedBy());
        getLogger().debug("idMaintainedSpv : " + getIdMaintainedSpv());
        
        double d = Double.parseDouble(getAmtLimitUSD().toString());
        String balLimitUSD = String.format("%f", d);
        
        map.put("model.compositeId.noCif", String.valueOf(getNoCif()));
        map.put("model.compositeId.noUd", getNoUd());
        map.put("model.typeUD", getTypeUD());
        map.put("model.payeeName", getPayeeName());
        map.put("model.payeeCountry", getPayeeCountry());
        map.put("model.tradingProduct", getTradingProduct());
        map.put("model.dtIssued", getDtIssued());
        map.put("model.dtExpiry", getDtExpiry());
        map.put("model.ccyUd", getCcyUd());
        map.put("model.amtLimit", String.valueOf(getAmtLimit()));
        map.put("model.amtAvail", String.valueOf(getAmtLimit()));
        map.put("model.remarks", getRemarks());
        map.put("model.cdBranch", String.valueOf(getCdBranch()));
        //
        map.put("model.amtLimitUsd", balLimitUSD);
        map.put("model.amtAvailUsd", balLimitUSD);
        
        //map.put("model.amtLimitUsd", String.valueOf(getAmtLimitUSD()));
        //map.put("model.amtAvailUsd", String.valueOf(getAmtLimitUSD()));
        map.put("model.ratUsdLim", String.valueOf(getUsdIdrRate()));
        map.put("model.ratFcyLim", String.valueOf(getRatFcyIdr()));
        map.put("model.status", "A");
        map.put("model.idMaintainedBy", getIdMaintainedBy());
        map.put("model.idMaintainedSpv", getIdMaintainedSpv());
        map.put("namMenu", NAM_MENU);
        map.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));
        
        result = HttpUtil.request(getBdsmHost() + ACTION_INSERT, map);
        
        try {
            resultMap = (HashMap) JSONUtil.deserialize(result);
            reqResult = (String) resultMap.get("jsonStatus");
            errorCode = (String) resultMap.get("errorCode");
        } catch (JSONException ex) {
            getLogger().fatal(ex, ex);
        }
        
        if (reqResult.equals(ActionSupport.ERROR)) {
            addActionError(getText(errorCode));
        } else if (reqResult.equals(ActionSupport.SUCCESS)) {
            addActionMessage(getText(errorCode));
        }
        
        return ActionSupport.SUCCESS;
    }
    
    /**
     * 
     * @return
     */
    @Override
    public String doEdit() {
        Map<String, String> map = new HashMap<String, String>();
        String result;
        HashMap resultMap;
        String reqResult = null;
        String errorCode = null;
        
        getLogger().debug("noCif : " + getNoCif());
        getLogger().debug("noUd : " + getNoUd());
        getLogger().debug("typeUD :" + getTypeUD());
        getLogger().debug("payeeName :" + getPayeeName());
        getLogger().debug("payeeCountry :" + getPayeeCountry());
        getLogger().debug("tradingProduct :" + getTradingProduct());
        getLogger().debug("dtIssued :" + getDtIssued());
        getLogger().debug("dtExpiry : " + getDtExpiry());
        getLogger().debug("ccyUd : " + getCcyUd());
        getLogger().debug("amtLimit : " + getAmtLimit());
        getLogger().debug("amtAvail : " + getAmtLimit());
        getLogger().debug("remarks : " + getRemarks());
        getLogger().debug("cdBranch : " + getCdBranch());
        getLogger().debug("amtLimitUSD : " + getAmtLimitUSD());
        getLogger().debug("amtAvailUsd : " + getAmtLimitUSD());
        getLogger().debug("usdIdrRate : " + getUsdIdrRate());
        getLogger().debug("ratFcyIdr : " + getRatFcyIdr());        
        getLogger().debug("status : " + getStatus());
        getLogger().debug("idMaintainedBy : " + getIdMaintainedBy());
        getLogger().debug("idMaintainedSpv : " + getIdMaintainedSpv());
        
        map.put("model.compositeId.noCif", String.valueOf(getNoCif()));
        map.put("model.compositeId.noUd", getNoUd());
        map.put("model.typeUD", getTypeUD());
        map.put("model.payeeName", getPayeeName());
        map.put("model.payeeCountry", getPayeeCountry());
        map.put("model.tradingProduct", getTradingProduct());
        map.put("model.dtIssued", getDtIssued());
        map.put("model.dtExpiry", getDtExpiry());
        map.put("model.ccyUd", getCcyUd());
        map.put("model.amtLimit", String.valueOf(getAmtLimit()));
        map.put("model.amtAvail", String.valueOf(getAmtLimit()));
        map.put("model.remarks", getRemarks());
        map.put("model.cdBranch", String.valueOf(getCdBranch()));
        map.put("model.amtLimitUsd", String.valueOf(getAmtLimitUSD()));
        map.put("model.amtAvailUsd", String.valueOf(getAmtLimitUSD()));
        map.put("model.ratUsdLim", String.valueOf(getUsdIdrRate()));
        map.put("model.ratFcyLim", String.valueOf(getRatFcyIdr()));        
        map.put("model.status", getStatus());
        map.put("model.idMaintainedBy", getIdMaintainedBy());
        map.put("model.idMaintainedSpv", getIdMaintainedSpv());
        map.put("namMenu", NAM_MENU);
        map.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));
        
        result = HttpUtil.request(getBdsmHost() + ACTION_EDIT, map);
        
        try {
            resultMap = (HashMap) JSONUtil.deserialize(result);
            reqResult = (String) resultMap.get("jsonStatus");
            errorCode = (String) resultMap.get("errorCode");
        } catch (JSONException ex) {
            getLogger().fatal(ex, ex);
        }
        
        if (reqResult.equals(ActionSupport.ERROR)) {
            addActionError(getText(errorCode));
        } else if (reqResult.equals(ActionSupport.SUCCESS)) {
            addActionMessage(getText(errorCode));
        }
        
        return ActionSupport.SUCCESS;
    }
    
    /**
     * 
     * @return
     */
    @Override
    public String doDelete() {
        Map<String, String> map = new HashMap<String, String>();
        String result;
        HashMap resultMap;
        String reqResult = null;
        String errorCode = null;
        
        getLogger().debug("noCif : " + getNoCif());
        getLogger().debug("noUd : " + getNoUd());
        getLogger().debug("ccyUd : " + getCcyUd());
        getLogger().debug("idMaintainedBy : " + getIdMaintainedBy());
        getLogger().debug("idMaintainedSpv : " + getIdMaintainedSpv());
        
        map.put("model.compositeId.noCif", String.valueOf(getNoCif()));
        map.put("model.compositeId.noUd", getNoUd());
        map.put("model.dtExpiry", getDtExpiry());
        map.put("model.ccyUd", getCcyUd());
        map.put("model.amtLimit", String.valueOf(getAmtLimit()));
        map.put("model.remarks", getRemarks());
        map.put("model.cdBranch", String.valueOf(getCdBranch()));
        map.put("model.amtLimitUsd", String.valueOf(getAmtLimitUSD()));
        map.put("model.ratUsdLim", String.valueOf(getUsdIdrRate()));
        map.put("model.ratFcyLim", String.valueOf(getRatFcyIdr()));        
        map.put("model.status", getStatus());
        map.put("model.idMaintainedBy", getIdMaintainedBy());
        map.put("model.idMaintainedSpv", getIdMaintainedSpv());
        map.put("namMenu", NAM_MENU);
        map.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));
        
        result = HttpUtil.request(getBdsmHost() + ACTION_DELETE, map);
        
        try {
            resultMap = (HashMap) JSONUtil.deserialize(result);
            reqResult = (String) resultMap.get("jsonStatus");
            errorCode = (String) resultMap.get("errorCode");
        } catch (JSONException ex) {
            getLogger().fatal(ex, ex);
        }
        
        if (reqResult.equals(ActionSupport.ERROR)) {
            addActionError(getText(errorCode));
        } else if (reqResult.equals(ActionSupport.SUCCESS)) {
            addActionMessage(getText(errorCode));
        }
        
        return ActionSupport.SUCCESS;
    }
    
    /**
     * 
     * @return
     */
    @SkipValidation
    @SuppressWarnings("unchecked")
    public String retrieveRateMessage() {
        this.getLogger().info("[Begin] retrieveRateMessage()");
        try {
            if (this.isValidSession()) {
                Map<String, Object> map = new HashMap<String, Object>(0);
                
                map = this.callHostHTTPRequest("BICloseRate", "get", map);
                Map<String, ? extends Object> mapModel = (Map<String, ? extends Object>) map.get("model");
                Date closeDateBI = null;
                    
                if (mapModel != null) {
                    String strCloseDate = ((Map<String, ? extends Object>) mapModel.get("compositeId")).get("BICloseDate").toString();
                    synchronized(DateUtility.DATE_TIME_FORMAT_YYYYMMDD) {
                        closeDateBI = DateUtility.DATE_TIME_FORMAT_YYYYMMDD.parse(strCloseDate.replace('/', '-').replace('T', ' '));
                    }
                }
                
                ScreenMsg scrMsg = this.getScreenMsg(this.tagMessage);
                if ((scrMsg == null) || (StringUtils.isBlank(scrMsg.getMsgTag()))) 
                    this.tagMessage = "";
                else {
                    this.tagMessage = scrMsg.getMessage();
                    this.tagMessage += (closeDateBI.before((Date) this.session.get(bdsm.web.Constant.C_DATEBUSINESS)))? "H-1": "H+0";
                }
                
                return "inqMessage";
            }
            else
                return logout();
        }
        catch (Throwable e) {
            this.setErrorMessage(e.getMessage());
            this.getLogger().fatal(e, e);
            
            return ActionSupport.ERROR;
        }
        finally {
            this.getLogger().info("[ End ] retrieveRateMessage()");
        }
    }
    
    
    @SuppressWarnings("unchecked")
    private ScreenMsg getScreenMsg(String tag) {
        Map<String, ? extends Object> mapScreen = new HashMap<String, Object>();
        Map<String, ? extends Object> msgMap = new HashMap<String, Object>();
        ScreenMsg msg = new ScreenMsg();
        
        ScreenMsgService sms = new ScreenMsgService(this.getServletRequest(), this.session, this.getBdsmHost(), this.getTokenKey(), this.getTzToken());
        this.getLogger().info("[Begin] getScreenMsg(), tag: " + tag);
        
        mapScreen = sms.getMessage(tag);
        if (mapScreen != null) {
            this.getLogger().debug("Result Map: " + mapScreen);
            try {
                msgMap = (HashMap<String, ? extends Object>) mapScreen.get("model");
                ClassConverterUtil.MapToClass(msgMap, msg);
            }
            catch (Exception e) {
                this.getLogger().debug(e, e);
            }
        }
        
        return msg;
    }


    /**
     * @return the noCif
     */
    public Integer getNoCif() {
        return noCif;
    }

    /**
     * @param noCif the noCif to set
     */
    public void setNoCif(Integer noCif) {
        this.noCif = noCif;
    }

    /**
     * @return the namCustFull
     */
    public String getNamCustFull() {
        return namCustFull;
    }

    /**
     * @param namCustFull the namCustFull to set
     */
    public void setNamCustFull(String namCustFull) {
        this.namCustFull = namCustFull;
    }

    /**
     * @return the noUd
     */
    public String getNoUd() {
        return noUd;
    }

    /**
     * @param noUd the noUd to set
     */
    public void setNoUd(String noUd) {
        this.noUd = noUd;
    }
    
    /**
     * @return the typeUD
     */
    public String getTypeUD() {
        return this.typeUD;
    }
    
    /**
     * @param typeUD the typeUD to set
     */
    public void setTypeUD(String typeUD) {
        this.typeUD = typeUD;
    }
    
    /**
     * @return the txtTypeUD
     */
    public String getTxtTypeUD() {
        return this.txtTypeUD;
    }
    
    /**
     * @param txtTypeUD the txtTypeUD to set
     */
    public void setTxtTypeUD(String txtTypeUD) {
        this.txtTypeUD = txtTypeUD;
    }
    
    /**
     * @return the payeeName
     */
    public String getPayeeName() {
        return this.payeeName;
    }
    
    /**
     * @param payeeName the payeeName to set
     */
    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }
    
    /**
     * @return the payeeCountry
     */
    public String getPayeeCountry() {
        return this.payeeCountry;
    }
    
    /**
     * @param payeeCountry the payeeCountry to set
     */
    public void setPayeeCountry(String payeeCountry) {
        this.payeeCountry = payeeCountry;
    }
    
    /**
     * @return the tradingProduct
     */
    public String getTradingProduct() {
        return this.tradingProduct;
    }
    
    /**
     * @param tradingProduct the tradingProduct to set
     */
    public void setTradingProduct(String tradingProduct) {
        this.tradingProduct = tradingProduct;
    }
    
    /**
     * @return the dtIssued
     */
    public String getDtIssued() {
        return this.dtIssued;
    }
    
    /**
     * @param dtIssued the dtIssued to set
     */
    public void setDtIssued(String dtIssued) {
        this.dtIssued = dtIssued;
    }

    /**
     * @return the dtExpiry
     */
    public String getDtExpiry() {
        /*String year = dtExpiry.substring(6, 10);
        String month = dtExpiry.substring(3, 5);
        String day = dtExpiry.substring(0, 2);
        return month + "/" + day + "/" + year;*/
        
        return dtExpiry;
    }

    /**
     * @param dtExpiry the dtExpiry to set
     */
    public void setDtExpiry(String dtExpiry) {
        this.dtExpiry = dtExpiry;
    }

    /**
     * @return the ccyUd
     */
    public String getCcyUd() {
        return ccyUd;
    }

    /**
     * @param ccyUd the ccyUd to set
     */
    public void setCcyUd(String ccyUd) {
        this.ccyUd = ccyUd;
    }

    /**
     * @return the amtLimit
     */
    public BigDecimal getAmtLimit() {
        return amtLimit;
    }

    /**
     * @param amtLimit the amtLimit to set
     */
    public void setAmtLimit(BigDecimal amtLimit) {
        this.amtLimit = amtLimit;
    }

    /**
     * @return the remarks
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * @param remarks the remarks to set
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * @return the cdBranch
     */
    public Integer getCdBranch() {
        return cdBranch;
    }

    /**
     * @param cdBranch the cdBranch to set
     */
    public void setCdBranch(Integer cdBranch) {
        this.cdBranch = cdBranch;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the idMaintainedBy
     */
    public String getIdMaintainedBy() {
        return idMaintainedBy;
    }

    /**
     * @param idMaintainedBy the idMaintainedBy to set
     */
    public void setIdMaintainedBy(String idMaintainedBy) {
        this.idMaintainedBy = idMaintainedBy;
    }

    /**
     * @return the idMaintainedSpv
     */
    public String getIdMaintainedSpv() {
        return idMaintainedSpv;
    }

    /**
     * @param idMaintainedSpv the idMaintainedSpv to set
     */
    public void setIdMaintainedSpv(String idMaintainedSpv) {
        this.idMaintainedSpv = idMaintainedSpv;
    }

    /**
     * @return the ratUsdIdr
     */
    public BigDecimal getRatUsdIdr() {
        return ratUsdIdr;
    }

    /**
     * @param ratUsdIdr the ratUsdIdr to set
     */
    public void setRatUsdIdr(BigDecimal ratUsdIdr) {
        this.ratUsdIdr = ratUsdIdr;
    }

    /**
     * @return the ratFcyIdr
     */
    public BigDecimal getRatFcyIdr() {
        return ratFcyIdr;
    }

    /**
     * @param ratFcyIdr the ratFcyIdr to set
     */
    public void setRatFcyIdr(BigDecimal ratFcyIdr) {
        this.ratFcyIdr = ratFcyIdr;
    }

    /**
     * @return the usdIdrRate
     */
    public BigDecimal getUsdIdrRate() {
        return usdIdrRate;
    }

    /**
     * @param usdIdrRate the usdIdrRate to set
     */
    public void setUsdIdrRate(BigDecimal usdIdrRate) {
        this.usdIdrRate = usdIdrRate;
    }

    /**
     * @return the amtLimitUSD
     */
    public BigDecimal getAmtLimitUSD() {
        return amtLimitUSD;
    }

    /**
     * @param amtLimitUSD the amtLimitUSD to set
     */
    public void setAmtLimitUSD(BigDecimal amtLimitUSD) {
        this.amtLimitUSD = amtLimitUSD;
    }

    /**
     * @return the tagMessage
     */
    public String getTagMessage() {
        return this.tagMessage;
    }

    /**
     * @param tagMessage the tagMessage to set
     */
    public void setTagMessage(String tagMessage) {
        this.tagMessage = tagMessage;
        
    }

}
