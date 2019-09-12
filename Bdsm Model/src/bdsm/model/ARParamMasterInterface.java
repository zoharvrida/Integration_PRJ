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
public class ARParamMasterInterface implements Serializable {
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
    private String delimiter;
    private String typProcess;
    private Integer orderNo;
    private String flgValidation;
    private String recType;
    private Integer dependNo;
    private String getQuery;
    private String typScheduler;

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
     * @return the typProcess
     */
    public String getTypProcess() {
        return typProcess;
    }

    /**
     * @param typProcess the typProcess to set
     */
    public void setTypProcess(String typProcess) {
        this.typProcess = typProcess;
    }

    /**
     * @return the orderNo
     */
    public Integer getOrderNo() {
        return orderNo;
    }

    /**
     * @param orderNo the orderNo to set
     */
    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * @return the flgValidation
     */
    public String getFlgValidation() {
        return flgValidation;
    }

    /**
     * @param flgValidation the flgValidation to set
     */
    public void setFlgValidation(String flgValidation) {
        this.flgValidation = flgValidation;
    }

    /**
     * @return the recType
     */
    public String getRecType() {
        return recType;
    }

    /**
     * @param recType the recType to set
     */
    public void setRecType(String recType) {
        this.recType = recType;
    }

    /**
     * @return the dependNo
     */
    public Integer getDependNo() {
        return dependNo;
    }

    /**
     * @param dependNo the dependNo to set
     */
    public void setDependNo(Integer dependNo) {
        this.dependNo = dependNo;
    }

    /**
     * @return the getQuery
     */
    public String getGetQuery() {
        return getQuery;
    }

    /**
     * @param getQuery the getQuery to set
     */
    public void setGetQuery(String getQuery) {
        this.getQuery = getQuery;
    }

    @Override
    public String toString() {
        return "ARParamMasterInterface{" + "idScheduler=" + idScheduler + ", tableName=" + tableName + ", dataSource=" + dataSource + ", validationFunction=" + validationFunction + ", processFunction=" + processFunction + ", rejectFunction=" + rejectFunction + ", idBefore=" + idBefore + ", idAfter=" + idAfter + ", trackingFlag=" + trackingFlag + ", queryRet=" + queryRet + ", delimiter=" + delimiter + ", typProcess=" + typProcess + ", orderNo=" + orderNo + ", flgValidation=" + flgValidation + ", recType=" + recType + ", dependNo=" + dependNo + ", getQuery=" + getQuery + '}';
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
