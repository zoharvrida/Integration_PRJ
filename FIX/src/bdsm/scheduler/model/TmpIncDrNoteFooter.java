package bdsm.scheduler.model;


/**
*
* @author v00019372
*/
@SuppressWarnings("serial")
public class TmpIncDrNoteFooter extends TmpIncDrNoteParent implements java.io.Serializable {
	private Integer checksum;
	
	
    /**
     * 
     * @return
     */
    public Integer getChecksum() {
		return this.checksum;
	}
    /**
     * 
     * @param checksum
     */
    public void setChecksum(Integer checksum) {
		this.checksum = checksum;
	}
	
	
    @Override
    public String toString() {
    	StringBuilder sb = new StringBuilder(this.getClass().getSimpleName())
    		.append("{")
    		.append(super.toString())
    		.append(",checksum=").append(this.checksum)
    		.append("}");
    	
    	return sb.toString();
    }
	
}
