/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

import bdsm.model.BaseModel;

/**
 *
 * @author v00019722
 */
public class TmpSKNSMIFooter extends BaseModel {
    private TmpSKNSMIPK pk; 
    private String tipeRecord;
    private String JumlahRecords;
    private String totalNominal;
	private String extractStatus;

    /**
     * @return the tipeRecord
     */
    public String getTipeRecord() {
        return tipeRecord;
    }

    /**
     * @param tipeRecord the tipeRecord to set
     */
    public void setTipeRecord(String tipeRecord) {
        this.tipeRecord = tipeRecord;
    }

    /**
     * @return the JumlahRecords
     */
    public String getJumlahRecords() {
        return JumlahRecords;
    }

    /**
     * @param JumlahRecords the JumlahRecords to set
     */
    public void setJumlahRecords(String JumlahRecords) {
        this.JumlahRecords = JumlahRecords;
    }

    /**
     * @return the totalNominal
     */
    public String getTotalNominal() {
        return totalNominal;
    }

    /**
     * @param totalNominal the totalNominal to set
     */
    public void setTotalNominal(String totalNominal) {
        this.totalNominal = totalNominal;
    }

	/**
	 * @return the pk
	 */
	public TmpSKNSMIPK getPk() {
		return pk;
	}

	/**
	 * @param pk the pk to set
	 */
	public void setPk(TmpSKNSMIPK pk) {
		this.pk = pk;
	}

	/**
	 * @return the extractStatus
	 */
	public String getExtractStatus() {
		return extractStatus;
	}

	/**
	 * @param extractStatus the extractStatus to set
	 */
	public void setExtractStatus(String extractStatus) {
		this.extractStatus = extractStatus;
	}

}
