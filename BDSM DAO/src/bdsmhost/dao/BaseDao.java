/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.dao;

import bdsm.model.BaseModel;
import java.sql.Timestamp;
import java.util.Calendar;
import org.apache.log4j.Logger;
import org.hibernate.Session;

/**
 *
 * @author bdsm
 */
public abstract class BaseDao {
    private Session session;
    
    public BaseDao(Session session) {
        this.session = session;
    }
	
	private Logger logger = Logger.getLogger(BaseDao.class);
    public boolean save(BaseModel model) {
        if (model != null) {
            boolean result = false;
            try {
                result = update(model);
                if (!result) {
                    result = insert(model);
                }
            } catch (Exception e) {
                logger.debug("EXCEPTION INSERT :" + e,e);
            }
            return result;
        }
        return false;
    }

    public boolean insert(BaseModel model) {
        if (model != null) {
			try {
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
			} catch (Exception e) {
				logger.info("EXCEPTION BASE :" + e,e);
			}
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

    public void evictObjectFromPersistenceContext(Object obj) {
		if (this.getSession().contains(obj))
			this.getSession().evict(obj);
	}
    
    public Object merge(BaseModel model) {
    	return this.getSession().merge(model);
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
