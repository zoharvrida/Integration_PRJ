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
public class FixReportParamPK implements Serializable{
    private String idReport;
    private Integer seq;

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
     * @return the seq
     */
    public Integer getSeq() {
        return seq;
    }

    /**
     * @param seq the seq to set
     */
    public void setSeq(Integer seq) {
        this.seq = seq;
    }
}
