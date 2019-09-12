/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;

import java.io.Serializable;

/**
 *
 * @author bdsm
 */
public class FixSchedulerPK implements Serializable{
    private Integer idTemplate;
    private Integer idScheduler;
    private String namScheduler;

    /**
     * @return the idTemplate
     */
    public Integer getIdTemplate() {
        return idTemplate;
    }

    /**
     * @param idTemplate the idTemplate to set
     */
    public void setIdTemplate(Integer idTemplate) {
        this.idTemplate = idTemplate;
    }

    /**
     * @return the idScheduler
     */
    public Integer getIdScheduler() {
        return idScheduler;
    }

    /**
     * @param idScheduler the idScheduler to set
     */
    public void setIdScheduler(Integer idScheduler) {
        this.idScheduler = idScheduler;
    }

    /**
     * @return the namScheduler
     */
    public String getNamScheduler() {
        return namScheduler;
    }

    /**
     * @param namScheduler the namScheduler to set
     */
    public void setNamScheduler(String namScheduler) {
        this.namScheduler = namScheduler;
    }

}
