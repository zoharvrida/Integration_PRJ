/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;


/**
 *
 * @author v00019372
 */
@SuppressWarnings("serial")
public class SknNgPK implements java.io.Serializable {
    private String batchNo;
    private Integer recordId;
    
    
    public SknNgPK() {}
    
    public SknNgPK(String batchNo, Integer recordId) {
    	this.batchNo = batchNo;
    	this.recordId = recordId;
    }
    
    
    public String getBatchNo() {
        return this.batchNo;
    }
    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public Integer getRecordId() {
        return this.recordId;
    }
    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }
    
    
    @Override
    public boolean equals(Object obj) {
    	if (obj instanceof SknNgPK) {
    		SknNgPK objPK = (SknNgPK) obj;
    		return ((this.batchNo.equals(objPK.batchNo)) && (this.recordId.equals(objPK.recordId)));
    	}
    	return false;
    }
    
    @Override
    public int hashCode() {
    	return (this.batchNo.hashCode() + this.recordId.hashCode());
    }
    
    
    @Override
    public String toString() {
    	StringBuilder sb = new StringBuilder(this.getClass().getSimpleName())
    		.append("{")
    		.append("batchNo=").append(this.batchNo)
    		.append(",recordId=").append(this.recordId)
    		.append("}");
    	
    	return sb.toString();
    }
}
