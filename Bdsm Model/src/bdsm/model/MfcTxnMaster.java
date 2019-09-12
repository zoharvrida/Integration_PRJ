/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;

/**
 *
 * @author user
 */
public class MfcTxnMaster extends BaseModel {
    private MfcTxnMasterPK compositeId;
    private Double amtAvailUsd;
    
    /**
     * @return the compositeId
     */
    public MfcTxnMasterPK getCompositeId() {
        return compositeId;
    }
    
    /**
     * @param compositeId the compositeId to set
     */
    public void setCompositeId(MfcTxnMasterPK compositeId) {
        this.compositeId = compositeId;
    }    
    
    /**
    * @return the amtAvailUsd
    */
    public Double getAmtAvailUsd() {
        return amtAvailUsd;
    }

    /**
     * @param amtAvailUsd the amtAvailUsd to set
     */
    public void setAmtAvailUsd(Double amtAvailUsd) {
        this.amtAvailUsd = amtAvailUsd;
    }
}