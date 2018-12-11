/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.fcr.model;

import java.io.Serializable;

/**
 *
 * @author v00022309
 */
public class CytmRatesPK implements Serializable {
    private String codCcy1;        
	private String codCcy2 = "IDR";                   
	private String branchCodes = "ALL";  
	private String rateType;

    /**
     * @return the codCcy1
     */
    public String getCodCcy1() {
        return codCcy1;
    }

    /**
     * @param codCcy1 the codCcy1 to set
     */
    public void setCodCcy1(String codCcy1) {
        this.codCcy1 = codCcy1;
    }

    /**
     * @return the codCcy2
     */
    public String getCodCcy2() {
        return codCcy2;
    }

    /**
     * @param codCcy2 the codCcy2 to set
     */
    public void setCodCcy2(String codCcy2) {
        this.codCcy2 = "IDR";
    }

    /**
     * @return the branchCodes
     */
    public String getBranchCodes() {
        return branchCodes;
    }

    /**
     * @param branchCodes the branchCodes to set
     */
    public void setBranchCodes(String branchCodes) {
        this.branchCodes = "ALL";
    }

    /**
     * @return the rateType
     */
    public String getRateType() {
        return rateType;
    }

    /**
     * @param rateType the rateType to set
     */
    public void setRateType(String rateType) {
        this.rateType = rateType;
    }
    
}
