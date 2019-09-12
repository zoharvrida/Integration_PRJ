package bdsm.web.menu10302;

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
 * @author bdsm
 */
public class Menu10302Action extends BaseContentAction {
    private static final String ACTION_GET = "xrefTemplateMenu_get.action";
    private static final String ACTION_EDIT = "xrefTemplateMenu_update.action";
    private static final String NAM_MENU = "Menu Access Registration";
    
    private String namTemplate;
    private String idTemplate;
    private String namMenu;
    private String idMenu;
    private String accessCode;
    private String accessRightChosen;
    private String idMaintainedBy;
    private String idMaintainedSpv;
    
    /**
     * 
     * @return
     */
    public String exec() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     * @return
     */
    @SkipValidation
    public String getAccessRight() {
        getLogger().info("[Begin] getAccessRight()");
        try {
            if (isValidSession()) {
                Map<String, String> map = new HashMap<String, String>();
                String result;
                HashMap resultMap;
                HashMap mapModel;
                String reqResult = null;
                String errorCode = null;
                accessRightChosen = "00000";

                getLogger().debug("idTemplate : " + getIdTemplate());
                getLogger().debug("idMenu : " + getIdMenu());

                map.put("model.compositeId.idTemplate", getIdTemplate());
                map.put("model.compositeId.idMenu", getIdMenu());
                map.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));

                result = HttpUtil.request(getBdsmHost() + ACTION_GET, map);

                try {
                    resultMap = (HashMap)JSONUtil.deserialize(result);
                    reqResult = (String)resultMap.get("jsonStatus");
                    errorCode = (String)resultMap.get("errorCode");
                    mapModel = (HashMap)resultMap.get("model");
                    if(mapModel != null) {
                        accessRightChosen = (String)mapModel.get("accessRight");                
                    }
                } catch (JSONException ex) {
                    getLogger().fatal(ex, ex);
                    return ActionSupport.ERROR;
                }

                return "accessCode";
            } else {
                return logout();
            }
        } catch (Throwable e) {
            getLogger().fatal(e, e);
            return ActionSupport.ERROR;
        } finally {
            getLogger().info("[ End ] getAccessRight()");
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
        String result;
        HashMap resultMap;
        String reqResult = null;
        String errorCode = null;
        
        getLogger().debug("idTemplate : " + getIdTemplate());
        getLogger().debug("idMenu : " + getIdMenu());
        getLogger().debug("accessRight : " + getAccessRight());
        getLogger().debug("idMaintainedBy : " + getIdMaintainedBy());
        getLogger().debug("idMaintainedSpv : " + getIdMaintainedSpv());
        
        map.put("model.compositeId.idTemplate", getIdTemplate());
        map.put("model.compositeId.idMenu", getIdMenu());
        map.put("model.accessRight", getAccessCode(getAccessCode()));
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
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * 
     * @param accessRight
     * @return
     */
    public String getAccessCode(String accessRight) {
        char accesscode[] = {'0', '0', '0', '0', '0'};
        if (accessRight != null) {
            if(accessRight.contains("Inquiry")) {
                accesscode[0] = '1';
            }

            if(accessRight.contains("Add")) {
                accesscode[1] = '1';
            }

            if(accessRight.contains("Edit")) {
                accesscode[2] = '1';
            }

            if(accessRight.contains("Delete")) {
                accesscode[3] = '1';
            }

            if(accessRight.contains("Authorize")) {
                accesscode[4] = '1';
            }
        }
        
        return new String(accesscode);
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
     * @return the namMenu
     */
    public String getNamMenu() {
        return namMenu;
    }

    /**
     * @param namMenu the namMenu to set
     */
    public void setNamMenu(String namMenu) {
        this.namMenu = namMenu;
    }
    
    /**
     * @return the idMenu
     */
    public String getIdMenu() {
        return idMenu;
    }

    /**
     * @param idMenu the idMenu to set
     */
    public void setIdMenu(String idMenu) {
        this.idMenu = idMenu;
    }
    
    /**
     * @return the accessCode
     */
    public String getAccessCode() {
        return this.accessCode;
    }

    /**
     * @param accessCode the accessCode to set
     */
    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }

    /**
     * @return the accessRightChosen
     */
    public String getAccessRightChosen() {
        return this.accessRightChosen;
    }

    /**
     * @param accessRightChosen the accessRightChosen to set
     */
    public void setAccessRightChosen(String accessRightChosen) {
        this.accessRightChosen = accessRightChosen;
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
