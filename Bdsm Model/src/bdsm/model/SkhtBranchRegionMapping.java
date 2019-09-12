package bdsm.model;


/**
 * @author v00022309
 */
@SuppressWarnings("serial")
public class SkhtBranchRegionMapping extends BaseModel {
	private Integer branchCode;
	private String branchName;
	private String branchAddress;
	private Integer coreProvCode;
	private String coreProvName;
	private Integer coreCityCode;
	private String coreCityName;
	private Integer skhtProvCode;
	private String skhtProvName;
	private Integer skhtCityCode;
	private String skhtCityName;
	
	
	public Integer getBranchCode() {
		return this.branchCode;
	}
	public void setBranchCode(Integer branchCode) {
		this.branchCode = branchCode;
	}
	
	public String getBranchName() {
		return this.branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	
	public String getBranchAddress() {
		return this.branchAddress;
	}
	public void setBranchAddress(String branchAddress) {
		this.branchAddress = branchAddress;
	}
	
	public Integer getCoreProvCode() {
		return this.coreProvCode;
	}
	public void setCoreProvCode(Integer coreProvCode) {
		this.coreProvCode = coreProvCode;
	}
	
	public String getCoreProvName() {
		return this.coreProvName;
	}
	public void setCoreProvName(String coreProvName) {
		this.coreProvName = coreProvName;
	}
	
	public Integer getCoreCityCode() {
		return this.coreCityCode;
	}
	public void setCoreCityCode(Integer coreCityCode) {
		this.coreCityCode = coreCityCode;
	}
	
	public String getCoreCityName() {
		return this.coreCityName;
	}
	public void setCoreCityName(String coreCityName) {
		this.coreCityName = coreCityName;
	}
	
	public Integer getSkhtProvCode() {
		return this.skhtProvCode;
	}
	public void setSkhtProvCode(Integer skhtProvCode) {
		this.skhtProvCode = skhtProvCode;
	}
	
	public String getSkhtProvName() {
		return this.skhtProvName;
	}
	public void setSkhtProvName(String skhtProvName) {
		this.skhtProvName = skhtProvName;
	}
	
	public Integer getSkhtCityCode() {
		return this.skhtCityCode;
	}
	public void setSkhtCityCode(Integer skhtCityCode) {
		this.skhtCityCode = skhtCityCode;
	}
	
	public String getSkhtCityName() {
		return this.skhtCityName;
	}
	public void setSkhtCityName(String skhtCityName) {
		this.skhtCityName = skhtCityName;
	}

	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(this.getClass().getSimpleName())
			.append("{")
			.append("branchCode=").append(this.branchCode)
			.append("branchName=").append(this.branchName)
			.append("branchAddress=").append(this.branchAddress)
			.append("coreProvCode=").append(this.coreProvCode)
			.append("coreProvName=").append(this.coreProvName)
			.append("coreCityCode=").append(this.coreCityCode)
			.append("coreCityName=").append(this.coreCityName)
			.append("skhtProvCode=").append(this.skhtProvCode)
			.append("skhtProvName=").append(this.skhtProvName)
			.append("skhtCityCode=").append(this.skhtCityCode)
			.append("skhtCityName=").append(this.skhtCityName)
			.append("}");

		return sb.toString();
	}
	
}
