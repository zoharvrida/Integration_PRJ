package bdsm.model;


/**
 * @author v00019372
 */
@SuppressWarnings("serial")
public class MPAcctRegDtls extends BaseModel implements java.io.Serializable {
	private Long acctRegId;
	private Integer codeComponent;
	private java.math.BigDecimal amountValue;
	private Double counterValue;
	private String stringValue;
	
	
	public MPAcctRegDtls() {}
	
	public MPAcctRegDtls(Long acctRegId, Integer codeComponent) {
		this.acctRegId = acctRegId;
		this.codeComponent = codeComponent;
	}
	
	
	public Long getAcctRegId() {
		return this.acctRegId;
	}
	public void setAcctRegId(Long acctRegId) {
		this.acctRegId = acctRegId;
	}
	
	public Integer getCodeComponent() {
		return this.codeComponent;
	}
	public void setCodeComponent(Integer codeComponent) {
		this.codeComponent = codeComponent;
	}
	
	public java.math.BigDecimal getAmountValue() {
		return this.amountValue;
	}
	public void setAmountValue(java.math.BigDecimal amountValue) {
		this.amountValue = amountValue;
	}
	
	public Double getCounterValue() {
		return this.counterValue;
	}
	public void setCounterValue(Double counterValue) {
		this.counterValue = counterValue;
	}
	
	public String getStringValue() {
		return this.stringValue;
	}
	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}
	
	
	@Override
	public String toString() {
    	StringBuilder sb = new StringBuilder(this.getClass().getSimpleName())
			.append("{")
			.append(",acctRegId=").append(this.acctRegId)
			.append(",codeComponent=").append(this.codeComponent)
			.append(",amountValue=").append(this.amountValue)
			.append(",counterValue=").append(this.counterValue)
			.append(",stringValue=").append(this.stringValue)
			.append("}");
    	
		return sb.toString();
	}
}
