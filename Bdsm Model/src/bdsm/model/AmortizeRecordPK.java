package bdsm.model;


/**
 * 
 * @author v00019372
 */
@SuppressWarnings("serial")
public class AmortizeRecordPK implements java.io.Serializable {
	private Integer amortizeAccountId;
	private Short sequenceNo;
	
	
	public AmortizeRecordPK() {}
	
	public AmortizeRecordPK(Integer amortizeAccountId, Short sequenceNo) {
		this.amortizeAccountId = amortizeAccountId;
		this.sequenceNo = sequenceNo;
	}
	
	
	public Integer getAmortizeAccountId() {
		return this.amortizeAccountId;
	}
	public void setAmortizeAccountId(Integer amortizeAccountId) {
		this.amortizeAccountId = amortizeAccountId;
	}
	
	public Short getSequenceNo() {
		return this.sequenceNo;
	}
	public void setSequenceNo(Short sequenceNo) {
		this.sequenceNo = sequenceNo;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AmortizeRecordPK) {
			AmortizeRecordPK other = (AmortizeRecordPK) obj;
			if (
					((this.amortizeAccountId == null)? (other.amortizeAccountId == null): (this.amortizeAccountId.equals(other.amortizeAccountId))) 
					&& ((this.sequenceNo == null)? (other.sequenceNo == null): (this.sequenceNo.equals(other.sequenceNo)))
				)
				return true;
		}
			
		return false;
	}
	
	@Override
    public String toString() {
    	StringBuilder sb = new StringBuilder(this.getClass().getSimpleName())
    		.append("{")
    		.append("amortizeAccountId=").append(this.amortizeAccountId)
    		.append(",sequenceNo=").append(this.sequenceNo)
    		.append("}");
    	
    	return sb.toString();
    }
	
}
