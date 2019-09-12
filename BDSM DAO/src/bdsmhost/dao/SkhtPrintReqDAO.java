package bdsmhost.dao;


import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import bdsm.model.BaseModel;
import bdsm.model.SkhtPrintReq;


/**
 * @author v00019372
 */
public class SkhtPrintReqDAO extends BaseAdapterDAO {
	
	public SkhtPrintReqDAO(Session session) {
		super(session);
	}
	
	
	@SuppressWarnings("unchecked")
	public SkhtPrintReq getByAcctNo(String accountNo) {
		Criteria crt = this.getSession().createCriteria(SkhtPrintReq.class)
			.add(Restrictions.eq("acctNo", accountNo))
			.addOrder(Order.desc("dtmCreated"));
		
		List<SkhtPrintReq> list = crt.list();
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