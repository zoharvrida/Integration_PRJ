/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.web;

import bdsm.model.MenuRedirect;
import bdsm.model.MenuRedirectionPK;
import bdsmhost.dao.MenuRedDao;

/**
 *
 * @author 00110310
 */
public class MenuRedirectionAction extends BaseHostAction {
    private MenuRedirect model;
    private String idMenu;
    private String alias;
    
    /**
     * 
     * @return
     */
    @Override
    public String exec() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * 
     * @return
     */
    @Override
    public String doList() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * 
     * @return
     */
    @Override
    public String doGet() {
        getLogger().debug("MENU : " + getIdMenu() + " ALIAS :" + getAlias());
        MenuRedDao dao = new MenuRedDao(getHSession());
        MenuRedirectionPK pk = new MenuRedirectionPK();
        pk.setAliasName(getAlias());
        pk.setSourceMenu(getIdMenu());
        setModel(dao.get(pk));
        dao.evictObjectFromPersistenceContext(getModel());
        return SUCCESS;
    }

    /**
     * 
     * @return
     */
    @Override
    public String doSave() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * 
     * @return
     */
    @Override
    public String doInsert() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * 
     * @return
     */
    @Override
    public String doUpdate() {
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
     * @return the model
     */
    public MenuRedirect getModel() {
        return model;
    }

    /**
     * @param model the model to set
     */
    public void setModel(MenuRedirect model) {
        this.model = model;
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
     * @return the alias
     */
    public String getAlias() {
        return alias;
    }

    /**
     * @param alias the alias to set
     */
    public void setAlias(String alias) {
        this.alias = alias;
    }
    
}
