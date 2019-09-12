/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.fcr.model;

import bdsm.model.BaseModel;
import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author v00020841
 */
public class BaMoveAccounts extends BaseModel{

    private BaMoveAccountsPK baMoveAccountsPK;
    private String codModule;
    private Integer codProd;
    private Integer codCcBrn;
    private Integer codXferBrn;
    private Date datProcess;
    private String codMntAction;
    private String codLastMntMakerid;
    private String codLastMntChkrid;
    private Timestamp datLastMnt;
    private Integer ctrUpdSrlNo;
    private String flgBranch;
    private String flgProcess;
    private Integer codStream;
    
    public BaMoveAccountsPK getBaMoveAccountsPK() {
        return baMoveAccountsPK;
    }

    public void setBaMoveAccountsPK(BaMoveAccountsPK baMoveAccountsPK) {
        this.baMoveAccountsPK = baMoveAccountsPK;
    }

    public String getCodModule() {
        return codModule;
    }

    public void setCodModule(String codModule) {
        this.codModule = codModule;
    }

    public Integer getCodProd() {
        return codProd;
    }

    public void setCodProd(Integer codProd) {
        this.codProd = codProd;
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

    public Date getDatProcess() {
        return datProcess;
    }

    public void setDatProcess(Date datProcess) {
        this.datProcess = datProcess;
    }

    public String getCodMntAction() {
        return codMntAction;
    }

    public void setCodMntAction(String codMntAction) {
        this.codMntAction = codMntAction;
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

    public Integer getCtrUpdSrlNo() {
        return ctrUpdSrlNo;
    }

    public void setCtrUpdSrlNo(Integer ctrUpdSrlNo) {
        this.ctrUpdSrlNo = ctrUpdSrlNo;
    }

    public String getFlgBranch() {
        return flgBranch;
    }

    public void setFlgBranch(String flgBranch) {
        this.flgBranch = flgBranch;
    }

    public String getFlgProcess() {
        return flgProcess;
    }

    public void setFlgProcess(String flgProcess) {
        this.flgProcess = flgProcess;
    }

    public Integer getCodStream() {
        return codStream;
    }

    public void setCodStream(Integer codStream) {
        this.codStream = codStream;
    }  
}
