package bdsm.scheduler.model;

import java.util.Date;


/**
 * 
 * @author bdsm
 */
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
	
	
    /**
     * 
     * @return
     */
    public TmpBdgwPK getId() {
		return id;
	}
    /**
     * 
     * @param id
     */
    public void setId(TmpBdgwPK id) {
		this.id = id;
	}
	
    /**
     * 
     * @return
     */
    public String getModuleName() {
		return moduleName;
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
		return target;
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
    public Date getDateTimeProcessStart() {
		return dateTimeProcessStart;
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
		return dateTimeProcessFinish;
	}
    /**
     * 
     * @param dateTimeProcessFinish
     */
    public void setDateTimeProcessFinish(Date dateTimeProcessFinish) {
		this.dateTimeProcessFinish = dateTimeProcessFinish;
	}
	
    /**
     * 
     * @return
     */
    public Integer getRecId() {
		return recId;
	}
    /**
     * 
     * @param recId
     */
    public void setRecId(Integer recId) {
		this.recId = recId;
	}
	
    /**
     * 
     * @return
     */
    public String getRecordType() {
		return recordType;
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
		return recordName;
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
    public Long getLength() {
		return length;
	}
    /**
     * 
     * @param length
     */
    public void setLength(Long length) {
		this.length = length;
	}
	
    /**
     * 
     * @return
     */
    public String getComments() {
		return comments;
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
		return recordStatus;
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
    public Long getParentRecordId() {
		return parentRecordId;
	}
    /**
     * 
     * @param parentRecordId
     */
    public void setParentRecordId(Long parentRecordId) {
		this.parentRecordId = parentRecordId;
	}
	
	
    /**
     * 
     * @return
     */
    public Long getRecordId() {
		return this.getId().getRecordId();
	}
	
    /**
     * 
     * @return
     */
    public String getFileId() {
		return this.getId().getFileId();
	}
	
	
	/*
	 * This method should be called after all properties are set
	 */
    /**
     * 
     * @return
     * @throws Exception
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
	
    /**
     * 
     * @return
     * @throws Exception
     */
    public int getDataLength() throws Exception {
		return (Integer) this.getData()[0];
	}
	
    /**
     * 
     * @return
     * @throws Exception
     */
    public String getDataString() throws Exception {
		return (String) this.getData()[1];
	}
}
