/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.web;

import bdsm.model.FcrBaBankMast;
import bdsm.model.FcrCiCustmast;
import bdsm.model.MfcUdMaster;
import bdsmhost.dao.AuditlogDao;
import bdsmhost.dao.FcrBaBankMastDao;
import bdsmhost.dao.FcrCiCustmastDao;
import bdsmhost.dao.MfcUdMasterDao;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author user
 */
public class MfcUdMasterAction extends BaseHostAction {
    private static final long serialVersionUID = 5078264277068533593L;

    private List modelList;
    private MfcUdMaster model;
    
    /**
     * Input:
     * HTTP Request parameters: "model.compositeId"
     * 
     * Output:
     * JSON Object: modelList
     * @return 
     */ 
    public String doList() {
        MfcUdMasterDao dao = new MfcUdMasterDao(getHSession());
        modelList = dao.list(getModel().getCompositeId());
        return SUCCESS;
    }
    
    /**
     * 
     * @return
     */
    public String listByUDNo() {
    	MfcUdMasterDao dao = new MfcUdMasterDao(getHSession());
        this.modelList = dao.listByNoUD(this.model.getCompositeId().getNoUd());
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
        getLogger().debug("MODEL :" + this.model);
        FcrCiCustmastDao cicustmastDao = new FcrCiCustmastDao(getHSession());
        FcrBaBankMastDao fcrBaBankMastDao = new FcrBaBankMastDao(getHSession());
        MfcUdMasterDao udmasterDao = new MfcUdMasterDao(getHSession());
        AuditlogDao auditDao = new AuditlogDao(getHSession());
        FcrCiCustmast persistenceCiCustmast = cicustmastDao.get(getModel().getCompositeId().getNoCif());
        FcrBaBankMast fcrBaBankMast = fcrBaBankMastDao.get();
        MfcUdMaster persistenceUdMaster = udmasterDao.get(getModel().getCompositeId());
        List<MfcUdMaster> listOfSameUDNo = udmasterDao.listByNoUD(this.getModel().getCompositeId().getNoUd());

        if (persistenceCiCustmast == null) {
            super.setErrorCode("20302.0");
            return ERROR;
        }
        
        if (getModel().getDtExpiry().compareTo(fcrBaBankMast.getDatProcess()) < 0) {
            super.setErrorCode("20302.3");
            return ERROR;
        }
        
        if (persistenceUdMaster == null) {
        	for (MfcUdMaster m : listOfSameUDNo)
        		if (this.getModel().equals(m)) {
        			super.setErrorCode("error.0");
        			return ERROR;
        		}
        	
            udmasterDao.insert(getModel());
            auditDao.insert(getModel().getIdMaintainedBy(), getModel().getIdMaintainedSpv(), 
                            "MfcUdMaster", getNamMenu(), "Add", "Insert");
            super.setErrorCode("success.0");
            return SUCCESS;
        }
        else {
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
        getLogger().debug("MODEL :" + this.model);
        MfcUdMasterDao dao = new MfcUdMasterDao(getHSession());
        FcrBaBankMastDao fcrBaBankMastDao = new FcrBaBankMastDao(getHSession());
        AuditlogDao auditdao = new AuditlogDao(getHSession());
        MfcUdMaster persistence = dao.get(getModel().getCompositeId());
        
        if(persistence != null) {
            FcrBaBankMast fcrBaBankMast = fcrBaBankMastDao.get();
            if(getModel().getDtExpiry().compareTo(fcrBaBankMast.getDatProcess()) < 0) {
                super.setErrorCode("20302.3");
                return ERROR;
            }
            
            if(getModel().getStatus().equals("Inactive")) {
                super.setErrorCode("20302.1");
                return ERROR;
            }
            persistence.setTypeUD(getModel().getTypeUD());
            persistence.setPayeeName(getModel().getPayeeName());
            persistence.setPayeeCountry(getModel().getPayeeCountry());
            persistence.setTradingProduct(getModel().getTradingProduct());
            persistence.setDtIssued(getModel().getDtIssued());
            persistence.setDtExpiry(getModel().getDtExpiry());
            persistence.setCcyUd(getModel().getCcyUd());
            
            BigDecimal tmpAmtLimit = new BigDecimal(String.valueOf(persistence.getAmtLimit()));
            BigDecimal tmpAmtLimitUSD = new BigDecimal(String.valueOf(persistence.getAmtLimitUsd()));
            BigDecimal tmpAmtAvail = new BigDecimal(String.valueOf(persistence.getAmtAvail()));
            BigDecimal tmpAmtAvailUSD = new BigDecimal(String.valueOf(persistence.getAmtAvailUsd()));
            BigDecimal amtUsed = tmpAmtLimit.subtract(tmpAmtAvail);
            BigDecimal amtUsedUSD = tmpAmtLimitUSD.subtract(tmpAmtAvailUSD);
            BigDecimal tmpNewLimit = new BigDecimal(String.valueOf(getModel().getAmtLimit()));
            BigDecimal tmpNewLimitUSD = new BigDecimal(String.valueOf(getModel().getAmtLimitUsd()));
            if(tmpNewLimit.compareTo(amtUsed) == -1) {
                super.setErrorCode("20302.4");
                return ERROR;
            }
            if(tmpNewLimitUSD.compareTo(amtUsedUSD) == -1) {
                super.setErrorCode("20302.4");
                return ERROR;
            }            
            persistence.setAmtLimit(getModel().getAmtLimit());
            persistence.setAmtLimitUsd(getModel().getAmtLimitUsd());
            persistence.setAmtAvail(tmpNewLimit.subtract(amtUsed).doubleValue());
            persistence.setAmtAvailUsd(tmpNewLimitUSD.subtract(amtUsedUSD).doubleValue());
            persistence.setRatFcyLim(getModel().getRatFcyLim());
            persistence.setRatUsdLim(getModel().getRatUsdLim());
            persistence.setRemarks(getModel().getRemarks());
            persistence.setIdMaintainedBy(getModel().getIdMaintainedBy());
            persistence.setIdMaintainedSpv(getModel().getIdMaintainedSpv());

            dao.update(persistence);
            auditdao.insert(getModel().getIdMaintainedBy(), getModel().getIdMaintainedSpv(), 
                            "MfcUdMaster", getNamMenu(), "Edit", "Edit");
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
        MfcUdMasterDao dao = new MfcUdMasterDao(getHSession());
        AuditlogDao auditdao = new AuditlogDao(getHSession());
        MfcUdMaster persistence = dao.get(getModel().getCompositeId());
        
        if(persistence != null) {
            if(getModel().getStatus().equals("Active")) {
                super.setErrorCode("20302.2");
                return ERROR;
            }
            
            dao.delete(persistence);
            auditdao.insert(getModel().getIdMaintainedBy(), getModel().getIdMaintainedSpv(), 
                            "MfcUdMaster", getNamMenu(), "Delete", "Delete");
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
    public void setModel(MfcUdMaster model) {
        this.model = model;
    }

    /**
     * @return the model
     */
    public MfcUdMaster getModel() {
        return model;
    }
}