/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

import bdsm.model.BaseModel;

/**
 *
 * @author v00018192
 */
@SuppressWarnings("serial")
public class AmtHldHeader extends BaseModel {
	  
    private AmtHldKey compositeId;
    // private String compositeId;
    private int rectype;
    private String filedate;
    private String recordtype;
    private String recordname;
    private String data;
    private int length;
    private String comments;
    private String recordstatus;
    private int parentrecid;
    private String FlgHold;

    /**
     * @return the compositeId
     */
    public AmtHldKey getCompositeId() {
        return compositeId;
    }

    /**
     * @param compositeId the compositeId to set
     */
    public void setCompositeId(AmtHldKey compositeId) {
        this.compositeId = compositeId;
    }

    /**
     * @return the rectype
     */
    public int getRectype() {
        return rectype;
    }

    /**
     * @param rectype the rectype to set
     */
    public void setRectype(int rectype) {
        this.rectype = rectype;
    }

    /**
     * @return the filedate
     */
    public String getFiledate() {
        return filedate;
    }

    /**
     * @param filedate the filedate to set
     */
    public void setFiledate(String filedate) {
        this.filedate = filedate;
    }

    /**
     * @return the recordtype
     */
    public String getRecordtype() {
        return recordtype;
    }

    /**
     * @param recordtype the recordtype to set
     */
    public void setRecordtype(String recordtype) {
        this.recordtype = recordtype;
    }

    /**
     * @return the recordname
     */
    public String getRecordname() {
        return recordname;
    }

    /**
     * @param recordname the recordname to set
     */
    public void setRecordname(String recordname) {
        this.recordname = recordname;
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
     * @return the length
     */
    public int getLength() {
        return length;
    }

    /**
     * @param length the length to set
     */
    public void setLength(int length) {
        this.length = length;
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
     * @return the recordstatus
     */
    public String getRecordstatus() {
        return recordstatus;
    }

    /**
     * @param recordstatus the recordstatus to set
     */
    public void setRecordstatus(String recordstatus) {
        this.recordstatus = recordstatus;
    }

    /**
     * @return the parentrecid
     */
    public int getParentrecid() {
        return parentrecid;
    }

    /**
     * @param parentrecid the parentrecid to set
     */
    public void setParentrecid(int parentrecid) {
        this.parentrecid = parentrecid;
    }

    /**
     * @return the FlgHold
     */
    public String getFlgHold() {
        return FlgHold;
    }

    /**
     * @param FlgHold the FlgHold to set
     */
    public void setFlgHold(String FlgHold) {
        this.FlgHold = FlgHold;
    }
}
