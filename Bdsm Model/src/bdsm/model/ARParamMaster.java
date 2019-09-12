/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;

/**
 *
 * @author SDM
 */
public class ARParamMaster {
    private Integer idScheduler;
    private String tableName;
    private String dataSource;
    private String validationFunction;
    private String processFunction;
    private String rejectFunction;
    private Integer idBefore;
    private Integer idAfter;
    private String trackingFlag;
    private String queryRet;

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
     * @return the tableName
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * @param tableName the tableName to set
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
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
     * @return the idBefore
     */
    public Integer getIdBefore() {
        return idBefore;
    }

    /**
     * @param idBefore the idBefore to set
     */
    public void setIdBefore(Integer idBefore) {
        this.idBefore = idBefore;
    }

    /**
     * @return the idAfter
     */
    public Integer getIdAfter() {
        return idAfter;
    }

    /**
     * @param idAfter the idAfter to set
     */
    public void setIdAfter(Integer idAfter) {
        this.idAfter = idAfter;
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
     * @return the queryRet
     */
    public String getQueryRet() {
        return queryRet;
    }

    /**
     * @param queryRet the queryRet to set
     */
    public void setQueryRet(String queryRet) {
        this.queryRet = queryRet;
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
}
