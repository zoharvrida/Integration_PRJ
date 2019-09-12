package bdsm.scheduler.model;

import java.util.Date;
import bdsm.model.BaseModel;

/**
 * 
 * @author bdsm
 */
@SuppressWarnings("serial")
public class UpdateCIFTmp extends BaseModel {
    private int codCustID;   
    private String bdiNIK;
    private String bdiFullName;
    private String bdiDateOfBirth;
    private String bdiPlaceOfBirth;
    private String bdiMothName;    
    private String dukNIK;
    private String dukFullName;
    private String dukDateOfBirth;
    private String dukPlaceOfBirth;
    private String dukMothName;    
    private Date datApplied;
    private String status;
    private String statusReason;
    private String noBatch;
    private Integer id;

    /**
     * 
     * @return
     */
    public int getCodCustID() {
        return codCustID;
    }

    /**
     * 
     * @param codCustID
     */
    public void setCodCustID(int codCustID) {
        this.codCustID = codCustID;
    }

    /**
     * 
     * @return
     */
    public String getBdiNIK() {
        return bdiNIK;
    }

    /**
     * 
     * @param bdiNIK
     */
    public void setBdiNIK(String bdiNIK) {
        this.bdiNIK = bdiNIK;
    }

    /**
     * 
     * @return
     */
    public String getBdiFullName() {
        return bdiFullName;
    }

    /**
     * 
     * @param bdiFullName
     */
    public void setBdiFullName(String bdiFullName) {
        this.bdiFullName = bdiFullName;
    }

    /**
     * 
     * @return
     */
    public String getBdiDateOfBirth() {
        return bdiDateOfBirth;
    }

    /**
     * 
     * @param bdiDateOfBirth
     */
    public void setBdiDateOfBirth(String bdiDateOfBirth) {
        this.bdiDateOfBirth = bdiDateOfBirth;
    }

    /**
     * 
     * @return
     */
    public String getBdiPlaceOfBirth() {
        return bdiPlaceOfBirth;
    }

    /**
     * 
     * @param bdiPlaceOfBirth
     */
    public void setBdiPlaceOfBirth(String bdiPlaceOfBirth) {
        this.bdiPlaceOfBirth = bdiPlaceOfBirth;
    }

    /**
     * 
     * @return
     */
    public String getBdiMothName() {
        return bdiMothName;
    }

    /**
     * 
     * @param bdiMothName
     */
    public void setBdiMothName(String bdiMothName) {
        this.bdiMothName = bdiMothName;
    }

    /**
     * 
     * @return
     */
    public String getDukNIK() {
        return dukNIK;
    }

    /**
     * 
     * @param dukNIK
     */
    public void setDukNIK(String dukNIK) {
        this.dukNIK = dukNIK;
    }

    /**
     * 
     * @return
     */
    public String getDukFullName() {
        return dukFullName;
    }

    /**
     * 
     * @param dukFullName
     */
    public void setDukFullName(String dukFullName) {
        this.dukFullName = dukFullName;
    }

    /**
     * 
     * @return
     */
    public String getDukDateOfBirth() {
        return dukDateOfBirth;
    }

    /**
     * 
     * @param dukDateOfBirth
     */
    public void setDukDateOfBirth(String dukDateOfBirth) {
        this.dukDateOfBirth = dukDateOfBirth;
    }

    /**
     * 
     * @return
     */
    public String getDukPlaceOfBirth() {
        return dukPlaceOfBirth;
    }

    /**
     * 
     * @param dukPlaceOfBirth
     */
    public void setDukPlaceOfBirth(String dukPlaceOfBirth) {
        this.dukPlaceOfBirth = dukPlaceOfBirth;
    }

    /**
     * 
     * @return
     */
    public String getDukMothName() {
        return dukMothName;
    }

    /**
     * 
     * @param dukMothName
     */
    public void setDukMothName(String dukMothName) {
        this.dukMothName = dukMothName;
    }

    /**
     * 
     * @return
     */
    public Date getDatApplied() {
        return datApplied;
    }

    /**
     * 
     * @param datApplied
     */
    public void setDatApplied(Date datApplied) {
        this.datApplied = datApplied;
    }

    /**
     * 
     * @return
     */
    public String getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 
     * @return
     */
    public String getStatusReason() {
        return statusReason;
    }

    /**
     * 
     * @param statusReason
     */
    public void setStatusReason(String statusReason) {
        this.statusReason = statusReason;
    }

    /**
     * 
     * @return
     */
    public String getNoBatch() {
        return noBatch;
    }

    /**
     * 
     * @param noBatch
     */
    public void setNoBatch(String noBatch) {
        this.noBatch = noBatch;
    }

    /**
     * 
     * @return
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }
}