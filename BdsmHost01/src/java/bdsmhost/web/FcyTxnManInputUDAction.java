/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.web;

import bdsm.model.FcyTxnManInputUD;
import bdsmhost.dao.FcyTxnManInputUDDao;
import java.util.List;

/**
 *
 * @author v00013493
 */
public class FcyTxnManInputUDAction extends BaseHostAction {
    private static final long serialVersionUID = 5078264277068533593L;

    private List modelList;
    private FcyTxnManInputUD model;

    /**
     * Input:
     * HTTP Request parameters: "model.noCif, model.noUd, model.ccyUd, model.amtTxn"
     * 
     * Output:
     * JSON Object: modelList
     * @return 
     */ 
    public String doList() {
        FcyTxnManInputUDDao dao = new FcyTxnManInputUDDao(getHSession());
        modelList = dao.list(model.getNoCif(), model.getNoUd(), model.getCcyUd(), model.getAmtTxn());
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
    public void setModel(FcyTxnManInputUD model) {
        this.model = model;
    }

    /**
     * @return the model
     */
    public FcyTxnManInputUD getModel() {
        return model;
    }
}