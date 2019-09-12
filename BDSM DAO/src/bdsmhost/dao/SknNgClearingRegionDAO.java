package bdsmhost.dao;


import org.hibernate.Session;

import bdsm.model.BaseModel;
import bdsm.model.SknNgClearingRegion;


/**
 * 
 * @author v00019372
 */
public class SknNgClearingRegionDAO extends BaseDao {

	public SknNgClearingRegionDAO(Session session) {
		super(session);
	}
	
	
	public SknNgClearingRegion get(java.io.Serializable id) {
		return (SknNgClearingRegion) this.getSession().get(SknNgClearingRegion.class, id);
	}
	

	@Override
	protected boolean doInsert(BaseModel model) {
		this.getSession().save((SknNgClearingRegion) model);
		return true;
	}

	@Override
	protected boolean doUpdate(BaseModel model) {
		this.getSession().update((SknNgClearingRegion) model);
		return true;
	}

	@Override
	protected boolean doDelete(BaseModel model) {
		this.getSession().delete((SknNgClearingRegion) model);
		return true;
	}

}
