package bdsmhost.dao;


import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import bdsm.model.BaseModel;
import bdsm.model.SknNgReturInDebitHeader;


@SuppressWarnings("unchecked")
public class SknNgReturInDebitHeaderDAO extends BaseDao {

	public SknNgReturInDebitHeaderDAO(Session session) {
		super(session);
	}
	
	
	public SknNgReturInDebitHeader get(java.io.Serializable id) {
		return (SknNgReturInDebitHeader) this.getSession().get(SknNgReturInDebitHeader.class, id);
	}
	
	public List<SknNgReturInDebitHeader> list(String batchNo) {
		Criteria crt = this.getSession().createCriteria(SknNgReturInDebitHeader.class);
		crt.add(Restrictions.eq("compositeId.batchNo", batchNo));
		crt.addOrder(Order.asc("compositeId.recordId"));
		
		return crt.list();
	}
	

	@Override
	protected boolean doInsert(BaseModel model) {
		this.getSession().save((SknNgReturInDebitHeader) model);
		return true;
	}

	@Override
	protected boolean doUpdate(BaseModel model) {
		this.getSession().update((SknNgReturInDebitHeader) model);
		return false;
	}

	@Override
	protected boolean doDelete(BaseModel model) {
		this.getSession().delete((SknNgReturInDebitHeader) model);
		return false;
	}

}
