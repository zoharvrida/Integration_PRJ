package bdsm.web.menu20201;

import bdsm.web.BaseTabContentAction;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * @author bdsm
 */
public class Menu20201Tab0Action extends BaseTabContentAction {
    private String flgAcct;
        private String idMenu;


        /**
         * 
         * @return
         */
        public String exec() {
        return ActionSupport.SUCCESS;
    }

    /**
     * 
     * @return
     */
    public String none() {
        return "none";
    }
    
    /**
     * 
     * @return
     */
    @Override
    public String doInput() {
        if(flgAcct.equals("0")) {
            return "inputCIF";
        } else if(flgAcct.equals("1")) {
            return "input";
        } else {
            return "none";
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
     * @return the flgAcct
     */
    public String getFlgAcct() {
        return flgAcct;
    }

    /**
     * @param flgAcct the flgAcct to set
     */
    public void setFlgAcct(String flgAcct) {
        this.flgAcct = flgAcct;
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
