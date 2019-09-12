/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.fcr.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author v00022309
 */
public class CytmRates implements Serializable{
    private CytmRatesPK compositeId;
    private BigDecimal buyRate; 
    private Double midRate;
    private Double sellRate;

    /**
     * @return the compositeId
     */
    public CytmRatesPK getCompositeId() {
        return compositeId;
    }

    /**
     * @param compositeId the compositeId to set
     */
    public void setCompositeId(CytmRatesPK compositeId) {
        this.compositeId = compositeId;
    }

    /**
     * @return the buyRates
     */
    public BigDecimal getBuyRate() {
        return buyRate;
    }

    /**
     * @param buyRates the buyRates to set
     */
    public void setBuyRate(BigDecimal buyRates) {
        this.buyRate = buyRates;
    }

    /**
     * @return the midRate
     */
    public Double getMidRate() {
        return midRate;
    }

    /**
     * @param midRate the midRate to set
     */
    public void setMidRate(Double midRate) {
        this.midRate = midRate;
    }

    /**
     * @return the sellRate
     */
    public Double getSellRate() {
        return sellRate;
    }

    /**
     * @param sellRate the sellRate to set
     */
    public void setSellRate(Double sellRate) {
        this.sellRate = sellRate;
    }

    @Override
    public String toString() {
        return "CytmRates{" + "compositeId=" + compositeId + ", buyRate=" + buyRate + ", midRate=" + midRate + ", sellRate=" + sellRate + '}';
    }

    
    
}
