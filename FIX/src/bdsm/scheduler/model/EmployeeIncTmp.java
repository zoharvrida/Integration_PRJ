package bdsm.scheduler.model;

import java.util.Date;
import bdsm.model.BaseModel;

/**
 * 
 * @author bdsm
 */
@SuppressWarnings("serial")
public class EmployeeIncTmp extends BaseModel {
    private Integer id;
    private Integer codCustID;
    private String newValue;
    private Date datApplied;
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
    public Integer getCodCustID() {
        return codCustID;
    }

    /**
     * 
     * @param codCustID
     */
    public void setCodCustID(Integer codCustID) {
        this.codCustID = codCustID;
    }

    /**
     * 
     * @return
     */
    public String getNewValue() {
        return newValue;
    }

    /**
     * 
     * @param newValue
     */
    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    /**
     * 
     * @return
     */
    public Date getDatApplied() {
        return datApplied;
    }

    /**
     * 
     * @param datApplied
     */
    public void setDatApplied(Date datApplied) {
        this.datApplied = datApplied;
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