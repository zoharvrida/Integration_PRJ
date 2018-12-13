package bdsmhost.web;


import java.util.List;

import bdsm.model.MfcNoBook;
import bdsm.scheduler.PropertyPersister;
import bdsmhost.dao.MfcNoBookDao;
import bdsmhost.dao.MfcNoBook_LLDDAO;


/**
 * 
 * @author v00013493
 */
public class MfcNoBookAction extends BaseHostAction {
	private static final long serialVersionUID = 5078264277068533593L;

	private List modelList;
	private MfcNoBook model;
	private String idMenu;

	/**
	 * Input: HTTP Request parameters:
	 * "model.compositeId.noAcct, model.dtPost, model.typMsg, model.status"
	 * 
	 * Output: JSON Object: modelList
     * 
     * @return 
     */
	public String doList() {
		MfcNoBookDao dao = new MfcNoBookDao(getHSession());
		MfcNoBook_LLDDAO daoLLD = new MfcNoBook_LLDDAO(getHSession());
		this.getLogger().info("Parameter LLD " + PropertyPersister.lldAmount);
		
		if (idMenu.equals("20401")) {
			modelList = dao.list(model.getCompositeId().getNoAcct(), model.getDtPost(), model.getCompositeId().getTypMsg(), model.getStatus());
		}
		else if ("28401".equalsIgnoreCase(idMenu)) {
			modelList = daoLLD.list(model.getCompositeId().getNoAcct(), model.getDtPost(), model.getCompositeId().getTypMsg(), model.getStatus(), 
							PropertyPersister.lldAmount, "L");
		}

		return SUCCESS;
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
	public String doGet() {
		MfcNoBookDao dao = new MfcNoBookDao(getHSession());
		if ("28401".equalsIgnoreCase(idMenu)) {
            model = dao.get(model.getCompositeId().getNoAcct(), model.getCompositeId().getRefTxn(), model.getCompositeId().getTypMsg(),model.getTypAcct(), model.getCompositeId().getTypTrx());
        } else {
            model = dao.get(model.getCompositeId().getNoAcct(), model.getCompositeId().getRefTxn(), model.getCompositeId().getTypMsg(), "VALAS");
        }
		
		return SUCCESS;
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
	 * @return the modelList
	 */
	public List getModelList() {
		return modelList;
	}

	/**
	 * @param model the model to set
	 */
	public void setModel(MfcNoBook model) {
		this.model = model;
	}

	/**
	 * @return the model
	 */
	public MfcNoBook getModel() {
		return model;
	}

	/**
	 * @return the idMenu
	 */
	public String getIdMenu() {
		return idMenu;
	}

	/**
	 * @param idMenu the idMenu to set
	 */
	public void setIdMenu(String idMenu) {
		this.idMenu = idMenu;
	}
}
