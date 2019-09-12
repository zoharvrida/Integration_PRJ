/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;

/**
 *
 * @author v00020800
 */
public class FcrBaCcBrnMast {
        private FcrBaCcBrnMastPK compositeId;
    private String namBranch;
    private String namBranchShrt;

 

        public FcrBaCcBrnMast() {
        }

    /**
     * @return the compositeId
     */
    public FcrBaCcBrnMastPK getCompositeId() {
        return compositeId;
    }

    /**
     * @param compositeId the compositeId to set
     */
    public void setCompositeId(FcrBaCcBrnMastPK compositeId) {
        this.compositeId = compositeId;
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
     * @return the namBranchShrt
     */
    public String getNamBranchShrt() {
        return namBranchShrt;
    }

    /**
     * @param namBranchShrt the namBranchShrt to set
     */
    public void setNamBranchShrt(String namBranchShrt) {
        this.namBranchShrt = namBranchShrt;
    }
    }

