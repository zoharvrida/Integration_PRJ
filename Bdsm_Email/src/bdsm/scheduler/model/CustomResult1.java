/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

import java.io.Serializable;

/**
 *
 * @author bdsm
 */
public class CustomResult1 implements Serializable{

    private Integer idTemplate;
    private Integer idScheduler;
    private String namTemplate;
    private String javaClass;
    private String body;
    private String filePattern;
    private String fileExtension;
    private String flgEncrypt;
    private String modDecrypt;
    private String keyDecrypt;
    private String flgAuth;
    private String spvAuth;
    private String grpId;
    private String cdAccess;
    private String status;
    private String param2;
    private String requester;
    private String spv;
    private String itemIdLink;
    private String batchNo;
    private String newFilename;
    private String flgChecksum;
    private String flgStatus;

    /**
     * @return the idTemplate
     */
    public Integer getIdTemplate() {
        return idTemplate;
    }

    /**
     * @param idTemplate the idTemplate to set
     */
    public void setIdTemplate(Integer idTemplate) {
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

    /**
     * @return the filePattern
     */
    public String getFilePattern() {
        return filePattern;
    }

    /**
     * @param filePattern the filePattern to set
     */
    public void setFilePattern(String filePattern) {
        this.filePattern = filePattern;
    }

    /**
     * @return the fileExtension
     */
    public String getFileExtension() {
        return fileExtension;
    }

    /**
     * @param fileExtension the fileExtension to set
     */
    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    /**
     * @return the flgEncrypt
     */
    public String getFlgEncrypt() {
        return flgEncrypt;
    }

    /**
     * @param flgEncrypt the flgEncrypt to set
     */
    public void setFlgEncrypt(String flgEncrypt) {
        this.flgEncrypt = flgEncrypt;
    }

    /**
     * @return the modDecrypt
     */
    public String getModDecrypt() {
        return modDecrypt;
    }

    /**
     * @param modDecrypt the modDecrypt to set
     */
    public void setModDecrypt(String modDecrypt) {
        this.modDecrypt = modDecrypt;
    }

    /**
     * @return the keyDecrypt
     */
    public String getKeyDecrypt() {
        return keyDecrypt;
    }

    /**
     * @param keyDecrypt the keyDecrypt to set
     */
    public void setKeyDecrypt(String keyDecrypt) {
        this.keyDecrypt = keyDecrypt;
    }

    /**
     * @return the spvAuth
     */
    public String getSpvAuth() {
        return spvAuth;
    }

    /**
     * @param spvAuth the spvAuth to set
     */
    public void setSpvAuth(String spvAuth) {
        this.spvAuth = spvAuth;
    }

    /**
     * @return the grpId
     */
    public String getGrpId() {
        return grpId;
    }

    /**
     * @param grpId the grpId to set
     */
    public void setGrpId(String grpId) {
        this.grpId = grpId;
    }

    /**
     * @return the cdAccess
     */
    public String getCdAccess() {
        return cdAccess;
    }

    /**
     * @param cdAccess the cdAccess to set
     */
    public void setCdAccess(String cdAccess) {
        this.cdAccess = cdAccess;
    }

    /**
     * @return the javaClass
     */
    public String getJavaClass() {
        return javaClass;
    }

    /**
     * @param javaClass the javaClass to set
     */
    public void setJavaClass(String javaClass) {
        this.javaClass = javaClass;
    }

    /**
     * @return the status
     */
    public String getParam1() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setParam1(String param1) {
        this.status = param1;
    }
    
    /**
     * @return the param2
     */
    public String getParam2() {
        return param2;
    }

    /**
     * @param param2 the param2 to set
     */
    public void setParam2(String param2) {
        this.param2 = param2;
    }

    /**
     * @return the requester
     */
    public String getRequester() {
        return requester;
    }

    /**
     * @param requester the requester to set
     */
    public void setRequester(String requester) {
        this.requester = requester;
    }

    /**
     * @return the spv
     */
    public String getSpv() {
        return spv;
    }

    /**
     * @param spv the spv to set
     */
    public void setSpv(String spv) {
        this.spv = spv;
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
     * @return the itemIdLink
     */
    public String getItemIdLink() {
        return itemIdLink;
    }

    /**
     * @param itemIdLink the itemIdLink to set
     */
    public void setItemIdLink(String itemIdLink) {
        this.itemIdLink = itemIdLink;
    }

    /**
     * @return the body
     */
    public String getBody() {
        return body;
    }

    /**
     * @param body the body to set
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * @return the flgAuth
     */
    public String getFlgAuth() {
        return flgAuth;
    }

    /**
     * @param flgAuth the flgAuth to set
     */
    public void setFlgAuth(String flgAuth) {
        this.flgAuth = flgAuth;
    }

    /**
     * @return the batchNo
     */
    public String getBatchNo() {
        return batchNo;
    }

    /**
     * @param batchNo the batchNo to set
     */
    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getNewFilename() {
        return newFilename;
    }

    public void setNewFilename(String newFilename) {
        this.newFilename = newFilename;
    }

    /**
     * @return the flgChecksum
     */
    public String getFlgChecksum() {
        return flgChecksum;
    }

    /**
     * @param flgChecksum the flgChecksum to set
     */
    public void setFlgChecksum(String flgChecksum) {
        this.flgChecksum = flgChecksum;
    }

    /**
     * @return the flgStatus
     */
    public String getFlgStatus() {
        return flgStatus;
    }

    /**
     * @param flgStatus the flgStatus to set
     */
    public void setFlgStatus(String flgStatus) {
        this.flgStatus = flgStatus;
    }

}
