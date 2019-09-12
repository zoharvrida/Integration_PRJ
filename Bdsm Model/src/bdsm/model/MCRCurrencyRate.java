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
 * @author v00022309
 */
public class MCRCurrencyRate implements Serializable{
    private String currencyCode;
    private Date valueDate;
    private BigDecimal sellRate;
    private String makerID;
    private String checkerID;
    private String dtmLog;
    private String flag;

    /**
     * @return the currencyCode
     */
    public String getCurrencyCode() {
        return currencyCode;
    }

    /**
     * @param currencyCode the currencyCode to set
     */
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
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
     * @return the sellRate
     */
    public BigDecimal getSellRate() {
        return sellRate;
    }
    

    /**
     * @param sellRate the sellRate to set
     */
    public void setSellRate(BigDecimal sellRate) {
        this.sellRate = sellRate;
    }


    /**
     * @return the makerID
     */
    public String getMakerID() {
        return makerID;
    }

    /**
     * @param makerID the makerID to set
     */
    public void setMakerID(String makerID) {
        this.makerID = makerID;
    }

    /**
     * @return the checkerID
     */
    public String getCheckerID() {
        return checkerID;
    }

    /**
     * @param checkerID the checkerID to set
     */
    public void setCheckerID(String checkerID) {
        this.checkerID = checkerID;
    }

    /**
     * @return the dtmLog
     */
    public String getDtmLog() {
        return dtmLog;
    }

    /**
     * @param dtmLog the dtmLog to set
     */
    public void setDtmLog(String dtmLog) {
        this.dtmLog = dtmLog;
    }

    @Override
    public String toString() {
        return "MCRCurrencyRate{" + "currencyCode=" + currencyCode + ", valueDate=" + valueDate + ", sellRate=" + sellRate + ", makerID=" + makerID + ", checkerID=" + checkerID + ", dtmLog=" + dtmLog + '}';
    }

    /**
     * @return the flag
     */
    public String getFlag() {
        return flag;
    }

    /**
     * @param flag the flag to set
     */
    public void setFlag(String flag) {
        this.flag = flag;
    }
    
    

}
