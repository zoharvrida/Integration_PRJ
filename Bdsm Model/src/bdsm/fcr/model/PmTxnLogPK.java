/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.fcr.model;

import java.io.Serializable;

/**
 *
 * @author v00022309
 */
public class PmTxnLogPK implements Serializable{
    private String refTxnNo;
    private String refSubNo;
    private Integer codEntityVpd;
    
    public PmTxnLogPK() {}

    /**
     * @return the refTxnNo
     */
    public String getRefTxnNo() {
        return refTxnNo;
    }

    /**
     * @param refTxnNo the refTxnNo to set
     */
    public void setRefTxnNo(String refTxnNo) {
        this.refTxnNo = refTxnNo;
    }

    /**
     * @return the refSubNo
     */
    public String getRefSubNo() {
        return refSubNo;
    }

    /**
     * @param refSubNo the refSubNo to set
     */
    public void setRefSubNo(String refSubNo) {
        this.refSubNo = refSubNo;
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

    /**
     * @return the refTxnNo
     */
   
    
    
}
