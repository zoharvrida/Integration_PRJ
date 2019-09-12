/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

import java.io.Serializable;

/**
 *
 * @author bdsm
 */
public class FixTemplateMasterPK implements Serializable{
    private Integer idTemplate;
    private String namTemplate;

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
     * @return the namTemplate
     */
    public String getNamTemplate() {
        return namTemplate;
    }

    /**
     * @param namTemplate the namTemplate to set
     */
    public void setNamTemplate(String namTemplate) {
        this.namTemplate = namTemplate;
    }

}
