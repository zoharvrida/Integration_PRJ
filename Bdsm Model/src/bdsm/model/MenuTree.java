/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 00030663
 */
public class MenuTree {
    private MenuTree parent;
    private List<MenuTree> childList;
    private String idMenu;
    private String namMenu;
    private String url;
    private String availAccess;
    
    public MenuTree(MenuTree parent) {
        this.parent = parent;
        if (parent != null) parent.childList.add(this);
        childList = new ArrayList<MenuTree>();
    }
    
    public boolean compact() {
        boolean flag = url != null;
        if (!flag) {
            for (MenuTree m: childList) {
                flag = m.compact();
                if (flag) break;
            }
            //if (!flag) childList.clear();
        }
        return flag;
    }
    
//    public MenuTree add(MenuTree m) {
//        childList.add(m);
//        return this;
//    }

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

    /**
     * @return the childList
     */
    public List<MenuTree> getChildList() {
        return childList;
    }

    /**
     * @param childList the childList to set
     */
    public void setChildList(List<MenuTree> childList) {
        this.childList = childList;
    }
}
