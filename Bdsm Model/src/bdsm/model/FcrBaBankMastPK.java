/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;

import java.io.Serializable;

/**
 *
 * @author 00030663
 */
public class FcrBaBankMastPK implements Serializable {
    private int codBank;
    private String flgMntStatus;

    /**
     * @return the codBank
     */
    public int getCodBank() {
        return codBank;
    }

    /**
     * @param codBank the codBank to set
     */
    public void setCodBank(int codBank) {
        this.codBank = codBank;
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
