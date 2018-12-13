/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.fcr.model;

import bdsm.model.BaseModel;
import java.util.Date;
import java.sql.Timestamp;

/**
 *
 * @author v00020841
 */
public class BaMoveCustomers extends BaseModel {
    private BaMoveCustomersPK baMoveCustomersPK;
    private Integer codCcBrn;
    private Integer codXferBrn;
	private String codMntAction;
    private String codLastMntMakerId;
    private String codLastMntChkrId;
    private Timestamp datLastMnt;
    private Integer ctrUpdtSrlNo; 
    private String flagBranch;
    private String flagProcess;
    private Integer codStream;

    public BaMoveCustomersPK getBaMoveCustomersPK() {
        return baMoveCustomersPK;
    }

    public void setBaMoveCustomersPK(BaMoveCustomersPK baMoveCustomersPK) {
        this.baMoveCustomersPK = baMoveCustomersPK;
    }

    public Integer getCodCcBrn() {
        return codCcBrn;
    }
    public void setCodCcBrn(Integer codCcBrn) {
        this.codCcBrn = codCcBrn;
    }

    public Integer getCodXferBrn() {
        return codXferBrn;
    }
    public void setCodXferBrn(Integer codXferBrn) {
        this.codXferBrn = codXferBrn;
    }

    public String getCodMntAction() {
        return codMntAction;
    }
    public void setCodMntAction(String codMntAction) {
        this.codMntAction = codMntAction;
    }

    public String getCodLastMntMakerId() {
        return codLastMntMakerId;
    }
    public void setCodLastMntMakerId(String codLastMntMakerId) {
        this.codLastMntMakerId = codLastMntMakerId;
    }

    public String getCodLastMntChkrId() {
        return codLastMntChkrId;
    }
    public void setCodLastMntChkrId(String codLastMntChkrId) {
        this.codLastMntChkrId = codLastMntChkrId;
    }

    public Timestamp getDatLastMnt() {
        return datLastMnt;
    }
    public void setDatLastMnt(Timestamp datLastMnt) {
        this.datLastMnt = datLastMnt;
    }

    public Integer getCtrUpdtSrlNo() {
        return ctrUpdtSrlNo;
    }
    public void setCtrUpdtSrlNo(Integer ctrUpdtSrlNo) {
        this.ctrUpdtSrlNo = ctrUpdtSrlNo;
    }

    public String getFlagBranch() {
        return flagBranch;
    }
    public void setFlagBranch(String flagBranch) {
        this.flagBranch = flagBranch;
    }

    public String getFlagProcess() {
        return flagProcess;
    }
    public void setFlagProcess(String flagProcess) {
        this.flagProcess = flagProcess;
    }
    
    public Integer getCodStream() {
        return codStream;
    }
    public void setCodStream(Integer codStream) {
        this.codStream = codStream;
    }
    
}
