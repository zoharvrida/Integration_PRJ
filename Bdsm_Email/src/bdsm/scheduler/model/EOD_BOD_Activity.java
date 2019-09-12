package bdsm.scheduler.model;


/**
 * 
 * @author v00019372
 */
@SuppressWarnings("serial")
public class EOD_BOD_Activity extends bdsm.model.BaseModel {
	private Integer activityId;
	private Integer procMastId;
	private Integer postingDate;
	private java.util.Date systemDate;
	
	
	public EOD_BOD_Activity() {}
	
	public EOD_BOD_Activity(Integer procMastId, Integer postingDate, java.util.Date systemDate) {
		this.procMastId = procMastId;
		this.postingDate = postingDate;
		this.systemDate = systemDate;
	}
	
	
	public Integer getActivityId() {
		return this.activityId;
	}
	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}
	
	public Integer getProcMastId() {
		return this.procMastId;
	}
	public void setProcMastId(Integer procMastId) {
		this.procMastId = procMastId;
	}
	
	public Integer getPostingDate() {
		return this.postingDate;
	}
	public void setPostingDate(Integer postingDate) {
		this.postingDate = postingDate;
	}
	
	public java.util.Date getSystemDate() {
		return this.systemDate;
	}
	public void setSystemDate(java.util.Date systemDate) {
		this.systemDate = systemDate;
	}
	
	

}
