/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.web;

import bdsm.model.FcyTxnSummLimUD;
import bdsmhost.dao.FcyTxnSummLimUDDao;
import java.util.List;

/**
 *
 * @author v00013493
 */
public class FcyTxnSummLimUDAction extends BaseHostAction {
    private static final long serialVersionUID = 5078264277068533593L;

    private List modelList;
    private FcyTxnSummLimUD model;
    
    /**
     * Input:
     * HTTP Request parameters: "model.noAcct"
     * 
     * Output:
     * JSON Object: modelList
     * @return 
     */ 
    public String doList() {
        FcyTxnSummLimUDDao dao = new FcyTxnSummLimUDDao(getHSession());
        modelList = dao.list(model.getFlgAcct(), model.getNoAcct());
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
    public void setModel(FcyTxnSummLimUD model) {
        this.model = model;
    }

    /**
     * @return the model
     */
    public FcyTxnSummLimUD getModel() {
        return model;
    }    
}
