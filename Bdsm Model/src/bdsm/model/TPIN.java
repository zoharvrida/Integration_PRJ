package bdsm.model;


public class TPIN extends bdsm.model.BaseModel implements java.io.Serializable {
	private String cardNo;
	private java.util.Date b24UpdatedTime;
	private String IVRStatus;
	private String filler;
	
	
	public TPIN() {}
	
	public TPIN(String cardNo, java.util.Date b24UpdatedTime, String IVRStatus, String filler) {
		this.cardNo = cardNo;
		this.b24UpdatedTime = b24UpdatedTime;
		this.IVRStatus = IVRStatus;
		this.filler = filler;
	}
	
	
	public String getCardNo() {
		return this.cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	
	public java.util.Date getB24UpdatedTime() {
		return this.b24UpdatedTime;
	}
	public void setB24UpdatedTime(java.util.Date b24UpdatedTime) {
		this.b24UpdatedTime = b24UpdatedTime;
	}
	
	public String getIVRStatus() {
		return this.IVRStatus;
	}
	public void setIVRStatus(String IVRStatus) {
		this.IVRStatus = IVRStatus;
	}
	
	public String getFiller() {
		return this.filler;
	}
	public void setFiller(String filler) {
		this.filler = filler;
	}
	
	
	
	/* Hibernate to Database properties */
	
	protected Character getIVRStatusDB() {
		return ((this.IVRStatus == null)? null: this.IVRStatus.charAt(0));
	}
	protected void setIVRStatusDB(Character IVRStatusDB) {
		this.IVRStatus = (IVRStatusDB == null)? null: IVRStatusDB.toString();
	}
	
	protected Character getFillerDB() {
		return ((this.filler == null)? null: this.filler.charAt(0));
	}
	protected void setFillerDB(Character fillerDB) {
		this.filler = (fillerDB == null)? null: fillerDB.toString();
	}
	
}
