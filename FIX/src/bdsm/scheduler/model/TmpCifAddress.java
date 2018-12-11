/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

import bdsm.model.BaseModel;
/**
 *
 * @author v00020841
 */
public class TmpCifAddress extends BaseModel{
    
    private TmpCifAddressPK compositeId;
    private String address1 ;
    private String address2;
    private String address3;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private String phone1Mobile;
    private String phone2;

    /**
     * 
     * @return
     */
    public TmpCifAddressPK getCompositeId() {
        return compositeId;
    }

    /**
     * 
     * @param compositeId
     */
    public void setCompositeId(TmpCifAddressPK compositeId) {
        this.compositeId = compositeId;
    }

    /**
     * 
     * @return
     */
    public String getAddress1() {
        return address1;
    }

    /**
     * 
     * @param address1
     */
    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    /**
     * 
     * @return
     */
    public String getAddress2() {
        return address2;
    }

    /**
     * 
     * @param address2
     */
    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    /**
     * 
     * @return
     */
    public String getAddress3() {
        return address3;
    }

    /**
     * 
     * @param address3
     */
    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    /**
     * 
     * @return
     */
    public String getCity() {
        return city;
    }

    /**
     * 
     * @param city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * 
     * @return
     */
    public String getState() {
        return state;
    }

    /**
     * 
     * @param state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * 
     * @return
     */
    public String getCountry() {
        return country;
    }

    /**
     * 
     * @param country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * 
     * @return
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * 
     * @param postalCode
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * 
     * @return
     */
    public String getPhone1Mobile() {
        return phone1Mobile;
    }

    /**
     * 
     * @param phone1Mobile
     */
    public void setPhone1Mobile(String phone1Mobile) {
        this.phone1Mobile = phone1Mobile;
    }

    /**
     * 
     * @return
     */
    public String getPhone2() {
        return phone2;
    }

    /**
     * 
     * @param phone2
     */
    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }
    
}
