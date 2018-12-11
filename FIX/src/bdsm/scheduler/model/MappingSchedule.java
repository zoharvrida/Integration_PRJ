/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

import bdsm.model.BaseModel;
import java.sql.Timestamp;

/**
 *
 * @author v00019722
 */
public class MappingSchedule extends BaseModel {
    private Timestamp DateFrom;
    private Timestamp DateTo;
    private String ModuleName;
    private String Status;

    /**
     * @return the DateFrom
     */
    public Timestamp getDateFrom() {
        return DateFrom;
    }

    /**
     * @param DateFrom the DateFrom to set
     */
    public void setDateFrom(Timestamp DateFrom) {
        this.DateFrom = DateFrom;
    }

    /**
     * @return the DateTo
     */
    public Timestamp getDateTo() {
        return DateTo;
    }

    /**
     * @param DateTo the DateTo to set
     */
    public void setDateTo(Timestamp DateTo) {
        this.DateTo = DateTo;
    }

    /**
     * @return the ModuleName
     */
    public String getModuleName() {
        return ModuleName;
    }

    /**
     * @param ModuleName the ModuleName to set
     */
    public void setModuleName(String ModuleName) {
        this.ModuleName = ModuleName;
    }

    /**
     * @return the Status
     */
    public String getStatus() {
        return Status;
    }

    /**
     * @param Status the Status to set
     */
    public void setStatus(String Status) {
        this.Status = Status;
    }
}
