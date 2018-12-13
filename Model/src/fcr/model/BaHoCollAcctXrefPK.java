/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.fcr.model;

/**
 *
 * @author v00020841
 */
public class BaHoCollAcctXrefPK implements java.io.Serializable{
    private Integer codColl;
    private String codCollatId;
    private String codAcctNo;
    private Integer codDepNo;
    private Integer codEntityVpd;

    public Integer getCodColl() {
        return codColl;
    }

    public void setCodColl(Integer codColl) {
        this.codColl = codColl;
    }

    public String getCodAcctNo() {
        return codAcctNo;
    }

    public void setCodAcctNo(String codAcctNo) {
        this.codAcctNo = codAcctNo;
    }

    public String getCodCollatId() {
        return codCollatId;
    }

    public void setCodCollatId(String codCollatId) {
        this.codCollatId = codCollatId;
    }

    public Integer getCodDepNo() {
        return codDepNo;
    }

    public void setCodDepNo(Integer codDepNo) {
        this.codDepNo = codDepNo;
    }

    public Integer getCodEntityVpd() {
        return codEntityVpd;
    }

    public void setCodEntityVpd(Integer codEntityVpd) {
        this.codEntityVpd = codEntityVpd;
    }
    
    
}
