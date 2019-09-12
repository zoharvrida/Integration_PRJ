/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;

import java.io.Serializable;

/**
 *
 * @author v00019722
 */
public class SknngInOutDebitPK implements Serializable {
    private String fileId;
    private Integer recordId;
    private Integer parentId;
    private Integer parentRecordId;

    /**
     * @return the fileId
     */
    public String getFileId() {
        return fileId;
    }

    /**
     * @param fileId the fileId to set
     */
    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    /**
     * @return the recordId
     */
    public Integer getRecordId() {
        return recordId;
    }

    /**
     * @param recordId the recordId to set
     */
    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    /**
     * @return the parentId
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * @param parentId the parentId to set
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * @return the parentRecordId
     */
    public Integer getParentRecordId() {
        return parentRecordId;
    }

    /**
     * @param parentRecordId the parentRecordId to set
     */
    public void setParentRecordId(Integer parentRecordId) {
        this.parentRecordId = parentRecordId;
    }
}
