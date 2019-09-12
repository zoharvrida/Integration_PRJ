/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;

import java.sql.Clob;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.UUID;

/**
 *
 * @author 00030663
 */
public class EktpLog extends BaseModel {

    private String idLog;
    private String idUser;
    private String ktpUser;
    private String inputNik;
    private Timestamp dtmRequest;
    private Timestamp dtmResponse;
    private Date datPost;
    private Date datLog;
    private String response;
    private String flgRespFromExternal;
    private String clientIp;
    private String serverIp;
    private String flg_Nik_Found;
    private String cdBranch;
    private String System_Src;
    private String MenuSrc;
    private Clob largeResp;

    public static EktpLog newEktpLog() {
        EktpLog newlog = new EktpLog();
        newlog.idLog = UUID.randomUUID().toString();
        return newlog;
    }
    
    /**
     * @return the idLog
     */
    public String getIdLog() {
        return idLog;
    }

    /**
     * @param idLog the idLog to set
     */
    public void setIdLog(String idLog) {
        this.idLog = idLog;
    }

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
     * @return the inputNik
     */
    public String getInputNik() {
        return inputNik;
    }

    /**
     * @param inputNik the inputNik to set
     */
    public void setInputNik(String inputNik) {
        this.inputNik = inputNik;
    }

    /**
     * @return the dtmRequest
     */
    public Timestamp getDtmRequest() {
        return dtmRequest;
    }

    /**
     * @param dtmRequest the dtmRequest to set
     */
    public void setDtmRequest(Timestamp dtmRequest) {
        this.dtmRequest = dtmRequest;
    }

    /**
     * @return the dtmResponse
     */
    public Timestamp getDtmResponse() {
        return dtmResponse;
    }

    /**
     * @param dtmResponse the dtmResponse to set
     */
    public void setDtmResponse(Timestamp dtmResponse) {
        this.dtmResponse = dtmResponse;
    }

    /**
     * @return the datPost
     */
    public Date getDatPost() {
        return datPost;
    }

    /**
     * @param datPost the datPost to set
     */
    public void setDatPost(Date datPost) {
        this.datPost = datPost;
    }

    /**
     * @return the datLog
     */
    public Date getDatLog() {
        return datLog;
    }

    /**
     * @param datLog the datLog to set
     */
    public void setDatLog(Date datLog) {
        this.datLog = datLog;
    }

    /**
     * @return the response
     */
    public String getResponse() {
        return response;
    }

    /**
     * @param response the response to set
     */
    public void setResponse(String response) {
        this.response = response;
    }

    /**
     * @return the flgRespFromExternal
     */
    public String getFlgRespFromExternal() {
        return flgRespFromExternal;
    }

    /**
     * @param flgRespFromExternal the flgRespFromExternal to set
     */
    public void setFlgRespFromExternal(String flgRespFromExternal) {
        this.flgRespFromExternal = flgRespFromExternal;
    }

    /**
     * @return the clientIp
     */
    public String getClientIp() {
        return clientIp;
    }

    /**
     * @param clientIp the clientIp to set
     */
    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    /**
     * @return the serverIp
     */
    public String getServerIp() {
        return serverIp;
    }

    /**
     * @param serverIp the serverIp to set
     */
    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    /**
     * @return the flg_Nik_Found
     */
    public String getFlg_Nik_Found() {
        return flg_Nik_Found;
    }

    /**
     * @param flg_Nik_Found the flg_Nik_Found to set
     */
    public void setFlg_Nik_Found(String flg_Nik_Found) {
        this.flg_Nik_Found = flg_Nik_Found;
    }
    

	@Override
	public String toString() {
		return "EktpLog ["
				+ "idLog=" + idLog + ", "
				+ "idUser=" + idUser + ", "
				+ "ktpUser=" + ktpUser + ", "
				+ "inputNik=" + inputNik + ", "
				+ "dtmRequest=" + dtmRequest + ", "
				+ "dtmResponse=" + dtmResponse + ", "
				+ "datPost=" + datPost + ", "
				+ "datLog=" + datLog + ", "
				+ "response=" + response + ", "
				+ "flgRespFromExternal=" + flgRespFromExternal + ", "
				+ "clientIp=" + clientIp + ", " 
				+ "serverIp=" + serverIp + ", "
				+ "flgNikFound=" + flg_Nik_Found + ", "
				+ "cdBranch=" + cdBranch + ", "
				+ "SystemSrc=" + System_Src + ", "
				+ "MenuSrc=" + MenuSrc
				+ "]";
	}
/**
     * @return the cdBranch
     */
    public String getCdBranch() {
        return cdBranch;
    }

    /**
     * @param cdBranch the cdBranch to set
     */
    public void setCdBranch(String cdBranch) {
        this.cdBranch = cdBranch;
    }

    /**
     * @return the System_Src
     */
    public String getSystem_Src() {
        return System_Src;
    }

    /**
     * @param System_Src the System_Src to set
     */
    public void setSystem_Src(String System_Src) {
        this.System_Src = System_Src;
    }

    /**
     * @return the MenuSrc
     */
    public String getMenuSrc() {
        return MenuSrc;
    }

    /**
     * @param MenuSrc the MenuSrc to set
     */
    public void setMenuSrc(String MenuSrc) {
        this.MenuSrc = MenuSrc;
    }

    /**
     * @return the largeResp
     */
    public Clob getLargeResp() {
        return largeResp;
    }

    /**
     * @param largeResp the largeResp to set
     */
    public void setLargeResp(Clob largeResp) {
        this.largeResp = largeResp;
    }
}
