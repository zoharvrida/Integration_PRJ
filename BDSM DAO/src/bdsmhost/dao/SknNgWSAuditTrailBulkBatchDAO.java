package bdsmhost.dao;


import org.hibernate.Session;

import bdsm.model.BaseModel;
import bdsm.model.SknNgWSAuditTrailBulkBatch;


/**
 * @author v00019372
 */
public class SknNgWSAuditTrailBulkBatchDAO extends BaseDao {

	public SknNgWSAuditTrailBulkBatchDAO(Session session) {
		super(session);
	}


	public SknNgWSAuditTrailBulkBatch get(java.io.Serializable id) {
		return (SknNgWSAuditTrailBulkBatch) this.getSession().get(SknNgWSAuditTrailBulkBatch.class, id);
	}

	@Override
	protected boolean doInsert(BaseModel model) {
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
