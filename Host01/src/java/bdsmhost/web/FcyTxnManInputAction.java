/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.web;

import bdsm.model.*;
import bdsm.util.BdsmException;
import bdsmhost.dao.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author v00013493
 */
public class FcyTxnManInputAction extends BaseHostAction {
    private static final long serialVersionUID = 5078264277068533593L;
    private static Calendar calendar = Calendar.getInstance();

    private List modelList;
    private FcyTxnManInput model;


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
    public String doSave() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     * @return
     */
    @Override
    public String doInsert() {
        AuditlogDao auditdao = new AuditlogDao(getHSession());
        ParameterDao parameterDao = new ParameterDao(getHSession());
        MfcNoBookDao mfcNoBookDao = new MfcNoBookDao(getHSession());
        MfcTxnMasterDao mfcTxnMasterDao = new MfcTxnMasterDao(getHSession());
        MfcTxnDetailsDao mfcTxnDetailsDao = new MfcTxnDetailsDao(getHSession());
        MfcUdMasterDao mfcUdMasterDao = new MfcUdMasterDao(getHSession());
        Timestamp dt = new java.sql.Timestamp(calendar.getTime().getTime());
        
        MfcNoBookPK mfcNoBookPK = new MfcNoBookPK();
        mfcNoBookPK.setNoAcct(model.getNoAcct());
        mfcNoBookPK.setRefTxn(model.getRefTxn());
        mfcNoBookPK.setTypMsg("O");
        mfcNoBookPK.setTypTrx("VALAS");
        
        MfcNoBook mfcNoBook = new MfcNoBook();
        mfcNoBook.setCompositeId(mfcNoBookPK);
        mfcNoBook.setTypAcct("M");
        mfcNoBook.setDtPost(model.getDtmTxn());
        mfcNoBook.setDtmTxn(new Timestamp(model.getDtmTxn().getTime()));
        mfcNoBook.setDtValue(model.getDtmTxn());
        mfcNoBook.setCcyTxn(model.getCcyTxn());
        mfcNoBook.setAmtTxn(model.getAmtTxn());
        mfcNoBook.setRatFcyIdr(model.getRatFcyIdr());
        mfcNoBook.setAmtTxnLcy(model.getAmtTxnLcy());
        mfcNoBook.setRatUsdIdr(model.getRatUsdIdr());
        mfcNoBook.setAmtTxnUsd(model.getAmtTxnUsd());
        mfcNoBook.setIdTxn(model.getIdTxn());
        mfcNoBook.setTxnDesc(model.getTxnDesc());
        mfcNoBook.setStatus("A");
        mfcNoBook.setTxnBranch(model.getTxnBranch());
        mfcNoBook.setIdMaintainedBy(model.getIdMaintainedBy());
        mfcNoBook.setIdMaintainedSpv(model.getIdMaintainedSpv());
        mfcNoBookDao.insert(mfcNoBook);
        auditdao.insert(model.getIdMaintainedBy(), model.getIdMaintainedSpv(), 
                "MfcNoBook", getNamMenu(), "Add", "Insert");

        MfcTxnMasterPK mfcTxnMasterPK = new MfcTxnMasterPK();
        mfcTxnMasterPK.setNoCif(model.getNoCif());
        mfcTxnMasterPK.setPeriod(model.getPeriod());
        
        MfcTxnMaster mfcTxnMaster = mfcTxnMasterDao.get(mfcTxnMasterPK);
        
        if(mfcTxnMaster == null) {
            Parameter parameter = parameterDao.get("MFCLMTAMT");
            Double amtAvailUsd = new Double(parameter.getValue());
            
            mfcTxnMaster = new MfcTxnMaster();
            mfcTxnMaster.setCompositeId(mfcTxnMasterPK);
            
            BigDecimal tmpAmtAvailUsd = new BigDecimal(String.valueOf(amtAvailUsd));
            BigDecimal tmpAmtTxnUsd = new BigDecimal(String.valueOf(model.getAmtTxnUsd()));
            tmpAmtAvailUsd = tmpAmtAvailUsd.subtract(tmpAmtTxnUsd);

            mfcTxnMaster.setAmtAvailUsd(tmpAmtAvailUsd.doubleValue());
            
            if(model.getAmtTxnUsd() > amtAvailUsd) {
                mfcTxnMaster.setAmtAvailUsd(amtAvailUsd);
                
                MfcUdMaster mfcUdMaster = mfcUdMasterDao.get(model.getNoCif(), model.getNoUd(), model.getCcyTxn());
                
                if(mfcUdMaster == null) {
                    super.setErrorCode("20402.3");
                    throw new BdsmException("20402.3");
                }                
                
                if(mfcUdMaster.getAmtAvailUsd()< model.getAmtTxnUsd()) {
                    super.setErrorCode("20402.0");
                    throw new BdsmException("20402.0");
                } else {
                    BigDecimal tmpAmtAvail = new BigDecimal(String.valueOf(mfcUdMaster.getAmtAvailUsd()));
                    BigDecimal tmpAmtTxn = new BigDecimal(String.valueOf(model.getAmtTxnUsd()));
                    tmpAmtAvail = tmpAmtAvail.subtract(tmpAmtTxn);

                    mfcUdMaster.setAmtAvailUsd(tmpAmtAvail.doubleValue());
                    mfcUdMaster.setIdMaintainedBy(model.getIdMaintainedBy());
                    mfcUdMaster.setIdMaintainedSpv(model.getIdMaintainedSpv());
                    mfcUdMaster.setDtmUpdated(dt);
                    mfcUdMaster.setDtmUpdatedSpv(dt);
                    mfcUdMasterDao.update(mfcUdMaster);
                    auditdao.insert(model.getIdMaintainedBy(), model.getIdMaintainedSpv(), 
                            "MfcUdMaster", getNamMenu(), "Add", "Update");
                }
            }
            
            mfcTxnMaster.setIdMaintainedBy(model.getIdMaintainedBy());
            mfcTxnMaster.setIdMaintainedSpv(model.getIdMaintainedSpv());
            mfcTxnMasterDao.insert(mfcTxnMaster);
            auditdao.insert(model.getIdMaintainedBy(), model.getIdMaintainedSpv(), 
                    "MfcTxnMaster", getNamMenu(), "Add", "Insert");
        } else {
            if(model.getAmtTxnUsd() > mfcTxnMaster.getAmtAvailUsd()) {
                MfcUdMaster mfcUdMaster = mfcUdMasterDao.get(model.getNoCif(), model.getNoUd(), model.getCcyTxn());
                
                if(mfcUdMaster.getAmtAvailUsd()< model.getAmtTxnUsd()) {
                    super.setErrorCode("20402.0");
                    throw new BdsmException("20402.0");
                } else {
                    BigDecimal tmpAmtAvail = new BigDecimal(String.valueOf(mfcUdMaster.getAmtAvailUsd()));
                    BigDecimal tmpAmtTxn = new BigDecimal(String.valueOf(model.getAmtTxnUsd()));
                    tmpAmtAvail = tmpAmtAvail.subtract(tmpAmtTxn);
                    
                    mfcUdMaster.setAmtAvailUsd(tmpAmtAvail.doubleValue());
                    mfcUdMaster.setIdMaintainedBy(model.getIdMaintainedBy());
                    mfcUdMaster.setIdMaintainedSpv(model.getIdMaintainedSpv());
                    mfcUdMaster.setDtmUpdated(dt);
                    mfcUdMaster.setDtmUpdatedSpv(dt);
                    mfcUdMasterDao.update(mfcUdMaster);
                    auditdao.insert(model.getIdMaintainedBy(), model.getIdMaintainedSpv(), 
                            "MfcUdMaster", getNamMenu(), "Add", "Update");
                }
            }
            
            BigDecimal tmpAmtAvailUsd = new BigDecimal(String.valueOf(mfcTxnMaster.getAmtAvailUsd()));
            BigDecimal tmpAmtTxnUsd = new BigDecimal(String.valueOf(model.getAmtTxnUsd()));
            tmpAmtAvailUsd = tmpAmtAvailUsd.subtract(tmpAmtTxnUsd);

            mfcTxnMaster.setAmtAvailUsd(tmpAmtAvailUsd.doubleValue());
            mfcTxnMaster.setIdMaintainedBy(model.getIdMaintainedBy());
            mfcTxnMaster.setIdMaintainedSpv(model.getIdMaintainedSpv());
            mfcTxnMaster.setDtmUpdated(dt);
            mfcTxnMaster.setDtmUpdatedSpv(dt);
            mfcTxnMasterDao.update(mfcTxnMaster);
            auditdao.insert(model.getIdMaintainedBy(), model.getIdMaintainedSpv(), 
                    "MfcTxnMaster", getNamMenu(), "Add", "Update");
        }
        
        MfcTxnDetailsPK mfcTxnDetailsPK = new MfcTxnDetailsPK();
        mfcTxnDetailsPK.setNoAcct(model.getNoAcct());
        mfcTxnDetailsPK.setRefTxn(model.getRefTxn());
        
        MfcTxnDetails mfcTxnDetails = new MfcTxnDetails();
        mfcTxnDetails.setCompositeId(mfcTxnDetailsPK);
        mfcTxnDetails.setNoCif(model.getNoCif());
        mfcTxnDetails.setPeriod(model.getPeriod());
        mfcTxnDetails.setNoUd(model.getNoUd());
        mfcTxnDetails.setStatus("A");
        mfcTxnDetails.setIdMaintainedBy(model.getIdMaintainedBy());
        mfcTxnDetails.setIdMaintainedSpv(model.getIdMaintainedSpv());
        mfcTxnDetailsDao.insert(mfcTxnDetails);
        auditdao.insert(model.getIdMaintainedBy(), model.getIdMaintainedSpv(), 
                "MfcTxnDetails", getNamMenu(), "Add", "Insert");
        
        super.setErrorCode("success.0");
        
        return SUCCESS;
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
        AuditlogDao auditdao = new AuditlogDao(getHSession());
        MfcTxnDetailsDao mfcTxnDetailsDao = new MfcTxnDetailsDao(getHSession());
        MfcNoBookDao mfcNoBookDao = new MfcNoBookDao(getHSession());
        MfcTxnMasterDao mfcTxnMasterDao = new MfcTxnMasterDao(getHSession());
        MfcUdMasterDao mfcUdMasterDao = new MfcUdMasterDao(getHSession());
        Timestamp dt = new java.sql.Timestamp(calendar.getTime().getTime());
        
        MfcTxnDetails mfcTxnDetails = mfcTxnDetailsDao.get(model.getNoCif(), model.getNoAcct(), model.getRefTxn());
        if(mfcTxnDetails == null) {
            super.setErrorCode("20402.1");
            throw new BdsmException("20402.1");
        } else {
            mfcTxnDetails.setStatus("D");
            mfcTxnDetails.setIdMaintainedBy(model.getIdMaintainedBy());
            mfcTxnDetails.setIdMaintainedSpv(model.getIdMaintainedSpv());
            mfcTxnDetails.setDtmUpdated(dt);
            mfcTxnDetails.setDtmUpdatedSpv(dt);
            mfcTxnDetailsDao.update(mfcTxnDetails);
            auditdao.insert(model.getIdMaintainedBy(), model.getIdMaintainedSpv(), 
                    "MfcTxnDetails", getNamMenu(), "Delete", "Update");
        }
        
        MfcNoBook mfcNoBook = mfcNoBookDao.get(model.getNoAcct(), model.getRefTxn(), "O", "M", "VALAS");
        if(mfcNoBook == null) {
            super.setErrorCode("20402.2");
            throw new BdsmException("20402.2");
        } else {
            mfcNoBookDao.delete(mfcNoBook);
            auditdao.insert(model.getIdMaintainedBy(), model.getIdMaintainedSpv(), 
                    "MfcNoBook", getNamMenu(), "Delete", "Delete");
        }
        
        MfcTxnMasterPK mfcTxnMasterPK = new MfcTxnMasterPK();
        mfcTxnMasterPK.setNoCif(model.getNoCif());
        mfcTxnMasterPK.setPeriod(model.getPeriod());

        MfcTxnMaster mfcTxnMaster = mfcTxnMasterDao.get(mfcTxnMasterPK);

        if(mfcTxnDetails.getNoUd() != null && !"".equals(mfcTxnDetails.getNoUd())) {
            MfcUdMaster mfcUdMaster = mfcUdMasterDao.get(model.getNoCif(), mfcTxnDetails.getNoUd(), model.getCcyTxn());
            if(mfcUdMaster == null) {
                super.setErrorCode("20402.3");
                throw new BdsmException("20402.3");
            } else {
                mfcUdMaster.setAmtAvail(mfcUdMaster.getAmtAvail() + model.getAmtTxn());
                mfcUdMaster.setIdMaintainedBy(model.getIdMaintainedBy());
                mfcUdMaster.setIdMaintainedSpv(model.getIdMaintainedSpv());
                mfcUdMaster.setDtmUpdated(dt);
                mfcUdMaster.setDtmUpdatedSpv(dt);
                mfcUdMasterDao.update(mfcUdMaster);
                auditdao.insert(model.getIdMaintainedBy(), model.getIdMaintainedSpv(), 
                        "MfcUdMaster", getNamMenu(), "Delete", "Update");
            }
        }
        
        if(mfcTxnMaster == null) {
            super.setErrorCode("20402.4");
            throw new BdsmException("20402.4");
        } else {
            mfcTxnMaster.setAmtAvailUsd(mfcTxnMaster.getAmtAvailUsd() + model.getAmtTxnUsd());
            mfcTxnMaster.setIdMaintainedBy(model.getIdMaintainedBy());
            mfcTxnMaster.setIdMaintainedSpv(model.getIdMaintainedSpv());
            mfcTxnMaster.setDtmUpdated(dt);
            mfcTxnMaster.setDtmUpdatedSpv(dt);
            mfcTxnMasterDao.update(mfcTxnMaster);
            auditdao.insert(model.getIdMaintainedBy(), model.getIdMaintainedSpv(), 
                    "MfcTxnMaster", getNamMenu(), "Delete", "Update");
        }
        
        super.setErrorCode("success.2");
        
        return SUCCESS;
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
    public void setModel(FcyTxnManInput model) {
        this.model = model;
    }

    /**
     * @return the model
     */
    public FcyTxnManInput getModel() {
        return model;
    }
}