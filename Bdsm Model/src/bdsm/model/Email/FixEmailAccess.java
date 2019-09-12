package bdsm.model.Email;

import bdsm.model.BaseModel;

public class FixEmailAccess extends BaseModel{
    private FixEmailAccessPK fixEmailAccessPK;
    private String grpId;
    private String cdAccess;

    /**
     * @return the grpId
     */
    public String getGrpId() {
        return grpId;
    }

    /**
     * @param grpId the grpId to set
     */
    public void setGrpId(String grpId) {
        this.grpId = grpId;
    }

    /**
     * @return the cdAccess
     */
    public String getCdAccess() {
        return cdAccess;
    }

    /**
     * @param cdAccess the cdAccess to set
     */
    public void setCdAccess(String cdAccess) {
        this.cdAccess = cdAccess;
    }

    /**
     * @return the fixEmailAccessPK
     */
    public FixEmailAccessPK getFixEmailAccessPK() {
        return fixEmailAccessPK;
    }

    /**
     * @param fixEmailAccessPK the fixEmailAccessPK to set
     */
    public void setFixEmailAccessPK(FixEmailAccessPK fixEmailAccessPK) {
        this.fixEmailAccessPK = fixEmailAccessPK;
    }
    
}