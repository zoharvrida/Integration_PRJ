/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.dao;

import bdsm.model.BaseModel;
import bdsm.model.FcrBaCcBrnMast;
import bdsm.model.FcrBaCcyCode;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author user
 */
public class FcrBaCcBrnMastDao extends BaseDao {

	public FcrBaCcBrnMastDao(Session session) {
		super(session);
	}

	protected Logger getLogger() {
		return Logger.getLogger(getClass().getName());
	}

	public FcrBaCcBrnMast get(int branchCode) {
		this.getLogger().info("DAO Branch " + branchCode);
		try {
			Criteria criteriaQuery = getSession().createCriteria(FcrBaCcBrnMast.class);
			criteriaQuery.add(Restrictions.eq("compositeId.codCcBrn", branchCode));
			criteriaQuery.add(Restrictions.eq("compositeId.flgMntStatus", "A"));
			criteriaQuery.add(Restrictions.eq("compositeId.codEntityVpd", 11));
			return (FcrBaCcBrnMast) criteriaQuery.uniqueResult();

		} catch (Exception e) {
			this.getLogger().info("Error " + e, e);
			return null;
		}
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
