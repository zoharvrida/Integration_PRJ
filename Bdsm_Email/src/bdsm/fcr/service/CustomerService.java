package bdsm.fcr.service;


import bdsm.fcr.model.CiCustMast;
import bdsm.fcr.model.CiCustMastPK;
import bdsmhost.fcr.dao.CiCustMastDao;


/**
 * @author v00019372
 */
public class CustomerService {
	private static org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(CustomerService.class);
	private CiCustMastDao ciCustMastDAO;
	
	
	public void setCiCustMastDAO(CiCustMastDao ciCustMastDAO) {
		this.ciCustMastDAO = ciCustMastDAO;
	}
	
	
	public CiCustMast getByCustomerId(Integer customerId) {
		CiCustMastPK id = new CiCustMastPK();
		id.setCodCustId(customerId);
		id.setFlgMntStatus("A");
		id.setCodEntityVpd(11);
		
		return this.ciCustMastDAO.get(id);
	}
	public String getCustomerTypeText(String customerType) {
		return this.ciCustMastDAO.getCustomerTypeText(customerType);
	}
	public String getResidentialStatusCode(Integer customerId) {
		Object[] result;
		if ((result = this.getResidentialStatus(customerId)) != null)
			return ((String) result[0]);
		return null;
	}
	public String getResidentialStatusText(Integer customerId) {
		Object[] result;
		if ((result = this.getResidentialStatus(customerId)) != null)
			return ((String) result[1]);
		return null;
	}
	public Object[] getResidentialStatus(Integer customerId) {
		return this.ciCustMastDAO.getResidentialStatus(customerId);
	}
	
}
