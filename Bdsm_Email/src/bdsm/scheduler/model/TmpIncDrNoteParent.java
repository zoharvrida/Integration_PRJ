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
	
	
	public TmpIncDrNotePK getCompositeId() {
		return this.compositeId;
	}
	public void setCompositeId(TmpIncDrNotePK compositeId) {
		this.compositeId = compositeId;
	}
	
	public String getModuleName() {
		return this.moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	
	public String getTarget() {
		return this.target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	
	public String getRecordType() {
		return this.recordType;
	}
	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}
	
	public String getRecordName() {
		return this.recordName;
	}
	public void setRecordName(String recordName) {
		this.recordName = recordName;
	}
	
	public String getData() {
		return this.data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
	public Short getLength() {
		return this.length;
	}
	public void setLength(Short length) {
		this.length = length;
	}
	
	public String getComments() {
		return this.comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	public String getRecordStatus() {
		return this.recordStatus;
	}
	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}
	
	public Date getDateTimeProcessStart() {
		return this.dateTimeProcessStart;
	}
	public void setDateTimeProcessStart(Date dateTimeProcessStart) {
		this.dateTimeProcessStart = dateTimeProcessStart;
	}
	
	public Date getDateTimeProcessFinish() {
		return this.dateTimeProcessFinish;
	}
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
