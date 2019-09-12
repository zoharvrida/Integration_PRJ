/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

/**
 *
 * @author v00020841
 */
public class TmpMaintenanceRrm01PK implements java.io.Serializable {

    private String batchiId;
    private Integer recordId;
    private String codId;

    public String getBatchiId() {
        return batchiId;
    }

    public void setBatchiId(String batchiId) {
        this.batchiId = batchiId;
    }

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public String getCodId() {
        return codId;
    }

    public void setCodId(String codId) {
        this.codId = codId;
    }
    
    
}
