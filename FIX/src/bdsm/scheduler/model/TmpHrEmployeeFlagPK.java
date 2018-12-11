/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

/**
 *
 * @author v00020841
 */
public class TmpHrEmployeeFlagPK implements java.io.Serializable {

    private String batchNo;
    private Integer recordId;
    private String codAcctNo;

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
    public Integer getRecordId() {
        return recordId;
    }
    /**
     * 
     * @param recordId
     */
    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }
    
    /**
     * 
     * @return
     */
    public String getCodAcctNo() {
        return codAcctNo;
    }
    /**
     * 
     * @param codAcctNo
     */
    public void setCodAcctNo(String codAcctNo) {
        this.codAcctNo = codAcctNo;
    }
}
