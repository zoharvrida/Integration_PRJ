/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;

import java.io.Serializable;

/**
 *
 * @author bdsm
 */
public class ARParamProfileInterface implements Serializable {
    private Integer idSchedulerImport;
    private Integer idSchedulerXtract;
	private String profileName;
    private String dataSource;
    private String validationFunction;
    private String processFunction;
    private String rejectFunction;
    private String trackingFlag;
    private String validateFlag;
	private String flgSameName;
    private String optExt;

    @Override
    public String toString() {
        return "ARParamProfileInterface{" + "idSchedulerImport=" + getIdSchedulerImport() + ", idSchedulerXtract=" + getIdSchedulerXtract() + ", profileName=" + getProfileName() + ", dataSource=" + getDataSource() + ", validationFunction=" + getValidationFunction() + ", processFunction=" + getProcessFunction() + ", rejectFunction=" + getRejectFunction() + ", trackingFlag=" + getTrackingFlag() + ", validateFlag=" + getValidateFlag() + ", flgSameName=" + getFlgSameName() + '}';
    }

    /**
     * @return the idSchedulerImport
     */
    public Integer getIdSchedulerImport() {
        return idSchedulerImport;
    }

    /**
     * @param idSchedulerImport the idSchedulerImport to set
     */
    public void setIdSchedulerImport(Integer idSchedulerImport) {
        this.idSchedulerImport = idSchedulerImport;
    }

    /**
     * @return the idSchedulerXtract
     */
    public Integer getIdSchedulerXtract() {
        return idSchedulerXtract;
    }

    /**
     * @param idSchedulerXtract the idSchedulerXtract to set
     */
    public void setIdSchedulerXtract(Integer idSchedulerXtract) {
        this.idSchedulerXtract = idSchedulerXtract;
    }

    /**
     * @return the profileName
     */
    public String getProfileName() {
        return profileName;
    }

    /**
     * @param profileName the profileName to set
     */
    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    /**
     * @return the dataSource
     */
    public String getDataSource() {
        return dataSource;
    }

    /**
     * @param dataSource the dataSource to set
     */
    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * @return the validationFunction
     */
    public String getValidationFunction() {
        return validationFunction;
    }

    /**
     * @param validationFunction the validationFunction to set
     */
    public void setValidationFunction(String validationFunction) {
        this.validationFunction = validationFunction;
    }

    /**
     * @return the processFunction
     */
    public String getProcessFunction() {
        return processFunction;
    }

    /**
     * @param processFunction the processFunction to set
     */
    public void setProcessFunction(String processFunction) {
        this.processFunction = processFunction;
    }

    /**
     * @return the rejectFunction
     */
    public String getRejectFunction() {
        return rejectFunction;
    }

    /**
     * @param rejectFunction the rejectFunction to set
     */
    public void setRejectFunction(String rejectFunction) {
        this.rejectFunction = rejectFunction;
    }

    /**
     * @return the trackingFlag
     */
    public String getTrackingFlag() {
        return trackingFlag;
    }

    /**
     * @param trackingFlag the trackingFlag to set
     */
    public void setTrackingFlag(String trackingFlag) {
        this.trackingFlag = trackingFlag;
    }

    /**
     * @return the validateFlag
     */
    public String getValidateFlag() {
        return validateFlag;
    }

    /**
     * @param validateFlag the validateFlag to set
     */
    public void setValidateFlag(String validateFlag) {
        this.validateFlag = validateFlag;
    }

    /**
     * @return the flgSameName
     */
    public String getFlgSameName() {
        return flgSameName;
    }

    /**
     * @param flgSameName the flgSameName to set
     */
    public void setFlgSameName(String flgSameName) {
        this.flgSameName = flgSameName;
    }

    /**
     * @return the optExt
     */
    public String getOptExt() {
        return optExt;
    }

    /**
     * @param optExt the optExt to set
     */
    public void setOptExt(String optExt) {
        this.optExt = optExt;
    }
}
