package bdsm.model;


import java.math.BigDecimal;


/**
 * @author v00019372
 */
@SuppressWarnings("serial")
public class MPClassDetails extends BaseModel implements java.io.Serializable {
	private Integer codeClass;
	private Integer codeComponent;
	private BigDecimal minimumValue;
	private BigDecimal maximumValue;
	private BigDecimal defaultValue;
	
	
	public Integer getCodeClass() {
		return this.codeClass;
	}
	public void setCodeClass(Integer codeClass) {
		this.codeClass = codeClass;
	}
	
	public Integer getCodeComponent() {
		return this.codeComponent;
	}
	public void setCodeComponent(Integer codeComponent) {
		this.codeComponent = codeComponent;
	}
	
	public BigDecimal getMinimumValue() {
		return this.minimumValue;
	}
	public void setMinimumValue(BigDecimal minimumValue) {
		this.minimumValue = minimumValue;
	}
	
	public BigDecimal getMaximumValue() {
		return this.maximumValue;
	}
	public void setMaximumValue(BigDecimal maximumValue) {
		this.maximumValue = maximumValue;
	}
	
	public BigDecimal getDefaultValue() {
		return this.defaultValue;
	}
	public void setDefaultValue(BigDecimal defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	
	@Override
	public String toString() {
    	StringBuilder sb = new StringBuilder(this.getClass().getSimpleName())
			.append("{")
			.append(",codeClass=").append(this.codeClass)
			.append(",codeComponent=").append(this.codeComponent)
			.append(",minimumValue=").append(this.minimumValue)
			.append(",maximumValue=").append(this.maximumValue)
			.append(",defaultValue=").append(this.defaultValue)
			.append("}");
    	
		return sb.toString();
	}

}
