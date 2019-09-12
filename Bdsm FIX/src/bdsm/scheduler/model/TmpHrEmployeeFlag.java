/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

import bdsm.model.BaseModel;
import java.sql.Timestamp;

/**
 *
 * @author v00020841
 */
public class TmpHrEmployeeFlag extends BaseModel{

    private TmpHrEmployeeFlagPK compositeId;
    private String flgEmpAcct;
    private String codLastMntMakerid;
    private String codLastMntChkrid;
    private Timestamp datLastMnt;
    private String flgStatus;
    private String statusReason;

    /**
     * 
     * @return
     */
    public TmpHrEmployeeFlagPK getCompositeId() {
        return compositeId;
    }
    /**
     * 
     * @param compositeId
     */
    public void setCompositeId(TmpHrEmployeeFlagPK compositeId) {
        this.compositeId = compositeId;
    }

    /**
     * 
     * @return
     */
    public String getFlgEmpAcct() {
        return flgEmpAcct;
    }
    /**
     * 
     * @param flgEmpAcct
     */
    public void setFlgEmpAcct(String flgEmpAcct) {
        this.flgEmpAcct = flgEmpAcct;
    }

    /**
     * 
     * @return
     */
    public String getCodLastMntMakerid() {
        return codLastMntMakerid;
    }
    /**
     * 
     * @param codLastMntMakerid
     */
    public void setCodLastMntMakerid(String codLastMntMakerid) {
        this.codLastMntMakerid = codLastMntMakerid;
    }

    /**
     * 
     * @return
     */
    public String getCodLastMntChkrid() {
        return codLastMntChkrid;
    }
    /**
     * 
     * @param codLastMntChkrid
     */
    public void setCodLastMntChkrid(String codLastMntChkrid) {
        this.codLastMntChkrid = codLastMntChkrid;
    }

    /**
     * 
     * @return
     */
    public Timestamp getDatLastMnt() {
        return datLastMnt;
    }
    /**
     * 
     * @param datLastMnt
     */
    public void setDatLastMnt(Timestamp datLastMnt) {
        this.datLastMnt = datLastMnt;
    }

    /**
     * 
     * @return
     */
    public String getFlgStatus() {
        return flgStatus;
    }
    /**
     * 
     * @param flgStatus
     */
    public void setFlgStatus(String flgStatus) {
        this.flgStatus = flgStatus;
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
