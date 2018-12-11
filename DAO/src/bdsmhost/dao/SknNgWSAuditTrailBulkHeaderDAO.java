package bdsmhost.dao;


import org.hibernate.Session;

import bdsm.model.BaseModel;
import bdsm.model.SknNgWSAuditTrailBulkHeader;


/**
 * @author v00019372
 */
public class SknNgWSAuditTrailBulkHeaderDAO extends BaseDao {

	public SknNgWSAuditTrailBulkHeaderDAO(Session session) {
		super(session);
	}


	public SknNgWSAuditTrailBulkHeader get(java.io.Serializable id) {
		return (SknNgWSAuditTrailBulkHeader) this.getSession().get(SknNgWSAuditTrailBulkHeader.class, id);
	}
	
	public SknNgWSAuditTrailBulkHeader getByBatchNoAndBatchId(String batchNo, String batchId) {
		return (SknNgWSAuditTrailBulkHeader) 
			this.getSession().getNamedQuery("SknNgWSAuditTrailBulkHeader#byBatchNoAndBatchId")
				.setString("batchNo", batchNo)
				.setString("batchId", batchId)
				.uniqueResult()
		;
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
