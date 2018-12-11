/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

/**
 *
 * @author v00020841
 */
public class TmpMaintenanceCim09ICIPK implements java.io.Serializable {

    private String batchiId;
    private Integer recordId;
    private Integer cif;

    /**
     * 
     * @return
     */
    public String getBatchiId() {
        return batchiId;
    }

    /**
     * 
     * @param batchiId
     */
    public void setBatchiId(String batchiId) {
        this.batchiId = batchiId;
    }

    /**
     * 
     * @return
     */
    public Integer getRecordId() {
        return recordId;
    }

    /**
     * 
     * @param recordId
     */
    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    /**
     * 
     * @return
     */
    public Integer getCif() {
        return cif;
    }

    /**
     * 
     * @param cif
     */
    public void setCif(Integer cif) {
        this.cif = cif;
    }
    
}
