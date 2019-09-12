package bdsm.scheduler.model;


/**
*
* @author v00019372
*/
@SuppressWarnings("serial")
public class TmpIncDrNoteDetail extends TmpIncDrNoteParent implements java.io.Serializable  {
	private String clearingDate;
	private Integer chequeNo;
	private Integer payersClearingCode;
	private Long payersAccountNo;
	private Byte transactionCode;
	private java.math.BigDecimal amount;
	private Integer payeeClearingCode;
	private String SOR;
	private String BDSMBatchNo;
	private Integer BDSMRecordId;
	
	
	public String getClearingDate() {
		return this.clearingDate;
	}
	public void setClearingDate(String clearingDate) {
		this.clearingDate = clearingDate;
	}
	
	public Integer getChequeNo() {
		return this.chequeNo;
	}
	public void setChequeNo(Integer chequeNo) {
		this.chequeNo = chequeNo;
	}
	
	public Integer getPayersClearingCode() {
		return this.payersClearingCode;
	}
	public void setPayersClearingCode(Integer payersClearingCode) {
		this.payersClearingCode = payersClearingCode;
	}
	
	public Long getPayersAccountNo() {
		return this.payersAccountNo;
	}
	public void setPayersAccountNo(Long payersAccountNo) {
		this.payersAccountNo = payersAccountNo;
	}
	
	public Byte getTransactionCode() {
		return this.transactionCode;
	}
	public void setTransactionCode(Byte transactionCode) {
		this.transactionCode = transactionCode;
	}
	
	public java.math.BigDecimal getAmount() {
		return this.amount;
	}
	public void setAmount(java.math.BigDecimal amount) {
		this.amount = amount;
	}
	
	public Integer getPayeeClearingCode() {
		return this.payeeClearingCode;
	}
	public void setPayeeClearingCode(Integer payeeClearingCode) {
		this.payeeClearingCode = payeeClearingCode;
	}
	
	public String getSOR() {
		return this.SOR;
	}
	public void setSOR(String SOR) {
		this.SOR = SOR;
	}
	
	public String getBDSMBatchNo() {
		return this.BDSMBatchNo;
	}
	public void setBDSMBatchNo(String BDSMBatchNo) {
		this.BDSMBatchNo = BDSMBatchNo;
	}
	
	public Integer getBDSMRecordId() {
		return this.BDSMRecordId;
	}
	public void setBDSMRecordId(Integer BDSMRecordId) {
		this.BDSMRecordId = BDSMRecordId;
	}
	
	
    @Override
    public String toString() {
    	StringBuilder sb = new StringBuilder(this.getClass().getSimpleName())
    		.append("{")
    		.append(super.toString())
    		.append(",clearingDate=").append(this.clearingDate)
    		.append(",chequeNo=").append(this.chequeNo)
    		.append(",payersClearingCode=").append(this.payersClearingCode)
    		.append(",payersAccountNo=").append(this.payersAccountNo)
    		.append(",transactionCode=").append(this.transactionCode)
    		.append(",amount=").append(this.amount)
    		.append(",payeeClearingCode=").append(this.payeeClearingCode)
    		.append(",SOR=").append(this.SOR)
    		.append(",BDSMBatchNo=").append(this.BDSMBatchNo)
    		.append(",BDSMRecordId=").append(this.BDSMRecordId)
    		.append("}");
    	
    	return sb.toString();
    }
	
}
