package bdsm.scheduler.model;

import java.util.Date;
import bdsm.model.BaseModel;

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

    public int getCodCustID() {
        return codCustID;
    }

    public void setCodCustID(int codCustID) {
        this.codCustID = codCustID;
    }

    public String getBdiNIK() {
        return bdiNIK;
    }

    public void setBdiNIK(String bdiNIK) {
        this.bdiNIK = bdiNIK;
    }

    public String getBdiFullName() {
        return bdiFullName;
    }

    public void setBdiFullName(String bdiFullName) {
        this.bdiFullName = bdiFullName;
    }

    public String getBdiDateOfBirth() {
        return bdiDateOfBirth;
    }

    public void setBdiDateOfBirth(String bdiDateOfBirth) {
        this.bdiDateOfBirth = bdiDateOfBirth;
    }

    public String getBdiPlaceOfBirth() {
        return bdiPlaceOfBirth;
    }

    public void setBdiPlaceOfBirth(String bdiPlaceOfBirth) {
        this.bdiPlaceOfBirth = bdiPlaceOfBirth;
    }

    public String getBdiMothName() {
        return bdiMothName;
    }

    public void setBdiMothName(String bdiMothName) {
        this.bdiMothName = bdiMothName;
    }

    public String getDukNIK() {
        return dukNIK;
    }

    public void setDukNIK(String dukNIK) {
        this.dukNIK = dukNIK;
    }

    public String getDukFullName() {
        return dukFullName;
    }

    public void setDukFullName(String dukFullName) {
        this.dukFullName = dukFullName;
    }

    public String getDukDateOfBirth() {
        return dukDateOfBirth;
    }

    public void setDukDateOfBirth(String dukDateOfBirth) {
        this.dukDateOfBirth = dukDateOfBirth;
    }

    public String getDukPlaceOfBirth() {
        return dukPlaceOfBirth;
    }

    public void setDukPlaceOfBirth(String dukPlaceOfBirth) {
        this.dukPlaceOfBirth = dukPlaceOfBirth;
    }

    public String getDukMothName() {
        return dukMothName;
    }

    public void setDukMothName(String dukMothName) {
        this.dukMothName = dukMothName;
    }

    public Date getDatApplied() {
        return datApplied;
    }

    public void setDatApplied(Date datApplied) {
        this.datApplied = datApplied;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusReason() {
        return statusReason;
    }

    public void setStatusReason(String statusReason) {
        this.statusReason = statusReason;
    }

    public String getNoBatch() {
        return noBatch;
    }

    public void setNoBatch(String noBatch) {
        this.noBatch = noBatch;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}