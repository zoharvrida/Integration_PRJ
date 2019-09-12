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
public class MfcNoBookPK implements Serializable {
    private String noAcct;
    private String refTxn;
    private String typMsg;
    private String typTrx;

    public MfcNoBookPK() {
    }

    public MfcNoBookPK(String noAcct, String refTxn, String typMsg) {
        this.noAcct = noAcct;
        this.refTxn = refTxn;
        this.typMsg = typMsg;
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
    
    /**
    * @return the typMsg
    */
    public String getTypMsg() {
        return typMsg;
    }

    /**
     * @param typMsg the typMsg to set
     */
    public void setTypMsg(String typMsg) {
        this.typMsg = typMsg;
    }

    /**
     * @return the typTrx
     */
    public String getTypTrx() {
        return typTrx;
    }

    /**
     * @param typTrx the typTrx to set
     */
    public void setTypTrx(String typTrx) {
        this.typTrx = typTrx;
    }
}