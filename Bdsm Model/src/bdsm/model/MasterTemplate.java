/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;
/**
 *
 * @author user
 */
public class MasterTemplate extends BaseModel {
    private String idTemplate;
    private String namTemplate;

    /**
    * @return the idTemplate
    */
    public String getIdTemplate() {
        return idTemplate;
    }

    /**
     * @param idTemplate the idTemplate to set
     */
    public void setIdTemplate(String idTemplate) {
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
