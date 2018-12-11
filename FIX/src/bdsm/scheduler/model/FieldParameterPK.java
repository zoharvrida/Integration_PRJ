/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

import java.io.Serializable;

/**
 *
 * @author v00019722
 */
public class FieldParameterPK implements Serializable {
    private Integer IdField;
    private Integer idNumber;
    private String moduleName;

    /**
     * @return the IdField
     */
    public Integer getIdField() {
        return IdField;
    }

    /**
     * @param IdField the IdField to set
     */
    public void setIdField(Integer IdField) {
        this.IdField = IdField;
    }

    /**
     * @return the idNumber
     */
    public Integer getIdNumber() {
        return idNumber;
    }

    /**
     * @param idNumber the idNumber to set
     */
    public void setIdNumber(Integer idNumber) {
        this.idNumber = idNumber;
    }

    /**
     * @return the moduleName
     */
    public String getModuleName() {
        return moduleName;
    }

    /**
     * @param moduleName the moduleName to set
     */
    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
}
