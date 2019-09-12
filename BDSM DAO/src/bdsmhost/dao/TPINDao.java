package bdsmhost.dao;


import org.hibernate.Session;
import org.hibernate.Transaction;

import bdsm.model.BaseHistoryInterface;
import bdsm.model.BaseModel;
import bdsm.model.TPIN;
import bdsmhost.interceptor.AbstractTimeHistoryInterceptor;


public class TPINDao extends bdsmhost.dao.BaseDao {
	private AbstractTimeHistoryInterceptor<? extends BaseHistoryInterface> interceptor;
	private Session localSession;
	private Transaction localTransaction;
	
	
	public TPINDao(Session session) {
		super(session);
	}
	
	
	public void setInterceptor(AbstractTimeHistoryInterceptor<? extends BaseHistoryInterface> interceptor) {
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
	
	public void clearPersistenceContext() {
		if (this.localSession != null)
			this.localSession.clear();
	}
	
	public Transaction beginTransaction() {
		if (this.localSession != null) {
			this.localTransaction = this.localSession.beginTransaction();
			return this.localTransaction;
		}
		else
			return null;
	}
	
	public void commitTransaction() {
		if ((this.localSession != null) && (this.localTransaction != null) && (this.localTransaction.isActive()))
			this.localTransaction.commit();
	}
	
	public void rollbackTransaction() {
		if ((this.localSession != null) && (this.localTransaction != null) && (this.localTransaction.isActive()))
			this.localTransaction.rollback();
	}
	
	
	@Override
	protected boolean doInsert(BaseModel model) {
		this.getSession().save(model);
		return true;
	}

	@Override
	protected boolean doUpdate(BaseModel model) {
		Object managedObject = this.get(((TPIN) model).getCardNo());
		
		if (managedObject == null)
			return false;
		else
			this.getSession().merge(model);
		
		return true;
	}

	@Override
	protected boolean doDelete(BaseModel model) {
		this.getSession().delete(model);
		return true;
	}

	
	public TPIN get(java.io.Serializable cardNo) {
		return (TPIN) this.getSession().get(TPIN.class, cardNo);
	}
	
}
