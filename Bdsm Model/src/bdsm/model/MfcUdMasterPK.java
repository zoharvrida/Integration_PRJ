/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;

import java.io.Serializable;

/**
 *
 * @author user
 */
public class MfcUdMasterPK implements Serializable {
    private Integer noCif;
    private String noUd;

    public MfcUdMasterPK(Integer noCif, String noUd) {
        this.noCif = noCif;
        this.noUd = noUd;
    }

    @Override
    public String toString() {
        return "MfcUdMasterPK{" + "noCif=" + noCif + ", noUd=" + noUd + '}';
    }

    public MfcUdMasterPK() {
    }
    
    /**
    * @return the noCif
    */
    
    public Integer getNoCif() {
        return noCif;
    }

    /**
     * @param noCif the noCif to set
     */
    public void setNoCif(Integer noCif) {
        this.noCif = noCif;
    }
    
    /**
    * @return the noUd
    */
    public String getNoUd() {
        return noUd;
    }

    /**
     * @param noUd the noUd to set
     */
    public void setNoUd(String noUd) {
        this.noUd = noUd;
    }
}
