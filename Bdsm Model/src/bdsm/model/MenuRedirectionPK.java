/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;

import java.io.Serializable;

/**
 *
 * @author SDM
 */
public class MenuRedirectionPK implements Serializable {
    private String sourceMenu;
    private String aliasName;

    public MenuRedirectionPK() {
    }

    /**
     * @return the sourceMenu
     */
    public String getSourceMenu() {
        return sourceMenu;
    }

    /**
     * @param sourceMenu the sourceMenu to set
     */
    public void setSourceMenu(String sourceMenu) {
        this.sourceMenu = sourceMenu;
    }

    /**
     * @return the aiasName
     */
    public String getAliasName() {
        return aliasName;
    }

    /**
     * @param aiasName the aiasName to set
     */
    public void setAliasName(String aiasName) {
        this.aliasName = aiasName;
    }

    public MenuRedirectionPK(String sourceMenu, String aliasName) {
        this.sourceMenu = sourceMenu;
        this.aliasName = aliasName;
    }
    
    
}
