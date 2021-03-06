/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.dao;

import bdsm.model.BaseModel;
import bdsm.scheduler.model.TmpSknngInOutCreditFooter;
import bdsmhost.dao.BaseDao;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 *
 * @author Jeffri Tambunan
 */
public class TmpSknngInOutCreditFooterDAO extends BaseDao {
	
    /**
     * 
     * @param session
     */
    public TmpSknngInOutCreditFooterDAO(Session session) {
        super(session);
    }
    
    /**
     * 
     * @param batchNo
     * @param parentRecordNo
     * @return
     */
    public TmpSknngInOutCreditFooter get(String batchNo, int parentRecordNo) {
		Query q = this.getSession().createQuery("FROM TmpSknngInOutCreditFooter WHERE compositeId.batchNo = :pBatchNo AND compositeId.parentRecordId = :pParentRecordNo");
		q.setString("pBatchNo", batchNo);
		q.setInteger("pParentRecordNo", parentRecordNo);

		return (TmpSknngInOutCreditFooter) q.uniqueResult();
	}

    /**
     * 
     * @param batchNo
     * @return
     */
    public List<TmpSknngInOutCreditFooter> list(String batchNo) {
        Query q = getSession().createQuery("FROM TmpSknngInOutCreditFooter WHERE compositeId.batchNo = :pBatchNo ORDER BY compositeId.recordId");
        q.setString("pBatchNo", batchNo);
        
        return q.list();
    }

    
    /**
     * 
     * @param model
     * @return
     */
    @Override
    protected boolean doInsert(BaseModel model) {
        getSession().save(model);
        return true;
    }

    /**
     * 
     * @param model
     * @return
     */
    @Override
    protected boolean doUpdate(BaseModel model) {
        getSession().update(model);
        return true;
    }

    /**
     * 
     * @param model
     * @return
     */
    @Override
    protected boolean doDelete(BaseModel model) {
        getSession().delete(model);
        return true;
    }
}
