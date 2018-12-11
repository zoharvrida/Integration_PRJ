package bdsmhost.dao;


import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import bdsm.model.BaseModel;
import bdsm.model.BaseReqApprHistoryInterface;
import bdsm.model.WIC;
import bdsmhost.interceptor.AbstractRequestApprovalHistoryInterceptor;


/**
 * 
 * @author v00019372
 */
public class WICDao extends BaseDao {
	private AbstractRequestApprovalHistoryInterceptor<? extends BaseReqApprHistoryInterface> interceptor;
	private Session localSession;
	private Transaction localTransaction;
	

	public WICDao(Session session) {
		super(session);
	}
	
	public void setInterceptor(AbstractRequestApprovalHistoryInterceptor<? extends BaseReqApprHistoryInterface> interceptor) {
		this.interceptor = interceptor;
	}
	
	public boolean isInterceptorExist() {
		return (this.interceptor != null);
	}
	
	@Override
	protected Session getSession() {
		if (this.localSession == null) {
			if (isInterceptorExist() == false)
				this.localSession = super.getSession();
			else {
				this.localSession = super.getSession().getSessionFactory().openSession(this.interceptor);
				this.interceptor.setSession(super.getSession());
				this.localTransaction = this.localSession.beginTransaction();
			}
		}
		
		return this.localSession;
	}
	
	public Session getLocalSession() {
		if (this.isInterceptorExist())
			return this.localSession;
		else
			return null;
	}
	
	public Transaction beginTransaction() {
		if (this.localSession != null) {
			this.localTransaction = this.localSession.beginTransaction();
			return this.localTransaction;
		}
		else
			return null;
	}
	
	public void commit() {
		if (this.isInterceptorExist())
			this.commitTransaction();
	}
	
	public void rollback() {
		if (this.isInterceptorExist())
			this.rollbackTransaction();
	}
	
	protected void commitTransaction() {
		if ((this.localSession != null) && (this.localTransaction != null) && (this.localTransaction.isActive()))
			this.localTransaction.commit();
	}
	
	protected void rollbackTransaction() {
		if ((this.localSession != null) && (this.localTransaction != null) && (this.localTransaction.isActive()))
			this.localTransaction.rollback();
	}
	

	@Override
	protected synchronized boolean doInsert(BaseModel model) {
		/* Generate ID */
		Query query = this.getSession().getNamedQuery("WIC#getMaximumWICNo");
		Integer maxId = (Integer) query.uniqueResult();
		
		((WIC) model).setWICNo((maxId==null)? 1: maxId + 1);
		
		model.setDtmUpdated(null);
		this.getSession().save(model);
		this.commitTransaction();
		
		return true;
	}

	@Override
	protected boolean doUpdate(BaseModel model) {
		Object managedObject = null;
		
		if (((WIC) model).getWICNo() != null)
			managedObject = this.get(((WIC) model).getWICNo());
		
		if (managedObject == null)
			managedObject = this.getByCustomerTypeAndId((WIC) model);
		
		if (managedObject == null)
			return false;
		else {
			this.evictObjectFromPersistenceContext(managedObject);
			this.getSession().merge(model);
		}
		
		this.commitTransaction();
		
		return true;
	}

	@Override
	protected boolean doDelete(BaseModel model) {
		throw new UnsupportedOperationException("Not yet supported!!!");
	}
	
	
	public WIC get(int wicNo) {
		return (WIC) this.getSession().get(WIC.class, wicNo);
	}
	
	@SuppressWarnings("unchecked")
	public WIC getByCustomerTypeAndId(WIC wic) {
		List<WIC> returnList;
		
		Query query = this.getSession().getNamedQuery("WIC#getByCustomerTypeAndId");
		query.setInteger("customerType", wic.getCustomerType());
		query.setString("idType", wic.getIdType());
		query.setString("idNumber", wic.getIdNumber());
		query.setCharacter("flagStatusDB", 'A');
		
		returnList = (List<WIC>) query.list();
		if ((returnList != null) && (returnList.size() > 0))
			return returnList.get(0);
		else
			return null;
	}

}
