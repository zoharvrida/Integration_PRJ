package bdsmhost.dao;


import org.hibernate.Session;

import bdsm.model.BaseModel;
import bdsm.model.TPINStatusMaster;


public class TPINStatusMasterDAO extends BaseDao {

	public TPINStatusMasterDAO(Session session) {
		super(session);
	}

	@Override
	protected boolean doInsert(BaseModel model) {
		throw new UnsupportedOperationException("Not supported yet");
	}

	@Override
	protected boolean doUpdate(BaseModel model) {
		throw new UnsupportedOperationException("Not supported yet");
	}

	@Override
	protected boolean doDelete(BaseModel model) {
		throw new UnsupportedOperationException("Not supported yet");
	}
	
	public TPINStatusMaster get(java.io.Serializable code) {
		return (TPINStatusMaster) this.getSession().get(TPINStatusMaster.class, code);
	}

}
