package bdsmhost.web;

import bdsm.model.FCR_CMPreembossCardInventory;
import bdsm.service.FCR_CMPreembossCardInventoryService;
import bdsmhost.dao.FCR_CMPreembossCardInventoryDAO;

/**
 * 
 * @author bdsm
 */
public class FCR_CMPreembossCardInventoryHostAction extends BaseHostAction {
	private java.util.List<FCR_CMPreembossCardInventory> modelList;
	private FCR_CMPreembossCardInventory model;

    /**
     * 
     * @return
     */
    @Override
	public String doList() {
		this.getLogger().info("[Begin] doList()");
		
		FCR_CMPreembossCardInventoryService cardInventoryService = new FCR_CMPreembossCardInventoryService();
		cardInventoryService.setFCR_CMPreembossCardInventoryDAO(new FCR_CMPreembossCardInventoryDAO(this.getHSession()));
		this.modelList = cardInventoryService.getCards(model.getCardNo());
		
		this.getLogger().info("[ End ] doList()");
		return SUCCESS;
	}

    /**
     * 
     * @return
     */
    @Override
	public String doGet() {
		return null;
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
    public java.util.List<FCR_CMPreembossCardInventory> getModelList() {
		return this.modelList;
	}
    /**
     * 
     * @param modelList
     */
    public void setModelList(java.util.List<FCR_CMPreembossCardInventory> modelList) {
		this.modelList = modelList;
	}
	
    /**
     * 
     * @return
     */
    public FCR_CMPreembossCardInventory getModel() {
		return this.model;
	}
    /**
     * 
     * @param model
     */
    public void setModel(FCR_CMPreembossCardInventory model) {
		this.model = model;
	}


}
