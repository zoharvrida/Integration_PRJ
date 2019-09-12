/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;

import java.sql.Date;

/**
 *
 * @author 00030663
 */
public class FcrBaBankMast {
    private FcrBaBankMastPK compositeId;
    private String namBank;
    private String namBankShrt;
    private Date datLastProcess;
    private Date datProcess;
    private Date datNextProcess;

    /**
     * @return the namBank
     */
    public String getNamBank() {
        return namBank;
    }

    /**
     * @param namBank the namBank to set
     */
    public void setNamBank(String namBank) {
        this.namBank = namBank;
    }

    /**
     * @return the namBankShrt
     */
    public String getNamBankShrt() {
        return namBankShrt;
    }

    /**
     * @param namBankShrt the namBankShrt to set
     */
    public void setNamBankShrt(String namBankShrt) {
        this.namBankShrt = namBankShrt;
    }

    /**
     * @return the datLastProcess
     */
    public Date getDatLastProcess() {
        return datLastProcess;
    }

    /**
     * @param datLastProcess the datLastProcess to set
     */
    public void setDatLastProcess(Date datLastProcess) {
        this.datLastProcess = datLastProcess;
    }

    /**
     * @return the datProcess
     */
    public Date getDatProcess() {
        return datProcess;
    }

    /**
     * @param datProcess the datProcess to set
     */
    public void setDatProcess(Date datProcess) {
        this.datProcess = datProcess;
    }

    /**
     * @return the datNextProcess
     */
    public Date getDatNextProcess() {
        return datNextProcess;
    }

    /**
     * @param datNextProcess the datNextProcess to set
     */
    public void setDatNextProcess(Date datNextProcess) {
        this.datNextProcess = datNextProcess;
    }

    /**
     * @return the compositeId
     */
    public FcrBaBankMastPK getCompositeId() {
        return compositeId;
    }

    /**
     * @param compositeId the compositeId to set
     */
    public void setCompositeId(FcrBaBankMastPK compositeId) {
        this.compositeId = compositeId;
    }
}
