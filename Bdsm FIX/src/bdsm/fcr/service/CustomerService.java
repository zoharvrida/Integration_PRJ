package bdsm.fcr.service;


import java.util.Calendar;
import java.util.Date;

import bdsm.fcr.model.CiCustMast;
import bdsm.fcr.model.CiCustMastPK;
import bdsmhost.fcr.dao.CiCustMastDao;


/**
 * @author v00019372
 */
public class CustomerService {
	private static org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(CustomerService.class);
	private CiCustMastDao ciCustMastDAO;
	
	
    /**
     * 
     * @param ciCustMastDAO
     */
    public void setCiCustMastDAO(CiCustMastDao ciCustMastDAO) {
		this.ciCustMastDAO = ciCustMastDAO;
	}
	
	
    /**
     * 
     * @param customerId
     * @return
     */
    public CiCustMast getByCustomerId(Integer customerId) {
		CiCustMastPK id = new CiCustMastPK();
		id.setCodCustId(customerId);
		id.setFlgMntStatus("A");
		id.setCodEntityVpd(11);
		
		return this.ciCustMastDAO.get(id);
	}

    /**
     * 
     * @param customerType
     * @return
     */
    public String getCustomerTypeText(String customerType) {
		return this.ciCustMastDAO.getCustomerTypeText(customerType);
	}


    /**
     * 
     * @param customerId
     * @return
     */
    public String getResidentialStatusCode(Integer customerId) {
		Object[] result;
		if ((result = this.getResidentialStatus(customerId)) != null)
			return ((String) result[0]);
		
		return null;
	}

    /**
     * 
     * @param customerId
     * @return
     */
    public String getResidentialStatusText(Integer customerId) {
		Object[] result;
		if ((result = this.getResidentialStatus(customerId)) != null)
			return ((String) result[1]);
		
		return null;
	}

    /**
     * 
     * @param customerId
     * @return
     */
    public Object[] getResidentialStatus(Integer customerId) {
		return this.ciCustMastDAO.getResidentialStatus(customerId);
	}
	
	
    /**
     * 
     * @param ciCustMast
     * @param currentDate
     * @return
     */
    public static int getAge(CiCustMast ciCustMast, Date currentDate) {
		int result;
		
		Calendar birthDate = Calendar.getInstance();
		birthDate.setTime(ciCustMast.getDatBirthCust());
		Calendar current = Calendar.getInstance();
		current.setTime(currentDate);
		
		result = current.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);
		if (
				(current.get(Calendar.MONTH) < birthDate.get(Calendar.MONTH)) ||
				((current.get(Calendar.MONTH) == birthDate.get(Calendar.MONTH)) && (current.get(Calendar.DATE) < birthDate.get(Calendar.DATE)))
			)
			result -= 1;
		
		return result;
	}
	
}
