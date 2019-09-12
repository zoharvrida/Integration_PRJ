/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

import bdsm.model.BaseModel;

/**
 *
 * @author v00019722
 */
public class FixSchedulerParam extends BaseModel{
  private FixSchedulerPK pkID;
  private String Module_Name;
  private Integer Days;
  private Integer Month;
  private Integer YEAR;
  private Integer Hour;
  private Integer Minutes;
  private Integer Second;
  private String BatchPrefix;  
  private String module_pattern;
  private String flg_mnt_status;
  private String ActiveDays;
  private String flgWorkday;
  private String flgParam;
  private String flgHolidaySkip;
  private String datPattern;
  private String flgCalendar;
  private String flgType;

    
    /**
     * @return the Days
     */
    public Integer getDays() {
        return Days;
    }

    /**
     * @param Days the Days to set
     */
    public void setDays(Integer Days) {
        this.Days = Days;
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
     * @return the Hour
     */
    public Integer getHour() {
        return Hour;
    }

    /**
     * @param Hour the Hour to set
     */
    public void setHour(Integer Hour) {
        this.Hour = Hour;
    }

    /**
     * @return the Minutes
     */
    public Integer getMinutes() {
        return Minutes;
    }

    /**
     * @param Minutes the Minutes to set
     */
    public void setMinutes(Integer Minutes) {
        this.Minutes = Minutes;
    }

    /**
     * @return the Second
     */
    public Integer getSecond() {
        return Second;
    }

    /**
     * @param Second the Second to set
     */
    public void setSecond(Integer Second) {
        this.Second = Second;
    }

    /**
     * @return the BatchPrefix
     */
    public String getBatchPrefix() {
        return BatchPrefix;
    }

    /**
     * @param BatchPrefix the BatchPrefix to set
     */
    public void setBatchPrefix(String BatchPrefix) {
        this.BatchPrefix = BatchPrefix;
    }

    /**
     * @return the module_pattern
     */
    public String getModule_pattern() {
        return module_pattern;
    }

    /**
     * @param module_pattern the module_pattern to set
     */
    public void setModule_pattern(String module_pattern) {
        this.module_pattern = module_pattern;
    }

    /**
     * @return the flg_mnt_status
     */
    public String getFlg_mnt_status() {
        return flg_mnt_status;
    }

    /**
     * @param flg_mnt_status the flg_mnt_status to set
     */
    public void setFlg_mnt_status(String flg_mnt_status) {
        this.flg_mnt_status = flg_mnt_status;
    }

    /**
     * @return the pkID
     */
    public FixSchedulerPK getPkID() {
        return pkID;
    }

    /**
     * @param pkID the pkID to set
     */
    public void setPkID(FixSchedulerPK pkID) {
        this.pkID = pkID;
    }

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
     * @return the ActiveDays
     */
    public String getActiveDays() {
        return ActiveDays;
    }

    /**
     * @param ActiveDays the ActiveDays to set
     */
    public void setActiveDays(String ActiveDays) {
        this.ActiveDays = ActiveDays;
    }

    /**
     * @return the flgWorkday
     */
    public String getFlgWorkday() {
        return flgWorkday;
    }

    /**
     * @param flgWorkday the flgWorkday to set
     */
    public void setFlgWorkday(String flgWorkday) {
        this.flgWorkday = flgWorkday;
    }

    /**
     * @return the flgParam
     */
    public String getFlgParam() {
        return flgParam;
    }

    /**
     * @param flgParam the flgParam to set
     */
    public void setFlgParam(String flgParam) {
        this.flgParam = flgParam;
    }

    /**
     * @return the flgHolidaySkip
     */
    public String getFlgHolidaySkip() {
        return flgHolidaySkip;
    }

    /**
     * @param flgHolidaySkip the flgHolidaySkip to set
     */
    public void setFlgHolidaySkip(String flgHolidaySkip) {
        this.flgHolidaySkip = flgHolidaySkip;
    }

    /**
     * @return the datPattern
     */
    public String getDatPattern() {
        return datPattern;
    }

    /**
     * @param datPattern the datPattern to set
     */
    public void setDatPattern(String datPattern) {
        this.datPattern = datPattern;
    }

    /**
     * @return the flgCalendar
     */
    public String getFlgCalendar() {
        return flgCalendar;
    }

    /**
     * @param flgCalendar the flgCalendar to set
     */
    public void setFlgCalendar(String flgCalendar) {
        this.flgCalendar = flgCalendar;
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
}
