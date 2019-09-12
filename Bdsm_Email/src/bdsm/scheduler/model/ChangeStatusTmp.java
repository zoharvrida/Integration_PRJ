package bdsm.scheduler.model;

import java.util.Date;
import bdsm.model.BaseModel;

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

    public String getCodAcctNo() {
        return codAcctNo;
    }

    public void setCodAcctNo(String codAcctNo) {
        this.codAcctNo = codAcctNo;
    }

    public int getCodAcctStatCurr() {
        return codAcctStatCurr;
    }

    public void setCodAcctStatCurr(int codAcctStatCurr) {
        this.codAcctStatCurr = codAcctStatCurr;
    }

    public int getCodAcctStatNew() {
        return codAcctStatNew;
    }

    public void setCodAcctStatNew(int codAcctStatNew) {
        this.codAcctStatNew = codAcctStatNew;
    }

    public Date getDatApplied() {
        return datApplied;
    }

    public void setDatApplied(Date datApplied) {
        this.datApplied = datApplied;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNoBatch() {
        return noBatch;
    }

    public void setNoBatch(String noBatch) {
        this.noBatch = noBatch;
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
}