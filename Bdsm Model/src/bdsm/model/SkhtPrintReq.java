package bdsm.model;


/**
 * @author v00019372
 */
@SuppressWarnings("serial")
public class SkhtPrintReq extends BaseModel {
	private String refNo;
	private String hajiType;
	private String validationNo;
	private Integer bpsBranchCode;
	private Integer cifNumber;
	private String acctNo;
	private Integer portionNo;
	
	
	public String getRefNo() {
		return this.refNo;
	}
	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}
	
	public String getHajiType() {
		return this.hajiType;
	}
	public void setHajiType(String hajiType) {
		this.hajiType = hajiType;
	}
	
	public String getValidationNo() {
		return this.validationNo;
	}
	public void setValidationNo(String validationNo) {
		this.validationNo = validationNo;
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
	
	public String getAcctNo() {
		return this.acctNo;
	}
	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}
	
	public Integer getPortionNo() {
		return this.portionNo;
	}
	public void setPortionNo(Integer portionNo) {
		this.portionNo = portionNo;
	}
	
	
	/* Hibernate to Database properties */
	protected Character getHajiTypeDB() {
		return ((this.hajiType == null)? null: this.hajiType.charAt(0));
	}
	protected void setHajiTypeDB(Character hajiTypeDB) {
		this.hajiType = (hajiTypeDB == null)? null: hajiTypeDB.toString();
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(this.getClass().getSimpleName())
			.append("{")
			.append("refNo=").append(this.refNo)
			.append(",hajiType=").append(this.hajiType)
			.append(",validationNo=").append(this.validationNo)
			.append(",bpsBranchCode=").append(this.bpsBranchCode)
			.append(",cifNumber=").append(this.cifNumber)
			.append(",acctNo=").append(this.acctNo)
			.append(",portionNo=").append(this.portionNo)
			.append("}");

		return sb.toString();
	}
	
}
