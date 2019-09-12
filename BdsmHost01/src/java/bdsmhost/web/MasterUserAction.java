package bdsmhost.web;

import bdsm.model.MasterUser;
import bdsmhost.dao.AuditlogDao;
import bdsmhost.dao.MasterUserDao;
import java.util.List;

/**
     * Input:
     * HTTP Request parameters: "model.namUser"
 *
     * Output:
     * JSON Object: list of model
 */
public class MasterUserAction extends BaseHostAction {

    private static final long serialVersionUID = 5078264277068533593L;

    private List<MasterUser> modelList;
    private MasterUser model;

    /**
     * 
     * @return
     */
    public String doList() {
        MasterUserDao dao = new MasterUserDao(getHSession());
        modelList = dao.list(getModel().getNamUser());
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
        MasterUserDao dao = new MasterUserDao(getHSession());
        model = dao.get(model.getIdUser());
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
        MasterUserDao dao = new MasterUserDao(getHSession());
        AuditlogDao auditdao = new AuditlogDao(getHSession());
        MasterUser persistence = dao.get(getModel().getIdUser());

        if(persistence == null) {
            dao.insert(getModel());
            auditdao.insert(getModel().getIdMaintainedBy(), getModel().getIdMaintainedSpv(),
                            "MasterUser", getNamMenu(), "Add", "Insert");
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
        MasterUserDao dao = new MasterUserDao(getHSession());
        AuditlogDao auditdao = new AuditlogDao(getHSession());
        MasterUser persistence = dao.get(getModel().getIdUser());

        if(persistence != null) {
            persistence.setNamUser(getModel().getNamUser());
            persistence.setCdBranch(getModel().getCdBranch());
            persistence.setIdTemplate(getModel().getIdTemplate());
            persistence.setIdMaintainedBy(getModel().getIdMaintainedBy());
            persistence.setIdMaintainedSpv(getModel().getIdMaintainedSpv());

            dao.update(persistence);
            auditdao.insert(getModel().getIdMaintainedBy(), getModel().getIdMaintainedSpv(),
                            "MasterUser", getNamMenu(), "Edit", "Edit");
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
        MasterUserDao dao = new MasterUserDao(getHSession());
        AuditlogDao auditdao = new AuditlogDao(getHSession());
        MasterUser persistence = dao.get(getModel().getIdUser());

        if(persistence != null) {
            dao.delete(persistence);
            auditdao.insert(getModel().getIdMaintainedBy(), getModel().getIdMaintainedSpv(),
                            "MasterUser", getNamMenu(), "Delete", "Delete");
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
    public List<MasterUser> getModelList() {
        return modelList;
    }

    /**
     * @param model the model to set
     */
    public void setModel(MasterUser model) {
        this.model = model;
    }

    /**
     * @return the model
     */
    public MasterUser getModel() {
        return model;
    }
}
