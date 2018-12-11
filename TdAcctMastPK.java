/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.fcr.model;

/**
 *
 * @author v00020841
 */
public class TdAcctMastPK implements java.io.Serializable {

    private String codAcctNo;
    private String flgMntStatus;
    private Integer codEntityVpd;
    
    public String getCodAcctNo() {
        return codAcctNo;
    }

    public void setCodAcctNo(String codAcctNo) {
        this.codAcctNo = codAcctNo;
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
