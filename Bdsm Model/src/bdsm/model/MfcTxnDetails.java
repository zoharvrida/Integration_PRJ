/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;

/**
 *
 * @author v00013493
 */
public class MfcTxnDetails extends BaseModel {
    private MfcTxnDetailsPK compositeId;
    private Integer noCif;
    private Integer period;
    private String noUd;
    private String status;
            
    /**
     * @return the compositeId
     */
    public MfcTxnDetailsPK getCompositeId() {
        return compositeId;
    }
    
    /**
     * @param compositeId the compositeId to set
     */
    public void setCompositeId(MfcTxnDetailsPK compositeId) {
        this.compositeId = compositeId;
    }
    
    /**
    * @return the noCif
    */
    public Integer getNoCif() {
        return noCif;
    }

    /**
     * @param noCif the noCif to set
     */
    public void setNoCif(Integer noCif) {
        this.noCif = noCif;
    }

    /**
    * @return the period
    */
    public Integer getPeriod() {
        return period;
    }

    /**
     * @param period the period to set
     */
    public void setPeriod(Integer period) {
        this.period = period;
    }
    
    /**
    * @return the noUd
    */
    public String getNoUd() {
        return noUd;
    }

    /**
     * @param noUd the noUd to set
     */
    public void setNoUd(String noUd) {
        this.noUd = noUd;
    }
    
    /**
    * @return the status
    */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }    
}
