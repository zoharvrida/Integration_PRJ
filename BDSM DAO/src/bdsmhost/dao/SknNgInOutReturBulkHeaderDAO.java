package bdsmhost.dao;


import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import bdsm.model.BaseModel;
import bdsm.model.SknNgInOutReturBulkHeader;


/**
 * @author v00019372
 */
public class SknNgInOutReturBulkHeaderDAO extends BaseDao {

	public SknNgInOutReturBulkHeaderDAO(Session session) {
		super(session);
	}
	
	
	public SknNgInOutReturBulkHeader get(java.io.Serializable id) {
		return (SknNgInOutReturBulkHeader) this.getSession().get(SknNgInOutReturBulkHeader.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<SknNgInOutReturBulkHeader> listByBatchNo(String batchNo) {
		Query query = this.getSession().getNamedQuery("SknNgInOutReturBulkHeader#listByBatchNo");
		query.setString("batchNo", batchNo);
		
		return query.list();
	}

	@Override
	protected boolean doInsert(BaseModel model) {
		model.setIdCreatedSpv(null);
		model.setDtmCreatedSpv(null);
		model.setIdUpdatedBy(null);
		model.setDtmUpdated(null);
		model.setIdUpdatedSpv(null);
		model.setDtmUpdatedSpv(null);

		this.getSession().save(model);
		return true;
	}

	@Override
	protected boolean doUpdate(BaseModel model) {
		throw new UnsupportedOperationException("Not yet implemented!!!");
	}

	@Override
	protected boolean doDelete(BaseModel model) {
		throw new UnsupportedOperationException("Not yet implemented!!!");
	}

}
