/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.dao;

import bdsm.model.BaseModel;
import bdsm.scheduler.model.TmpNtfsFcyRateHdr;
import bdsmhost.dao.BaseDao;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author bdsm
 */
public class TmpNtfsFcyRateHdrDao extends BaseDao {

    public TmpNtfsFcyRateHdrDao(Session session) {
        super(session);
    }

    public TmpNtfsFcyRateHdr get(String batch){
        Query q = getSession().createQuery("from TmpNtfsFcyRateHdr where BdsmBatch = :batch");
        q.setString("batch", batch);
        return (TmpNtfsFcyRateHdr)q.uniqueResult();
    }

    @Override
    protected boolean doInsert(BaseModel model) {
        getSession().save((TmpNtfsFcyRateHdr) model);
        return true;
    }

    @Override
    protected boolean doUpdate(BaseModel model) {
        getSession().update((TmpNtfsFcyRateHdr) model);
        return true;
    }

    @Override
    protected boolean doDelete(BaseModel model) {
        getSession().delete((TmpNtfsFcyRateHdr) model);
        return true;
    }
}
