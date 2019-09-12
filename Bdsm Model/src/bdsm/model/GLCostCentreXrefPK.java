/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bdsm.model;

import java.io.Serializable;
/**
 *
 * @author V00020654
 */
public class GLCostCentreXrefPK implements Serializable {
      private String codGLAcct;
      private Integer codCCBrn;          
      private Integer codLob ;
      private String flgMntStatus ;
      private Integer codEntityVpd;

    /**
     * @return the codGLAcct
     */
    public String getCodGLAcct() {
        return codGLAcct;
    }

    /**
     * @param codGLAcct the codGLAcct to set
     */
    public void setCodGLAcct(String codGLAcct) {
        this.codGLAcct = codGLAcct;
    }

    /**
     * @return the codCCBrn
     */
    public Integer getCodCCBrn() {
        return codCCBrn;
    }

    /**
     * @param codCCBrn the codCCBrn to set
     */
    public void setCodCCBrn(Integer codCCBrn) {
        this.codCCBrn = codCCBrn;
    }

    /**
     * @return the codLob
     */
    public Integer getCodLob() {
        return codLob;
    }

    /**
     * @param codLob the codLob to set
     */
    public void setCodLob(Integer codLob) {
        this.codLob = codLob;
    }

    /**
     * @return the flgMntStatus
     */
    public String getFlgMntStatus() {
        return flgMntStatus;
    }

    /**
     * @param flgMntStatus the flgMntStatus to set
     */
    public void setFlgMntStatus(String flgMntStatus) {
        this.flgMntStatus = flgMntStatus;
    }

    /**
     * @return the codEntityVpd
     */
    public Integer getCodEntityVpd() {
        return codEntityVpd;
    }

    /**
     * @param codEntityVpd the codEntityVpd to set
     */
    public void setCodEntityVpd(Integer codEntityVpd) {
        this.codEntityVpd = codEntityVpd;
    }
}
