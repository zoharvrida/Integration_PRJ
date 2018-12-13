package bdsmhost.web;

import bdsm.model.MasterTemplate;
import bdsmhost.dao.AuditlogDao;
import bdsmhost.dao.MasterTemplateDao;
import java.util.List;

/**
 * 
 * @author 00030663
 */
public class MasterTemplateAction extends BaseHostAction {

    private static final long serialVersionUID = 5078264277068533593L;

    private List modelList;
    private MasterTemplate model;

    /**
     * Input:
     * HTTP Request parameters: "model.namTemplate"
     * 
     * Output:
     * JSON Object: modelList
     * @return 
     */ 
    public String doList() {
        MasterTemplateDao dao = new MasterTemplateDao(getHSession());
        modelList = dao.list(getModel().getNamTemplate());
        return SUCCESS;
    }

    /**
     * Input:
     * HTTP Request parameters: "model.idUser"
     * 
     * Output:
     * JSON Object: model
     * @return 
     */ 
    public String doGet() {
        MasterTemplateDao dao = new MasterTemplateDao(getHSession());
        setModel(dao.get(getModel().getIdTemplate()));
        return SUCCESS;
    }
    
    /**
     * To save or update user.
     * @return String
     */
    public String doSave() {
        throw new UnsupportedOperationException("Not supported yet.");
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
     * Input:
     * HTTP Request parameters: "model.idTemplate", "model.namTemplate"
     * , "model.idMaintainedBy", "model.idMaintainedSpv"
     * 
     * Output:
     * JSON Object: modelList
     * @return 
     */ 
    @Override
    public String doInsert() {
        MasterTemplateDao dao = new MasterTemplateDao(getHSession());
        AuditlogDao auditdao = new AuditlogDao(getHSession());
        MasterTemplate persistence = dao.get(getModel().getIdTemplate());
        
        if(persistence == null) {
        dao.insert(getModel());
            auditdao.insert(getModel().getIdMaintainedBy(), getModel().getIdMaintainedSpv(), 
                            "MasterTemplate", getNamMenu(), "Add", "Insert");
            super.setErrorCode("success.0");
        return SUCCESS;
        } else {
            super.setErrorCode("error.0");
            return ERROR;
        }
    }

    /**
     * 
     * @return
     */
    @Override
    public String doUpdate() {
        MasterTemplateDao dao = new MasterTemplateDao(getHSession());
        AuditlogDao auditdao = new AuditlogDao(getHSession());
        MasterTemplate persistence = dao.get(getModel().getIdTemplate());
        
        if(persistence != null) {
        persistence.setNamTemplate(getModel().getNamTemplate());
        persistence.setIdMaintainedBy(getModel().getIdMaintainedBy());
        persistence.setIdMaintainedSpv(getModel().getIdMaintainedSpv());
        
        dao.update(persistence);
            auditdao.insert(getModel().getIdMaintainedBy(), getModel().getIdMaintainedSpv(), 
                            "MasterTemplate", getNamMenu(), "Edit", "Edit");
            super.setErrorCode("success.1");
        return SUCCESS;
        } else {
            super.setErrorCode("error.1");
            return ERROR;
        }
    }

    /**
     * 
     * @return
     */
    @Override
    public String doDelete() {
        MasterTemplateDao dao = new MasterTemplateDao(getHSession());
        AuditlogDao auditdao = new AuditlogDao(getHSession());
        MasterTemplate persistence = dao.get(getModel().getIdTemplate());
        
        if(persistence != null) {
            dao.delete(persistence);
            auditdao.insert(getModel().getIdMaintainedBy(), getModel().getIdMaintainedSpv(), 
                            "MasterTemplate", getNamMenu(), "Delete", "Delete");
            super.setErrorCode("success.2");
        return SUCCESS;
        } else {
            super.setErrorCode("error.2");
            return ERROR;
        }
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
    public void setModel(MasterTemplate model) {
        this.model = model;
    }

    /**
     * @return the model
     */
    public MasterTemplate getModel() {
        return model;
    }
}
