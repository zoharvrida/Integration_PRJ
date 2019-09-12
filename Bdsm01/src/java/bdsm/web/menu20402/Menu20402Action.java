/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.web.menu20402;

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
 * @author v00013493
 */
public class Menu20402Action extends BaseContentAction {
    
    private static final String ACTION_GET_CIF = "fcyTxnManInputCIF_get.action";
    private static final String ACTION_GET_UD = "mfcTxnDetails_get.action";
    private static final String ACTION_GET_USDIDR_RATE = "fcrBaCcyRate_get.action";
    private static final String ACTION_INSERT = "fcyTxnManInput_insert.action";
    private static final String ACTION_DELETE = "fcyTxnManInput_delete.action";
    private static final String NAM_MENU = "Foreign Currency Transaction Manual Input";
    private Integer noCif;
    private String namCustFull;
    private String refTxn;
    private String dtmTxn;
    private String ccyTxn;
    private BigDecimal amtTxn;
    private BigDecimal ratFcyIdr;
    private BigDecimal amtTxnLcy;
    private BigDecimal ratUsdIdr;
    private BigDecimal amtTxnUsd;
    private String txnDesc;
    private BigDecimal amtAvailUsd;
    private String noUd;
    private Integer period;
    private Integer txnBranch;
    private String idMaintainedBy;
    private String idMaintainedSpv;
    private String tagMessage;
    
    
    /**
     * 
     * @return
     */
    @SkipValidation
    public String inqCif() {
        getLogger().info("[Begin] inqCif()");
        try {
            if (isValidSession()) {
                Map<String, String> map = new HashMap<String, String>();
                String result;
                HashMap resultMap;
                HashMap mapModel;
                String reqResult = null;
                String errorCode = null;
                
                getLogger().debug("noCif : " + getNoCif());
                getLogger().debug("period : " + getPeriod());
                
                map.put("model.noCif", String.valueOf(getNoCif()));
                map.put("model.period", String.valueOf(getPeriod()));
                map.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));
                
                result = HttpUtil.request(getBdsmHost() + ACTION_GET_CIF, map);
                
                try {
                    resultMap = (HashMap)JSONUtil.deserialize(result);
                    reqResult = (String)resultMap.get("jsonStatus");
                    errorCode = (String)resultMap.get("errorCode");
                    mapModel = (HashMap)resultMap.get("model");
                    if(mapModel != null) {
                        namCustFull = (String)mapModel.get("namCustFull");                
                        amtAvailUsd = new BigDecimal(mapModel.get("amtAvailUsd").toString());
                    }
                    
                    String tokenS = TokenHelper.setToken();
                    getLogger().debug("TokenS : " + tokenS);
                    mapModel.put("struts.token.name", tokenS);
                    
                } catch (JSONException ex) {
                    getLogger().fatal(ex, ex);
                    return ActionSupport.ERROR;
                }
                
                return "inqCif";
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
            getLogger().info("[ End ] inqCif()");
        }
    }
    
    /**
     * 
     * @return
     */
    @SkipValidation
    public String inqUd() {
        Map<String, String> map = new HashMap<String, String>();
        String result;
        HashMap resultMap;
        HashMap mapModel;
        String reqResult = null;
        String errorCode = null;
        
        getLogger().debug("noCif : " + getNoCif());
        getLogger().debug("noAcct : " + getNoCif());
        getLogger().debug("refTxn : " + getRefTxn());
        
        map.put("model.noCif", String.valueOf(getNoCif()));
        map.put("model.compositeId.noAcct", String.valueOf(getNoCif()));
        map.put("model.compositeId.refTxn", getRefTxn());
        map.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));
        
        result = HttpUtil.request(getBdsmHost() + ACTION_GET_UD, map);
        
        try {
            resultMap = (HashMap)JSONUtil.deserialize(result);
            reqResult = (String)resultMap.get("jsonStatus");
            errorCode = (String)resultMap.get("errorCode");
            mapModel = (HashMap)resultMap.get("model");
            if(mapModel != null) {
                noUd = (String)mapModel.get("noUd");
            }
        } catch (JSONException ex) {
            getLogger().fatal(ex, ex);
        }
        
        return "inqUd";
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
            resultMap = (HashMap)JSONUtil.deserialize(result);
            reqResult = (String)resultMap.get("jsonStatus");
            errorCode = (String)resultMap.get("errorCode");
            mapModel = (HashMap)resultMap.get("model");
            if(mapModel != null) {
                ratUsdIdr = new BigDecimal(mapModel.get("ratCcyMid").toString());
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
        
        map.put("modelCode.namCcyShort", ccyTxn);
        map.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));
        
        result = HttpUtil.request(getBdsmHost() + ACTION_GET_USDIDR_RATE, map);
        
        try {
            resultMap = (HashMap) JSONUtil.deserialize(result);
            reqResult = (String) resultMap.get("jsonStatus");
            errorCode = (String) resultMap.get("errorCode");
            mapModel = (HashMap) resultMap.get("model");
            if (mapModel != null) {
                ratFcyIdr = new BigDecimal(mapModel.get("ratCcyMid").toString());
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
        getLogger().debug("period : " + getPeriod());
        getLogger().debug("noAcct : " + getNoCif());
        getLogger().debug("refTxn : " + getRefTxn());
        getLogger().debug("dtmTxn : " + getDtmTxn());
        getLogger().debug("ccyTxn : " + getCcyTxn());
        getLogger().debug("amtTxn : " + getAmtTxn());
        getLogger().debug("ratFcyIdr : " + getRatFcyIdr());
        getLogger().debug("amtTxnLcy : " + getAmtTxnLcy());
        getLogger().debug("ratUsdIdr : " + getRatUsdIdr());
        getLogger().debug("amtTxnUsd : " + getAmtTxnUsd());
        getLogger().debug("idTxn : " + getIdMaintainedBy());
        getLogger().debug("txnDesc : " + getTxnDesc());
        getLogger().debug("noUd : " + getNoUd());
        getLogger().debug("txnBranch : " + getTxnBranch());
        getLogger().debug("idMaintainedBy : " + getIdMaintainedBy());
        getLogger().debug("idMaintainedSpv : " + getIdMaintainedSpv());
        
        map.put("model.noCif", String.valueOf(getNoCif()));
        map.put("model.period", String.valueOf(getPeriod()));
        map.put("model.noAcct", String.valueOf(getNoCif()));
        map.put("model.refTxn", getRefTxn());
        map.put("model.strDtmTxn", getDtmTxn());
        map.put("model.ccyTxn", getCcyTxn());
        map.put("model.amtTxn", String.valueOf(getAmtTxn()));
        map.put("model.ratFcyIdr", String.valueOf(getRatFcyIdr()));
        map.put("model.amtTxnLcy", String.valueOf(getAmtTxnLcy()));
        map.put("model.ratUsdIdr", String.valueOf(getRatUsdIdr()));
        map.put("model.amtTxnUsd", String.valueOf(getAmtTxnUsd()));
        map.put("model.idTxn", getIdMaintainedBy());
        map.put("model.txnDesc", getTxnDesc());
        map.put("model.noUd", getNoUd());
        map.put("model.txnBranch", String.valueOf(getTxnBranch()));
        map.put("model.idMaintainedBy", getIdMaintainedBy());
        map.put("model.idMaintainedSpv", getIdMaintainedSpv());
        map.put("namMenu", NAM_MENU);
        map.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));
        
        result = HttpUtil.request(getBdsmHost() + ACTION_INSERT, map);
        
        try {
            resultMap = (HashMap)JSONUtil.deserialize(result);
            reqResult = (String)resultMap.get("jsonStatus");
            errorCode = (String)resultMap.get("errorCode");
        } catch (JSONException ex) {
            getLogger().fatal(ex, ex);
        }
        
        if(reqResult.equals(ActionSupport.ERROR)) {
            addActionError(getText(errorCode));
        } else if(reqResult.equals(ActionSupport.SUCCESS)) {
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
        throw new UnsupportedOperationException("Not supported yet.");
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
        getLogger().debug("period : " + getPeriod());
        getLogger().debug("noAcct : " + getNoCif());
        getLogger().debug("refTxn : " + getRefTxn());
        getLogger().debug("dtmTxn : " + getDtmTxn());
        getLogger().debug("ccyTxn : " + getCcyTxn());
        getLogger().debug("amtTxn : " + getAmtTxn());
        getLogger().debug("ratFcyIdr : " + getRatFcyIdr());
        getLogger().debug("amtTxnLcy : " + getAmtTxnLcy());
        getLogger().debug("ratUsdIdr : " + getRatUsdIdr());
        getLogger().debug("amtTxnUsd : " + getAmtTxnUsd());
        getLogger().debug("idTxn : " + getIdMaintainedBy());
        getLogger().debug("txnDesc : " + getTxnDesc());
        getLogger().debug("noUd : " + getNoUd());
        getLogger().debug("idMaintainedBy : " + getIdMaintainedBy());
        getLogger().debug("idMaintainedSpv : " + getIdMaintainedSpv());
        
        map.put("model.noCif", String.valueOf(getNoCif()));
        map.put("model.period", String.valueOf(getPeriod()));
        map.put("model.noAcct", String.valueOf(getNoCif()));
        map.put("model.refTxn", getRefTxn());
        map.put("model.strDtmTxn", getDtmTxn());
        map.put("model.ccyTxn", getCcyTxn());
        map.put("model.amtTxn", String.valueOf(getAmtTxn()));
        map.put("model.ratFcyIdr", String.valueOf(getRatFcyIdr()));
        map.put("model.amtTxnLcy", String.valueOf(getAmtTxnLcy()));
        map.put("model.ratUsdIdr", String.valueOf(getRatUsdIdr()));
        map.put("model.amtTxnUsd", String.valueOf(getAmtTxnUsd()));
        map.put("model.idTxn", getIdMaintainedBy());
        map.put("model.txnDesc", getTxnDesc());
        map.put("model.noUd", getNoUd());
        map.put("model.idMaintainedBy", getIdMaintainedBy());
        map.put("model.idMaintainedSpv", getIdMaintainedSpv());
        map.put("namMenu", NAM_MENU);
        map.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));
        
        result = HttpUtil.request(getBdsmHost() + ACTION_DELETE, map);
        
        try {
            resultMap = (HashMap)JSONUtil.deserialize(result);
            reqResult = (String)resultMap.get("jsonStatus");
            errorCode = (String)resultMap.get("errorCode");
        } catch (JSONException ex) {
            getLogger().fatal(ex, ex);
        }
        
        if(reqResult.equals(ActionSupport.ERROR)) {
            addActionError(getText(errorCode));
        } else if(reqResult.equals(ActionSupport.SUCCESS)) {
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
     * @return the refTxn
     */
    public String getRefTxn() {
        return refTxn;
    }

    /**
     * @param refTxn the refTxn to set
     */
    public void setRefTxn(String refTxn) {
        this.refTxn = refTxn;
    }
    
    /**
     * @return the dtmTxn
     */
    public String getDtmTxn() {
        return dtmTxn;
    }

    /**
     * @param dtmTxn the dtmTxn to set
     */
    public void setDtmTxn(String dtmTxn) {
        this.dtmTxn = dtmTxn;
    }
    
    /**
     * @return the ccyTxn
     */
    public String getCcyTxn() {
        return ccyTxn;
    }

    /**
     * @param ccyTxn the ccyTxn to set
     */
    public void setCcyTxn(String ccyTxn) {
        this.ccyTxn = ccyTxn;
    }
    
    /**
     * @return the amtTxn
     */
    public BigDecimal getAmtTxn() {
        return amtTxn;
    }

    /**
     * @param amtTxn the amtTxn to set
     */
    public void setAmtTxn(BigDecimal amtTxn) {
        this.amtTxn = amtTxn;
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
     * @return the amtTxnLcy
     */
    public BigDecimal getAmtTxnLcy() {
        return amtTxnLcy;
    }

    /**
     * @param amtTxnLcy the amtTxnLcy to set
     */
    public void setAmtTxnLcy(BigDecimal amtTxnLcy) {
        this.amtTxnLcy = amtTxnLcy;
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
     * @return the amtTxnUsd
     */
    public BigDecimal getAmtTxnUsd() {
        return amtTxnUsd;
    }

    /**
     * @param amtTxnUsd the amtTxnUsd to set
     */
    public void setAmtTxnUsd(BigDecimal amtTxnUsd) {
        this.amtTxnUsd = amtTxnUsd;
    }
    
    /**
     * @return the txnDesc
     */
    public String getTxnDesc() {
        return txnDesc;
    }

    /**
     * @param txnDesc the txnDesc to set
     */
    public void setTxnDesc(String txnDesc) {
        this.txnDesc = txnDesc;
    }
    
    /**
     * @return the amtAvailUsd
     */
    public BigDecimal getAmtAvailUsd() {
        return amtAvailUsd;
    }

    /**
     * @param amtAvailUsd the amtAvailUsd to set
     */
    public void setAmtAvailUsd(BigDecimal amtAvailUsd) {
        this.amtAvailUsd = amtAvailUsd;
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
     * @return the period
     */
    public Integer getPeriod() {
        return period;
    }

    /**
     * @param period the period to set
     */
    public void setPeriod(Integer period) {
        this.period = period;
    }
    
    /**
     * @return the txnBranch
     */
    public Integer getTxnBranch() {
        return txnBranch;
    }

    /**
     * @param txnBranch the txnBranch to set
     */
    public void setTxnBranch(Integer txnBranch) {
        this.txnBranch = txnBranch;
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
     * @return the tagMessage
     */
    public String getTagMessage() {
        return tagMessage;
    }

    /**
     * @param tagMessage the tagMessage to set
     */
    public void setTagMessage(String tagMessage) {
        this.tagMessage = tagMessage;
    }

}