package bdsmhost.dao;


import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import bdsm.model.BaseModel;
import bdsm.model.SknNgReturInDebitFooter;


@SuppressWarnings("unchecked")
public class SknNgReturInDebitFooterDAO extends BaseDao {

	public SknNgReturInDebitFooterDAO(Session session) {
		super(session);
	}
	
	
	public SknNgReturInDebitFooter get(java.io.Serializable id) {
		return (SknNgReturInDebitFooter) this.getSession().get(SknNgReturInDebitFooter.class, id);
	}
	
	public List<SknNgReturInDebitFooter> list(String batchNo) {
		Criteria crt = this.getSession().createCriteria(SknNgReturInDebitFooter.class);
		crt.add(Restrictions.eq("compositeId.batchNo", batchNo));
		crt.addOrder(Order.asc("parentRecordId"));
		crt.addOrder(Order.asc("compositeId.recordId"));
		
		return crt.list();
	}
	
	public SknNgReturInDebitFooter getByParentRecordId(String batchNo, Integer parentRecordId) {
		Criteria crt = this.getSession().createCriteria(SknNgReturInDebitFooter.class);
		crt.add(Restrictions.eq("compositeId.batchNo", batchNo));
		crt.add(Restrictions.eq("parentRecordId", parentRecordId));
		crt.addOrder(Order.asc("parentRecordId"));
		crt.addOrder(Order.asc("compositeId.recordId"));
		
		return ((SknNgReturInDebitFooter) crt.uniqueResult());
	}
	

	@Override
	protected boolean doInsert(BaseModel model) {
		this.getSession().save((SknNgReturInDebitFooter) model);
		return false;
	}

	@Override
	protected boolean doUpdate(BaseModel model) {
		this.getSession().update((SknNgReturInDebitFooter) model);
		return false;
	}

	@Override
	protected boolean doDelete(BaseModel model) {
		this.getSession().delete((SknNgReturInDebitFooter) model);
		return false;
	}
	

}
