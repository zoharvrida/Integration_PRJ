package bdsm.scheduler.dao;


import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import bdsm.model.BaseModel;
import bdsm.scheduler.model.EOD_BOD_Dates;


/**
 * 
 * @author v00019372
 */
public class EOD_BOD_DatesDAO extends bdsmhost.dao.BaseDao {

	public EOD_BOD_DatesDAO(Session session) {
		super(session);
	}
	

	@SuppressWarnings("unchecked")
	public EOD_BOD_Dates get() {
		Query q = getSession().createQuery("FROM EOD_BOD_Dates WHERE id = 1");
		List<EOD_BOD_Dates> result = q.list();
		
		if (result.size() == 1)
			return result.get(0);
		
		return null;
	}
	
	
	@Override
	protected boolean doInsert(BaseModel model) {
		throw new UnsupportedOperationException("Insert Operation");
	}

	@Override
	protected boolean doUpdate(BaseModel model) {
		throw new UnsupportedOperationException("Update Operation");
	}

	@Override
	protected boolean doDelete(BaseModel model) {
		throw new UnsupportedOperationException("Delete Operation");
	}
}
