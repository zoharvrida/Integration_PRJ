package bdsm.scheduler.dao;


import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import bdsm.model.BaseModel;
import bdsm.scheduler.model.TmpIncDrNoteDetail;
import bdsm.scheduler.model.TmpIncDrNotePK;


/**
*
* @author v00019372
*/
@SuppressWarnings("unchecked")
public class TmpIncDrNoteDetailDAO extends bdsmhost.dao.BaseDao {
	
	public TmpIncDrNoteDetailDAO(Session session) {
		super(session);
	}
	
	
	public TmpIncDrNoteDetail get(TmpIncDrNotePK pk) {
		return (TmpIncDrNoteDetail) this.getSession().get(TmpIncDrNotePK.class, pk);
	}
	
	public TmpIncDrNoteDetail get(String fileId, Long recordId) {
		TmpIncDrNotePK pk = new TmpIncDrNotePK(fileId, recordId);
		return (TmpIncDrNoteDetail) this.getSession().get(TmpIncDrNotePK.class, pk);
	}
	
	public List<TmpIncDrNoteDetail> getDetailsByFileId(String fileId) {
		Criteria crt = this.getSession().createCriteria(TmpIncDrNoteDetail.class);
		crt.add(Restrictions.eq("compositeId.fileId", fileId));
		
		return crt.list();
	}
	

	@Override
	protected boolean doInsert(BaseModel model) {
		this.getSession().save((TmpIncDrNoteDetail) model);
		return true;
	}

	@Override
	protected boolean doUpdate(BaseModel model) {
		this.getSession().update((TmpIncDrNoteDetail) model);
		return true;
	}

	@Override
	protected boolean doDelete(BaseModel model) {
		this.getSession().delete((TmpIncDrNoteDetail) model);
		return true;
	}

}
