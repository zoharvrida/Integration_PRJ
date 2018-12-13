/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.web;

import bdsm.model.XrefTemplateMenu;
import bdsmhost.dao.AuditlogDao;
import bdsmhost.dao.XrefTemplateMenuDao;
import java.util.List;

/**
 *
 * @author 00030663
 */
public class XrefTemplateMenuAction extends BaseHostAction {
    private static final long serialVersionUID = 5078264277068533593L;
 
    private List modelList;
    private XrefTemplateMenu model;
    
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
     * HTTP Request parameters: "model.idTemplate"
     * 
     * Output:
     * JSON Object: modelList of XrefTemplateMenu
     * @return 
     */ 
    @Override
    public String doList() {
        XrefTemplateMenuDao dao = new XrefTemplateMenuDao(getHSession());
        List<XrefTemplateMenu> lList = dao.list(getModel().getCompositeId().getIdTemplate());
        
        return SUCCESS;
    }

    /**
     * Input:
     * HTTP Request parameters: "model.idTemplate, model.idMenu"
     * 
     * Output:
     * JSON Object: model
     * @return 
     */ 
    @Override
    public String doGet() {
        XrefTemplateMenuDao dao = new XrefTemplateMenuDao(getHSession());
        model = dao.get(model.getCompositeId());
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
        XrefTemplateMenuDao dao = new XrefTemplateMenuDao(getHSession());
        AuditlogDao auditdao = new AuditlogDao(getHSession());
        XrefTemplateMenu persistence = dao.get(getModel().getCompositeId());

        if(persistence == null) {
            dao.insert(getModel());
            auditdao.insert(getModel().getIdMaintainedBy(), getModel().getIdMaintainedSpv(), 
                            "XrefTemplateMenu", getNamMenu(), "Edit", "Insert");
            super.setErrorCode("success.0");
            return SUCCESS;
        } else {
            persistence.setAccessRight(getModel().getAccessRight());
            persistence.setIdMaintainedBy(getModel().getIdMaintainedBy());
            persistence.setIdMaintainedSpv(getModel().getIdMaintainedSpv());
            
            dao.update(persistence);
            auditdao.insert(getModel().getIdMaintainedBy(), getModel().getIdMaintainedSpv(), 
                            "XrefTemplateMenu", getNamMenu(), "Edit", "Edit");
            super.setErrorCode("success.1");
        return SUCCESS;
        }
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
    public void setModel(XrefTemplateMenu model) {
        this.model = model;
    }

    /**
     * @return the model
     */
    public XrefTemplateMenu getModel() {
        return model;
    }
}
