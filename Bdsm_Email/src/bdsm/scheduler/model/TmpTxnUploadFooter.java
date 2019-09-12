package bdsm.scheduler.model;


/**
 * 
 * @author v00019372
 */
@SuppressWarnings("serial")
public class TmpTxnUploadFooter extends TmpTxnUploadParent implements java.io.Serializable {
	private Integer noOfDr;
	private java.math.BigDecimal amtDr;
	private Integer noOfCr;
	private java.math.BigDecimal amtCr;
	
	
	public TmpTxnUploadFooter(TmpTxnUploadHeader header, Integer recordId) {
		this.setCompositeId(header.getCompositeId().clone());
		this.getCompositeId().setRecordId(recordId);
		this.setModuleName(header.getModuleName());
		this.setTarget("EXT_TXNUPLOAD_FOOTERRECDTO");
		this.setDtmProcStart(header.getDtmProcStart());
		this.setRecType((byte) 3);
		this.setRecordType(this.getRecType().toString());
		this.setRecordName("Footer");
		this.setParentRecId(header.getCompositeId().getRecordId());
	}
	
	
	public Integer getNoOfDr() {
		return this.noOfDr;
	}
	public void setNoOfDr(Integer noOfDr) {
		this.noOfDr = noOfDr;
	}
	
	public java.math.BigDecimal getAmtDr() {
		return this.amtDr;
	}
	public void setAmtDr(java.math.BigDecimal amtDr) {
		this.amtDr = amtDr;
	}
	
	public Integer getNoOfCr() {
		return this.noOfCr;
	}
	public void setNoOfCr(Integer noOfCr) {
		this.noOfCr = noOfCr;
	}
	
	public java.math.BigDecimal getAmtCr() {
		return this.amtCr;
	}
	public void setAmtCr(java.math.BigDecimal amtCr) {
		this.amtCr = amtCr;
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(this.getClass().getSimpleName())
			.append("{")
			.append(super.toString())
			.append(",noOfDr=").append(this.noOfDr)
			.append(",amtDr=").append(this.amtDr)
			.append(",noOfCr=").append(this.noOfCr)
			.append(",amtCr=").append(this.amtCr)
			.append("}");
		
		return sb.toString();
	}
	
}
