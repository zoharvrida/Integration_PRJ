/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author v00024535
 */
public class EtaxPrint extends BaseModel implements Serializable {

    private String idTrx;
    private String tglBayar;
    private Date tglBuku;
    private String kodeCabang;
    private String ntb;
    private String ntpn;
    private String stan;
    private String kodeBilling;
    private String npwp;
    private String wpName;
    private String wpAddress;
    private String nop;
    private BigDecimal jmlSetoran;
    private String jenisDokumen;
    private String nomorDokumen;
    private Date tglDokumen;
    private String mataAnggaran;
    private String masaPajak;
    private String noKetetapan;
    private String billingType;
    private String mataUang;
    private String terbilang;
    private String jenisSetoran;
    private String kl;
    private String unitEselon;
    private String satuanKerja;
    private String kodeKppbc;

    /**
     * @return the idTrx
     */
    public String getIdTrx() {
        return idTrx;
    }

    @Override
    public String toString() {
        return "EtaxPrint{" + "idTrx=" + idTrx + ", tglBayar=" + tglBayar + ", tglBuku=" + tglBuku + ", kodeCabang=" + kodeCabang + ", ntb=" + ntb + ", ntpn=" + ntpn + ", stan=" + stan + ", kodeBilling=" + kodeBilling + ", npwp=" + npwp + ", wpName=" + wpName + ", wpAddress=" + wpAddress + ", nop=" + nop + ", jmlSetoran=" + jmlSetoran + ", jenisDokumen=" + jenisDokumen + ", nomorDokumen=" + nomorDokumen + ", tglDokumen=" + tglDokumen + ", mataAnggaran=" + mataAnggaran + ", masaPajak=" + masaPajak + ", noKetetapan=" + noKetetapan + ", billingType=" + billingType + ", mataUang=" + mataUang + ", terbilang=" + terbilang + ", jenisSetoran=" + jenisSetoran + '}';
    }

    /**
     * @param idTrx the idTrx to set
     */
    public void setIdTrx(String idTrx) {
        this.idTrx = idTrx;
    }

    /**
     * @return the tglBuku
     */
    public Date getTglBuku() {
        return tglBuku;
    }

    /**
     * @param tglBuku the tglBuku to set
     */
    public void setTglBuku(Date tglBuku) {
        this.tglBuku = tglBuku;
    }

    /**
     * @return the kodeCabang
     */
    public String getKodeCabang() {
        return kodeCabang;
    }

    /**
     * @param kodeCabang the kodeCabang to set
     */
    public void setKodeCabang(String kodeCabang) {
        this.kodeCabang = kodeCabang;
    }

    /**
     * @return the ntb
     */
    public String getNtb() {
        return ntb;
    }

    /**
     * @param ntb the ntb to set
     */
    public void setNtb(String ntb) {
        this.ntb = ntb;
    }

    /**
     * @return the ntpn
     */
    public String getNtpn() {
        return ntpn;
    }

    /**
     * @param ntpn the ntpn to set
     */
    public void setNtpn(String ntpn) {
        this.ntpn = ntpn;
    }

    /**
     * @return the stan
     */
    public String getStan() {
        return stan;
    }

    /**
     * @param stan the stan to set
     */
    public void setStan(String stan) {
        this.stan = stan;
    }

    /**
     * @return the kodeBilling
     */
    public String getKodeBilling() {
        return kodeBilling;
    }

    /**
     * @param kodeBilling the kodeBilling to set
     */
    public void setKodeBilling(String kodeBilling) {
        this.kodeBilling = kodeBilling;
    }

    /**
     * @return the npwp
     */
    public String getNpwp() {
        return npwp;
    }

    /**
     * @param npwp the npwp to set
     */
    public void setNpwp(String npwp) {
        this.npwp = npwp;
    }

    /**
     * @return the wpName
     */
    public String getWpName() {
        return wpName;
    }

    /**
     * @param wpName the wpName to set
     */
    public void setWpName(String wpName) {
        this.wpName = wpName;
    }

    /**
     * @return the wpAddress
     */
    public String getWpAddress() {
        return wpAddress;
    }

    /**
     * @param wpAddress the wpAddress to set
     */
    public void setWpAddress(String wpAddress) {
        this.wpAddress = wpAddress;
    }

    /**
     * @return the nop
     */
    public String getNop() {
        return nop;
    }

    /**
     * @param nop the nop to set
     */
    public void setNop(String nop) {
        this.nop = nop;
    }

    /**
     * @return the jmlSetoran
     */
    public BigDecimal getJmlSetoran() {
        return jmlSetoran;
    }

    /**
     * @param jmlSetoran the jmlSetoran to set
     */
    public void setJmlSetoran(BigDecimal jmlSetoran) {
        this.jmlSetoran = jmlSetoran;
    }

    /**
     * @return the jenisDokumen
     */
    public String getJenisDokumen() {
        return jenisDokumen;
    }

    /**
     * @param jenisDokumen the jenisDokumen to set
     */
    public void setJenisDokumen(String jenisDokumen) {
        this.jenisDokumen = jenisDokumen;
    }

    /**
     * @return the nomorDokumen
     */
    public String getNomorDokumen() {
        return nomorDokumen;
    }

    /**
     * @param nomorDokumen the nomorDokumen to set
     */
    public void setNomorDokumen(String nomorDokumen) {
        this.nomorDokumen = nomorDokumen;
    }

    /**
     * @return the tglDokumen
     */
    public Date getTglDokumen() {
        return tglDokumen;
    }

    /**
     * @param tglDokumen the tglDokumen to set
     */
    public void setTglDokumen(Date tglDokumen) {
        this.tglDokumen = tglDokumen;
    }

    /**
     * @return the mataAnggaran
     */
    public String getMataAnggaran() {
        return mataAnggaran;
    }

    /**
     * @param mataAnggaran the mataAnggaran to set
     */
    public void setMataAnggaran(String mataAnggaran) {
        this.mataAnggaran = mataAnggaran;
    }

    /**
     * @return the masaPajak
     */
    public String getMasaPajak() {
        return masaPajak;
    }

    /**
     * @param masaPajak the masaPajak to set
     */
    public void setMasaPajak(String masaPajak) {
        this.masaPajak = masaPajak;
    }

    /**
     * @return the noKetetapan
     */
    public String getNoKetetapan() {
        return noKetetapan;
    }

    /**
     * @param noKetetapan the noKetetapan to set
     */
    public void setNoKetetapan(String noKetetapan) {
        this.noKetetapan = noKetetapan;
    }

    /**
     * @return the billingType
     */
    public String getBillingType() {
        return billingType;
    }

    /**
     * @param billingType the billingType to set
     */
    public void setBillingType(String billingType) {
        this.billingType = billingType;
    }

    /**
     * @return the tglBayar
     */
    public String getTglBayar() {
        return tglBayar;
    }

    /**
     * @param tglBayar the tglBayar to set
     */
    public void setTglBayar(String tglBayar) {
        this.tglBayar = tglBayar;
    }

    /**
     * @return the terbilang
     */
    public String getTerbilang() {
        return terbilang;
    }

    /**
     * @param terbilang the terbilang to set
     */
    public void setTerbilang(String terbilang) {
        this.terbilang = terbilang;
    }

    /**
     * @return the mataUang
     */
    public String getMataUang() {
        return mataUang;
    }

    /**
     * @param mataUang the mataUang to set
     */
    public void setMataUang(String mataUang) {
        this.mataUang = mataUang;
    }

    /**
     * @return the jenisSetoran
     */
    public String getJenisSetoran() {
        return jenisSetoran;
    }

    /**
     * @param jenisSetoran the jenisSetoran to set
     */
    public void setJenisSetoran(String jenisSetoran) {
        this.jenisSetoran = jenisSetoran;
    }

    /**
     * @return the kl
     */
    public String getKl() {
        return kl;
    }

    /**
     * @param kl the kl to set
     */
    public void setKl(String kl) {
        this.kl = kl;
    }

    /**
     * @return the unitEselon
     */
    public String getUnitEselon() {
        return unitEselon;
    }

    /**
     * @param unitEselon the unitEselon to set
     */
    public void setUnitEselon(String unitEselon) {
        this.unitEselon = unitEselon;
    }

    /**
     * @return the satuanKerja
     */
    public String getSatuanKerja() {
        return satuanKerja;
    }

    /**
     * @param satuanKerja the satuanKerja to set
     */
    public void setSatuanKerja(String satuanKerja) {
        this.satuanKerja = satuanKerja;
    }

    /**
     * @return the kodeKppbc
     */
    public String getKodeKppbc() {
        return kodeKppbc;
    }

    /**
     * @param kodeKppbc the kodeKppbc to set
     */
    public void setKodeKppbc(String kodeKppbc) {
        this.kodeKppbc = kodeKppbc;
    }
}
