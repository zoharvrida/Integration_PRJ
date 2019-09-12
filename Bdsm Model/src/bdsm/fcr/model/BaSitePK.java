package bdsm.fcr.model;


/**
 * @author v00019372
 */
@SuppressWarnings("serial")
public class BaSitePK implements java.io.Serializable {
	private String macroName;
	private String macroValue;
	private String flgMntStatus;
	private Integer codEntityVPD;
	
	
	public String getMacroName() {
		return this.macroName;
	}
	public void setMacroName(String macroName) {
		this.macroName = macroName;
	}
	
	public String getMacroValue() {
		return this.macroValue;
	}
	public void setMacroValue(String macroValue) {
		this.macroValue = macroValue;
	}
	
	public String getFlgMntStatus() {
		return this.flgMntStatus;
	}
	public void setFlgMntStatus(String flgMntStatus) {
		this.flgMntStatus = flgMntStatus;
	}
	
	public Integer getCodEntityVPD() {
		return this.codEntityVPD;
	}
	public void setCodEntityVPD(Integer codEntityVPD) {
		this.codEntityVPD = codEntityVPD;
	}
	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(this.getClass().getSimpleName())
			.append("{")
			.append(",macroName=").append(this.macroName)
			.append(",macroValue=").append(this.macroValue)
			.append(",flgMntStatus=").append(this.flgMntStatus)
			.append(",codEntityVPD=").append(this.codEntityVPD)
			.append("}");
	
		return sb.toString();
	}
	
}
