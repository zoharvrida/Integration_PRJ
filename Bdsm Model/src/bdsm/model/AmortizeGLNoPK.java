package bdsm.model;


/**
 * 
 * @author v00019372
 */
@SuppressWarnings("serial")
public class AmortizeGLNoPK implements java.io.Serializable {
	static final Integer TYPE_ADVANCED = 1;
	static final Integer TYPE_ARREAR = 2;
	static final Integer PROCESS_TYPE_OPENING_ACCOUNT = 1;
	static final Integer PROCESS_TYPE_CANCELING_ACCOUNT = 2;
	static final Integer PROCESS_TYPE_END_OF_MONTH = 3;
	static final Integer PROCESS_TYPE_CLOSING_ACCOUNT = 4;
	static final String DRCR_DEBIT = "D";
	static final String DRCR_CREDIT = "C";
	
	private Integer type;
	private Integer processType;
	private String DrCr;
	private Integer sequenceNo;
	
	
	public AmortizeGLNoPK() {}
	
	public AmortizeGLNoPK(Integer type, Integer processType, String DrCr, Integer sequenceNo) {
		this.type = type;
		this.processType = processType;
		this.DrCr = DrCr;
		this.sequenceNo = sequenceNo;
	}
	
	
	public Integer getType() {
		return this.type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	public Integer getProcessType() {
		return this.processType;
	}
	public void setProcessType(Integer processType) {
		this.processType = processType;
	}
	
	public String getDrCr() {
		return this.DrCr;
	}
	public void setDrCr(String DrCr) {
		this.DrCr = DrCr;
	}
	
	public Integer getSequenceNo() {
		return this.sequenceNo;
	}
	public void setSequenceNo(Integer sequenceNo) {
		this.sequenceNo = sequenceNo;
	}
	
	
	
	/* Hibernate to Database properties */
	
	protected Character getDrCrDB() {
		return ((this.DrCr == null)? null: this.DrCr.charAt(0));
	}
	protected void setDrCrDB(Character DrCrDB) {
		this.DrCr = (DrCrDB == null)? null: DrCrDB.toString();
	}
	
	
	@Override
    public String toString() {
    	StringBuilder sb = new StringBuilder(this.getClass().getSimpleName())
    		.append("{")
    		.append("type=").append(this.type)
    		.append(",processType=").append(this.processType)
    		.append(",DrCr=").append(this.DrCr)
    		.append(",sequenceNo=").append(this.sequenceNo)
    		.append("}");
    	
    	return sb.toString();
    }
	
}
