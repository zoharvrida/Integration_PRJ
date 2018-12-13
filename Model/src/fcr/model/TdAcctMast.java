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
public class TdAcctMast extends BaseModel {
    private TdAcctMastPK tdAcctMastPK;
    private Integer codCcBrn;
    private Integer codProd;
  
    public TdAcctMastPK getTdAcctMastPK() {
        return tdAcctMastPK;
    }
    public void setTdAcctMastPK(TdAcctMastPK tdAcctMastPK) {
        this.tdAcctMastPK = tdAcctMastPK;
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
