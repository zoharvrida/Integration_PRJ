package bdsmhost.fcr.dao;


import org.hibernate.Query;

import bdsm.fcr.model.SPAJReference;
import bdsm.model.BaseModel;
import bdsm.util.BdsmUtil;



/**
 * 
 * @author v00019372
 *
 */
public class SPAJReferenceDAO extends bdsmhost.dao.BaseAdapterDAO {
	public SPAJReferenceDAO(org.hibernate.Session session) {
		super(session);
	}
	
	
	public SPAJReference getByReferenceNo(String referenceNo) {
		return (SPAJReference) this.getSession().get(SPAJReference.class, BdsmUtil.rightPad(BdsmUtil.leftPad(referenceNo, 12, '0'), 16));
	}
	
	
	@SuppressWarnings("unchecked")
	public String[] getInDataFromSPAJReferenceHistory(String referenceNo, java.util.Date inSysdate) {
		Query query = this.getSession().getNamedQuery("SPAJReference#getInDataAndStatusFromHistory");
		query.setString("referenceNo", BdsmUtil.rightPad(BdsmUtil.leftPad(referenceNo, 12, '0'), 16));
		query.setTimestamp("inSysdate", inSysdate);
		query.setMaxResults(1);
		
		String[] returnValue = null;
		java.util.List<Object[]> listResult = query.list();
		
		if (listResult.size() > 0) {
			Object[] result = listResult.get(0);
			returnValue = new String[] {result[0].toString(), result[1].toString()};
		}
		
		return returnValue;
	}
	
	public void insertSPAJReferenceHistory(SPAJReference spajReference) {
		Query query = this.getSession().getNamedQuery("SPAJReference#insertIntoHistory");
		query
			.setString("referenceNo", spajReference.getReferenceNo())
			.setString("outData", spajReference.getOutData())
			.setString("outStatus", spajReference.getOutStatus())
			.setString("outDesc1", spajReference.getOutDesc1())
			.setTimestamp("outSysdate", spajReference.getOutSysdate())
			.setString("inData", spajReference.getInData())
			.setString("inStatus", spajReference.getInStatus())
			.setString("inDesc1", spajReference.getInDesc1())
			.setString("inDesc2", spajReference.getInDesc2())
			.setTimestamp("inSysdate", spajReference.getInSysdate())
			.setTimestamp("dtmLog", new java.sql.Timestamp(System.currentTimeMillis()))
		;
		
		query.executeUpdate();
	}
	
	public void deleteSPAJReferenceHistory(String referenceNo, java.util.Date inSysdate) {
		Query query = this.getSession().createSQLQuery("DELETE FROM spaj_reference_history WHERE reference_no = :referenceNo AND in_sysdate = :inSysdate");
		query.setString("referenceNo", BdsmUtil.rightPad(BdsmUtil.leftPad(referenceNo, 12, '0'), 16));
		query.setTimestamp("inSysdate", inSysdate);
		
		query.executeUpdate();
	}
	
	
	@Override
	protected boolean doInsert(BaseModel model) {
		model.setDtmUpdated(null);
		this.getSession().save(model);
		
		return true;
	}
	
	@Override
	protected boolean doUpdate(BaseModel model) {
		this.getSession().saveOrUpdate(model);
		return true;
	}
	
}
