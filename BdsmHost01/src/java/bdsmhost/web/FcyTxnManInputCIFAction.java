/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.web;

import bdsm.model.FcyTxnManInputCIF;
import bdsmhost.dao.FcyTxnManInputCIFDao;
import java.util.List;

/**
 *
 * @author v00013493
 */
public class FcyTxnManInputCIFAction extends BaseHostAction {
    private static final long serialVersionUID = 5078264277068533593L;

    private List modelList;
    private FcyTxnManInputCIF model;

    /**
     * 
     * @return
     */
    public String doGet() {
        FcyTxnManInputCIFDao dao = new FcyTxnManInputCIFDao(getHSession());
        model = dao.get(model.getNoCif(), model.getPeriod());
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
    public List getModelList() {
        return modelList;
    }

    /**
     * @param model the model to set
     */
    public void setModel(FcyTxnManInputCIF model) {
        this.model = model;
    }

    /**
     * @return the model
     */
    public FcyTxnManInputCIF getModel() {
        return model;
    }
}
