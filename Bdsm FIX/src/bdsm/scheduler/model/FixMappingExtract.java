package bdsm.scheduler.model;

import bdsm.model.BaseModel;

/**
 * 
 * @author bdsm
 */
public class FixMappingExtract extends BaseModel{
    private FixMappingExtractPK fixMappingExtractPK;
    private String responseFilename;

    /**
     * @return the fixMappingExtractPK
     */
    public FixMappingExtractPK getFixMappingExtractPK() {
        return fixMappingExtractPK;
    }

    /**
     * @param fixMappingExtractPK the fixMappingExtractPK to set
     */
    public void setFixMappingExtractPK(FixMappingExtractPK fixMappingExtractPK) {
        this.fixMappingExtractPK = fixMappingExtractPK;
    }

    /**
     * @return the responseFilename
     */
    public String getResponseFilename() {
        return responseFilename;
    }

    /**
     * @param responseFilename the responseFilename to set
     */
    public void setResponseFilename(String responseFilename) {
        this.responseFilename = responseFilename;
    }
    
}
