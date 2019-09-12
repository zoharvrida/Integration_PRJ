/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;

/**
 *
 * @author v00019722
 */
public class SknngInOutDebitGen1Footer extends BaseModel {
    private SknngInOutDebitHPK compositeId;
    private Integer TotalRecord;
    private String CrcCheck;
    private String RecordType;
    private String DKE_Code;
    private String BatchNo;
    private String ErrMessage;
    private String ExtractStatus;
    private Integer ExtractNum;
    private Integer TSuccess;
    private Integer TReject;

    /**
     * @return the compositeId
     */
    public SknngInOutDebitHPK getCompositeId() {
        return compositeId;
    }

    /**
     * @param compositeId the compositeId to set
     */
    public void setCompositeId(SknngInOutDebitHPK compositeId) {
        this.compositeId = compositeId;
    }

    /**
     * @return the TotalRecord
     */
    public Integer getTotalRecord() {
        return TotalRecord;
    }

    /**
     * @param TotalRecord the TotalRecord to set
     */
    public void setTotalRecord(Integer TotalRecord) {
        this.TotalRecord = TotalRecord;
    }

    /**
     * @return the CrcCheck
     */
    public String getCrcCheck() {
        return CrcCheck;
    }

    /**
     * @param CrcCheck the CrcCheck to set
     */
    public void setCrcCheck(String CrcCheck) {
        this.CrcCheck = CrcCheck;
    }

    /**
     * @return the RecordType
     */
    public String getRecordType() {
        return RecordType;
    }

    /**
     * @param RecordType the RecordType to set
     */
    public void setRecordType(String RecordType) {
        this.RecordType = RecordType;
    }

    /**
     * @return the DKE_Code
     */
    public String getDKE_Code() {
        return DKE_Code;
    }

    /**
     * @param DKE_Code the DKE_Code to set
     */
    public void setDKE_Code(String DKE_Code) {
        this.DKE_Code = DKE_Code;
    }

    /**
     * @return the BatchNo
     */
    public String getBatchNo() {
        return BatchNo;
    }

    /**
     * @param BatchNo the BatchNo to set
     */
    public void setBatchNo(String BatchNo) {
        this.BatchNo = BatchNo;
    }

    /**
     * @return the ErrMessage
     */
    public String getErrMessage() {
        return ErrMessage;
    }

    /**
     * @param ErrMessage the ErrMessage to set
     */
    public void setErrMessage(String ErrMessage) {
        this.ErrMessage = ErrMessage;
    }

    /**
     * @return the ExtractStatus
     */
    public String getExtractStatus() {
        return ExtractStatus;
    }

    /**
     * @param ExtractStatus the ExtractStatus to set
     */
    public void setExtractStatus(String ExtractStatus) {
        this.ExtractStatus = ExtractStatus;
    }

    /**
     * @return the ExtractNum
     */
    public Integer getExtractNum() {
        return ExtractNum;
    }

    /**
     * @param ExtractNum the ExtractNum to set
     */
    public void setExtractNum(Integer ExtractNum) {
        this.ExtractNum = ExtractNum;
    }

    /**
     * @return the TSuccess
     */
    public Integer getTSuccess() {
        return TSuccess;
    }

    /**
     * @param TSuccess the TSuccess to set
     */
    public void setTSuccess(Integer TSuccess) {
        this.TSuccess = TSuccess;
    }

    /**
     * @return the TReject
     */
    public Integer getTReject() {
        return TReject;
    }

    /**
     * @param TReject the TReject to set
     */
    public void setTReject(Integer TReject) {
        this.TReject = TReject;
    }
}
