/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.web.menu28401;

import bdsm.util.BdsmUtil;
import bdsm.util.HttpUtil;
import bdsm.web.BaseContentAction;
import bdsm.web.Constant;
import bdsm.web.DateInquiryService;
import bdsm.web.request.RequestService;
import com.opensymphony.xwork2.ActionSupport;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;

/**
 *
 * @author v00013493
 */
public class Menu28401Action extends BaseContentAction {
    private static final String ACTION_GET_DATA = "mfcNoBook_get.action";
    private static final String ACTION_FLAG = "saveFcyTxnFlag_save.action";
    private static final String ACTION_UNFLAG = "saveFcyTxnUnflagLLD_save.action";
    private static final String NAM_MENU = "Foreign Currency Transaction Flagging";
    
    private String noAcct;
    private String refTxn;
    private String ccyTxn;
    private BigDecimal amtTxn;
    private String amtTxnStr;
    private BigDecimal ratFcyIdr;
    private String ratFcyIdrStr;
    private BigDecimal amtTxnLcy;
    private String amtTxnLcyStr;
    private BigDecimal ratUsdIdr;
    private String ratUsdIdrStr;
    private BigDecimal amtTxnUsd;
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
    private BigDecimal amtAvailUsd;
    private String noUd;
    private Integer period;
    private String idMaintainedBy;
    private String idMaintainedSpv;
    private Date dtPost;
    private String udTypeCategory;
    private String typTrx;
    private String typAcct;
    
    /**
     * 
     * @return
     */
    @SkipValidation
    public String getDate(){
        if (isValidSession()) {
            Map inquiry = new HashMap();
            DateInquiryService ds = new DateInquiryService(this.getServletRequest(), this.session, this.getBdsmHost(), this.getTokenKey(), this.getTzToken());
            inquiry = ds.getBussDate();
            if(inquiry != null){
                try {
                    Date dtBuss = (Date) inquiry.get("model");
                    this.dtPost = dtBuss;
                } catch (Exception e) {
                    getLogger().debug("DATE PARSE FAILED :" + e,e);        
                }
            }
        }
        return "datBuss";
    }
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
                getLogger().debug("typTrx : " + this.typTrx);
                getLogger().debug("typAcct : " + this.typAcct);
                
                map.put("model.compositeId.noAcct", getNoAcct());
                map.put("model.compositeId.refTxn", getRefTxn());
                map.put("model.compositeId.typMsg", getTypMsg());
                map.put("model.typTrx", this.typTrx);
                map.put("model.typAcct", this.typAcct);
                map.put("idMenu", this.session.get(Constant.C_IDMENU).toString());
                
                map.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));

                result = HttpUtil.request(getBdsmHost() + ACTION_GET_DATA, map);

                try {
                    resultMap = (HashMap)JSONUtil.deserialize(result);
                    reqResult = (String)resultMap.get("jsonStatus");
                    errorCode = (String)resultMap.get("errorCode");
                    mapModel = (HashMap)resultMap.get("model");

                    if(mapModel != null) {
                        NumberFormat formatter = new DecimalFormat("#0.00");
                        Double amtTxnDb = null;
                        Double ratFcyIdrDb = null;
                        Double amtTxnLcyDb = null;
                        Double ratUsdIdrDb = null;
                        Double amtTxnUsdDb = null;
                        ccyTxn = (String)mapModel.get("ccyTxn");
                        if(mapModel.get("amtTxn") != null){
                            amtTxnDb = Double.parseDouble(mapModel.get("amtTxn").toString());
                        } else {
                            amtTxnDb = 0.0;
                        }
                        amtTxn = BigDecimal.valueOf(amtTxnDb);
                        amtTxnStr = formatter.format(amtTxn);
                        if(mapModel.get("ratFcyIdr") != null){
                            ratFcyIdrDb = Double.parseDouble(mapModel.get("ratFcyIdr").toString());
                        } else {
                            ratFcyIdrDb = 0.0;
                        }
                        ratFcyIdr = BigDecimal.valueOf(ratFcyIdrDb);
                        if(mapModel.get("amtTxnLcy") != null){
                            amtTxnLcyDb = Double.parseDouble(mapModel.get("amtTxnLcy").toString());
                        } else {
                            amtTxnLcyDb = 0.0;
                        }
                        amtTxnLcy = BigDecimal.valueOf(amtTxnLcyDb);
                        amtTxnLcyStr = formatter.format(amtTxnLcy);
                        if(mapModel.get("ratUsdIdr") != null){
                            ratUsdIdrDb = Double.parseDouble(mapModel.get("ratUsdIdr").toString());
                        } else {
                            ratUsdIdrDb = 0.0;
                        }
                        ratUsdIdr = BigDecimal.valueOf(ratUsdIdrDb);
                        if(mapModel.get("amtTxnUsd") != null){
                            amtTxnUsdDb = Double.parseDouble(mapModel.get("amtTxnUsd").toString());
                        } else {
                            amtTxnUsdDb = 0.0;
                        }
                        amtTxnUsd = BigDecimal.valueOf(amtTxnUsdDb);
                        amtTxnUsdStr = formatter.format(amtTxnUsd);
                        txnDesc = (String)mapModel.get("txnDesc");
                        dtmTxn = (String)mapModel.get("dtmTxn");
                        dtmTxn = dtmTxn.replace("T", " ");
                        dtValue = (String)mapModel.get("dtValue");
                        dtValue = dtValue.substring(0, 10);
                        idTxn = (String)mapModel.get("idTxn");
                        txnBranch = new Integer(((Long)mapModel.get("txnBranch")).intValue());
                        typTrx = (String)mapModel.get("typTrx");
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
        map.put("model.amtTxn", String.valueOf(getAmtTxn()));
        map.put("model.amtTxnUsd", String.valueOf(getAmtTxnUsd()));
        map.put("model.noUd", getNoUd());
        map.put("model.flgJoin", getCodAcctCustRel());
        map.put("model.idMaintainedBy", getIdMaintainedBy());
        map.put("model.idMaintainedSpv", getIdMaintainedSpv());
        map.put("model.category", this.udTypeCategory);
        map.put("model.typTrx", this.typTrx);
        map.put("lld.compositeId.noAcct", getNoAcct());
        map.put("lld.compositeId.refTxn", getRefTxn());
        map.put("lld.compositeId.typMsg", getTypMsg().substring(0, 1));
        map.put("lld.typAcct", "L");
        map.put("lld.ccyTxn", getCcyTxn());
        map.put("lld.ratFcyIdr", String.valueOf(getRatFcyIdr()));
        map.put("lld.amtTxnLcy", String.valueOf(getAmtTxnLcy()));
        map.put("lld.ratUsdIdr", String.valueOf(getRatUsdIdr()));
        map.put("lld.amtTxnUsd", String.valueOf(getAmtTxnUsd()));
        map.put("lld.txnDesc", this.txnDesc);
        map.put("lld.txnBranch", getTxnBranch().toString());
        map.put("lld.status", "A");
        map.put("lld.idTxn", getIdTxn());
        map.put("lld.documentType", "20");
        map.put("lld.idMaintainedBy", getIdMaintainedBy());
        map.put("lld.idMaintainedSpv", getIdMaintainedSpv());
        map.put("namMenu", NAM_MENU);
        map.put("idMenu", this.session.get(Constant.C_IDMENU).toString());
        //map.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));
        
        getLogger().debug("MAP FLAG :" + map);
        RequestService servReq = new RequestService(session, this.getBdsmHost(), this.getTokenKey(), this.getTzToken(), null, ACTION_FLAG);
        
        
        //result = HttpUtil.request(getBdsmHost() + ACTION_FLAG, map);
        
        try {
            resultMap = (HashMap)servReq.getRequest(map, "model", "ALL");
            //resultMap = (HashMap)JSONUtil.deserialize(result);
            reqResult = (String)resultMap.get("jsonStatus");
            errorCode = (String)resultMap.get("errorCode");
        } catch (Exception ex) {
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
        map.put("model.amtTxn", String.valueOf(getAmtTxn()));
        map.put("model.amtTxnUsd", String.valueOf(getAmtTxnUsd()));
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
     * 
     * @return
     */
    public Date getDtPost() {
        return dtPost;
    }
    /**
     * 
     * @param dtPost
     */
    public void setDtPost(Date dtPost) {
        this.dtPost = dtPost;
    }

    /**
     * @return the udTypeCategory
     */
    public String getUdTypeCategory() {
        return udTypeCategory;
    }

    /**
     * @param udTypeCategory the udTypeCategory to set
     */
    public void setUdTypeCategory(String udTypeCategory) {
        this.udTypeCategory = udTypeCategory;
    }

    /**
     * @return the typTrx
     */
    public String getTypTrx() {
        return typTrx;
    }

    /**
     * @param typTrx the typTrx to set
     */
    public void setTypTrx(String typTrx) {
        this.typTrx = typTrx;
    }

    /**
     * @return the typAcct
     */
    public String getTypAcct() {
        return typAcct;
    }

    /**
     * @param typAcct the typAcct to set
     */
    public void setTypAcct(String typAcct) {
        this.typAcct = typAcct;
    }
}
