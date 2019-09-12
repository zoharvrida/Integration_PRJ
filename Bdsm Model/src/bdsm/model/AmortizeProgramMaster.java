package bdsm.model;


/**
 * 
 * @author v00019372
 */
@SuppressWarnings("serial")
public class AmortizeProgramMaster extends BaseModel implements java.io.Serializable {
	public static final Integer AMORTIZE_TYPE_ADVANCED        = 1;
	public static final Integer AMORTIZE_TYPE_ARREAR          = 2;
	public static final Integer AMORTIZE_METHOD_STRAIGHT_LINE = 1;
	public static final Integer AMORTIZE_METHOD_EIR           = 2;
	
	private AmortizeProgramMasterPK compositeId;
	private String giftName;
	private Integer type;
	private Boolean status;
	private String programId;
	private String programName;
	private String idAccrual;
	private Double taxPercent;
	private Integer amortizeMethod;
	private Boolean voucher;
	
	
	public AmortizeProgramMasterPK getCompositeId() {
		return this.compositeId;
	}
	public void setCompositeId(AmortizeProgramMasterPK compositeId) {
		this.compositeId = compositeId;
	}
	
	public String getGiftName() {
		return this.giftName;
	}
	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}
	
	public Integer getType() {
		return this.type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	public Boolean getStatus() {
		return this.status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	
	public String getProgramId() {
		return this.programId;
	}
	public void setProgramId(String programId) {
		this.programId = programId;
	}
	
	public String getProgramName() {
		return this.programName;
	}
	public void setProgramName(String programName) {
		this.programName = programName;
	}
	
	public String getIdAccrual() {
		return this.idAccrual;
	}
	public void setIdAccrual(String idAccrual) {
		this.idAccrual = idAccrual;
	}
	
	public Double getTaxPercent() {
		return this.taxPercent;
	}
	public void setTaxPercent(Double taxPercent) {
		this.taxPercent = taxPercent;
	}
	
	public Integer getAmortizeMethod() {
		return this.amortizeMethod;
	}
	public void setAmortizeMethod(Integer amortizeMethod) {
		this.amortizeMethod = amortizeMethod;
	}
	
	public Boolean getVoucher() {
		return this.voucher;
	}
	public void setVoucher(Boolean voucher) {
		this.voucher = voucher;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AmortizeProgramMaster) {
			AmortizeProgramMaster other = (AmortizeProgramMaster) obj;
			if (
					((this.compositeId == null)? (other.compositeId == null): this.compositeId.equals(other.compositeId))
					&& ((this.giftName == null)? (other.giftName == null): this.giftName.equals(other.giftName))
					&& ((this.type == null)? (other.type == null): this.type.equals(other.type))
					&& ((this.status == null)? (other.status == null): this.status.equals(other.status))
					&& ((this.programId == null)? (other.programId == null): this.programId.equals(other.programId))
					&& ((this.programName == null)? (other.programName == null): this.programName.equals(other.programName))
					&& ((this.idAccrual == null)? (other.idAccrual == null): this.idAccrual.equals(other.idAccrual))
					&& ((this.taxPercent == null)? (other.taxPercent == null): this.taxPercent.equals(other.taxPercent))
					&& ((this.amortizeMethod == null)? (other.amortizeMethod == null): this.amortizeMethod.equals(other.amortizeMethod))
					&& ((this.voucher == null)? (other.voucher == null): this.voucher.equals(other.voucher))
				)
					return true;
		}
		
		return false;
	}
	
    @Override
    public String toString() {
    	StringBuilder sb = new StringBuilder(this.getClass().getSimpleName())
    		.append("compositeId=").append(this.compositeId.toString())
    		.append(",giftName=").append(this.giftName)
    		.append(",type=").append(this.type)
    		.append(",status=").append(this.status)
    		.append(",programId=").append(this.programId)
    		.append(",programName=").append(this.programName)
    		.append(",idAccrual=").append(this.idAccrual)
    		.append(",taxPercent=").append(this.taxPercent)
    		.append(",amortizeMethod=").append(this.amortizeMethod)
    		.append(",voucher=").append(this.voucher)
    		;
    	
    	return sb.toString();
    }

}
