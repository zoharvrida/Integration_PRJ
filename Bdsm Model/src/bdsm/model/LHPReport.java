/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author v00024535
 */
public class LHPReport implements Serializable {

    private String tanggalBuku;
    private String bankCode;
    private String bankName;
    private String accountNo;
    private String mataUang;
    private String recordCount;
    private BigDecimal totalAmount;
    private String noSakti;
    private BigDecimal totalAmountIDR;

    /**
     * @return the tanggalBuku
     */
    public String getTanggalBuku() {
        return tanggalBuku;
    }

    /**
     * @param tanggalBuku the tanggalBuku to set
     */
    public void setTanggalBuku(String tanggalBuku) {
        this.tanggalBuku = tanggalBuku;
    }

    /**
     * @return the bankCode
     */
    public String getBankCode() {
        return bankCode;
    }

    /**
     * @param bankCode the bankCode to set
     */
    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    /**
     * @return the bankName
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * @param bankName the bankName to set
     */
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    /**
     * @return the accountNo
     */
    public String getAccountNo() {
        return accountNo;
    }

    /**
     * @param accountNo the accountNo to set
     */
    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
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
     * @return the recordCount
     */
    public String getRecordCount() {
        return recordCount;
    }

    /**
     * @param recordCount the recordCount to set
     */
    public void setRecordCount(String recordCount) {
        this.recordCount = recordCount;
    }

    /**
     * @return the totalAmount
     */
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    /**
     * @param totalAmount the totalAmount to set
     */
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    /**
     * @return the noSakti
     */
    public String getNoSakti() {
        return noSakti;
    }

    /**
     * @param noSakti the noSakti to set
     */
    public void setNoSakti(String noSakti) {
        this.noSakti = noSakti;
    }

    /**
     * @return the totalAmountIDR
     */
    public BigDecimal getTotalAmountIDR() {
        return totalAmountIDR;
    }

    /**
     * @param totalAmountIDR the totalAmountIDR to set
     */
    public void setTotalAmountIDR(BigDecimal totalAmountIDR) {
        this.totalAmountIDR = totalAmountIDR;
    }
}
