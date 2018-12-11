/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

import bdsm.model.BaseModel;

/**
 *
 * @author v00019722
 */
public class BaBankCldr extends BaseModel {
    private FixCalendarPK pkID;
    private String WorkingDays;

  
    /**
     * @return the WorkingDays
     */
    public String getWorkingDays() {
        return WorkingDays;
    }

    /**
     * @param WorkingDays the WorkingDays to set
     */
    public void setWorkingDays(String WorkingDays) {
        this.WorkingDays = WorkingDays;
    }
    /**
     * @return the pkID
     */
    public FixCalendarPK getPkID() {
        return pkID;
    }

    /**
     * @param pkID the pkID to set
     */
    public void setPkID(FixCalendarPK pkID) {
        this.pkID = pkID;
    }
}
