package bdsm.model;


/**
 * 
 * @author v00019372
 */
@SuppressWarnings("serial")
public class AmortizeProgramDetail extends BaseModel implements java.io.Serializable {
	private Integer id;
	private String giftCode;
	private Integer productCode;
	private java.util.Date effectiveDate;
	private Integer term;
	private java.math.BigDecimal giftPrice;
	private java.math.BigDecimal minimumHold;
	private java.math.BigDecimal maximumHold;
	
	
	public Integer getId() {
		return this.id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getGiftCode() {
		return this.giftCode;
	}
	public void setGiftCode(String giftCode) {
		this.giftCode = giftCode;
	}
	
	public Integer getProductCode() {
		return this.productCode;
	}
	public void setProductCode(Integer productCode) {
		this.productCode = productCode;
	}
	
	public java.util.Date getEffectiveDate() {
		return this.effectiveDate;
	}
	public void setEffectiveDate(java.util.Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	
	public Integer getTerm() {
		return this.term;
	}
	public void setTerm(Integer term) {
		this.term = term;
	}
	
	public java.math.BigDecimal getGiftPrice() {
		return this.giftPrice;
	}
	public void setGiftPrice(java.math.BigDecimal giftPrice) {
		this.giftPrice = giftPrice;
	}
	
	public java.math.BigDecimal getMinimumHold() {
		return this.minimumHold;
	}
	public void setMinimumHold(java.math.BigDecimal minimumHold) {
		this.minimumHold = minimumHold;
	}
	
	public java.math.BigDecimal getMaximumHold() {
		return this.maximumHold;
	}
	public void setMaximumHold(java.math.BigDecimal maximumHold) {
		this.maximumHold = maximumHold;
	}
	
	
	@Override
    public String toString() {
    	StringBuilder sb = new StringBuilder(this.getClass().getSimpleName())
    		.append("{")
    		.append("id=").append(this.id)
    		.append(",giftCode=").append(this.giftCode)
    		.append(",productCode=").append(this.productCode)
    		.append(",effectiveDate=").append(this.effectiveDate)
    		.append(",term=").append(this.term)
    		.append(",giftPrice=").append(this.giftPrice)
    		.append(",minimumHold=").append(this.minimumHold)
    		.append(",maximumHold=").append(this.maximumHold)
    		.append("}");
    	
    	return sb.toString();
    }
	
}
