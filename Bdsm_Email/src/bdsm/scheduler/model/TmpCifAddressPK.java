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

    public String getBatchId() {
        return batchId;
    }
    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getCodFieldFP() {
        return codFieldFP;
    }
    public void setCodFieldFP(String codFieldFP) {
        this.codFieldFP = codFieldFP;
    }
    
    public Integer getCif() {
        return cif;
    }
    public void setCif(Integer cif) {
        this.cif = cif;
    }

    public Integer getRecordId() {
        return recordId;
    }
    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    
}
