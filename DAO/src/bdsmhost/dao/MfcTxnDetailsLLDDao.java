/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.dao;

import bdsm.model.BaseModel;
import bdsm.model.MfcTxnDetailsPK;
import bdsm.model.MfcTxnDetails_LLD;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author 00110310
 */
public class MfcTxnDetailsLLDDao extends BaseDao {
     public MfcTxnDetailsLLDDao(Session session) {
        super(session);
    }

    private Logger logger = Logger.getLogger(MfcNoBook_LLDDAO.class);
    
    public MfcTxnDetails_LLD get(MfcTxnDetailsPK pk){
        return (MfcTxnDetails_LLD) getSession().get(MfcTxnDetails_LLD.class, pk);
    }
     
    @Override
    protected boolean doInsert(BaseModel model) {
        logger.debug("INSERT :" + (MfcTxnDetails_LLD)model);
        getSession().save((MfcTxnDetails_LLD)model);
        return true;
    }

    @Override
    protected boolean doUpdate(BaseModel model) {
        logger.debug("UPDATE :" + (MfcTxnDetails_LLD)model);
        try {
            getSession().update((MfcTxnDetails_LLD) model);
        } catch (Exception e) {
            logger.debug("EX UPDATE :" + e,e);
        }
        return true;
    }

    @Override
    protected boolean doDelete(BaseModel model) {
        getSession().delete((MfcTxnDetails_LLD)model);
        return true;
    }
}
