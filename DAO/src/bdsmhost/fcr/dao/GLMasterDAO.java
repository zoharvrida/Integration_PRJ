package bdsmhost.fcr.dao;


import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import bdsm.fcr.model.GLMaster;
import bdsmhost.dao.BaseDao;


/**
 * @author v00019372
 */
public class GLMasterDAO extends BaseDao {

	public GLMasterDAO(Session session) {
		super(session);
	}


	@SuppressWarnings("unchecked")
	public GLMaster getByBranchAccountAndCurrency(Integer branchCode, Integer accountNo, Integer currencyCode) {
		Query query = this.getSession().getNamedQuery("GLMaster#getByBranchAccountAndCurrency");
		query.setInteger("codCCBranch", branchCode);
		query.setInteger("codGLAcct", accountNo);
		query.setInteger("codGLAcctCcy", currencyCode);
		
		List<GLMaster> list = query.list();
		if (list.size() > 0)
			return list.get(0);
		else
			return null;
	}

	@Override
	protected boolean doInsert(bdsm.model.BaseModel model) {
		throw new UnsupportedOperationException("Insert Operation");
	}

	@Override
	protected boolean doUpdate(bdsm.model.BaseModel model) {
		throw new UnsupportedOperationException("Insert Operation");
	}

	@Override
	protected boolean doDelete(bdsm.model.BaseModel model) {
		throw new UnsupportedOperationException("Insert Operation");
	}

}
