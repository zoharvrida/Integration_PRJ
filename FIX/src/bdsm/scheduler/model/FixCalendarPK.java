/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

import java.io.Serializable;

/**
 *
 * @author v00019722
 */
public class FixCalendarPK implements Serializable{
    private String Module_Name;
    private Integer YEAR;
    private Integer Month;
    private String flgType;


    /**
     * @return the Module_Name
     */
    public String getModule_Name() {
        return Module_Name;
    }

    /**
     * @param Module_Name the Module_Name to set
     */
    public void setModule_Name(String Module_Name) {
        this.Module_Name = Module_Name;
    }

    /**
     * @return the flgType
     */
    public String getFlgType() {
        return flgType;
    }

    /**
     * @param flgType the flgType to set
     */
    public void setFlgType(String flgType) {
        this.flgType = flgType;
    }

    /**
     * @return the YEAR
     */
    public Integer getYEAR() {
        return YEAR;
    }

    /**
     * @param YEAR the YEAR to set
     */
    public void setYEAR(Integer YEAR) {
        this.YEAR = YEAR;
    }

    /**
     * @return the Month
     */
    public Integer getMonth() {
        return Month;
    }

    /**
     * @param Month the Month to set
     */
    public void setMonth(Integer Month) {
        this.Month = Month;
    }
}
