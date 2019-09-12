/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;

/**
 *
 * @author Roby
 */
public class ADUser{
    private String displayName;
    private String sAMAccountName;

    public ADUser(){
    }
    /**
     * @return the displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * @param displayName the displayName to set
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * @return the sAMAccountName
     */
    public String getSAMAccountName() {
        return sAMAccountName;
    }

    /**
     * @param sAMAccountName the sAMAccountName to set
     */
    public void setSAMAccountName(String sAMAccountName) {
        this.sAMAccountName = sAMAccountName;
    }

}
