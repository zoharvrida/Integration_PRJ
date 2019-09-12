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
public class BaProdBrnXref extends BaseModel{
    private Integer codCcBrn;
    private Integer codProd;
    private String  moduleCode;
    private String  codCustType;

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

    public String getModuleCode() {
        return moduleCode;
    }
    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getCodCustType() {
        return codCustType;
    }
    public void setCodCustType(String codCustType) {
        this.codCustType = codCustType;
    }
    
    

  
    
    
}
