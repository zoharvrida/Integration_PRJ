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
	
	
    /**
     * 
     */
    public EOD_BOD_Activity() {}
	
    /**
     * 
     * @param procMastId
     * @param postingDate
     * @param systemDate
     */
    public EOD_BOD_Activity(Integer procMastId, Integer postingDate, java.util.Date systemDate) {
		this.procMastId = procMastId;
		this.postingDate = postingDate;
		this.systemDate = systemDate;
	}
	
	
    /**
     * 
     * @return
     */
    public Integer getActivityId() {
		return this.activityId;
	}
    /**
     * 
     * @param activityId
     */
    public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}
	
    /**
     * 
     * @return
     */
    public Integer getProcMastId() {
		return this.procMastId;
	}
    /**
     * 
     * @param procMastId
     */
    public void setProcMastId(Integer procMastId) {
		this.procMastId = procMastId;
	}
	
    /**
     * 
     * @return
     */
    public Integer getPostingDate() {
		return this.postingDate;
	}
    /**
     * 
     * @param postingDate
     */
    public void setPostingDate(Integer postingDate) {
		this.postingDate = postingDate;
	}
	
    /**
     * 
     * @return
     */
    public java.util.Date getSystemDate() {
		return this.systemDate;
	}
    /**
     * 
     * @param systemDate
     */
    public void setSystemDate(java.util.Date systemDate) {
		this.systemDate = systemDate;
	}
	
	

}
