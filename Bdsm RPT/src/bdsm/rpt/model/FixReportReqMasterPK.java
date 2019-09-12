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
public class FixReportReqMasterPK implements Serializable {
    private String idBatch;

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
}
