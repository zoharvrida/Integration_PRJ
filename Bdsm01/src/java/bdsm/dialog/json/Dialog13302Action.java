/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.dialog.json;

import bdsm.util.BdsmUtil;
import bdsm.util.HttpUtil;
import com.opensymphony.xwork2.ActionSupport;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;

/**
 *
 * @author v00019722
 */
public class Dialog13302Action extends BaseDialogAction {

    private final String ACTION_LIST_GROUP = "fixAccessEmail_list.action";
    private final String ACTION_LIST_SCHEDULER = "fixSchedulerImportGroup_list.action";
    
    private String gRPID;
    private String schedImport;
    private String sender;
    private String cdAccess;
    private String idMaintainedBy;
    private String idMaintainedSpv;
    private String result;
    private List grpiDList;
    private List schedImportList;
    private String GRPID;

    /**
     * 
     * @return
     */
    public String getListType()
    {
        getListGroup();
            if (getgRPID() != null)
            {
               getListImport();
            }  
        return ActionSupport.SUCCESS;
    }
    
    /**
     * 
     * @return
     */
    @SkipValidation
    public String getListGroup() {
        getLogger().info("[Begin] getGroupList()");
        //schedulerList = new ArrayList<ListProfile>();
        try {
            if (isValidSession()) {
                Map<String, String> map = new HashMap<String, String>();
                String result;
                HashMap resultMap;
                //List mapModel;
                String reqResult = null;
                String errorCode = null;
               
                map.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));
                result = HttpUtil.request(getBdsmHost() + ACTION_LIST_GROUP, map);
          

                try {
                    resultMap = (HashMap) JSONUtil.deserialize(result);
                    reqResult = (String) resultMap.get("jsonStatus");
                    errorCode = (String) resultMap.get("errorCode");
                    
                    setGrpiDList((List) resultMap.get("model"));

                } catch (JSONException ex) {
                    getLogger().fatal(ex, ex);
                    return ActionSupport.ERROR;
                }
                return ActionSupport.SUCCESS;
            } else {
                return logout();
            }
        } catch (Throwable e) {
            getLogger().fatal(e, e);
            return ActionSupport.ERROR;
        } finally {
            getLogger().info("[ End ] getGroupList()");
        }
    }

    /**
     * 
     * @return
     */
    public String getListImport() {
        getLogger().info("[Begin] get List Scheduler()");
        
        try {
            if (isValidSession()) {
                Map<String, String> map = new HashMap<String, String>();
                String result;
                HashMap resultMap;
                //List mapModel;
                String reqResult = null;
                String errorCode = null;

                    map.put("model.grpId", getGRPID());
                   
                    map.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));
                    result = HttpUtil.request(getBdsmHost() + ACTION_LIST_SCHEDULER, map);

                    try {
                        resultMap = (HashMap) JSONUtil.deserialize(result);
                        reqResult = (String) resultMap.get("jsonStatus");
                        errorCode = (String) resultMap.get("errorCode");
                        //mapModel = (List)resultMap.get("modelList");
                        schedImportList = (List) resultMap.get("modelList");

                    } catch (JSONException ex) {
                        getLogger().fatal(ex, ex);
                        return ActionSupport.ERROR;
                    }
                return ActionSupport.SUCCESS;
            } else {
                return logout();
            }
        } catch (Throwable e) {
            getLogger().fatal(e, e);
            return ActionSupport.ERROR;
        } finally {
            getLogger().info("[ End ] get List Scheduler()");
        }
    }

    /**
     * 
     * @return
     * @throws Exception
     */
    @Override
    public List doList() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * @return the grpiD
     */
    public String getGrpiD() {
        return getgRPID();
    }

    /**
     * @param gRPID 
     */
    public void setGrpiD(String gRPID) {
        this.setgRPID(gRPID);
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
     * @return the result
     */
    public String getResult() {
        return result;
    }

    /**
     * @param result the result to set
     */
    public void setResult(String result) {
        this.result = result;
    }

    /**
     * @return the grpiDList
     */
    public List getGrpiDList() {
        return grpiDList;
    }

    /**
     * @param grpiDList the grpiDList to set
     */
    public void setGrpiDList(List grpiDList) {
        this.grpiDList = grpiDList;
    }

    /**
     * @return the schedImportList
     */
    public List getSchedImportList() {
        return schedImportList;
    }

    /**
     * @param schedImportList the schedImportList to set
     */
    public void setSchedImportList(List schedImportList) {
        this.schedImportList = schedImportList;
    }

    /**
     * @return the gRPID
     */
    public String getgRPID() {
        return gRPID;
    }

    /**
     * @param gRPID the gRPID to set
     */
    public void setgRPID(String gRPID) {
        this.gRPID = gRPID;
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
}
