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
public class SknNgInOutDebitFooter extends SknNgInOutDebitParent implements java.io.Serializable {
	private String crc;
	private Integer parentRecordId;
	

	public String getCrc() {
		return this.crc;
	}
	public void setCrc(String crc) {
		this.crc = crc;
	}
	
	public Integer getParentRecordId() {
		return parentRecordId;
	}
	public void setParentRecordId(Integer parentRecordId) {
		this.parentRecordId = parentRecordId;
	}

	
	
    @Override
    public String toString() {
    	StringBuilder sb = new StringBuilder(this.getClass().getSimpleName())
    		.append("{")
    		.append(super.toString())
    		.append(",crc=").append(this.crc)
    		.append(",parentRecordId=").append(this.parentRecordId)
    		.append("}");
    	
    	return sb.toString();
    }
}
