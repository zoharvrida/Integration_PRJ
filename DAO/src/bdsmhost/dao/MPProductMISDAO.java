package bdsmhost.dao;


import org.hibernate.Query;
import org.hibernate.Session;

import bdsm.model.BaseModel;
import bdsm.model.MPProductMIS;


/**
 * @author v00019372
 */
public class MPProductMISDAO extends BaseDao {

	public MPProductMISDAO(Session session) {
		super(session);
	}
	
	
	public MPProductMIS get(MPProductMIS id) {
		return ((MPProductMIS) this.getSession().get(MPProductMIS.class, id));
	}
	
	@SuppressWarnings("unchecked")
	public java.util.List<MPProductMIS> listByProductCode(Integer productCode) {
		Query query = this.getSession().getNamedQuery("MPProductMIS#listByProductCode");
		query.setInteger("productCode", productCode);
		
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
