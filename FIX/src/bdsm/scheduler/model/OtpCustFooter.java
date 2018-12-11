/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

import bdsm.model.BaseModel;
import java.util.Date;

/**
 *
 * @author v00020800
 */
public class OtpCustFooter extends BaseModel {

    private String batchID;
    private String norecordpelunasan;
    private String norecordpencairan;
    private String typerecord;
    private String status;
    private Integer id;

    /**
     * @return the batchID
     */
    public String getBatchID() {
        return batchID;
    }

    /**
     * @param batchID the batchID to set
     */
    public void setBatchID(String batchID) {
        this.batchID = batchID;
    }


    /**
     * @return the typerecord
     */
    public String getTyperecord() {
        return typerecord;
    }

    /**
     * @param typerecord the typerecord to set
     */
    public void setTyperecord(String typerecord) {
        this.typerecord = typerecord;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the norecordpelunasan
     */
    public String getNorecordpelunasan() {
        return norecordpelunasan;
    }

    /**
     * @param norecordpelunasan the norecordpelunasan to set
     */
    public void setNorecordpelunasan(String norecordpelunasan) {
        this.norecordpelunasan = norecordpelunasan;
    }

    /**
     * @return the norecordpencairan
     */
    public String getNorecordpencairan() {
        return norecordpencairan;
    }

    /**
     * @param norecordpencairan the norecordpencairan to set
     */
    public void setNorecordpencairan(String norecordpencairan) {
        this.norecordpencairan = norecordpencairan;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

}
