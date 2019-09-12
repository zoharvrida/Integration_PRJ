/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

import bdsm.model.BaseModel;

/**
 *
 * @author v00019722
 */
public class SKNIncomingCreditFooter extends BaseModel  {
	private SknNgIncomingCreditFPK compositeId;
	private Integer TotalRec;
	private String checkSum;
	private String recordType;
	private String recordName;
	private String data;
	private Integer length;
	private String comments;
	private String recordStatus;
	private Integer codEntityVpd;

	/**
	 * @return the compositeId
	 */
	public SknNgIncomingCreditFPK getCompositeId() {
		return compositeId;
	}

	/**
	 * @param compositeId the compositeId to set
	 */
	public void setCompositeId(SknNgIncomingCreditFPK compositeId) {
		this.compositeId = compositeId;
	}

	/**
	 * @return the TotalRec
	 */
	public Integer getTotalRec() {
		return TotalRec;
	}

	/**
	 * @param TotalRec the TotalRec to set
	 */
	public void setTotalRec(Integer TotalRec) {
		this.TotalRec = TotalRec;
	}

	/**
	 * @return the checkSum
	 */
	public String getCheckSum() {
		return checkSum;
	}

	/**
	 * @param checkSum the checkSum to set
	 */
	public void setCheckSum(String checkSum) {
		this.checkSum = checkSum;
	}

	/**
	 * @return the recordType
	 */
	public String getRecordType() {
		return recordType;
	}

	/**
	 * @param recordType the recordType to set
	 */
	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}

	/**
	 * @return the recordName
	 */
	public String getRecordName() {
		return recordName;
	}

	/**
	 * @param recordName the recordName to set
	 */
	public void setRecordName(String recordName) {
		this.recordName = recordName;
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
	 * @return the recordStatus
	 */
	public String getRecordStatus() {
		return recordStatus;
	}

	/**
	 * @param recordStatus the recordStatus to set
	 */
	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}

	/**
	 * @return the codEntityVpd
	 */
	public Integer getCodEntityVpd() {
		return codEntityVpd;
	}

	/**
	 * @param codEntityVpd the codEntityVpd to set
	 */
	public void setCodEntityVpd(Integer codEntityVpd) {
		this.codEntityVpd = codEntityVpd;
	}
}
