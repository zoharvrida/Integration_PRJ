package bdsm.model;


/**
 * @author v00019372
 */
@SuppressWarnings("serial")
public class MPProductMIS extends BaseModel implements java.io.Serializable {
	private Integer codeProduct;
	private String codeMIS;
	private String productMISName;
	
	
	public MPProductMIS() {}
	
	public MPProductMIS(Integer codeProduct, String codeMIS) {
		this.codeProduct = codeProduct;
		this.codeMIS = codeMIS;
	}
	
	
	public Integer getCodeProduct() {
		return this.codeProduct;
	}
	public void setCodeProduct(Integer codeProduct) {
		this.codeProduct = codeProduct;
	}
	
	public String getCodeMIS() {
		return this.codeMIS;
	}
	public void setCodeMIS(String codeMIS) {
		this.codeMIS = codeMIS;
	}
	
	public String getProductMISName() {
		return this.productMISName;
	}
	public void setProductMISName(String productMISName) {
		this.productMISName = productMISName;
	}
	
	
	@Override
	public String toString() {
    	StringBuilder sb = new StringBuilder(this.getClass().getSimpleName())
			.append("{")
			.append(",codeProduct=").append(this.codeProduct)
			.append(",codeMIS=").append(this.codeMIS)
			.append(",productMISName=").append(this.productMISName)
			.append("}");
    	
		return sb.toString();
	}

}
