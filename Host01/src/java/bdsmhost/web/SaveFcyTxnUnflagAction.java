/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.web;

import bdsm.model.MfcNoBook;
import bdsm.model.MfcTxnDetails;
import bdsm.model.MfcTxnMaster;
import bdsm.model.MfcTxnMasterPK;
import bdsm.model.MfcUdMaster;
import bdsm.model.SaveFcyTxnUnflag;
import bdsmhost.dao.AuditlogDao;
import bdsmhost.dao.MfcNoBookDao;
import bdsmhost.dao.MfcTxnDetailsDao;
import bdsmhost.dao.MfcTxnMasterDao;
import bdsmhost.dao.MfcUdMasterDao;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author v00013493
 */
public class SaveFcyTxnUnflagAction extends BaseHostAction {
    private static final long serialVersionUID = 5078264277068533593L;
    private static Calendar calendar = Calendar.getInstance();

    private List modelList;
    private SaveFcyTxnUnflag model;

    /**
     * 
     * @return
     */
    @Override
    public String doSave() {
        AuditlogDao auditdao = new AuditlogDao(getHSession());
        MfcTxnMasterDao mfcTxnMasterDao = new MfcTxnMasterDao(getHSession());
        MfcNoBookDao mfcNoBookDao = new MfcNoBookDao(getHSession());
        MfcTxnDetailsDao mfcTxnDetailsDao = new MfcTxnDetailsDao(getHSession());
        MfcUdMasterDao mfcUdMasterDao = new MfcUdMasterDao(getHSession());
        Timestamp dt = new java.sql.Timestamp(calendar.getTime().getTime());
        
        MfcTxnMasterPK mfcTxnMasterPK = new MfcTxnMasterPK();
        mfcTxnMasterPK.setNoCif(model.getNoCif());
        mfcTxnMasterPK.setPeriod(model.getPeriod());
        
        MfcTxnMaster mfcTxnMaster = mfcTxnMasterDao.get(mfcTxnMasterPK);
        
        if(mfcTxnMaster == null) {
            super.setErrorCode("20401.0");
            return ERROR;
        }
        
        MfcNoBook mfcNoBook1 = mfcNoBookDao.get(model.getNoAcct(), model.getRefTxn(), "R", "VALAS");
        MfcNoBook mfcNoBook2 = mfcNoBookDao.get(model.getNoAcct(), model.getRefTxn(), "O" ,"VALAS");
        MfcTxnDetails mfcTxnDetails = mfcTxnDetailsDao.get(model.getNoCif(), model.getNoAcct(), model.getRefTxn());
        
        if(mfcNoBook1 == null) {
            super.setErrorCode("20401.1");
            return ERROR;
        }
        
        if(mfcNoBook2 == null) {
            super.setErrorCode("20401.1");
            return ERROR;
        }
        
        if(mfcTxnDetails == null) {
            super.setErrorCode("20401.3");
            return ERROR;
        }
        
        if((mfcTxnMaster.getAmtAvailUsd() - model.getAmtTxnUsd() < 0) && "".equals(model.getNoUd())) {
            super.setErrorCode("20401.4");
            return ERROR;
        }
        
        mfcNoBook1.setStatus("R");
        mfcNoBook1.setIdMaintainedBy(model.getIdMaintainedBy());
        mfcNoBook1.setIdMaintainedSpv(model.getIdMaintainedSpv());
        mfcNoBook1.setDtmUpdated(dt);
        mfcNoBook1.setDtmUpdatedSpv(dt);
        mfcNoBookDao.update(mfcNoBook1);
        auditdao.insert(model.getIdMaintainedBy(), model.getIdMaintainedSpv(), 
                "MfcNoBook", getNamMenu(), "Unflag", "Update");
        
        mfcNoBook2.setStatus("R");
        mfcNoBook2.setIdMaintainedBy(model.getIdMaintainedBy());
        mfcNoBook2.setIdMaintainedSpv(model.getIdMaintainedSpv());
        mfcNoBook2.setDtmUpdated(dt);
        mfcNoBook2.setDtmUpdatedSpv(dt);
        mfcNoBookDao.update(mfcNoBook2);
        auditdao.insert(model.getIdMaintainedBy(), model.getIdMaintainedSpv(), 
                "MfcNoBook", getNamMenu(), "Unflag", "Update");
        
        mfcTxnDetails.setStatus("R");
        mfcTxnDetails.setIdMaintainedBy(model.getIdMaintainedBy());
        mfcTxnDetails.setIdMaintainedSpv(model.getIdMaintainedSpv());
        mfcTxnDetailsDao.update(mfcTxnDetails);
        auditdao.insert(model.getIdMaintainedBy(), model.getIdMaintainedSpv(), 
                "MfcTxnDetails", getNamMenu(), "Unflag", "Update");
        
        mfcTxnMaster.setAmtAvailUsd(mfcTxnMaster.getAmtAvailUsd() + model.getAmtTxnUsd());
        mfcTxnMaster.setIdMaintainedBy(model.getIdMaintainedBy());
        mfcTxnMaster.setIdMaintainedSpv(model.getIdMaintainedSpv());
        mfcTxnMaster.setDtmUpdated(dt);
        mfcTxnMaster.setDtmUpdatedSpv(dt);
        mfcTxnMasterDao.update(mfcTxnMaster);
        auditdao.insert(model.getIdMaintainedBy(), model.getIdMaintainedSpv(), 
                "MfcTxnMaster", getNamMenu(), "Unflag", "Update");
        
        MfcUdMaster mfcUdMaster = mfcUdMasterDao.get(model.getNoCif(), mfcTxnDetails.getNoUd(), model.getCcyUd());
        if(mfcUdMaster != null) {
            mfcUdMaster.setAmtAvailUsd(mfcUdMaster.getAmtAvailUsd()+ model.getAmtTxnUsd());
            mfcUdMaster.setIdMaintainedBy(model.getIdMaintainedBy());
            mfcUdMaster.setIdMaintainedSpv(model.getIdMaintainedSpv());
            mfcUdMaster.setDtmUpdated(dt);
            mfcUdMaster.setDtmUpdatedSpv(dt);
            mfcUdMasterDao.update(mfcUdMaster);
            auditdao.insert(model.getIdMaintainedBy(), model.getIdMaintainedSpv(), 
                    "MfcUdMaster", getNamMenu(), "Unflag", "Update");
        }
        
        super.setErrorCode("success.4");
        
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
    public String doGet() {
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
    public void setModel(SaveFcyTxnUnflag model) {
        this.model = model;
    }

    /**
     * @return the model
     */
    public SaveFcyTxnUnflag getModel() {
        return model;
    }
}