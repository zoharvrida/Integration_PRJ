/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.web;

import bdsm.model.MfcNoBook;
import bdsmhost.dao.MfcNoBookDao;
import java.util.List;

/**
 *
 * @author v00013493
 */
public class FcyTxnManInputListAction extends BaseHostAction {
    
    private static final long serialVersionUID = 5078264277068533593L;

    private List modelList;
    private MfcNoBook model;

    /**
     * Input:
     * HTTP Request parameters: "model.compositeId.noAcct, model.dtPost, model.typMsg, model.status"
     * 
     * Output:
     * JSON Object: modelList
     * @return 
     */ 
    public String doList() {
        MfcNoBookDao dao = new MfcNoBookDao(getHSession());
        modelList = dao.list(model.getCompositeId().getNoAcct(), model.getCompositeId().getTypMsg(), 
                model.getTypAcct(), model.getCompositeId().getRefTxn());
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
    public void setModel(MfcNoBook model) {
        this.model = model;
    }

    /**
     * @return the model
     */
    public MfcNoBook getModel() {
        return model;
    }
}
