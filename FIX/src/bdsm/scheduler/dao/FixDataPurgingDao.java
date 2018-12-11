/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.dao;

import bdsm.model.BaseModel;
import bdsm.scheduler.model.FixDataPurging;
import bdsmhost.dao.BaseDao;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author NCBS
 */
public class FixDataPurgingDao extends BaseDao {

    /**
     * 
     * @param session
     */
    public FixDataPurgingDao(Session session) {
        super(session);
    }

    /**
     * 
     * @param id
     * @return
     */
    public FixDataPurging get(int id) {
        return (FixDataPurging) getSession().get(FixDataPurging.class, id);
    }
    
    /**
     * 
     * @return
     */
    public List<FixDataPurging> list(){
        Query q = getSession().createQuery("from FixDataPurging where flgstat = 'A'");
        return q.list();
    }

    /**
     * 
     * @param model
     * @return
     */
    @Override
    protected boolean doInsert(BaseModel model) {
        getSession().save((FixDataPurging) model);
        return true;
    }

    /**
     * 
     * @param model
     * @return
     */
    @Override
    protected boolean doUpdate(BaseModel model) {
        getSession().update((FixDataPurging) model);
        return true;
    }

    /**
     * 
     * @param model
     * @return
     */
    @Override
    protected boolean doDelete(BaseModel model) {
        getSession().delete((FixDataPurging) model);
        return true;
    }
}
