/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.dao;

import bdsm.model.BaseModel;
import bdsm.scheduler.model.TmpAdqPml;
import bdsmhost.dao.BaseDao;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author NCBS
 */
public class TmpAdqPmlDao extends BaseDao {

    public TmpAdqPmlDao(Session session) {
        super(session);
    }

    public TmpAdqPml get(int id) {
        return (TmpAdqPml) getSession().get(TmpAdqPml.class, id);
    }

    public TmpAdqPml getChecksum(String check) {
        Query q = getSession().createQuery("from TmpAdqPml where checksum = :prmCheck");
        q.setString("prmCheck", check);
        if (q.list().size() != 0) {
            return (TmpAdqPml) q.list().get(0);
        }
        return null;
    }

    public List<TmpAdqPml> list(String batchNo) {
        Query q = getSession().createQuery("from TmpAdqPml where batchNo = :prmBatchNo");
        q.setString("prmBatchNo", batchNo);
        return q.list();
    }

    @Override
    protected boolean doInsert(BaseModel model) {
        getSession().save((TmpAdqPml) model);
        return true;
    }

    @Override
    protected boolean doUpdate(BaseModel model) {
        getSession().update((TmpAdqPml) model);
        return true;
    }

    @Override
    protected boolean doDelete(BaseModel model) {
        getSession().delete((TmpAdqPml) model);
        return true;
    }
}
