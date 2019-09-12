package bdsm.web.menu20201;

import bdsm.web.BaseTabContentAction;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * @author bdsm
 */
public class Menu20201Tab2Action extends BaseTabContentAction {
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
        return ActionSupport.INPUT;
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
}
