/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.web;

import bdsm.model.FcyTxnFlagCIF;
import bdsmhost.dao.FcyTxnFlagCIFDao;
import java.util.List;

/**
 *
 * @author v00013493
 */
public class FcyTxnFlagCIFAction extends BaseHostAction {

    private static final long serialVersionUID = 5078264277068533593L;
    private List modelList;
    private FcyTxnFlagCIF model;
    private String idMenu;

    /**
     * Input: HTTP Request parameters: "model.acctNo"
     *
     * Output: JSON Object: modelList
     * @return 
     */
    public String doList() {
        getLogger().debug("IDMENU :" + idMenu);
        FcyTxnFlagCIFDao dao = new FcyTxnFlagCIFDao(getHSession());
        if ("28401".equalsIgnoreCase(idMenu)) {
            modelList = dao.list(model.getAcctNo());
        } else if ("20401".equalsIgnoreCase(idMenu)) {
            modelList = dao.list(model.getAcctNo(), model.getPeriod());
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
    public void setModel(FcyTxnFlagCIF model) {
        this.model = model;
    }

    /**
     * @return the model
     */
    public FcyTxnFlagCIF getModel() {
        return model;
    }

    /**
     * 
     * @return
     */
    public String getIdMenu() {
        return idMenu;
    }

    /**
     * 
     * @param idMenu
     */
    public void setIdMenu(String idMenu) {
        this.idMenu = idMenu;
    }
}
