/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author bdsm
 */
public class BaseModel implements Serializable {
    private String idCreatedBy;
    private String idCreatedSpv;
    private String idUpdatedBy;
    private String idUpdatedSpv;
    private Timestamp dtmCreated;
    private Timestamp dtmCreatedSpv;
    private Timestamp dtmUpdated;
    private Timestamp dtmUpdatedSpv;
    private String idMaintainedBy;
    private String idMaintainedSpv;

    /**
     * @return the idCreatedBy
     */
    public String getIdCreatedBy() {
        return idCreatedBy;
    }

    /**
     * @param idCreatedBy the idCreatedBy to set
     */
    public void setIdCreatedBy(String idCreatedBy) {
        this.idCreatedBy = idCreatedBy;
    }

    /**
     * @return the idCreatedSpv
     */
    public String getIdCreatedSpv() {
        return idCreatedSpv;
    }

    /**
     * @param idCreatedSpv the idCreatedSpv to set
     */
    public void setIdCreatedSpv(String idCreatedSpv) {
        this.idCreatedSpv = idCreatedSpv;
    }

    /**
     * @return the idUpdatedBy
     */
    public String getIdUpdatedBy() {
        return idUpdatedBy;
    }

    /**
     * @param idUpdatedBy the idUpdatedBy to set
     */
    public void setIdUpdatedBy(String idUpdatedBy) {
        this.idUpdatedBy = idUpdatedBy;
    }

    /**
     * @return the idUpdatedSpv
     */
    public String getIdUpdatedSpv() {
        return idUpdatedSpv;
    }

    /**
     * @param idUpdatedSpv the idUpdatedSpv to set
     */
    public void setIdUpdatedSpv(String idUpdatedSpv) {
        this.idUpdatedSpv = idUpdatedSpv;
    }

    /**
     * @return the dtmCreated
     */
    public Timestamp getDtmCreated() {
        return dtmCreated;
    }

    /**
     * @param dtmCreated the dtmCreated to set
     */
    public void setDtmCreated(Timestamp dtmCreated) {
        this.dtmCreated = dtmCreated;
    }

    /**
     * @return the dtmCreatedSpv
     */
    public Timestamp getDtmCreatedSpv() {
        return dtmCreatedSpv;
    }

    /**
     * @param dtmCreatedSpv the dtmCreatedSpv to set
     */
    public void setDtmCreatedSpv(Timestamp dtmCreatedSpv) {
        this.dtmCreatedSpv = dtmCreatedSpv;
    }

    /**
     * @return the dtmUpdated
     */
    public Timestamp getDtmUpdated() {
        return dtmUpdated;
    }

    /**
     * @param dtmUpdated the dtmUpdated to set
     */
    public void setDtmUpdated(Timestamp dtmUpdated) {
        this.dtmUpdated = dtmUpdated;
    }

    /**
     * @return the dtmUpdatedSpv
     */
    public Timestamp getDtmUpdatedSpv() {
        return dtmUpdatedSpv;
    }

    /**
     * @param dtmUpdatedSpv the dtmUpdatedSpv to set
     */
    public void setDtmUpdatedSpv(Timestamp dtmUpdatedSpv) {
        this.dtmUpdatedSpv = dtmUpdatedSpv;
    }

    /**
     * @return the idMaintainedBy
     */
    public String getIdMaintainedBy() {
        return idMaintainedBy;
    }

    /**
     * @param idMaintainedBy the idMaintainedBy to set
     */
    public void setIdMaintainedBy(String idMaintainedBy) {
        this.idMaintainedBy = idMaintainedBy;
    }

    /**
     * @return the idMaintainedSpv
     */
    public String getIdMaintainedSpv() {
        return idMaintainedSpv;
    }

    /**
     * @param idMaintainedSpv the idMaintainedSpv to set
     */
    public void setIdMaintainedSpv(String idMaintainedSpv) {
        this.idMaintainedSpv = idMaintainedSpv;
    }
}
