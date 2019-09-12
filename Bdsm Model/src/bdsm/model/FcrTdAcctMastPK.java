/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;

import java.io.Serializable;

/**
 *
 * @author user
 */
public class FcrTdAcctMastPK implements Serializable {
    private String codAcctNo;
    private String flgMntStatus;
    
    /**
     * @return the codAcctNo
     */
    public String getCodAcctNo() {
        return codAcctNo;
    }

    /**
     * @param codAcctNo the codAcctNo to set
     */
    public void setCodAcctNo(String codAcctNo) {
        this.codAcctNo = codAcctNo;
    }
    
    /**
     * @return the flgMntStatus
     */
    public String getFlgMntStatus() {
        return flgMntStatus;
    }

    /**
     * @param flgMntStatus the flgMntStatus to set
     */
    public void setFlgMntStatus(String flgMntStatus) {
        this.flgMntStatus = flgMntStatus;
    }
}
