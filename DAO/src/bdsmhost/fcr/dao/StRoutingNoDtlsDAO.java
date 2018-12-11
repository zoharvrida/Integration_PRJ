package bdsmhost.fcr.dao;


import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import bdsm.fcr.model.StRoutingNoDtlsPK;
import bdsm.fcr.model.StRoutingNoDtls;
import bdsm.model.BaseModel;


/**
 * @author v00019372
 */
public class StRoutingNoDtlsDAO extends bdsmhost.dao.BaseDao {

	public StRoutingNoDtlsDAO(Session session) {
		super(session);
	}
	
	
	public StRoutingNoDtls get(StRoutingNoDtlsPK id) {
		return (StRoutingNoDtls) this.getSession().get(StRoutingNoDtls.class, id);
	}
	
	@Override
	protected boolean doInsert(BaseModel model) {
		throw new UnsupportedOperationException("Not yet implemented!!!");
	}

	@Override
	protected boolean doUpdate(BaseModel model) {
		throw new UnsupportedOperationException("Not yet implemented!!!");
	}

	@Override
	protected boolean doDelete(BaseModel model) {
		throw new UnsupportedOperationException("Not yet implemented!!!");
	}

}
