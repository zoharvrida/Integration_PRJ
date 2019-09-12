/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.web;

import bdsm.model.FcrCiCustmast;
import bdsmhost.dao.FcrCiCustmastDao;
import java.util.List;

/**
 *
 * @author user
 */
public class FcrCiCustmastAction extends BaseHostAction {
    private static final long serialVersionUID = 5078264277068533593L;

    private List modelList;
    private FcrCiCustmast model;

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
     * Input:
     * HTTP Request parameters: "model.codCustId"
     * 
     * Output:
     * JSON Object: model
     * @return 
     */ 
    public String doGet() {
        FcrCiCustmastDao dao = new FcrCiCustmastDao(getHSession());
        model = dao.get(model.getCompositeId().getCodCustId());
        return SUCCESS;
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
    public void setModel(FcrCiCustmast model) {
        this.model = model;
    }

    /**
     * @return the model
     */
    public FcrCiCustmast getModel() {
        return model;
    }
}
