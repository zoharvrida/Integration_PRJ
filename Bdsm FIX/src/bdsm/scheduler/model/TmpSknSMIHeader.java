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
public class TmpSknSMIHeader extends BaseModel{
    private TmpSKNSMIPK pk; 
    private String tipeRecord;
    private String kode_dke;
    private String batch_ID;
    private String BatchGen2;
    private String jumlahRecords;
    private String TotalNominal;
    private String tanggalBatch;
	private String extractStatus;

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
     * @return the kode_dke
     */
    public String getKode_dke() {
        return kode_dke;
    }

    /**
     * @param kode_dke the kode_dke to set
     */
    public void setKode_dke(String kode_dke) {
        this.kode_dke = kode_dke;
    }

    /**
     * @return the batch_ID
     */
    public String getBatch_ID() {
        return batch_ID;
    }

    /**
     * @param batch_ID the batch_ID to set
     */
    public void setBatch_ID(String batch_ID) {
        this.batch_ID = batch_ID;
    }

    /**
     * @return the BatchGen2
     */
    public String getBatchGen2() {
        return BatchGen2;
    }

    /**
     * @param BatchGen2 the BatchGen2 to set
     */
    public void setBatchGen2(String BatchGen2) {
        this.BatchGen2 = BatchGen2;
    }

    /**
     * @return the jumlahRecords
     */
    public String getJumlahRecords() {
        return jumlahRecords;
    }

    /**
     * @param jumlahRecords the jumlahRecords to set
     */
    public void setJumlahRecords(String jumlahRecords) {
        this.jumlahRecords = jumlahRecords;
    }

    /**
     * @return the TotalNominal
     */
    public String getTotalNominal() {
        return TotalNominal;
    }

    /**
     * @param TotalNominal the TotalNominal to set
     */
    public void setTotalNominal(String TotalNominal) {
        this.TotalNominal = TotalNominal;
    }

    /**
     * @return the tanggalBatch
     */
    public String getTanggalBatch() {
        return tanggalBatch;
    }

    /**
     * @param tanggalBatch the tanggalBatch to set
     */
    public void setTanggalBatch(String tanggalBatch) {
        this.tanggalBatch = tanggalBatch;
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
