package bdsm.scheduler.model;

import java.util.Date;


public abstract class TmpBdgwParent extends bdsm.model.BaseModel implements java.io.Serializable {
	private TmpBdgwPK id;
	private String moduleName;
	private String target;
	private Date dateTimeProcessStart;
	private Date dateTimeProcessFinish;
	private Integer recId;
	private String recordType;
	private String recordName;
	private Long length;
	private String comments;
	private String recordStatus;
	private Long parentRecordId;
	
	
	public TmpBdgwPK getId() {
		return id;
	}
	public void setId(TmpBdgwPK id) {
		this.id = id;
	}
	
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	
	public Date getDateTimeProcessStart() {
		return dateTimeProcessStart;
	}
	public void setDateTimeProcessStart(Date dateTimeProcessStart) {
		this.dateTimeProcessStart = dateTimeProcessStart;
	}
	
	public Date getDateTimeProcessFinish() {
		return dateTimeProcessFinish;
	}
	public void setDateTimeProcessFinish(Date dateTimeProcessFinish) {
		this.dateTimeProcessFinish = dateTimeProcessFinish;
	}
	
	public Integer getRecId() {
		return recId;
	}
	public void setRecId(Integer recId) {
		this.recId = recId;
	}
	
	public String getRecordType() {
		return recordType;
	}
	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}
	
	public String getRecordName() {
		return recordName;
	}
	public void setRecordName(String recordName) {
		this.recordName = recordName;
	}
	
	public Long getLength() {
		return length;
	}
	public void setLength(Long length) {
		this.length = length;
	}
	
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	public String getRecordStatus() {
		return recordStatus;
	}
	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}
	
	public Long getParentRecordId() {
		return parentRecordId;
	}
	public void setParentRecordId(Long parentRecordId) {
		this.parentRecordId = parentRecordId;
	}
	
	
	public Long getRecordId() {
		return this.getId().getRecordId();
	}
	
	public String getFileId() {
		return this.getId().getFileId();
	}
	
	
	/*
	 * This method should be called after all properties are set
	 */
	public Object[] getData() throws Exception {
		int intResult = 0;
		String strResult = "";
		
		Class clazz = this.getClass();
		java.lang.reflect.Method method;
		java.lang.reflect.Field[] fields = clazz.getDeclaredFields();;
		String[] propertyNames;
		
		propertyNames = new String[fields.length + 1];
		propertyNames[0] = "recordType";
		for (int i=0; i<fields.length; i++)
			propertyNames[i+1] = fields[i].getName();
		
		
		for (int i=0; i<propertyNames.length; i++) {
			String value;
			method = clazz.getMethod("get" + propertyNames[i].substring(0, 1).toUpperCase() + propertyNames[i].substring(1), new Class[0]);
			value = (String) method.invoke(this, new Object[0]);
			intResult += (value!=null)? value.length(): 0;
			strResult += value;
		}
		
		return new Object[] {intResult, strResult};
	}
	
	public int getDataLength() throws Exception {
		return (Integer) this.getData()[0];
	}
	
	public String getDataString() throws Exception {
		return (String) this.getData()[1];
	}
}
