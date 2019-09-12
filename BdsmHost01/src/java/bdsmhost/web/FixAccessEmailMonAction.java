/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.web;

import bdsm.scheduler.dao.FixEmailAccessDao;
import bdsm.scheduler.model.FixEmailAccess;
import java.util.ArrayList;

/**
 *
 * @author v00019722
 */
public class FixAccessEmailMonAction extends BaseHostAction {

    private FixEmailAccess model;
    private ArrayList modelList;

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
        getLogger().info("Begin List Object Monitoring");
        getLogger().debug("Cek grup id = " + getModel().getGrpId());
        getLogger().debug("Cek id Code Access = " + getModel().getCdAccess());
        getLogger().debug("Cek id Scheduler = " + getModel().getFixEmailAccessPK().getIdScheduler());
        FixEmailAccessDao dao = new FixEmailAccessDao(getHSession());
        setModelList((ArrayList) dao.lister(getModel().getGrpId(), getModel().getCdAccess(), getModel().getFixEmailAccessPK().getIdScheduler()));
        getLogger().info("Monitoring = " + getModelList());
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

    /**
     * @return the modelList
     */
    public ArrayList getModelList() {
        return modelList;
    }

    /**
     * @param modelList the modelList to set
     */
    public void setModelList(ArrayList modelList) {
        this.modelList = modelList;
    }
}
