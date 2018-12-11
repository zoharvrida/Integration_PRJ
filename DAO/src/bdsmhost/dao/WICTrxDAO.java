package bdsmhost.dao;


import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import bdsm.model.BaseModel;
import bdsm.model.WICTrx;


/**
 * 
 * @author v00019372
 */
public class WICTrxDAO extends BaseDao {

	public WICTrxDAO(Session session) {
		super(session);
	}
	
	
	/* do nothing method, because already handled by BDSM Framework */
	public void commit() {}
	public void rollback() {} 
	

	@Override
	protected synchronized boolean doInsert(BaseModel model) {
		/* Generate ID */
		Query query = this.getSession().getNamedQuery("WICTrx#getMaximumTrxNo");
		Long maxTrxNo = (Long) query.uniqueResult();
		
		((WICTrx) model).setTrxNo((maxTrxNo==null)? 1: maxTrxNo + 1);
		
		model.setDtmUpdated(null);
		this.getSession().save(model);
		
		return true;
	}

	@Override
	protected boolean doUpdate(BaseModel model) {
		Object managedObject = null;
		
		if (((WICTrx) model).getTrxNo() != null)
			managedObject = this.get(((WICTrx) model).getTrxNo());
		
		if (managedObject == null)
			managedObject = this.getByWICNoAndTrxRefNo((WICTrx) model);
		
		if (managedObject == null)
			return false;
		else {
			this.evictObjectFromPersistenceContext(managedObject);
			this.getSession().merge(model);
		}
		
		return true;
	}

	@Override
	protected boolean doDelete(BaseModel model) {
		throw new UnsupportedOperationException("Not supported yet.");
	}
	
	
	public WICTrx get(Long trxNo) {
		return ((WICTrx) this.getSession().get(WICTrx.class, trxNo));
	}
	
	@SuppressWarnings("unchecked")
	public WICTrx getByWICNoAndTrxRefNo(WICTrx wicTrx) {
		List<WICTrx> returnList;
		
		Query query = this.getSession().getNamedQuery("WICTrx#getByWICNoAndTrxRefNo");
		query.setInteger("WICNo", wicTrx.getWICNo());
		query.setString("refNo", wicTrx.getRefNo());
		query.setCharacter("flagStatusDB", 'A');
		
		returnList = (List<WICTrx>) query.list();
		if ((returnList != null) && (returnList.size() > 0))
			return returnList.get(0);
		else
			return null;
	}
	
	public WICTrx getByTrxRefNo(String trxRefNo) {
		Query query = this.getSession().getNamedQuery("WICTrx#getByWICNoAndTrxRefNo");
		query.setParameter("WICNo", null, new org.hibernate.type.IntegerType());
		query.setString("refNo", trxRefNo);
		query.setCharacter("flagStatusDB", 'A');
		
		return (WICTrx) query.uniqueResult();
	}

}
