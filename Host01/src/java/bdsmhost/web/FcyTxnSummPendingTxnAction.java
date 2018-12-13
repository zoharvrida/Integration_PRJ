package bdsmhost.web;


import java.util.List;

import bdsm.model.FcyTxnSummPendingTxn;
import bdsmhost.dao.FcyTxnSummPendingTxnDao;
import bdsmhost.dao.MfcNoBook_LLDDAO;


/**
 * 
 * @author v00013493
 */
public class FcyTxnSummPendingTxnAction extends BaseHostAction {
	private static final long serialVersionUID = 5078264277068533593L;

	private List modelList;
	private FcyTxnSummPendingTxn model;

	/**
	 * Input: HTTP Request parameters: "model.noAcct"
	 * 
	 * Output: JSON Object: modelList
     * 
     * @return 
     */
	public String doList() {
		if ("28201".equals(this.model.getIdTxn())) { // LLD
			MfcNoBook_LLDDAO dao = new MfcNoBook_LLDDAO(this.getHSession());
			this.modelList = dao.listPendingTxn(this.model.getNoAcct());
		}
		else { // Valas
			FcyTxnSummPendingTxnDao dao = new FcyTxnSummPendingTxnDao(this.getHSession());
			this.modelList = dao.list(this.model.getNoAcct());
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
	 * @return the modelList
	 */
	public List getModelList() {
		return modelList;
	}

	/**
	 * @param model the model to set
	 */
	public void setModel(FcyTxnSummPendingTxn model) {
		this.model = model;
	}

	/**
	 * @return the model
	 */
	public FcyTxnSummPendingTxn getModel() {
		return model;
	}
}
