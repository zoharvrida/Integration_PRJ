package bdsm.fcr.model;


/**
 * 
 * @author v00019372
 *
 */
@SuppressWarnings("serial")
public class SPAJReference extends bdsm.model.BaseModel {
	private String referenceNo;
	private String outData;
	private String outStatus;
	private String outDesc1;
	private String inData;
	private String inStatus;
	private String inDesc1;
	private String inDesc2;
	private Boolean locked;
	
	
	public SPAJReference() {}
	
	public SPAJReference(String referenceNo) {
		this.referenceNo = referenceNo;
	}
	
	
	public String getReferenceNo() {
		return this.referenceNo;
	}
	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}
	
	public String getOutData() {
		return this.outData;
	}
	public void setOutData(String outData) {
		this.outData = outData;
	}
	
	public String getOutStatus() {
		return this.outStatus;
	}
	public void setOutStatus(String outStatus) {
		this.outStatus = outStatus;
	}
	
	public String getOutDesc1() {
		return this.outDesc1;
	}
	public void setOutDesc1(String outDesc1) {
		this.outDesc1 = outDesc1;
	}
	
	public java.sql.Timestamp getOutSysdate() {
		return this.getDtmCreated();
	}
	
	public String getInData() {
		return this.inData;
	}
	public void setInData(String inData) {
		this.inData = inData;
	}
	
	public String getInStatus() {
		return this.inStatus;
	}
	public void setInStatus(String inStatus) {
		this.inStatus = inStatus;
	}
	
	public String getInDesc1() {
		return this.inDesc1;
	}
	public void setInDesc1(String inDesc1) {
		this.inDesc1 = inDesc1;
	}
	
	public String getInDesc2() {
		return this.inDesc2;
	}
	public void setInDesc2(String inDesc2) {
		this.inDesc2 = inDesc2;
	}
	
	public Boolean isLocked() {
		return this.locked;
	}
	public void setLocked(Boolean locked) {
		this.locked = locked;
	}
	
	public java.sql.Timestamp getInSysdate() {
		return this.getDtmUpdated();
	}
	
	
}
