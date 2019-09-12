/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;
/**
 *
 * @author user
 */
public class MasterMenu extends BaseModel {
    private String idMenu;
    private String idMenuParent;
    private String namMenu;
    private String url;
    private String availAccess;

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
     * @return the idMenuParent
     */
    public String getIdMenuParent() {
        return idMenuParent;
    }

    /**
     * @param idMenuParent the idMenuParent to set
     */
    public void setIdMenuParent(String idMenuParent) {
        this.idMenuParent = idMenuParent;
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
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the availAccess
     */
    public String getAvailAccess() {
        return availAccess;
    }

    /**
     * @param availAccess the availAccess to set
     */
    public void setAvailAccess(String availAccess) {
        this.availAccess = availAccess;
    }
}
