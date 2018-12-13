/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.web;

import bdsm.fcr.model.ChAcctMast;
import bdsm.fcr.model.ChAcctMastPK;
import bdsm.model.FcrChAcctMast;
import bdsm.model.FcrChAcctMastPK;
import bdsmhost.dao.FcrChAcctMastDao;
import bdsmhost.fcr.dao.ChAcctMastDAO;
import java.util.List;

/**
 *
 * @author v00013493
 */
public class FcrChAcctMastAction extends BaseHostAction {

	private static final long serialVersionUID = 5078264277068533593L;
	private List<FcrChAcctMast> modelList;
	private FcrChAcctMast model;
	private FcrChAcctMastPK modelPk;
	private ChAcctMast modelFCR;
	private ChAcctMastPK modelPKfcr;

    /**
     * 
     * @return
     */
    public String doGet() {
		FcrChAcctMastDao dao = new FcrChAcctMastDao(getHSession());
		model = dao.get(model.getCompositeId());
        getLogger().debug("CH_MAP :" + model);
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
	public String doList() {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

    /**
     * 
     * @return
     */
    @Override
	public String doSave() {
		getLogger().info("ACCOUNT :" + getModelPKfcr().getCodAcctNo());
		getLogger().info("FLAG :" + getModelPKfcr().getFlgMntStatus());
		modelPKfcr.setCodEntityVpd(11);
		ChAcctMastDAO dao = new ChAcctMastDAO(getHSession());
		modelFCR = dao.get(modelPKfcr);
		getLogger().info("MODEL :" + modelFCR);
		return SUCCESS;
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
	public List<FcrChAcctMast> getModelList() {
		return modelList;
	}

	/**
	 * @param model the model to set
	 */
	public void setModel(FcrChAcctMast model) {
		this.model = model;
	}

	/**
	 * @return the model
	 */
	public FcrChAcctMast getModel() {
		return model;
	}

	/**
	 * @return the modelPk
	 */
	public FcrChAcctMastPK getModelPk() {
		return modelPk;
	}

	/**
	 * @param modelPk the modelPk to set
	 */
	public void setModelPk(FcrChAcctMastPK modelPk) {
		this.modelPk = modelPk;
	}

	/**
	 * @return the modelFCR
	 */
	public ChAcctMast getModelFCR() {
		return modelFCR;
	}

	/**
	 * @param modelFCR the modelFCR to set
	 */
	public void setModelFCR(ChAcctMast modelFCR) {
		this.modelFCR = modelFCR;
	}

	/**
	 * @return the modelPKfcr
	 */
	public ChAcctMastPK getModelPKfcr() {
		return modelPKfcr;
	}

	/**
	 * @param modelPKfcr the modelPKfcr to set
	 */
	public void setModelPKfcr(ChAcctMastPK modelPKfcr) {
		this.modelPKfcr = modelPKfcr;
	}
}
