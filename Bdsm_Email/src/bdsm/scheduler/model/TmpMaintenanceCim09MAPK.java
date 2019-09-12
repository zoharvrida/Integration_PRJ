/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

/**
 *
 * @author v00020841
 */
public class TmpMaintenanceCim09MAPK implements java.io.Serializable {

    private String batchiId;
    private Integer recordId;
    private Integer cif;

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

    public Integer getCif() {
        return cif;
    }

    public void setCif(Integer cif) {
        this.cif = cif;
    }
}
