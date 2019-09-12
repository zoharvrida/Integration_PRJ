/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;

import java.io.Serializable;

/**
 *
 * @author v00013493
 */
public class MfcTxnDetailsPK implements Serializable {
    private String noAcct;
    private String refTxn;

    public MfcTxnDetailsPK() {
    }

    public MfcTxnDetailsPK(String noAcct, String refTxn) {
        this.noAcct = noAcct;
        this.refTxn = refTxn;
    }
    
    
    /**
    * @return the noAcct
    */
    public String getNoAcct() {
        return noAcct;
    }

    /**
     * @param noAcct the noAcct to set
     */
    public void setNoAcct(String noAcct) {
        this.noAcct = noAcct;
    }

    /**
    * @return the refTxn
    */
    public String getRefTxn() {
        return refTxn;
    }

    /**
     * @param refTxn the refTxn to set
     */
    public void setRefTxn(String refTxn) {
        this.refTxn = refTxn;
    }
}
