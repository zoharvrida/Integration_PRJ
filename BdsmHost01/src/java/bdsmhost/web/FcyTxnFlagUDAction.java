/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.web;


import java.math.BigDecimal;
import java.util.List;

import bdsm.model.FcyTxnFlagUD;
import bdsm.model.MfcUdMasterPK;
import bdsm.model.MfcUdMaster_LLD;
import bdsm.scheduler.PropertyPersister;
import bdsmhost.dao.FcyTxnFlagUDDao;
import bdsmhost.dao.MfcUdMaster_LLDDAO;


/**
 *
 * @author v00013493
 */
public class FcyTxnFlagUDAction extends BaseHostAction {
    private static final long serialVersionUID = 5078264277068533593L;

    private List modelList;
    private FcyTxnFlagUD model;
    private String datTransaction;
    private String dateTXN;
    private String idMenu;
    private String noAcct;

    /**
     * Input:
     * HTTP Request parameters: "model.noCif, model.ccyUd, model.noUd, model.amtTxn"
     * 
     * Output:
     * JSON Object: modelList
     * @return 
     */ 
    public String doList() {
        this.getLogger().info("LLDDao menu : " + this.idMenu);
        this.getLogger().debug("LLD Menu :" + this.model);
        
        FcyTxnFlagUDDao dao = new FcyTxnFlagUDDao(getHSession());
        MfcUdMaster_LLDDAO daoLLD = new MfcUdMaster_LLDDAO(getHSession());
        
        if (idMenu.equals("20401"))
            this.modelList = dao.list(this.model.getNoCif(), this.model.getCcyUd(), this.model.getNoUd(), this.model.getAmtTxn());
        else {
            try {
                BigDecimal equivUSD = new BigDecimal(this.model.getAmtTxn());
                this.getLogger().debug("USD :" + equivUSD);
                if (this.model.getNoCif() != null) {
                    this.modelList = daoLLD.listByCifNo(this.model.getNoCif(), this.noAcct, equivUSD, null);
                }
                MfcUdMasterPK pk = new MfcUdMasterPK();
                pk.setNoCif(0);
                pk.setNoUd(PropertyPersister.LLDDummy);
                MfcUdMaster_LLD lld = daoLLD.getDummyUD(pk);
                
                this.modelList.add(lld);   
                dao.evictObjectFromPersistenceContext(lld);
            } catch (Exception e) {
                getLogger().info("LLDDao Info : " + e,e);
            }
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
        return this.modelList;
    }

    /**
     * @param model the model to set
     */
    public void setModel(FcyTxnFlagUD model) {
        this.model = model;
    }

    /**
     * @return the model
     */
    public FcyTxnFlagUD getModel() {
        return this.model;
    }
    
    /**
     * 
     * @return
     */
    public String getDatTransaction() {
        return this.datTransaction;
    }
    /**
     * 
     * @param datTransaction
     */
    public void setDatTransaction(String datTransaction) {
        this.datTransaction = datTransaction;
    }
    
    /**
     * 
     * @return
     */
    public String getDateTXN() {
        return this.dateTXN;
    }
    /**
     * 
     * @param dateTXN
     */
    public void setDateTXN(String dateTXN) {
        this.dateTXN = dateTXN;
    }
    
    /**
     * 
     * @return
     */
    public String getIdMenu() {
        return this.idMenu;
    }
    /**
     * 
     * @param idMenu
     */
    public void setIdMenu(String idMenu) {
        this.idMenu = idMenu;
    }
    
    /**
     * 
     * @return
     */
    public String getNoAcct() {
        return this.noAcct;
    }
    /**
     * 
     * @param noAcct
     */
    public void setNoAcct(String noAcct) {
        this.noAcct = noAcct;
    }
}