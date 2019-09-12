/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;

/**
 *
 * @author bdsm
 */
public class ErrorProfileDictionary {
    private String errorDesc;
    private String errorProfile;
    private Integer errorNumb;

    /**
     * @return the errorDesc
     */
    public String getErrorDesc() {
        return errorDesc;
    }

    /**
     * @param errorDesc the errorDesc to set
     */
    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }

    /**
     * @return the errorProfile
     */
    public String getErrorProfile() {
        return errorProfile;
    }

    /**
     * @param errorProfile the errorProfile to set
     */
    public void setErrorProfile(String errorProfile) {
        this.errorProfile = errorProfile;
    }

    /**
     * @return the errorNumb
     */
    public Integer getErrorNumb() {
        return errorNumb;
    }

    /**
     * @param errorNumb the errorNumb to set
     */
    public void setErrorNumb(Integer errorNumb) {
        this.errorNumb = errorNumb;
    }
}
