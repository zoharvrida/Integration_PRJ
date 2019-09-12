package bdsm.scheduler.dao;


import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import bdsm.scheduler.model.TmpTxnUploadDetail;
import bdsm.scheduler.model.TmpTxnUploadPK;


public class TmpTxnUploadDetailDAO extends bdsmhost.dao.BaseDao {
	
	public TmpTxnUploadDetailDAO(Session session) {
		super(session);
	}
	
	
	public TmpTxnUploadDetail get(TmpTxnUploadPK id) {
		return (TmpTxnUploadDetail) this.getSession().get(TmpTxnUploadDetail.class, id);
	}
	
	public TmpTxnUploadDetail get(String fileId, Integer recordId) {
		return this.get(new TmpTxnUploadPK(fileId, recordId));
	}
	
	@SuppressWarnings("unchecked")
	public List<TmpTxnUploadDetail> listByFileId(String fileId) {
		Criteria crt = this.getSession().createCriteria(TmpTxnUploadDetail.class);
		crt.add(Restrictions.eq("compositeId.fileId", fileId));
		
		return crt.list();
	}
	

	@Override
	protected boolean doInsert(bdsm.model.BaseModel model) {
		this.getSession().save((TmpTxnUploadDetail) model);
		return true;
	}

	@Override
	protected boolean doUpdate(bdsm.model.BaseModel model) {
		throw new UnsupportedOperationException("Not Yet Implemented!!!");
	}

	@Override
	protected boolean doDelete(bdsm.model.BaseModel model) {
		throw new UnsupportedOperationException("Not Yet Implemented!!!");
	}
	
}
