/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model.Com;

import bdsm.model.BaseModel;

/**
 *
 * @author SDM
 */
public class ComDenomMast extends BaseModel {
    private String denomId;
    private String codCcy;
    private String denomType;
    private Integer valDenom;
    private String namDenom;

    /**
     * @return the denomId
     */
    public String getDenomId() {
        return denomId;
    }

    /**
     * @param denomId the denomId to set
     */
    public void setDenomId(String denomId) {
        this.denomId = denomId;
    }

    /**
     * @return the codCcy
     */
    public String getCodCcy() {
        return codCcy;
    }

    /**
     * @param codCcy the codCcy to set
     */
    public void setCodCcy(String codCcy) {
        this.codCcy = codCcy;
    }

    /**
     * @return the denomType
     */
    public String getDenomType() {
        return denomType;
    }

    /**
     * @param denomType the denomType to set
     */
    public void setDenomType(String denomType) {
        this.denomType = denomType;
    }

    /**
     * @return the valDenom
     */
    public Integer getValDenom() {
        return valDenom;
    }

    /**
     * @param valDenom the valDenom to set
     */
    public void setValDenom(Integer valDenom) {
        this.valDenom = valDenom;
    }

    /**
     * @return the namDenom
     */
    public String getNamDenom() {
        return namDenom;
    }

    /**
     * @param namDenom the namDenom to set
     */
    public void setNamDenom(String namDenom) {
        this.namDenom = namDenom;
    }
}
