/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.web;

import bdsm.fcr.model.ChProdMast;
import bdsmhost.fcr.dao.ChProdMastDAO;

/**
 *
 * @author 00110310
 */
public class ChProdMastAction extends BaseHostAction {

    private ChProdMast model;
    private Integer prodNum;
    
    /**
     * 
     * @return
     */
    @Override
    public String exec() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    public String doGet() {
        getLogger().debug("COD_PROD :" + getProdNum());
        ChProdMastDAO dao = new ChProdMastDAO(getHSession());
        setModel(dao.getActiveProduct(getProdNum()));
        return SUCCESS;
    }

    /**
     * 
     * @return
     */
    @Override
    public String doSave() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * 
     * @return
     */
    @Override
    public String doInsert() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * 
     * @return
     */
    @Override
    public String doUpdate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * 
     * @return
     */
    @Override
    public String doDelete() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return the model
     */
    public ChProdMast getModel() {
        return model;
    }

    /**
     * @param model the model to set
     */
    public void setModel(ChProdMast model) {
        this.model = model;
    }

    /**
     * @return the prodNum
     */
    public Integer getProdNum() {
        return prodNum;
    }

    /**
     * @param prodNum the prodNum to set
     */
    public void setProdNum(Integer prodNum) {
        this.prodNum = prodNum;
    }
    
}
