package bdsmhost.dao;


import org.hibernate.Session;

import bdsm.model.BaseModel;
import bdsm.model.SkhtPrintDep;


/**
 * @author v00019372
 */
public class SkhtPrintDepDAO extends BaseAdapterDAO {
	
	public SkhtPrintDepDAO(Session session) {
		super(session);
	}
	
	
	public SkhtPrintDep get(String refNo) {
		return (SkhtPrintDep) this.getSession().get(SkhtPrintDep.class, refNo);
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
