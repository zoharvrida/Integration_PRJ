/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

/**
 *
 * @author v00020800
 */
public class TmpMaintenanceCim17ShareHoldPK implements java.io.Serializable{
    private String batchiId;
    private Integer recordId;
    private Integer cif;

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

    /**
     * @return the cif
     */
    public Integer getCif() {
        return cif;
    }

    /**
     * @param cif the cif to set
     */
    public void setCif(Integer cif) {
        this.cif = cif;
    }
}
