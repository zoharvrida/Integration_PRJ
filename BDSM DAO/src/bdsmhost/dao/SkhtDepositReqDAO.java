package bdsmhost.dao;


import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import bdsm.model.BaseModel;
import bdsm.model.SkhtDepositReq;


/**
 * @author v00019372
 */
public class SkhtDepositReqDAO extends BaseAdapterDAO {

	public SkhtDepositReqDAO(Session session) {
		super(session);
	}
	
	
	public SkhtDepositReq get(String refNo) {
		return (SkhtDepositReq) this.getSession().get(SkhtDepositReq.class, refNo);
	}
	
	@SuppressWarnings("unchecked")
	public SkhtDepositReq getByAcctNo(String acctNo) {
		Criteria crt = this.getSession().createCriteria(SkhtDepositReq.class)
			.add(Restrictions.eq("acctNo", acctNo))
			.addOrder(Order.desc("dtmCreated"))
			.setMaxResults(1);
		
		List<SkhtDepositReq> list = crt.list();
		if (list.size() > 0)
			return list.get(0);
		
		return null;
	}
	
	
	@Override
	protected boolean doInsert(BaseModel model) {
		if (model.getIdCreatedBy() == null) model.setDtmCreated(null);
		if (model.getIdCreatedSpv() == null) model.setDtmCreatedSpv(null);
		model.setIdUpdatedBy(null); model.setDtmUpdated(null);
		model.setIdUpdatedSpv(null); model.setDtmUpdatedSpv(null);
		this.getSession().save(model);
		
		return true;
	}

}
