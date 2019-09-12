/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.rpt.model;

import bdsm.model.BaseModel;

/**
 *
 * @author v00019722
 */
public class FixReportParam extends BaseModel {
    private FixReportParamPK compositeId;
    private String title;
    private String format;
    private String maxLength;
    private String manDatory;
    private String regExp;
    
        /**
     * @return title idReport
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the idMenu to set
     */
    public void setTitle(String title) {
        this.title = title;
    }
    
    /**
     * @return seq idReport
     */
    public String getFormat() {
        return format;
    }

    /**
     * @param seq the idMenu to set
     */
    public void setFormat(String format) {
        this.format = format;
    }
    /**
     * @return seq idReport
     */
    public String getMaxLength() {
        return maxLength;
    }

    /**
     * @param seq the idMenu to set
     */
    public void setMaxLength(String maxLength) {
        this.maxLength = maxLength;
    }

    /**
     * @return the compositeId
     */
    public FixReportParamPK getCompositeId() {
        return compositeId;
    }

    /**
     * @param compositeId the compositeId to set
     */
    public void setCompositeId(FixReportParamPK compositeId) {
        this.compositeId = compositeId;
    }

    /**
     * @return the manDatory
     */
    public String getManDatory() {
        return manDatory;
    }

    /**
     * @param manDatory the manDatory to set
     */
    public void setManDatory(String manDatory) {
        this.manDatory = manDatory;
    }

    /**
     * @return the regExp
     */
    public String getRegExp() {
        return regExp;
    }

    /**
     * @param regExp the regExp to set
     */
    public void setRegExp(String regExp) {
        this.regExp = regExp;
    }
}
