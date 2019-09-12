/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

import java.io.Serializable;

/**
 *
 * @author SDM
 */

public class FixqxtractWatcherPK implements Serializable {

    private Integer qid;
    private Integer typeProcess;
    private String namProcess;

    public FixqxtractWatcherPK() {
    }

    public FixqxtractWatcherPK(Integer qid, Integer typeProcess, String namProcess) {
        this.qid = qid;
        this.typeProcess = typeProcess;
        this.namProcess = namProcess;
    }

    public Integer getQid() {
        return qid;
    }

    public void setQid(Integer qid) {
        this.qid = qid;
    }

    public Integer getTypeProcess() {
        return typeProcess;
    }

    public void setTypeProcess(Integer typeProcess) {
        this.typeProcess = typeProcess;
    }

    public String getNamProcess() {
        return namProcess;
    }

    public void setNamProcess(String namProcess) {
        this.namProcess = namProcess;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (qid != null ? qid.hashCode() : 0);
        hash += (typeProcess != null ? typeProcess.hashCode() : 0);
        hash += (namProcess != null ? namProcess.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FixqxtractWatcherPK)) {
            return false;
        }
        FixqxtractWatcherPK other = (FixqxtractWatcherPK) object;
        if ((this.qid == null && other.qid != null) || (this.qid != null && !this.qid.equals(other.qid))) {
            return false;
        }
        if ((this.typeProcess == null && other.typeProcess != null) || (this.typeProcess != null && !this.typeProcess.equals(other.typeProcess))) {
            return false;
        }
        if ((this.namProcess == null && other.namProcess != null) || (this.namProcess != null && !this.namProcess.equals(other.namProcess))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bdsm.scheduler.model.FixqxtractWatcherPK[ qid=" + qid + ", typeProcess=" + typeProcess + ", namProcess=" + namProcess + " ]";
    }
    
}
