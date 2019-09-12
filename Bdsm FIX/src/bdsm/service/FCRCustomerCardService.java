package bdsm.service;


import java.util.List;

import bdsm.model.FCRCustomerCardMaster;
import bdsm.model.FcrChAcctMast;
import bdsm.scheduler.PropertyPersister;
import bdsmhost.dao.FCRCustomerCardMasterDAO;


/**
 * 
 * @author bdsm
 */
public class FCRCustomerCardService {
	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(FCRCustomerCardService.class);
	private FCRCustomerCardMasterDAO custCardMasterDAO;
	
	
	/* == Setter Injection == */
    /**
     * 
     * @param custCardMasterDAO
     */
    public void setFCRCustomerCardMasterDAO(FCRCustomerCardMasterDAO custCardMasterDAO) {
		this.custCardMasterDAO = custCardMasterDAO;
	}
	
	
    /**
     * 
     * @param cardNo
     * @return
     */
    public FCRCustomerCardMaster getByCardNo(String cardNo) {
		logger.debug("getByCardNo : " + cardNo);
		
		return this.custCardMasterDAO.get(new FCRCustomerCardMaster(cardNo, "A", PropertyPersister.codEntityVPD));
	}
	
    /**
     * 
     * @param customerCard
     * @return
     */
    public List<FcrChAcctMast> getAccounts(FCRCustomerCardMaster customerCard) {
		return this.getAccountsByFlagMaintenance(customerCard, "A");
	}
	
    /**
     * 
     * @param customerCard
     * @return
     */
    public List<FcrChAcctMast> getInactiveAccounts(FCRCustomerCardMaster customerCard) {
		return this.getAccountsByFlagMaintenance(customerCard, "X");
	}
	
    /**
     * 
     * @param customerCard
     * @param flagMaintenanceStatus
     * @return
     */
    protected List<FcrChAcctMast> getAccountsByFlagMaintenance(FCRCustomerCardMaster customerCard, String flagMaintenanceStatus) {
		List<FcrChAcctMast> listAccounts = this.custCardMasterDAO.getAccounts(customerCard);
		
		if ((listAccounts.size() > 0))
			for (int i=0; i<listAccounts.size(); i++) {
				FcrChAcctMast acct = listAccounts.get(i);
				if (acct.getCompositeId().getFlgMntStatus().equalsIgnoreCase(flagMaintenanceStatus) == false)
					listAccounts.remove(i--);
			}
		
		return listAccounts;
	} 
}
