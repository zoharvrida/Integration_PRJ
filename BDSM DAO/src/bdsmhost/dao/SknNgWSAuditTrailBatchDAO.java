package bdsmhost.dao;


import java.util.List;

import org.hibernate.Query;

import bdsm.model.SknNgWSAuditTrailBatch;


/**
 * @author v00019372
 */
public class SknNgWSAuditTrailBatchDAO extends BaseDao {

	public SknNgWSAuditTrailBatchDAO(org.hibernate.Session session) {
		super(session);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<SknNgWSAuditTrailBatch> listByBatchNoTypeAndStatus(String batchNo, Integer type, String status, java.util.Date date) {
		Query query = this.getSession().getNamedQuery("SknNgWSAuditTrailBatch#listByBatchNo,TypeAndStatus");
		query.setString("batchNo", (batchNo == null)? "%": batchNo);
		query.setByte("type", Byte.valueOf((byte) type.intValue()));
		query.setString("status", status);
		query.setDate("date", date);
		
		return query.list();
	}
	

	@Override
	protected boolean doInsert(bdsm.model.BaseModel model) {
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
