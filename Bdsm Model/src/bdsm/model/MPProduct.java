package bdsm.model;


/**
 * @author v00019372
 */
@SuppressWarnings("serial")
public class MPProduct extends BaseModel implements java.io.Serializable {
	private Integer codeProduct;
	
	
	public Integer getCodeProduct() {
		return this.codeProduct;
	}
	public void setCodeProduct(Integer codeProduct) {
		this.codeProduct = codeProduct;
	}
	
	
	@Override
	public String toString() {
    	StringBuilder sb = new StringBuilder(this.getClass().getSimpleName())
			.append("{")
			.append(",codeProduct=").append(this.codeProduct)
			.append("}");
    	
		return sb.toString();
	}
}
