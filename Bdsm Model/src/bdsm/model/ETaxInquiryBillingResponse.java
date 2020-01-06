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
@SuppressWarnings("serial")
public class ETaxInquiryBillingResponse extends BaseModel implements Serializable {

    private String refNo;
    private String branchCode;
    private String costCenter;
    private String userId;
    private String djpTS;
    private String ccy;
    private String amount;
    private String responseCode;
    private String responseDesc;
    private String responseTime;
    private String responseTimeString;
    private String cashIdType;
    private String cashBranchGL;
    private String cashCustomerType;
    private String cashCustomerAddress;
    private String cashCustomerName;
    private String cashCustomerPhone;
    private String cashIdNo;
    private String creditAccountName;
    private String debitAccountName;
    private String debitAccountNo;
    private String description;
    private String exchangeRate = "1";
    private String glAccountName;
    private String glAccountNo;
    private String nominalLCE;
    private String creditNominalLCE;
    private String nomorKPPN;
    private String trxCurrency;
    // credit account
    private String kppnAccountNo;
    private String kppnAccountCcyCode;
    private String kppnAccountCcyName;
    private String kppnAccountName;

    /**
     * @return the refNo
     */
    public String getRefNo() {
        return refNo;
    }

    /**
     * @param refNo the refNo to set
     */
    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    /**
     * @return the branchCode
     */
    public String getBranchCode() {
        return branchCode;
    }

    /**
     * @param branchCode the branchCode to set
     */
    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
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
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return the djpTS
     */
    public String getDjpTS() {
        return djpTS;
    }

    /**
     * @param djpTS the djpTS to set
     */
    public void setDjpTS(String djpTS) {
        this.djpTS = djpTS;
    }

    /**
     * @return the ccy
     */
    public String getCcy() {
        return ccy;
    }

    /**
     * @param ccy the ccy to set
     */
    public void setCcy(String ccy) {
        this.ccy = ccy;
    }

    /**
     * @return the amount
     */
    public String getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }

    /**
     * @return the responseCode
     */
    public String getResponseCode() {
        return responseCode;
    }

    /**
     * @param responseCode the responseCode to set
     */
    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    /**
     * @return the responseDesc
     */
    public String getResponseDesc() {
        return responseDesc;
    }

    /**
     * @param responseDesc the responseDesc to set
     */
    public void setResponseDesc(String responseDesc) {
        this.responseDesc = responseDesc;
    }

    /**
     * @return the responseTime
     */
    public String getResponseTime() {
        return responseTime;
    }

    /**
     * @param responseTime the responseTime to set
     */
    public void setResponseTime(String responseTime) {
        this.responseTime = responseTime;
    }

    /**
     * @return the responseTimeString
     */
    public String getResponseTimeString() {
        return responseTimeString;
    }

    /**
     * @param responseTimeString the responseTimeString to set
     */
    public void setResponseTimeString(String responseTimeString) {
        this.responseTimeString = responseTimeString;
    }

    /**
     * @return the cashIdType
     */
    public String getCashIdType() {
        return cashIdType;
    }

    /**
     * @param cashIdType the cashIdType to set
     */
    public void setCashIdType(String cashIdType) {
        this.cashIdType = cashIdType;
    }

    /**
     * @return the cashBranchGL
     */
    public String getCashBranchGL() {
        return cashBranchGL;
    }

    /**
     * @param cashBranchGL the cashBranchGL to set
     */
    public void setCashBranchGL(String cashBranchGL) {
        this.cashBranchGL = cashBranchGL;
    }

    /**
     * @return the cashCustomerType
     */
    public String getCashCustomerType() {
        return cashCustomerType;
    }

    /**
     * @param cashCustomerType the cashCustomerType to set
     */
    public void setCashCustomerType(String cashCustomerType) {
        this.cashCustomerType = cashCustomerType;
    }

    /**
     * @return the cashCustomerAddress
     */
    public String getCashCustomerAddress() {
        return cashCustomerAddress;
    }

    /**
     * @param cashCustomerAddress the cashCustomerAddress to set
     */
    public void setCashCustomerAddress(String cashCustomerAddress) {
        this.cashCustomerAddress = cashCustomerAddress;
    }

    /**
     * @return the cashCustomerName
     */
    public String getCashCustomerName() {
        return cashCustomerName;
    }

    /**
     * @param cashCustomerName the cashCustomerName to set
     */
    public void setCashCustomerName(String cashCustomerName) {
        this.cashCustomerName = cashCustomerName;
    }

    /**
     * @return the cashCustomerPhone
     */
    public String getCashCustomerPhone() {
        return cashCustomerPhone;
    }

    /**
     * @param cashCustomerPhone the cashCustomerPhone to set
     */
    public void setCashCustomerPhone(String cashCustomerPhone) {
        this.cashCustomerPhone = cashCustomerPhone;
    }

    /**
     * @return the cashIdNo
     */
    public String getCashIdNo() {
        return cashIdNo;
    }

    /**
     * @param cashIdNo the cashIdNo to set
     */
    public void setCashIdNo(String cashIdNo) {
        this.cashIdNo = cashIdNo;
    }

    /**
     * @return the creditAccountName
     */
    public String getCreditAccountName() {
        return creditAccountName;
    }

    /**
     * @param creditAccountName the creditAccountName to set
     */
    public void setCreditAccountName(String creditAccountName) {
        this.creditAccountName = creditAccountName;
    }

    /**
     * @return the debitAccountName
     */
    public String getDebitAccountName() {
        return debitAccountName;
    }

    /**
     * @param debitAccountName the debitAccountName to set
     */
    public void setDebitAccountName(String debitAccountName) {
        this.debitAccountName = debitAccountName;
    }

    /**
     * @return the debitAccountNo
     */
    public String getDebitAccountNo() {
        return debitAccountNo;
    }

    /**
     * @param debitAccountNo the debitAccountNo to set
     */
    public void setDebitAccountNo(String debitAccountNo) {
        this.debitAccountNo = debitAccountNo;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the exchangeRate
     */
    public String getExchangeRate() {
        return exchangeRate;
    }

    /**
     * @param exchangeRate the exchangeRate to set
     */
    public void setExchangeRate(String exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    /**
     * @return the glAccountName
     */
    public String getGlAccountName() {
        return glAccountName;
    }

    /**
     * @param glAccountName the glAccountName to set
     */
    public void setGlAccountName(String glAccountName) {
        this.glAccountName = glAccountName;
    }

    /**
     * @return the glAccountNo
     */
    public String getGlAccountNo() {
        return glAccountNo;
    }

    /**
     * @param glAccountNo the glAccountNo to set
     */
    public void setGlAccountNo(String glAccountNo) {
        this.glAccountNo = glAccountNo;
    }

    /**
     * @return the nominalLCE
     */
    public String getNominalLCE() {
        return nominalLCE;
    }

    /**
     * @param nominalLCE the nominalLCE to set
     */
    public void setNominalLCE(String nominalLCE) {
        this.nominalLCE = nominalLCE;
    }

    /**
     * @return the creditNominalLCE
     */
    public String getCreditNominalLCE() {
        return creditNominalLCE;
    }

    /**
     * @param creditNominalLCE the creditNominalLCE to set
     */
    public void setCreditNominalLCE(String creditNominalLCE) {
        this.creditNominalLCE = creditNominalLCE;
    }

    /**
     * @return the nomorKPPN
     */
    public String getNomorKPPN() {
        return nomorKPPN;
    }

    /**
     * @param nomorKPPN the nomorKPPN to set
     */
    public void setNomorKPPN(String nomorKPPN) {
        this.nomorKPPN = nomorKPPN;
    }

    /**
     * @return the trxCurrency
     */
    public String getTrxCurrency() {
        return trxCurrency;
    }

    /**
     * @param trxCurrency the trxCurrency to set
     */
    public void setTrxCurrency(String trxCurrency) {
        this.trxCurrency = trxCurrency;
    }

    /**
     * @return the kppnAccountNo
     */
    public String getKppnAccountNo() {
        return kppnAccountNo;
    }

    /**
     * @param kppnAccountNo the kppnAccountNo to set
     */
    public void setKppnAccountNo(String kppnAccountNo) {
        this.kppnAccountNo = kppnAccountNo;
    }

    /**
     * @return the kppnAccountCcyCode
     */
    public String getKppnAccountCcyCode() {
        return kppnAccountCcyCode;
    }

    /**
     * @param kppnAccountCcyCode the kppnAccountCcyCode to set
     */
    public void setKppnAccountCcyCode(String kppnAccountCcyCode) {
        this.kppnAccountCcyCode = kppnAccountCcyCode;
    }

    /**
     * @return the kppnAccountCcyName
     */
    public String getKppnAccountCcyName() {
        return kppnAccountCcyName;
    }

    /**
     * @param kppnAccountCcyName the kppnAccountCcyName to set
     */
    public void setKppnAccountCcyName(String kppnAccountCcyName) {
        this.kppnAccountCcyName = kppnAccountCcyName;
    }

    /**
     * @return the kppnAccountName
     */
    public String getKppnAccountName() {
        return kppnAccountName;
    }

    /**
     * @param kppnAccountName the kppnAccountName to set
     */
    public void setKppnAccountName(String kppnAccountName) {
        this.kppnAccountName = kppnAccountName;
    }
}
