/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import bdsm.model.BaseModel;
import bdsm.model.SknNgInOutDebitFooter;
import bdsm.model.SknNgPK;

/**
 *
 * @author v00019372
 */
public class SknNgInOutDebitFooterDAO extends bdsmhost.dao.BaseDao {

	public SknNgInOutDebitFooterDAO(Session session) {
		super(session);
	}

	public SknNgInOutDebitFooter get(String batchNo, int parentRecordNo) {
		Query q = this.getSession().createQuery("FROM SknNgInOutDebitFooter WHERE compositeId.batchNo = :pBatchNo "
				+ "AND parentRecordId = :pParentRecordNo");
		q.setString("pBatchNo", batchNo);
		q.setInteger("pParentRecordNo", parentRecordNo);

		return (SknNgInOutDebitFooter) q.uniqueResult();
	}

	public List<SknNgInOutDebitFooter> listOutgoing(String batch) {
		Query q = this.getSession().createQuery("FROM SknNgInOutDebitFooter WHERE compositeId.batchNo = :pBatchNo "
				+ "AND typeDB = :types");
		q.setString("pBatchNo", batch);
		q.setString("types", "O");
		return q.list();
	}

	public SknNgInOutDebitFooter getOutgoing(SknNgPK pk) {
		Query q = this.getSession().createQuery("FROM SknNgInOutDebitFooter WHERE compositeId.batchNo = :pBatchNo "
				+ "AND parentRecordId = :pParentRecordNo AND typeDB = :types");
		q.setString("pBatchNo", pk.getBatchNo());
		q.setInteger("pParentRecordNo", pk.getRecordId());
		q.setString("types", "O");

		return (SknNgInOutDebitFooter) q.uniqueResult();
	}

	public List<SknNgInOutDebitFooter> list(String batchNo) {
		Query q = getSession().createQuery("FROM SknNgInOutDebitFooter WHERE compositeId.batchNo = :pBatchNo ORDER BY compositeId.recordId");
		q.setString("pBatchNo", batchNo);

		return q.list();
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
		this.getSession().update(model);
		return true;
	}

	@Override
	protected boolean doDelete(BaseModel model) {
		this.getSession().delete(model);
		return true;
	}
}
