/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

import java.io.Serializable;

/**
 *
 * @author NCBS
 */
public class FixFolderWatcherPK implements Serializable {

    private String folderPath;
    private String prependFileBackup;

    /**
     * @return the folderPath
     */
    public String getFolderPath() {
        return folderPath;
    }

    /**
     * @param folderPath the folderPath to set
     */
    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }

    /**
     * @return the prependFileBkp
     */
    public String getPrependFileBackup() {
        return prependFileBackup;
    }

    /**
     * @param prependFileBkp the prependFileBkp to set
     */
    public void setPrependFileBackup(String prependFileBackup) {
        this.prependFileBackup = prependFileBackup;
    }
}
