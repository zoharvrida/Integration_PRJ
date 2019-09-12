package bdsm.scheduler.model;

public class TmpBdgwFooter extends TmpBdgwParent implements java.io.Serializable {
	private String noRecord;	
	
	
	public TmpBdgwFooter() {
		this.setRecId(99);
		this.setRecordType("99");
		this.setRecordName("Footer");
	}
	
	
	public String getNoRecord() {
		return noRecord;
	}
	public void setNoRecord(String noRecord) {
		this.noRecord = noRecord;
	}
	
}
