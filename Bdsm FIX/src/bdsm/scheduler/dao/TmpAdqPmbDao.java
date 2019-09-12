/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.dao;

import bdsm.model.BaseModel;
import bdsm.scheduler.model.TmpAdqPmb;
import bdsmhost.dao.BaseDao;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author NCBS
 */
public class TmpAdqPmbDao extends BaseDao {

    /**
     * 
     * @param session
     */
    public TmpAdqPmbDao(Session session) {
        super(session);
    }

    /**
     * 
     * @param id
     * @return
     */
    public TmpAdqPmb get(int id) {
        return (TmpAdqPmb) getSession().get(TmpAdqPmb.class, id);
    }

    /**
     * 
     * @param check
     * @return
     */
    public TmpAdqPmb getChecksum(String check) {
        Query q = getSession().createQuery("from TmpAdqPmb where checksum = :prmCheck");
        q.setString("prmCheck", check);
        if (q.list().size() != 0) {
            return (TmpAdqPmb)q.list().get(0);
        }
        return null;
    }

    /**
     * 
     * @param batchNo
     * @return
     */
    public List<TmpAdqPmb> list(String batchNo) {
        Query q = getSession().createQuery("from TmpAdqPmb where batchNo = :prmBatchNo");
        q.setString("prmBatchNo", batchNo);
        return q.list();
    }

    /**
     * 
     * @param model
     * @return
     */
    @Override
    protected boolean doInsert(BaseModel model) {
        getSession().save((TmpAdqPmb) model);
        return true;
    }

    /**
     * 
     * @param model
     * @return
     */
    @Override
    protected boolean doUpdate(BaseModel model) {
        getSession().update((TmpAdqPmb) model);
        return true;
    }

    /**
     * 
     * @param model
     * @return
     */
    @Override
    protected boolean doDelete(BaseModel model) {
        getSession().delete((TmpAdqPmb) model);
        return true;
    }
}
