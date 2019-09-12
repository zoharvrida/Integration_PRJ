/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.rpt.model;

import java.io.Serializable;

/**
 *
 * @author v00019722
 */
public class FixMasterReportPK implements Serializable {
    private String idReport;
    private Integer idScheduler;

    /**
     * @return the idReport
     */
    public String getIdReport() {
        return idReport;
    }

    /**
     * @param idReport the idReport to set
     */
    public void setIdReport(String idReport) {
        this.idReport = idReport;
    }

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
}
