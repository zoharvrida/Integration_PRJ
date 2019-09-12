/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;

import bdsm.model.BaseModel;


/**
 * 
 * @author v00019237
 */
public class HostSknngInOutCreditFooter extends BaseModel implements java.io.Serializable {
	private HostSknngInOutCreditPK compositeId;
	private Integer parentRecordId;
	private String tipeRecord;
	private String kodeDke;
	private String crc;
	private String type;
	private String extractStatus;
	private String message;
	

	public HostSknngInOutCreditPK getCompositeId() {
		return this.compositeId;
	}
	public void setCompositeId(HostSknngInOutCreditPK compositeId) {
		this.compositeId = compositeId;
	}

	public Integer getParentRecordId() {
		return this.parentRecordId;
	}
	public void setParentRecordId(Integer parentRecordId) {
		this.parentRecordId = parentRecordId;
	}

	public String getTipeRecord() {
		return this.tipeRecord;
	}
	public void setTipeRecord(String tipeRecord) {
		this.tipeRecord = tipeRecord;
	}

	public String getKodeDke() {
		return this.kodeDke;
	}
	public void setKodeDke(String kodeDke) {
		this.kodeDke = kodeDke;
	}

	public String getCrc() {
		return this.crc;
	}
	public void setCrc(String crc) {
		this.crc = crc;
	}

	public String getType() {
		return this.type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getExtractStatus() {
		return this.extractStatus;
	}
	public void setExtractStatus(String extractStatus) {
		this.extractStatus = extractStatus;
	}

	public String getMessage() {
		return this.message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
