/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author v00013493
 */
public class FcrBaCcyRatePK implements Serializable {
    private Integer codCcy;
    private Timestamp datTimRateEff;
    private String flgMntStatus;
    
    /**
     * @return the codCcy
     */
    public Integer getCodCcy() {
        return codCcy;
    }

    /**
     * @param codCcy the codCcy to set
     */
    public void setCodCcy(Integer codCcy) {
        this.codCcy = codCcy;
    }

    /**
     * @return the datTimRateEff
     */
    public Timestamp getDatTimRateEff() {
        return datTimRateEff;
    }

    /**
     * @param datTimRateEff the datTimRateEff to set
     */
    public void setDatTimRateEff(Timestamp datTimRateEff) {
        this.datTimRateEff = datTimRateEff;
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
