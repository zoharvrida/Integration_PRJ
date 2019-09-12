package bdsmhost.dao;


import java.util.List;

import org.hibernate.Query;

import bdsm.model.BaseModel;
import bdsm.model.SknNgInOutCreditHeader;


/**
 * @author v00019372
 */
public class SknNgInOutCreditHeaderDAO extends BaseDao {

	public SknNgInOutCreditHeaderDAO(org.hibernate.Session session) {
		super(session);
	}
	
	
	public SknNgInOutCreditHeader get(java.io.Serializable id) {
		return (SknNgInOutCreditHeader) this.getSession().get(SknNgInOutCreditHeader.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<SknNgInOutCreditHeader> getByBatchNo(String batchNo) {
		Query query = this.getSession().getNamedQuery("SknNgInOutCreditHeader#listByBatchNo");
		query.setString("batchNo", batchNo);
		
		return query.list();
	}
	

	@Override
	protected boolean doInsert(BaseModel model) {
		this.getSession().save(model);
		return true;
	}

	@Override
	protected boolean doUpdate(BaseModel model) {
		this.getSession().update(model);
		return true;
	}

	@Override
	protected boolean doDelete(BaseModel model) {
		this.getSession().delete(model);
		return true;
	}

}
