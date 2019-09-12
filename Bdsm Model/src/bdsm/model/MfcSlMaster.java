/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;

/**
 *
 * @author user
 */
public class MfcSlMaster extends BaseModel {
    private MfcSlMasterPK compositeId;
    private Integer cdBranch;
    
    /**
     * @return the compositeId
     */
    public MfcSlMasterPK getCompositeId() {
        return compositeId;
    }
    
    /**
     * @param compositeId the compositeId to set
     */
    public void setCompositeId(MfcSlMasterPK compositeId) {
        this.compositeId = compositeId;
    }
    
    /**
    * @return the cdBranch
    */
    public Integer getCdBranch() {
        return cdBranch;
    }

    /**
     * @param cdBranch the cdBranch to set
     */
    public void setCdBranch(Integer cdBranch) {
        this.cdBranch = cdBranch;
    }
}
