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

    public TmpMaintenanceCim09BIPK getCompositeId() {
        return compositeId;
    }

    public void setCompositeId(TmpMaintenanceCim09BIPK compositeId) {
        this.compositeId = compositeId;
    }

    public String getSalutation() {
        return salutation;
    }

    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    public String getIncomeTaxNo() {
        return incomeTaxNo;
    }

    public void setIncomeTaxNo(String incomeTaxNo) {
        this.incomeTaxNo = incomeTaxNo;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getReasonVerifed() {
        return reasonVerifed;
    }

    public void setReasonVerifed(Integer reasonVerifed) {
        this.reasonVerifed = reasonVerifed;
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

    public String getVerified() {
        return verified;
    }

    public void setVerified(String verified) {
        this.verified = verified;
    }
    
    
    
}
