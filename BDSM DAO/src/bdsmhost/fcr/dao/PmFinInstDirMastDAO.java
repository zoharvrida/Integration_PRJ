package bdsmhost.fcr.dao;

import org.hibernate.Session;

import bdsm.fcr.model.PmFinInstDirMast;
import bdsm.fcr.model.PmFinInstDirMastPK;
import bdsm.model.BaseModel;


/**
 * @author v00019372
 */
public class PmFinInstDirMastDAO extends bdsmhost.dao.BaseDao {

	public PmFinInstDirMastDAO(Session session) {
		super(session);
	}
	
	
	public PmFinInstDirMast get(PmFinInstDirMastPK id) {
		return (PmFinInstDirMast) this.getSession().get(PmFinInstDirMast.class, id);
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
