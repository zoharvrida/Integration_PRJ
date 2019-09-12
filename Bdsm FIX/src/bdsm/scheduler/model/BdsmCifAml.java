/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

import bdsm.model.BaseModel;
import java.util.Date;

/**
 *
 * @author v00020841
 */
public class BdsmCifAml extends BaseModel{

    private String partyKey;
    private String partyType;
    private String partyGivenName;
    private String partySurname;
    private String partyDatBirth;
    private String partyBirthCountry;
    private String partyCountry;
    private String partyIds;
    private Date datInsert;

    /**
     * 
     * @return
     */
    public String getPartyKey() {
        return partyKey;
    }

    /**
     * 
     * @param partyKey
     */
    public void setPartyKey(String partyKey) {
        this.partyKey = partyKey;
    }

    /**
     * 
     * @return
     */
    public String getPartyType() {
        return partyType;
    }

    /**
     * 
     * @param partyType
     */
    public void setPartyType(String partyType) {
        this.partyType = partyType;
    }

    /**
     * 
     * @return
     */
    public String getPartyGivenName() {
        return partyGivenName;
    }

    /**
     * 
     * @param partyGivenName
     */
    public void setPartyGivenName(String partyGivenName) {
        this.partyGivenName = partyGivenName;
    }

    /**
     * 
     * @return
     */
    public String getPartySurname() {
        return partySurname;
    }

    /**
     * 
     * @param partySurname
     */
    public void setPartySurname(String partySurname) {
        this.partySurname = partySurname;
    }

    /**
     * 
     * @return
     */
    public String getPartyDatBirth() {
        return partyDatBirth;
    }

    /**
     * 
     * @param partyDatBirth
     */
    public void setPartyDatBirth(String partyDatBirth) {
        this.partyDatBirth = partyDatBirth;
    }

    /**
     * 
     * @return
     */
    public String getPartyBirthCountry() {
        return partyBirthCountry;
    }

    /**
     * 
     * @param partyBirthCountry
     */
    public void setPartyBirthCountry(String partyBirthCountry) {
        this.partyBirthCountry = partyBirthCountry;
    }

    /**
     * 
     * @return
     */
    public String getPartyCountry() {
        return partyCountry;
    }

    /**
     * 
     * @param partyCountry
     */
    public void setPartyCountry(String partyCountry) {
        this.partyCountry = partyCountry;
    }

    /**
     * 
     * @return
     */
    public String getPartyIds() {
        return partyIds;
    }

    /**
     * 
     * @param partyIds
     */
    public void setPartyIds(String partyIds) {
        this.partyIds = partyIds;
    }

    /**
     * 
     * @return
     */
    public Date getDatInsert() {
        return datInsert;
    }

    /**
     * 
     * @param datInsert
     */
    public void setDatInsert(Date datInsert) {
        this.datInsert = datInsert;
    }
    
    /**
     * 
     * @param delimiter
     * @return
     */
    public String rearrangeString(String delimiter){
        StringBuilder strCif = new StringBuilder();
        strCif.append(this.partyKey.trim()).append(delimiter)
                .append(this.partyType.trim()).append(delimiter)
                .append(this.partyGivenName.trim()).append(delimiter)
              .append(this.partySurname == null ? "":this.partySurname.trim()).append(delimiter)
              .append(this.partyDatBirth == null ? "":this.partyDatBirth.trim()).append(delimiter)
              .append(this.partyBirthCountry == null ? "":this.partyBirthCountry.trim()).append(delimiter)
              .append(this.partyCountry == null ? "":this.partyCountry.trim()).append(delimiter)
              .append(this.partyIds == null ? "":this.partyIds.trim());
        
        return strCif.toString();
    }
    
    
}
