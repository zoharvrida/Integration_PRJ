package bdsm.model;


/**
 * @author v00019372
 */
@SuppressWarnings("serial")
public class SknNgInOutCreditFooter extends SknNgInOutCreditParent implements java.io.Serializable {
	private String CRC;
	private Integer parentRecordId;
	
	
	public String getCRC() {
		return this.CRC;
	}
	public void setCRC(String CRC) {
		this.CRC = CRC;
	}
	
	public Integer getParentRecordId() {
		return parentRecordId;
	}
	public void setParentRecordId(Integer parentRecordId) {
		this.parentRecordId = parentRecordId;
	}

	
	
    @Override
    public String toString() {
    	StringBuilder sb = new StringBuilder(this.getClass().getSimpleName())
    		.append("{")
    		.append(super.toString())
    		.append(",CRC=").append(this.CRC)
    		.append(",parentRecordId=").append(this.parentRecordId)
    		.append("}");
    	
    	return sb.toString();
    }
}
