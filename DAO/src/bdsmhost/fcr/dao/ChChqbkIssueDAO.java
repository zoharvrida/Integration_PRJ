/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.fcr.dao;

import bdsm.fcr.model.ChChqbkIssue;
import bdsm.fcr.model.ChChqbkIssuePK;
import bdsm.model.BaseModel;
import bdsmhost.dao.BaseDao;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author NCBS
 */
public class ChChqbkIssueDAO extends BaseDao{
	private static final int COD_ENTITY_VPD = 11;
	private int codEntityVPD;
	
	public ChChqbkIssueDAO(Session session) {
		this(session, COD_ENTITY_VPD);
	}
	public ChChqbkIssueDAO(Session session, int codEntityVPD) {
		super(session);
		this.codEntityVPD = codEntityVPD;
	}
	
	public ChChqbkIssue get(ChChqbkIssuePK pk) {
		return (ChChqbkIssue) this.getSession().get(ChChqbkIssue.class, pk);
	}
	
	public List getDetails(ChChqbkIssuePK pk) {
        Criteria criteriaQuery = getSession().createCriteria(ChChqbkIssue.class);
		criteriaQuery.add(Restrictions.eq("compositeId.codAcct", pk.getCodAcct()));
        criteriaQuery.add(Restrictions.le("compositeId.chqkStart", pk.getChqkStart()));
        criteriaQuery.add(Restrictions.ge("compositeId.chqkEnd", pk.getChqkEnd()));
        criteriaQuery.add(Restrictions.eq("compositeId.flgMntStatus", pk.getFlgMntStatus()));
		criteriaQuery.add(Restrictions.eq("compositeId.codEntityVPD", pk.getCodEntityVPD()));
		return criteriaQuery.list();
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
