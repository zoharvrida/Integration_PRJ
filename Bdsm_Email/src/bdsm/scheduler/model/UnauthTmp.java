package bdsm.scheduler.model;

import java.util.Date;
import bdsm.model.BaseModel;

@SuppressWarnings("serial")
public class UnauthTmp extends BaseModel {
    private Integer id;
    private String idPatch;
    private String codID;
    private String flagProcess;
    private Date dateProcess;
    private String status;
    private String statusReason;
    private String noBatch;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdPatch() {
        return idPatch;
    }

    public void setIdPatch(String idPatch) {
        this.idPatch = idPatch;
    }

    public String getCodID() {
        return codID;
    }

    public void setCodID(String codID) {
        this.codID = codID;
    }

    public String getFlagProcess() {
        return flagProcess;
    }

    public void setFlagProcess(String flagProcess) {
        this.flagProcess = flagProcess;
    }  

    public Date getDateProcess() {
        return dateProcess;
    }

    public void setDateProcess(Date dateProcess) {
        this.dateProcess = dateProcess;
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
