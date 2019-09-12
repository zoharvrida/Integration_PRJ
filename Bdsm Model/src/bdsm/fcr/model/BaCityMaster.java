package bdsm.fcr.model;


/**
 * @author v00019372
 */
@SuppressWarnings("serial")
public class BaCityMaster implements java.io.Serializable {
	private BaCityMasterPK compositeId;
	private String cityName;
	
	
	public BaCityMasterPK getCompositeId() {
		return this.compositeId;
	}
	public void setCompositeId(BaCityMasterPK compositeId) {
		this.compositeId = compositeId;
	}
	
	public String getCityName() {
		return this.cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
}
