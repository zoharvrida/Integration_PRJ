package bdsmhost.dao;


import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import bdsm.model.BaseModel;
import bdsm.model.FCR_CMPreembossCardInventory;


public class FCR_CMPreembossCardInventoryDAO extends BaseDao {

	public FCR_CMPreembossCardInventoryDAO(Session session) {
		super(session);
	}
	
	public FCR_CMPreembossCardInventory get(java.io.Serializable pk) {
		return (FCR_CMPreembossCardInventory) this.getSession().get(FCR_CMPreembossCardInventory.class, pk);
	}
	
	public java.util.List<FCR_CMPreembossCardInventory> getByCardNo(String cardNo) {
		Criteria criteria = this.getSession().createCriteria(FCR_CMPreembossCardInventory.class);
		criteria.add(Restrictions.eq("cardNo", cardNo));
		criteria.add(Restrictions.eq("flagStatusDB", "A"));
		
		return criteria.list();
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
