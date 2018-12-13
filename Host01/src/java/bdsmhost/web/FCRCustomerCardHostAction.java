package bdsmhost.web;

import bdsm.model.FCRCustomerCardMaster;
import bdsm.scheduler.PropertyPersister;
import bdsm.service.FCRCustomerCardService;
import bdsmhost.dao.FCRCustomerCardMasterDAO;

/**
 * 
 * @author bdsm
 */
public class FCRCustomerCardHostAction extends BaseHostAction {
	private static String adminEmail = PropertyPersister.adminEmail; // load PropertyPersister class
	private FCRCustomerCardMaster model;

    /**
     * 
     * @return
     */
    @Override
	public String doGet() {
		this.getLogger().info("[Begin] doGet()");

		/* Customer Card */
		FCRCustomerCardService custCardService = new FCRCustomerCardService();
		custCardService.setFCRCustomerCardMasterDAO(new FCRCustomerCardMasterDAO(this.getHSession()));
		this.model = custCardService.getByCardNo(model.getCardNo());

		this.getLogger().info("[ End ] doGet()");
		return FCRCustomerCardHostAction.SUCCESS;
	}

    /**
     * 
     * @return
     */
    @Override
	public String doList() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

    /**
     * 
     * @return
     */
    @Override
	public String exec() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

    /**
     * 
     * @return
     */
    @Override
	public String doSave() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

    /**
     * 
     * @return
     */
    @Override
	public String doInsert() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

    /**
     * 
     * @return
     */
    @Override
	public String doUpdate() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

    /**
     * 
     * @return
     */
    @Override
	public String doDelete() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

    /**
     * 
     * @return
     */
    public FCRCustomerCardMaster getModel() {
		return this.model;
	}

    /**
     * 
     * @param model
     */
    public void setModel(FCRCustomerCardMaster model) {
		this.model = model;
	}
}
