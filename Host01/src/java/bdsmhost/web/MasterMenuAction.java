/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.web;

import bdsm.model.MasterMenu;
import bdsmhost.dao.MasterMenuDao;
import java.util.List;

/**
 *
 * @author user
 */
public class MasterMenuAction extends BaseHostAction {
    
    private static final long serialVersionUID = 5078264277068533593L;

    private List modelList;
    private MasterMenu model;

    /**
     * 
     * @return
     */
    @Override
    public String exec() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Input:
     * HTTP Request parameters: "model.namMenu"
     * 
     * Output:
     * JSON Object: modelList
     * @return 
     */ 
    public String doList() {
        MasterMenuDao dao = new MasterMenuDao(getHSession());
        modelList = dao.list(getModel().getNamMenu());
        return SUCCESS;
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
     * @return the modelList
     */
    public List getModelList() {
        return modelList;
    }

    /**
     * @param model the model to set
     */
    public void setModel(MasterMenu model) {
        this.model = model;
    }

    /**
     * @return the model
     */
    public MasterMenu getModel() {
        return model;
    }
}
