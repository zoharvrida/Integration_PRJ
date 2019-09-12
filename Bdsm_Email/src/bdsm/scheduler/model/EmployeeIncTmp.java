package bdsm.scheduler.model;

import java.util.Date;
import bdsm.model.BaseModel;

@SuppressWarnings("serial")
public class EmployeeIncTmp extends BaseModel {
    private Integer id;
    private Integer codCustID;
    private String newValue;
    private Date datApplied;
    private String status;
    private String statusReason;
    private String noBatch;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCodCustID() {
        return codCustID;
    }

    public void setCodCustID(Integer codCustID) {
        this.codCustID = codCustID;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public Date getDatApplied() {
        return datApplied;
    }

    public void setDatApplied(Date datApplied) {
        this.datApplied = datApplied;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusReason() {
        return statusReason;
    }

    public void setStatusReason(String statusReason) {
        this.statusReason = statusReason;
    }

    public String getNoBatch() {
        return noBatch;
    }

    public void setNoBatch(String noBatch) {
        this.noBatch = noBatch;
    }
}