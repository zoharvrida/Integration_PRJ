/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.web;

import bdsm.scheduler.dao.FixSchedulerXtractDao;
import bdsm.scheduler.model.FixSchedulerXtract;
import bdsmhost.dao.AuditlogDao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author v00019722
 */
public class FixSchedulerXtractAction extends BaseHostAction {

    private static final long serialVersionUID = 5078264277068533593L;

    private List modelList;
    private FixSchedulerXtract model;
    private String idTemplate;
    private String namScheduler;
    private List modelListS;
    private ArrayList value = new ArrayList();
    private ArrayList oldValue = new ArrayList();
    private ArrayList fieldName = new ArrayList();

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
        FixSchedulerXtractDao dao = new FixSchedulerXtractDao(getHSession());

        modelList = dao.list(getModel().getFixSchedulerPK().getNamScheduler());

        return SUCCESS;
    }

    /**
     * 
     * @return
     */
    @Override
    public String doGet() {
        FixSchedulerXtractDao dao = new FixSchedulerXtractDao(getHSession());
        modelList = dao.listbyPattern(getModel().getFilePattern());

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
        getLogger().info("idTemplate     = " + getModel().getFixSchedulerPK().getIdTemplate());
        getLogger().info("Scheduler      = " + getModel().getFixSchedulerPK().getIdScheduler());
        getLogger().info("nama Scheduler = " + getModel().getFixSchedulerPK().getNamScheduler());
        FixSchedulerXtractDao dao = new FixSchedulerXtractDao(getHSession());
        AuditlogDao auditdao = new AuditlogDao(getHSession());
        getLogger().info("Audit");
        FixSchedulerXtract persistence = dao.get(getModel().getFixSchedulerPK());

        if (persistence != null) {
            persistence.setFlgStatus(getModel().getFlgStatus());
            
            oldValue.add(persistence.getFlgStatus());
            oldValue.add(persistence.getIdMaintainedBy());
            oldValue.add(persistence.getIdMaintainedSpv());
            value.add(getModel().getFlgStatus());
            value.add(getModel().getIdMaintainedBy());
            value.add(getModel().getIdMaintainedBy());
            fieldName.add("FLGSTATUS");
            fieldName.add("IDUSER");
            fieldName.add("IDUSERSPV");
            
            getLogger().info("Flg Status = " + persistence.getFlgStatus());
            persistence.setIdMaintainedBy(getModel().getIdMaintainedBy());
            persistence.setIdMaintainedSpv(getModel().getIdMaintainedSpv());

            dao.update(persistence);
            getLogger().info(" Test hingga persistance Extract");
            //auditdao.insert(getModel().getIdMaintainedBy(), getModel().getIdMaintainedSpv(),
            //        "FixSchedulerExtract", getNamMenu(), "Edit", "Edit");
            try {
                auditdao.runPackage(getNamMenu(),
                        "FixSchedulerExtract",
                        getModel().getIdMaintainedBy(),
                        getModel().getIdMaintainedSpv(),
                        "Edit", "Edit", value, oldValue, fieldName);
            } catch (SQLException ex) {
                Logger.getLogger(FixSchedulerXtractAction.class.getName()).log(Level.SEVERE, null, ex);
            }
            getLogger().info("Error Code.....");
            super.setErrorCode("success.6");
            return SUCCESS;
        } else {
            super.setErrorCode("error.4");
            return ERROR;
        }

        //super.setErrorCode("success.1");
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
     * @return the model
     */
    public FixSchedulerXtract getModel() {
        return model;
    }

    /**
     * @param model the model to set
     */
    public void setModel(FixSchedulerXtract model) {
        this.model = model;
    }

    /**
     * @return the idTemplate
     */
    public String getIdTemplate() {
        return idTemplate;
    }

    /**
     * @param idTemplate the idTemplate to set
     */
    public void setIdTemplate(String idTemplate) {
        this.idTemplate = idTemplate;
    }

    /**
     * @return the modelListS
     */
    public List getModelListS() {
        return modelListS;
    }

    /**
     * @param modelListS the modelListS to set
     */
    public void setModelListS(List modelListS) {
        this.modelListS = modelListS;
    }
}
