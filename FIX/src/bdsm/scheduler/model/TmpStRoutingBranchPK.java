/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

/**
 *
 * @author v00020800
 */
public class TmpStRoutingBranchPK implements java.io.Serializable{
        private String batchNo;
         private Integer codBank;
        private Integer recordId;


 
    /**
     * @return the batchNo
     */
    public String getBatchNo() {
        return batchNo;
    }

    /**
     * @param batchNo the batchNo to set
     */
    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    /**
     * @return the recordId
     */
    public Integer getRecordId() {
        return recordId;
    }

    /**
     * @param recordId the recordId to set
     */
    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    /**
     * @return the codBank
     */
    public Integer getCodBank() {
        return codBank;
    }

    /**
     * @param codBank the codBank to set
     */
    public void setCodBank(Integer codBank) {
        this.codBank = codBank;
    }
}
