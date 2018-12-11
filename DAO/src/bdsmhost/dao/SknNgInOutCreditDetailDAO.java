package bdsmhost.dao;


import java.util.List;

import org.hibernate.Query;

import bdsm.model.BaseModel;
import bdsm.model.SknNgInOutCreditDetail;


/**
 * @author v00019372
 */
public class SknNgInOutCreditDetailDAO extends BaseDao {
	
	public SknNgInOutCreditDetailDAO(org.hibernate.Session session) {
		super(session);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<SknNgInOutCreditDetail> listByBatchNoAndParentRecordId(String batchNo, Integer parentRecordId) {
		Query query = this.getSession().getNamedQuery("SknNgInOutCreditDetail#listByBatchNoAndParentRecordId");
		query.setString("batchNo", batchNo);
		query.setInteger("parentRecordId", parentRecordId);
		
		return (List<SknNgInOutCreditDetail>) query.list();
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
