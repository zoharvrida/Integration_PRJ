/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.dao;

import bdsm.model.BaseModel;
import bdsm.scheduler.model.TmpNtfsFcyRateFtr;
import bdsmhost.dao.BaseDao;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author bdsm
 */
public class TmpNtfsFcyRateFtrDao extends BaseDao {

    public TmpNtfsFcyRateFtrDao(Session session) {
        super(session);
    }

    public TmpNtfsFcyRateFtr get(String batch) {
        Query q = getSession().createQuery("from TmpNtfsFcyRateFtr where bdsmBatch = :batch");
        q.setString("batch", batch);
        return (TmpNtfsFcyRateFtr) q.uniqueResult();
    }

    @Override
    protected boolean doInsert(BaseModel model) {
        getSession().save((TmpNtfsFcyRateFtr) model);
        return true;
    }

    @Override
    protected boolean doUpdate(BaseModel model) {
        getSession().update((TmpNtfsFcyRateFtr) model);
        return true;
    }

    @Override
    protected boolean doDelete(BaseModel model) {
        getSession().delete((TmpNtfsFcyRateFtr) model);
        return true;
    }
}
