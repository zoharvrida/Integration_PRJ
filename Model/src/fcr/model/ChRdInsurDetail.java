/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.fcr.model;

import bdsm.model.BaseModel;
import java.sql.Timestamp;

/**
 *
 * @author v00020841
 */
public class ChRdInsurDetail extends BaseModel{
    private ChRdInsurDetailPK chRdInsurDetailPK;
    private String codRdAcctNo;
    private Integer codCcBrn;
    private String codMntAction;
    private String codLastMntMakerid;
    private String codLastMntChkrid;
    private Timestamp datLastMnt;
    private Integer ctrUpdtSrlno;

    public ChRdInsurDetailPK getChRdInsurDetailPK() {
        return chRdInsurDetailPK;
    }

    public void setChRdInsurDetailPK(ChRdInsurDetailPK chRdInsurDetailPK) {
        this.chRdInsurDetailPK = chRdInsurDetailPK;
    }

    public String getCodRdAcctNo() {
        return codRdAcctNo;
    }

    public void setCodRdAcctNo(String codRdAcctNo) {
        this.codRdAcctNo = codRdAcctNo;
    }

    public Integer getCodCcBrn() {
        return codCcBrn;
    }

    public void setCodCcBrn(Integer codCcBrn) {
        this.codCcBrn = codCcBrn;
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

    public Integer getCtrUpdtSrlno() {
        return ctrUpdtSrlno;
    }

    public void setCtrUpdtSrlno(Integer ctrUpdtSrlno) {
        this.ctrUpdtSrlno = ctrUpdtSrlno;
    }

}
