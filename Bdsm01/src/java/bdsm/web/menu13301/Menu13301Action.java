/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.web.menu13301;


import bdsm.util.BdsmUtil;
import bdsm.util.HttpUtil;
import bdsm.web.BaseContentAction;
import com.opensymphony.xwork2.ActionSupport;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.interceptor.validation.SkipValidation;
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;

/**
 *
 * @author v00019722
 */
public class Menu13301Action extends BaseContentAction {

    private static final String ACTION_EDIT_PASSWORDE = "fixSchedulerXtractPassword_update.action";
    private static final String ACTION_EDIT_PASSWORDI = "fixSchedulerImportPassword_update.action";
    private static final String ACTION_GET_PASSWORDE = "fixSchedulerXtractPassword_get.action";
    private static final String ACTION_GET_PASSWORDI = "fixSchedulerImportPassword_get.action";
    private static final String ACTION_EDIT_E = "fixSchedulerXtract_update.action";
    private static final String ACTION_EDIT_I = "fixSchedulerImport_update.action";
    private static final String NAM_MENU = "Fix Scheduler Maintenance";

    private String namschedProfile;    
    private String idTemplate;
    private String idScheduler;
    private String keyEncrypt;
    private String keyDecrypt;
    private String flgStatus;
    private String chgStat;
    private String idMaintainedBy;
    private String idMaintainedSpv;
    private String schedStatus;
    private String statusChosen;
    private String typeS;
    private String schedProfile;
    private String schedStat;
    private String idFixTemplate;
    private String oldKey;
    private String idschedProfile;
    private String reset;
    private String validatePass;
    private String password;
    
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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     * @return
     */
    @Override
    public String doEdit() {
        String result;
        HashMap resultMap;
        String reqResult = null;
        String errorCode = null;

        
        getLogger().debug("scheduler Name :" + getNamschedProfile());
        getLogger().debug("reset State :" + getReset());
        if (typeS.equalsIgnoreCase("Extract")) {
            result = Extract();
        } else if (typeS.equalsIgnoreCase("Import")) {
            result = Import();
        } else {
            result = null;
        }
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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     * @return
     */
    public String Extract() {
        String result = null;

        getLogger().debug("reset State :" + getReset());
        Map<String, String> map = new HashMap<String, String>();

        if ("Status".equals(chgStat)) {
                
            map.put("model.fixSchedulerPK.idScheduler", getIdschedProfile());
            map.put("model.fixSchedulerPK.idTemplate", getIdFixTemplate());
            map.put("model.fixSchedulerPK.namScheduler", getNamschedProfile());
            map.put("resetState",getReset());
            if (getSchedStat().equalsIgnoreCase("Inactive")) {
                map.put("model.flgStatus", "I");
            } else if (getSchedStat().equalsIgnoreCase("Active")) {
                map.put("model.flgStatus", "A");
            }
            map.put("model.idMaintainedBy", getIdMaintainedBy());
            map.put("model.idMaintainedSpv", getIdMaintainedSpv());
            map.put("namMenu", NAM_MENU);
            map.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));
            result = HttpUtil.request(getBdsmHost() + ACTION_EDIT_E, map);
            
        } else {
            getLogger().debug("key : " + getOldKey());
            getLogger().debug("id_Template : " + getIdFixTemplate());
            getLogger().debug("Validate : " + validatePass);
            map.put("resetState",getReset());
            map.put("Key", getOldKey());
            map.put("model.fixSchedulerPK.idScheduler", getIdschedProfile());
            map.put("model.fixSchedulerPK.idTemplate", getIdFixTemplate());
            map.put("model.fixSchedulerPK.namScheduler", getNamschedProfile());
            map.put("model.keyEncrypt", getKeyEncrypt());
            map.put("model.idMaintainedBy", getIdMaintainedBy());
            map.put("model.idMaintainedSpv", getIdMaintainedSpv());
            map.put("namMenu", NAM_MENU);
            map.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));
            if("1".equals(validatePass)){
               result = HttpUtil.request(getBdsmHost() + ACTION_GET_PASSWORDE, map);
            } else {
               result = HttpUtil.request(getBdsmHost() + ACTION_EDIT_PASSWORDE, map);
            }
        }
        return result;
    }

    /**
     * 
     * @return
     */
    public String Import() {
        String result = null;
        Map<String, String> map = new HashMap<String, String>();

        if ("Status".equals(chgStat)) {

            map.put("modelImport.fixSchedulerPK.idScheduler", getIdschedProfile());
            map.put("modelImport.fixSchedulerPK.idTemplate", getIdFixTemplate());
            map.put("modelImport.fixSchedulerPK.namScheduler", getNamschedProfile());
            if (getSchedStat().equalsIgnoreCase("Inactive")) {
                map.put("modelImport.flgStatus", "I");
            } else if (getSchedStat().equalsIgnoreCase("Active")) {
                map.put("modelImport.flgStatus", "A");
            }
            map.put("modelImport.idMaintainedBy", getIdMaintainedBy());
            map.put("modelImport.idMaintainedSpv", getIdMaintainedSpv());
            map.put("namMenu", NAM_MENU);
            map.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));
            result = HttpUtil.request(getBdsmHost() + ACTION_EDIT_I, map);

        } else {
            getLogger().debug("key : " + getOldKey());
            getLogger().debug("id_Template : " + getIdFixTemplate());
            getLogger().debug("Validate : " + validatePass);
            map.put("resetState",getReset());
            map.put("Key", getOldKey());
            map.put("modelImport.fixSchedulerPK.idScheduler", getIdschedProfile());
            map.put("modelImport.fixSchedulerPK.idTemplate", getIdFixTemplate());
            map.put("modelImport.fixSchedulerPK.namScheduler", getNamschedProfile());
            map.put("modelImport.keyDecrypt", getKeyEncrypt());
            map.put("modelImport.idMaintainedBy", getIdMaintainedBy());
            map.put("modelImport.idMaintainedSpv", getIdMaintainedSpv());
            map.put("namMenu", NAM_MENU);
            map.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));
            if ("1".equals(validatePass)) {
                result = HttpUtil.request(getBdsmHost() + ACTION_GET_PASSWORDI, map);
            } else {
                result = HttpUtil.request(getBdsmHost() + ACTION_EDIT_PASSWORDI, map);
            }
        }
        return result;
    }
    
    /**
     * 
     * @return
     */
    @SkipValidation
    public String validation() {
        String result = null;
        getLogger().debug("scheduler Name :" + getNamschedProfile());
        //getLogger().debug("PASS :" + oldKey);
        getLogger().debug("STAT :" + chgStat);
        getLogger().debug("ID Template :" + idFixTemplate);
        try {
            if (isValidSession()) {
                HashMap resultMap;
                String encrypt = null;
                String oldEncrypt = null;
                String reqResult = null;
                String errorCode = null;
                password = "";
                
                if (typeS.equalsIgnoreCase("Extract")) {
                    result = Extract();
                } else if (typeS.equalsIgnoreCase("Import")) {
                    result = Import();
                } else {
                    result = null;
                }
                getLogger().debug("result : " + result);
                try {
                    resultMap = (HashMap) JSONUtil.deserialize(result);
                    reqResult = (String) resultMap.get("jsonStatus");
                    errorCode = (String) resultMap.get("errorCode");
                    
                    encrypt = (String) resultMap.get("oldEncrypt");
                    oldEncrypt = (String) resultMap.get("newEncrypt");
                    
                    getLogger().debug("Result :" + reqResult);
                    getLogger().debug("Code :" + errorCode);
                    
                    if (encrypt.equalsIgnoreCase(oldEncrypt)) {
                        password = "";
                    } else {
                        password = getText(errorCode);
                        //addActionMessage(getText(errorCode));//"Wrong Password";
                    }
                    getLogger().debug("STATUS :" + password);
                } catch (JSONException ex) {
                    getLogger().fatal(ex, ex);
                } catch (Exception e) {
                    getLogger().debug("ANY EX :" + e);
                }
                if (reqResult.equals(ActionSupport.ERROR)) {
                    addActionError(getText(errorCode));
                } else if (reqResult.equals(ActionSupport.SUCCESS)) {
                    addActionMessage(getText(errorCode));
                }
                return "password";
            } else {
                return logout();
            }
        } catch (NullPointerException e) {
            getLogger().debug("null Pointer" + e);
            password = "";
            return "password";
        } catch (Throwable e) {
            getLogger().fatal(e, e);
            return ActionSupport.ERROR;
        } finally {
            getLogger().debug("[ End ] PASSWORD()");
        }
        //return ActionSupport.SUCCESS;
    }
    
    /**
     * 
     * @return
     */
    @SkipValidation
    public String refreshParameter() {
        getLogger().info("[Begin] refreshParameter()");
        try {
            if (isValidSession()) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("methodName", "refreshParameter");
                map.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));
                HttpUtil.request(getBdsmHost() + "fixSchedulerXtractPassword_callMethod.action", map);
                
                java.io.PrintWriter w = org.apache.struts2.ServletActionContext.getResponse().getWriter();
                w.write("<div id=\"divMessage\" /><script type=\"text/javascript\"> alert(\"Success Refresh\"); </script>");
                
                return NONE;
            }
            else
                return logout();
        }
        catch (Throwable e) {
            getLogger().fatal(e, e);
            return ActionSupport.ERROR;
        }
        finally {
            getLogger().info("[ End ] refreshParameter()");
        }
    }
    
    /**
     * @return the namschedProfile
     */
    public String getNamschedProfile() {
        return namschedProfile;
    }

    /**
     * @param namschedProfile the namschedProfile to set
     */
    public void setNamschedProfile(String namschedProfile) {
        this.namschedProfile = namschedProfile;
    }

    /**
     * @return the idTemplate
     */
    public String getIdTemplate() {
        return idTemplate;
    }

    /**
     * @param idTemplate the idTemplate to set
     */
    public void setIdTemplate(String idTemplate) {
        this.idTemplate = idTemplate;
    }

    /**
     * @return the idScheduler
     */
    public String getIdScheduler() {
        return idScheduler;
    }

    /**
     * @param idScheduler the idScheduler to set
     */
    public void setIdScheduler(String idScheduler) {
        this.idScheduler = idScheduler;
    }

    /**
     * @return the keyEncrypt
     */
    public String getKeyEncrypt() {
        return keyEncrypt;
    }

    /**
     * @param keyEncrypt the keyEncrypt to set
     */
    public void setKeyEncrypt(String keyEncrypt) {
        this.keyEncrypt = keyEncrypt;
    }

    /**
     * @return the flgStatus
     */
    public String getFlgStatus() {
        return flgStatus;
    }

    /**
     * @param flgStatus the flgStatus to set
     */
    public void setFlgStatus(String flgStatus) {
        this.flgStatus = flgStatus;
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
     * @return the schedStatus
     */
    public String getSchedStatus() {
        return schedStatus;
    }

    /**
     * @param schedStatus the schedStatus to set
     */
    public void setSchedStatus(String schedStatus) {
        this.schedStatus = schedStatus;
    }

    /**
     * @return the statusChosen
     */
    public String getStatusChosen() {
        return statusChosen;
    }

    /**
     * @param statusChosen the statusChosen to set
     */
    public void setStatusChosen(String statusChosen) {
        this.statusChosen = statusChosen;
    }

    /**
     * @return the typeS
     */
    public String getTypeS() {
        return typeS;
    }

    /**
     * @param typeS the typeS to set
     */
    public void setTypeS(String typeS) {
        this.typeS = typeS;
    }
    /**
     * @return the schedProfile
     */
    public String getSchedProfile() {
        return schedProfile;
    }

    /**
     * @param schedProfile the schedProfile to set
     */
    public void setSchedProfile(String schedProfile) {
        this.schedProfile = schedProfile;
    }

    /**
     * @return the schedStat
     */
    public String getSchedStat() {
        return schedStat;
    }

    /**
     * @param schedStat the schedStat to set
     */
    public void setSchedStat(String schedStat) {
        this.schedStat = schedStat;
    }

    /**
     * @return the keyDecrypt
     */
    public String getKeyDecrypt() {
        return keyDecrypt;
    }

    /**
     * @param keyDecrypt the keyDecrypt to set
     */
    public void setKeyDecrypt(String keyDecrypt) {
        this.keyDecrypt = keyDecrypt;
    }

    /**
     * @return the idFixTemplate
     */
    public String getIdFixTemplate() {
        return idFixTemplate;
    }

    /**
     * @param idFixTemplate the idFixTemplate to set
     */
    public void setIdFixTemplate(String idFixTemplate) {
        this.idFixTemplate = idFixTemplate;
    }

    /**
     * @return the oldKey
     */
    public String getOldKey() {
        return oldKey;
    }

    /**
     * @param oldKey the oldKey to set
     */
    public void setOldKey(String oldKey) {
        this.oldKey = oldKey;
    }

    /**
     * @return the chgStat
     */
    public String getChgStat() {
        return chgStat;
    }

    /**
     * @param chgStat the chgStat to set
     */
    public void setChgStat(String chgStat) {
        this.chgStat = chgStat;
    }

    /**
     * @return the idschedProfile
     */
    public String getIdschedProfile() {
        return idschedProfile;
    }

    /**
     * @param idschedProfile the idschedProfile to set
     */
    public void setIdschedProfile(String idschedProfile) {
        this.idschedProfile = idschedProfile;
    }

    /**
     * @return the reset
     */
    public String getReset() {
        return reset;
    }

    /**
     * @param reset the reset to set
     */
    public void setReset(String reset) {
        this.reset = reset;
    }

    /**
     * @return the validatePass
     */
    public String getValidatePass() {
        return validatePass;
    }

    /**
     * @param validatePass the validatePass to set
     */
    public void setValidatePass(String validatePass) {
        this.validatePass = validatePass;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
