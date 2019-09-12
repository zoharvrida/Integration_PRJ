/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;

/**
 *
 * @author v00013493
 */
public class FcrBaCcyRate {
    private FcrBaCcyRatePK compositeId;
    private Double ratCcyMid;
    private Integer codLcy;
    
    /**
     * @return the compositeId
     */
    public FcrBaCcyRatePK getCompositeId() {
        return compositeId;
    }

    /**
     * @param compositeId the compositeId to set
     */
    public void setCompositeId(FcrBaCcyRatePK compositeId) {
        this.compositeId = compositeId;
    }
    
    /**
     * @return the ratCcyMid
     */
    public Double getRatCcyMid() {
        return ratCcyMid;
    }

    /**
     * @param ratCcyMid the ratCcyMid to set
     */
    public void setRatCcyMid(Double ratCcyMid) {
        this.ratCcyMid = ratCcyMid;
    }

    /**
     * @return the codLcy
     */
    public Integer getCodLcy() {
        return codLcy;
    }

    /**
     * @param codLcy the codLcy to set
     */
    public void setCodLcy(Integer codLcy) {
        this.codLcy = codLcy;
    }    
}
