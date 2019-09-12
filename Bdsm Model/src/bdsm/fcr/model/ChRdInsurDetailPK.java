/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.fcr.model;

/**
 *
 * @author v00020841
 */
public class ChRdInsurDetailPK implements java.io.Serializable {
    private String codInsurAcctNo;
    private String flgMntStatus;
    private Integer codEntityVpd;
    
    public String getCodInsurAcctNo() {
        return codInsurAcctNo;
    }

    public void setCodInsurAcctNo(String codInsurAcctNo) {
        this.codInsurAcctNo = codInsurAcctNo;
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
