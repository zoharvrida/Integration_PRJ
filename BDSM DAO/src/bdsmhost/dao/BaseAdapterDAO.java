package bdsmhost.dao;

import org.hibernate.Session;


/**
 * @author v00019372
 */
public abstract class BaseAdapterDAO extends BaseDao {

	public BaseAdapterDAO(Session session) {
		super(session);
	}


	@Override
	protected boolean doInsert(bdsm.model.BaseModel model) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	protected boolean doUpdate(bdsm.model.BaseModel model) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	protected boolean doDelete(bdsm.model.BaseModel model) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

}
