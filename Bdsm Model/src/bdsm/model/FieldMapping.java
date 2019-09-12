/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;

/**
 *
 * @author v00019722
 */
public class FieldMapping extends BaseModel{
    private FieldMappingPK pk;
    private String fieldValueToComp;
    private String fieldIndex;
    private String fieldIndexToComp;
    private String type;


    /**
     * @return the fieldValueToComp
     */
    public String getFieldValueToComp() {
        return fieldValueToComp;
    }

    /**
     * @param fieldValueToComp the fieldValueToComp to set
     */
    public void setFieldValueToComp(String fieldValueToComp) {
        this.fieldValueToComp = fieldValueToComp;
    }

    /**
     * @return the fieldIndex
     */
    public String getFieldIndex() {
        return fieldIndex;
    }

    /**
     * @param fieldIndex the fieldIndex to set
     */
    public void setFieldIndex(String fieldIndex) {
        this.fieldIndex = fieldIndex;
    }

    /**
     * @return the fieldIndexToComp
     */
    public String getFieldIndexToComp() {
        return fieldIndexToComp;
    }

    /**
     * @param fieldIndexToComp the fieldIndexToComp to set
     */
    public void setFieldIndexToComp(String fieldIndexToComp) {
        this.fieldIndexToComp = fieldIndexToComp;
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
	 * @return the pk
	 */
	public FieldMappingPK getPk() {
		return pk;
	}

	/**
	 * @param pk the pk to set
	 */
	public void setPk(FieldMappingPK pk) {
		this.pk = pk;
	}
}
