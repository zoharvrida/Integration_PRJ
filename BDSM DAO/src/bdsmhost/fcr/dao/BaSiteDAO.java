package bdsmhost.fcr.dao;


import org.hibernate.Session;

import bdsm.fcr.model.BaSite;
import bdsm.model.BaseModel;


/**
 * @author v00019372
 */
public class BaSiteDAO extends bdsmhost.dao.BaseDao {

	public BaSiteDAO(Session session) {
		super(session);
	}
	
	
	public BaSite getByMacroName(String macroName) {
		return (BaSite) this.getSession().getNamedQuery("BaSite#getByMacroName").setString("macroName", macroName).uniqueResult();
	}

	@Override
	protected boolean doInsert(BaseModel model) {
		throw new UnsupportedOperationException("Not yet implemented!!!");
	}

	@Override
	protected boolean doUpdate(BaseModel model) {
		this.getSession().update(model);
		return true;
	}

	@Override
	protected boolean doDelete(BaseModel model) {
		throw new UnsupportedOperationException("Not yet implemented!!!");
	}

}
