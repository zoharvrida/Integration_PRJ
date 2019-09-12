/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;

import java.io.Serializable;

/**
 *
 * @author v00019722
 */
@SuppressWarnings("serial")
public class FieldMappingPK implements Serializable {
    private String namField;
    private String classType;
    private String classTypeToComp;
    private String values;

    /**
     * @return the namField
     */
    public String getNamField() {
        return namField;
    }

    /**
     * @param namField the namField to set
     */
    public void setNamField(String namField) {
        this.namField = namField;
    }

    /**
     * @return the classType
     */
    public String getClassType() {
        return classType;
    }

    /**
     * @param classType the classType to set
     */
    public void setClassType(String classType) {
        this.classType = classType;
    }

    /**
     * @return the classTypeToComp
     */
    public String getClassTypeToComp() {
        return classTypeToComp;
    }

    /**
     * @param classTypeToComp the classTypeToComp to set
     */
    public void setClassTypeToComp(String classTypeToComp) {
        this.classTypeToComp = classTypeToComp;
    }

    /**
     * @return the values
     */
    public String getValues() {
        return values;
    }

    /**
     * @param values the values to set
     */
    public void setValues(String values) {
        this.values = values;
    }
}
