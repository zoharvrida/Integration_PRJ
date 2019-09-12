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
public class XrefReportTemplatePK implements Serializable {
    private String idTemplate;
    private String idMasterReport;

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

    /**
     * @return the idMasterReport
     */
    public String getIdMasterReport() {
        return idMasterReport;
    }

    /**
     * @param idMasterReport the idMasterReport to set
     */
    public void setIdMasterReport(String idMasterReport) {
        this.idMasterReport = idMasterReport;
    }

}
