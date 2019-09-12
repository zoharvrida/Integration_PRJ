package bdsm.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author v00017250
 */
@SuppressWarnings("serial")
public class ETaxInquiryBillingResp extends BaseModel {

    private String refNo;
    private String branchCode;
    private String costCenter;
    private String userId;
    private String djpTS;
    private ETaxBillingInfo billingInfo;
    private String ccy;
    private BigDecimal amount;
    private String responseCode;
    private String responseDesc;
    private Date responseTime;
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
    private double exchangeRate = 1;
    private String glAccountName;
    private String glAccountNo;
    private BigDecimal nominalLCE;
    private BigDecimal creditNominalLCE;
    private String nomorKPPN;
    private String trxCurrency;
    
    // credit account
    private String kppnAccountNo;
    private Long kppnAccountCcyCode;
    private String kppnAccountCcyName;
    private String kppnAccountName;
    
    protected static String stringMax(String input, int max) {
        if ((input != null) && (input.length() > max)) {
            return input.substring(0, max);
        } else {
            return input;
        }
    }

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
     * @return the billingInfo
     */
    public ETaxBillingInfo getBillingInfo() {
        return billingInfo;
    }

    /**
     * @param billingInfo the billingInfo to set
     */
    public void setBillingInfo(ETaxBillingInfo billingInfo) {
        this.billingInfo = billingInfo;
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
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(BigDecimal amount) {
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

    public Date getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Date responseTime) {
        this.responseTime = responseTime;
    }

    public String getResponseTimeString() {
        return responseTimeString;
    }

    public void setResponseTimeString(String responseTimeString) {
        this.responseTimeString = responseTimeString;
    }

    public String getCashIdType() {
        return cashIdType;
    }

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

    public String getCashCustomerType() {
        return cashCustomerType;
    }

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
    public double getExchangeRate() {
        return exchangeRate;
    }

    /**
     * @param exchangeRate the exchangeRate to set
     */
    public void setExchangeRate(double exchangeRate) {
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
    public BigDecimal getNominalLCE() {
        return nominalLCE;
    }

    /**
     * @param nominalLCE the nominalLCE to set
     */
    public void setNominalLCE(BigDecimal nominalLCE) {
        this.nominalLCE = nominalLCE;
    }

    public BigDecimal getCreditNominalLCE() {
        return creditNominalLCE;
    }

    public void setCreditNominalLCE(BigDecimal creditNominalLCE) {
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
    public Long getKppnAccountCcyCode() {
        return kppnAccountCcyCode;
    }

    /**
     * @param kppnAccountCcyCode the kppnAccountCcyCode to set
     */
    public void setKppnAccountCcyCode(Long kppnAccountCcyCode) {
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

    @Override
    public String toString() {
        return "ETaxInquiryBillingResp{" + "refNo=" + refNo + ", branchCode=" + branchCode + ", costCenter=" + costCenter + ", userId=" + userId + ", djpTS=" + djpTS + ", billingInfo=" + billingInfo + ", ccy=" + ccy + ", amount=" + amount + ", responseCode=" + responseCode + ", responseDesc=" + responseDesc + '}';
    }

}
