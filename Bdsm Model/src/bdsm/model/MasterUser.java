/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model; 

import java.util.Date;

/**
 *
 * @author bdsm
 */
public class MasterUser extends BaseModel {
    private String  idUser;
    private String  idTemplate;
    private String  namUser;
    private Integer     cdBranch;
    private String  isAd="Y";
    private String  isFcrMap;
    private String  password;
    private String  idUserFcr;
    private Date datLastSignOn;
    private String ktpUser;
    private String ktpPwd;
    
    /**
    * @return the idUser
    */
    public String getIdUser() {
        return idUser;
    }

    /**
     * @param idUser the idUser to set
     */
    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    /**
    * @return the namUser
    */
    public String getNamUser() {
        return namUser;
    }

    /**
     * @param namUser the namUser to set
     */
    public void setNamUser(String namUser) {
        this.namUser = namUser;
    }

    /**
    * @return the cdBranch
    */
    public Integer getCdBranch() {
        return cdBranch;
    }

    /**
     * @param cdBranch the cdBranch to set
     */
    public void setCdBranch(Integer cdBranch) {
        this.cdBranch = cdBranch;
    }

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
    * @return the idUserFcr
    */
    public String getIdUserFcr() {
        return idUserFcr;
    }

    /**
     * @param idUserFcr the idUserFcr to set
     */
    public void setIdUserFcr(String idUserFcr) {
        this.idUserFcr = idUserFcr;
    }

    /**
     * @return the isAd
     */
    public String getIsAd() {
        return isAd;
    }

    /**
     * @param isAd the isAd to set
     */
    public void setIsAd(String isAd) {
        this.isAd = isAd;
    }

    /**
     * @return the isFcrMap
     */
    public String getIsFcrMap() {
        return isFcrMap;
    }

    /**
     * @param isFcrMap the isFcrMap to set
     */
    public void setIsFcrMap(String isFcrMap) {
        this.isFcrMap = isFcrMap;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the datLastSignOn
     */
    public Date getDatLastSignOn() {
        return datLastSignOn;
    }

    /**
     * @param datLastSignOn the datLastSignOn to set
     */
    public void setDatLastSignOn(Date datLastSignOn) {
        this.datLastSignOn = datLastSignOn;
    }

    /**
     * @return the ktpUser
     */
    public String getKtpUser() {
        return ktpUser;
    }

    /**
     * @param ktpUser the ktpUser to set
     */
    public void setKtpUser(String ktpUser) {
        this.ktpUser = ktpUser;
    }

    /**
     * @return the ktpPwd
     */
    public String getKtpPwd() {
        return ktpPwd;
    }

    /**
     * @param ktpPwd the ktpPwd to set
     */
    public void setKtpPwd(String ktpPwd) {
        this.ktpPwd = ktpPwd;
    }
}
