/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.fcr.model;

import bdsm.model.BaseModel;

/**
 *
 * @author v00020841
 */
public class LnAcctDtls extends BaseModel{
    private LnAcctDtlsPK lnAcctDtlsPK;
    private Integer codCcBrn;
    private Integer codProd;
    
    public LnAcctDtlsPK getLnAcctDtlsPK() {
        return lnAcctDtlsPK;
    }
    public void setLnAcctDtlsPK(LnAcctDtlsPK lnAcctDtlsPK) {
        this.lnAcctDtlsPK = lnAcctDtlsPK;
    }

    public Integer getCodCcBrn() {
        return codCcBrn;
    }
    public void setCodCcBrn(Integer codCcBrn) {
        this.codCcBrn = codCcBrn;
    }

    public Integer getCodProd() {
        return codProd;
    }
    public void setCodProd(Integer codProd) {
        this.codProd = codProd;
    }

}
