/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;

/**
 *
 * @author Jaka
 */
public class ETaxCurrency extends BaseModel {
    
    private String id;
    private String currCode;
    private String currName;
    private String currCodeNcbs;
    private int ordering;

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the currCode
     */
    public String getCurrCode() {
        return currCode;
    }

    /**
     * @param currCode the currCode to set
     */
    public void setCurrCode(String currCode) {
        this.currCode = currCode;
    }

    /**
     * @return the currName
     */
    public String getCurrName() {
        return currName;
    }

    /**
     * @param currName the currName to set
     */
    public void setCurrName(String currName) {
        this.currName = currName;
    }

    /**
     * @return the currCodeNcbs
     */
    public String getCurrCodeNcbs() {
        return currCodeNcbs;
    }

    /**
     * @param currCodeNcbs the currCodeNcbs to set
     */
    public void setCurrCodeNcbs(String currCodeNcbs) {
        this.currCodeNcbs = currCodeNcbs;
    }

    public int getOrdering() {
        return ordering;
    }

    public void setOrdering(int ordering) {
        this.ordering = ordering;
    }

    @Override
    public String toString() {
        return "ETaxCurrency{" + "id=" + id + ", currCode=" + currCode + ", currName=" + currName + ", currCodeNcbs=" + currCodeNcbs + '}';
    }
    
    
}
