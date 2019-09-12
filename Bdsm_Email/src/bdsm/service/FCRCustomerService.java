package bdsm.service;


import bdsm.model.CoCiCustmast;
import bdsm.model.CoCiCustmastPK;
import bdsm.model.FcrCiCustmast;
import bdsm.model.ScreenOpening;
import bdsmhost.dao.CifCasaExtOpeningDao;
import bdsmhost.dao.FcrCiCustmastDao;
import bdsmhost.dao.ParameterDao;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class FCRCustomerService {
	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(FCRCustomerService.class);
	private FcrCiCustmastDao customerMasterDAO;
	
	public List<FcrCiCustmast> list(FCRCustomerService customerService) {
		return this.list(customerService);
	}
	/* == Setter Injection == */
	public void setFCRCustomerMasterDAO(FcrCiCustmastDao customerMasterDAO) {
            logger.info("[ Dao Service ] "+ customerMasterDAO);
		this.customerMasterDAO = customerMasterDAO;
	}
	public FcrCiCustmast getByCustomerId(Integer customerId) {
            
		return this.customerMasterDAO.get(customerId);
	}

   
}
