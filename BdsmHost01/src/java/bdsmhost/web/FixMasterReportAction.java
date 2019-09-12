/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.web;

import bdsm.rpt.dao.FixMasterReportDao;
import bdsm.rpt.model.FixMasterReport;
import bdsm.rpt.model.XrefReportTemplate;
import java.util.List;

/**
 *
 * @author v00019722
 */
public class FixMasterReportAction extends BaseHostAction {
    private static final long serialVersionUID = 5078264277068533593L;

    private List modelList;
    private List modelRep;
    private FixMasterReport model;
    private XrefReportTemplate modelT;
    
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
    public String doList() {
        FixMasterReportDao dao = new FixMasterReportDao(getHSession());
        getLogger().info("test Model :" + getModel().getReportName().toString());
        
        modelList = dao.list(getModel().getReportName(),getModelT().getCompositeId().getIdTemplate());
        getLogger().info("list Suceess :"+ String.valueOf(modelList.size()));
        return SUCCESS;
    }
    
    /**
     * 
     * @return
     */
    @Override
    public String doGet() {
        FixMasterReportDao dao = new FixMasterReportDao(getHSession());
        this.model = dao.get(model.getCompositeId().getIdReport().toString());
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
    public void setModel(FixMasterReport model) {
        this.model = model;
    }

    /**
     * @return the model
     */
    public FixMasterReport getModel() {
        return model;
    }

    /**
     * @return the modelRep
     */
    public List getModelRep() {
        return modelRep;
    }

    /**
     * @return the modelT
     */
    public XrefReportTemplate getModelT() {
        return modelT;
    }

    /**
     * @param modelT the modelT to set
     */
    public void setModelT(XrefReportTemplate modelT) {
        this.modelT = modelT;
    }
}
