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
public class BaHoCollAcctXref extends BaseModel {

    private BaHoCollAcctXrefPK acctXrefPK;
    private Integer codProd;
    private Integer codCcBrn;
    private String codLastMntMakerid;
    private String codLastMntChkrid;
    private Timestamp datLastMnt;

    public BaHoCollAcctXrefPK getAcctXrefPK() {
        return acctXrefPK;
    }

    public void setAcctXrefPK(BaHoCollAcctXrefPK acctXrefPK) {
        this.acctXrefPK = acctXrefPK;
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


}
