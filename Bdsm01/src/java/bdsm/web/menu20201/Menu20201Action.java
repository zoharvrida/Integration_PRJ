package bdsm.web.menu20201;

import bdsm.model.ScreenMsg;
import bdsm.util.BdsmUtil;
import bdsm.util.ClassConverterUtil;
import bdsm.util.HttpUtil;
import bdsm.web.BaseContentAction;
import bdsm.web.ScreenMsgService;

import com.opensymphony.xwork2.ActionSupport;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;

/**
 * 
 * @author bdsm
 */
public class Menu20201Action extends BaseContentAction {
    private static final String GET_CUSTMAST = "ciCustmast_get.action";
    private static final String GET_CH_ACCT_MAST = "fcrChAcctMast_get.action";
    private static final String GET_TD_ACCT_MAST = "fcrTdAcctMast_get.action";

    private String noAccount;
    private Integer flgAcct;
    private String tagMessage;


    /**
     * 
     * @return
     */
    @SkipValidation
    public String validateAcc() {
        getLogger().info("[Begin] validateAcc()");
        try {
            if (isValidSession()) {

                getLogger().debug("noAccount : " + getNoAccount());

                if (getNoAccount().toUpperCase().startsWith("CIF:")) {
                    if (isNumeric(getNoAccount().substring(4))) {
                        flgAcct = 2;

                        Map<String, String> map = new HashMap<String, String>();
                        String result;
                        HashMap resultMap;
                        HashMap mapModel;
                        HashMap mapCompId;
                        String reqResult = null;
                        String errorCode = null;

                        getLogger().debug("codCustId : " + getNoAccount().substring(4));

                        map.put("model.compositeId.codCustId", getNoAccount().substring(4).trim());
                        map.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));

                        result = HttpUtil.request(getBdsmHost() + GET_CUSTMAST, map);

                        try {
                            resultMap = (HashMap) JSONUtil.deserialize(result);
                            reqResult = (String) resultMap.get("jsonStatus");
                            errorCode = (String) resultMap.get("errorCode");
                            mapModel = (HashMap) resultMap.get("model");
                            if (mapModel != null) {
                                flgAcct = 0;
                            }
                        } catch (JSONException ex) {
                            getLogger().fatal(ex, ex);
                            return ActionSupport.ERROR;
                        }
                    } else {
                        flgAcct = 4;
                    }
                } else {
                    if (isNumeric(getNoAccount())) {
                        flgAcct = 3;

                        Map<String, String> map = new HashMap<String, String>();
                        String result;
                        HashMap resultMap;
                        HashMap mapModel;
                        HashMap mapCompId;
                        String reqResult = null;
                        String errorCode = null;

                        getLogger().debug("codAcctNo : " + getNoAccount());

                        map.put("model.compositeId.codAcctNo", getNoAccount());
                        map.put("model.compositeId.flgMntStatus", "A");
                        map.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));

                        result = HttpUtil.request(getBdsmHost() + GET_CH_ACCT_MAST, map);

                        try {
                            resultMap = (HashMap) JSONUtil.deserialize(result);
                            reqResult = (String) resultMap.get("jsonStatus");
                            errorCode = (String) resultMap.get("errorCode");
                            mapModel = (HashMap) resultMap.get("model");
                            if (mapModel != null) {
                                flgAcct = 1;
                            }
                        } catch (JSONException ex) {
                            getLogger().fatal(ex, ex);
                            return ActionSupport.ERROR;
                        }

                        map = new HashMap<String, String>();
                        map.put("model.compositeId.codAcctNo", getNoAccount());
                        map.put("model.compositeId.flgMntStatus", "A");
                        map.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));

                        result = HttpUtil.request(getBdsmHost() + GET_TD_ACCT_MAST, map);

                        try {
                            resultMap = (HashMap) JSONUtil.deserialize(result);
                            reqResult = (String) resultMap.get("jsonStatus");
                            errorCode = (String) resultMap.get("errorCode");
                            mapModel = (HashMap) resultMap.get("model");
                            if (mapModel != null) {
                                flgAcct = 1;
                            }
                        } catch (JSONException ex) {
                            getLogger().fatal(ex, ex);
                            return ActionSupport.ERROR;
                        }
                    } else {
                        flgAcct = 5;
                    }
                }

                return "validateAcc";
            } else {
                return logout();
            }
        } catch (Throwable e) {
            getLogger().fatal(e, e);
            return ActionSupport.ERROR;
        } finally {
            getLogger().info("[ End ] validateAcc()");
        }
    }

    /**
     * 
     * @return
     */
    @SkipValidation
    public String retrieveMessage() {
        this.getLogger().info("[Begin] retrieveMessage()");
        try {
            if (this.isValidSession()) {
                ScreenMsg scrMsg = this.getScreenMsg(this.tagMessage);
                if ((scrMsg == null) || (StringUtils.isBlank(scrMsg.getMsgTag()))) 
                    this.tagMessage = "";
                else
                    this.tagMessage = scrMsg.getMessage();
                
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
            this.getLogger().info("[ End ] retrieveMessage()");
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
     * 
     * @return
     */
    public String exec() {
        return ActionSupport.SUCCESS;
    }

    /**
     * 
     * @return
     */
    @Override
    public String doAdd() {
        throw new UnsupportedOperationException("Not supported yet.");
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

    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * @return the noAccount
     */
    public String getNoAccount() {
        return noAccount;
    }

    /**
     * @param noAccount the noAccount to set
     */
    public void setNoAccount(String noAccount) {
        this.noAccount = noAccount;
    }

    /**
     * @return the flgAcct
     */
    public Integer getFlgAcct() {
        return flgAcct;
    }

    /**
     * @param flgAcct the flgAcct to set
     */
    public void setFlgAcct(Integer flgAcct) {
        this.flgAcct = flgAcct;
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
