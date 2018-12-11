package bdsmhost.dao;

import bdsm.model.BaseModel;
import bdsm.model.TransactionParameter;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

public class TransactionParameterDao extends BaseDao {
	
	public TransactionParameterDao(Session session) {
		super(session);
	}
	
	
	public TransactionParameter getById(Integer id) {
		return (TransactionParameter) this.getSession().get(TransactionParameter.class, id);
	}
	
	public TransactionParameter getByModuleName(String moduleName) {
		Criteria criteria = this.getSession().createCriteria(TransactionParameter.class);
		criteria.add(Restrictions.eq("moduleName", moduleName));
		
		return (TransactionParameter) criteria.uniqueResult();
	}

	@Override
	protected boolean doInsert(BaseModel model) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	protected boolean doUpdate(BaseModel model) {
		this.getSession().update(model);
		return true;
	}

	@Override
	protected boolean doDelete(BaseModel model) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

}
