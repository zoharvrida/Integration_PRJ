/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.model;

import java.io.Serializable;

/**
 *
 * @author SDM
 */
public class EktpSysuser extends BaseModel implements Serializable{
    private String ktpUser;
    private String ktpPwd;
    private String nikUser;
    private String ipUser;
    private Integer ktpQuota;

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

    /**
     * @return the ktpQuota
     */
    public Integer getKtpQuota() {
        return ktpQuota;
    }

    /**
     * @param ktpQuota the ktpQuota to set
     */
    public void setKtpQuota(Integer ktpQuota) {
        this.ktpQuota = ktpQuota;
    }
    @Override
	public String toString() {
    	StringBuilder sb = new StringBuilder(this.getClass().getSimpleName())
			.append("{")
			.append("ktpUser=").append(this.ktpUser)
			.append(",ktpPwd=").append(this.ktpPwd)
			.append(",nikUser=").append(this.nikUser)
			.append(",ipUser=").append(this.ipUser)
			.append(",ktpQuota=").append(this.ktpQuota)
			.append("}");
		return sb.toString();
	}
}
