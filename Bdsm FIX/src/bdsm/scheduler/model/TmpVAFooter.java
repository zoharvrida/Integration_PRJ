package bdsm.scheduler.model;

/**
 * 
 * @author bdsm
 */
public class TmpVAFooter extends bdsm.model.BaseModel {
	private TmpVAPK id;
	private Integer noOfRecord;
	private Integer rowNoHeader;
	private Integer lengthInFile;
	
	
    /**
     * 
     */
    public TmpVAFooter() {}
	
    /**
     * 
     * @param batchNo
     * @param rowNo
     */
    public TmpVAFooter(String batchNo, int rowNo) {
		this.setId(new TmpVAPK(batchNo, rowNo));
	}
	
	
    /**
     * 
     * @return
     */
    public TmpVAPK getId() {
		return id;
	}
    /**
     * 
     * @param id
     */
    public void setId(TmpVAPK id) {
		this.id = id;
	}
	
    /**
     * 
     * @return
     */
    public Integer getNoOfRecord() {
		return noOfRecord;
	}
    /**
     * 
     * @param noOfRecord
     */
    public void setNoOfRecord(Integer noOfRecord) {
		this.noOfRecord = noOfRecord;
	}
	
    /**
     * 
     * @return
     */
    public Integer getRowNoHeader() {
		return rowNoHeader;
	}
    /**
     * 
     * @param rowNoHeader
     */
    public void setRowNoHeader(Integer rowNoHeader) {
		this.rowNoHeader = rowNoHeader;
	}
	
    /**
     * 
     * @return
     */
    public Integer getLengthInFile() {
		return lengthInFile;
	}
    /**
     * 
     * @param lengthInFile
     */
    public void setLengthInFile(Integer lengthInFile) {
		this.lengthInFile = lengthInFile;
	}
	
	
    /**
     * 
     * @return
     */
    public String getBatchNo() {
		return this.getId().getBatchNo();
	}
	
    /**
     * 
     * @return
     */
    public int getRowNo() {
		return this.getId().getRowNo();
	}
	
}
