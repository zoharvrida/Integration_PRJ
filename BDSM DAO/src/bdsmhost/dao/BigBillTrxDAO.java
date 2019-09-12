package bdsmhost.dao;


import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import bdsm.model.BaseModel;
import bdsm.model.BigBillTrx;


/**
 * @author v00019372
 */
public class BigBillTrxDAO extends BaseDao {

	public BigBillTrxDAO(Session session) {
		super(session);
	}
	
	
	public BigBillTrx getByRecordId(String recordId) {
		return (BigBillTrx) this.getSession().get(BigBillTrx.class, recordId);
	}
	
	@SuppressWarnings("unchecked")
	public String pickBatchNo(Character... listRecordStatus) {
		Query query = this.getSession().getNamedQuery("BigBillTrx#listBatchNo");
		query.setParameterList("listRecordStatus", listRecordStatus);
		query.setMaxResults(1);
		List<String> listBatchNo = query.list();
		
		if ((listBatchNo != null) && (listBatchNo.size() > 0))
			return listBatchNo.get(0);
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<BigBillTrx> listRequestData(String batchNo, Integer maxResult, Integer maxRetry, Character... listRecordStatus) {
		Query query = this.getSession().getNamedQuery("BigBillTrx#listRequestData");
		query.setString("batchNo", batchNo);
		query.setInteger("maxRetry", maxRetry);
		query.setParameterList("listRecordStatus", listRecordStatus);
		
		if (maxResult != null)
			query.setMaxResults(maxResult);
		
		return query.list();
	}

	@Override
	protected boolean doInsert(BaseModel model) {
		return false;
	}

	@Override
	protected boolean doUpdate(BaseModel model) {
		return false;
	}

	@Override
	protected boolean doDelete(BaseModel model) {
		return false;
	}

}
