/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.dao;

import bdsm.model.BaseModel;
import bdsmhost.dao.BaseDao;
import org.hibernate.Session;

/**
 *
 * @author bdsm
 */
public class FixSentBoxDao extends BaseDao {

    /**
     * 
     * @param session
     */
    public FixSentBoxDao(Session session) {
        super(session);
    }

    /**
     * 
     * @param model
     * @return
     */
    @Override
    protected boolean doInsert(BaseModel model) {
        getSession().save(model);
        return true;
    }

    /**
     * 
     * @param model
     * @return
     */
    @Override
    protected boolean doUpdate(BaseModel model) {
        getSession().update(model);
        return true;
    }

    /**
     * 
     * @param model
     * @return
     */
    @Override
    protected boolean doDelete(BaseModel model) {
        getSession().delete(model);
        return true;
    }
}
