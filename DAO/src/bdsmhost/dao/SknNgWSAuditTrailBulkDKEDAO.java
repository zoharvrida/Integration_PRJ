package bdsmhost.dao;


import java.sql.Timestamp;

import org.hibernate.Query;
import org.hibernate.Session;

import bdsm.model.BaseModel;
import bdsm.model.SknNgWSAuditTrailBulkDKE;


/**
 * @author v00019372
 */
public class SknNgWSAuditTrailBulkDKEDAO extends BaseDao {

	public SknNgWSAuditTrailBulkDKEDAO(Session session) {
		super(session);
	}


	public SknNgWSAuditTrailBulkDKE get(java.io.Serializable id) {
		return (SknNgWSAuditTrailBulkDKE) this.getSession().get(SknNgWSAuditTrailBulkDKE.class, id);
	}
	
	public SknNgWSAuditTrailBulkDKE getByBatchNoAndNomorReferensi(String batchNo, String nomorReferensi, Integer parentRecordId) {
		return (SknNgWSAuditTrailBulkDKE) 
			this.getSession().getNamedQuery("SknNgWSAuditTrailBulkDKE#byBatchNoAndNomorReferensi")
				.setString("batchNo", batchNo)
				.setString("nomorReferensi", nomorReferensi)
				.setInteger("parentRecordId", parentRecordId)
				.uniqueResult()
		;
	}
	
	public void updateStatusAndRejectCode(String batchNo, String status, String rejectCode, Timestamp dtmUpdated) {
		Query query = this.getSession().getNamedQuery("SknNgWSAuditTrailBulkDKE#updateStatusAndRejectCode");
		query.setString("batchNo", batchNo);
		query.setString("status", status);
		query.setString("rejectCode", rejectCode);
		query.setTimestamp("dtmUpdated", dtmUpdated);
		
		query.executeUpdate();
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

}
