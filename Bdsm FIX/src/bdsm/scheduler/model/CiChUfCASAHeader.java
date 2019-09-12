/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

import bdsm.model.BaseModel;
import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author v00019722
 */
public class CiChUfCASAHeader extends BaseModel {   
    private Integer recordId;
    private Integer recID;
    private String batchId;
    private String recType;
    private String fileName;
    private Integer recCount;
    private String comments;
    private String data;
    private Integer respCode;
    private Date fileDate;
    private String flgUpdate;
    private Timestamp dtmReceive;

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

    /**
     * @return the respCode
     */
    public Integer getRespCode() {
        return respCode;
    }

    /**
     * @param respCode the respCode to set
     */
    public void setRespCode(Integer respCode) {
        this.respCode = respCode;
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
     * @return the recCount
     */
    public Integer getRecCount() {
        return recCount;
    }

    /**
     * @param recCount the recCount to set
     */
    public void setRecCount(Integer recCount) {
        this.recCount = recCount;
    }

    /**
     * @return the fileDate
     */
    public Date getFileDate() {
        return fileDate;
    }

    /**
     * @param fileDate the fileDate to set
     */
    public void setFileDate(Date fileDate) {
        this.fileDate = fileDate;
    }

    /**
     * @return the flgUpdate
     */
    public String getFlgUpdate() {
        return flgUpdate;
    }

    /**
     * @param flgUpdate the flgUpdate to set
     */
    public void setFlgUpdate(String flgUpdate) {
        this.flgUpdate = flgUpdate;
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return the recID
     */
    public Integer getRecID() {
        return recID;
    }

    /**
     * @param recID the recID to set
     */
    public void setRecID(Integer recID) {
        this.recID = recID;
    }

    /**
     * @return the dtmReceive
     */
    public Timestamp getDtmReceive() {
        return dtmReceive;
    }

    /**
     * @param dtmReceive the dtmReceive to set
     */
    public void setDtmReceive(Timestamp dtmReceive) {
        this.dtmReceive = dtmReceive;
    }
}
