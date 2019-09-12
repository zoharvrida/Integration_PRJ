/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.web.menu28202;

import bdsm.fcr.model.ChAcctMast;
import bdsm.fcr.model.ChProdMast;
import bdsm.model.MenuRedirect;
import bdsm.model.MenuRedirectionPK;
import bdsm.model.ScreenMsg;
import bdsm.util.ClassConverterUtil;
import bdsm.web.AccountInquiryService;
import bdsm.web.BaseContentAction;
import bdsm.web.Constant;
import bdsm.web.ProductInquiryService;
import bdsm.web.ScreenMsgService;
import bdsm.web.ValasMidRateService;
import bdsm.web.request.JSONService;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;

/**
 *
 * @author 00110310
 */
public class Menu28202Action extends BaseContentAction {

    private static final String persisterTag = "LLD_LIMIT_AMOUNT";
    //private static final String deviationTag = "LLD_DEVIATION_RATE";
    //private static final String tagMFC = "MFCLMTAMT";
    //private static final String VALAS = "VALAS";
    private static final String LLD = "LLD";
    private static final String NO_ROUTE = "NO_ROUTE";
    private static final String LLDMessagenonUD = "TXT_MON_LLD_NO_UD";
    private static final String LLDMessageUD = "TXT_MON_LLD_UD";
    //private static final String VALASMessagenonUD = "TXT_MON_VAL_NO_UD";
    //private static final String VALASMessageUD = "TXT_MON_VAL_UD";
    private String ccyName;
    private String ccyRate;
    private String ccyMidRate;
    private String amtTxn;
    private String noAcct;
    private String acctTitle;
    private String balAvail;
    private String reason;
    private String directLink;
    private ChAcctMast model;
    private ChProdMast product;
    private String idMenuRed;
    private String contentData;
    private String prodData;
    private String drCr;
    private String codCcy;
    private ScreenMsg scrMsg;
    private MenuRedirect menuRed;
    private String scrnData;
    //private JSONService JSONUtil = new JSONService();

    /**
     * 
     * @return
     */
    @Override
    public String exec() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
                    
                    
                        // Value failed to calculate
    }

    /**
     * 
     * @return
     */
    @Override
    public String doAdd() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * 
     * @return
     */
    @Override
    public String doEdit() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * 
     * @return
     */
    @Override
    public String doDelete() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * 
     * @return
     */
    @SkipValidation
    public String getBalAvailable() {
        Map acctMap = new HashMap();
        Map prod = new HashMap();
        if (isValidSession()) {
            try {
            getLogger().info("[Begin] get Balance MIDRATE-SERVICE()");
            AccountInquiryService ais = new AccountInquiryService(this.getServletRequest(), this.session, this.getBdsmHost(), this.getTokenKey(), this.getTzToken());
            acctMap = ais.getAccount(this.noAcct);

            Map acctMaps = (Map) acctMap.get("compositeId");
            getLogger().info("PK :" + acctMaps);
			if (acctMaps != null) {
                
                if (acctMap != null) {
                    getLogger().debug("MAP :" + acctMap);
                    acctTitle = acctMap.get("codAcctTitle").toString();
                    double d = Double.parseDouble(acctMap.get("balAvailable").toString());
                    balAvail = String.format("%f", d);
                    noAcct = acctMaps.get("codAcctNo").toString();
                        model = new ChAcctMast();
                        product = new ChProdMast();
                    try {
                    ClassConverterUtil.MapToClass(acctMap, model);
                            ProductInquiryService inq = new ProductInquiryService(this.getServletRequest(), session, this.getBdsmHost(), this.getTokenKey(), this.getTzToken());
                            prod = inq.getProduct(model.getCodProd().toString());
                            if(prod != null){
                                getLogger().debug("PRODUCT :" + product);
                                ClassConverterUtil.MapToClass(prod, product);
                            }
                    } catch (Exception e) {
                        getLogger().debug("logg :" + e,e);
                    }
                        
                    try {
                        setContentData(JSONUtil.serialize(getModel()));
                            setProdData(JSONUtil.serialize(product));
                        getLogger().debug("DATA :" + contentData);
                    } catch (JSONException ex) {
                        getLogger().debug("FAILED TO SERIALIZE");
                        getLogger().debug(ex,ex);
                    }
                } else {
                    getLogger().debug("MAPnull????");
                }
            } else {
                setErrorMessage(getText(ais.getErrorCode()));
                reason = getText(ais.getErrorCode());
            }
            } catch (Exception e) {
                getLogger().debug("EXCEPTION :" + e,e);
            }
            getLogger().info("[END] get Balance MIDRATE-SERVICE()");
        }
        return "balAvails";
    }

    /**
     * 
     * @return
     */
    @SkipValidation
    public String getMidRate() {
        Map mapModel = new HashMap();
        
        Map resultMap = new HashMap();
        if (isValidSession()) {
            ValasMidRateService midRate = new ValasMidRateService(session, this.getBdsmHost(), this.getTokenKey(), this.getTzToken(), this.getServletRequest());
            getLogger().info("[Begin] getDate MIDRATE-SERVICE()");
            try {
                mapModel = midRate.getMidValue(ccyName);
                if (mapModel != null) {
                    getLogger().debug("RATE MAP :" + mapModel);
                    try {
                        ccyMidRate = Double.toString((Double) mapModel.get("midRate"));
                    } catch (Exception e) {
                        getLogger().error(e, e);
                        reason = "Failed get Rate for null value";
                    }
                } else {
                    setErrorMessage(getText(midRate.getErrorCode()));
                    reason = getText(midRate.getErrorCode());
                }
            } catch (Exception e) {
                getLogger().error(e, e);
            }
            getLogger().info("[END] getDate MIDRATE-SERVICE()");
        }
        return "midRate";
    }

    /**
     * 
     * @return
     */
    public String getCalc(){
        Map mapModel = new HashMap();
        Map menuGet = new HashMap();
        String paramNum = null;
        if (isValidSession()) {
            ValasMidRateService midRate = new ValasMidRateService(session, this.getBdsmHost(), this.getTokenKey(), this.getTzToken(), this.getServletRequest());
            getLogger().info("[Begin] getCalc MIDRATE-SERVICE()");
            try {
                mapModel = midRate.getMidCalc(amtTxn, ccyName);
                if (mapModel != null) {
                    getLogger().debug("RATE MAP :" + mapModel);
                    amtTxn = mapModel.get("amtTxn").toString();
                    paramNum = parseParameter(persisterTag);
                    BigDecimal paramDec = new BigDecimal(paramNum);
                    String idMenus = this.session.get(Constant.C_IDMENU).toString();
                    this.drCr = "";
                    BigDecimal val = new BigDecimal(amtTxn);
                    if (paramDec.compareTo(val) > 0) {
                        // No LLD UD
                            scrMsg = getScreenMsgresp(LLDMessagenonUD);
                            //paramNum = parseParameter(tagMFC);
                            //BigDecimal paramVal = new BigDecimal(paramNum);
                            //if (paramVal.compareTo(val) < 0) {
                                // UD Valas
                                //scrMsg = getScreenMsgresp(VALASMessageUD);
                                menuRed = new MenuRedirect();
                            //} else {
                                //scrMsg = getScreenMsgresp(VALASMessagenonUD);
                                //menuRed = new MenuRedirect();
                            this.directLink = NO_ROUTE;
                            //}
                        /*if (this.ccyName.equalsIgnoreCase("IDR") && this.drCr.equalsIgnoreCase("Debit")) {
                            scrMsg = getScreenMsgresp(LLDMessagenonUD);
                            menuRed = new MenuRedirect();
                            this.directLink = NO_ROUTE;
                    } */

                    } else {
                        menuRed = getMenuMsg(idMenus, LLD);
                        scrMsg = getScreenMsgresp(LLDMessageUD);
                        this.directLink = LLD;
                    }
                } else {
                    setErrorMessage(getText(midRate.getErrorCode()));
                    reason = getText(midRate.getErrorCode());
                }
                setDirectLink(JSONUtil.serialize(menuRed));
                setScrnData(JSONUtil.serialize(scrMsg));
            } catch (Exception e) {
                getLogger().error(e, e);
            }
            getLogger().info("[END] getCalc MIDRATE-SERVICE()");
        }
        return "calcAmt";
    }
    /**
     * 
     * @param tag
     * @return
     */
    @SkipValidation
    public String parseParameter(String tag) {
        Map mapScreen = new HashMap();
        String paramNum = null;
        ScreenMsgService sms = new ScreenMsgService(this.getServletRequest(), session, this.getBdsmHost(), this.getTokenKey(), this.getTzToken());
        getLogger().info("[Begin] Detect LLD Param MIDRATE-SERVICE()");
        mapScreen = sms.getMessage(tag);
        if (mapScreen != null) {
            getLogger().debug("LLD MAP :" + mapScreen);
            try {
                paramNum = mapScreen.get("values").toString();
            } catch (Exception e) {
                getLogger().debug(e, e);
                getLogger().debug("VALUES int");
                try {
                    paramNum = mapScreen.get("valuesInt").toString();
                } catch (Exception ex) {
                    getLogger().debug(ex, ex);
                    getLogger().debug("No Value to Compare");
                    paramNum = amtTxn;
                }
            }
        }
        return paramNum;
    }
    /**
     * 
     * @param tag
     * @return
     */
    @SkipValidation
    public ScreenMsg getScreenMsgresp(String tag) {
        Map mapScreen = new HashMap();
        Map msgMap = new HashMap();
        ScreenMsg msg = new ScreenMsg();
        ScreenMsgService sms = new ScreenMsgService(this.getServletRequest(), session, this.getBdsmHost(), this.getTokenKey(), this.getTzToken());
        getLogger().info("[Begin] Detect LLD Screen Message MIDRATE-SERVICE()");
        mapScreen = sms.getMessage(tag);
        if (mapScreen != null) {
            getLogger().debug("LLD MAP :" + mapScreen);
            try {
                msgMap = (HashMap) mapScreen.get("model");
                ClassConverterUtil.MapToClass(msgMap, msg);
            } catch (Exception e) {
                getLogger().debug(e, e);
            }
        }
        return msg;
    }
    /**
     * 
     * @param tag
     * @param alias
     * @return
     */
    @SkipValidation
    public MenuRedirect getMenuMsg(String tag, String alias) {
        Map mapScreen = new HashMap();
        Map msgMap = new HashMap();
        Map pk = new HashMap();
        MenuRedirect msg = new MenuRedirect();
        MenuRedirectionPK pkMsg = new MenuRedirectionPK();
        ScreenMsgService sms = new ScreenMsgService(this.getServletRequest(), session, this.getBdsmHost(), this.getTokenKey(), this.getTzToken());
        getLogger().info("[Begin] Detect LLD Menu MIDRATE-SERVICE()");
        mapScreen = sms.getMenuRed(tag,alias);
        if (mapScreen != null) {
            getLogger().debug("LLD MAP :" + mapScreen);
            try {
                msgMap = (HashMap) mapScreen.get("model");
                pk = (HashMap) msgMap.get("pk");
                ClassConverterUtil.MapToClass(msgMap, msg);
                ClassConverterUtil.MapToClass(pk, pkMsg);
                msg.setPk(pkMsg);
            } catch (Exception e) {
                getLogger().debug(e, e);
            }
        }
        return msg;
    }
    /**
     * @return the ccyName
     */
    public String getCcyName() {
        return ccyName;
    }

    /**
     * @param ccyName the ccyName to set
     */
    public void setCcyName(String ccyName) {
        this.ccyName = ccyName;
    }

    /**
     * @return the ccyRate
     */
    public String getCcyRate() {
        return ccyRate;
    }

    /**
     * @param ccyRate the ccyRate to set
     */
    public void setCcyRate(String ccyRate) {
        this.ccyRate = ccyRate;
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
     * @return the acctTitle
     */
    public String getAcctTitle() {
        return acctTitle;
    }

    /**
     * @param acctTitle the acctTitle to set
     */
    public void setAcctTitle(String acctTitle) {
        this.acctTitle = acctTitle;
    }

    /**
     * @param balAvail the balAvail to set
     */
    public void setBalAvail(String balAvail) {
        this.balAvail = balAvail;
    }

    /**
     * @return the balAvail
     */
    public String getBalAvail() {
        return balAvail;
    }

    /**
     * @return the reason
     */
    public String getReason() {
        return reason;
    }

    /**
     * @param reason the reason to set
     */
    public void setReason(String reason) {
        this.reason = reason;
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
     * @return the ccyMidRate
     */
    public String getCcyMidRate() {
        return ccyMidRate;
    }

    /**
     * @param ccyMidRate the ccyMidRate to set
     */
    public void setCcyMidRate(String ccyMidRate) {
        this.ccyMidRate = ccyMidRate;
    }
    /**
     * 
     * @return
     */
    public String getDirectLink() {
        return directLink;
    }
    /**
     * 
     * @param directLink
     */
    public void setDirectLink(String directLink) {
        this.directLink = directLink;
    }
    /**
     * 
     * @return
     */
    public ChAcctMast getModel() {
        return model;
    }
    /**
     * 
     * @param model
     */
    public void setModel(ChAcctMast model) {
        this.model = model;
    }
    /**
     * 
     * @return
     */
    public String getIdMenuRed() {
        return idMenuRed;
    }
    /**
     * 
     * @param idMenuRed
     */
    public void setIdMenuRed(String idMenuRed) {
        this.idMenuRed = idMenuRed;
    }
    /**
     * 
     * @return
     */
    public String getContentData() {
        return contentData;
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
    public ChProdMast getProduct() {
        return product;
    }
    /**
     * 
     * @param product
     */
    public void setProduct(ChProdMast product) {
        this.product = product;
    }
    /**
     * 
     * @return
     */
    public String getProdData() {
        return prodData;
    }
    /**
     * 
     * @param prodData
     */
    public void setProdData(String prodData) {
        this.prodData = prodData;
    }
    /**
     * 
     * @return
     */
    public String getDrCr() {
        return drCr;
    }
    /**
     * 
     * @param drCr
     */
    public void setDrCr(String drCr) {
        this.drCr = drCr;
    }
    /**
     * 
     * @return
     */
    public String getCodCcy() {
        return codCcy;
    }
    /**
     * 
     * @param codCcy
     */
    public void setCodCcy(String codCcy) {
        this.codCcy = codCcy;
    }
    /**
     * 
     * @return
     */
    public ScreenMsg getScrMsg() {
        return scrMsg;
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
    public String getScrnData() {
        return scrnData;
    }
    /**
     * 
     * @param scrnData
     */
    public void setScrnData(String scrnData) {
        this.scrnData = scrnData;
    }
    /**
     * 
     * @return
     */
    public MenuRedirect getMenuRed() {
        return menuRed;
    }
    /**
     * 
     * @param menuRed
     */
    public void setMenuRed(MenuRedirect menuRed) {
        this.menuRed = menuRed;
    }
}
