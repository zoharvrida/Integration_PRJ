package bdsm.model;

import java.io.Serializable;

/**
 *
 * @author Jaka
 */
public class ETaxBillingInfo implements Serializable {
    
    private ETaxBillingType type;
    private String billingId;
    private String wpName;
    /** DJP **/
    private String npwp;
    private String wpAddress;
    private String akun;
    private String kodeJenisSetoran;
    private String masaPajak;
    private String nomorSK;
    private String nop;
    /** End of DJP **/
    /** DJBC **/
    private String idWajibBayar;
    private String jenisDokumen;
    private String nomorDokumen;
    private String tanggalDokumen;
    private String kodeKPBC;
    /** End of DJBC **/
    /** DJA **/
    private String kl;
    private String unitEselon1;
    private String kodeSatker;
    /** End of DJA **/

    public ETaxBillingInfo() {
    }

    /**
     * @return the type
     */
    public ETaxBillingType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(ETaxBillingType type) {
        this.type = type;
    }

    /**
     * @return the billingId
     */
    public String getBillingId() {
        return billingId;
    }

    /**
     * @param billingId the billingId to set
     */
    public void setBillingId(String billingId) {
        this.billingId = billingId;
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
     * @return the akun
     */
    public String getAkun() {
        return akun;
    }

    /**
     * @param akun the akun to set
     */
    public void setAkun(String akun) {
        this.akun = akun;
    }

    /**
     * @return the kodeJenisSetoran
     */
    public String getKodeJenisSetoran() {
        return kodeJenisSetoran;
    }

    /**
     * @param kodeJenisSetoran the kodeJenisSetoran to set
     */
    public void setKodeJenisSetoran(String kodeJenisSetoran) {
        this.kodeJenisSetoran = kodeJenisSetoran;
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
     * @return the nomorSK
     */
    public String getNomorSK() {
        return nomorSK;
    }

    /**
     * @param nomorSK the nomorSK to set
     */
    public void setNomorSK(String nomorSK) {
        this.nomorSK = nomorSK;
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
     * @return the idWajibBayar
     */
    public String getIdWajibBayar() {
        return idWajibBayar;
    }

    /**
     * @param idWajibBayar the idWajibBayar to set
     */
    public void setIdWajibBayar(String idWajibBayar) {
        this.idWajibBayar = idWajibBayar;
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
     * @return the tanggalDokumen
     */
    public String getTanggalDokumen() {
        return tanggalDokumen;
    }

    /**
     * @param tanggalDokumen the tanggalDokumen to set
     */
    public void setTanggalDokumen(String tanggalDokumen) {
        this.tanggalDokumen = tanggalDokumen;
    }

    /**
     * @return the kodeKPBC
     */
    public String getKodeKPBC() {
        return kodeKPBC;
    }

    /**
     * @param kodeKPBC the kodeKPBC to set
     */
    public void setKodeKPBC(String kodeKPBC) {
        this.kodeKPBC = kodeKPBC;
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
     * @return the unitEselon1
     */
    public String getUnitEselon1() {
        return unitEselon1;
    }

    /**
     * @param unitEselon1 the unitEselon1 to set
     */
    public void setUnitEselon1(String unitEselon1) {
        this.unitEselon1 = unitEselon1;
    }

    /**
     * @return the kodeSatker
     */
    public String getKodeSatker() {
        return kodeSatker;
    }

    /**
     * @param kodeSatker the kodeSatker to set
     */
    public void setKodeSatker(String kodeSatker) {
        this.kodeSatker = kodeSatker;
    }

    @Override
    public String toString() {
        return "ETaxBillingInfo{" + "type=" + type + ", billingId=" + billingId + ", wpName=" + wpName + ", npwp=" + npwp + ", wpAddress=" + wpAddress + ", akun=" + akun + ", kodeJenisSetoran=" + kodeJenisSetoran + ", masaPajak=" + masaPajak + ", nomorSK=" + nomorSK + ", nop=" + nop + ", idWajibBayar=" + idWajibBayar + ", jenisDokumen=" + jenisDokumen + ", nomorDokumen=" + nomorDokumen + ", tanggalDokumen=" + tanggalDokumen + ", kodeKPBC=" + kodeKPBC + ", kl=" + kl + ", unitEselon1=" + unitEselon1 + ", kodeSatker=" + kodeSatker + '}';
    }
    
}
