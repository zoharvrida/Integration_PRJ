package bdsm.scheduler.model;

import java.util.Date;
import bdsm.model.BaseModel;

/**
 * 
 * @author bdsm
 */
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

    /**
     * 
     * @return
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * @return
     */
    public String getIdPatch() {
        return idPatch;
    }

    /**
     * 
     * @param idPatch
     */
    public void setIdPatch(String idPatch) {
        this.idPatch = idPatch;
    }

    /**
     * 
     * @return
     */
    public String getCodID() {
        return codID;
    }

    /**
     * 
     * @param codID
     */
    public void setCodID(String codID) {
        this.codID = codID;
    }

    /**
     * 
     * @return
     */
    public String getFlagProcess() {
        return flagProcess;
    }

    /**
     * 
     * @param flagProcess
     */
    public void setFlagProcess(String flagProcess) {
        this.flagProcess = flagProcess;
    }  

    /**
     * 
     * @return
     */
    public Date getDateProcess() {
        return dateProcess;
    }

    /**
     * 
     * @param dateProcess
     */
    public void setDateProcess(Date dateProcess) {
        this.dateProcess = dateProcess;
    }
    /**
     * 
     * @return
     */
    public String getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
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
    public String getNoBatch() {
        return noBatch;
    }

    /**
     * 
     * @param noBatch
     */
    public void setNoBatch(String noBatch) {
        this.noBatch = noBatch;
    }
}
