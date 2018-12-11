/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

import java.io.Serializable;

/**
 *
 * @author bdsm
 */
public class FixMappingExtractPK implements Serializable{
    private String noBatch;
    private String extractFilename;

    /**
     * @return the noBatch
     */
    public String getNoBatch() {
        return noBatch;
    }

    /**
     * @param noBatch the noBatch to set
     */
    public void setNoBatch(String noBatch) {
        this.noBatch = noBatch;
    }

    /**
     * @return the extractFilename
     */
    public String getExtractFilename() {
        return extractFilename;
    }

    /**
     * @param extractFilename the extractFilename to set
     */
    public void setExtractFilename(String extractFilename) {
        this.extractFilename = extractFilename;
    }


}
