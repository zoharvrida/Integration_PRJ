package bdsm.scheduler.model;


/**
 * 
 * @author v00019372
 */
@SuppressWarnings("serial")
public abstract class TmpTxnUploadParent extends bdsm.model.BaseModel implements java.io.Serializable {
	private static final String RECORD_STATUS = String.valueOf(1);
	private static final Integer COD_ENTITY_VPD = 11;
	
	private static org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(TmpTxnUploadParent.class); 
	
	private TmpTxnUploadPK compositeId;
	private String moduleName;
	private String target;
	private java.sql.Timestamp dtmProcStart;
	private java.sql.Timestamp dtmProcFinish;
	private Byte recType;
	private String recordType;
	private String recordName;
	private String data;
	private Short length;
	private String comments;
	private String recordStatus;
	private Integer parentRecId;
	private Integer codEntityVpd;
	
	
    /**
     * 
     */
    public TmpTxnUploadParent() {
		this.recordStatus = RECORD_STATUS;
		this.codEntityVpd = COD_ENTITY_VPD;
	}
	
	
    /**
     * 
     * @return
     */
    public TmpTxnUploadPK getCompositeId() {
		return this.compositeId;
	}
    /**
     * 
     * @param compositeId
     */
    public void setCompositeId(TmpTxnUploadPK compositeId) {
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
    public java.sql.Timestamp getDtmProcStart() {
		return this.dtmProcStart;
	}
    /**
     * 
     * @param dtmProcStart
     */
    public void setDtmProcStart(java.sql.Timestamp dtmProcStart) {
		this.dtmProcStart = dtmProcStart;
	}
	
    /**
     * 
     * @return
     */
    public java.sql.Timestamp getDtmProcFinish() {
		return this.dtmProcFinish;
	}
    /**
     * 
     * @param dtmProcFinish
     */
    public void setDtmProcFinish(java.sql.Timestamp dtmProcFinish) {
		this.dtmProcFinish = dtmProcFinish;
	}
	
    /**
     * 
     * @return
     */
    public Byte getRecType() {
		return this.recType;
	}
    /**
     * 
     * @param recType
     */
    public void setRecType(Byte recType) {
		this.recType = recType;
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
    public Integer getParentRecId() {
		return this.parentRecId;
	}
    /**
     * 
     * @param parentRecId
     */
    public void setParentRecId(Integer parentRecId) {
		this.parentRecId = parentRecId;
	}
	
    /**
     * 
     * @return
     */
    public Integer getCodEntityVpd() {
		return this.codEntityVpd;
	}
    /**
     * 
     * @param codEntityVpd
     */
    public void setCodEntityVpd(Integer codEntityVpd) {
		this.codEntityVpd = codEntityVpd;
	}
	
	
	/**
	 * This method should be called after all properties set
	 */
	public void generateLength() {
		java.lang.reflect.Field[] fields = this.getClass().getDeclaredFields();
		int length = 1; // recType length
		Object value;
		
		try {
			for (java.lang.reflect.Field field : fields) {
				field.setAccessible(true);
				value = field.get(this);
				length += ((value==null)? "": value.toString()).length();
			}
			length += fields.length; // delimiters ':'
			
			this.setLength((short) length);
		}
		catch (IllegalAccessException iae) {
			LOGGER.error(iae, iae);
		}
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder()
			.append("compositeId=").append(this.compositeId)
			.append(",moduleName=").append(this.moduleName)
			.append(",target=").append(this.target)
			.append(",dtmProcStart=").append(this.dtmProcStart)
			.append(",dtmProcFinish=").append(this.dtmProcFinish)
			.append(",recType=").append(this.recType)
			.append(",recordType=").append(this.recordType)
			.append(",recordName=").append(this.recordName)
			.append(",data=").append(this.data)
			.append(",length=").append(this.length)
			.append(",comments=").append(this.comments)
			.append(",recordStatus=").append(this.recordStatus)
			.append(",parentRecId=").append(this.parentRecId)
			.append(",codEntityVpd=").append(this.codEntityVpd);
		
		return sb.toString();
	}
	
}
