/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.web;

import bdsm.model.*;
import bdsm.util.BdsmException;
import bdsmhost.dao.*;
import static com.opensymphony.xwork2.Action.SUCCESS;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author v00013493
 */
public class SaveFcyTxnFlagAction extends BaseHostAction {

    private static final long serialVersionUID = 5078264277068533593L;
    private static Calendar calendar = Calendar.getInstance();
    private List modelList;
    private SaveFcyTxnFlag model;
    private String idMenu;
    private String namMenu;
    private MfcNoBook_LLD lld;

    /**
     * 
     * @return
     */
    @Override
    public String doSave() {
        if ("28401".equalsIgnoreCase(idMenu)) {
            getLogger().info("MESSAGE :" + model);
            SaveFcyTxnFlagLLDAction lldclass = new SaveFcyTxnFlagLLDAction();
            lldclass.setLld(lld);
            lldclass.setModel(model);
            lldclass.setNamMenu(this.namMenu);
            lldclass.setSession(getHSession());
            try {
                lldclass.doSave();
                this.setErrorCode(lldclass.getErrorCode());
            } catch (Exception e) {
                getLogger().debug("EXCEPTION :" + e, e);
                return ERROR;
            }
            if (this.getErrorCode().equalsIgnoreCase("") || this.getErrorCode() == null) {
                super.setErrorCode("success.3");
                return SUCCESS;
            } else {
                super.setErrorCode(this.getErrorCode());
                return ERROR;
            }
        }
        getLogger().debug("VALAS PROCESSING");
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
        getLogger().debug("MODEL vALAS :" + model);
        if (mfcTxnMaster == null) {
            getLogger().debug("TXN NULL");
            ParameterDao parameterDao = new ParameterDao(getHSession());
            Parameter parameter = parameterDao.get("MFCLMTAMT");
            BigDecimal amtAvailUsd = new BigDecimal(parameter.getValue());

            mfcTxnMaster = new MfcTxnMaster();
            mfcTxnMaster.setCompositeId(mfcTxnMasterPK);

            BigDecimal tmpAmtAvailUsd = new BigDecimal(String.valueOf(amtAvailUsd));
            BigDecimal tmpAmtTxnUsd = new BigDecimal(String.valueOf(model.getAmtTxnUsd()));
            tmpAmtAvailUsd = tmpAmtAvailUsd.subtract(tmpAmtTxnUsd);

            mfcTxnMaster.setAmtAvailUsd(tmpAmtAvailUsd.doubleValue());
            
            if ((model.getAmtTxnUsd().compareTo(amtAvailUsd) > 0) || (!"SOW".equalsIgnoreCase(model.getFlgJoin()))) {
                MfcUdMaster mfcUdMaster = mfcUdMasterDao.get(model.getNoCif(), model.getNoUd(), model.getCcyTxn());

                if (mfcUdMaster == null) {
                    super.setErrorCode("20401.4");
                    throw new BdsmException("20401.4");
                }

                BigDecimal udAvail = new BigDecimal(mfcUdMaster.getAmtAvailUsd());
                getLogger().debug("MUD :" + mfcUdMaster);
                if (udAvail.compareTo(model.getAmtTxnUsd()) < 0) {
                    super.setErrorCode("20401.5");
                    throw new BdsmException("20401.5");
                } else {
                    BigDecimal tmpAmtAvail = new BigDecimal(String.valueOf(mfcUdMaster.getAmtAvailUsd()));
                    //BigDecimal tmpAmtTxn = new BigDecimal(String.valueOf(model.getAmtTxnUsd()));
                    BigDecimal tmpAmtAvailLast = tmpAmtAvail.subtract(model.getAmtTxnUsd());
                    getLogger().debug("AVAIL : " +tmpAmtAvail+ " - " + model.getAmtTxnUsd() + " = " + tmpAmtAvailLast);
                    mfcUdMaster.setAmtAvailUsd(tmpAmtAvailLast.doubleValue());
                    mfcUdMaster.setIdMaintainedBy(model.getIdMaintainedBy());
                    mfcUdMaster.setIdMaintainedSpv(model.getIdMaintainedSpv());
                    mfcUdMaster.setDtmUpdated(dt);
                    mfcUdMaster.setDtmUpdatedSpv(dt);
                    mfcUdMasterDao.update(mfcUdMaster);
                    auditdao.insert(model.getIdMaintainedBy(), model.getIdMaintainedSpv(),
                            "MfcUdMaster", getNamMenu(), "Flag", "Update");
                }
            }

            mfcTxnMaster.setIdMaintainedBy(model.getIdMaintainedBy());
            mfcTxnMaster.setIdMaintainedSpv(model.getIdMaintainedSpv());
            if ("SOW".equalsIgnoreCase(model.getFlgJoin())) {
                mfcTxnMasterDao.insert(mfcTxnMaster);
                auditdao.insert(model.getIdMaintainedBy(), model.getIdMaintainedSpv(),
                        "MfcTxnMaster", getNamMenu(), "Flag", "Insert");
            }
        } else {
            getLogger().debug("TXN EXIST");
            BigDecimal txnAvail = new BigDecimal(mfcTxnMaster.getAmtAvailUsd());
            if ((model.getAmtTxnUsd().compareTo(txnAvail) > 0) || (!"SOW".equalsIgnoreCase(model.getFlgJoin()))) {
                MfcUdMaster mfcUdMaster = mfcUdMasterDao.get(model.getNoCif(), model.getNoUd(), model.getCcyTxn());

                if (mfcUdMaster == null) {
                    super.setErrorCode("20401.4");
                    throw new BdsmException("20401.4");
                }
                BigDecimal mfcAvail = new BigDecimal(mfcUdMaster.getAmtAvailUsd());
                getLogger().debug("MUD :" + mfcUdMaster);
                if (mfcAvail.compareTo(model.getAmtTxnUsd()) < 0) {
                    super.setErrorCode("20401.5");
                    throw new BdsmException("20401.5");
                } else {
                    BigDecimal tmpAmtAvail = new BigDecimal(String.valueOf(mfcUdMaster.getAmtAvailUsd()));
                    BigDecimal tmpAmtTxn = new BigDecimal(String.valueOf(model.getAmtTxnUsd()));
                    BigDecimal tmpAmtAvailLast = tmpAmtAvail.subtract(tmpAmtTxn);
                    getLogger().debug("AVAIL : " +tmpAmtAvail+ " - " +tmpAmtTxn + " = " + tmpAmtAvailLast);
                    mfcUdMaster.setAmtAvailUsd(tmpAmtAvailLast.doubleValue());
                    mfcUdMaster.setIdMaintainedBy(model.getIdMaintainedBy());
                    mfcUdMaster.setIdMaintainedSpv(model.getIdMaintainedSpv());
                    mfcUdMaster.setDtmUpdated(dt);
                    mfcUdMaster.setDtmUpdatedSpv(dt);
                    getLogger().debug("MFC :" + mfcUdMaster);
                    mfcUdMasterDao.update(mfcUdMaster);
                    auditdao.insert(model.getIdMaintainedBy(), model.getIdMaintainedSpv(),
                            "MfcUdMaster", getNamMenu(), "Flag", "Update");
                }
            }
            if ("SOW".equalsIgnoreCase(model.getFlgJoin())) {
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
                        "MfcTxnMaster", getNamMenu(), "Flag", "Update");
            }
        }
        getLogger().debug("BFORE MFC :" + this.model);
        MfcNoBook mfcNoBook = mfcNoBookDao.get(model.getNoAcct(), model.getRefTxn(), model.getTypMsg(), "VALAS");

        if (mfcNoBook == null) {
            super.setErrorCode("20401.1");
            throw new BdsmException("20401.1");
        }
        getLogger().debug("MID PROCESS");
        mfcNoBook.setStatus("A");
        mfcNoBook.setIdMaintainedBy(model.getIdMaintainedBy());
        mfcNoBook.setIdMaintainedSpv(model.getIdMaintainedSpv());
        mfcNoBook.setDtmUpdated(dt);
        mfcNoBook.setDtmUpdatedSpv(dt);
        mfcNoBookDao.update(mfcNoBook);
        auditdao.insert(model.getIdMaintainedBy(), model.getIdMaintainedSpv(),
                "MfcNoBook", getNamMenu(), "Flag", "Update");

        getLogger().debug("MODEL :" + this.model);
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
                "MfcTxnDetails", getNamMenu(), "Flag", "Insert");
        getLogger().debug("LAST PROCESS");
        super.setErrorCode("success.3");

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
    public void setModel(SaveFcyTxnFlag model) {
        this.model = model;
    }

    /**
     * @return the model
     */
    public SaveFcyTxnFlag getModel() {
        return model;
    }

    /**
     * @return the idMenu
     */
    public String getIdMenu() {
        return idMenu;
    }

    /**
     * @param idMenu the idMenu to set
     */
    public void setIdMenu(String idMenu) {
        this.idMenu = idMenu;
    }

    /**
     * @return the lld
     */
    public MfcNoBook_LLD getLld() {
        return lld;
    }

    /**
     * @param lld the lld to set
     */
    public void setLld(MfcNoBook_LLD lld) {
        this.lld = lld;
    }

    /**
     * @return the namMenu
     */
    public String getNamMenu() {
        return namMenu;
    }

    /**
     * @param namMenu the namMenu to set
     */
    public void setNamMenu(String namMenu) {
        this.namMenu = namMenu;
    }
}
