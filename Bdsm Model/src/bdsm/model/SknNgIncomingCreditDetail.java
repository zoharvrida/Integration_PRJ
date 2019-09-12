package bdsm.model;

public class SknNgIncomingCreditDetail extends BaseModel{
	
	private HostSknngInOutCreditPK compositeId;
	//private int recordId;
	//private String batchNo;
	
	private String clearingDate;
	private String referenceNo;
	private String remitterName;
	private String beneficiaryName;
	private String beneficiaryAccount;
	private String remarks;
	private Double amountDbl;
	private String amount;
	private String receiverBankCode;
	private String senderBICode;
	private String senderSknCoordinatorCode;
	private String transactionCode;
	private String bankType;
	private String remitterResidencyStatus;
	private String remitterCitizenshipStatus;
	private String beneficiaryProvinceCode;
	private String beneficiaryCityCode;
	private String receiverSknCoordinatorCode;
	private String senderClearingCode;
	private String sor;
	private String recordType;
	private String recordName;
	private String data;
	private Integer length;
	private String comments;
	private String recordStatus;
	private Integer codEntityVpd;
	private String jData;
	
	
	public SknNgIncomingCreditDetail() {
		
	}
	/*
	public int getRecordId() {
		return recordId;
	}
	public void setRecordId(int recordId) {
		this.recordId = recordId;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}*/
	public String getClearingDate() {
		return clearingDate.substring(0, 10);
	}
	public void setClearingDate(String clearingDate) {
		this.clearingDate = clearingDate;
	}
	public String getReferenceNo() {
		return referenceNo;
	}
	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}
	public String getRemitterName() {
		return remitterName;
	}
	public void setRemitterName(String remitterName) {
		this.remitterName = remitterName;
	}
	public String getBeneficiaryName() {
		return beneficiaryName;
	}
	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}
	public String getBeneficiaryAccount() {
		return beneficiaryAccount;
	}
	public void setBeneficiaryAccount(String beneficiaryAccount) {
		this.beneficiaryAccount = beneficiaryAccount;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
		this.amountDbl = new Double(amount);
	}
	public Double getAmountDbl() {
		return amountDbl;
	}
	public void setAmountDbl(Double amountDbl) {
		this.amountDbl = amountDbl;
		this.amount = amountDbl.toString();
	}
	public String getReceiverBankCode() {
		return receiverBankCode;
	}
	public void setReceiverBankCode(String receiverBankCode) {
		this.receiverBankCode = receiverBankCode;
	}
	public String getSenderBICode() {
		return senderBICode;
	}
	public void setSenderBICode(String senderBICode) {
		this.senderBICode = senderBICode;
	}
	public String getSenderSknCoordinatorCode() {
		return senderSknCoordinatorCode;
	}
	public void setSenderSknCoordinatorCode(String senderSknCoordinatorCode) {
		this.senderSknCoordinatorCode = senderSknCoordinatorCode;
	}
	public String getTransactionCode() {
		return transactionCode;
	}
	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}
	public String getBankType() {
		return bankType;
	}
	public void setBankType(String bankType) {
		this.bankType = bankType;
	}
	public String getRemitterResidencyStatus() {
		return remitterResidencyStatus;
	}
	public void setRemitterResidencyStatus(String remitterResidencyStatus) {
		this.remitterResidencyStatus = remitterResidencyStatus;
	}
	public String getRemitterCitizenshipStatus() {
		return remitterCitizenshipStatus;
	}
	public void setRemitterCitizenshipStatus(String remitterCitizenshipStatus) {
		this.remitterCitizenshipStatus = remitterCitizenshipStatus;
	}
	public String getBeneficiaryProvinceCode() {
		return beneficiaryProvinceCode;
	}
	public void setBeneficiaryProvinceCode(String beneficiaryProvinceCode) {
		this.beneficiaryProvinceCode = beneficiaryProvinceCode;
	}
	public String getBeneficiaryCityCode() {
		return beneficiaryCityCode;
	}
	public void setBeneficiaryCityCode(String beneficiaryCityCode) {
		this.beneficiaryCityCode = beneficiaryCityCode;
	}
	public String getReceiverSknCoordinatorCode() {
		return receiverSknCoordinatorCode;
	}
	public void setReceiverSknCoordinatorCode(String receiverSknCoordinatorCode) {
		this.receiverSknCoordinatorCode = receiverSknCoordinatorCode;
	}
	public String getSenderClearingCode() {
		return senderClearingCode;
	}
	public void setSenderClearingCode(String senderClearingCode) {
		this.senderClearingCode = senderClearingCode;
	}
	public String getSor() {
		return sor;
	}
	public void setSor(String sor) {
		this.sor = sor;
	}
	public String getRecordType() {
		return recordType;
	}
	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}
	public String getRecordName() {
		return recordName;
	}
	public void setRecordName(String recordName) {
		this.recordName = recordName;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public Integer getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getRecordStatus() {
		return recordStatus;
	}
	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}
	public Integer getCodEntityVpd() {
		return codEntityVpd;
	}
	public void setCodEntityVpd(int codEntityVpd) {
		this.codEntityVpd = codEntityVpd;
	}

	/**
	 * @return the compositeId
	 */
	public HostSknngInOutCreditPK getCompositeId() {
		return compositeId;
	}

	/**
	 * @param compositeId the compositeId to set
	 */
	public void setCompositeId(HostSknngInOutCreditPK compositeId) {
		this.compositeId = compositeId;
	}

	/**
	 * @return the jData
	 */
	public String getjData() {
		return jData;
	}

	/**
	 * @param jData the jData to set
	 */
	public void setjData(String jData) {
		this.jData = jData;
	}
	
	
	
}
