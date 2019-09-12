/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;

/**
 *
 * @author user
 */
public class FcrTdAcctMast {
    private FcrTdAcctMastPK compositeId;
    private Integer codEntityVpd;
    private String codAcctTitle;
    private Integer codCust;
    private Integer codProd;
    private Integer codTds;
    
    /**
     * @return the compositeId
     */
    public FcrTdAcctMastPK getCompositeId() {
        return compositeId;
    }

    /**
     * @param compositeId the compositeId to set
     */
    public void setCompositeId(FcrTdAcctMastPK compositeId) {
        this.compositeId = compositeId;
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
     * @return the codAcctTitle
     */
    public String getCodAcctTitle() {
        return codAcctTitle;
    }

    /**
     * @param codAcctTitle the codAcctTitle to set
     */
    public void setCodAcctTitle(String codAcctTitle) {
        this.codAcctTitle = codAcctTitle;
    }

    /**
     * @return the codCust
     */
    public Integer getCodCust() {
        return codCust;
    }

    /**
     * @param codCust the codCust to set
     */
    public void setCodCust(Integer codCust) {
        this.codCust = codCust;
    }

    /**
     * @return the codProd
     */
    public Integer getCodProd() {
        return codProd;
    }

    /**
     * @param codProd the codProd to set
     */
    public void setCodProd(Integer codProd) {
        this.codProd = codProd;
    }

    /**
     * @return the codTds
     */
    public Integer getCodTds() {
        return codTds;
    }

    /**
     * @param codTds the codTds to set
     */
    public void setCodTds(Integer codTds) {
        this.codTds = codTds;
    }
}
