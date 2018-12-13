package bdsm.fcr.model;


/**
 * @author v00019372
 */
@SuppressWarnings("serial")
public class StRoutingNoDtls implements java.io.Serializable {
	private StRoutingNoDtlsPK compositeId;
	private Integer codSector;
	private Integer codCity;
	private Integer codProvince;
	
	
	public StRoutingNoDtlsPK getCompositeId() {
		return this.compositeId;
	}
	public void setCompositeId(StRoutingNoDtlsPK compositeId) {
		this.compositeId = compositeId;
	}
	
	public Integer getCodSector() {
		return this.codSector;
	}
	public void setCodSector(Integer codSector) {
		this.codSector = codSector;
	}
	
	public Integer getCodCity() {
		return this.codCity;
	}
	public void setCodCity(Integer codCity) {
		this.codCity = codCity;
	}
	
	public Integer getCodProvince() {
		return this.codProvince;
	}
	public void setCodProvince(Integer codProvince) {
		this.codProvince = codProvince;
	}
	
}
