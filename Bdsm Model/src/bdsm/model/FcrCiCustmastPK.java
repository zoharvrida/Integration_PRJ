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
public class FcrCiCustmastPK implements Serializable {
    private Integer codCustId;
    private String flgMntStatus;

    /**
     * @return the codCustId
     */
    public Integer getCodCustId() {
        return codCustId;
    }

    /**
     * @param codCustId the codCustId to set
     */
    public void setCodCustId(Integer codCustId) {
        this.codCustId = codCustId;
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
