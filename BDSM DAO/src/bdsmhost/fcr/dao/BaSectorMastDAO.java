package bdsmhost.fcr.dao;


import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import bdsm.fcr.model.BaSectorMast;
import bdsm.fcr.model.BaSectorMastPK;
import bdsm.model.BaseModel;


/**
 * @author v00019372
 */
public class BaSectorMastDAO extends bdsmhost.dao.BaseDao {

	public BaSectorMastDAO(Session session) {
		super(session);
	}
	
	
	public BaSectorMast get(BaSectorMastPK id) {
		return (BaSectorMast) this.getSession().get(BaSectorMast.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<BaSectorMast> listByCodBI(String codBI, String like) {
		Query query = this.getSession().getNamedQuery("BaSectorMast#listByCodBI");
		query.setString("codBI", codBI);
		query.setString("strLike", "%" + like + "%");
		
		return query.list();
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
