package bdsmhost.dao;


import org.hibernate.Session;

import bdsm.model.BaseModel;
import bdsm.model.SkhtPrintPaid;


/**
 * @author v00019372
 */
public class SkhtPrintPaidDAO extends BaseAdapterDAO {

	public SkhtPrintPaidDAO(Session session) {
		super(session);
	}
	
	
	public SkhtPrintPaid get(String refNo) {
		return (SkhtPrintPaid) this.getSession().get(SkhtPrintPaid.class, refNo);
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
