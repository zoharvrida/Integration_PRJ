/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;

/**
 *
 * @author user
 */
public class FcrCiCustmast {
    private FcrCiCustmastPK compositeId;
    private String namCustFull;
    private Integer codEntityVpd;
    private String CodCustNatlID;
    
    /**
     * @return the compositeId
     */
    public FcrCiCustmastPK getCompositeId() {
        return compositeId;
    }

    /**
     * @param compositeId the compositeId to set
     */
    public void setCompositeId(FcrCiCustmastPK compositeId) {
        this.compositeId = compositeId;
    }
    
    /**
     * @return the namCustFull
     */
    public String getNamCustFull() {
        return namCustFull;
    }

    /**
     * @param namCustFull the namCustFull to set
     */
    public void setNamCustFull(String namCustFull) {
        this.namCustFull = namCustFull;
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
     * @return the CodCustNatlID
     */
    public String getCodCustNatlID() {
        return CodCustNatlID;
    }

    /**
     * @param CodCustNatlID the CodCustNatlID to set
     */
    public void setCodCustNatlID(String CodCustNatlID) {
        this.CodCustNatlID = CodCustNatlID;
    }
}