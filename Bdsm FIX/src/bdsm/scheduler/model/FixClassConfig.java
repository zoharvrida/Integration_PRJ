/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

import bdsm.model.BaseModel;

/**
 *
 * @author USER
 */
public class FixClassConfig extends BaseModel{
    private String Source;
    private String Classname;
    private String Param;
    private String FixType;
    private int IdScheduler;
    private int id;

    /**
     * @return the Source
     */
    public String getSource() {
        return Source;
    }

    /**
     * @param Source the Source to set
     */
    public void setSource(String Source) {
        this.Source = Source;
    }

    /**
     * @return the Classname
     */
    public String getClassname() {
        return Classname;
    }

    /**
     * @param Classname the Classname to set
     */
    public void setClassname(String Classname) {
        this.Classname = Classname;
    }

    /**
     * @return the Param
     */
    public String getParam() {
        return Param;
    }

    /**
     * @param Param the Param to set
     */
    public void setParam(String Param) {
        this.Param = Param;
    }

    /**
     * @return the FixType
     */
    public String getFixType() {
        return FixType;
    }

    /**
     * @param FixType the FixType to set
     */
    public void setFixType(String FixType) {
        this.FixType = FixType;
    }

    /**
     * @return the IdScheduler
     */
    public int getIdScheduler() {
        return IdScheduler;
    }

    /**
     * @param IdScheduler the IdScheduler to set
     */
    public void setIdScheduler(int IdScheduler) {
        this.IdScheduler = IdScheduler;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

}
