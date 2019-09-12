/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model.Com;

import bdsm.model.BaseModel;
import java.sql.Timestamp;

/**
 *
 * @author SDM
 */
public class ComBrnVendorXrefHist extends BaseModel {
    private String idBrn;
    private Timestamp dtmLog;
    private String vendorId;
    private String flgDefault;

    /**
     * @return the idBrn
     */
    public String getIdBrn() {
        return idBrn;
    }

    /**
     * @param idBrn the idBrn to set
     */
    public void setIdBrn(String idBrn) {
        this.idBrn = idBrn;
    }

    /**
     * @return the dtmLog
     */
    public Timestamp getDtmLog() {
        return dtmLog;
    }

    /**
     * @param dtmLog the dtmLog to set
     */
    public void setDtmLog(Timestamp dtmLog) {
        this.dtmLog = dtmLog;
    }

    /**
     * @return the vendorId
     */
    public String getVendorId() {
        return vendorId;
    }

    /**
     * @param vendorId the vendorId to set
     */
    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    /**
     * @return the flgDefault
     */
    public String getFlgDefault() {
        return flgDefault;
    }

    /**
     * @param flgDefault the flgDefault to set
     */
    public void setFlgDefault(String flgDefault) {
        this.flgDefault = flgDefault;
    }
}
