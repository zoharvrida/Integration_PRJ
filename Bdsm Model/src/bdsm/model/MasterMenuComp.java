/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author user
 */
public class MasterMenuComp extends MasterMenu {
    private MasterMenu menuParent;
    private List<MasterMenu> childList;
    
    public MasterMenuComp(MasterMenu menuParent) {
        this.menuParent = menuParent;
        childList = new ArrayList<MasterMenu>();
    }
    
    public void add(MasterMenu m) {
        childList.add(m);
    }

    /**
     * @return the menuParent
     */
    public MasterMenu getMenuParent() {
        return menuParent;
    }

    /**
     * @param menuParent the menuParent to set
     */
    public void setMenuParent(MasterMenu menuParent) {
        this.menuParent = menuParent;
    }

    /**
     * @return the childList
     */
    public List<MasterMenu> getChildList() {
        return childList;
    }

    /**
     * @param childList the childList to set
     */
    public void setChildList(List<MasterMenu> childList) {
        this.childList = childList;
    }
}
