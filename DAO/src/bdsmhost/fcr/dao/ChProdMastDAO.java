package bdsmhost.fcr.dao;


import org.apache.log4j.Logger;
import org.hibernate.Session;

import bdsm.fcr.model.ChProdMast;
import bdsm.fcr.model.ChProdMastPK;
import bdsm.model.BaseModel;


/**
 * 
 * @author v00019372
 */
public class ChProdMastDAO extends bdsmhost.dao.BaseDao {
	private static final Logger LOGGER = Logger.getLogger(ChAcctMastDAO.class);
	private static final Integer COD_ENTITY_VPD = 11;
	

	public ChProdMastDAO(Session session) {
		super(session);
	}
	
	
	public ChProdMast get(ChProdMastPK id) {
		return (ChProdMast) this.getSession().get(ChProdMast.class, id); 
	}
	
	public ChProdMast getActiveProduct(Integer codProd) {
		return this.get(new ChProdMastPK(codProd, "A", COD_ENTITY_VPD));
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
