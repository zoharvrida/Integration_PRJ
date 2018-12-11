package bdsmhost.dao;


import org.hibernate.Session;

import bdsm.model.BaseModel;
import bdsm.model.SknNgSPKErrorCode;


/**
 * @author v00019372
 */
public class SknNgSPKErrorCodeDAO extends BaseDao {

	public SknNgSPKErrorCodeDAO(Session session) {
		super(session);
	}
	
	
	public SknNgSPKErrorCode get(String code) {
		return (SknNgSPKErrorCode) this.getSession().get(SknNgSPKErrorCode.class, code);
	}
	
	@Override
	protected boolean doInsert(BaseModel model) {
		throw new UnsupportedOperationException("Not Yet Implemented...");
	}

	@Override
	protected boolean doUpdate(BaseModel model) {
		throw new UnsupportedOperationException("Not Yet Implemented...");
	}

	@Override
	protected boolean doDelete(BaseModel model) {
		throw new UnsupportedOperationException("Not Yet Implemented...");
	}

}
