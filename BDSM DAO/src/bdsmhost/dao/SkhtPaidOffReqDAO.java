package bdsmhost.dao;


import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import bdsm.model.BaseModel;
import bdsm.model.SkhtPaidOffReq;


/**
 * @author v00019372
 */
public class SkhtPaidOffReqDAO extends BaseAdapterDAO {

	public SkhtPaidOffReqDAO(Session session) {
		super(session);
	}
	
	
	public SkhtPaidOffReq get(String refNo) {
		return (SkhtPaidOffReq) this.getSession().get(SkhtPaidOffReq.class, refNo);
	}
	
	@SuppressWarnings("unchecked")
	public SkhtPaidOffReq getByAcctNoAndPortionNo(String accountNo, Long portionNo) {
		Criteria crt = this.getSession().createCriteria(SkhtPaidOffReq.class);
		crt.add(Restrictions.eq("acctNo", accountNo))
			.add(Restrictions.eq("portionNo", portionNo))
			.addOrder(Order.desc("dtmCreated"))
			.setMaxResults(1);
		
		List<SkhtPaidOffReq> list = crt.list();
		if (list.size() > 0)
			return list.get(0);
		else
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
