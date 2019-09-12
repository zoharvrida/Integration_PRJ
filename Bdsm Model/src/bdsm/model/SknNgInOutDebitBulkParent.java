package bdsm.model;


/**
 * 
 * @author v00019372
 */
@SuppressWarnings("serial")
public abstract class SknNgInOutDebitBulkParent extends bdsm.model.BaseModel implements java.io.Serializable {
	private SknNgPK compositeId;
	private String tipeRecord;
	private String kodeDKE;
	
	
	
	public SknNgPK getCompositeId() {
		return this.compositeId;
	}
	public void setCompositeId(SknNgPK compositeId) {
		this.compositeId = compositeId;
	}
	public String getTipeRecord() {
		return this.tipeRecord;
	}
	public void setTipeRecord(String tipeRecord) {
		this.tipeRecord = tipeRecord;
	}
	public String getKodeDKE() {
		return this.kodeDKE;
	}
	public void setKodeDKE(String kodeDKE) {
		this.kodeDKE = kodeDKE;
	}
	
	
	
    @Override
    public String toString() {
    	StringBuilder sb = new StringBuilder()
    		.append("compositeId=").append(this.compositeId.toString())
    		.append(",tipeRecord=").append(this.tipeRecord)
    		.append(",kodeDKE=").append(this.kodeDKE)
    		;
    	
    	return sb.toString();
    }
	

}
