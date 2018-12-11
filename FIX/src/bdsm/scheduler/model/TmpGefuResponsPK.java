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
public class TmpGefuResponsPK implements Serializable{
    private String ocpackage;
    private String ocfunction;
    private String batchNo;
    
    
    /**
     * 
     */
    public TmpGefuResponsPK() {}
    
    /**
     * 
     * @param ocpackage
     * @param ocfunction
     * @param batchNo
     */
    public TmpGefuResponsPK(String ocpackage, String ocfunction, String batchNo) {
    	this.ocpackage = ocpackage;
    	this.ocfunction = ocfunction;
    	this.batchNo = batchNo;
    }
    

    /**
     * @return the ocpackage
     */
    public String getOcpackage() {
        return ocpackage;
    }

    /**
     * @param ocpackage the ocpackage to set
     */
    public void setOcpackage(String ocpackage) {
        this.ocpackage = ocpackage;
    }

    /**
     * @return the ocfunction
     */
    public String getOcfunction() {
        return ocfunction;
    }

    /**
     * @param ocfunction the ocfunction to set
     */
    public void setOcfunction(String ocfunction) {
        this.ocfunction = ocfunction;
    }

    /**
     * @return the batchNo
     */
    public String getBatchNo() {
        return batchNo;
    }

    /**
     * @param batchNo the batchNo to set
     */
    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }
}
