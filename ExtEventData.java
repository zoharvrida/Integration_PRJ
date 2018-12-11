/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.fcr.model;


/**
 * 
 * @author v00019372
 */
public class ExtEventData {
	private Integer eventId;
	private String eventType;
	private Integer postingDate;
	private java.util.Date systemDate;

	
	/**
	 * @return the eventId
	 */
	public Integer getEventId() {
		return this.eventId;
	}

	/**
	 * @param eventId the eventId to set
	 */
	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	/**
	 * @return the eventType
	 */
	public String getEventType() {
		return this.eventType;
	}

	/**
	 * @param eventType the eventType to set
	 */
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	/**
	 * @return the postingDate
	 */
	public Integer getPostingDate() {
		return this.postingDate;
	}

	/**
	 * @param postingDate the postingDate to set
	 */
	public void setPostingDate(Integer postingDate) {
		this.postingDate = postingDate;
	}

	/**
	 * @return the systemDate
	 */
	public java.util.Date getSystemDate() {
		return this.systemDate;
	}

	/**
	 * @param systemDate the systemDate to set
	 */
	public void setSystemDate(java.util.Date systemDate) {
		this.systemDate = systemDate;
	}

}
