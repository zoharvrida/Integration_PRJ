/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsmhost.svc;

/**
 *
 * @author 00030663
 */
public class SvcContext {
	private static final long serialVersionUID = 1L;
    private String wsUrl;
    private boolean viaProxy;
    private boolean isProxyNeedAuth;
    private String proxyIp;
    private int proxyPort;
    private String proxyUser;
    private String proxyPwd;
    private String ktpUser;
    private String ktpPwd;
    private String ktpIpAddr;
    private int maxQuery;
    private String inputName="NIK";

    
    public SvcContext clone() {
        SvcContext result = new SvcContext();
        result.wsUrl = this.wsUrl;
        result.viaProxy = this.viaProxy;
        result.isProxyNeedAuth = this.isProxyNeedAuth;
        result.proxyIp = this.proxyIp;
        result.proxyPort = this.proxyPort;
        result.proxyUser = this.proxyUser;
        result.proxyPwd = this.proxyPwd;
        result.ktpUser = this.ktpUser;
        result.ktpPwd = this.ktpPwd;
        result.ktpIpAddr = this.ktpIpAddr;
        return result;
    }
    /**
     * @return the wsUrl
     */
    public String getWsUrl() {
        return wsUrl;
    }

    /**
     * @param wsUrl the wsUrl to set
     */
    public void setWsUrl(String wsUrl) {
        this.wsUrl = wsUrl;
    }

    /**
     * @return the viaProxy
     */
    public boolean isViaProxy() {
        return viaProxy;
    }

    /**
     * @param viaProxy the viaProxy to set
     */
    public void setViaProxy(boolean viaProxy) {
        this.viaProxy = viaProxy;
    }

    /**
     * @return the isProxyNeedAuth
     */
    public boolean isIsProxyNeedAuth() {
        return isProxyNeedAuth;
    }

    /**
     * @param isProxyNeedAuth the isProxyNeedAuth to set
     */
    public void setIsProxyNeedAuth(boolean isProxyNeedAuth) {
        this.isProxyNeedAuth = isProxyNeedAuth;
    }

    /**
     * @return the proxyUser
     */
    public String getProxyUser() {
        return proxyUser;
    }

    /**
     * @param proxyUser the proxyUser to set
     */
    public void setProxyUser(String proxyUser) {
        this.proxyUser = proxyUser;
    }

    /**
     * @return the proxyPwd
     */
    public String getProxyPwd() {
        return proxyPwd;
    }

    /**
     * @param proxyPwd the proxyPwd to set
     */
    public void setProxyPwd(String proxyPwd) {
        this.proxyPwd = proxyPwd;
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
     * @return the ktpIpAddr
     */
    public String getKtpIpAddr() {
        return ktpIpAddr;
    }

    /**
     * @param ktpIpAddr the ktpIpAddr to set
     */
    public void setKtpIpAddr(String ktpIpAddr) {
        this.ktpIpAddr = ktpIpAddr;
    }

    /**
     * @return the proxyIp
     */
    public String getProxyIp() {
        return proxyIp;
    }

    /**
     * @param proxyIp the proxyIp to set
     */
    public void setProxyIp(String proxyIp) {
        this.proxyIp = proxyIp;
    }

    /**
     * @return the proxyPort
     */
    public int getProxyPort() {
        return proxyPort;
    }

    /**
     * @param proxyPort the proxyPort to set
     */
    public void setProxyPort(int proxyPort) {
        this.proxyPort = proxyPort;
    }
    
    /**
     * @return the maxQuery
     */
    public int getMaxQuery() {
        return maxQuery;
    }

    /**
     * @param maxQuery the maxQuery to set
     */
    public void setMaxQuery(int maxQuery) {
        this.maxQuery = maxQuery;
    }
    
    /**
     * @return the inputName
     */
    public String getInputName() {
        return inputName;
    }

    /**
     * @param inputName the inputName to set
     */
    public void setInputName(String inputName) {
        if (inputName==null) return;
        this.inputName = inputName;
    }
    
}
