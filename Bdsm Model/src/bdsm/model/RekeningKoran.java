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
public class RekeningKoran implements Serializable {

    private String senderName;
    private String receiverName;
    private String creationDateTime;
    private String messageIdentifier;
    private String messageType;
    private String bankCode;
    private String bankAccountNo;
    private Date bankStatementDate;
    private String currency;
    private BigDecimal beginningBalance;
    private BigDecimal endingBalance;
    private String lineNumber;
    private String bankTransactionCode;
    private String debitCredit;
    private String bankReferenceNumber;
    private Date transactionDate;
    private Date valueDate;
    private BigDecimal originalAmount;
    private String versionText;

    /**
     * @return the senderName
     */
    public String getSenderName() {
        return senderName;
    }

    /**
     * @param senderName the senderName to set
     */
    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    /**
     * @return the receiverName
     */
    public String getReceiverName() {
        return receiverName;
    }

    /**
     * @param receiverName the receiverName to set
     */
    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    /**
     * @return the messageIdentifier
     */
    public String getMessageIdentifier() {
        return messageIdentifier;
    }

    /**
     * @param messageIdentifier the messageIdentifier to set
     */
    public void setMessageIdentifier(String messageIdentifier) {
        this.messageIdentifier = messageIdentifier;
    }

    /**
     * @return the messageType
     */
    public String getMessageType() {
        return messageType;
    }

    /**
     * @param messageType the messageType to set
     */
    public void setMessageType(String messageType) {
        this.messageType = messageType;
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
     * @return the bankAccountNo
     */
    public String getBankAccountNo() {
        return bankAccountNo;
    }

    /**
     * @param bankAccountNo the bankAccountNo to set
     */
    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }

    /**
     * @return the bankStatementDate
     */
    public Date getBankStatementDate() {
        return bankStatementDate;
    }

    /**
     * @param bankStatementDate the bankStatementDate to set
     */
    public void setBankStatementDate(Date bankStatementDate) {
        this.bankStatementDate = bankStatementDate;
    }

    /**
     * @return the currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * @param currency the currency to set
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * @return the beginningBalance
     */
    public BigDecimal getBeginningBalance() {
        return beginningBalance;
    }

    /**
     * @param beginningBalance the beginningBalance to set
     */
    public void setBeginningBalance(BigDecimal beginningBalance) {
        this.beginningBalance = beginningBalance;
    }

    /**
     * @return the endingBalance
     */
    public BigDecimal getEndingBalance() {
        return endingBalance;
    }

    /**
     * @param endingBalance the endingBalance to set
     */
    public void setEndingBalance(BigDecimal endingBalance) {
        this.endingBalance = endingBalance;
    }

    /**
     * @return the lineNumber
     */
    public String getLineNumber() {
        return lineNumber;
    }

    /**
     * @param lineNumber the lineNumber to set
     */
    public void setLineNumber(String lineNumber) {
        this.lineNumber = lineNumber;
    }

    /**
     * @return the bankTransactionCode
     */
    public String getBankTransactionCode() {
        return bankTransactionCode;
    }

    /**
     * @param bankTransactionCode the bankTransactionCode to set
     */
    public void setBankTransactionCode(String bankTransactionCode) {
        this.bankTransactionCode = bankTransactionCode;
    }

    /**
     * @return the debitCredit
     */
    public String getDebitCredit() {
        return debitCredit;
    }

    /**
     * @param debitCredit the debitCredit to set
     */
    public void setDebitCredit(String debitCredit) {
        this.debitCredit = debitCredit;
    }

    /**
     * @return the bankReferenceNumber
     */
    public String getBankReferenceNumber() {
        return bankReferenceNumber;
    }

    /**
     * @param bankReferenceNumber the bankReferenceNumber to set
     */
    public void setBankReferenceNumber(String bankReferenceNumber) {
        this.bankReferenceNumber = bankReferenceNumber;
    }

    /**
     * @return the transactionDate
     */
    public Date getTransactionDate() {
        return transactionDate;
    }

    /**
     * @param transactionDate the transactionDate to set
     */
    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    /**
     * @return the valueDate
     */
    public Date getValueDate() {
        return valueDate;
    }

    /**
     * @param valueDate the valueDate to set
     */
    public void setValueDate(Date valueDate) {
        this.valueDate = valueDate;
    }

    /**
     * @return the originalAmount
     */
    public BigDecimal getOriginalAmount() {
        return originalAmount;
    }

    /**
     * @param originalAmount the originalAmount to set
     */
    public void setOriginalAmount(BigDecimal originalAmount) {
        this.originalAmount = originalAmount;
    }

    /**
     * @return the creationDateTime
     */
    public String getCreationDateTime() {
        return creationDateTime;
    }

    /**
     * @param creationDateTime the creationDateTime to set
     */
    public void setCreationDateTime(String creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    /**
     * @return the versionText
     */
    public String getVersionText() {
        return versionText;
    }

    /**
     * @param versionText the versionText to set
     */
    public void setVersionText(String versionText) {
        this.versionText = versionText;
    }
}
