/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

import bdsm.model.BaseModel;

/**
 *
 * @author NCBS
 */
public class FixFolderWatcher extends BaseModel{

    private FixFolderWatcherPK compositeId;
    private String flgStat;

    /**
     * @return the compositeId
     */
    public FixFolderWatcherPK getCompositeId() {
        return compositeId;
    }

    /**
     * @param compositeId the compositeId to set
     */
    public void setCompositeId(FixFolderWatcherPK compositeId) {
        this.compositeId = compositeId;
    }

    /**
     * @return the flgStat
     */
    public String getFlgStat() {
        return flgStat;
    }

    /**
     * @param flgStat the flgStat to set
     */
    public void setFlgStat(String flgStat) {
        this.flgStat = flgStat;
    }
}
