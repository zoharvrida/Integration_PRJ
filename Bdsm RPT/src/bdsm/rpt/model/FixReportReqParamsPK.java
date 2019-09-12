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
public class FixReportReqParamsPK implements Serializable {
    private String idBatch;
    private Integer idReport;
    private Integer noSeq;
    private String value;

    /**
     * @return the idBatch
     */
    public String getIdBatch() {
        return idBatch;
    }

    /**
     * @param idBatch the idBatch to set
     */
    public void setIdBatch(String idBatch) {
        this.idBatch = idBatch;
    }

    /**
     * @return the idReport
     */
    public Integer getIdReport() {
        return idReport;
    }

    /**
     * @param idReport the idReport to set
     */
    public void setIdReport(Integer idReport) {
        this.idReport = idReport;
    }

    /**
     * @return the noSeq
     */
    public Integer getNoSeq() {
        return noSeq;
    }

    /**
     * @param noSeq the noSeq to set
     */
    public void setNoSeq(Integer noSeq) {
        this.noSeq = noSeq;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }
}
