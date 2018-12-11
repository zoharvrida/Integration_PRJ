package bdsmhost.dao;


import org.hibernate.Session;

import bdsm.model.BaseModel;
import bdsm.model.SkhtPaidOffResp;


/**
 * @author v00019372
 */
public class SkhtPaidOffRespDAO extends BaseAdapterDAO {

	public SkhtPaidOffRespDAO(Session session) {
		super(session);
	}
	
	
	public SkhtPaidOffResp get(String refNo) {
		return (SkhtPaidOffResp) this.getSession().get(SkhtPaidOffResp.class, refNo);
	}
	
	
	@Override
	protected boolean doInsert(BaseModel model) {
		if (model.getIdCreatedBy() == null) model.setDtmCreated(null);
		if (model.getIdCreatedSpv() == null) model.setDtmCreatedSpv(null);
		model.setIdUpdatedBy(null); model.setDtmUpdated(null);
		model.setIdUpdatedSpv(null); model.setDtmUpdatedSpv(null);
		this.getSession().save(model);
		
		return true;
	}

}
