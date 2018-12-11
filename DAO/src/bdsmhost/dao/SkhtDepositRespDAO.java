package bdsmhost.dao;


import org.hibernate.Session;

import bdsm.model.BaseModel;
import bdsm.model.SkhtDepositResp;


/**
 * @author v00019372
 */
public class SkhtDepositRespDAO extends BaseAdapterDAO {

	public SkhtDepositRespDAO(Session session) {
		super(session);
	}
	
	
	public SkhtDepositResp get(String refNo) {
		return (SkhtDepositResp) this.getSession().get(SkhtDepositResp.class, refNo);
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
