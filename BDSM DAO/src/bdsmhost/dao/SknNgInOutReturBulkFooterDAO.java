package bdsmhost.dao;


import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import bdsm.model.SknNgInOutReturBulkFooter;


/**
 * @author v00019372
 */
public class SknNgInOutReturBulkFooterDAO extends BaseDao {

	public SknNgInOutReturBulkFooterDAO(Session session) {
		super(session);
	}


	@SuppressWarnings("unchecked")
	public List<SknNgInOutReturBulkFooter> listByBatchNoAndParentRecordId(String batchNo, Integer parentRecordId) {
		Query query = this.getSession().getNamedQuery("SknNgInOutReturBulkFooter#listByBatchNoAndParentRecordId");
		query.setString("batchNo", batchNo);
		query.setInteger("parentRecordId", parentRecordId);
		
		return query.list();
	}
	
	@Override
	protected boolean doInsert(bdsm.model.BaseModel model) {
		model.setIdCreatedSpv(null);
		model.setDtmCreatedSpv(null);
		model.setIdUpdatedBy(null);
		model.setDtmUpdated(null);
		model.setIdUpdatedSpv(null);
		model.setDtmUpdatedSpv(null);

		this.getSession().save(model);
		return true;
	}

	@Override
	protected boolean doUpdate(bdsm.model.BaseModel model) {
		throw new UnsupportedOperationException("Not yet implemented!!!");
	}

	@Override
	protected boolean doDelete(bdsm.model.BaseModel model) {
		throw new UnsupportedOperationException("Not yet implemented!!!");
	}

}
