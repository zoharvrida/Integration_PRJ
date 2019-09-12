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

    public String getBatchNo() {
        return batchNo;
    }
    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public Integer getRecordId() {
        return recordId;
    }
    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }
    
    public String getCodAcctNo() {
        return codAcctNo;
    }
    public void setCodAcctNo(String codAcctNo) {
        this.codAcctNo = codAcctNo;
    }
}
