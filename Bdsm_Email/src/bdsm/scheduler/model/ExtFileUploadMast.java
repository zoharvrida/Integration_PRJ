/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

import bdsm.model.BaseModel;

/**
 *
 * @author NCBS
 */
public class ExtFileUploadMast extends BaseModel{
    private String codFileId;
    private String fileType;
    private String codFileStatus;

    /**
     * @return the codFileId
     */
    public String getCodFileId() {
        return codFileId;
    }

    /**
     * @param codFileId the codFileId to set
     */
    public void setCodFileId(String codFileId) {
        this.codFileId = codFileId;
    }

    /**
     * @return the fileType
     */
    public String getFileType() {
        return fileType;
    }

    /**
     * @param fileType the fileType to set
     */
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    /**
     * @return the codFileStatus
     */
    public String getCodFileStatus() {
        return codFileStatus;
    }

    /**
     * @param codFileStatus the codFileStatus to set
     */
    public void setCodFileStatus(String codFileStatus) {
        this.codFileStatus = codFileStatus;
    }
}
