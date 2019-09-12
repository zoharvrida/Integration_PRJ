package bdsm.model;

import java.math.BigDecimal;

public class TransactionParameter extends BaseModel implements java.io.Serializable {
	private Integer id;
	private String moduleName;
	private String accountNo;
	private String accountName;
	private String accountBranch;
	private String GLNo;
	private String GLName;
	private String costCenter;
	private BigDecimal feeBDI;
	private BigDecimal feeLLG;
	private BigDecimal feeRTGS;
	private BigDecimal feeSWIFT;
	private String memo;
	private String moduleOwnerId;
	private String moduleOwnerName;
	private String moduleOwnerEmail;
	private String flagStatus;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
	public String getAccountBranch() {
		return accountBranch;
	}
	public void setAccountBranch(String accountBranch) {
		this.accountBranch = accountBranch;
	}
	
	public String getGLNo() {
		return GLNo;
	}
	public void setGLNo(String gLNo) {
		GLNo = gLNo;
	}
	
	public String getGLName() {
		return GLName;
	}
	public void setGLName(String gLName) {
		GLName = gLName;
	}
	
	public String getCostCenter() {
		return costCenter;
	}
	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}
	
	public BigDecimal getFeeBDI() {
		return feeBDI;
	}
	public void setFeeBDI(BigDecimal feeBDI) {
		this.feeBDI = feeBDI;
	}
	
	public BigDecimal getFeeLLG() {
		return feeLLG;
	}
	public void setFeeLLG(BigDecimal feeLLG) {
		this.feeLLG = feeLLG;
	}
	
	public BigDecimal getFeeRTGS() {
		return feeRTGS;
	}
	public void setFeeRTGS(BigDecimal feeRTGS) {
		this.feeRTGS = feeRTGS;
	}
	
	public BigDecimal getFeeSWIFT() {
		return feeSWIFT;
	}
	public void setFeeSWIFT(BigDecimal feeSWIFT) {
		this.feeSWIFT = feeSWIFT;
	}
	
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	public String getModuleOwnerId() {
		return moduleOwnerId;
	}
	public void setModuleOwnerId(String moduleOwnerId) {
		this.moduleOwnerId = moduleOwnerId;
	}
	
	public String getModuleOwnerName() {
		return moduleOwnerName;
	}
	public void setModuleOwnerName(String moduleOwnerName) {
		this.moduleOwnerName = moduleOwnerName;
	}
	
	public String getModuleOwnerEmail() {
		return moduleOwnerEmail;
	}
	public void setModuleOwnerEmail(String moduleOwnerEmail) {
		this.moduleOwnerEmail = moduleOwnerEmail;
	}
	
	public String getFlagStatus() {
		return flagStatus;
	}
	public void setFlagStatus(String flagStatus) {
		this.flagStatus = flagStatus;
	}
	
	
	/* Hibernate to Database properties */
	
	public Character getFlagStatusDB() {
		return (this.flagStatus == null)? null: this.flagStatus.charAt(0);
	}
	
	public void setFlagStatusDB(Character flagStatusDB) {
		this.flagStatus = (flagStatusDB == null)? null: flagStatusDB.toString();
	}
}
