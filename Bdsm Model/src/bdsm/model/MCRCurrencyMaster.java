/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;

/**
 *
 * @author v00022309
 */
public class MCRCurrencyMaster extends bdsm.model.BaseModel implements java.io.Serializable {

    private String currencyCode;
    private String currencyName;
    private String currencyDestination;
    
    

    /**
     * @return the currencyCode
     */
    public String getCurrencyCode() {
        return currencyCode;
    }

    /**
     * @param currencyCode the currencyCode to set
     */
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    /**
     * @return the currencyName
     */
    public String getCurrencyName() {
        return currencyName;
    }

    /**
     * @param currencyName the currencyName to set
     */
    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    /**
     * @return the currencyDestination
     */
    public String getCurrencyDestination() {
        return currencyDestination;
    }

    /**
     * @param currencyDestination the currencyDestination to set
     */
    public void setCurrencyDestination(String currencyDestination) {
        this.currencyDestination = currencyDestination;
    }
        
}
