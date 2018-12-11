/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.fcr.model;

/**
 *
 * @author v00020841
 */
public class BaCollHdrPK implements java.io.Serializable {
    private String codCollatId;
    private String flgMntStatus;
    private Integer codEntityVpd;
    private Integer codColl;

    public String getCodCollatId() {
        return codCollatId;
    }

    public void setCodCollatId(String codCollatId) {
        this.codCollatId = codCollatId;
    }

    public String getFlgMntStatus() {
        return flgMntStatus;
    }

    public void setFlgMntStatus(String flgMntStatus) {
        this.flgMntStatus = flgMntStatus;
    }

    public Integer getCodEntityVpd() {
        return codEntityVpd;
    }

    public void setCodEntityVpd(Integer codEntityVpd) {
        this.codEntityVpd = codEntityVpd;
    }

    public Integer getCodColl() {
        return codColl;
    }

    public void setCodColl(Integer codColl) {
        this.codColl = codColl;
    }
   
}
