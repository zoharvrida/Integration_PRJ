package bdsm.fcr.model;


/**
 * 
 * @author v00019372
 */
@SuppressWarnings("serial")
public class ChProdMastPK implements java.io.Serializable {
	private Integer codProd;
	private String flgMntStatus;
	private Integer codEntityVpd;
	
	
	public ChProdMastPK() {}
	
	public ChProdMastPK(Integer codProd, String flgMntStatus, Integer codEntityVpd) {
		this.codProd = codProd;
		this.flgMntStatus = flgMntStatus;
		this.codEntityVpd = codEntityVpd;
	}
	
	
	public Integer getCodProd() {
		return this.codProd;
	}
	public void setCodProd(Integer codProd) {
		this.codProd = codProd;
	}
	
	public String getFlgMntStatus() {
		return this.flgMntStatus;
	}
	public void setFlgMntStatus(String flgMntStatus) {
		this.flgMntStatus = flgMntStatus;
	}
	
	public Integer getCodEntityVpd() {
		return this.codEntityVpd;
	}
	public void setCodEntityVpd(Integer codEntityVpd) {
		this.codEntityVpd = codEntityVpd;
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(this.getClass().getSimpleName())
			.append("{")
			.append(",codProd=").append(this.codProd)
			.append(",flgMntStatus=").append(this.flgMntStatus)
			.append(",codEntityVpd=").append(this.codEntityVpd)
			.append("}");
		
		return sb.toString();
	}
}
