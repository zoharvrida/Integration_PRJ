/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

import bdsm.model.BaseModel;
import java.util.Date;

/**
 *
 * @author NCBS
 */
public class UajTmpSrc extends BaseModel{
    private String DealerNo;
    private String AajiCert;
    private String Nip;
    private String Status;
    private Date CertExpiryDate;
        private String CertType;
    private String NoBatch;
    private String FlgStatus;
    private String StatusReason;
    private int id;

    /**
     * @return the DealerNo
     */
    public String getDealerNo() {
        return DealerNo;
    }

    /**
     * @param DealerNo the DealerNo to set
     */
    public void setDealerNo(String DealerNo) {
        this.DealerNo = DealerNo;
    }

    /**
     * @return the AajiCert
     */
    public String getAajiCert() {
        return AajiCert;
    }

    /**
     * @param AajiCert the AajiCert to set
     */
    public void setAajiCert(String AajiCert) {
        this.AajiCert = AajiCert;
    }

    /**
     * @return the Nip
     */
    public String getNip() {
        return Nip;
    }

    /**
     * @param Nip the Nip to set
     */
    public void setNip(String Nip) {
        this.Nip = Nip;
    }

    /**
     * @return the Status
     */
    public String getStatus() {
        return Status;
    }

    /**
     * @param Status the Status to set
     */
    public void setStatus(String Status) {
        this.Status = Status;
    }

    /**
     * @return the CertExpiryDate
     */
    public Date getCertExpiryDate() {
        return CertExpiryDate;
    }

    /**
     * @param CertExpiryDate the CertExpiryDate to set
     */
    public void setCertExpiryDate(Date CertExpiryDate) {
        this.CertExpiryDate = CertExpiryDate;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the NoBatch
     */
    public String getNoBatch() {
        return NoBatch;
    }

    /**
     * @param NoBatch the NoBatch to set
     */
    public void setNoBatch(String NoBatch) {
        this.NoBatch = NoBatch;
    }

    /**
     * @return the FlgStatus
     */
    public String getFlgStatus() {
        return FlgStatus;
    }

    /**
     * @param FlgStatus the FlgStatus to set
     */
    public void setFlgStatus(String FlgStatus) {
        this.FlgStatus = FlgStatus;
    }

    /**
     * @return the StatusReason
     */
    public String getStatusReason() {
        return StatusReason;
    }

    /**
     * @param StatusReason the StatusReason to set
     */
    public void setStatusReason(String StatusReason) {
        this.StatusReason = StatusReason;
    }

    /**
     * @return the CertType
     */
    public String getCertType() {
        return CertType;
    }

    /**
     * @param CertType the CertType to set
     */
    public void setCertType(String CertType) {
        this.CertType = CertType;
    }
}
