/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;

import java.io.Serializable;

/**
 *
 * @author v00024535
 */
public class DNPReport implements Serializable {

    private String Strval;
    private String TrxDateTime;
    private String TrxHourTime;
    private String TanggalBuku;
    private String BillingId;
    private String Ntb;
    private String Ntpn;
    private String JmlSetoran;
    private String MataUang;
    private String noSk;

    /**
     * @return the Strval
     */
    public String getStrval() {
        return Strval;
    }

    /**
     * @param Strval the Strval to set
     */
    public void setStrval(String Strval) {
        this.Strval = Strval;
    }

    /**
     * @return the TrxDateTime
     */
    public String getTrxDateTime() {
        return TrxDateTime;
    }

    /**
     * @param TrxDateTime the TrxDateTime to set
     */
    public void setTrxDateTime(String TrxDateTime) {
        this.TrxDateTime = TrxDateTime;
    }

    /**
     * @return the TrxHourTime
     */
    public String getTrxHourTime() {
        return TrxHourTime;
    }

    /**
     * @param TrxHourTime the TrxHourTime to set
     */
    public void setTrxHourTime(String TrxHourTime) {
        this.TrxHourTime = TrxHourTime;
    }

    /**
     * @return the TanggalBuku
     */
    public String getTanggalBuku() {
        return TanggalBuku;
    }

    /**
     * @param TanggalBuku the TanggalBuku to set
     */
    public void setTanggalBuku(String TanggalBuku) {
        this.TanggalBuku = TanggalBuku;
    }

    /**
     * @return the BillingId
     */
    public String getBillingId() {
        return BillingId;
    }

    /**
     * @param BillingId the BillingId to set
     */
    public void setBillingId(String BillingId) {
        this.BillingId = BillingId;
    }

    /**
     * @return the Ntb
     */
    public String getNtb() {
        return Ntb;
    }

    /**
     * @param Ntb the Ntb to set
     */
    public void setNtb(String Ntb) {
        this.Ntb = Ntb;
    }

    /**
     * @return the Ntpn
     */
    public String getNtpn() {
        return Ntpn;
    }

    /**
     * @param Ntpn the Ntpn to set
     */
    public void setNtpn(String Ntpn) {
        this.Ntpn = Ntpn;
    }

    /**
     * @return the JmlSetoran
     */
    public String getJmlSetoran() {
        return JmlSetoran;
    }

    /**
     * @param JmlSetoran the JmlSetoran to set
     */
    public void setJmlSetoran(String JmlSetoran) {
        this.JmlSetoran = JmlSetoran;
    }

    /**
     * @return the MataUang
     */
    public String getMataUang() {
        return MataUang;
    }

    /**
     * @param MataUang the MataUang to set
     */
    public void setMataUang(String MataUang) {
        this.MataUang = MataUang;
    }

    /**
     * @return the noSk
     */
    public String getNoSk() {
        return noSk;
    }

    /**
     * @param noSk the noSk to set
     */
    public void setNoSk(String noSk) {
        this.noSk = noSk;
    }
}
