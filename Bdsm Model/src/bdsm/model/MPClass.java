package bdsm.model;


/**
 * @author v00019372
 */
@SuppressWarnings("serial")
public class MPClass extends BaseModel implements java.io.Serializable {
	private Integer code;
	private Integer codeProduct;
	private String codeMIS;
	private String name;
	
	
	public Integer getCode() {
		return this.code;
	}
	public void setCode(Integer code) {
		this.code = code;
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
	
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	@Override
	public String toString() {
    	StringBuilder sb = new StringBuilder(this.getClass().getSimpleName())
			.append("{")
			.append("code=").append(this.code)
			.append(",codeProduct=").append(this.codeProduct)
			.append(",codeMIS=").append(this.codeMIS)
			.append(",name=").append(this.name)
			.append("}");
    	
		return sb.toString();
	}
	
}
