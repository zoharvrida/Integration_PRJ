/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.web.menu20401;

import bdsm.model.ScreenMsg;
import bdsm.util.BdsmUtil;
import bdsm.util.ClassConverterUtil;
import bdsm.util.HttpUtil;
import bdsm.util.oracle.DateUtility;
import bdsm.web.BaseContentAction;
import bdsm.web.ScreenMsgService;

import com.opensymphony.xwork2.ActionSupport;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;

/**
 *
 * @author v00013493
 */
public class Menu20401Action extends BaseContentAction {
    private static final String ACTION_GET_DATA = "mfcNoBook_get.action";
    private static final String ACTION_FLAG = "saveFcyTxnFlag_save.action";
    private static final String ACTION_UNFLAG = "saveFcyTxnUnflag_save.action";
    private static final String NAM_MENU = "Foreign Currency Transaction Flagging";
    
    private String noAcct;
    private String refTxn;
    private String ccyTxn;
    private String amtTxn;
    private String amtTxnStr;
    private String ratFcyIdr;
    private String amtTxnLcy;
    private String amtTxnLcyStr;
    private String ratUsdIdr;
    private String amtTxnUsd;
    private String amtTxnUsdStr;
    private String txnDesc;
    private String dtmTxn;
    private String dtValue;
    private String idTxn;
    private String typMsg;
    private Integer txnBranch;
    private Integer noCif;
    private String namCustFull;
    private String codAcctCustRel;
    private String amtAvailUsd;
    private String noUd;
    private Integer period;
    private String idMaintainedBy;
    private String idMaintainedSpv;
    private String tagMessage;
    
    
    /**
     * 
     * @return
     */
    @SkipValidation
    public String getData() {
        getLogger().info("[Begin] getData()");
        try {
            if (isValidSession()) {
                Map<String, String> map = new HashMap<String, String>();
                String result;
                HashMap resultMap;
                HashMap mapModel;
                String reqResult = null;
                String errorCode = null;
                
                getLogger().debug("noAcct : " + getNoAcct());
                getLogger().debug("refTxn : " + getRefTxn());
                getLogger().debug("typMsg : " + getTypMsg());
                
                map.put("model.compositeId.noAcct", getNoAcct());
                map.put("model.compositeId.refTxn", getRefTxn());
                map.put("model.compositeId.typMsg", getTypMsg());
                map.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));
                
                result = HttpUtil.request(getBdsmHost() + ACTION_GET_DATA, map);
                
                try {
                    resultMap = (HashMap)JSONUtil.deserialize(result);
                    reqResult = (String)resultMap.get("jsonStatus");
                    errorCode = (String)resultMap.get("errorCode");
                    mapModel = (HashMap)resultMap.get("model");
                    
                    if(mapModel != null) {
                        NumberFormat formatter = new DecimalFormat("#0.00");
                        Double dblValue;
                        
                        ccyTxn = (String)mapModel.get("ccyTxn");
                        dblValue = (Double)mapModel.get("amtTxn"); 
                        amtTxn = dblValue.toString();
                        amtTxnStr = formatter.format(dblValue);
                        
                        ratFcyIdr = ((Double)mapModel.get("ratFcyIdr")).toString();
                        dblValue = (Double)mapModel.get("amtTxnLcy");
                        amtTxnLcy = dblValue.toString();
                        amtTxnLcyStr = formatter.format(dblValue);
                        
                        ratUsdIdr = ((Double)mapModel.get("ratUsdIdr")).toString();
                        dblValue = (Double)mapModel.get("amtTxnUsd");
                        amtTxnUsd = dblValue.toString();
                        amtTxnUsdStr = formatter.format(dblValue);
                        
                        txnDesc = (String)mapModel.get("txnDesc");
                        dtmTxn = (String)mapModel.get("dtmTxn");
                        dtmTxn = dtmTxn.replace("T", " ");
                        dtValue = (String)mapModel.get("dtValue");
                        dtValue = dtValue.substring(0, 10);
                        idTxn = (String)mapModel.get("idTxn");
                        txnBranch = new Integer(((Long)mapModel.get("txnBranch")).intValue());
                    }
                } catch (JSONException ex) {
                    getLogger().fatal(ex, ex);
                    return ActionSupport.ERROR;
                } catch (Exception e) {
                    getLogger().fatal(e, e);           
                    return ActionSupport.ERROR;
                }
                
                return "getData";
            } else {
                return logout();
            }
        } catch (Throwable e) {
            getLogger().fatal(e, e);
            return ActionSupport.ERROR;
        } finally {
            getLogger().info("[ End ] getData()");
        }
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
        
        getLogger().debug("noAcct : " + getNoAcct());
        getLogger().debug("refTxn : " + getRefTxn());
        getLogger().debug("ccyTxn : " + getCcyTxn());
        getLogger().debug("noCif : " + getNoCif());
        getLogger().debug("period : " + getPeriod());
        getLogger().debug("typMsg : " + getTypMsg().substring(0, 1));
        getLogger().debug("ccyTxn : " + getCcyTxn());
        getLogger().debug("amtTxn : " + getAmtTxn());
        getLogger().debug("amtTxnUsd : " + getAmtTxnUsd());
        getLogger().debug("noUd : " + getNoUd());
        getLogger().debug("flgJoin : " + getCodAcctCustRel());
        getLogger().debug("idMaintainedBy : " + getIdMaintainedBy());
        getLogger().debug("idMaintainedSpv : " + getIdMaintainedSpv());
        
        map.put("model.noAcct", getNoAcct());
        map.put("model.refTxn", getRefTxn());
        map.put("model.ccyUd", getCcyTxn());
        map.put("model.noCif", String.valueOf(getNoCif()));
        map.put("model.period", String.valueOf(getPeriod()));
        map.put("model.typMsg", getTypMsg().substring(0, 1));
        map.put("model.ccyTxn", getCcyTxn());
        map.put("model.amtTxn", getAmtTxn());
        map.put("model.amtTxnUsd", getAmtTxnUsd());
        map.put("model.noUd", getNoUd());
        map.put("model.flgJoin", getCodAcctCustRel());
        map.put("model.idMaintainedBy", getIdMaintainedBy());
        map.put("model.idMaintainedSpv", getIdMaintainedSpv());
        map.put("namMenu", NAM_MENU);
        map.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));
        
        result = HttpUtil.request(getBdsmHost() + ACTION_FLAG, map);
        
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
        Map<String, String> map = new HashMap<String, String>();
        String result;
        HashMap resultMap;
        String reqResult = null;
        String errorCode = null;
        
        getLogger().debug("noAcct : " + getNoAcct());
        getLogger().debug("refTxn : " + getRefTxn());
        getLogger().debug("ccyTxn : " + getCcyTxn());
        getLogger().debug("noCif : " + getNoCif());
        getLogger().debug("period : " + getPeriod());
        getLogger().debug("typMsg : " + getTypMsg().substring(0, 1));
        getLogger().debug("amtTxn : " + getAmtTxn());
        getLogger().debug("amtTxnUsd : " + getAmtTxnUsd());
        getLogger().debug("noUd : " + getNoUd());
        getLogger().debug("idMaintainedBy : " + getIdMaintainedBy());
        getLogger().debug("idMaintainedSpv : " + getIdMaintainedSpv());
        
        map.put("model.noAcct", getNoAcct());
        map.put("model.refTxn", getRefTxn());
        map.put("model.ccyUd", getCcyTxn());
        map.put("model.noCif", String.valueOf(getNoCif()));
        map.put("model.period", String.valueOf(getPeriod()));
        map.put("model.typMsg", getTypMsg().substring(0, 1));
        map.put("model.amtTxn", getAmtTxn());
        map.put("model.amtTxnUsd", getAmtTxnUsd());
        map.put("model.noUd", getNoUd());
        map.put("model.flgJoin", getCodAcctCustRel());
        map.put("model.idMaintainedBy", getIdMaintainedBy());
        map.put("model.idMaintainedSpv", getIdMaintainedSpv());
        map.put("namMenu", NAM_MENU);
        map.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));
        
        result = HttpUtil.request(getBdsmHost() + ACTION_UNFLAG, map);
        
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
    public String doDelete() {
        throw new UnsupportedOperationException("Not supported yet.");
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
                Date dtmTxn = null;
                Map<String, Object> map = new HashMap<String, Object>(0);
                map.put("methodName", "getByCloseDate");
                map.put("closeDate", this.dtValue);
                
                map = this.callHostHTTPRequest("BICloseRate", "callMethod", map);
                List<Map<String, ? extends Object>> listBICloseRate = (List<Map<String, ? extends Object>>) map.get("modelList");
                Map<String, ? extends Object> rate = (Map<String, ? extends Object>) listBICloseRate.get(0);
                String strTemp, strDate;
                Date dateRate1 = null;
                
                if (rate != null) {
                    strTemp = ((Map<String, ? extends Object>) rate.get("compositeId")).get("BICloseDate").toString(); // format returned value yyyy/MM/ddTHH:mm:ss
                    strDate = strTemp.replace('/', '-').split("T")[0];
                    strTemp = rate.get("dtmInsertLog").toString();
                    synchronized(DateUtility.DATE_TIME_FORMAT_YYYYMMDD) {
                        dateRate1 = DateUtility.DATE_TIME_FORMAT_YYYYMMDD.parse(strDate + " " + strTemp.split("T")[1]);
                    }
                }
                
                this.dtmTxn = new StringBuffer(this.dtmTxn.split(" ")[1])
                    .insert(0, " ")
                    .insert(0, this.dtValue).toString();
                
                synchronized(DateUtility.DATE_TIME_FORMAT_DDMMYYYY) {
                    dtmTxn = DateUtility.DATE_TIME_FORMAT_DDMMYYYY.parse(this.dtmTxn.replace('/', '-'));
                }
                
                
                ScreenMsg scrMsg = this.getScreenMsg(this.tagMessage);
                if ((scrMsg == null) || (StringUtils.isBlank(scrMsg.getMsgTag()))) 
                    this.tagMessage = "";
                else {
                    this.tagMessage = scrMsg.getMessage();
                    if (DateUtils.isSameDay(dtmTxn, dateRate1) && (dtmTxn.after(dateRate1)))
                        this.tagMessage += "H+0";
                    else
                        this.tagMessage += "H-1";
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
     * @return the noAcct
     */
    public String getNoAcct() {
        return noAcct;
    }

    /**
     * @param noAcct the noAcct to set
     */
    public void setNoAcct(String noAcct) {
        this.noAcct = noAcct;
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
    public String getAmtTxn() {
        return amtTxn;
    }

    /**
     * @param amtTxn the amtTxn to set
     */
    public void setAmtTxn(String amtTxn) {
        this.amtTxn = amtTxn;
    }
    
    /**
     * @return the amtTxnStr
     */
    public String getAmtTxnStr() {
        return amtTxnStr;
    }

    /**
     * @param amtTxnStr the amtTxnStr to set
     */
    public void setAmtTxnStr(String amtTxnStr) {
        this.amtTxnStr = amtTxnStr;
    }
    
    /**
     * @return the ratFcyIdr
     */
    public String getRatFcyIdr() {
        return ratFcyIdr;
    }

    /**
     * @param ratFcyIdr the ratFcyIdr to set
     */
    public void setRatFcyIdr(String ratFcyIdr) {
        this.ratFcyIdr = ratFcyIdr;
    }
    
    /**
     * @return the amtTxnLcy
     */
    public String getAmtTxnLcy() {
        return amtTxnLcy;
    }

    /**
     * @param amtTxnLcy the amtTxnLcy to set
     */
    public void setAmtTxnLcy(String amtTxnLcy) {
        this.amtTxnLcy = amtTxnLcy;
    }
    
    /**
     * @return the amtTxnLcyStr
     */
    public String getAmtTxnLcyStr() {
        return amtTxnLcyStr;
    }

    /**
     * @param amtTxnLcyStr the amtTxnLcyStr to set
     */
    public void setAmtTxnLcyStr(String amtTxnLcyStr) {
        this.amtTxnLcyStr = amtTxnLcyStr;
    }
    
    /**
     * @return the ratUsdIdr
     */
    public String getRatUsdIdr() {
        return ratUsdIdr;
    }

    /**
     * @param ratUsdIdr the ratUsdIdr to set
     */
    public void setRatUsdIdr(String ratUsdIdr) {
        this.ratUsdIdr = ratUsdIdr;
    }
    
    /**
     * @return the amtTxnUsd
     */
    public String getAmtTxnUsd() {
        return amtTxnUsd;
    }

    /**
     * @param amtTxnUsd the amtTxnUsd to set
     */
    public void setAmtTxnUsd(String amtTxnUsd) {
        this.amtTxnUsd = amtTxnUsd;
    }
    
    /**
     * @return the amtTxnUsdStr
     */
    public String getAmtTxnUsdStr() {
        return amtTxnUsdStr;
    }

    /**
     * @param amtTxnUsdStr the amtTxnUsdStr to set
     */
    public void setAmtTxnUsdStr(String amtTxnUsdStr) {
        this.amtTxnUsdStr = amtTxnUsdStr;
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
     * @return the dtValue
     */
    public String getDtValue() {
        return dtValue;
    }

    /**
     * @param dtValue the dtValue to set
     */
    public void setDtValue(String dtValue) {
        this.dtValue = dtValue;
    }
    
    /**
     * @return the idTxn
     */
    public String getIdTxn() {
        return idTxn;
    }

    /**
     * @param idTxn the idTxn to set
     */
    public void setIdTxn(String idTxn) {
        this.idTxn = idTxn;
    }
    
    /**
     * @return the typMsg
     */
    public String getTypMsg() {
        return typMsg;
    }

    /**
     * @param typMsg the typMsg to set
     */
    public void setTypMsg(String typMsg) {
        this.typMsg = typMsg;
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
     * @return the codAcctCustRel
     */
    public String getCodAcctCustRel() {
        return codAcctCustRel;
    }

    /**
     * @param codAcctCustRel the codAcctCustRel to set
     */
    public void setCodAcctCustRel(String codAcctCustRel) {
        this.codAcctCustRel = codAcctCustRel;
    }
    
    /**
     * @return the amtAvailUsd
     */
    public String getAmtAvailUsd() {
        return amtAvailUsd;
    }

    /**
     * @param amtAvailUsd the amtAvailUsd to set
     */
    public void setAmtAvailUsd(String amtAvailUsd) {
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
        return this.tagMessage;
    }
    
    /**
     * @param tagMessage the tagMessage to set
     */
    public void setTagMessage(String tagMessage) {
        this.tagMessage = tagMessage;
        
    }
    
}
