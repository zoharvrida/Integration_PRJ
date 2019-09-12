/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.web;

import bdsm.scheduler.dao.FixSchedulerImportDao;
import bdsm.scheduler.model.FixEmailAccess;
import java.util.List;

/**
 *
 * @author v00019722
 */
public class FixSchedulerImportGroupAction extends BaseHostAction{

    private List modelList;
    private FixEmailAccess model;
    
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
        FixSchedulerImportDao dao = new FixSchedulerImportDao(getHSession());

        setModelList(dao.list(getModel().getGrpId()));

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
     * @param modelList the modelList to set
     */
    public void setModelList(List modelList) {
        this.modelList = modelList;
    }

    /**
     * @return the model
     */
    public FixEmailAccess getModel() {
        return model;
    }

    /**
     * @param model the model to set
     */
    public void setModel(FixEmailAccess model) {
        this.model = model;
    }
    
}
