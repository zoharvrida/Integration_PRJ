/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

import bdsm.model.BaseModel;
import java.util.Date;

/**
 *
 * @author NCBS
 */
public class UcmTmpSrc extends BaseModel {

    private String CodCustId;
    private String Addr1;
    private String Addr2;
    private String Addr3;
    private String Mobile;
    private Date Dob;
    private String Npwp;
    private String NoBatch;
    private String FlgStatus;
    private String StatusReason;
    private String NamPermAdrCity;
    private String NamPermAdrState;
    private String namPermAdrCntry;
    private String TxtPermAdrZip;
    private int id;

    /**
     * @return the CodCustId
     */
    public String getCodCustId() {
        return CodCustId;
    }

    /**
     * @param CodCustId the CodCustId to set
     */
    public void setCodCustId(String CodCustId) {
        this.CodCustId = CodCustId;
    }

    /**
     * @return the Addr1
     */
    public String getAddr1() {
        return Addr1;
    }

    /**
     * @param Addr1 the Addr1 to set
     */
    public void setAddr1(String Addr1) {
        this.Addr1 = Addr1;
    }

    /**
     * @return the Addr2
     */
    public String getAddr2() {
        return Addr2;
    }

    /**
     * @param Addr2 the Addr2 to set
     */
    public void setAddr2(String Addr2) {
        this.Addr2 = Addr2;
    }

    /**
     * @return the Addr3
     */
    public String getAddr3() {
        return Addr3;
    }

    /**
     * @param Addr3 the Addr3 to set
     */
    public void setAddr3(String Addr3) {
        this.Addr3 = Addr3;
    }

    /**
     * @return the Mobile
     */
    public String getMobile() {
        return Mobile;
    }

    /**
     * @param Mobile the Mobile to set
     */
    public void setMobile(String Mobile) {
        this.Mobile = Mobile;
    }

    /**
     * @return the Dob
     */
    public Date getDob() {
        return Dob;
    }

    /**
     * @param Dob the Dob to set
     */
    public void setDob(Date Dob) {
        this.Dob = Dob;
    }

    /**
     * @return the Npwp
     */
    public String getNpwp() {
        return Npwp;
    }

    /**
     * @param Npwp the Npwp to set
     */
    public void setNpwp(String Npwp) {
        this.Npwp = Npwp;
    }

    /**
     * @return the NoBatch
     */
    public String getNoBatch() {
        return NoBatch;
    }

    /**
     * @param NoBatch the NoBatch to set
     */
    public void setNoBatch(String NoBatch) {
        this.NoBatch = NoBatch;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the FlgStatus
     */
    public String getFlgStatus() {
        return FlgStatus;
    }

    /**
     * @param FlgStatus the FlgStatus to set
     */
    public void setFlgStatus(String FlgStatus) {
        this.FlgStatus = FlgStatus;
    }

    /**
     * @return the StatusReason
     */
    public String getStatusReason() {
        return StatusReason;
    }

    /**
     * @param StatusReason the StatusReason to set
     */
    public void setStatusReason(String StatusReason) {
        this.StatusReason = StatusReason;
    }

    /**
     * @return the NamPermAdrCity
     */
    public String getNamPermAdrCity() {
        return NamPermAdrCity;
    }

    /**
     * @param NamPermAdrCity the NamPermAdrCity to set
     */
    public void setNamPermAdrCity(String NamPermAdrCity) {
        this.NamPermAdrCity = NamPermAdrCity;
    }

    /**
     * @return the NamPermAdrState
     */
    public String getNamPermAdrState() {
        return NamPermAdrState;
    }

    /**
     * @param NamPermAdrState the NamPermAdrState to set
     */
    public void setNamPermAdrState(String NamPermAdrState) {
        this.NamPermAdrState = NamPermAdrState;
    }

    /**
     * @return the namPermAdrCntry
     */
    public String getNamPermAdrCntry() {
        return namPermAdrCntry;
    }

    /**
     * @param namPermAdrCntry the namPermAdrCntry to set
     */
    public void setNamPermAdrCntry(String namPermAdrCntry) {
        this.namPermAdrCntry = namPermAdrCntry;
    }

    /**
     * @return the TxtPermAdrZip
     */
    public String getTxtPermAdrZip() {
        return TxtPermAdrZip;
    }

    /**
     * @param TxtPermAdrZip the TxtPermAdrZip to set
     */
    public void setTxtPermAdrZip(String TxtPermAdrZip) {
        this.TxtPermAdrZip = TxtPermAdrZip;
    }
}
