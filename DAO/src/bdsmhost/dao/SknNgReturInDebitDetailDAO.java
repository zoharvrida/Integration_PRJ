package bdsmhost.dao;


import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import bdsm.model.BaseModel;
import bdsm.model.SknNgReturInDebitDetail;


@SuppressWarnings("unchecked")
public class SknNgReturInDebitDetailDAO extends BaseDao {

	public SknNgReturInDebitDetailDAO(Session session) {
		super(session);
	}
	
	
	public List<SknNgReturInDebitDetail> list(String batchNo) {
		Criteria crt = this.getSession().createCriteria(SknNgReturInDebitDetail.class);
		crt.add(Restrictions.eq("compositeId.batchNo", batchNo));
		crt.addOrder(Order.asc("parentRecordId"));
		crt.addOrder(Order.asc("compositeId.recordId"));
		
		return crt.list();
	}
	
	public List<SknNgReturInDebitDetail> listByParentRecordId(String batchNo, Integer parentRecordId) {
		Criteria crt = this.getSession().createCriteria(SknNgReturInDebitDetail.class);
		crt.add(Restrictions.eq("compositeId.batchNo", batchNo));
		crt.add(Restrictions.eq("parentRecordId", parentRecordId));
		crt.addOrder(Order.asc("parentRecordId"));
		crt.addOrder(Order.asc("compositeId.recordId"));
		
		return crt.list();
	}
	

	@Override
	protected boolean doInsert(BaseModel model) {
		this.getSession().save((SknNgReturInDebitDetail) model);
		return true;
	}

	@Override
	protected boolean doUpdate(BaseModel model) {
		this.getSession().update((SknNgReturInDebitDetail) model);
		return true;
	}

	@Override
	protected boolean doDelete(BaseModel model) {
		this.getSession().delete((SknNgReturInDebitDetail) model);
		return true;
	}

}
