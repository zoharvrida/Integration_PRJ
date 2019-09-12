package bdsmhost.dao;


import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import bdsm.model.BaseModel;


/**
 * @author v00019372
 */
public class MPClassDetailsDAO extends BaseDao {

	public MPClassDetailsDAO(Session session) {
		super(session);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<? extends Object> listComponentByClass(Integer codeClass) {
		Query query = this.getSession().getNamedQuery("MPClassDetails#listComponentByClass");
		query.setInteger("codeClass", codeClass);
		
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
