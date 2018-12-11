/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

import bdsm.model.BaseModel;


/**
 *
 * @author v00020841
 */
public class TmpMaintenanceCim09BI extends BaseModel{
    private TmpMaintenanceCim09BIPK compositeId;
    private String salutation;
    private String incomeTaxNo;
    private String employeeId;
    private Integer reasonVerifed;
    private String flagStatus;
    private String statusReason;
    private String verified;

    /**
     * 
     * @return
     */
    public TmpMaintenanceCim09BIPK getCompositeId() {
        return compositeId;
    }

    /**
     * 
     * @param compositeId
     */
    public void setCompositeId(TmpMaintenanceCim09BIPK compositeId) {
        this.compositeId = compositeId;
    }

    /**
     * 
     * @return
     */
    public String getSalutation() {
        return salutation;
    }

    /**
     * 
     * @param salutation
     */
    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    /**
     * 
     * @return
     */
    public String getIncomeTaxNo() {
        return incomeTaxNo;
    }

    /**
     * 
     * @param incomeTaxNo
     */
    public void setIncomeTaxNo(String incomeTaxNo) {
        this.incomeTaxNo = incomeTaxNo;
    }

    /**
     * 
     * @return
     */
    public String getEmployeeId() {
        return employeeId;
    }

    /**
     * 
     * @param employeeId
     */
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * 
     * @return
     */
    public Integer getReasonVerifed() {
        return reasonVerifed;
    }

    /**
     * 
     * @param reasonVerifed
     */
    public void setReasonVerifed(Integer reasonVerifed) {
        this.reasonVerifed = reasonVerifed;
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

    /**
     * 
     * @return
     */
    public String getVerified() {
        return verified;
    }

    /**
     * 
     * @param verified
     */
    public void setVerified(String verified) {
        this.verified = verified;
    }
    
    
    
}
