/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.dao;

import bdsm.model.BaseModel;
import bdsm.model.MfcSlMaster;
import bdsm.model.MfcSlMasterPK;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author user
 */
public class MfcSlMasterDao extends BaseDao {
    public MfcSlMasterDao(Session session) {
        super(session);
    }

    public MfcSlMaster get(MfcSlMasterPK pk) {
        return (MfcSlMaster)getSession().get(MfcSlMaster.class, pk);
    }
    
    public MfcSlMaster get(Integer noCif, Integer period) {
        Criteria criteriaQuery = getSession().createCriteria(MfcSlMaster.class);
        criteriaQuery.add(Restrictions.eq("compositeId.noCif", noCif));
        criteriaQuery.add(Restrictions.eq("compositeId.period", period));
        return (MfcSlMaster)criteriaQuery.uniqueResult();
    }
    
    @Override
    protected boolean doInsert(BaseModel model) {
        getSession().save((MfcSlMaster)model);
        return true;
    }

    @Override
    protected boolean doUpdate(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected boolean doDelete(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
