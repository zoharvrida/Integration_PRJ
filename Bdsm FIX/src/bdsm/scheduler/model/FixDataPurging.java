/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

import bdsm.model.BaseModel;
import java.util.Date;

/**
 *
 * @author NCBS
 */
public class FixDataPurging extends BaseModel {

    private int idpurging;
    private String dataloc;
    private String filepattern;
    private Long fileSizeLessOrEqual;
    private String flgstat;
    private String flgtyp;
    private Date dtmeff;
    private Double retperiod;
    private Date lastsuccesspurging;
    private Date nextpurgingplan;
    private String histTable;

    /**
     * @return the idpurging
     */
    public int getIdpurging() {
        return idpurging;
    }

    /**
     * @param idpurging the idpurging to set
     */
    public void setIdpurging(int idpurging) {
        this.idpurging = idpurging;
    }

    /**
     * @return the dataloc
     */
    public String getDataloc() {
        return dataloc;
    }

    /**
     * @param dataloc the dataloc to set
     */
    public void setDataloc(String dataloc) {
        this.dataloc = dataloc;
    }

    /**
     * @return the filepattern
     */
    public String getFilepattern() {
        return filepattern;
    }

    /**
     * @param filepattern the filepattern to set
     */
    public void setFilepattern(String filepattern) {
        this.filepattern = filepattern;
    }
    
    /**
     * @return the fileSizeLessOrEqual
     */
    public Long getFileSizeLessOrEqual() {
    	return fileSizeLessOrEqual;
    }
    
    /**
     * @param fileSizeLessOrEqual the fileSizeLessOrEqual to set
     */
    public void setFileSizeLessOrEqual(Long fileSizeLessOrEqual) {
    	this.fileSizeLessOrEqual = fileSizeLessOrEqual;
    }

    /**
     * @return the flgstat
     */
    public String getFlgstat() {
        return flgstat;
    }

    /**
     * @param flgstat the flgstat to set
     */
    public void setFlgstat(String flgstat) {
        this.flgstat = flgstat;
    }

    /**
     * @return the flgtyp
     */
    public String getFlgtyp() {
        return flgtyp;
    }

    /**
     * @param flgtyp the flgtyp to set
     */
    public void setFlgtyp(String flgtyp) {
        this.flgtyp = flgtyp;
    }

    /**
     * @return the dtmeff
     */
    public Date getDtmeff() {
        return dtmeff;
    }

    /**
     * @param dtmeff the dtmeff to set
     */
    public void setDtmeff(Date dtmeff) {
        this.dtmeff = dtmeff;
    }

    /**
     * @return the retperiod
     */
    public Double getRetperiod() {
        return retperiod;
    }

    /**
     * @param retperiod the retperiod to set
     */
    public void setRetperiod(Double retperiod) {
        this.retperiod = retperiod;
    }

    /**
     * @return the lastsuccesspurging
     */
    public Date getLastsuccesspurging() {
        return lastsuccesspurging;
    }

    /**
     * @param lastsuccesspurging the lastsuccesspurging to set
     */
    public void setLastsuccesspurging(Date lastsuccesspurging) {
        this.lastsuccesspurging = lastsuccesspurging;
    }

    /**
     * @return the nextpurgingplan
     */
    public Date getNextpurgingplan() {
        return nextpurgingplan;
    }

    /**
     * @param nextpurgingplan the nextpurgingplan to set
     */
    public void setNextpurgingplan(Date nextpurgingplan) {
        this.nextpurgingplan = nextpurgingplan;
    }

    /**
     * @return the histTable
     */
    public String getHistTable() {
        return histTable;
    }

    /**
     * @param histTable the histTable to set
     */
    public void setHistTable(String histTable) {
        this.histTable = histTable;
    }
}
