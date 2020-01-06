/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;

/**
 *
 * @author v00024535
 */
public class MasterService extends BaseModel {

    private Integer idScheduler;
    private String nameScheduler;
    private String type;
    private String userName;
    private String password;
    private String url;
    private String keyAuth;
    private String typeKey;
    private String encryptType;
    private String flag;

    @Override
    public String toString() {
        return "MasterService{" + "idScheduler=" + idScheduler + ", nameScheduler=" + nameScheduler + ", type=" + type + ", userName=" + userName + ", password=" + password + ", url=" + url + ", keyAuth=" + keyAuth + ", typeKey=" + typeKey + ", encryptType=" + encryptType + ", flag=" + flag + '}';
    }

    
    
    /**
     * @return the idScheduler
     */
    public Integer getIdScheduler() {
        return idScheduler;
    }

    /**
     * @param idScheduler the idScheduler to set
     */
    public void setIdScheduler(Integer idScheduler) {
        this.idScheduler = idScheduler;
    }

    /**
     * @return the nameScheduler
     */
    public String getNameScheduler() {
        return nameScheduler;
    }

    /**
     * @param nameScheduler the nameScheduler to set
     */
    public void setNameScheduler(String nameScheduler) {
        this.nameScheduler = nameScheduler;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
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
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the keyAuth
     */
    public String getKeyAuth() {
        return keyAuth;
    }

    /**
     * @param keyAuth the keyAuth to set
     */
    public void setKeyAuth(String keyAuth) {
        this.keyAuth = keyAuth;
    }

    /**
     * @return the typeKey
     */
    public String getTypeKey() {
        return typeKey;
    }

    /**
     * @param typeKey the typeKey to set
     */
    public void setTypeKey(String typeKey) {
        this.typeKey = typeKey;
    }

    /**
     * @return the encryptType
     */
    public String getEncryptType() {
        return encryptType;
    }

    /**
     * @param encryptType the encryptType to set
     */
    public void setEncryptType(String encryptType) {
        this.encryptType = encryptType;
    }

    /**
     * @return the flag
     */
    public String getFlag() {
        return flag;
    }

    /**
     * @param flag the flag to set
     */
    public void setFlag(String flag) {
        this.flag = flag;
    }
}
