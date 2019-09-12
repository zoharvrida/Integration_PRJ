/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.dao;


import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import bdsm.model.BaseModel;
import bdsm.scheduler.model.TmpGefuRespons;


/**
 * 
 * @author NCBS
 */
public class TmpGefuResponsDao extends bdsmhost.dao.BaseDao {
	
    /**
     * 
     * @param session
     */
    public TmpGefuResponsDao(Session session) {
		super(session);
	}
	
	
    /**
     * 
     * @param batchNo
     * @return
     */
    public TmpGefuRespons getByBatchNo(String batchNo) {
		Query query = this.getSession().createQuery("FROM TmpGefuRespons WHERE compositeId.batchNo = :pBatchNo");
		query.setString("pBatchNo", batchNo);
		
		List returnList = query.list();
		if (returnList.size() == 1)
			return (TmpGefuRespons) returnList.get(0);
		else
			return null;
	}
	
    /**
     * 
     * @return
     */
    public List<TmpGefuRespons> list() {
		Query q = this.getSession().createQuery("from TmpGefuRespons where status is null");
		return q.list();
	}
	
    /**
     * 
     * @param batchNoLike
     * @return
     */
    @SuppressWarnings("unchecked")
	public List<TmpGefuRespons> listByBatchNoLike(String batchNoLike) {
		Criteria c = this.getSession().createCriteria(TmpGefuRespons.class);
		c.add(Restrictions.like("compositeId.batchNo", batchNoLike, MatchMode.START));
		
		return c.list();
	}
	
    /**
     * 
     * @param model
     * @return
     */
    @Override
	protected boolean doInsert(BaseModel model) {
		getSession().save((TmpGefuRespons) model);
		return true;
	}
	
    /**
     * 
     * @param model
     * @return
     */
    @Override
	protected boolean doUpdate(BaseModel model) {
		getSession().update((TmpGefuRespons) model);
		return true;
	}
	
    /**
     * 
     * @param model
     * @return
     */
    @Override
	protected boolean doDelete(BaseModel model) {
		getSession().delete((TmpGefuRespons) model);
		return true;
	}
}
