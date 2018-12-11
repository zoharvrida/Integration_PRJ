package bdsmhost.dao;


import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;

import bdsm.model.SknNgInOutDebitDetail;
import bdsm.model.SknNgPK;


/**
 *
 * @author v00019372
 */
@SuppressWarnings("rawtypes")
public class SknNgInOutDebitDetailDAO extends bdsmhost.dao.BaseDao {

	public SknNgInOutDebitDetailDAO(Session session) {
		super(session);
	}
	
	
	public SknNgInOutDebitDetail get(SknNgPK id) {
		return (SknNgInOutDebitDetail) this.getSession().get(SknNgInOutDebitDetail.class, id);
	}

	public List<SknNgInOutDebitDetail> listByParentRecordNo(String batchNo, int parentRecordNo) {
		Query q = this.getSession().createQuery("FROM SknNgInOutDebitDetail WHERE compositeId.batchNo = :pBatchNo "
				+ "AND parentRecordId = :pParentRecordNo ORDER BY compositeId.recordId");
		q.setString("pBatchNo", batchNo);
		q.setInteger("pParentRecordNo", parentRecordNo);

		return q.list();
	}

	public List<SknNgInOutDebitDetail> listByParentRecordSMI(String batchNo, int parentRecordNo) {
		Query q = this.getSession().createQuery("FROM SknNgInOutDebitDetail WHERE compositeId.batchNo = :pBatchNo "
				+ "AND parentRecordId = :pParentRecordNo AND typeDB = :pType ORDER BY compositeId.recordId");
		q.setString("pBatchNo", batchNo);
		q.setInteger("pParentRecordNo", parentRecordNo);
		q.setCharacter("pType", 'I');

		return q.list();
	}

	public List<SknNgInOutDebitDetail> listByIncSMO(String batchNo) {
		Query q = this.getSession().createQuery("FROM SknNgInOutDebitDetail WHERE compositeId.batchNo = :pBatchNo "
				+ "AND typeDB = :pType ORDER BY compositeId.recordId");
		q.setString("pBatchNo", batchNo);
		q.setCharacter("pType", 'O');
		return q.list();
	}

	public List<SknNgInOutDebitDetail> listByParentSMO(String batchNo,Integer Record) {
		Query q = this.getSession().createQuery("FROM SknNgInOutDebitDetail WHERE compositeId.batchNo = :pBatchNo "
				+ "AND typeDB = :pType AND parentRecordId = :pRecord ORDER BY compositeId.recordId");
		q.setString("pBatchNo", batchNo);
		q.setCharacter("pType", 'O');
		q.setInteger("pRecord", Record);
		return q.list();
	}

	public List<SknNgInOutDebitDetail> listByIncSMI(String batchNo) {
		Query q = this.getSession().createQuery("FROM SknNgInOutDebitDetail WHERE compositeId.batchNo = :pBatchNo "
				+ "AND typeDB = :pType ORDER BY compositeId.recordId");
		q.setString("pBatchNo", batchNo);
		q.setCharacter("pType", 'I');
		return q.list();
	}

	public List<SknNgInOutDebitDetail> listOutgoing(String batchNo) {
		Query q = this.getSession().createQuery("FROM SknNgInOutDebitDetail WHERE compositeId.batchNo = :pBatchNo "
				+ "AND typeDB = :pType ORDER BY compositeId.recordId");
		q.setString("pBatchNo", batchNo);
		q.setCharacter("pType", 'O');
		return q.list();
	}

	public List list(String batchNo) {
		Query q = this.getSession().createQuery("FROM SknNgInOutDebitDetail WHERE compositeId.batchNo = :pBatchNo ORDER BY compositeId.recordId");
		q.setString("pBatchNo", batchNo);
		
		return q.list();
	}

	public java.util.List listGrid(String batchNo, String batchId, int mode) {
		Query q = this.getSession().getNamedQuery("SknNgInOutDebitDetail#listGrid");
		q.setString("pBatchNo", batchNo);
		q.setString("pExtractStatus", (mode == 2) ? " " : "%"); // 1=Inquiry; 2=Approval
		q.setString("pBatchId", batchId);
		q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

		return q.list();
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
		this.getSession().update(model);
		return true;
	}

	@Override
	protected boolean doDelete(bdsm.model.BaseModel model) {
		this.getSession().delete(model);
		return true;
	}

}
