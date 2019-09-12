/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.web.menu10303;

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
 * @author user
 */
public class Menu10303Action extends BaseContentAction {
    private static final String ACTION_INSERT = "user_insert.action";
    private static final String ACTION_EDIT = "user_update.action";
    private static final String ACTION_DELETE = "user_delete.action";
    private static final String NAM_MENU = "User Registration";
    private static final String ACTION_GET_USERMAST = "adUser_get.action";

    private String namUser;
    private String idUser;
    private Integer cdBranch;
    private String namTemplate;
    private String idTemplate;
    private String idMaintainedBy;
    private String idMaintainedSpv;

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
    public String inquiry() {
        getLogger().info("[Begin] inquiry()");
        try {
            if (isValidSession()) {
                Map<String, String> map = new HashMap<String, String>();
                String result;
                HashMap resultMap;
                HashMap mapModel;
                String reqResult = null;
                String errorCode = null;

                getLogger().debug("ID USER : " + getIdUser());
                map.put("model.sAMAccountName", getIdUser());
                map.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));

                result = HttpUtil.request(getBdsmHost() + ACTION_GET_USERMAST, map);

                try {
                    resultMap = (HashMap)JSONUtil.deserialize(result);
                    reqResult = (String)resultMap.get("jsonStatus");
                    errorCode = (String)resultMap.get("errorCode");
                    mapModel = (HashMap)resultMap.get("model");
                    if(mapModel != null) {
                        namUser = (String)mapModel.get("displayName");
                    }
                } catch (JSONException ex) {
                    getLogger().fatal(ex, ex);
                    return ActionSupport.ERROR;
                }
        return "inquiry";
            } else {
                return logout();
            }
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
    @Override
    public String doAdd() {
        Map<String, String> map = new HashMap<String, String>();
        String result;
        HashMap resultMap;
        String reqResult = null;
        String errorCode = null;

        getLogger().debug("namUser : " + getNamUser());
        getLogger().debug("idUser : " + getIdUser());
        getLogger().debug("cdBranch : " + getCdBranch());
        getLogger().debug("idTemplate : " + getIdTemplate());
        getLogger().debug("idMaintainedBy : " + getIdMaintainedBy());
        getLogger().debug("idMaintainedSpv : " + getIdMaintainedSpv());

        map.put("model.namUser", getNamUser());
        map.put("model.idUser", getIdUser());
        map.put("model.cdBranch", String.valueOf(getCdBranch()));
        map.put("model.idTemplate", getIdTemplate());
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
        Map<String, String> map = new HashMap<String, String>();
        String result;
        HashMap resultMap;
        String reqResult = null;
        String errorCode = null;

        getLogger().debug("namUser : " + getNamUser());
        getLogger().debug("idUser : " + getIdUser());
        getLogger().debug("cdBranch : " + getCdBranch());
        getLogger().debug("idTemplate : " + getIdTemplate());
        getLogger().debug("idMaintainedBy : " + getIdMaintainedBy());
        getLogger().debug("idMaintainedSpv : " + getIdMaintainedSpv());

        map.put("model.namUser", getNamUser());
        map.put("model.idUser", getIdUser());
        map.put("model.cdBranch", String.valueOf(getCdBranch()));
        map.put("model.idTemplate", getIdTemplate());
        map.put("model.idMaintainedBy", getIdMaintainedBy());
        map.put("model.idMaintainedSpv", getIdMaintainedSpv());
        map.put("namMenu", NAM_MENU);
        map.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));

        result = HttpUtil.request(getBdsmHost() + ACTION_EDIT, map);

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
        Map<String, String> map = new HashMap<String, String>();
        String result;
        HashMap resultMap;
        String reqResult = null;
        String errorCode = null;

        getLogger().debug("namUser : " + getNamUser());
        getLogger().debug("idUser : " + getIdUser());
        getLogger().debug("cdBranch : " + getCdBranch());
        getLogger().debug("idTemplate : " + getIdTemplate());
        getLogger().debug("idMaintainedBy : " + getIdMaintainedBy());
        getLogger().debug("idMaintainedSpv : " + getIdMaintainedSpv());

        map.put("model.namUser", getNamUser());
        map.put("model.idUser", getIdUser());
        map.put("model.cdBranch", String.valueOf(getCdBranch()));
        map.put("model.idTemplate", getIdTemplate());
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
     * @return the namUser
     */
    public String getNamUser() {
        return namUser;
    }

    /**
     * @param namUser the namUser to set
     */
    public void setNamUser(String namUser) {
        this.namUser = namUser;
    }

    /**
     * @return the idUser
     */
    public String getIdUser() {
        return idUser;
    }

    /**
     * @param idUser the idUser to set
     */
    public void setIdUser(String idUser) {
        this.idUser = idUser;
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
     * @return the namTemplate
     */
    public String getNamTemplate() {
        return namTemplate;
    }

    /**
     * @param namTemplate the namTemplate to set
     */
    public void setNamTemplate(String namTemplate) {
        this.namTemplate = namTemplate;
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
}
