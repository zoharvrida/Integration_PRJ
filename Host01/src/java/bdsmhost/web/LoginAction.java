/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.web;

import bdsm.model.MasterUser;
import bdsm.model.XrefTemplateMenu;
import bdsm.model.XrefTemplateMenuPK;
import bdsm.util.BdsmUtil;
import bdsm.util.HibernateUtil;
import bdsmhost.dao.MasterUserDao;
import bdsmhost.dao.XrefTemplateMenuDao;
import com.opensymphony.xwork2.ActionSupport;
import javax.servlet.ServletContext;
import org.apache.struts2.json.annotations.JSON;
import org.hibernate.Session;

/**
 *
 * @author bdsm This action required 3 parameters: 1. action. Available options:
 * login 2. userId. 3. password.
 *
 * This action will return 1 boolean object: validLogin.
 */
public class LoginAction extends BaseHostAction {

    private String action = null;
    private String idUser = null;
    private String password = null;
    private String idMenu = null;
    private int validLogin = 0;
    private ServletContext servletContext;

    /**
     * 
     * @return
     */
    @Override
    public String exec() {
        //boolean isAD = false;
        getLogger().debug("action   : " + action);
        getLogger().debug("idUser: " + idUser);
        //getLogger().debug("password: " + password);
        getLogger().debug("idMenu: " + idMenu);
        if ("".equals(idUser) || "".equals(password)) {
            validLogin = 0;
        } else {
            validLogin = checkLoginCredential(idUser, password, idMenu);
        }
        getLogger().debug("validLogin: " + validLogin);
        return ActionSupport.SUCCESS;
    }

    private int checkLoginCredential(String userId, String password, String idMenu) {
        int validLogin = 0;
        Session hsession = null;
        MasterUserDao dao = null;
        boolean isAD = false;
        MasterUser model = new MasterUser();
        model.setIdUser(userId);
        try {
            hsession = (Session) HibernateUtil.getSession();
            dao = new MasterUserDao(hsession);
            isAD = dao.isAD(model);
            if ("login".equals(action)) {
                if (isAD) {
                    return BdsmUtil.isADValid(userId, password, getadServer(), getadRootDN());
                } else {
                    password = BdsmUtil.enc(userId, password);
                    model.setPassword(password);
                    if (dao.isValidUser(model)) {
                        validLogin = 1;
                    }
                    return validLogin;
                }
            } else if ("auth".equals(action)) {
                MasterUser modelOri = dao.get(userId);
                XrefTemplateMenuPK pk = null;
                XrefTemplateMenuDao xrefTemplateDao = null;
                XrefTemplateMenu xtp = null;

                if (isAD) {
                    if ((validLogin = BdsmUtil.isADValid(userId, password, getadServer(), getadRootDN())) == 1) {

                        pk = new XrefTemplateMenuPK();
                        pk.setIdMenu(idMenu);
                        pk.setIdTemplate(modelOri.getIdTemplate());
                        xrefTemplateDao = new XrefTemplateMenuDao(hsession);
                        xtp = xrefTemplateDao.get(pk);
                        String accessRight = xtp.getAccessRight();

                        if (!accessRight.substring(4, 5).equals("1")) {
                            validLogin = 2;
                        }

                    }

                    return validLogin;
                } else {

                    password = BdsmUtil.enc(userId, password);
                    model.setPassword(password);
                    if (dao.isValidUser(model)) {
                        validLogin = 1;
                        pk = new XrefTemplateMenuPK();
                        pk.setIdMenu(idMenu);
                        pk.setIdTemplate(modelOri.getIdTemplate());
                        xrefTemplateDao = new XrefTemplateMenuDao(hsession);
                        xtp = xrefTemplateDao.get(pk);
                        String accessRight = xtp.getAccessRight();

                        if (!accessRight.substring(4, 5).equals("1")) {
                            validLogin = 2;
                        }

                    }

                    return validLogin;
                }
            }
        } finally {
            HibernateUtil.closeSession(hsession);
        }
        return validLogin;
    }

    /**
     * 
     * @return
     */
    @JSON(serialize = true)
    public int getValidLogin() {
        return validLogin;
    }

    /**
     * @return 
     */
    @JSON(serialize = false)
    public String getAction() {
        return this.action;
    }

    /**
     * @return  
     */
    @JSON(serialize = false)
    public String getIdUser() {
        return this.idUser;
    }

    /**
     * @return  
     */
    @JSON(serialize = false)
    public String getPassword() {
        return this.password;
    }

    /**
     * @param action the action to set
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * @param idUser 
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
     * 
     * @return
     */
    @Override
    public String doSave() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     * @return
     */
    @Override
    public String doInsert() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     * @return
     */
    @Override
    public String doUpdate() {
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

    /**
     * 
     * @return
     */
    @Override
    public String doList() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     * @return
     */
    @Override
    public String doGet() {
        throw new UnsupportedOperationException("Not supported yet.");
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


}
