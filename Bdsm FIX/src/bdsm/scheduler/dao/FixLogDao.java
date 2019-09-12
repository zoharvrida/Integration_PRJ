package bdsm.scheduler.dao;


import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import bdsm.model.BaseModel;
import bdsm.scheduler.model.FixLog;


/**
 * 
 * @author bdsm
 */
public class FixLogDao extends bdsmhost.dao.BaseDao {
	private static final Logger LOGGER  = Logger.getLogger(FixLogDao.class);
    /**
     * 
     * @param session
     */
    public FixLogDao(Session session) {
		super(session);
	}
	

    /**
     * 
     * @param inboxId
     * @return
     */
    public FixLog get(String inboxId) {
		LOGGER.debug("Get FixLog using Inbox ID : " + inboxId);
		StringBuilder sb = new StringBuilder("from FixLog ");
		sb.append(" where fixLogPK.inboxId = :inboxId");
		Query q = this.getSession().createQuery(sb.toString());
		q.setString("inboxId", inboxId);
		List result = q.list();
		LOGGER.debug("List fixlog result : " + result.size());
		
		if (result.size() == 1) {
			LOGGER.debug("Return  : " + result.get(0));
			return (FixLog) result.get(0);
		}
		
		return null;
	}

    /**
     * 
     * @param filename
     * @param dtPost
     * @return
     */
    public FixLog get(String filename, String dtPost) {
		StringBuilder sb = new StringBuilder("from FixLog ");
		sb.append(" where fixLogPK.fileName = :fileName and fixLogPK.dtPost = to_date(:dtPost,'dd/MM/yyyy')");
		Query q = this.getSession().createQuery(sb.toString());
		q.setString("fileName", filename);
		q.setString("dtPost", dtPost);
		List result = q.list();
		
		if (result.size() == 1)
			return (FixLog) result.get(0);
		
		return null;
	}
	
    /**
     * 
     * @param filename
     * @param dtPost
     * @return
     */
    public List<FixLog> getByFilenameAndDatePost(String filename, java.util.Date dtPost) {
		Criteria crt = this.getSession().createCriteria(FixLog.class);
		crt.add(Restrictions.like("fixLogPK.fileName", filename));
		if (dtPost != null)
			crt.add(Restrictions.eq("fixLogPK.dtPost", dtPost));
		
		return crt.list();
	}
	

    /**
     * 
     * @param baseModel
     * @return
     */
    @Override
	protected boolean doInsert(BaseModel baseModel) {
		this.getSession().save((FixLog) baseModel);
		return true;
	}

    /**
     * 
     * @param baseModel
     * @return
     */
    @Override
	protected boolean doUpdate(BaseModel baseModel) {
		this.getSession().update((FixLog) baseModel);
		return true;
	}

    /**
     * 
     * @param baseModel
     * @return
     */
    @Override
	protected boolean doDelete(BaseModel baseModel) {
		this.getSession().delete((FixLog) baseModel);
		return true;
	}

}
