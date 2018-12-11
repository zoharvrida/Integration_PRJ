package bdsmhost.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

import bdsm.model.BaseReqApprHistoryInterface;
import bdsmhost.interceptor.AbstractRequestApprovalHistoryInterceptor;


/**
 * @author v00019372
 */
public class BaseHistoryInterceptorDAO extends BaseAdapterDAO {
	private AbstractRequestApprovalHistoryInterceptor<? extends BaseReqApprHistoryInterface> interceptor;
	private Session localSession;
	private Transaction localTransaction;


	public BaseHistoryInterceptorDAO(Session session) {
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

}
