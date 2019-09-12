/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;

import java.io.Serializable;
import java.sql.Clob;

/**
 *
 * @author SDM
 */
public class ARParamDetailsInterface implements Serializable, Comparable<ARParamDetailsInterface> {
    private Integer idScheduler;
    private Integer idOrder;
    private String type;
    private String fieldName;
    private String fieldType;
    private String fieldFormat;
    private Integer maxLength;
    private String deffVal;
    private String delimiter;
    private String typTrx;
    private String flgMandatory;
    private String padding;
    private String paddType;
    private String contextRef;
    private String paramRef;
    private String customRef;
    private Integer idenNumber;
    private String idenOpr;
    private String suffix;
    private String idenRel;
    private String defaultVal;
    private Integer idOrderoutput;
    private String fieldFormatOut;
    private Clob clobQuery;
    private String typScheduler;
    
    /**
     * @return the idScheduler
     */
    public Integer getIdScheduler() {
        return idScheduler;
    }

    @Override
    public String toString() {
        return "ARParamDetailsInterface{" + "idScheduler=" + idScheduler + ", idOrder=" + idOrder + ", type=" + type + ", fieldName=" + fieldName + ", fieldType=" + fieldType + ", fieldFormat=" + fieldFormat + ", maxLength=" + maxLength + ", deffVal=" + deffVal + ", delimiter=" + delimiter + ", typTrx=" + typTrx + ", flgMandatory=" + flgMandatory + ", padding=" + padding + ", paddType=" + paddType + ", contextRef=" + contextRef + ", paramRef=" + paramRef + '}';
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

    /**
     * @return the delimiter
     */
    public String getDelimiter() {
        return delimiter;
    }

    /**
     * @param delimiter the delimiter to set
     */
    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    /**
     * @return the typTrx
     */
    public String getTypTrx() {
        return typTrx;
    }

    /**
     * @param typTrx the typTrx to set
     */
    public void setTypTrx(String typTrx) {
        this.typTrx = typTrx;
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
    public String getPadding() {
        return padding;
    }
    public void setPadding(String padding) {
        this.padding = padding;
    }
    public String getPaddType() {
        return paddType;
    }
    public void setPaddType(String paddType) {
        this.paddType = paddType;
    }

    /**
     * @return the contextRef
     */
    public String getContextRef() {
        return contextRef;
    }

    /**
     * @param contextRef the contextRef to set
     */
    public void setContextRef(String contextRef) {
        this.contextRef = contextRef;
    }

    /**
     * @return the paramRef
     */
    public String getParamRef() {
        return paramRef;
    }

    /**
     * @param paramRef the paramRef to set
     */
    public void setParamRef(String paramRef) {
        this.paramRef = paramRef;
    }

    public int compareTo(ARParamDetailsInterface o) {
        int compareResult = ((ARParamDetailsInterface) o).getIdenNumber();
        return this.idenNumber - compareResult;
    }

    /**
     * @return the idenNumber
     */
    public Integer getIdenNumber() {
        return idenNumber;
    }

    /**
     * @param idenNumber the idenNumber to set
     */
    public void setIdenNumber(Integer idenNumber) {
        this.idenNumber = idenNumber;
    }

    /**
     * @return the customRef
     */
    public String getCustomRef() {
        return customRef;
    }

    /**
     * @param customRef the customRef to set
     */
    public void setCustomRef(String customRef) {
        this.customRef = customRef;
    }

    /**
     * @return the idenOpr
     */
    public String getIdenOpr() {
        return idenOpr;
    }

    /**
     * @param idenOpr the idenOpr to set
     */
    public void setIdenOpr(String idenOpr) {
        this.idenOpr = idenOpr;
    }

    /**
     * @return the suffix
     */
    public String getSuffix() {
        return suffix;
    }

    /**
     * @param suffix the suffix to set
     */
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    /**
     * @return the idenRel
     */
    public String getIdenRel() {
        return idenRel;
    }

    /**
     * @param idenRel the idenRel to set
     */
    public void setIdenRel(String idenRel) {
        this.idenRel = idenRel;
    }

    /**
     * @return the defaultVal
     */
    public String getDefaultVal() {
        return defaultVal;
    }

    /**
     * @param defaultVal the defaultVal to set
     */
    public void setDefaultVal(String defaultVal) {
        this.defaultVal = defaultVal;
    }

    /**
     * @return the idOrderoutput
     */
    public Integer getIdOrderoutput() {
        return idOrderoutput;
    }

    /**
     * @param idOrderoutput the idOrderoutput to set
     */
    public void setIdOrderoutput(Integer idOrderoutput) {
        this.idOrderoutput = idOrderoutput;
    }

    /**
     * @return the fieldFormatOut
     */
    public String getFieldFormatOut() {
        return fieldFormatOut;
    }

    /**
     * @param fieldFormatOut the fieldFormatOut to set
     */
    public void setFieldFormatOut(String fieldFormatOut) {
        this.fieldFormatOut = fieldFormatOut;
    }

    /**
     * @return the clobQuery
     */
    public Clob getClobQuery() {
        return clobQuery;
    }

    /**
     * @param clobQuery the clobQuery to set
     */
    public void setClobQuery(Clob clobQuery) {
        this.clobQuery = clobQuery;
    }

    /**
     * @return the typScheduler
     */
    public String getTypScheduler() {
        return typScheduler;
    }

    /**
     * @param typScheduler the typScheduler to set
     */
    public void setTypScheduler(String typScheduler) {
        this.typScheduler = typScheduler;
    }
}
