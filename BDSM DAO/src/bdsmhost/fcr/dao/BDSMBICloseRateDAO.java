package bdsmhost.fcr.dao;


import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

import bdsm.fcr.model.BDSMBICloseRate;
import bdsm.model.BaseModel;
import bdsmhost.dao.BaseDao;



/**
 * @author v00020800
 */
public class BDSMBICloseRateDAO extends BaseDao {
	
	public BDSMBICloseRateDAO(Session session) {
		super(session);
	}


	public BDSMBICloseRate get() {
		return this.get(840);

	}

	@SuppressWarnings("unchecked")
	public BDSMBICloseRate get(Integer pCodccy) {
		Query query = this.getSession().getNamedQuery("BDSMBICloseRate#getByCodCcy");
		query.setInteger("ccy", pCodccy);
		query.setMaxResults(1);

		java.util.List<BDSMBICloseRate> list = query.list();
		return ((list.size() > 0)? (BDSMBICloseRate) list.get(0): null);
	}

	@SuppressWarnings("unchecked")
	public List<BDSMBICloseRate> getByCloseDate(Integer pCodccy, java.util.Date closeDate) {
		Query query = this.getSession().getNamedQuery("BDSMBICloseRate#getByCodCcyAndCloseDate");
		query.setInteger("ccy", pCodccy);
		query.setDate("closeDate", closeDate);
		query.setMaxResults(2);

		return (java.util.List<BDSMBICloseRate>) query.list();
	}

    @SuppressWarnings("unchecked")
	public List<BDSMBICloseRate> getBIDate(Integer ccy_Cod){
    	Query q = this.getSession().getNamedQuery("BDSMBICloseRate#getByCodCcy");
    	q.setInteger("ccy", ccy_Cod);
        q.setMaxResults(2);
        List<BDSMBICloseRate> listResult = q.list();
        
        return listResult;
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

}
