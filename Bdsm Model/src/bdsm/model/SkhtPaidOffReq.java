package bdsm.model;


import java.math.BigDecimal;


/**
 * @author v00019372
 */
@SuppressWarnings("serial")
public class SkhtPaidOffReq extends BaseModel {
	private String refNo;
	private String hajiType;
	private String jamaahName;
	private String acctNo;
	private Integer currencyCode;
	private BigDecimal finalDeposit;
	private Integer bpsBranchCode;
	private String validationNo;
	private String embarkasi;
	private String kloter;
	private Long portionNo;
	private BigDecimal bpihCost;
	private BigDecimal usdExchange;
	private BigDecimal bpihInIDR;
	private BigDecimal residualCost;
	private String flgPaidOff;
	private String delayYear;
	private Integer PIHKCode;
	private String PIHKName;
	private java.util.Date trxDate;
	private BigDecimal initialDeposit;
	
	
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
	
	public String getJamaahName() {
		return this.jamaahName;
	}
	public void setJamaahName(String jamaahName) {
		this.jamaahName = jamaahName;
	}
	
	public String getAcctNo() {
		return this.acctNo;
	}
	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}
	
	public Integer getCurrencyCode() {
		return this.currencyCode;
	}
	public void setCurrencyCode(Integer currencyCode) {
		this.currencyCode = currencyCode;
	}
	
	public BigDecimal getFinalDeposit() {
		return this.finalDeposit;
	}
	public void setFinalDeposit(BigDecimal finalDeposit) {
		this.finalDeposit = finalDeposit;
	}
	
	public Integer getBpsBranchCode() {
		return this.bpsBranchCode;
	}
	public void setBpsBranchCode(Integer bpsBranchCode) {
		this.bpsBranchCode = bpsBranchCode;
	}
	
	public String getValidationNo() {
		return this.validationNo;
	}
	public void setValidationNo(String validationNo) {
		this.validationNo = validationNo;
	}
	
	public String getEmbarkasi() {
		return this.embarkasi;
	}
	public void setEmbarkasi(String embarkasi) {
		this.embarkasi = embarkasi;
	}
	
	public String getKloter() {
		return this.kloter;
	}
	public void setKloter(String kloter) {
		this.kloter = kloter;
	}
	
	public Long getPortionNo() {
		return this.portionNo;
	}
	public void setPortionNo(Long portionNo) {
		this.portionNo = portionNo;
	}
	
	public BigDecimal getBPIHCost() {
		return this.bpihCost;
	}
	public void setBPIHCost(BigDecimal BPIHCost) {
		this.bpihCost = BPIHCost;
	}
	
	public BigDecimal getUSDExchange() {
		return this.usdExchange;
	}
	public void setUSDExchange(BigDecimal USDExchange) {
		this.usdExchange = USDExchange;
	}
	
	public BigDecimal getBPIHInIDR() {
		return this.bpihInIDR;
	}
	public void setBPIHInIDR(BigDecimal BPIHInIDR) {
		this.bpihInIDR = BPIHInIDR;
	}
	
	public BigDecimal getResidualCost() {
		return this.residualCost;
	}
	public void setResidualCost(BigDecimal residualCost) {
		this.residualCost = residualCost;
	}
	
	public String getFlgPaidOff() {
		return this.flgPaidOff;
	}
	public void setFlgPaidOff(String flgPaidOff) {
		this.flgPaidOff = flgPaidOff;
	}
	
	public String getDelayYear() {
		return this.delayYear;
	}
	public void setDelayYear(String delayYear) {
		this.delayYear = delayYear;
	}
	
	public Integer getPIHKCode() {
		return this.PIHKCode;
	}
	public void setPIHKCode(Integer PIHKCode) {
		this.PIHKCode = PIHKCode;
	}
	
	public String getPIHKName() {
		return this.PIHKName;
	}
	public void setPIHKName(String PIHKName) {
		this.PIHKName = PIHKName;
	}
	
	public java.util.Date getTrxDate() {
		return this.trxDate;
	}
	public void setTrxDate(java.util.Date trxDate) {
		this.trxDate = trxDate;
	}
	
	public BigDecimal getInitialDeposit() {
		return this.initialDeposit;
	}
	public void setInitialDeposit(BigDecimal initialDeposit) {
		this.initialDeposit = initialDeposit;
	}
	
	
	/* Hibernate to Database properties */
	protected Character getHajiTypeDB() {
		return ((this.hajiType == null)? null: this.hajiType.charAt(0));
	}
	protected void setHajiTypeDB(Character hajiTypeDB) {
		this.hajiType = (hajiTypeDB == null)? null: hajiTypeDB.toString();
	}
	
	protected Character getFlgPaidOffDB() {
		return ((this.flgPaidOff == null)? null: this.flgPaidOff.charAt(0));
	}
	protected void setFlgPaidOffDB(Character flgPaidOffDB) {
		this.flgPaidOff = (flgPaidOffDB == null)? null: flgPaidOffDB.toString();
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(this.getClass().getSimpleName())
			.append("{")
			.append("refNo=").append(this.refNo)
			.append(",hajiType=").append(this.hajiType)
			.append(",jamaahName=").append(this.jamaahName)
			.append(",acctNo=").append(this.acctNo)
			.append(",currencyCode=").append(this.currencyCode)
			.append(",finalDeposit=").append(this.finalDeposit)
			.append(",bpsBranchCode=").append(this.bpsBranchCode)
			.append(",validationNo=").append(this.validationNo)
			.append(",embarkasi=").append(this.embarkasi)
			.append(",kloter=").append(this.kloter)
			.append(",portionNo=").append(this.portionNo)
			.append(",bpihCost=").append(this.bpihCost)
			.append(",usdExchange=").append(this.usdExchange)
			.append(",bpihInIDR=").append(this.bpihInIDR)
			.append(",residualCost=").append(this.residualCost)
			.append(",flgPaidOff=").append(this.flgPaidOff)
			.append(",delayYear=").append(this.delayYear)
			.append(",PIHKCode=").append(this.PIHKCode)
			.append(",PIHKName=").append(this.PIHKName)
			.append(",trxDate=").append(this.trxDate)
			.append(",initialDeposit=").append(this.initialDeposit)
			.append("}");
	
		return sb.toString();
	}
	
}
