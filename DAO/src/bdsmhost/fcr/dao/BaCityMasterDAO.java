package bdsmhost.fcr.dao;


import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;

import bdsm.fcr.model.BaCityMaster;
import bdsm.fcr.model.BaCityMasterPK;
import bdsm.model.BaseModel;


/**
 * @author v00019372
 */
public class BaCityMasterDAO extends bdsmhost.dao.BaseDao {

	public BaCityMasterDAO(Session session) {
		super(session);
	}
	

	public BaCityMaster get(BaCityMasterPK id) {
		return (BaCityMaster) this.getSession().get(BaCityMaster.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<BaCityMaster> listByCodBI(String codBI, String like) {
		Query query = this.getSession().getNamedQuery("BaCityMaster#listByCodBI");
		query.setString("codBI", codBI);
		query.setString("strLike", "%" + like + "%");
		
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, String>> listByCodBI_2(String codBI, String like) {
		Query query = this.getSession().getNamedQuery("BaCityMaster#listByCodBI_2");
		query.setString("codBI", codBI);
		query.setString("strLike", "%" + like + "%");
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		
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
