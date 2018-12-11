package bdsmhost.dao;


import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import bdsm.model.BaseModel;
import bdsm.model.MPClass;


/**
 * @author v00019372
 */
public class MPClassDAO extends BaseDao {

	public MPClassDAO(Session session) {
		super(session);
	}
	
	
	public MPClass get(Integer codeClass) {
		return ((MPClass) this.getSession().get(MPClass.class, codeClass));
	}
	
	@SuppressWarnings("unchecked")
	public List<MPClass> listByProductMIS(Integer codeProduct, String codeMIS) {
		Query query = this.getSession().getNamedQuery("MPClass#listByProductMIS");
		query.setInteger("codeProduct", codeProduct);
		query.setString("codeMIS", codeMIS);
		
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
