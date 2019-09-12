/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model.Com;

import bdsm.model.BaseModel;

/**
 *
 * @author SDM
 */
public class ComBrnVendorXref extends BaseModel {
    private String idBrn;
    private String vendorId;
    private String flgDefault;

    /**
     * @return the idBrn
     */
    public String getIdBrn() {
        return idBrn;
    }

    /**
     * @param idBrn the idBrn to set
     */
    public void setIdBrn(String idBrn) {
        this.idBrn = idBrn;
    }

    /**
     * @return the vendorId
     */
    public String getVendorId() {
        return vendorId;
    }

    /**
     * @param vendorId the vendorId to set
     */
    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    /**
     * @return the flgDefault
     */
    public String getFlgDefault() {
        return flgDefault;
    }

    /**
     * @param flgDefault the flgDefault to set
     */
    public void setFlgDefault(String flgDefault) {
        this.flgDefault = flgDefault;
    }
}
