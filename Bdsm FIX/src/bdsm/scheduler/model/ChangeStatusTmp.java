package bdsm.scheduler.model;

import java.util.Date;
import bdsm.model.BaseModel;

/**
 * 
 * @author bdsm
 */
@SuppressWarnings("serial")
public class ChangeStatusTmp extends BaseModel {
    private Integer id;
    private String codAcctNo;    
    private int codAcctStatCurr;
    private int codAcctStatNew;
    private Date datApplied;
    private String status;
    private String statusReason;
    private String noBatch;

    /**
     * 
     * @return
     */
    public String getCodAcctNo() {
        return codAcctNo;
    }

    /**
     * 
     * @param codAcctNo
     */
    public void setCodAcctNo(String codAcctNo) {
        this.codAcctNo = codAcctNo;
    }

    /**
     * 
     * @return
     */
    public int getCodAcctStatCurr() {
        return codAcctStatCurr;
    }

    /**
     * 
     * @param codAcctStatCurr
     */
    public void setCodAcctStatCurr(int codAcctStatCurr) {
        this.codAcctStatCurr = codAcctStatCurr;
    }

    /**
     * 
     * @return
     */
    public int getCodAcctStatNew() {
        return codAcctStatNew;
    }

    /**
     * 
     * @param codAcctStatNew
     */
    public void setCodAcctStatNew(int codAcctStatNew) {
        this.codAcctStatNew = codAcctStatNew;
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
}