/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.web;

import bdsm.model.*;
import bdsm.scheduler.PropertyPersister;
import bdsm.util.BdsmException;
import bdsm.util.SchedulerUtil;
import bdsmhost.dao.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Session;

/**
 *
 * @author v00013493
 */
public class SaveFcyTxnFlagLLDAction {

    private static final long serialVersionUID = 5078264277068533593L;
    private static Calendar calendar = Calendar.getInstance();
    private List modelList;
    private SaveFcyTxnFlag model;
    private MfcNoBook_LLD lld;
    private Logger logger = Logger.getLogger(SaveFcyTxnFlagLLDAction.class);
    private Session session;
    private String namMenu;
    private String errorCode;

    /**
     * 
     */
    public void doSave() {
        logger.debug("MAP APP:" + lld);
        logger.debug("MAP SAVE:" + model);
        String status = "ERROR";
        AuditlogDao auditdao = new AuditlogDao(session);
        MfcNoBookDao mfcNoBookDao = new MfcNoBookDao(session);
        MfcUdMaster_LLDDAO mfcUdMasterDao = new MfcUdMaster_LLDDAO(session);
        MfcUdMaster_LLD llds = new MfcUdMaster_LLD();
        MfcUdMasterPK pks = new MfcUdMasterPK();
        if (PropertyPersister.LLDDummy.equalsIgnoreCase(model.getNoUd())) {
            pks = new MfcUdMasterPK(0, model.getNoUd());
        } else {
            pks = new MfcUdMasterPK(model.getNoCif(), model.getNoUd());
        }

        logger.debug("PK :" + pks);
        MfcUdMaster_LLD lldModel = mfcUdMasterDao.get(pks);

        if (lldModel == null) {
            // UD not available ??
            setErrorCode("28401.1");
            //return status;
            throw new BdsmException("28401.1");
        }

        Timestamp dt = new java.sql.Timestamp(calendar.getTime().getTime());

        //if (mfcTxnMaster == null) {
        Double amtAvailUsd = new Double(PropertyPersister.lldAmount);

        double availUSD = Double.parseDouble(String.valueOf(amtAvailUsd));
        String availUSDstr = String.format("%f", availUSD);
        double amtUSD = Double.parseDouble(String.valueOf(model.getAmtTxnUsd()));
        String amtUSDstr = String.format("%f", amtUSD);
        // greater than 100.000 USD
        BigDecimal tmpAmtAvailUsd = new BigDecimal(availUSDstr);
        BigDecimal tmpAmtTxnUsd = new BigDecimal(amtUSDstr);
        logger.debug("USD :" + tmpAmtTxnUsd + "AVAIL :" + tmpAmtAvailUsd);
        

        MfcNoBook mfcNoBook = mfcNoBookDao.get(model.getNoAcct(), model.getRefTxn(), model.getTypMsg(), lld.getTypAcct(),"LLD");
        if (PropertyPersister.LLDDummy.equalsIgnoreCase(model.getNoUd())) {
            logger.debug("SAME NAME NO UD");
        } else {
            if ((tmpAmtTxnUsd.compareTo(tmpAmtAvailUsd) > 0)) {
                //MfcUdMaster mfcUdMaster = mfcUdMasterDao.get(model.getNoCif(), model.getNoUd(), model.getCcyTxn());
                MfcUdMasterPK pkc = new MfcUdMasterPK();
                pkc.setNoCif(model.getNoCif());
                pkc.setNoUd(model.getNoUd());
                llds = mfcUdMasterDao.get(pkc);
                //MfcUdMaster mfcUdMaster = mfcUdMasterDao.get(model.getNoCif(), model.getNoUd(), model.getCcyTxn());
                
                if (llds == null) {
                    setErrorCode("20401.4");
                    //return status;
                    throw new BdsmException("20401.4");
                }
                logger.debug("LLD OBJ :" + llds);
                logger.debug("AMT :" + llds.getAmtAvailUsd() + " - " + tmpAmtTxnUsd);
                if (llds.getAmtAvailUsd().compareTo(tmpAmtTxnUsd) < 0) {
                    //super.setErrorCode("20401.5");
                    throw new BdsmException("20401.5");
                } else {
                    BigDecimal tmpAmtAvail = llds.getAmtAvailUsd();
                    BigDecimal tmpAmtTxn = new BigDecimal(String.valueOf(model.getAmtTxnUsd()));
                    tmpAmtAvail = tmpAmtAvail.subtract(tmpAmtTxn);
                    logger.debug("AMT AVAIL :" + tmpAmtAvail);
                    llds.setAmtAvailUsd(tmpAmtAvail);
                    llds.setIdMaintainedBy(model.getIdMaintainedBy());
                    llds.setIdMaintainedSpv(model.getIdMaintainedSpv());
                    llds.setDtmUpdated(dt);
                    llds.setDtmUpdatedSpv(dt);
                    mfcUdMasterDao.update(llds);
                    auditdao.insert(model.getIdMaintainedBy(), model.getIdMaintainedSpv(),
                            "MfcUdMaster_LLD", getNamMenu(), "Flag", "Update");
                }
            } else {
                
            }
        }
        if (mfcNoBook == null) {
                setErrorCode("20401.1");
                mfcUdMasterDao.evictObjectFromPersistenceContext(llds);
                //throw new BdsmException("20401.1");
            } else {
                mfcNoBook.setStatus("A");
                mfcNoBook.setIdMaintainedBy(model.getIdMaintainedBy());
                mfcNoBook.setIdMaintainedSpv(model.getIdMaintainedSpv());
                mfcNoBook.setDtmUpdated(dt);
                mfcNoBook.setDtmUpdatedSpv(dt);
                mfcNoBookDao.update(mfcNoBook);
                auditdao.insert(model.getIdMaintainedBy(), model.getIdMaintainedSpv(),
                        "MfcNoBook", getNamMenu(), "Flag", "Update");
                this.lld.setDtPost(mfcNoBook.getDtPost());
                this.lld.setDtValue(mfcNoBook.getDtValue());
                this.lld.setDtmTxn(mfcNoBook.getDtmTxn());
                this.lld.setAmtTxn(mfcNoBook.getAmtTxn());
                //super.setErrorCode("success.3");
                logger.debug("update Done :" + mfcNoBook);
                flagToLLD();
            }
    }

    private String flagToLLD() {
        MfcNoBook_LLDDAO daoLLD = new MfcNoBook_LLDDAO(session);
        MfcNoBook_LLD mfcLLD = new MfcNoBook_LLD();
        MfcTxnDetails_LLD mfcDtls = new MfcTxnDetails_LLD();
        MfcTxnDetailsLLDDao daoDtlsLLD = new MfcTxnDetailsLLDDao(session);
        AuditlogDao auditdao = new AuditlogDao(session);
        Timestamp dt = new java.sql.Timestamp(calendar.getTime().getTime());

        MfcNoBookPK pk = new MfcNoBookPK(model.getNoAcct(), model.getRefTxn(), model.getTypMsg());
        MfcTxnDetailsPK pks = new MfcTxnDetailsPK(model.getNoAcct(), model.getRefTxn());

        logger.debug("PK :" + pks);
        mfcLLD = daoLLD.get(pk);
        mfcDtls = daoDtlsLLD.get(pks);

        logger.debug("LLD :" + mfcLLD + " - " + mfcDtls);
        try {
            if (mfcLLD == null) {
                mfcLLD = this.lld;
                List lovVal = SchedulerUtil.getParameter(PropertyPersister.LLDLainlain, "\\;");
                if (lovVal.get(0).toString().equalsIgnoreCase(this.model.getCategory())){
                    mfcLLD.setDocumentType(10);
                }
                
                if (PropertyPersister.LLDDummy.equalsIgnoreCase(this.model.getNoUd())) {
                    mfcLLD.setDocumentType(99);
                }
                
                mfcLLD.setStatus("A");
                mfcLLD.setIdMaintainedBy(model.getIdMaintainedBy());
                mfcLLD.setIdMaintainedSpv(model.getIdMaintainedSpv());
                mfcLLD.setDtmUpdated(dt);
                mfcLLD.setDtmUpdatedSpv(dt);
                
                daoLLD.insert(mfcLLD);
                logger.debug("MFCNLLD :" + mfcLLD);
                auditdao.insert(model.getIdMaintainedBy(), model.getIdMaintainedSpv(),
                        "MfcNoBook_LLD", getNamMenu(), "Flag", "Update");
            } else {
                mfcLLD.setStatus("A");
                mfcLLD.setIdMaintainedBy(model.getIdMaintainedBy());
                mfcLLD.setIdMaintainedSpv(model.getIdMaintainedSpv());
                mfcLLD.setDtmUpdated(dt);
                mfcLLD.setDtmUpdatedSpv(dt);
                daoLLD.update(mfcLLD);
            }

            if (mfcDtls == null) {
                mfcDtls = new MfcTxnDetails_LLD(pks);
                mfcDtls.setNoCif(model.getNoCif());
                mfcDtls.setNoUd(model.getNoUd());
                mfcDtls.setStatus("A");
                mfcDtls.setIdMaintainedBy(model.getIdMaintainedBy());
                mfcDtls.setIdMaintainedSpv(model.getIdMaintainedSpv());
                daoDtlsLLD.insert(mfcDtls);
            } else {
                mfcDtls.setStatus("A");
                mfcDtls.setIdMaintainedBy(model.getIdMaintainedBy());
                mfcDtls.setIdMaintainedSpv(model.getIdMaintainedSpv());
                daoDtlsLLD.update(mfcDtls);
            }
            this.setErrorCode("");
            auditdao.insert(model.getIdMaintainedBy(), model.getIdMaintainedSpv(),
                    "MfcTxnDetails_LLD", getNamMenu(), "Flag", "Insert");
        } catch (Exception e) {
            logger.debug("EXCEPTION ROLLBACK :" + e, e);
            daoDtlsLLD.evictObjectFromPersistenceContext(daoDtlsLLD);
            daoLLD.evictObjectFromPersistenceContext(daoLLD);
            return "ERROR";
        }
        return "SUCCESS";
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
     * @return the session
     */
    public Session getSession() {
        return session;
    }

    /**
     * @param session the session to set
     */
    public void setSession(Session session) {
        this.session = session;
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

    /**
     * @return the errorCode
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * @param errorCode the errorCode to set
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
