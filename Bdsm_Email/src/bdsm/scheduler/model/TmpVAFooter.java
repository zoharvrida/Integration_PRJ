package bdsm.scheduler.model;

public class TmpVAFooter extends bdsm.model.BaseModel {
	private TmpVAPK id;
	private Integer noOfRecord;
	private Integer rowNoHeader;
	private Integer lengthInFile;
	
	
	public TmpVAFooter() {}
	
	public TmpVAFooter(String batchNo, int rowNo) {
		this.setId(new TmpVAPK(batchNo, rowNo));
	}
	
	
	public TmpVAPK getId() {
		return id;
	}
	public void setId(TmpVAPK id) {
		this.id = id;
	}
	
	public Integer getNoOfRecord() {
		return noOfRecord;
	}
	public void setNoOfRecord(Integer noOfRecord) {
		this.noOfRecord = noOfRecord;
	}
	
	public Integer getRowNoHeader() {
		return rowNoHeader;
	}
	public void setRowNoHeader(Integer rowNoHeader) {
		this.rowNoHeader = rowNoHeader;
	}
	
	public Integer getLengthInFile() {
		return lengthInFile;
	}
	public void setLengthInFile(Integer lengthInFile) {
		this.lengthInFile = lengthInFile;
	}
	
	
	public String getBatchNo() {
		return this.getId().getBatchNo();
	}
	
	public int getRowNo() {
		return this.getId().getRowNo();
	}
	
}
