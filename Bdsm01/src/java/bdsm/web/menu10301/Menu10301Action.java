/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.web.menu10301;

import bdsm.util.BdsmUtil;
import bdsm.util.HttpUtil;
import bdsm.web.BaseContentAction;
import com.opensymphony.xwork2.ActionSupport;
import java.util.HashMap;
import java.util.Map;
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;

/**
 *
 * @author user
 */
public class Menu10301Action extends BaseContentAction {
    private static final String ACTION_INSERT = "template_insert.action";
    private static final String ACTION_EDIT = "template_update.action";
    private static final String ACTION_DELETE = "template_delete.action";
    private static final String NAM_MENU = "Template Registration";
    
    private String namTemplate;
    private String idTemplate;
    private String idMaintainedBy;
    private String idMaintainedSpv;
    
    /**
     * 
     * @return
     */
    public String doAdd() {
        Map<String, String> map = new HashMap<String, String>();
        String result;
        HashMap resultMap;
        String reqResult = null;
        String errorCode = null;
        
        getLogger().debug("namTemplate : " + getNamTemplate());
        getLogger().debug("idTemplate : " + getIdTemplate());
        getLogger().debug("idMaintainedBy : " + getIdMaintainedBy());
        getLogger().debug("idMaintainedSpv : " + getIdMaintainedSpv());
        
        map.put("model.idTemplate", getIdTemplate());
        map.put("model.namTemplate", getNamTemplate());
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
            
//        setIdTemplate("");
//        setNamTemplate("");
            
        return ActionSupport.SUCCESS;
            }
            
    /**
     * 
     * @return
     */
    public String doEdit() {
        Map<String, String> map = new HashMap<String, String>();
        String result;
        HashMap resultMap;
        String reqResult = null;
        String errorCode = null;
        
        getLogger().debug("namTemplate : " + getNamTemplate());
        getLogger().debug("idTemplate : " + getIdTemplate());
        getLogger().debug("idMaintainedBy : " + getIdMaintainedBy());
        getLogger().debug("idMaintainedSpv : " + getIdMaintainedSpv());
        
        map.put("model.idTemplate", getIdTemplate());
        map.put("model.namTemplate", getNamTemplate());
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
        
//        setIdTemplate("");
//        setNamTemplate("");
        
        return ActionSupport.SUCCESS;
    }
    
    /**
     * 
     * @return
     */
    public String doDelete() {
        Map<String, String> map = new HashMap<String, String>();
        String result;
        HashMap resultMap;
        String reqResult = null;
        String errorCode = null;

        getLogger().debug("namTemplate : " + getNamTemplate());
        getLogger().debug("idTemplate : " + getIdTemplate());

        map.put("model.idTemplate", getIdTemplate());
        map.put("model.namTemplate", getNamTemplate());
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
        
//        setIdTemplate("");
//        setNamTemplate("");
        
        return ActionSupport.SUCCESS;
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
