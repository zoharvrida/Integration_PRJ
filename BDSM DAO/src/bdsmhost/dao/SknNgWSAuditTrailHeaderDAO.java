package bdsmhost.dao;


import java.util.List;

import org.hibernate.Query;

import bdsm.model.SknNgWSAuditTrailHeader;


/**
 * @author v00019372
 */
public class SknNgWSAuditTrailHeaderDAO extends BaseDao {

	public SknNgWSAuditTrailHeaderDAO(org.hibernate.Session session) {
		super(session);
	}
	
	
	public SknNgWSAuditTrailHeader get(java.io.Serializable pk) {
		return ((SknNgWSAuditTrailHeader) this.getSession().get(SknNgWSAuditTrailHeader.class, pk));
	}
	
	@SuppressWarnings("rawtypes")
	public List listCreditByBatchNoAndStatus(String batchNo, String status) {
		Query query = this.getSession().getNamedQuery("SknNgWSAuditTrailHeader#listCreditByBatchNoAndStatus");
		query.setString("batchNo", batchNo);
		query.setString("status", status);
		
		return query.list();
	}
	
	@SuppressWarnings("rawtypes")
	public List listDebitByBatchNoAndStatus(String batchNo, String status) {
		Query query = this.getSession().getNamedQuery("SknNgWSAuditTrailHeader#listDebitByBatchNoAndStatus");
		query.setString("batchNo", batchNo);
		query.setString("status", status);
		
		return query.list();
	}
	
	@SuppressWarnings("rawtypes")
	public List listReturByBatchNoAndStatus(String batchNo, String status) {
		Query query = this.getSession().getNamedQuery("SknNgWSAuditTrailHeader#listReturByBatchNoAndStatus");
		query.setString("batchNo", batchNo);
		query.setString("status", status);
		
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
