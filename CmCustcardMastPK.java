/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.fcr.model;

/**
 *
 * @author v00020841
 */
public class CmCustcardMastPK implements java.io.Serializable{
    private Integer codCustId;
    private String flgMntStatus;
    private Integer codEntityVpd;

    public Integer getCodCustId() {
        return codCustId;
    }

    public void setCodCustId(Integer codCustId) {
        this.codCustId = codCustId;
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
    
}
