package bdsmhost.dao;


import org.hibernate.Session;


/**
 * @author v00017250
 */
public class ETaxWSAuditTrailDAO extends BaseAdapterDAO {

	public ETaxWSAuditTrailDAO(Session session) {
		super(session);
	}
	
	
	@Override
	protected boolean doInsert(bdsm.model.BaseModel model) {
		this.getSession().save(model);
		return true;
	}
	
}
