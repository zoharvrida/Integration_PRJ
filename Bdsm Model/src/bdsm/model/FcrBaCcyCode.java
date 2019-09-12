/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;

/**
 *
 * @author user
 */
public class FcrBaCcyCode {
    private FcrBaCcyCodePK compositeId;
    private String namCcyShort;
    private String namCurrency;
    
    /**
     * @return the compositeId
     */
    public FcrBaCcyCodePK getCompositeId() {
        return compositeId;
    }

    /**
     * @param compositeId the compositeId to set
     */
    public void setCompositeId(FcrBaCcyCodePK compositeId) {
        this.compositeId = compositeId;
    }
    
    /**
     * @return the namCcyShort
     */
    public String getNamCcyShort() {
        return namCcyShort;
    }

    /**
     * @param namCcyShort the namCcyShort to set
     */
    public void setNamCcyShort(String namCcyShort) {
        this.namCcyShort = namCcyShort;
    }

    /**
     * @return the namCurrency
     */
    public String getNamCurrency() {
        return namCurrency;
    }

    /**
     * @param namCurrency the namCurrency to set
     */
    public void setNamCurrency(String namCurrency) {
        this.namCurrency = namCurrency;
    }
}