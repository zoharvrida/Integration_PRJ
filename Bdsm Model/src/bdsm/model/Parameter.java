/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;

/**
 *
 * @author v00013493
 */
public class Parameter extends BaseModel{
    private String cdParam;
    private String typParam;
    private Integer value;
    private String strVal;

    @Override
    public String toString() {
        return "Parameter{" + "cdParam=" + cdParam + ", typParam=" + typParam + ", value=" + value + ", strVal=" + strVal + '}';
    }
    
    /**
    * @return the cdParam
    */
    
    public String getCdParam() {
        return cdParam;
    }

    /**
     * @param cdParam the cdParam to set
     */
    public void setCdParam(String cdParam) {
        this.cdParam = cdParam;
    }

    /**
    * @return the typParam
    */
    public String getTypParam() {
        return typParam;
    }

    /**
     * @param typParam the typParam to set
     */
    public void setTypParam(String typParam) {
        this.typParam = typParam;
    }

    /**
    * @return the value
    */
    public Integer getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(Integer value) {
        this.value = value;
    }

    /**
    * @return the strVal
    */
    public String getStrVal() {
        return strVal;
    }

    /**
     * @param strVal the strVal to set
     */
    public void setStrVal(String strVal) {
        this.strVal = strVal;
    }
}
