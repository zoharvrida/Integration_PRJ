package bdsm.model;


/**
 * @author v00019372
 */
@SuppressWarnings("serial")
public class SkhtPaidOffInqReq extends BaseModel {
	private String refNo;
	private java.util.Date trxDate;
	private String acctNo;
	private Integer bpsBranchCode;
	private Integer cifNumber;
	private Long portionNo;
	
	
	public String getRefNo() {
		return this.refNo;
	}
	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}
	
	public java.util.Date getTrxDate() {
		return this.trxDate;
	}
	public void setTrxDate(java.util.Date trxDate) {
		this.trxDate = trxDate;
	}
	
	public String getAcctNo() {
		return this.acctNo;
	}
	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}
	
	public Integer getBpsBranchCode() {
		return this.bpsBranchCode;
	}
	public void setBpsBranchCode(Integer bpsBranchCode) {
		this.bpsBranchCode = bpsBranchCode;
	}
	
	public Integer getCifNumber() {
		return this.cifNumber;
	}
	public void setCifNumber(Integer cifNumber) {
		this.cifNumber = cifNumber;
	}
	
	public Long getPortionNo() {
		return this.portionNo;
	}
	public void setPortionNo(Long portionNo) {
		this.portionNo = portionNo;
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(this.getClass().getSimpleName())
			.append("{")
			.append("refNo=").append(this.refNo)
			.append(",trxDate=").append(this.trxDate)
			.append(",acctNo=").append(this.acctNo)
			.append(",bpsBranchCode=").append(this.bpsBranchCode)
			.append(",cifNumber=").append(this.cifNumber)
			.append(",portionNo=").append(this.portionNo)
			.append("}");

		return sb.toString();
	}
	
}
