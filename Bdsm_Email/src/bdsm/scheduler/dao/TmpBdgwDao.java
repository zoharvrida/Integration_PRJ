package bdsm.scheduler.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import bdsm.model.BaseModel;
import bdsm.scheduler.model.TmpBdgwDetail;
import bdsm.scheduler.model.TmpBdgwFooter;
import bdsm.scheduler.model.TmpBdgwHeader;
import bdsm.scheduler.model.TmpBdgwPK;
import bdsmhost.dao.BaseDao;


public class TmpBdgwDao extends BaseDao {

	public TmpBdgwDao(Session session) {
		super(session);
	}

	@Override
	protected boolean doInsert(BaseModel model) {
		this.getSession().save(model);
		return true;
	}

	@Override
	protected boolean doUpdate(BaseModel model) {
		this.getSession().update(model);
		return false;
	}

	@Override
	protected boolean doDelete(BaseModel model) {
		this.getSession().delete(model);
		return false;
	}
	
	
	public TmpBdgwHeader getHeader(TmpBdgwPK id) {
		return (TmpBdgwHeader) this.getSession().get(TmpBdgwHeader.class, id);
	}
	
	public TmpBdgwDetail getDetail(TmpBdgwPK id) {
		return (TmpBdgwDetail) this.getSession().get(TmpBdgwDetail.class, id);
	}
	
	public TmpBdgwFooter getFooter(TmpBdgwPK id) {
		return (TmpBdgwFooter) this.getSession().get(TmpBdgwFooter.class, id);
	}
	
	public TmpBdgwHeader getHeaderByFileId(String fileId) {
		Criteria criteria = this.getSession().createCriteria(TmpBdgwHeader.class);
		criteria.add(Restrictions.eq("id.fileId", fileId));
		
		return (TmpBdgwHeader) criteria.uniqueResult();
	}
	
	public List<TmpBdgwDetail> getDetailsByFileId(String fileId) {
		Criteria criteria = this.getSession().createCriteria(TmpBdgwDetail.class);
		criteria.add(Restrictions.eq("id.fileId", fileId));
		
		return criteria.list();
	}
	
	public TmpBdgwFooter getFooterByFileId(String fileId) {
		Criteria criteria = this.getSession().createCriteria(TmpBdgwFooter.class);
		criteria.add(Restrictions.eq("id.fileId", fileId));
		
		return (TmpBdgwFooter) criteria.uniqueResult();
	}
	
}
