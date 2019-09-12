package bdsm.scheduler.model;

/**
 * 
 * @author bdsm
 */
public class TmpVAPK implements java.io.Serializable {
	private String batchNo;
	private Integer rowNo;
	
    /**
     * 
     */
    public TmpVAPK() {}
	
    /**
     * 
     * @param batchNo
     * @param rowNo
     */
    public TmpVAPK(String batchNo, Integer rowNo) {
		this.batchNo = batchNo;
		this.rowNo = rowNo;
	}
	
	
    /**
     * 
     * @return
     */
    public String getBatchNo() {
		return batchNo;
	}
    /**
     * 
     * @param batchNo
     */
    public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	
    /**
     * 
     * @return
     */
    public Integer getRowNo() {
		return rowNo;
	}
    /**
     * 
     * @param rowNo
     */
    public void setRowNo(Integer rowNo) {
		this.rowNo = rowNo;
	}
}
