/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;

import java.math.BigDecimal ;
import java.sql.Date;
/**
 *
 * @author user
 */
public class SaveFcyManInput extends BaseModel {
	private String cifNo;
	private String loginUserId;
	private String spvUserId;
	private String udNo;
	private String udCcy;
	private Date txnDate;
	private String txnCcy;
	private BigDecimal txnAmount;
	private BigDecimal lcyAmount;
	private BigDecimal usdIdrRate;
	private BigDecimal usdTxnAmount;
	private String description;

    /**
    * @return the cifNo
    */
    public String getCifNo() {
        return cifNo;
    }

    /**
     * @param cifNo the cifNo to set
     */
    public void setCifNo(String cifNo) {
        this.cifNo = cifNo;
    }

    /**
    * @return the loginUserId
    */
    public String getLoginUserId() {
        return loginUserId;
    }

    /**
     * @param loginUserId the loginUserId to set
     */
    public void setLoginUserId(String loginUserId) {
        this.loginUserId = loginUserId;
    }

    /**
    * @return the spvUserId
    */
    public String getSpvUserId() {
        return spvUserId;
    }

    /**
     * @param spvUserId the spvUserId to set
     */
    public void setSpvUserId(String spvUserId) {
        this.spvUserId = spvUserId;
    }

    /**
    * @return the udNo
    */
    public String getUdNo() {
        return udNo;
    }

    /**
     * @param udNo the udNo to set
     */
    public void setUdNo(String udNo) {
        this.udNo = udNo;
    }

    /**
    * @return the udCcy
    */
    public String getUdCcy() {
        return udCcy;
    }

    /**
     * @param udCcy the udCcy to set
     */
    public void setUdCcy(String udCcy) {
        this.udCcy = udCcy;
    }

    /**
    * @return the txnDate
    */
    public Date getTxnDate() {
        return txnDate;
    }

    /**
     * @param txnDate the txnDate to set
     */
    public void setTxnDate(Date txnDate) {
        this.txnDate = txnDate;
    }

    /**
    * @return the txnCcy
    */
    public String getTxnCcy() {
        return txnCcy;
    }

    /**
     * @param txnCcy the txnCcy to set
     */
    public void setTxnCcy(String txnCcy) {
        this.txnCcy = txnCcy;
    }

    /**
    * @return the txnAmount
    */
    public BigDecimal getTxnAmount() {
        return txnAmount;
    }

    /**
     * @param txnAmount the txnAmount to set
     */
    public void setTxnAmount(BigDecimal txnAmount) {
        this.txnAmount = txnAmount;
    }

    /**
    * @return the lcyAmount
    */
    public BigDecimal getLcyAmount() {
        return lcyAmount;
    }

    /**
     * @param lcyAmount the lcyAmount to set
     */
    public void setLcyAmount(BigDecimal lcyAmount) {
        this.lcyAmount = lcyAmount;
    }

    /**
    * @return the usdIdrRate
    */
    public BigDecimal getUsdIdrRate() {
        return usdIdrRate;
    }

    /**
     * @param usdIdrRate the usdIdrRate to set
     */
    public void setUsdIdrRate(BigDecimal usdIdrRate) {
        this.usdIdrRate = usdIdrRate;
    }

    /**
    * @return the usdTxnAmount
    */
    public BigDecimal getUsdTxnAmount() {
        return usdTxnAmount;
    }

    /**
     * @param usdTxnAmount the usdTxnAmount to set
     */
    public void setUsdTxnAmount(BigDecimal usdTxnAmount) {
        this.usdTxnAmount = usdTxnAmount;
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
}
