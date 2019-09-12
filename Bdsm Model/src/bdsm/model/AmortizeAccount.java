package bdsm.model;


import java.math.BigDecimal;
import java.util.Date;


/**
 * 
 * @author v00019372
 */
@SuppressWarnings("serial")
public class AmortizeAccount extends BaseModel implements java.io.Serializable {
	public static final Integer STATUS_OPENED      = 1;
	public static final Integer STATUS_CANCELED    = 2;
	public static final Integer STATUS_FINISHED    = 3;
	public static final Byte    FINISH_NORMAL      = 1;
	public static final Byte    FINISH_ACCT_CLOSED = 2;
	
	
	private Integer id;
	private Integer programDetailId;
	private String accountNo;
	private Integer status;
	private Date openDate;
	private Date cancelDate;
	private String programId;
	private String programName;
	private Integer type;
	private BigDecimal giftPrice;
	private BigDecimal taxPercent;
	private BigDecimal taxAmount;
	private BigDecimal giftPriceGross;
	private BigDecimal holdAmount;
	private Boolean finish;
	private Byte finishType;
	
	
	public Integer getId() {
		return this.id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getProgramDetailId() {
		return this.programDetailId;
	}
	public void setProgramDetailId(Integer programDetailId) {
		this.programDetailId = programDetailId;
	}
	
	public String getAccountNo() {
		return this.accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	
	public Integer getStatus() {
		return this.status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public java.util.Date getOpenDate() {
		return this.openDate;
	}
	public void setOpenDate(java.util.Date openDate) {
		this.openDate = openDate;
	}
	
	public java.util.Date getCancelDate() {
		return this.cancelDate;
	}
	public void setCancelDate(java.util.Date cancelDate) {
		this.cancelDate = cancelDate;
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
	
	public Integer getType() {
		return this.type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	public BigDecimal getGiftPrice() {
		return this.giftPrice;
	}
	public void setGiftPrice(BigDecimal giftPrice) {
		this.giftPrice = giftPrice;
	}
	
	public BigDecimal getTaxPercent() {
		return this.taxPercent;
	}
	public void setTaxPercent(BigDecimal taxPercent) {
		this.taxPercent = taxPercent;
	}
	
	public BigDecimal getTaxAmount() {
		return this.taxAmount;
	}
	public void setTaxAmount(BigDecimal taxAmount) {
		this.taxAmount = taxAmount;
	}
	
	public BigDecimal getGiftPriceGross() {
		return this.giftPriceGross;
	}
	public void setGiftPriceGross(BigDecimal giftPriceGross) {
		this.giftPriceGross = giftPriceGross;
	}
	
	public java.math.BigDecimal getHoldAmount() {
		return this.holdAmount;
	}
	public void setHoldAmount(java.math.BigDecimal holdAmount) {
		this.holdAmount = holdAmount;
	}
	
	public Boolean isFinish() {
		return this.finish;
	}
	public void setFinish(Boolean finish) {
		this.finish = finish;
	}
	
	public Byte getFinishType() {
		return this.finishType; 
	}
	public void setFinishType(Byte finishType) {
		this.finishType = finishType;
	}
	
	
	@Override
    public String toString() {
    	StringBuilder sb = new StringBuilder(this.getClass().getSimpleName())
    		.append("{")
    		.append("id=").append(this.id)
    		.append(",programDetailId=").append(this.programDetailId)
    		.append(",accountNo=").append(this.accountNo)
    		.append(",status=").append(this.status)
    		.append(",openDate=").append(this.openDate)
    		.append(",cancelDate=").append(this.cancelDate)
    		.append(",programId=").append(this.programId)
    		.append(",programName=").append(this.programName)
    		.append(",type=").append(this.type)
    		.append(",giftPrice=").append(this.giftPrice)
    		.append(",taxPercent=").append(this.taxPercent)
    		.append(",taxAmount=").append(this.taxAmount)
    		.append(",giftPriceGross=").append(this.giftPriceGross)
    		.append(",holdAmount=").append(this.holdAmount)
    		.append(",finish=").append(this.finish)
    		.append(",finishType=").append(this.finishType)
    		.append("}");
    	
    	return sb.toString();
    }
	
}
