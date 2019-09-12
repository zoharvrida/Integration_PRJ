/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.web;

import bdsm.model.FcrBaBankMast;
import bdsm.model.FcrCiCustmast;
import bdsm.model.MfcSlMaster;
import bdsmhost.dao.AuditlogDao;
import bdsmhost.dao.FcrBaBankMastDao;
import bdsmhost.dao.FcrCiCustmastDao;
import bdsmhost.dao.MfcSlMasterDao;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author user
 */
public class MfcSlMasterAction extends BaseHostAction {
    private static final long serialVersionUID = 5078264277068533593L;

    private List modelList;
    private MfcSlMaster model;

    /**
     * Input:
     * HTTP Request parameters: "model.compositeId.codCustId, model.compositeId.period"
     * 
     * Output:
     * JSON Object: model
     * @return 
     */ 
    public String doGet() {
        MfcSlMasterDao dao = new MfcSlMasterDao(getHSession());
        model = dao.get(model.getCompositeId().getNoCif(), model.getCompositeId().getPeriod());
        return SUCCESS;
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
    public String doSave() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     * @return
     */
    @Override
    public String doInsert() {
        FcrCiCustmastDao cicustmastDao = new FcrCiCustmastDao(getHSession());
        MfcSlMasterDao slmasterDao = new MfcSlMasterDao(getHSession());
        AuditlogDao auditDao = new AuditlogDao(getHSession());
        FcrCiCustmast persistenceCiCustmast = cicustmastDao.get(getModel().getCompositeId().getNoCif());
        MfcSlMaster persistenceSlMaster = slmasterDao.get(getModel().getCompositeId());

        if(persistenceCiCustmast == null) {
            super.setErrorCode("20301.0");
            return ERROR;
        }
        
        if(persistenceSlMaster == null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
            FcrBaBankMastDao fcrBaBankMastDao = new FcrBaBankMastDao(getHSession());
            FcrBaBankMast baBankMast = fcrBaBankMastDao.get();
            Date datProcess = baBankMast.getDatProcess();
            
            Integer periodProcess = Integer.parseInt(sdf.format(datProcess));
            
            if(periodProcess < getModel().getCompositeId().getPeriod()) {
                super.setErrorCode("20301.1");
                return ERROR;
            }
            
            slmasterDao.insert(getModel());
            auditDao.insert(getModel().getIdMaintainedBy(), getModel().getIdMaintainedSpv(), 
                            "MfcSlMaster", getNamMenu(), "Add", "Insert");
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
    public void setModel(MfcSlMaster model) {
        this.model = model;
    }

    /**
     * @return the model
     */
    public MfcSlMaster getModel() {
        return model;
    }
}
