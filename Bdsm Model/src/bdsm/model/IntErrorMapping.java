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
public class IntErrorMapping implements Serializable {
    private Integer errNumber;
    private String profileName;
    private String errCode;

    public IntErrorMapping(Integer errNumber, String profileName) {
        this.errNumber = errNumber;
        this.profileName = profileName;
    }


    /**
     * @return the errNumber
     */
    public Integer getErrNumber() {
        return errNumber;
    }

    /**
     * @param errNumber the errNumber to set
     */
    public void setErrNumber(Integer errNumber) {
        this.errNumber = errNumber;
    }

    /**
     * @return the profileName
     */
    public String getProfileName() {
        return profileName;
    }

    /**
     * @param profileName the profileName to set
     */
    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    /**
     * @return the errCode
     */
    public String getErrCode() {
        return errCode;
    }

    /**
     * @param errCode the errCode to set
     */
    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }
}
