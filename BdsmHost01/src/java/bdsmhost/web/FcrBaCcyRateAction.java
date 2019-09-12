/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.web;

import bdsm.model.FcrBaCcyCode;
import bdsm.model.FcrBaCcyRate;
import bdsmhost.dao.FcrBaCcyCodeDao;
import bdsmhost.dao.FcrBaCcyRateDao;
import bdsmhost.dao.FcrBaCcyRateExtDAO;

import java.util.List;

/**
 *
 * @author v00013493
 */
public class FcrBaCcyRateAction extends BaseHostAction {

    private static final long serialVersionUID = 5078264277068533593L;

    private List modelList;
    private FcrBaCcyRate model;
    private FcrBaCcyCode modelCode;

    /**
     * Input: HTTP Request parameters: ""
     *
     * Output: JSON Object: model
     * @return 
     */
    public String doGet() {
        FcrBaCcyRateDao dao = new FcrBaCcyRateExtDAO(getHSession());
        FcrBaCcyCodeDao daoCode = new FcrBaCcyCodeDao(getHSession());
        String namCcyShort;
        try {
            namCcyShort = modelCode.getNamCcyShort();
            modelCode = daoCode.get(namCcyShort);
        } catch (NullPointerException ne) {
            namCcyShort = "";
        }
        if ("".equals(namCcyShort)) {
            model = dao.get();
        }else{
            model = dao.get(modelCode.getCompositeId().getCodCcy());
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
    public String doList() {
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
    public void setModel(FcrBaCcyRate model) {
        this.model = model;
    }

    /**
     * @return the model
     */
    public FcrBaCcyRate getModel() {
        return model;
    }

    /**
     * @return the modelCode
     */
    public FcrBaCcyCode getModelCode() {
        return modelCode;
    }

    /**
     * @param modelCode the modelCode to set
     */
    public void setModelCode(FcrBaCcyCode modelCode) {
        this.modelCode = modelCode;
    }
}
