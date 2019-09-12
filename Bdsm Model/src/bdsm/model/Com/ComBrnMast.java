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
public class ComBrnMast extends BaseModel {
    private String idBrn;
    private String namBranch;
    private String branchType;
    private String idBranchParent;
    private String remarks;
    private Set<ComVendorMast> xref = new HashSet<ComVendorMast>();

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
     * @return the namBranch
     */
    public String getNamBranch() {
        return namBranch;
    }

    /**
     * @param namBranch the namBranch to set
     */
    public void setNamBranch(String namBranch) {
        this.namBranch = namBranch;
    }

    /**
     * @return the branchType
     */
    public String getBranchType() {
        return branchType;
    }

    /**
     * @param branchType the branchType to set
     */
    public void setBranchType(String branchType) {
        this.branchType = branchType;
    }

    /**
     * @return the idBranchParent
     */
    public String getIdBranchParent() {
        return idBranchParent;
    }

    /**
     * @param idBranchParent the idBranchParent to set
     */
    public void setIdBranchParent(String idBranchParent) {
        this.idBranchParent = idBranchParent;
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
    public Set<ComVendorMast> getXref() {
        return xref;
    }

    /**
     * @param xref the xref to set
     */
    public void setXref(Set<ComVendorMast> xref) {
        this.xref = xref;
    }
}
