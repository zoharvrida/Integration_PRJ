package bdsm.model;


/**
 * @author v00019372
 */
@SuppressWarnings("serial")
public class SkhtCoreMapping extends BaseModel {
	private String moduleName;
	private String coreValue;
	private String skhtValue;
	
	
	public String getModuleName() {
		return this.moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	
	public String getCoreValue() {
		return this.coreValue;
	}
	public void setCoreValue(String coreValue) {
		this.coreValue = coreValue;
	}
	
	public String getSkhtValue() {
		return this.skhtValue;
	}
	public void setSkhtValue(String skhtValue) {
		this.skhtValue = skhtValue;
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(this.getClass().getSimpleName())
			.append("{")
			.append("moduleName=").append(this.moduleName)
			.append("coreValue=").append(this.coreValue)
			.append("skhtValue=").append(this.skhtValue)
			.append("}");

		return sb.toString();
	}

}
