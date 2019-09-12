/*
 * 2015-11-06: Add ktpMaxQuery
 */
package bdsm.model;

/**
 *
 * @author bdsm
 */
public class EktpUser extends BaseModel {
    private String  idUser;
    private String  idTemplate;
    private String  namUser;
    private Integer     cdBranch;
    private String  isAd="Y";
	
    private String ktpUser;
    private String ktpPwd;
    private String nikUser;
    private String ipUser;
    
    //*** Max query per user - Start
    private Integer ktpMaxQuery;
    //*** Max query per user - End
    
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

    /**
     * @return the nikUser
     */
    public String getNikUser() {
        return nikUser;
    }

    /**
     * @param nikUser the nikUser to set
     */
    public void setNikUser(String nikUser) {
        this.nikUser = nikUser;
    }

    /**
     * @return the ipUser
     */
    public String getIpUser() {
        return ipUser;
    }

    /**
     * @param ipUser the ipUser to set
     */
    public void setIpUser(String ipUser) {
        this.ipUser = ipUser;
    }

    //*** Max query per user - Start
    /**
     * @return the ktpMaxQuery
     */
    public Integer getKtpMaxQuery() {
        return ktpMaxQuery;
    }

    /**
     * @param ktpMaxQuery the ktpMaxQuery to set
     */
    public void setKtpMaxQuery(Integer ktpMaxQuery) {
        this.ktpMaxQuery = ktpMaxQuery;
    }
    //*** Max query per user - End

    @Override
    public String toString() {
        return "EktpUser{" + "idUser=" + idUser + ", idTemplate=" + idTemplate + ", namUser=" + namUser + ", cdBranch=" + cdBranch + ", isAd=" + isAd + ", ktpUser=" + ktpUser + ", nikUser=" + nikUser + ", ipUser=" + ipUser + ", ktpMaxQuery=" + ktpMaxQuery + '}';
    }
    
}
