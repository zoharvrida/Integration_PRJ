/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;

import java.io.Serializable;

/**
 *
 * @author SDM
 */
public class ARParamDetails implements Serializable{
    private Integer idScheduler;
    private Integer idOrder;
    private String type;
    private String fieldName;
    private String fieldType;
    private String fieldFormat;
    private Integer maxLength;
    private String deffVal;

    /**
     * @return the idScheduler
     */
    public Integer getIdScheduler() {
        return idScheduler;
    }

    /**
     * @param idScheduler the idScheduler to set
     */
    public void setIdScheduler(Integer idScheduler) {
        this.idScheduler = idScheduler;
    }

    /**
     * @return the idOrder
     */
    public Integer getIdOrder() {
        return idOrder;
    }

    /**
     * @param idOrder the idOrder to set
     */
    public void setIdOrder(Integer idOrder) {
        this.idOrder = idOrder;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

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
     * @return the fieldType
     */
    public String getFieldType() {
        return fieldType;
    }

    /**
     * @param fieldType the fieldType to set
     */
    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    /**
     * @return the fieldFormat
     */
    public String getFieldFormat() {
        return fieldFormat;
    }

    /**
     * @param fieldFormat the fieldFormat to set
     */
    public void setFieldFormat(String fieldFormat) {
        this.fieldFormat = fieldFormat;
    }

    /**
     * @return the maxLength
     */
    public Integer getMaxLength() {
        return maxLength;
    }

    /**
     * @param maxLength the maxLength to set
     */
    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    /**
     * @return the deffVal
     */
    public String getDeffVal() {
        return deffVal;
    }

    /**
     * @param deffVal the deffVal to set
     */
    public void setDeffVal(String deffVal) {
        this.deffVal = deffVal;
    }
}
