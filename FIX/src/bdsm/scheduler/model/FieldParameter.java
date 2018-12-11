/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

import bdsm.model.BaseModel;

/**
 *
 * @author v00019722
 */
public class FieldParameter extends BaseModel{
   private FieldParameterPK pkID;
   private String fieldName;
   private Integer idTemplate;
   private String Format;
   private String formatRule;
   private String flgTmp;
   private String flgMandatory;
   private String DestTable;

    /**
     * @return the fieldName
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * @param fieldName the fieldName to set
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * @return the idTemplate
     */
    public Integer getIdTemplate() {
        return idTemplate;
    }

    /**
     * @param idTemplate the idTemplate to set
     */
    public void setIdTemplate(Integer idTemplate) {
        this.idTemplate = idTemplate;
    }

    /**
     * @return the Format
     */
    public String getFormat() {
        return Format;
    }

    /**
     * @param Format the Format to set
     */
    public void setFormat(String Format) {
        this.Format = Format;
    }

    /**
     * @return the formatRule
     */
    public String getFormatRule() {
        return formatRule;
    }

    /**
     * @param formatRule the formatRule to set
     */
    public void setFormatRule(String formatRule) {
        this.formatRule = formatRule;
    }

    /**
     * @return the flgTmp
     */
    public String getFlgTmp() {
        return flgTmp;
    }

    /**
     * @param flgTmp the flgTmp to set
     */
    public void setFlgTmp(String flgTmp) {
        this.flgTmp = flgTmp;
    }

    /**
     * @return the flgMandatory
     */
    public String getFlgMandatory() {
        return flgMandatory;
    }

    /**
     * @param flgMandatory the flgMandatory to set
     */
    public void setFlgMandatory(String flgMandatory) {
        this.flgMandatory = flgMandatory;
    }

    /**
     * @return the DestTable
     */
    public String getDestTable() {
        return DestTable;
    }

    /**
     * @param DestTable the DestTable to set
     */
    public void setDestTable(String DestTable) {
        this.DestTable = DestTable;
    }

    /**
     * @return the pkID
     */
    public FieldParameterPK getPkID() {
        return pkID;
    }

    /**
     * @param pkID the pkID to set
     */
    public void setPkID(FieldParameterPK pkID) {
        this.pkID = pkID;
    }
}
