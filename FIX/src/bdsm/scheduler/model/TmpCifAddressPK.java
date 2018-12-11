/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

/**
 *
 * @author v00020841
 */
public class TmpCifAddressPK implements java.io.Serializable{
    private String batchId;
    private String codFieldFP;
    private Integer cif;
    private Integer recordId;

    /**
     * 
     * @return
     */
    public String getBatchId() {
        return batchId;
    }
    /**
     * 
     * @param batchId
     */
    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    /**
     * 
     * @return
     */
    public String getCodFieldFP() {
        return codFieldFP;
    }
    /**
     * 
     * @param codFieldFP
     */
    public void setCodFieldFP(String codFieldFP) {
        this.codFieldFP = codFieldFP;
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

    
}
