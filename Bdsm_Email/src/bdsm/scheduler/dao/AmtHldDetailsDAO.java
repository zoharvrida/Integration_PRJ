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
 * @author v00018192
 */
public class AmtHldDetailsDAO extends BaseDao {

private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(AmtHldDetailsDAO.class);

    public AmtHldDetailsDAO(Session session) {
        super(session);
    }

    @Override
    protected boolean doInsert(BaseModel model) {
        this.getSession().save(model);
        return true;
    }

    @Override
    protected boolean doUpdate(BaseModel model) {
        this.getSession().update(model);
        return true;
    }

    @Override
    protected boolean doDelete(BaseModel model) {
        this.getSession().delete(model);
        return true;
    }
    
}
