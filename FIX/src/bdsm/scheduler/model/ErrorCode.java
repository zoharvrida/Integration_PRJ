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
public class ErrorCode extends BaseModel {
    private Integer moduleCode;
    private Integer subModuleCode;
    private Integer numberCode;
    private String errorDescription;
    private String errorSeverity;
    private Integer templateId;
    private Integer orderNumber;
    private String packageName;

    /**
     * @return the moduleCode
     */
    public Integer getModuleCode() {
        return moduleCode;
    }

    /**
     * @param moduleCode the moduleCode to set
     */
    public void setModuleCode(Integer moduleCode) {
        this.moduleCode = moduleCode;
    }

    /**
     * @return the subModuleCode
     */
    public Integer getSubModuleCode() {
        return subModuleCode;
    }

    /**
     * @param subModuleCode the subModuleCode to set
     */
    public void setSubModuleCode(Integer subModuleCode) {
        this.subModuleCode = subModuleCode;
    }

    /**
     * @return the numberCode
     */
    public Integer getNumberCode() {
        return numberCode;
    }

    /**
     * @param numberCode the numberCode to set
     */
    public void setNumberCode(Integer numberCode) {
        this.numberCode = numberCode;
    }

    /**
     * @return the errorDescription
     */
    public String getErrorDescription() {
        return errorDescription;
    }

    /**
     * @param errorDescription the errorDescription to set
     */
    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    /**
     * @return the errorSeverity
     */
    public String getErrorSeverity() {
        return errorSeverity;
    }

    /**
     * @param errorSeverity the errorSeverity to set
     */
    public void setErrorSeverity(String errorSeverity) {
        this.errorSeverity = errorSeverity;
    }

    /**
     * @return the templateId
     */
    public Integer getTemplateId() {
        return templateId;
    }

    /**
     * @param templateId the templateId to set
     */
    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    /**
     * @return the orderNumber
     */
    public Integer getOrderNumber() {
        return orderNumber;
    }

    /**
     * @param orderNumber the orderNumber to set
     */
    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    /**
     * @return the packageName
     */
    public String getPackageName() {
        return packageName;
    }

    /**
     * @param packageName the packageName to set
     */
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
