package bdsmhost.dao;


import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import bdsm.model.BaseModel;
import bdsm.model.MPComponent;


/**
 * @author v00019372
 */
public class MPComponentDAO extends BaseDao {

	public MPComponentDAO(Session session) {
		super(session);
	}
	
	
	public MPComponent get(Integer code) {
		return ((MPComponent) this.getSession().get(MPComponent.class, code));
	}
	
	@SuppressWarnings("unchecked")
	public List<MPComponent> listByType(String type) {
		Query query = this.getSession().getNamedQuery("MPComponent#listByType");
		query.setCharacter("type", type.charAt(0));
		
		return query.list();
	}
	

	@Override
	protected boolean doInsert(BaseModel model) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	protected boolean doUpdate(BaseModel model) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	protected boolean doDelete(BaseModel model) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

}
 