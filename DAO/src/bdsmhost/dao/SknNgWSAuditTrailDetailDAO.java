package bdsmhost.dao;


import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Query;

import bdsm.model.SknNgWSAuditTrailDetail;


/**
 * @author v00019372
 */
public class SknNgWSAuditTrailDetailDAO extends BaseDao {

	public SknNgWSAuditTrailDetailDAO(org.hibernate.Session session) {
		super(session);
	}
	
	
	public SknNgWSAuditTrailDetail get(java.io.Serializable pk) {
		return ((SknNgWSAuditTrailDetail) this.getSession().get(SknNgWSAuditTrailDetail.class, pk));
	}
	
	@SuppressWarnings("rawtypes")
	public List listCreditByBatchNoAndStatus(String batchNo, Integer recordId, String status) {
		Query query = this.getSession().getNamedQuery("SknNgWSAuditTrailDetail#listCreditByBatchNoAndStatus");
		query.setString("batchNo", batchNo);
		query.setInteger("recordId", recordId);
		query.setString("status", status);
		
		return query.list();
	}
	
	@SuppressWarnings("rawtypes")
	public List listDebitByBatchNoAndStatus(String batchNo, Integer recordId, String status) {
		Query query = this.getSession().getNamedQuery("SknNgWSAuditTrailDetail#listDebitByBatchNoAndStatus");
		query.setString("batchNo", batchNo);
		query.setInteger("recordId", recordId);
		query.setString("status", status);
		
		return query.list();
	}
	
	@SuppressWarnings("rawtypes")
	public List listReturByBatchNoAndStatus(String batchNo, Integer recordId, String status) {
		Query query = this.getSession().getNamedQuery("SknNgWSAuditTrailDetail#listReturByBatchNoAndStatus");
		query.setString("batchNo", batchNo);
		query.setInteger("recordId", recordId);
		query.setString("status", status);
		
		return query.list();
	}
	
	public void updateStatusAndRejectCode(String batchNo, String status, String rejectCode, java.util.List<Integer> listRecordId) {
		this.updateStatusAndRejectCode(batchNo, status, rejectCode, listRecordId, null);
	}
	
	public void updateStatusAndRejectCode(String batchNo, String status, String rejectCode, java.util.List<Integer> listRecordId, Timestamp dtmUpdated) {
		Query query = this.getSession().getNamedQuery("SknNgWSAuditTrailDetail#updateStatusAndRejectCode");
		query.setString("batchNo", batchNo);
		query.setString("status", status);
		query.setString("rejectCode", rejectCode);
		query.setTimestamp("dtmUpdated", dtmUpdated);
		query.setParameterList("listRecordId", listRecordId);
		
		query.executeUpdate();
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<? extends Object> countByStatus(List<? extends Object> headerKeys, String status) {
		Query query = this.getSession().getNamedQuery("SknNgWSAuditTrailDetail#countByStatus");
		query.setString("status", status);
		query.setParameterList("headerList", headerKeys);
		
		return query.list();
	}
	
	
	@Override
	protected boolean doInsert(bdsm.model.BaseModel model) {
		model.setDtmUpdated(null);
		this.getSession().save(model);
		
		return true;
	}

	@Override
	protected boolean doUpdate(bdsm.model.BaseModel model) {
		throw new UnsupportedOperationException();
	}

	@Override
	protected boolean doDelete(bdsm.model.BaseModel model) {
		throw new UnsupportedOperationException();
	}

}
