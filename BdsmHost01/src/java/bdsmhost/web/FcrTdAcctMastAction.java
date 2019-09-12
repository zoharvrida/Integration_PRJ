/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.web;

import bdsm.model.FcrTdAcctMast;
import bdsmhost.dao.FcrTdAcctMastDao;
import java.util.List;

/**
 *
 * @author v00013493
 */
public class FcrTdAcctMastAction extends BaseHostAction {
    private static final long serialVersionUID = 5078264277068533593L;
    private List<FcrTdAcctMast> modelList;
    private FcrTdAcctMast model;

    /**
     * 
     * @return
     */
    public String doGet() {
        FcrTdAcctMastDao dao = new FcrTdAcctMastDao(getHSession());
        model = dao.get(model.getCompositeId());
        return SUCCESS;
    }
    
    /**
     * 
     * @return
     */
    @Override
    public String exec() {
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
    public List<FcrTdAcctMast> getModelList() {
        return modelList;
    }

    /**
     * @param model the model to set
     */
    public void setModel(FcrTdAcctMast model) {
        this.model = model;
    }

    /**
     * @return the model
     */
    public FcrTdAcctMast getModel() {
        return model;
    }
}
