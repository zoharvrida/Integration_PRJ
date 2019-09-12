package bdsm.fcr.model;


/**
 * 
 * @author v00019372
 */
@SuppressWarnings("serial")
public class BaCcyCode implements java.io.Serializable {
	private BaCcyCodePK compositeId;
	private String namCcyShort;
	private String namCurrency;
	private Byte ctrCcyDec;
	private Short codCcySwift;
	
	
	public BaCcyCodePK getCompositeId() {
		return this.compositeId;
	}
	public void setCompositeId(BaCcyCodePK compositeId) {
		this.compositeId = compositeId;
	}
	
	public String getNamCcyShort() {
		return this.namCcyShort;
	}
	public void setNamCcyShort(String namCcyShort) {
		this.namCcyShort = namCcyShort;
	}
	
	public String getNamCurrency() {
		return this.namCurrency;
	}
	public void setNamCurrency(String namCurrency) {
		this.namCurrency = namCurrency;
	}
	public Byte getCtrCcyDec() {
		return this.ctrCcyDec;
	}
	public void setCtrCcyDec(Byte ctrCcyDec) {
		this.ctrCcyDec = ctrCcyDec;
	}
	
	public Short getCodCcySwift() {
		return this.codCcySwift;
	}
	public void setCodCcySwift(Short codCcySwift) {
		this.codCcySwift = codCcySwift;
	}
	
}
