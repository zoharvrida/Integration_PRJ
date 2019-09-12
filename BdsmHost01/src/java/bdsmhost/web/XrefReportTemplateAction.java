/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.web;

import bdsm.rpt.dao.XrefReportTemplateDao;
import bdsm.rpt.model.XrefReportTemplate;
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
public class XrefReportTemplateAction extends BaseHostAction {

    private static final long serialVersionUID = 5078264277068533593L;
    private List modelList;
    private XrefReportTemplate model;
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
    @Override
    public String doList() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     * @return
     */
    @Override
    public String doGet() {
        XrefReportTemplateDao dao = new XrefReportTemplateDao(getHSession());
        model = dao.get(model.getCompositeId());
        getLogger().info("test Value Xref = " + model);
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
        XrefReportTemplateDao dao = new XrefReportTemplateDao(getHSession());
        AuditlogDao auditdao = new AuditlogDao(getHSession());
        getLogger().info("cek isinya = " + getModel().getCompositeId());
        XrefReportTemplate persistence = dao.get(getModel().getCompositeId());

        if (persistence == null) {
            
            for(int i = 0; i < 7; i++){
                oldValue.add("");
            }
            value.add(getModel().getIdXref());
            value.add(getModel().getCompositeId().getIdTemplate());
            value.add(getModel().getCompositeId().getIdMasterReport());
            value.add(getModel().getIdCreatedBy());
            value.add(getModel().getIdCreatedSpv());
            value.add(getModel().getDtmCreated());
            value.add(getModel().getDtmCreatedSpv());
            fieldName.add("IDXREF");
            fieldName.add("IDTEMPLATE");
            fieldName.add("IDMASTERREPORT");
            fieldName.add("IDCREATEDBY");
            fieldName.add("IDCREATEDSPV");
            fieldName.add("DTMCREATED");
            fieldName.add("DTMCREATEDSPV");
            
            dao.insert(getModel());
            
            try {
                    auditdao.runPackage(getNamMenu(),
                        "xrefreporttemplate",
                        getModel().getIdMaintainedBy(),
                        getModel().getIdMaintainedSpv(),
                        "Add", "Insert", value, oldValue, fieldName);
                } catch (SQLException ex) {
                    Logger.getLogger(XrefReportTemplateAction.class.getName()).log(Level.SEVERE, null, ex);
                }
            auditdao.insert(getModel().getIdMaintainedBy(), getModel().getIdMaintainedSpv(),
                    "XrefReportTemplate", getNamMenu(), "Edit", "Insert");
            super.setErrorCode("success.0");
            return SUCCESS;
        } else {
            persistence.setCompositeId(getModel().getCompositeId());
            persistence.setIdMaintainedBy(getModel().getIdMaintainedBy());
            persistence.setIdMaintainedSpv(getModel().getIdMaintainedSpv());

            oldValue.add(getModel().getCompositeId().getIdTemplate());
            oldValue.add(getModel().getCompositeId().getIdMasterReport());
            value.add(persistence.getCompositeId().getIdTemplate());
            value.add(persistence.getCompositeId().getIdMasterReport());
            fieldName.add("IDTEMPLATE");
            fieldName.add("IDMASTERREPORT");
            
            dao.update(persistence);
            
            try {
                    auditdao.runPackage(getNamMenu(),
                        "xrefreporttemplate",
                        getModel().getIdMaintainedBy(),
                        getModel().getIdMaintainedSpv(),
                        "Add", "Update", value, oldValue, fieldName);
                } catch (SQLException ex) {
                    Logger.getLogger(XrefReportTemplateAction.class.getName()).log(Level.SEVERE, null, ex);
                }
            super.setErrorCode("success.1");
            return SUCCESS;
        }
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
        XrefReportTemplateDao dao = new XrefReportTemplateDao(getHSession());
        AuditlogDao auditdao = new AuditlogDao(getHSession());
        getLogger().debug("test_Dbdelete = " + dao.get(getModel().getCompositeId()));
        XrefReportTemplate persistence = dao.get(getModel().getCompositeId());


        if (persistence != null) {
            dao.delete(persistence);
            
            oldValue.add(getModel().getIdXref());
            oldValue.add(getModel().getCompositeId().getIdTemplate());
            oldValue.add(getModel().getCompositeId().getIdMasterReport());
            oldValue.add(getModel().getIdCreatedBy());
            oldValue.add(getModel().getIdCreatedSpv());
            oldValue.add(getModel().getDtmCreated());
            oldValue.add(getModel().getDtmCreatedSpv());
            for(int i = 0; i < 7; i++){
                value.add("");
            }
            fieldName.add("IDXREF");
            fieldName.add("IDTEMPLATE");
            fieldName.add("IDMASTERREPORT");
            fieldName.add("IDCREATEDBY");
            fieldName.add("IDCREATEDSPV");
            fieldName.add("DTMCREATED");
            fieldName.add("DTMCREATEDSPV");
            try {
                    auditdao.runPackage(getNamMenu(),
                        "xrefreporttemplate",
                        getModel().getIdMaintainedBy(),
                        getModel().getIdMaintainedSpv(),
                        "Delete", "Delete", value, oldValue, fieldName);
                } catch (SQLException ex) {
                    Logger.getLogger(XrefReportTemplateAction.class.getName()).log(Level.SEVERE, null, ex);
                }
            
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
    public void setModel(XrefReportTemplate model) {
        this.model = model;
    }

    /**
     * @return the model
     */
    public XrefReportTemplate getModel() {
        return model;
    }
}
