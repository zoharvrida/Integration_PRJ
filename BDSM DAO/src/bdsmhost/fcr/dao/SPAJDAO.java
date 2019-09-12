package bdsmhost.fcr.dao;


import org.hibernate.Query;

import bdsm.fcr.model.SPAJ;
import bdsm.model.BaseModel;
import bdsm.util.BdsmUtil;


/**
 * 
 * @author v00019372
 *
 */
public class SPAJDAO extends bdsmhost.dao.BaseAdapterDAO {

	public SPAJDAO(org.hibernate.Session session) {
		super(session);
	}
	
	
	public SPAJ getByAccountNo(String accountNo) {
		return (SPAJ) this.getSession().get(SPAJ.class, BdsmUtil.rightPad(BdsmUtil.leftPad(accountNo, 12, '0'), 16));
	}
	
	public String generateId() {
		Long requestId;
		Query query = this.getSession().createQuery("SELECT MAX(refNo2) FROM SPAJ");
		String id = (String) query.uniqueResult();
		
		if (id == null)
			id = "0";
		requestId = Long.parseLong(id) + 1;
		id = requestId.toString().trim();
		
		return bdsm.util.BdsmUtil.leftPad(id, (id.length() > 8)? id.length(): 8, '0');
	}
	
	
	@Override
	protected boolean doUpdate(BaseModel model) {
		this.getSession().saveOrUpdate(model);
		return true;
	}

}
