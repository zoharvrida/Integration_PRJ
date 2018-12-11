package bdsm.scheduler.model;


/**
 *
 * @author v00020841
 */
@SuppressWarnings("serial")
public class TmpBigBillTrx extends bdsm.model.BaseModel {
    private Long recordId;
    private String batchNo;
    private String custIdPel;
    private String typeTrx;
    private java.math.BigDecimal amountFlagging;
    private String flagStatus;
    private String statusReason;

    /**
     * 
     * @return
     */
    public Long getRecordId() {
        return recordId;
    }

    /**
     * 
     * @param recordId
     */
    public void setRecordId(Long recordId) {
        this.recordId = recordId;
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
    public String getCustIdPel() {
        return custIdPel;
    }

    /**
     * 
     * @param custIdPel
     */
    public void setCustIdPel(String custIdPel) {
        this.custIdPel = custIdPel;
    }

    /**
     * 
     * @return
     */
    public String getTypeTrx() {
        return typeTrx;
    }

    /**
     * 
     * @param typeTrx
     */
    public void setTypeTrx(String typeTrx) {
        this.typeTrx = typeTrx;
    }
    
    /**
     * 
     * @return
     */
    public java.math.BigDecimal getAmountFlagging() {
    	return amountFlagging;
    }
    
    /**
     * 
     * @param amountFlagging
     */
    public void setAmountFlagging(java.math.BigDecimal amountFlagging) {
    	this.amountFlagging = amountFlagging;
    }

    /**
     * 
     * @return
     */
    public String getFlagStatus() {
        return flagStatus;
    }

    /**
     * 
     * @param flagStatus
     */
    public void setFlagStatus(String flagStatus) {
        this.flagStatus = flagStatus;
    }

    /**
     * 
     * @return
     */
    public String getStatusReason() {
        return statusReason;
    }

    /**
     * 
     * @param statusReason
     */
    public void setStatusReason(String statusReason) {
        this.statusReason = statusReason;
    }
    
    
    
}
