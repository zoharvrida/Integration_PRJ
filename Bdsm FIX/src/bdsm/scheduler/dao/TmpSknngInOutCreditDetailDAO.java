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
import java.util.Map;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Jeffri Tambunan
 */
public class TmpSknngInOutCreditDetailDAO extends BaseDao {

    private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(TmpSknngInOutCreditDetailDAO.class);
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
	//TUNING SKN
    public List<TmpSknngInOutCreditDetail> getQueryDatabyParent(String batchNo, int recdno){
		StringBuilder q = new StringBuilder();
		q.append("SELECT A.* FROM TMP_SKNNG_IN_OUT_CREDIT_DETAIL A , TMP_SKNNG_IN_OUT_CREDIT_HEADER B WHERE b.BATCH_NO = :pBatchNo   AND a.batch_no = b.batch_no AND a.parent_record_id = b.parent_record_id	 AND a.parent_record_id = :pRec ORDER BY a.parent_record_id DESC NULLS LAST");
		Query qlist = this.getSession().createSQLQuery(q.toString()).addEntity(TmpSknngInOutCreditDetail.class);
		qlist.setParameter("pBatchNo", batchNo);
		qlist.setParameter("pRec", recdno);
        List<TmpSknngInOutCreditDetail> lData = qlist.list(); 
        logger.debug("LIST SIZE DETAIL :" + lData.size());
		return lData;
	}
    
	public List<TmpSknngInOutCreditDetail> getQueryDatabyParent(String batchNo){
		StringBuilder q = new StringBuilder();
		q.append("SELECT A.* FROM TMP_SKNNG_IN_OUT_CREDIT_DETAIL A , TMP_SKNNG_IN_OUT_CREDIT_HEADER B WHERE b.BATCH_NO = :pBatchNo   AND a.batch_no = b.batch_no AND a.parent_record_id = b.parent_record_id ORDER BY a.parent_record_id DESC NULLS LAST");
		Query qlist = this.getSession().createSQLQuery(q.toString()).addEntity(TmpSknngInOutCreditDetail.class);
		qlist.setParameter("pBatchNo", batchNo);
        List<TmpSknngInOutCreditDetail> lData = qlist.list(); 
        logger.debug("LIST SIZE DETAIL NO HEADER :" + lData.size());
		return lData;
	}
	
    public List<Map> getQueryBICODE(String batchNo, String type){
       StringBuilder q = new StringBuilder();
       
       Query qlist = null;
       if ("IPPengirim".equalsIgnoreCase(type)){
           qlist = this.getSession().getNamedQuery("SKN#IPPengirim");
       } else if("IPPengirimSKA".equalsIgnoreCase(type)){
           qlist = this.getSession().getNamedQuery("SKN#IPPengirimSKA");
       } else if ("IPPenerima".equalsIgnoreCase(type)){
           qlist = this.getSession().getNamedQuery("SKN#IPPenerima");
       } else if("IPPenerimaSKT".equalsIgnoreCase(type)){
           qlist = this.getSession().getNamedQuery("SKN#IPPenerimaSKT");
       }
       
       qlist.setString("pId", batchNo);
       qlist.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
       List<Map> lData = qlist.list();
       return lData;
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
