/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.dao;


import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;

import bdsm.model.BaseModel;
import bdsm.model.HostSknngInOutCreditDetail;
import bdsm.model.HostSknngInOutCreditPK;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;


/**
 * 
 * @author Jeffri Tambunan
 */
public class SknngInOutGen2CreditDetailDAO extends BaseDao {
	private Logger logger = Logger.getLogger(SknngInOutGen2CreditDetailDAO.class);
	public SknngInOutGen2CreditDetailDAO(Session session) {
		super(session);
	}

     public HostSknngInOutCreditDetail get(HostSknngInOutCreditPK pk) {
	        return (HostSknngInOutCreditDetail) getSession().get(HostSknngInOutCreditDetail.class, pk);
	 }
	public List<HostSknngInOutCreditDetail> listByParentRecordNo(String batchNo, int parentRecordNo) {
		Query q = this.getSession().createQuery("FROM TmpSknngInOutCreditDetail WHERE compositeId.batchNo = :pBatchNo AND parentRecordId = :pParentRecordNo ORDER BY compositeId.recordId");
		q.setString("pBatchNo", batchNo);
		q.setInteger("pParentRecordNo", parentRecordNo);

		return (List<HostSknngInOutCreditDetail>) q.list();
	}	    
        public List<HostSknngInOutCreditDetail> getListDetails(String BatchNo, Integer parendRecordId){
            return (List<HostSknngInOutCreditDetail>) getSession().createCriteria(HostSknngInOutCreditDetail.class)
	        .add(Restrictions.eq("compositeId.batchNo", BatchNo))
	        .add(Restrictions.eq("compositeId.parentRecordId",parendRecordId))
            .addOrder(Order.asc("compositeId.recordId")).list();
        }

	@Override
	protected boolean doInsert(BaseModel model) {
		getSession().save(model);
		return true;
	}

	@Override
	protected boolean doUpdate(BaseModel model) {
		getSession().update(model);
		return true;
	}

	@Override
	protected boolean doDelete(BaseModel model) {
		getSession().delete(model);
		return true;
	}
}
