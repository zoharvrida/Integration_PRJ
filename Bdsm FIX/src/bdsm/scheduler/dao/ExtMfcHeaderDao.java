/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.dao;

import bdsm.model.BaseModel;
import bdsm.scheduler.model.ExtMfcHeader;
import bdsmhost.dao.BaseDao;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author v00013493
 */
public class ExtMfcHeaderDao extends BaseDao {
    /**
     * 
     * @param session
     */
    public ExtMfcHeaderDao(Session session) {
        super(session);
    }

    /**
     * 
     * @param noBatch
     * @return
     */
    public List get(String noBatch) {
        Criteria criteriaQuery = getSession().createCriteria(ExtMfcHeader.class);
        criteriaQuery.add(Restrictions.eq("noBatch", noBatch));
        return criteriaQuery.list();
    }
    
    /**
     * 
     * @param batchId
     * @return
     */
    public ExtMfcHeader getModel(String batchId) {
        Criteria criteriaQuery = getSession().createCriteria(ExtMfcHeader.class);
        criteriaQuery.add(Restrictions.eq("batchId", batchId));
        return (ExtMfcHeader)criteriaQuery.uniqueResult();
    }
    
    /**
     * 
     * @param model
     * @return
     */
    @Override
    protected boolean doInsert(BaseModel model) {
        getSession().save((ExtMfcHeader)model);
        return true;
    }

    /**
     * 
     * @param model
     * @return
     */
    @Override
    protected boolean doUpdate(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     * @param model
     * @return
     */
    @Override
    protected boolean doDelete(BaseModel model) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
