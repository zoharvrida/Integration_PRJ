/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;

import java.io.Serializable;

/**
 *
 * @author v00020800
 */
class FcrBaCcBrnMastPK implements Serializable{
    private int codCcBrn;
    private String flgMntStatus;
        private int codEntityVpd;

    /**
     * @return the codCcBrn
     */
    public int getCodCcBrn() {
        return codCcBrn;
    }

    /**
     * @param codCcBrn the codCcBrn to set
     */
    public void setCodCcBrn(int codCcBrn) {
        this.codCcBrn = codCcBrn;
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

    /**
     * @return the codEntityVpd
     */
    public int getCodEntityVpd() {
        return codEntityVpd;
    }

    /**
     * @param codEntityVpd the codEntityVpd to set
     */
    public void setCodEntityVpd(int codEntityVpd) {
        this.codEntityVpd = codEntityVpd;
    }



}
