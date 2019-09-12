package bdsmhost.dao;


import org.hibernate.Session;


/**
 * @author v00019372
 */
public class SkhtWSAuditTrailDAO extends BaseAdapterDAO {

	public SkhtWSAuditTrailDAO(Session session) {
		super(session);
	}
	
	
	@Override
	protected boolean doInsert(bdsm.model.BaseModel model) {
		this.getSession().save(model);
		return true;
	}
	
}
