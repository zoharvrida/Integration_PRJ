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

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getCustIdPel() {
        return custIdPel;
    }

    public void setCustIdPel(String custIdPel) {
        this.custIdPel = custIdPel;
    }

    public String getTypeTrx() {
        return typeTrx;
    }

    public void setTypeTrx(String typeTrx) {
        this.typeTrx = typeTrx;
    }
    
    public java.math.BigDecimal getAmountFlagging() {
    	return amountFlagging;
    }
    
    public void setAmountFlagging(java.math.BigDecimal amountFlagging) {
    	this.amountFlagging = amountFlagging;
    }

    public String getFlagStatus() {
        return flagStatus;
    }

    public void setFlagStatus(String flagStatus) {
        this.flagStatus = flagStatus;
    }

    public String getStatusReason() {
        return statusReason;
    }

    public void setStatusReason(String statusReason) {
        this.statusReason = statusReason;
    }
    
    
    
}
