package bdsmhost.fcr.dao;


import bdsm.fcr.model.CiCustMast;
import bdsm.fcr.model.CiCustMastPK;
import bdsm.model.BaseModel;
import bdsmhost.dao.BaseDao;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;


/**
 * @author v00020841
 */
public class CiCustMastDao extends BaseDao {

	public CiCustMastDao(Session session) {
		super(session);
	}

	public CiCustMast get(CiCustMastPK id) {
		return (CiCustMast) this.getSession().get(CiCustMast.class, id);
	}


	public String getCustomerTypeText(String customerType) {
		String result = null;
		
		if (StringUtils.isNotBlank(customerType)) {
			Query query = this.getSession().getNamedQuery("CiCustMast#getTextCustomerType");
			query.setCharacter("customerType", customerType.charAt(0));
			
			result = (String) query.uniqueResult();
		}
		
		return result;
	}

	public Object[] getResidentialStatus(Integer customerId) {
		Object[] result = null;
		
		if (customerId != null) {
			Query query = this.getSession().getNamedQuery("CiCustMast#getResidentialStatus");
			query.setInteger("customerId", customerId);
			
			result = (Object[]) query.uniqueResult();
		}
		
		return result;
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
