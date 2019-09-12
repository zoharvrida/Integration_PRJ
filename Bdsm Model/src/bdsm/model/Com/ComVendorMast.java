/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model.Com;

import bdsm.model.BaseModel;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author SDM
 */
public class ComVendorMast extends BaseModel{
    private String vendorId;
    private String vendorName;
    private String remarks;
    private Set<ComBrnMast> xref = new HashSet<ComBrnMast>();

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
     * @return the vendorName
     */
    public String getVendorName() {
        return vendorName;
    }

    /**
     * @param vendorName the vendorName to set
     */
    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    /**
     * @return the remarks
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * @param remarks the remarks to set
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * @return the xref
     */
    public Set<ComBrnMast> getXref() {
        return xref;
    }

    /**
     * @param xref the xref to set
     */
    public void setXref(Set<ComBrnMast> xref) {
        this.xref = xref;
    }
}
