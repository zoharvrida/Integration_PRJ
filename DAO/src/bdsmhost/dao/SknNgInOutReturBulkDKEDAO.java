package bdsmhost.dao;


import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import bdsm.model.SknNgInOutReturBulkDKE;


/**
 * @author v00019372
 */
public class SknNgInOutReturBulkDKEDAO extends BaseDao {

	public SknNgInOutReturBulkDKEDAO(Session session) {
		super(session);
	}


	@SuppressWarnings("unchecked")
	public List<SknNgInOutReturBulkDKE> listByBatchNoAndParentRecordId(String batchNo, Integer parentRecordId) {
		Query query = this.getSession().getNamedQuery("SknNgInOutReturBulkDKE#listByBatchNoAndParentRecordId");
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
