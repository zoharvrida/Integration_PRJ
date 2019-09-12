package bdsm.scheduler.model;

public class TmpVAPK implements java.io.Serializable {
	private String batchNo;
	private Integer rowNo;
	
	public TmpVAPK() {}
	
	public TmpVAPK(String batchNo, Integer rowNo) {
		this.batchNo = batchNo;
		this.rowNo = rowNo;
	}
	
	
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	
	public Integer getRowNo() {
		return rowNo;
	}
	public void setRowNo(Integer rowNo) {
		this.rowNo = rowNo;
	}
}
