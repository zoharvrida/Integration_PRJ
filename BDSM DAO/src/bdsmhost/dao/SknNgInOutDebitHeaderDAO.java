/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import bdsm.model.BaseModel;
import bdsm.model.SknNgInOutDebitHeader;
import bdsm.util.SchedulerUtil;
import bdsm.util.oracle.DateUtility;

/**
 *
 * @author v00019372
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class SknNgInOutDebitHeaderDAO extends bdsmhost.dao.BaseDao {

	public SknNgInOutDebitHeaderDAO(Session session) {
		super(session);
	}

	public SknNgInOutDebitHeader get(java.io.Serializable id) {
		return (SknNgInOutDebitHeader) this.getSession().get(SknNgInOutDebitHeader.class, id);
	}

	public List<SknNgInOutDebitHeader> listSMOIndividual(String batchNo, Integer RecordId) {
		Query q = this.getSession().createQuery("FROM SknNgInOutDebitHeader WHERE compositeId.batchNo = :pBatchNo "
				+ "AND compositeId.recordId = :pRecordId ORDER BY compositeId.recordId");
		q.setString("pBatchNo", batchNo);
		q.setInteger("pRecordId", RecordId);
		return q.list();
	}

	public List<SknNgInOutDebitHeader> list(String batchNo) {
		Query q = this.getSession().createQuery("FROM SknNgInOutDebitHeader WHERE compositeId.batchNo = :pBatchNo ORDER BY compositeId.recordId");
		q.setString("pBatchNo", batchNo);

		return q.list();
	}

	public List<SknNgInOutDebitHeader> getListbySMO(String batch_ID) {
		Query q = this.getSession().createQuery("FROM SknNgInOutDebitHeader WHERE compositeId.batchNo = :pBatchNo "
				+ "AND typeDB = :pType "
				+ "ORDER BY compositeId.recordId ");
		q.setString("pBatchNo", batch_ID);
		q.setCharacter("pType", 'O');
		return q.list();
	}

	public List<SknNgInOutDebitHeader> getListbySMII(String batch_ID, String dateClearing) {
		Query q = this.getSession().createQuery("FROM SknNgInOutDebitHeader WHERE compositeId.batchNo = :pBatchNo "
				+ "AND typeDB = :pType AND approved = :pApproval AND tanggalBatch > :pBatchDate "
				+ "AND hPlus1 = :pHplus "
				+ "ORDER BY compositeId.recordId ");
		q.setString("pBatchNo", batch_ID);
		q.setCharacter("pType", 'I');
		q.setString("pBatchDate", dateClearing);
		q.setBoolean("pApproval", true);
		q.setBoolean("pHplus", true);
		return q.list();
	}

	public List<String> listBatchNoByBranchNo(String branchNo, Date date, int mode) {
		Query q = this.getSession().getNamedQuery("SknNgInOutDebitHeader#listBatchNoByBranchNo");
		StringBuilder batchNo = new StringBuilder(branchNo);

		if (date != null) {
			batchNo.append('-').append(DateUtility.DATE_FORMAT_YYYYMMDD.format(date));
		}
		q.setString("pBatchNo", batchNo.toString());
		q.setString("pApproved", (mode == 1) ? "%" : " "); // 1=Inquiry; 2=Approval

		return q.list();
	}

	public List listByBatchNoGrid(String batchNo, int mode) {
		Query q = this.getSession().getNamedQuery("SknNgInOutDebitHeader#listByBatchNoGrid");
		q.setString("pBatchNo", batchNo);
		q.setString("pApproved", (mode == 1) ? "%" : " "); // 1=Inquiry; 2=Approval
		q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

		return q.list();
	}

	public List listApprovedBatchNoByHPlus(Integer hPlus, String status, String batchDate) {
		Criteria crt = this.getSession().createCriteria(SknNgInOutDebitHeader.class);
		crt.setProjection(Projections.distinct(Projections.property("compositeId.batchNo")));
		crt.add(Restrictions.eq("hPlus1", Boolean.valueOf(hPlus.equals(1))));
		crt.add(Restrictions.eq("approved", Boolean.TRUE));

		if (batchDate != null) {
			crt.add(Restrictions.eq("tanggalBatch", batchDate));
		}

		if (status != null) {
			if (status.equalsIgnoreCase("NULL")) {
				crt.add(Restrictions.isNull("statusDB"));
			} else {
				crt.add(Restrictions.eq("statusDB", Character.valueOf(status.toUpperCase().charAt(0))));
			}
		}

		return crt.list();
	}

	public boolean isApprovalCompleted(String batchNo) {
		Integer count = this.countByBatchNoAndApprovedStatus(batchNo, "NULL");

		return (count == 0);
	}

	public Integer countByBatchNoAndApprovedStatus(String batchNo, String approvedStatus) {
		Criteria crt = this.getSession().createCriteria(SknNgInOutDebitHeader.class);
		crt.setProjection(Projections.rowCount());
		crt.add(Restrictions.eq("compositeId.batchNo", batchNo));

		if (approvedStatus != null) {
			if (approvedStatus.equalsIgnoreCase("NULL")) {
				crt.add(Restrictions.isNull("approved"));
			} else {
				crt.add(Restrictions.eq("approved", Boolean.valueOf(approvedStatus)));
			}
		}

		return (Integer) crt.uniqueResult();
	}

	public void updateApprovedByBatchNoAndRecordIdCollection(String batchNo, boolean isApproved, List<Integer> recordIdList, String userId) {
		Query q = this.getSession().getNamedQuery("SknNgInOutDebitHeader#updateApprovedByBatchNoAndRecordIdCollection");
		q.setString("pBatchNo", batchNo);
		q.setBoolean("pApproved", isApproved);
		q.setParameterList("pcolRecordId", recordIdList);
		q.setString("userId", userId);
		q.setTimestamp("currentTime", SchedulerUtil.getTime());

		q.executeUpdate();
	}

    public void updateApprovedByBatchNo(String batchNo, boolean isApproved, String userId) {
        Query q = this.getSession().getNamedQuery("SknNgInOutDebitHeader#updateApprovedByBatchNoAllCollection");
        q.setString("pBatchNo", batchNo);
        q.setBoolean("pApproved", isApproved);
        q.setString("userId", userId);
        q.setTimestamp("currentTime", SchedulerUtil.getTime());

        q.executeUpdate();
    }

	public void updateStatusByBatchNoCollectionAndHPlus(List<String> batchNoList, Integer hPlus, Character status) {
		Query q = this.getSession().getNamedQuery("SknNgInOutDebitHeader#updateStatusByBatchNoCollectionAndHPlus1");
		q.setParameterList("pcolBatchNo", batchNoList);
		q.setCharacter("status", status);
		q.setBoolean("hPlus1", hPlus.equals(1));

		q.executeUpdate();
	}
	
    public List<SknNgInOutDebitHeader> SingleApprove(String BatchNo) {
        Criteria crt = getSession().createCriteria(SknNgInOutDebitHeader.class);
        crt.add(Restrictions.eq("compositeId.batchNo", BatchNo));
        crt.add(Restrictions.isNull("approved"));
        List single = crt.list();
        return single;
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
