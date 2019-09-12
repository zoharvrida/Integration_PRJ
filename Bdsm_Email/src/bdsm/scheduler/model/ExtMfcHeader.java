/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

import bdsm.model.BaseModel;

/**
 *
 * @author v00013493
 */
public class ExtMfcHeader extends BaseModel {
    private Long recordId;
    private String batchId;
    private String recType;
    private String srcSystem;
    private String noBatch;
    private String recCount;
    private String respCode;
    private String comments;
    private String data;
    
    /**
     * @return the recordId
     */
    public Long getRecordId() {
        return recordId;
    }

    /**
     * @param recordId the recordId to set
     */
    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    /**
     * @return the batchId
     */
    public String getBatchId() {
        return batchId;
    }

    /**
     * @param batchId the batchId to set
     */
    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }
    
    /**
     * @return the recType
     */
    public String getRecType() {
        return recType;
    }

    /**
     * @param recType the recType to set
     */
    public void setRecType(String recType) {
        this.recType = recType;
    }
    
    /**
     * @return the srcSystem
     */
    public String getSrcSystem() {
        return srcSystem;
    }

    /**
     * @param srcSystem the srcSystem to set
     */
    public void setSrcSystem(String srcSystem) {
        this.srcSystem = srcSystem;
    }
    
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
     * @return the recCount
     */
    public String getRecCount() {
        return recCount;
    }

    /**
     * @param recCount the recCount to set
     */
    public void setRecCount(String recCount) {
        this.recCount = recCount;
    }
    
    /**
     * @return the respCode
     */
    public String getRespCode() {
        return respCode;
    }

    /**
     * @param respCode the respCode to set
     */
    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }
    
    /**
     * @return the comments
     */
    public String getComments() {
        return comments;
    }

    /**
     * @param comments the comments to set
     */
    public void setComments(String comments) {
        this.comments = comments;
    }
    
    /**
     * @return the data
     */
    public String getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(String data) {
        this.data = data;
    }
}
