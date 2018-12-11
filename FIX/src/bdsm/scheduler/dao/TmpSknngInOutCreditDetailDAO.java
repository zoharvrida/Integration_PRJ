/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.dao;

import bdsm.model.BaseModel;
import bdsm.scheduler.model.TmpSknngInOutCreditDetail;
import bdsm.scheduler.model.TmpSknngInOutCreditPK;
import bdsmhost.dao.BaseDao;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Jeffri Tambunan
 */
public class TmpSknngInOutCreditDetailDAO extends BaseDao {

    /**
     * 
     * @param session
     */
    public TmpSknngInOutCreditDetailDAO(Session session) {
		super(session);
	}
    /**
     * 
     * @param pk
     * @return
     */
    public TmpSknngInOutCreditDetail get(TmpSknngInOutCreditPK pk) {
		return (TmpSknngInOutCreditDetail) getSession().get(TmpSknngInOutCreditDetail.class, pk);
	}
    /**
     * 
     * @param batchNo
     * @param parentRecordNo
     * @return
     */
    public java.util.List<TmpSknngInOutCreditDetail> listByParentRecordNo(String batchNo, int parentRecordNo) {
		Query q = this.getSession().createQuery("FROM TmpSknngInOutCreditDetail WHERE compositeId.batchNo = :pBatchNo AND parentRecordId = :pParentRecordNo ORDER BY compositeId.recordId");
		q.setString("pBatchNo", batchNo);
		q.setInteger("pParentRecordNo", parentRecordNo);

		return q.list();
	}

    /**
     * 
     * @param BatchNo
     * @param Parent
     * @return
     */
    public List<TmpSknngInOutCreditDetail> getFullListDetails(String BatchNo, List Parent) {
		return (List<TmpSknngInOutCreditDetail>) getSession().createCriteria(TmpSknngInOutCreditDetail.class).add(Restrictions.eq("compositeId.batchNo", BatchNo)).add(Restrictions.in("compositeId.parentRecordId", Parent)).addOrder(Order.asc("compositeId.recordId")).list();
	}

    /**
     * 
     * @param batchNo
     * @return
     */
    public List<TmpSknngInOutCreditDetail> getQueryFullData(String batchNo){
		StringBuilder q = new StringBuilder();
		q.append("SELECT A.* FROM TMP_SKNNG_IN_OUT_CREDIT_DETAIL A "
				+ "WHERE BATCH_NO = :pBatchNo ");
		Query qlist = this.getSession().createSQLQuery(q.toString()).addEntity(TmpSknngInOutCreditDetail.class);
		qlist.setParameter("pBatchNo", batchNo);
		return qlist.list();
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
