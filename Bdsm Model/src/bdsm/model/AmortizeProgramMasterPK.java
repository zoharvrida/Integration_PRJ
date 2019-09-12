package bdsm.model;


/**
 * 
 * @author v00019372
 */
@SuppressWarnings("serial")
public class AmortizeProgramMasterPK implements java.io.Serializable {
	private String giftCode;
	private Integer productCode;
	
	
	public AmortizeProgramMasterPK() {}
	
	public AmortizeProgramMasterPK(String giftCode, Integer productCode) {
		this.giftCode = giftCode;
		this.productCode = productCode;
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
	
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AmortizeProgramMasterPK) {
			AmortizeProgramMasterPK other = (AmortizeProgramMasterPK) obj;
			if (
					((this.giftCode == null)? (other.giftCode == null): (this.giftCode.equals(other.giftCode))) 
					&& ((this.productCode == null)? (other.productCode == null): (this.productCode.equals(other.productCode)))
				)
				return true;
		}
			
		return false;
	}
	
	@Override
    public String toString() {
    	StringBuilder sb = new StringBuilder(this.getClass().getSimpleName())
    		.append("{")
    		.append("giftCode=").append(this.giftCode)
    		.append(",productCode=").append(this.productCode)
    		.append("}");
    	
    	return sb.toString();
    }
	
}
