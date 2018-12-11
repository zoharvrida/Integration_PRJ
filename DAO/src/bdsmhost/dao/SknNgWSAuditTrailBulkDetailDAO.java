package bdsmhost.dao;


import java.sql.Timestamp;

import org.hibernate.Query;
import org.hibernate.Session;

import bdsm.model.BaseModel;
import bdsm.model.SknNgWSAuditTrailBulkDetail;


/**
 * @author v00019372
 */
public class SknNgWSAuditTrailBulkDetailDAO extends BaseDao {

	public SknNgWSAuditTrailBulkDetailDAO(Session session) {
		super(session);
	}


	public SknNgWSAuditTrailBulkDetail get(java.io.Serializable id) {
		return (SknNgWSAuditTrailBulkDetail) this.getSession().get(SknNgWSAuditTrailBulkDetail.class, id);
	}
	
	public SknNgWSAuditTrailBulkDetail getByBatchNoAndNomorUrut(String batchNo, String nomorUrut, Integer parentRecordId) {
		return (SknNgWSAuditTrailBulkDetail) 
			this.getSession().getNamedQuery("SknNgWSAuditTrailBulkDetail#byBatchNoAndNomorUrut")
				.setString("batchNo", batchNo)
				.setString("nomorUrut", nomorUrut)
				.setInteger("parentRecordId", parentRecordId)
				.uniqueResult()
		;
	}
	

	@Override
	protected boolean doInsert(BaseModel model) {
		model.setDtmUpdated(null);
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
	
	
	public void updateStatusAndRejectCode(String batchNo, String status, String rejectCode, Timestamp dtmUpdated) {
		Query query = this.getSession().getNamedQuery("SknNgWSAuditTrailBulkDetail#updateStatusAndRejectCode");
		query.setString("batchNo", batchNo);
		query.setString("status", status);
		query.setString("rejectCode", rejectCode);
		query.setTimestamp("dtmUpdated", dtmUpdated);
		
		query.executeUpdate();
	}

}
