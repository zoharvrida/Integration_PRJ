/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

import bdsm.model.BaseModel;
import java.util.Date;

/**
 *
 * @author v00020800
 */
public class OtpCustDetail extends BaseModel {

    private String batchId;
    private String typeRecord;
    private String typeOtp;
    private Integer recordId;
    private String cifNumber;
    private String nama;
    private String spk;
    private String kodeAo;
    private String lob;
    private String costCenter;
    private String grading;
    private String codprodNcbs;
    private String codprodIcbs;
    private String variance;
    private String bookingLoan;
    private String ac;
    private Double fasilitas;
    private Double plafonAwal;
    private Double os;
    private Double transaksiOtp;
    private Double sisaPlafon;
    private Double jumlah;
    private Double indexRate;
    private String ratecode;
    private String periode;
    private String sukuBunga;
    private Double ratePinalty;
    private String tanggalPelunasan;
    private String status;
    private String flgStatus;
    private String comments;


    /**
     * @return the batchId
     */
    public String getBatchId() {
        return batchId;
    }

    /**
     * @param batchId the batchId to set
     */
    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    /**
     * @return the typeRecord
     */
    public String getTypeRecord() {
        return typeRecord;
    }

    /**
     * @param typeRecord the typeRecord to set
     */
    public void setTypeRecord(String typeRecord) {
        this.typeRecord = typeRecord;
    }

    /**
     * @return the typeOtp
     */
    public String getTypeOtp() {
        return typeOtp;
    }

    /**
     * @param typeOtp the typeOtp to set
     */
    public void setTypeOtp(String typeOtp) {
        this.typeOtp = typeOtp;
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
     * @return the cifNumber
     */
    public String getCifNumber() {
        return cifNumber;
    }

    /**
     * @param cifNumber the cifNumber to set
     */
    public void setCifNumber(String cifNumber) {
        this.cifNumber = cifNumber;
    }

    /**
     * @return the nama
     */
    public String getNama() {
        return nama;
    }

    /**
     * @param nama the nama to set
     */
    public void setNama(String nama) {
        this.nama = nama;
    }

    /**
     * @return the spk
     */
    public String getSpk() {
        return spk;
    }

    /**
     * @param spk the spk to set
     */
    public void setSpk(String spk) {
        this.spk = spk;
    }

    /**
     * @return the kodeAo
     */
    public String getKodeAo() {
        return kodeAo;
    }

    /**
     * @param kodeAo the kodeAo to set
     */
    public void setKodeAo(String kodeAo) {
        this.kodeAo = kodeAo;
    }

    /**
     * @return the lob
     */
    public String getLob() {
        return lob;
    }

    /**
     * @param lob the lob to set
     */
    public void setLob(String lob) {
        this.lob = lob;
    }

    /**
     * @return the costCenter
     */
    public String getCostCenter() {
        return costCenter;
    }

    /**
     * @param costCenter the costCenter to set
     */
    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter;
    }

    /**
     * @return the grading
     */
    public String getGrading() {
        return grading;
    }

    /**
     * @param grading the grading to set
     */
    public void setGrading(String grading) {
        this.grading = grading;
    }

    /**
     * @return the codprodNcbs
     */
    public String getCodprodNcbs() {
        return codprodNcbs;
    }

    /**
     * @param codprodNcbs the codprodNcbs to set
     */
    public void setCodprodNcbs(String codprodNcbs) {
        this.codprodNcbs = codprodNcbs;
    }

    /**
     * @return the codprodIcbs
     */
    public String getCodprodIcbs() {
        return codprodIcbs;
    }

    /**
     * @param codprodIcbs the codprodIcbs to set
     */
    public void setCodprodIcbs(String codprodIcbs) {
        this.codprodIcbs = codprodIcbs;
    }

    /**
     * @return the variance
     */
    public String getVariance() {
        return variance;
    }

    /**
     * @param variance the variance to set
     */
    public void setVariance(String variance) {
        this.variance = variance;
    }

    /**
     * @return the bookingLoan
     */
    public String getBookingLoan() {
        return bookingLoan;
    }

    /**
     * @param bookingLoan the bookingLoan to set
     */
    public void setBookingLoan(String bookingLoan) {
        this.bookingLoan = bookingLoan;
    }

    /**
     * @return the ac
     */
    public String getAc() {
        return ac;
    }

    /**
     * @param ac the ac to set
     */
    public void setAc(String ac) {
        this.ac = ac;
    }

    /**
     * @return the fasilitas
     */
    public Double getFasilitas() {
        return fasilitas;
    }

    /**
     * @param fasilitas the fasilitas to set
     */
    public void setFasilitas(Double fasilitas) {
        this.fasilitas = fasilitas;
    }

    /**
     * @return the plafonAwal
     */
    public Double getPlafonAwal() {
        return plafonAwal;
    }

    /**
     * @param plafonAwal the plafonAwal to set
     */
    public void setPlafonAwal(Double plafonAwal) {
        this.plafonAwal = plafonAwal;
    }

    /**
     * @return the os
     */
    public Double getOs() {
        return os;
    }

    /**
     * @param os the os to set
     */
    public void setOs(Double os) {
        this.os = os;
    }

    /**
     * @return the transaksiOtp
     */
    public Double getTransaksiOtp() {
        return transaksiOtp;
    }

    /**
     * @param transaksiOtp the transaksiOtp to set
     */
    public void setTransaksiOtp(Double transaksiOtp) {
        this.transaksiOtp = transaksiOtp;
    }

    /**
     * @return the sisaPlafon
     */
    public Double getSisaPlafon() {
        return sisaPlafon;
    }

    /**
     * @param sisaPlafon the sisaPlafon to set
     */
    public void setSisaPlafon(Double sisaPlafon) {
        this.sisaPlafon = sisaPlafon;
    }

    /**
     * @return the jumlah
     */
    public Double getJumlah() {
        return jumlah;
    }

    /**
     * @param jumlah the jumlah to set
     */
    public void setJumlah(Double jumlah) {
        this.jumlah = jumlah;
    }

    /**
     * @return the indexRate
     */
    public Double getIndexRate() {
        return indexRate;
    }

    /**
     * @param indexRate the indexRate to set
     */
    public void setIndexRate(Double indexRate) {
        this.indexRate = indexRate;
    }

    /**
     * @return the ratecode
     */
    public String getRatecode() {
        return ratecode;
    }

    /**
     * @param ratecode the ratecode to set
     */
    public void setRatecode(String ratecode) {
        this.ratecode = ratecode;
    }

    /**
     * @return the periode
     */
    public String getPeriode() {
        return periode;
    }
   
    /**
     * @param periode the periode to set
     */
    public void setPeriode(String periode) {
        this.periode = periode;
    }

    /**
     * @return the sukuBunga
     */
    public String getSukuBunga() {
        return sukuBunga;
    }

    /**
     * @param sukuBunga the sukuBunga to set
     */
    public void setSukuBunga(String sukuBunga) {
        this.sukuBunga = sukuBunga;
    }

    /**
     * @return the ratePinalty
     */
    public Double getRatePinalty() {
        return ratePinalty;
    }

    /**
     * @param ratePinalty the ratePinalty to set
     */
    public void setRatePinalty(Double ratePinalty) {
        this.ratePinalty = ratePinalty;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the comments
     */
    public String getComments() {
        return comments;
    }

    /**
     * @param comments the comments to set
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    /**
     * @return the tanggalPelunasan
     */
    public String getTanggalPelunasan() {
        return tanggalPelunasan;
    }

    /**
     * @param tanggalPelunasan the tanggalPelunasan to set
     */
    public void setTanggalPelunasan(String tanggalPelunasan) {
        this.tanggalPelunasan = tanggalPelunasan;
    }
   
    /**
     * @return the flgStatus
     */
    public String getFlgStatus() {
        return flgStatus;
    }

    /**
     * @param flgStatus the flgStatus to set
     */
    public void setFlgStatus(String flgStatus) {
        this.flgStatus = flgStatus;
    }
   
}
