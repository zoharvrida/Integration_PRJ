package bdsm.model;


import java.math.BigDecimal;
import java.util.Date;


/**
 * @author v00019372
 */
@SuppressWarnings("serial")
public class MfcUdMaster_LLD extends BaseModel {
	private MfcUdMasterPK compositeId;
	private Date dtIssued;
	private Date dtExpiry;
	private String ccyUd;
	private BigDecimal amtLimit;
	private BigDecimal amtAvail;
	private Integer cdBranch;
	private String status;
	private String remarks;
	private BigDecimal amtLimitUsd;
	private BigDecimal amtAvailUsd;
	private BigDecimal ratFcyLim;
	private BigDecimal ratUsdLim;
	private String udTypeCategory;
	private String udTypeValue;
	private String payeeName;
	private String payeeCountry;
	private String tradingProduct;
	private String channelId;
	
	
	public MfcUdMaster_LLD() {}
	
	public MfcUdMaster_LLD(MfcUdMasterPK compositeId) {
		this.setCompositeId(compositeId);
	}
	
	
	public MfcUdMasterPK getCompositeId() {
		return this.compositeId;
	}
	public void setCompositeId(MfcUdMasterPK compositeId) {
		this.compositeId = compositeId;
	}
	
	public Date getDtIssued() {
		return this.dtIssued;
	}
	public void setDtIssued(Date dtIssued) {
		this.dtIssued = dtIssued;
	}

	public Date getDtExpiry() {
		return this.dtExpiry;
	}
	public void setDtExpiry(Date dtExpiry) {
		this.dtExpiry = dtExpiry;
	}

	public String getCcyUd() {
		return this.ccyUd;
	}
	public void setCcyUd(String ccyUd) {
		this.ccyUd = ccyUd;
	}

	public BigDecimal getAmtLimit() {
		return this.amtLimit;
	}
	public void setAmtLimit(BigDecimal amtLimit) {
		this.amtLimit = amtLimit;
	}

	public BigDecimal getAmtAvail() {
		return this.amtAvail;
	}
	public void setAmtAvail(BigDecimal amtAvail) {
		this.amtAvail = amtAvail;
	}

	public Integer getCdBranch() {
		return this.cdBranch;
	}
	public void setCdBranch(Integer cdBranch) {
		this.cdBranch = cdBranch;
	}

	public String getStatus() {
		return this.status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemarks() {
		return this.remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public BigDecimal getAmtLimitUsd() {
		return this.amtLimitUsd;
	}
	public void setAmtLimitUsd(BigDecimal amtLimitUsd) {
		this.amtLimitUsd = amtLimitUsd;
	}

	public BigDecimal getAmtAvailUsd() {
		return this.amtAvailUsd;
	}
	public void setAmtAvailUsd(BigDecimal amtAvailUsd) {
		this.amtAvailUsd = amtAvailUsd;
	}

	public BigDecimal getRatFcyLim() {
		return this.ratFcyLim;
	}
	public void setRatFcyLim(BigDecimal ratFcyLim) {
		this.ratFcyLim = ratFcyLim;
	}

	public BigDecimal getRatUsdLim() {
		return this.ratUsdLim;
	}
	public void setRatUsdLim(BigDecimal ratUsdLim) {
		this.ratUsdLim = ratUsdLim;
	}

	public String getUdTypeCategory() {
		return this.udTypeCategory;
	}
	public void setUdTypeCategory(String udTypeCategory) {
		this.udTypeCategory = udTypeCategory;
	}
	
	public String getUdTypeValue() {
		return this.udTypeValue;
	}
	public void setUdTypeValue(String udTypeValue) {
		this.udTypeValue = udTypeValue;
	}

	public String getPayeeName() {
		return this.payeeName;
	}
	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	public String getPayeeCountry() {
		return this.payeeCountry;
	}
	public void setPayeeCountry(String payeeCountry) {
		this.payeeCountry = payeeCountry;
	}

	public String getTradingProduct() {
		return this.tradingProduct;
	}
	public void setTradingProduct(String tradingProduct) {
		this.tradingProduct = tradingProduct;
	}

	public String getChannelId() {
		return this.channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}



	public String toString() {
		StringBuilder sb = new StringBuilder(this.getClass().getSimpleName())
			.append("{")
			.append("compositeId=").append(this.compositeId)
			.append(",dtIssued=").append(this.dtIssued)
			.append(",dtExpiry=").append(this.dtExpiry)
			.append(",ccyUd=").append(this.ccyUd)
			.append(",amtLimit=").append(this.amtLimit)
			.append(",amtAvail=").append(this.amtAvail)
			.append(",cdBranch=").append(this.cdBranch)
			.append(",status=").append(this.status)
			.append(",remarks=").append(this.remarks)
			.append(",amtLimitUsd=").append(this.amtLimitUsd)
			.append(",amtAvailUsd=").append(this.amtAvailUsd)
			.append(",ratFcyLim=").append(this.ratFcyLim)
			.append(",ratUsdLim=").append(this.ratUsdLim)
			.append(",udTypeCategory=").append(this.udTypeCategory)
			.append(",udTypeValue=").append(this.udTypeValue)
			.append(",payeeName=").append(this.payeeName)
			.append(",payeeCountry=").append(this.payeeCountry)
			.append(",tradingProduct=").append(this.tradingProduct)
			.append(",channelId=").append(this.channelId)
			.append("}");
		
		return sb.toString();
	}

}