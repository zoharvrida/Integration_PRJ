package bdsm.model;


/**
 * 
 * @author v00019372
 */
@SuppressWarnings("serial")
public class SknNgClearingRegion extends BaseModel implements java.io.Serializable {
	private Integer codSector;
	private String codZone;
	private String namSector;
	
	
	public Integer getCodSector() {
		return this.codSector;
	}
	public void setCodSector(Integer codSector) {
		this.codSector = codSector;
	}
	
	public String getCodZone() {
		return this.codZone;
	}
	public void setCodZone(String codZone) {
		this.codZone = codZone;
	}
	
	public String getNamSector() {
		return this.namSector;
	}
	public void setNamSector(String namSector) {
		this.namSector = namSector;
	}
	
}
