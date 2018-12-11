package bdsm.fcr.model;


/**
 * @author v00019372
 */
@SuppressWarnings("serial")
public class BaSectorMast implements java.io.Serializable {
	private BaSectorMastPK compositeId;
	private String namClgSector;
	
	
	public BaSectorMastPK getCompositeId() {
		return this.compositeId;
	}
	public void setCompositeId(BaSectorMastPK compositeId) {
		this.compositeId = compositeId;
	}
	
	public String getNamClgSector() {
		return this.namClgSector;
	}
	public void setNamClgSector(String namClgSector) {
		this.namClgSector = namClgSector;
	}
	
}
