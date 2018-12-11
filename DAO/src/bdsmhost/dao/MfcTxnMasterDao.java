/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.dao;

import bdsm.model.BaseModel;
import bdsm.model.MfcTxnMaster;
import bdsm.model.MfcTxnMasterPK;
import org.hibernate.Session;

/**
 *
 * @author user
 */
public class MfcTxnMasterDao extends BaseDao {
    public MfcTxnMasterDao(Session session) {
        super(session);
    }

    public MfcTxnMaster get(MfcTxnMasterPK pk) {
        return (MfcTxnMaster)getSession().get(MfcTxnMaster.class, pk);
    }
    
    @Override
    protected boolean doInsert(BaseModel model) {
        getSession().save((MfcTxnMaster)model);
        return true;
    }

    @Override
    protected boolean doUpdate(BaseModel model) {
        getSession().update((MfcTxnMaster)model);
        return true;
    }

    @Override
    protected boolean doDelete(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}