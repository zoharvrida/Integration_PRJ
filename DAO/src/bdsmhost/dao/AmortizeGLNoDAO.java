package bdsmhost.dao;


import org.hibernate.Query;
import org.hibernate.Session;

import bdsm.model.AmortizeGLNo;
import bdsm.model.AmortizeGLNoPK;
import bdsm.model.BaseModel;


/**
 * 
 * @author v00019372
 */
public class AmortizeGLNoDAO extends BaseDao {

	public AmortizeGLNoDAO(Session session) {
		super(session);
	}
	
	
	public AmortizeGLNo get(AmortizeGLNoPK id) {
		return (AmortizeGLNo) this.getSession().get(AmortizeGLNo.class, id);
	}
	
	public java.util.List<AmortizeGLNo> getAll() {
		return this.getByTypeAndProcessType(-1, -1);
	}
	
	@SuppressWarnings("unchecked")
	public java.util.List<AmortizeGLNo> getByTypeAndProcessType(Integer type, Integer processType) {
		Query query = this.getSession().getNamedQuery("AmortizeGLNo#getByTypeAndProcessType");
		query.setInteger("type", type);
		query.setInteger("processType", processType);
		
		return query.list();
	}
	

	@Override
	protected boolean doInsert(BaseModel model) {
		throw new UnsupportedOperationException("Not Implemented Yet!!!");
	}

	@Override
	protected boolean doUpdate(BaseModel model) {
		throw new UnsupportedOperationException("Not Implemented Yet!!!");
	}

	@Override
	protected boolean doDelete(BaseModel model) {
		throw new UnsupportedOperationException("Not Implemented Yet!!!");
	}

}
