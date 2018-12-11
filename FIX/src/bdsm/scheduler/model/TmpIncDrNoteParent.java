package bdsm.scheduler.model;


import java.util.Date;


/**
*
* @author v00019372
*/
@SuppressWarnings("serial")
public abstract class TmpIncDrNoteParent extends bdsm.model.BaseModel implements java.io.Serializable {
	private TmpIncDrNotePK compositeId;
	private String moduleName;
	private String target;
	private String recordType;
	private String recordName;
	private String data;
	private Short length;
	private String comments;
	private String recordStatus;
	private Date dateTimeProcessStart;
	private Date dateTimeProcessFinish;
	
	
    /**
     * 
     * @return
     */
    public TmpIncDrNotePK getCompositeId() {
		return this.compositeId;
	}
    /**
     * 
     * @param compositeId
     */
    public void setCompositeId(TmpIncDrNotePK compositeId) {
		this.compositeId = compositeId;
	}
	
    /**
     * 
     * @return
     */
    public String getModuleName() {
		return this.moduleName;
	}
    /**
     * 
     * @param moduleName
     */
    public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	
    /**
     * 
     * @return
     */
    public String getTarget() {
		return this.target;
	}
    /**
     * 
     * @param target
     */
    public void setTarget(String target) {
		this.target = target;
	}
	
    /**
     * 
     * @return
     */
    public String getRecordType() {
		return this.recordType;
	}
    /**
     * 
     * @param recordType
     */
    public void setRecordType(String recordType) {
		this.recordType = recordType;
	}
	
    /**
     * 
     * @return
     */
    public String getRecordName() {
		return this.recordName;
	}
    /**
     * 
     * @param recordName
     */
    public void setRecordName(String recordName) {
		this.recordName = recordName;
	}
	
    /**
     * 
     * @return
     */
    public String getData() {
		return this.data;
	}
    /**
     * 
     * @param data
     */
    public void setData(String data) {
		this.data = data;
	}
	
    /**
     * 
     * @return
     */
    public Short getLength() {
		return this.length;
	}
    /**
     * 
     * @param length
     */
    public void setLength(Short length) {
		this.length = length;
	}
	
    /**
     * 
     * @return
     */
    public String getComments() {
		return this.comments;
	}
    /**
     * 
     * @param comments
     */
    public void setComments(String comments) {
		this.comments = comments;
	}
	
    /**
     * 
     * @return
     */
    public String getRecordStatus() {
		return this.recordStatus;
	}
    /**
     * 
     * @param recordStatus
     */
    public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}
	
    /**
     * 
     * @return
     */
    public Date getDateTimeProcessStart() {
		return this.dateTimeProcessStart;
	}
    /**
     * 
     * @param dateTimeProcessStart
     */
    public void setDateTimeProcessStart(Date dateTimeProcessStart) {
		this.dateTimeProcessStart = dateTimeProcessStart;
	}
	
    /**
     * 
     * @return
     */
    public Date getDateTimeProcessFinish() {
		return this.dateTimeProcessFinish;
	}
    /**
     * 
     * @param dateTimeProcessFinish
     */
    public void setDateTimeProcessFinish(Date dateTimeProcessFinish) {
		this.dateTimeProcessFinish = dateTimeProcessFinish;
	}

	
	
    @Override
    public String toString() {
    	StringBuilder sb = new StringBuilder()
    		.append("compositeId=").append(this.compositeId.toString())
    		.append(",moduleName=").append(this.moduleName)
    		.append(",target=").append(this.target)
    		.append(",recordType=").append(this.recordType)
    		.append(",recordName=").append(this.recordName)
    		.append(",data=").append(this.data)
    		.append(",length=").append(this.length)
    		.append(",comments=").append(this.comments)
    		.append(",recordStatus=").append(this.recordStatus)
    		.append(",dateTimeProcessStart=").append(this.dateTimeProcessStart)
    		.append(",dateTimeProcessFinish=").append(this.dateTimeProcessFinish)
    		;
    	
    	return sb.toString();
    }

}
