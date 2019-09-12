package bdsmhost.fcr.dao;


import static bdsm.fcr.model.XfOlStTxnLogCurrent.REF_TXN_NO_LENGTH;

import org.hibernate.Query;
import org.hibernate.Session;

import bdsm.model.BaseModel;
import bdsm.fcr.model.XfOlStTxnLogCurrent;
import bdsm.fcr.model.XfOlStTxnLogCurrentPK;


/**
 * 
 * @author v00019372
 */
public class XfOlStTxnLogCurrentDAO extends bdsmhost.dao.BaseDao {
	public XfOlStTxnLogCurrentDAO(Session session) {
		super(session);
	}
	
	
	public XfOlStTxnLogCurrent get(XfOlStTxnLogCurrentPK pk) {
		return (XfOlStTxnLogCurrent) this.getSession().get(XfOlStTxnLogCurrent.class, pk);
	}
	
	public XfOlStTxnLogCurrent getByRefTxnNoAndCodOrgBrn(String refTxnNo, Integer codOrgBrn) {
		Query query = this.getSession().getNamedQuery("XfOlStTxnLogCurrent#getByRefTxnNoAndCodOrgBrn");
		query.setString("refTxnNo", this.rightPadSpaceRefTxnNo(refTxnNo));
		query.setParameter("codOrgBrn", codOrgBrn, new org.hibernate.type.IntegerType());
		
		return (XfOlStTxnLogCurrent) query.uniqueResult();
	}
	

	@Override
	protected boolean doInsert(BaseModel model) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	protected boolean doUpdate(BaseModel model) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	protected boolean doDelete(BaseModel model) {
		throw new UnsupportedOperationException("Not supported yet.");
	}
	
	
	protected final String rightPadSpaceRefTxnNo(String refTxnNo) {
		if (refTxnNo == null)
			refTxnNo = "";
		else if (refTxnNo.length() >= REF_TXN_NO_LENGTH)
			return refTxnNo;
		
		StringBuilder sb = new StringBuilder(refTxnNo);
		for (int i=refTxnNo.length(); i<REF_TXN_NO_LENGTH; i++)
			sb.append(' ');
		
		return sb.toString();
	}

}
