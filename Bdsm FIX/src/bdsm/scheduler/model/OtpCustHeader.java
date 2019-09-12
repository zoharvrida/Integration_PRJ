/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

import bdsm.model.BaseModel;
import java.sql.Timestamp;
import java.util.Date;
import org.hibernate.Session;

/**
 *
 * @author v00020800
 */
public class OtpCustHeader extends BaseModel {

    private String batchID;
    private Date createdDate;
    private Date finishDate;
    private String typeRecord;
    private String status;
    private Date fileDate;
    private Integer respCode;
	private Integer recID;
    private Date uploadDate;
    private Timestamp dtmReceive;

    /**
     * @return the batchID
     */
    public String getBatchID() {
        return batchID;
    }

    /**
     * @param batchID the batchID to set
     */
    public void setBatchID(String batchID) {
        this.batchID = batchID;
    }

    /**
     * @return the createdDate
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * @param createdDate the createdDate to set
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * @return the finishDate
     */
    public Date getFinishDate() {
        return finishDate;
    }

    /**
     * @param finishDate the finishDate to set
     */
    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    /**
     * @return the typeRecord
     */
    public String getTypeRecord() {
        return typeRecord;
    }

    /**
     * @param typeRecord the typeRecord to set
     */
    public void setTypeRecord(String typeRecord) {
        this.typeRecord = typeRecord;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
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
     * @return the uploadDate
     */
    public Date getUploadDate() {
        return uploadDate;
    }

    /**
     * @param uploadDate the uploadDate to set
     */
    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
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
