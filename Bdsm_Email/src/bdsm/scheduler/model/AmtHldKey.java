/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

import java.io.Serializable;

/**
 *
 * @author v00018192
 */
@SuppressWarnings("serial")
public class AmtHldKey implements Serializable {
    private int recId;
    private String fileid;

    /**
     * @return the fileid
     */
    public String getFileid() {
        return fileid;
    }

    /**
     * @param fileid the fileid to set
     */
    public void setFileid(String fileid) {
        this.fileid = fileid;
    }

    /**
     * @return the recId
     */
    public int getRecId() {
        return recId;
    }

    /**
     * @param recId the recId to set
     */
    public void setRecId(int recId) {
        this.recId = recId;
    }
}
