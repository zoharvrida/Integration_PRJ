/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.web.menu13302;

import bdsm.util.BdsmUtil;
import bdsm.util.HttpUtil;
import bdsm.web.BaseContentAction;
import com.opensymphony.xwork2.ActionSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;
import org.apache.struts2.util.TokenHelper;

/**
 *
 * @author v00019722
 */
public class Menu13302Action extends BaseContentAction {

    private static final String ACTION_EDIT = "fixAccessEmail_update.action";
    private static final String ACTION_LIST_REQUESTER = "fixAccessEmailReq_list.action";
    private static final String ACTION_LIST_SUPERVISOR = "fixAccessEmailSup_list.action";
    private static final String ACTION_LIST_MONITORING = "fixAccessEmailMon_list.action";
    private static final String NAM_MENU = "Email Access Registration";
    private String GRPID;
    private String schedImport;
    private String requester;
    private String supervisor;
    private String monitoring;
    private String sender;
    private String cdAccess;
    private String idMaintainedBy;
    private String idMaintainedSpv;
    private String result;
    private String frmDlg_idUser;
    private int delimiter;

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
    @SkipValidation
    public String requester() {
        /*
         * Getting Requester from requester action and dao, then serialize
         * result with ";"
         */
        StringBuilder requesterString = new StringBuilder();
        getLogger().info("[Begin] Requester Textarea()");
        try {
            if (isValidSession()) {
                Map<String, String> map = new HashMap<String, String>();
                String result;
                HashMap resultMap;
                HashMap mapModel = null;
                ArrayList reqUester;
                String temp;
                String reqs = null;
                int reQtemp = 0;
                int last;
                String reqResult = null;
                String errorCode = null;

                getLogger().info("Scheduler id : " + getSchedImport());
                setCdAccess("100");

                map.put("model.cdAccess", getCdAccess());
                map.put("model.fixEmailAccessPK.idScheduler", getSchedImport());
                map.put("model.grpId", GRPID);
                map.put("model.idMaintainedBy", getIdMaintainedBy());
                map.put("model.idMaintainedSpv", getIdMaintainedSpv());
                map.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));

                result = HttpUtil.request(getBdsmHost() + ACTION_LIST_REQUESTER, map);


                try {
                    resultMap = (HashMap) JSONUtil.deserialize(result);
                    reqResult = (String) resultMap.get("jsonStatus");
                    errorCode = (String) resultMap.get("errorCode");
                    //mapModel = (HashMap) resultMap.get("model");
                    reqUester = (ArrayList) resultMap.get("modelList");

                    
                    if (reqUester != null) {
                        reQtemp = reqUester.size();

                        for (int i = 0; i < reQtemp; i++) {
                            last = (reqUester.get(i).toString().length()) - 1;
                            temp = reqUester.get(i).toString().substring(3, last);

                            requesterString.append(temp).append(";");
                            
                        }
                        requester = requesterString.toString();

                    }
                    String tokenS = TokenHelper.setToken();
                    getLogger().debug("TokenS : " + tokenS);
               

                } catch (JSONException ex) {
                    getLogger().fatal(ex, ex);
                    return ActionSupport.ERROR;
                }

                return "requester";
            } else {
                return logout();
            }
        } catch (NullPointerException e) {
            getLogger().info("null Pointer" + e);
            requester = "";
          
            return "requester";
           
        } catch (Throwable e) {
            getLogger().fatal(e, e);
            return ActionSupport.ERROR;
        } finally {
            getLogger().info("[ End ] requester()");
        }
    }

    /**
     * 
     * @return
     */
    @SkipValidation
    public String supervisor() {
        /*
         * Getting Requester from requester action and dao, then serialize
         * result with ";"
         */
        StringBuilder supervisorString = new StringBuilder();
        getLogger().info("[Begin] Supervisor Textarea()");
        try {
            if (isValidSession()) {
                Map<String, String> map = new HashMap<String, String>();
                String result = null;
                HashMap resultMap;
                HashMap mapModel = null;
                ArrayList superVisor;
        
                String temp;
                String sups = null;
                int suPtemp = 0;
                int last;
                String reqResult = null;
                String errorCode = null;

                setCdAccess("010");

                map.put("model.cdAccess", getCdAccess());
                map.put("model.fixEmailAccessPK.idScheduler", getSchedImport());
                map.put("model.grpId", getGRPID());
                map.put("model.idMaintainedBy", getIdMaintainedBy());
                map.put("model.idMaintainedSpv", getIdMaintainedSpv());
                
                map.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));

                result = HttpUtil.request(getBdsmHost() + ACTION_LIST_SUPERVISOR, map);

                try {
                    resultMap = (HashMap) JSONUtil.deserialize(result);
                    reqResult = (String) resultMap.get("jsonStatus");
                    errorCode = (String) resultMap.get("errorCode");
                 
                    superVisor = (ArrayList) resultMap.get("modelList");

                    if (superVisor != null) {
                        suPtemp = superVisor.size();

                        for (int i = 0; i < suPtemp; i++) {
                            last = (superVisor.get(i).toString().length()) - 1;
                            temp = superVisor.get(i).toString().substring(3, last);

                            supervisorString.append(temp).append(";");
        
                        }
                        supervisor = supervisorString.toString();

                    }
                    String tokenS = TokenHelper.setToken();
                    getLogger().debug("TokenS : " + tokenS);
                   

                } catch (JSONException ex) {
                    getLogger().fatal(ex, ex);
                    return ActionSupport.ERROR;
                }

                return "supervisor";
            } else {
                return logout();
            }
        } catch (NullPointerException e) {
            getLogger().info("null Pointer" + e);
            supervisor = "";
            return "supervisor";
         
        } catch (Throwable e) {
            getLogger().fatal(e, e);
            return ActionSupport.ERROR;
        } finally {
            getLogger().info("[ End ] requester()");
        }
    }

    /**
     * 
     * @return
     */
    @SkipValidation
    public String monitoring() {
        /*
         * Getting Requester from requester action and dao, then serialize
         * result with ";"
         */
        StringBuilder monitoringString = new StringBuilder();
        getLogger().info("[Begin] Monitoring Textarea()");
        try {
            if (isValidSession()) {
                Map<String, String> map = new HashMap<String, String>();
                String result;
                HashMap resultMap;
                HashMap mapModel = null;
                ArrayList moniToring;
                String temp;
                String mons = null;
                int moNtemp = 0;
                int last;
                String reqResult = null;
                String errorCode = null;

                setCdAccess("001");

                map.put("model.cdAccess", getCdAccess());
                map.put("model.fixEmailAccessPK.idScheduler", getSchedImport());
                map.put("model.grpId", getGRPID());
                map.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));
                map.put("model.idMaintainedBy", getIdMaintainedBy());
                map.put("model.idMaintainedSpv", getIdMaintainedSpv());
                
                result = HttpUtil.request(getBdsmHost() + ACTION_LIST_MONITORING, map);

                try {
                    resultMap = (HashMap) JSONUtil.deserialize(result);
                    reqResult = (String) resultMap.get("jsonStatus");
                    errorCode = (String) resultMap.get("errorCode");
                
                    moniToring = (ArrayList) resultMap.get("modelList");

                    if (moniToring != null) {
                        moNtemp = moniToring.size();

                        for (int i = 0; i < moNtemp; i++) {
                            last = (moniToring.get(i).toString().length()) - 1;
                            temp = moniToring.get(i).toString().substring(3, last);

                            monitoringString.append(temp).append(";");

                        }
                        monitoring = monitoringString.toString();

                    }
                    String tokenS = TokenHelper.setToken();
                    getLogger().debug("TokenS : " + tokenS);
                   

                } catch (JSONException ex) {
                    getLogger().fatal(ex, ex);
                    return ActionSupport.ERROR;
                }

                return "monitoring";
            } else {
                return logout();
            }
        } catch (NullPointerException e) {
            getLogger().info("null Pointer" + e);
            monitoring = null;
            return "monitoring";
            
        } catch (Throwable e) {
            getLogger().fatal(e, e);
            return ActionSupport.ERROR;
        } finally {
            getLogger().info("[ End ] supervisor()");
        }
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

        Map<String, String> map = new HashMap<String, String>();
        HashMap resultMap;
        String reqResult = null;
        String errorCode = null;

        map.put("modelObj.fixEmailAccessPK.idScheduler", getSchedImport());
        map.put("modelObj.grpId", getGRPID());
        map.put("requeSter", getRequester());
        map.put("superVisor", getSupervisor());
        map.put("moniToring", getMonitoring());
        map.put("modelObj.idMaintainedBy", getIdMaintainedBy());
        map.put("modelObj.idMaintainedSpv", getIdMaintainedSpv());
        map.put("namMenu", NAM_MENU);
        map.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));

        result = HttpUtil.request(getBdsmHost() + ACTION_EDIT, map);

        try {
            resultMap = (HashMap) JSONUtil.deserialize(result);
            reqResult = (String) resultMap.get("jsonStatus");
            errorCode = (String) resultMap.get("errorCode");
        } catch (JSONException ex) {
            getLogger().fatal(ex, ex);
        } catch (NullPointerException e){
            getLogger().info(" NULL P : " + e.getMessage());
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
     * @return the schedImport
     */
    public String getSchedImport() {
        return schedImport;
    }

    /**
     * @param schedImport the schedImport to set
     */
    public void setSchedImport(String schedImport) {
        this.schedImport = schedImport;
    }

    /**
     * @return the requester
     */
    public String getRequester() {
        return requester;
    }

    /**
     * @param requester the requester to set
     */
    public void setRequester(String requester) {
        this.requester = requester;
    }

    /**
     * @return the supervisor
     */
    public String getSupervisor() {
        return supervisor;
    }

    /**
     * @param supervisor the supervisor to set
     */
    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    /**
     * @return the monitoring
     */
    public String getMonitoring() {
        return monitoring;
    }

    /**
     * @param monitoring the monitoring to set
     */
    public void setMonitoring(String monitoring) {
        this.monitoring = monitoring;
    }

    /**
     * @return the sender
     */
    public String getSender() {
        return sender;
    }

    /**
     * @param sender the sender to set
     */
    public void setSender(String sender) {
        this.sender = sender;
    }

    /**
     * @return the cdAccess
     */
    public String getCdAccess() {
        return cdAccess;
    }

    /**
     * @param cdAccess the cdAccess to set
     */
    public void setCdAccess(String cdAccess) {
        this.cdAccess = cdAccess;
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
     * @return the delimiter
     */
    public int getDelimiter() {
        return delimiter;
    }

    /**
     * @param delimiter the delimiter to set
     */
    public void setDelimiter(int delimiter) {
        this.delimiter = delimiter;
    }

    /**
     * @return the GRPID
     */
    public String getGRPID() {
        return GRPID;
    }

    /**
     * @param GRPID the GRPID to set
     */
    public void setGRPID(String GRPID) {
        this.GRPID = GRPID;
    }

    /**
     * @return the frmDlg_idUser
     */
    public String getFrmDlg_idUser() {
        return frmDlg_idUser;
    }

    /**
     * @param frmDlg_idUser the frmDlg_idUser to set
     */
    public void setFrmDlg_idUser(String frmDlg_idUser) {
        this.frmDlg_idUser = frmDlg_idUser;
    }
}
