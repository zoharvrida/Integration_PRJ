/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.web;

import bdsm.rpt.dao.ReportDeleteDao;
import bdsm.rpt.model.RepDelComp;
import java.util.List;

/**
 *
 * @author v00019722
 */
public class FixMasterReportDeleteAction extends BaseHostAction {
    private static final long serialVersionUID = 5078264277068533593L;

    private List modelList;
    private RepDelComp model;
    
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
        ReportDeleteDao dao = new ReportDeleteDao(getHSession());

        modelList = dao.list(getModel().getReportName(),getModel().getIdTemplate()); 
        getLogger().info("ModelList = " + getModelList());
        getLogger().info("list Suceess :" + String.valueOf(getModelList().size()));
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
     * @param model the model to set
     */
    public void setModel(RepDelComp model) {
        this.model = model;
    }
    /**
     * @return the model
     */
    public RepDelComp getModel() {
        return model;
    }
}
