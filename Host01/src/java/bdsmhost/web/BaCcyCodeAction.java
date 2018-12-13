/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.web;

import bdsm.model.FcrBaCcyCode;
import bdsmhost.dao.FcrBaCcyCodeDao;
import java.util.List;

/**
 *
 * @author user
 */
public class BaCcyCodeAction extends BaseHostAction {
    private static final long serialVersionUID = 5078264277068533593L;

    private List modelList;
    private FcrBaCcyCode model;

    /**
     * Output:
     * JSON Object: modelList
     * @return 
     */ 
    public String doList() {
        FcrBaCcyCodeDao dao = new FcrBaCcyCodeDao(getHSession());
        modelList = dao.list();
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
    public void setModel(FcrBaCcyCode model) {
        this.model = model;
    }

    /**
     * @return the model
     */
    public FcrBaCcyCode getModel() {
        return model;
    }
}