package bdsmhost.dao;


import org.hibernate.Query;

import bdsm.model.BaseModel;
import bdsm.model.SknNgInOutCreditFooter;


/**
 * @author v00019372
 */
public class SknNgInOutCreditFooterDAO extends BaseDao {
	
	public SknNgInOutCreditFooterDAO(org.hibernate.Session session) {
		super(session);
	}
	
	
	public SknNgInOutCreditFooter get(java.io.Serializable id) {
		return (SknNgInOutCreditFooter) this.getSession().get(SknNgInOutCreditFooter.class, id);
	}
	
	public SknNgInOutCreditFooter getByBatchNoAndParentRecordId(String batchNo, Integer parentRecordId) {
		Query query = this.getSession().getNamedQuery("SknNgInOutCreditFooter#getByBatchNoAndParentRecordId");
		query.setString("batchNo", batchNo);
		query.setInteger("parentRecordId", parentRecordId);
		
		return (SknNgInOutCreditFooter) query.uniqueResult();
	}
	
	
	@Override
	protected boolean doInsert(BaseModel model) {
		this.getSession().save(model);
		return true;
	}

	@Override
	protected boolean doUpdate(BaseModel model) {
		this.getSession().update(model);
		return true;
	}

	@Override
	protected boolean doDelete(BaseModel model) {
		this.getSession().delete(model);
		return true;
	}
	
}
