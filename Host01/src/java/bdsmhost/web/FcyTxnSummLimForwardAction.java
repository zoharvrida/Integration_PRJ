/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.web;

import bdsm.model.FcyTxnSummLimMonthly;
import bdsmhost.dao.FcyTxnSummLimMonthlyDao;
import java.util.List;

/**
 *
 * @author v00013493
 */
public class FcyTxnSummLimForwardAction extends BaseHostAction {
    private static final long serialVersionUID = 5078264277068533593L;

    private List modelList;
    private FcyTxnSummLimMonthly model;
    
    /**
     * Input:
     * HTTP Request parameters: "model.noAcct"
     * 
     * Output:
     * JSON Object: modelList
     * @return 
     */ 
    public String doList() {
        FcyTxnSummLimMonthlyDao dao = new FcyTxnSummLimMonthlyDao(getHSession());
        modelList = dao.list(model.getFlgAcct(), model.getNoAcct(), model.getPeriod());
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
    public void setModel(FcyTxnSummLimMonthly model) {
        this.model = model;
    }

    /**
     * @return the model
     */
    public FcyTxnSummLimMonthly getModel() {
        return model;
    }
}
