package bdsm.fcr.model;


/**
 * @author v00020800
 */
@SuppressWarnings("serial")
public class BDSMBICloseRate implements java.io.Serializable {

	private BDSMBICloseRatePK compositeId;
	private Integer ccyCod;
	private Double midRate;
	private Double conversionUSD;
	private java.sql.Timestamp dtmInsertLog;
	
	
	public BDSMBICloseRatePK getCompositeId() {
		return this.compositeId;
	}
	public void setCompositeId(BDSMBICloseRatePK compositeId) {
		this.compositeId = compositeId;
	}
	
	public Integer getCcyCod() {
		return this.ccyCod;
	}
	public void setCcyCod(Integer ccyCod) {
		this.ccyCod = ccyCod;
	}
	
	public Double getMidRate() {
		return this.midRate;
	}
	public void setMidRate(Double midRate) {
		this.midRate = midRate;
	}
	
	public Double getConversionUSD() {
		return this.conversionUSD;
	}
	public void setConversionUSD(Double conversionUSD) {
		this.conversionUSD = conversionUSD;
	}
	
	public java.sql.Timestamp getDtmInsertLog() {
		return this.dtmInsertLog;
	}
	public void setDtmInsertLog(java.sql.Timestamp dtmInsertLog) {
		this.dtmInsertLog = dtmInsertLog;
	}
	
}
