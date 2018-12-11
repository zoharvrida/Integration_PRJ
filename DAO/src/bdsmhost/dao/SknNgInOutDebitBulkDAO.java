package bdsmhost.dao;


import org.hibernate.Query;
import org.hibernate.Session;

import bdsm.model.BaseModel;
import bdsm.model.SknNgInOutDebitBulkHeader;


/**
 * @author v00019372
 */
public class SknNgInOutDebitBulkDAO extends BaseDao {

	public SknNgInOutDebitBulkDAO(Session session) {
		super(session);
	}


	@SuppressWarnings("unchecked")
	public java.util.List<SknNgInOutDebitBulkHeader> listHeaderByBatchNo(String batchNo) {
		Query query = this.getSession().getNamedQuery("SknNgInOutDebitBulkHeader#listByBatchNo");
		query.setString("batchNo", batchNo);
		
		return query.list();
	}


	public boolean isExistBatchDKE(String batchDate, String SOR) {
		Query query = this.getSession().getNamedQuery("SknNgInOutDebitBulkDKE#getByBatchDateAndSOR");
		query.setString("batchDate", batchDate);
		query.setString("SOR", SOR);
		Object obj = query.uniqueResult();
		
		if (obj != null) {
			this.evictObjectFromPersistenceContext(obj);
			return true;
		}
		
		return false;
	}


	@Override
	protected boolean doInsert(BaseModel model) {
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
	protected boolean doUpdate(BaseModel model) {
		throw new UnsupportedOperationException("Not yet implemented!!!");
	}

	@Override
	protected boolean doDelete(BaseModel model) {
		throw new UnsupportedOperationException("Not yet implemented!!!");
	}

}
