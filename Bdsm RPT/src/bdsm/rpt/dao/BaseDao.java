/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.rpt.dao;

import bdsm.model.BaseModel;
import java.sql.Timestamp;
import java.util.Calendar;
import org.hibernate.Session;

/**
 *
 * @author v00019722
 */

public abstract class BaseDao {
    private Session session;
    
    public BaseDao(Session session) {
        this.session = session;
    }

    public boolean save(BaseModel model) {
        if (model != null) {
            boolean result = update(model);
            if (!result) {
                result = insert(model);
            }
            return result;
        }
        return false;
    }

    public boolean insert(BaseModel model) {
        if (model != null) {
            Calendar calendar = Calendar.getInstance();
            model.setIdCreatedBy(model.getIdMaintainedBy());
            model.setIdCreatedSpv(model.getIdMaintainedSpv());
            model.setIdUpdatedBy(model.getIdMaintainedBy());
            model.setIdUpdatedSpv(model.getIdMaintainedSpv());
            Timestamp dt = new java.sql.Timestamp(calendar.getTime().getTime());
            model.setDtmCreated(dt);
            model.setDtmCreatedSpv(dt);
            model.setDtmUpdated(dt);
            model.setDtmUpdatedSpv(dt);
            return doInsert(model);
        }
        return false;
    }

    public boolean update(BaseModel model) {
        if (model != null) {
            Calendar calendar = Calendar.getInstance();
            model.setIdUpdatedBy(model.getIdMaintainedBy());
            model.setIdUpdatedSpv(model.getIdMaintainedSpv());
            Timestamp dt = new java.sql.Timestamp(calendar.getTime().getTime());
            model.setDtmUpdated(dt);
            model.setDtmUpdatedSpv(dt);
            return doUpdate(model);
        }
        return false;
    }

    public boolean delete(BaseModel model) {
        if (model != null) {
            Calendar calendar = Calendar.getInstance();
            model.setIdUpdatedBy(model.getIdMaintainedBy());
            model.setIdUpdatedSpv(model.getIdMaintainedSpv());
            Timestamp dt = new java.sql.Timestamp(calendar.getTime().getTime());
            model.setDtmUpdated(dt);
            model.setDtmUpdatedSpv(dt);
            return doDelete(model);
        }
        return false;
    }

    protected abstract boolean doInsert(BaseModel model);
    protected abstract boolean doUpdate(BaseModel model);
    protected abstract boolean doDelete(BaseModel model);

    /**
     * @return the session
     */
    protected Session getSession() {
        return session;
    }

    /**
     * @param session the session to set
     */
    public void setSession(Session session) {
        this.session = session;
    }
}
