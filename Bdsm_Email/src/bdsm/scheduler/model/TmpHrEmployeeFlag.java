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

    public TmpHrEmployeeFlagPK getCompositeId() {
        return compositeId;
    }
    public void setCompositeId(TmpHrEmployeeFlagPK compositeId) {
        this.compositeId = compositeId;
    }

    public String getFlgEmpAcct() {
        return flgEmpAcct;
    }
    public void setFlgEmpAcct(String flgEmpAcct) {
        this.flgEmpAcct = flgEmpAcct;
    }

    public String getCodLastMntMakerid() {
        return codLastMntMakerid;
    }
    public void setCodLastMntMakerid(String codLastMntMakerid) {
        this.codLastMntMakerid = codLastMntMakerid;
    }

    public String getCodLastMntChkrid() {
        return codLastMntChkrid;
    }
    public void setCodLastMntChkrid(String codLastMntChkrid) {
        this.codLastMntChkrid = codLastMntChkrid;
    }

    public Timestamp getDatLastMnt() {
        return datLastMnt;
    }
    public void setDatLastMnt(Timestamp datLastMnt) {
        this.datLastMnt = datLastMnt;
    }

    public String getFlgStatus() {
        return flgStatus;
    }
    public void setFlgStatus(String flgStatus) {
        this.flgStatus = flgStatus;
    }

    public String getStatusReason() {
        return statusReason;
    }
    public void setStatusReason(String statusReason) {
        this.statusReason = statusReason;
    }
    
}
