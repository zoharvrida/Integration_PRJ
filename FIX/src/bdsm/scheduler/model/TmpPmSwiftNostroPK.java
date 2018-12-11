/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

import java.io.Serializable;

/**
 *
 * @author v00020800
 */
public class TmpPmSwiftNostroPK implements Serializable {
    private String batchiId;
    private Integer recordId;

    /**
     * @return the batchiId
     */
    public String getBatchiId() {
        return batchiId;
    }

    /**
     * @param batchiId the batchiId to set
     */
    public void setBatchiId(String batchiId) {
        this.batchiId = batchiId;
    }

    /**
     * @return the recordId
     */
    public Integer getRecordId() {
        return recordId;
    }

    /**
     * @param recordId the recordId to set
     */
    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    
}
