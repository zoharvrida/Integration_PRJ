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
	
	
    /**
     * 
     * @return
     */
    public String getClearingDate() {
		return this.clearingDate;
	}
    /**
     * 
     * @param clearingDate
     */
    public void setClearingDate(String clearingDate) {
		this.clearingDate = clearingDate;
	}
	
    /**
     * 
     * @return
     */
    public Integer getChequeNo() {
		return this.chequeNo;
	}
    /**
     * 
     * @param chequeNo
     */
    public void setChequeNo(Integer chequeNo) {
		this.chequeNo = chequeNo;
	}
	
    /**
     * 
     * @return
     */
    public Integer getPayersClearingCode() {
		return this.payersClearingCode;
	}
    /**
     * 
     * @param payersClearingCode
     */
    public void setPayersClearingCode(Integer payersClearingCode) {
		this.payersClearingCode = payersClearingCode;
	}
	
    /**
     * 
     * @return
     */
    public Long getPayersAccountNo() {
		return this.payersAccountNo;
	}
    /**
     * 
     * @param payersAccountNo
     */
    public void setPayersAccountNo(Long payersAccountNo) {
		this.payersAccountNo = payersAccountNo;
	}
	
    /**
     * 
     * @return
     */
    public Byte getTransactionCode() {
		return this.transactionCode;
	}
    /**
     * 
     * @param transactionCode
     */
    public void setTransactionCode(Byte transactionCode) {
		this.transactionCode = transactionCode;
	}
	
    /**
     * 
     * @return
     */
    public java.math.BigDecimal getAmount() {
		return this.amount;
	}
    /**
     * 
     * @param amount
     */
    public void setAmount(java.math.BigDecimal amount) {
		this.amount = amount;
	}
	
    /**
     * 
     * @return
     */
    public Integer getPayeeClearingCode() {
		return this.payeeClearingCode;
	}
    /**
     * 
     * @param payeeClearingCode
     */
    public void setPayeeClearingCode(Integer payeeClearingCode) {
		this.payeeClearingCode = payeeClearingCode;
	}
	
    /**
     * 
     * @return
     */
    public String getSOR() {
		return this.SOR;
	}
    /**
     * 
     * @param SOR
     */
    public void setSOR(String SOR) {
		this.SOR = SOR;
	}
	
    /**
     * 
     * @return
     */
    public String getBDSMBatchNo() {
		return this.BDSMBatchNo;
	}
    /**
     * 
     * @param BDSMBatchNo
     */
    public void setBDSMBatchNo(String BDSMBatchNo) {
		this.BDSMBatchNo = BDSMBatchNo;
	}
	
    /**
     * 
     * @return
     */
    public Integer getBDSMRecordId() {
		return this.BDSMRecordId;
	}
    /**
     * 
     * @param BDSMRecordId
     */
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
