/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.rpt.model;

import bdsm.rpt.model.XrefReportTemplate;

/**
 *
 * @author v00019722
 */
public class RepDelComp extends XrefReportTemplate {
    private String reportName;
    private String idTemplate;

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
     * @return the idTemplate
     */
    public String getIdTemplate() {
        return idTemplate;
    }

    /**
     * @param idTemplate the idTemplate to set
     */
    public void setIdTemplate(String idTemplate) {
        this.idTemplate = idTemplate;
    }
}
