/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.scheduler.model;

import bdsm.model.BaseModel;
import java.util.Date;

/**
 *
 * @author NCBS
 */
@SuppressWarnings("serial")
public class EKtpUpdateTmp extends BaseModel{

    private String UserId;
    private String namuser;
    private String nikuser;
    private String ktpuser;
    private String ktppwd;
    private String ktppwddecrypt;
    private Date ktpdtmupdated;    
    private String ipuser;    
    private String status;
    private String statusreason;
    private String nobatch;
    private Integer id;
    private String cmd;
	private String ktpMaxQuery;
    private String source;

	/**
	 * @return the UserId
	 */
	public String getUserId() {
		return UserId;
	}

	/**
	 * @param UserId the UserId to set
	 */
	public void setUserId(String UserId) {
		this.UserId = UserId;
	}

	/**
	 * @return the namuser
	 */
	public String getNamuser() {
		return namuser;
	}

	/**
	 * @param namuser the namuser to set
	 */
	public void setNamuser(String namuser) {
		this.namuser = namuser;
	}

	/**
	 * @return the nikuser
	 */
	public String getNikuser() {
		return nikuser;
	}

	/**
	 * @param nikuser the nikuser to set
	 */
	public void setNikuser(String nikuser) {
		this.nikuser = nikuser;
	}

	/**
	 * @return the ktpuser
	 */
	public String getKtpuser() {
		return ktpuser;
	}

	/**
	 * @param ktpuser the ktpuser to set
	 */
	public void setKtpuser(String ktpuser) {
		this.ktpuser = ktpuser;
	}

	/**
	 * @return the ktppwd
	 */
	public String getKtppwd() {
		return ktppwd;
	}

	/**
	 * @param ktppwd the ktppwd to set
	 */
	public void setKtppwd(String ktppwd) {
		this.ktppwd = ktppwd;
	}

	/**
	 * @return the ktppwddecrypt
	 */
	public String getKtppwddecrypt() {
		return ktppwddecrypt;
	}

	/**
	 * @param ktppwddecrypt the ktppwddecrypt to set
	 */
	public void setKtppwddecrypt(String ktppwddecrypt) {
		this.ktppwddecrypt = ktppwddecrypt;
	}

	/**
	 * @return the ktpdtmupdated
	 */
	public Date getKtpdtmupdated() {
		return ktpdtmupdated;
	}

	/**
	 * @param ktpdtmupdated the ktpdtmupdated to set
	 */
	public void setKtpdtmupdated(Date ktpdtmupdated) {
		this.ktpdtmupdated = ktpdtmupdated;
	}

	/**
	 * @return the ipuser
	 */
	public String getIpuser() {
		return ipuser;
	}

	/**
	 * @param ipuser the ipuser to set
	 */
	public void setIpuser(String ipuser) {
		this.ipuser = ipuser;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the statusreason
	 */
	public String getStatusreason() {
		return statusreason;
	}

	/**
	 * @param statusreason the statusreason to set
	 */
	public void setStatusreason(String statusreason) {
		this.statusreason = statusreason;
	}

	/**
	 * @return the nobatch
	 */
	public String getNobatch() {
		return nobatch;
	}

	/**
	 * @param nobatch the nobatch to set
	 */
	public void setNobatch(String nobatch) {
		this.nobatch = nobatch;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

    /**
     * 
     * @return
     */
    public String getCmd() {
        return cmd;
    }
    /**
     * 
     * @param cmd
     */
    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    /**
     * 
     * @return
     */
    public String getKtpMaxQuery() {
		return ktpMaxQuery;
	}

    /**
     * 
     * @param ktpMaxQuery
     */
    public void setKtpMaxQuery(String ktpMaxQuery) {
		this.ktpMaxQuery = ktpMaxQuery;
	}
    
    /**
     * @return the source
     */
    public String getSource() {
        return source;
    }

    /**
     * @param source the source to set
     */
    public void setSource(String source) {
        this.source = source;
    }

}