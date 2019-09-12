/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

import bdsm.model.BaseModel;
import java.sql.Timestamp;

/**
 *
 * @author v00019722
 */
public class TmpchHoldMntHeader extends BaseModel{
    private TmpchHoldMntPK compositeId;
    private String profileName;
    private Integer rectype;
    private String filedate;
    private String flghold;
    private String recordtype;
    private String recordname;
    private String data;
    private Integer length;
    private String comments;
    private String gefustatus;
    private Timestamp dtmProcStart;
    private Timestamp dtmProcFinish;
    private String validationstatus;

	/**
	 * @return the compositeId
	 */
	public TmpchHoldMntPK getCompositeId() {
		return compositeId;
	}

	/**
	 * @param compositeId the compositeId to set
	 */
	public void setCompositeId(TmpchHoldMntPK compositeId) {
		this.compositeId = compositeId;
	}

	/**
	 * @return the rectype
	 */
	public Integer getRectype() {
		return rectype;
	}

	/**
	 * @param rectype the rectype to set
	 */
	public void setRectype(Integer rectype) {
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
	 * @return the flghold
	 */
	public String getFlghold() {
		return flghold;
	}

	/**
	 * @param flghold the flghold to set
	 */
	public void setFlghold(String flghold) {
		this.flghold = flghold;
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
	public Integer getLength() {
		return length;
	}

	/**
	 * @param length the length to set
	 */
	public void setLength(Integer length) {
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
	 * @return the gefustatus
	 */
	public String getGefustatus() {
		return gefustatus;
	}

	/**
	 * @param gefustatus the gefustatus to set
	 */
	public void setGefustatus(String gefustatus) {
		this.gefustatus = gefustatus;
	}

	/**
	 * @return the dtmProcStart
	 */
	public Timestamp getDtmProcStart() {
		return dtmProcStart;
	}

	/**
	 * @param dtmProcStart the dtmProcStart to set
	 */
	public void setDtmProcStart(Timestamp dtmProcStart) {
		this.dtmProcStart = dtmProcStart;
	}

	/**
	 * @return the dtmProcFinish
	 */
	public Timestamp getDtmProcFinish() {
		return dtmProcFinish;
	}

	/**
	 * @param dtmProcFinish the dtmProcFinish to set
	 */
	public void setDtmProcFinish(Timestamp dtmProcFinish) {
		this.dtmProcFinish = dtmProcFinish;
	}

	/**
	 * @return the validationstatus
	 */
	public String getValidationstatus() {
		return validationstatus;
	}

	/**
	 * @param validationstatus the validationstatus to set
	 */
	public void setValidationstatus(String validationstatus) {
		this.validationstatus = validationstatus;
	}
  
    /**
     * @return the profileName
     */
    public String getProfileName() {
        return profileName;
    }

    /**
     * @param profileName the profileName to set
     */
    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }
  
}
