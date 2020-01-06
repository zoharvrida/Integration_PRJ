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
public class FixMasterReport extends BaseModel {
    private FixMasterReportPK compositeId;
    private String namScheduler;
    private String reportName;
    private String paramCount;
    private String remarks;
    private String timestamp;
    /**
     * @return the namScheduler
     */
    public String getNamScheduler() {
        return namScheduler;
    }

    @Override
    public String toString() {
        return "FixMasterReport{" + "compositeId=" + compositeId + ", namScheduler=" + namScheduler + ", reportName=" + reportName + ", paramCount=" + paramCount + ", remarks=" + remarks + ", timestamp=" + timestamp + '}';
    }

    
    /**
     * @param namScheduler the namScheduler to set
     */
    public void setNamScheduler(String namScheduler) {
        this.namScheduler = namScheduler;
    }

    /**
     * @return the reportName
     */
    public String getReportName() {
        return reportName;
    }

    /**
     * @param reportName the reportName to set
     */
    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    /**
     * @return the paramCount
     */
    public String getParamCount() {
        return paramCount;
    }

    /**
     * @param paramCount the paramCount to set
     */
    public void setParamCount(String paramCount) {
        this.paramCount = paramCount;
    }

    /**
     * @return the remarks
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * @param remarks the remarks to set
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * @return the compositeId
     */
    public FixMasterReportPK getCompositeId() {
        return compositeId;
    }

    /**
     * @param compositeId the compositeId to set
     */
    public void setCompositeId(FixMasterReportPK compositeId) {
        this.compositeId = compositeId;
    }

    /**
     * @return the timestamp
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
