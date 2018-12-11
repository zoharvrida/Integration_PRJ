package bdsm.scheduler.model;

/**
 * 
 * @author bdsm
 */
public class TmpBdgwFooter extends TmpBdgwParent implements java.io.Serializable {
	private String noRecord;	
	
	
    /**
     * 
     */
    public TmpBdgwFooter() {
		this.setRecId(99);
		this.setRecordType("99");
		this.setRecordName("Footer");
	}
	
	
    /**
     * 
     * @return
     */
    public String getNoRecord() {
		return noRecord;
	}
    /**
     * 
     * @param noRecord
     */
    public void setNoRecord(String noRecord) {
		this.noRecord = noRecord;
	}
	
}
