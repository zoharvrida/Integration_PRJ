/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.dao;

import bdsm.model.BaseModel;
import bdsm.model.FcrBaCcyRate;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author v00013493
 */
public class FcrBaCcyRateDao extends BaseDao {
    public FcrBaCcyRateDao(Session session) {
        super(session);
    }
    
    public FcrBaCcyRate get() {
        Criteria criteriaQuery = getSession().createCriteria(FcrBaCcyRate.class);
        criteriaQuery.add(Restrictions.eq("compositeId.codCcy", 840));
        criteriaQuery.add(Restrictions.eq("compositeId.flgMntStatus", "A"));
        criteriaQuery.addOrder(Order.desc("compositeId.datTimRateEff"));
        criteriaQuery.setMaxResults(1);
        
        return (FcrBaCcyRate)criteriaQuery.uniqueResult();
    }
    
    public FcrBaCcyRate get(int codCcy) {
        Criteria criteriaQuery = getSession().createCriteria(FcrBaCcyRate.class);
        criteriaQuery.add(Restrictions.eq("compositeId.codCcy", codCcy));
        criteriaQuery.add(Restrictions.eq("compositeId.flgMntStatus", "A"));
        criteriaQuery.addOrder(Order.desc("compositeId.datTimRateEff"));
        criteriaQuery.setMaxResults(1);
        
        return (FcrBaCcyRate)criteriaQuery.uniqueResult();
    }    
    
    @Override
    protected boolean doInsert(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet.");
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
