/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.dialog;

import bdsm.util.BdsmUtil;
import bdsm.util.HttpUtil;
import com.opensymphony.xwork2.ActionSupport;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import org.apache.log4j.Logger;
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;
import org.apache.struts2.util.ServletContextAware;

/**
 *
 * @author 00030663
 */
public class AuthAction extends ActionSupport implements ServletContextAware {
    private static final String ACTION_LOGIN = "login.action";

    private static Logger logger = Logger.getLogger(AuthAction.class);
    private ServletContext servletContext;
    private String idMenu = "";
    private int authorized = 0;
    private String idUser = "";
    private String password= "";
    private String errorMessage = "";
    
    /**
     * 
     * @return
     */
    @Override
    public String execute() {
        logger.info("[Begin] execute()");
        try {
            errorMessage="";
            authorized = isValidCredential(idUser, password, idMenu);
//            authorized = isValidCredential(idUser, password, idMenu);
//            if (!authorized) {
//                errorMessage = "Authorization failed for user " + idUser + ".";
//                addActionError(errorMessage);
//                return ActionSupport.INPUT;
//            }
            
            if(authorized == 0) {
                errorMessage = "Authorization failed for user " + idUser + ".";
                addActionError(errorMessage);
                return ActionSupport.INPUT;
            } else if(authorized == 2) {
                errorMessage = "Authorization is failed for user " + idUser + ".";
                addActionError(errorMessage);
                return ActionSupport.INPUT;
            }
            return ActionSupport.SUCCESS;
        } catch (Throwable ex) {
            logger.fatal(ex, ex);
            errorMessage = ex.getMessage();
            addActionError(errorMessage);
            return ActionSupport.INPUT;
        } finally {
            logger.info("[ End ] execute()");
        }
    }

    /**
     * 
     * @param idMenu
     */
    public void setIdMenu(String idMenu) {
        this.idMenu = idMenu;
    }

    /**
     * @return the isAuth
     */
    public int isAuthorized() {
        return authorized;
    }

    /**
     * @return the idUser
     */
    public String getIdUser() {
        return idUser;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param idUser the idUser to set
     */
    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the idMenu
     */
    public String getIdMenu() {
        return idMenu;
    }

    /**
     * @return the errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    private int isValidCredential(String idUser, String password, String idMenu) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("action", "auth");
        map.put("idUser", idUser);
        map.put("password", password);
        map.put("idMenu", idMenu);
        map.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));
        String result = HttpUtil.request(getBdsmHost() + ACTION_LOGIN, map);
        if (result == null) return 0;

        int ret;
        try {
            HashMap resultMap = (HashMap)JSONUtil.deserialize(result);
            //Boolean httpStatus = (Boolean) resultMap.get("jsonResult");
            //if (httpStatus != null && httpStatus==true) {
                ret = ((Long)resultMap.get("validLogin")).intValue();
//                if (ret==null) ret=false;
            //}
        } catch (JSONException ex) {
            logger.fatal(ex);
            return 0;
        }

        return ret;
    }


    /**
     * 
     * @param sc
     */
    public void setServletContext(ServletContext sc) {
        this.servletContext = sc;
    }
    
    /**
     * 
     * @return
     */
    protected String getBdsmHost() {
        return servletContext.getInitParameter("bdsmHost");
    }

    /**
     * 
     * @return
     */
    protected String getTokenKey() {
        return servletContext.getInitParameter("tokenKey");
    }

    /**
     * 
     * @return
     */
    protected String getTzToken() {
        return servletContext.getInitParameter("tzToken");
    }
}
