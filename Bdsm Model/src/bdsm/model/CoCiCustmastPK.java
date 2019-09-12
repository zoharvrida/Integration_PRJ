/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author user
 */
public class CoCiCustmastPK implements Serializable {
    private String nik;
    private Date dateofBirth;
    private Integer codEntityVpd;
    private String flgMntStatus;

    /**
     * @return the nik
     */
    public String getNik() {
        return nik;
    }

    /**
     * @param nik the nik to set
     */
    public void setNik(String nik) {
        this.nik = nik;
    }

    /**
     * @return the dateofBirth
     */
    public Date getDateofBirth() {
        return dateofBirth;
    }

    /**
     * @param dateofBirth the dateofBirth to set
     */
    public void setDateofBirth(Date dateofBirth) {
        this.dateofBirth = dateofBirth;
    }

    /**
     * @return the codEntityVpd
     */
    public Integer getCodEntityVpd() {
        return codEntityVpd;
    }

    /**
     * @param codEntityVpd the codEntityVpd to set
     */
    public void setCodEntityVpd(Integer codEntityVpd) {
        this.codEntityVpd = codEntityVpd;
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
