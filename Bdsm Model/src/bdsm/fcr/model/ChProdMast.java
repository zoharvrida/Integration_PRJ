package bdsm.fcr.model;


/**
 * 
 * @author v00019372
 */
@SuppressWarnings("serial")
public class ChProdMast implements java.io.Serializable {
	public static final String COD_TYPE_PROD_SAVING  = "L";
	public static final String COD_TYPE_PROD_CURRENT = "A";
	
	private ChProdMastPK compositeId;
	private Integer codCcy;
	private String codTypProd;
	private String codProdType;
	private String namProduct;
	private java.util.Date datProdExpiry;
	
	
	public ChProdMastPK getCompositeId() {
		return this.compositeId;
	}
	public void setCompositeId(ChProdMastPK compositeId) {
		this.compositeId = compositeId;
	}
	
	public Integer getCodCcy() {
		return this.codCcy;
	}
	public void setCodCcy(Integer codCcy) {
		this.codCcy = codCcy;
	}
	
	public String getCodTypProd() {
		return this.codTypProd;
	}
	public void setCodTypProd(String codTypProd) {
		this.codTypProd = codTypProd;
	}
	
	public String getCodProdType() {
		return this.codProdType;
	}
	public void setCodProdType(String codProdType) {
		this.codProdType = codProdType;
	}
	
	public String getNamProduct() {
		return this.namProduct;
	}
	public void setNamProduct(String namProduct) {
		this.namProduct = namProduct;
	}
	
	public java.util.Date getDatProdExpiry() {
		return this.datProdExpiry;
	}
	public void setDatProdExpiry(java.util.Date datProdExpiry) {
		this.datProdExpiry = datProdExpiry;
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(this.getClass().getSimpleName())
			.append("{")
			.append(",compositeId=").append(this.compositeId)
			.append(",codCcy=").append(this.codCcy)
			.append(",codTypProd=").append(this.codTypProd)
			.append(",codProdType=").append(this.codProdType)
			.append(",namProduct=").append(this.namProduct)
			.append(",datProdExpiry=").append(this.datProdExpiry)
			.append("}");
		
		return sb.toString();
	}
}
