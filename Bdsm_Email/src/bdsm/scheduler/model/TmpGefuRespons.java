/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

import java.util.Date;


/**
 * 
 * @author NCBS
 */
public class TmpGefuRespons extends bdsm.model.BaseModel {
	private TmpGefuResponsPK compositeId;
	private String status;
	private String inboxid;
	private Date dateTimeRequest;
	private Date dateTimeFinish;
	private Integer idScheduler;
	private String moduleDesc;
	
	
	public TmpGefuRespons() {
		this.dateTimeRequest = new Date();
	}
	

	/**
	 * @return the compositeId
	 */
	public TmpGefuResponsPK getCompositeId() {
		return this.compositeId;
	}

	/**
	 * @param compositeId the compositeId to set
	 */
	public void setCompositeId(TmpGefuResponsPK compositeId) {
		this.compositeId = compositeId;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return this.status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the inboxid
	 */
	public String getInboxid() {
		return this.inboxid;
	}

	/**
	 * @param inboxid the inboxid to set
	 */
	public void setInboxid(String inboxid) {
		this.inboxid = inboxid;
	}
	
	/**
	 * @return the dateTimeRequest
	 */
	public Date getDateTimeRequest() {
		return this.dateTimeRequest;
	}
	
	/**
	 * @param dateTimeRequest the dateTimeRequest to set
	 */
	public void setDateTimeRequest(Date dateTimeRequest) {
		this.dateTimeRequest = dateTimeRequest;
	}
	
	/**
	 * @return the dateTimeFinish
	 */
	public Date getDateTimeFinish() {
		return this.dateTimeFinish;
	}
	
	/**
	 * @param dateTimeFinish the dateTimeFinish to set
	 */
	public void setDateTimeFinish(Date dateTimeFinish) {
		this.dateTimeFinish = dateTimeFinish;
	}

	/**
	 * @return the idScheduler
	 */
	public Integer getIdScheduler() {
		return this.idScheduler;
	}

	/**
	 * @param idScheduler the idScheduler to set
	 */
	public void setIdScheduler(Integer idScheduler) {
		this.idScheduler = idScheduler;
	}

	/**
	 * @return the moduleDesc
	 */
	public String getModuleDesc() {
		return this.moduleDesc;
	}

	/**
	 * @param moduleDesc the moduleDesc to set
	 */
	public void setModuleDesc(String moduleDesc) {
		this.moduleDesc = moduleDesc;
	}

}
