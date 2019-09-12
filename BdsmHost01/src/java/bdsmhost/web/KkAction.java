/*
 * 2015-11-06: Add ktpMaxQuery
 * 2015-11-06: Fix no response or timeout message
 * 2015-11-06: Fix blank response message
 */
package bdsmhost.web;

import bdsm.model.EktpLog;
import bdsm.model.EktpUser;
import bdsm.model.FcrBaBankMast;
import bdsm.model.Parameter;
import bdsmhost.dao.EktpLogDao;
import bdsmhost.dao.EktpUserDao;
import bdsmhost.dao.FcrBaBankMastDao;
import bdsmhost.dao.ParameterDao;
import bdsmhost.svc.KtpSvcClient;
import bdsmhost.svc.SvcContext;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import org.apache.struts2.json.JSONUtil;

/**
 * 
 * @author bdsm
 */
public class KkAction extends BaseHostAction {
    private static final String keyKtpMaxQuery = "ktpMaxQuery";
    private static final String keyKkUrl = "kkUrl";
    private static final String keyKtpIp = "ktpIp";
    private static final String keyKtpProxy = "ktpProxy";

    private static final long serialVersionUID = 5078264277068533593L;

    private String inputKk;
    private String clientIp;
    private String serverIp;
    private String userId;
    private String responseData;
    private String cdBranch;
    private String SysSource;
    private String MenuSource;

    /**
     * 
     * @return
     */
    @Override
    public String doGet() {
        KtpSvcClient svc = new KtpSvcClient();
        EktpUserDao userdao = new EktpUserDao(getHSession());
        getLogger().debug("userId: " + userId);
        getLogger().debug("inputKk: " + inputKk);
        getLogger().debug("clientIp: " + clientIp);
        getLogger().debug("serverIp: " + serverIp);
		getLogger().debug("SYSTEM: " + SysSource);
        getLogger().debug("MENU: " + MenuSource);
        EktpUser user = userdao.get(userId);
        EktpLog elog = EktpLog.newEktpLog();
        elog.setIdUser(userId);
        elog.setDatLog(new Date(Calendar.getInstance().getTimeInMillis()));
        FcrBaBankMastDao bankDao = new FcrBaBankMastDao(getHSession());
        FcrBaBankMast bank = bankDao.get();
        elog.setDatPost(new Date(bank.getDatProcess().getTime()));

        SvcContext localcontext = setKkContext();
        getLogger().debug("ktpUser: " + user.getKtpUser());
        getLogger().debug("ktpPwd: " + user.getKtpPwd());
        getLogger().debug("ktpIp: " + user.getIpUser());
        elog.setKtpUser(user.getKtpUser());
        localcontext.setKtpUser(user.getKtpUser());
        localcontext.setKtpPwd(user.getKtpPwd());
        localcontext.setKtpIpAddr(user.getIpUser());
        //*** Max query per user - Start
        Integer userKtpMaxQuery = user.getKtpMaxQuery();
        if (userKtpMaxQuery != null) {
            localcontext.setMaxQuery(userKtpMaxQuery.intValue());
        }
        //*** Max query per user - End
        elog.setDtmRequest(new Timestamp(Calendar.getInstance().getTime().getTime()));
        elog.setSystem_Src(SysSource);
        elog.setMenuSrc(MenuSource);
        elog.setInputNik(inputKk);
        elog.setClientIp(clientIp);
        elog.setServerIp(serverIp);
        EktpLogDao elogDao = new EktpLogDao(getHSession());
        if (!user.getIpUser().contains(clientIp)) {
            if (user.getIpUser()==null || "".equals(user.getIpUser().trim())) {
                responseData = String.format("{\"content\":[{\"RESPON\":\"IP user tidak terdaftar\"}],\"lastPage\":true,\"numberOfElements\":1,\"sort\":null,\"totalElements\":1,\"firstPage\":true,\"number\":0,\"size\":1}");
            } else {
                responseData = String.format("{\"content\":[{\"RESPON\":\"IP user (%s) tidak sama dengan IP yang terdaftar (%s)\"}],\"lastPage\":true,\"numberOfElements\":1,\"sort\":null,\"totalElements\":1,\"firstPage\":true,\"number\":0,\"size\":1}", clientIp, user.getIpUser());
            }
            elog.setFlgRespFromExternal("N");
        } else {
        int cnt = elogDao.count(elog.getDatLog(), elog.getIdUser());
        if (cnt >=localcontext.getMaxQuery()) {
            responseData = String.format("{\"content\":[{\"RESPON\":\"Inquiry data melebihi batas yang diperbolehkan sebesar %d\"}],\"lastPage\":true,\"numberOfElements\":1,\"sort\":null,\"totalElements\":1,\"firstPage\":true,\"number\":0,\"size\":1}", localcontext.getMaxQuery());
            elog.setFlgRespFromExternal("N");
        } else {
            responseData = svc.get(localcontext, inputKk);
            if (responseData==null) {
                // *** Null response fix - Start (Please also chekc ApplicationResources.properties)
                String msg = this.getText("ektp.nullresp");
                responseData = String.format("{\"content\":[{\"RESPON\":\"%s\"}],\"lastPage\":true,\"numberOfElements\":1,\"sort\":null,\"totalElements\":1,\"firstPage\":true,\"number\":0,\"size\":1}", msg);
                // *** Null response fix - End
                elog.setFlgRespFromExternal("N");
            } else {
                // *** Blank response fix - Start (Please also chekc ApplicationResources.properties)
                if ("{}".equals(responseData.trim())) {
                    String msg = this.getText("ektp.blankresp");
                    responseData = String.format("{\"content\":[{\"RESPON\":\"%s\"}],\"lastPage\":true,\"numberOfElements\":1,\"sort\":null,\"totalElements\":1,\"firstPage\":true,\"number\":0,\"size\":1}", msg);
                }
                // *** Blank response fix - End
                elog.setFlgRespFromExternal("Y");
                    if (responseData.contains("EKTP_STATUS")) {
                        elog.setFlg_Nik_Found("Y");
                    } else {
                        elog.setFlg_Nik_Found("N");
                    }
                }
            }
        }
        elog.setDtmResponse(new Timestamp(Calendar.getInstance().getTime().getTime()));
        elog.setResponse(responseData);
        elog.setCdBranch(cdBranch);
        try { getLogger().debug("ektplog: " + JSONUtil.serialize(elog)); } catch (Exception e) {}
        elogDao.insert(elog);
        getLogger().debug("responseData: " + responseData);
        return SUCCESS;
    }
    
    private void timeoutTest(int seconds) {
        getLogger().info("[BEGIN] Trying timeout");
        try {
            long start = Calendar.getInstance().getTimeInMillis();
            while (true) {
                long end = Calendar.getInstance().getTimeInMillis();
                if (end - start >= seconds * 1000) {
                    break;
                }
                try {
                    Thread.sleep(987);
                } catch (InterruptedException ex) { }
            }
        } finally {
            getLogger().info("[  END] Timeout");
        }
    }

    /**
     * @return the inputKk
     */
    public String getInputKk() {
        return inputKk;
    }

    /**
     * @param inputKk the inputKk to set
     */
    public void setInputKk(String inputKk) {
        this.inputKk = inputKk;
    }

    /**
     * @return the responseData
     */
    public String getResponseData() {
        return responseData;
    }

    /**
     * @param responseData the responseData to set
     */
    public void setResponseData(String responseData) {
        this.responseData = responseData;
    }

    private SvcContext setKkContext() {
        SvcContext ktpcontext = new SvcContext();
        ParameterDao paramDao = new ParameterDao(getHSession());
        Parameter param = paramDao.get(keyKkUrl);
        if (param!=null) {
            ktpcontext.setWsUrl(param.getStrVal());
            ktpcontext.setInputName("No_KK");
        }
        
        param = paramDao.get(keyKtpIp);
        if (param!=null) {
            ktpcontext.setKtpIpAddr(param.getStrVal());
        }

        param = paramDao.get(keyKtpMaxQuery);
        if (param!=null) {
            ktpcontext.setMaxQuery(param.getValue());
        }
        
        String proxyParam = "";
        param = paramDao.get(keyKtpProxy);
        if (param!=null) {
            proxyParam = param.getStrVal();
        }
        String[] ss = proxyParam.split(";");
        for (String s: ss) {
            String[] ss2 = s.split("=");
            if ("viaProxy".equals(ss2[0])) {
                boolean b = false;
                try { b = Boolean.parseBoolean(ss2[1]); } catch (Exception e) {}
                ktpcontext.setViaProxy(b);
            } else if ("isProxyNeedAuth".equals(ss2[0])) {
                ktpcontext.setIsProxyNeedAuth(Boolean.parseBoolean(ss2[1]));
            } else if ("proxyUser".equals(ss2[0])) {
                ktpcontext.setProxyUser(ss2[1]);
            } else if ("proxyPwd".equals(ss2[0])) {
                ktpcontext.setProxyPwd(ss2[1]);
            } else if ("proxyIp".equals(ss2[0])) {
                ktpcontext.setProxyIp(ss2[1]);
            } else if ("proxyPort".equals(ss2[0])) {
                int n = 0;
                try { n = Integer.parseInt(ss2[1]); } catch (Exception e) {}
                ktpcontext.setProxyPort(n);
            }
        }
        return ktpcontext;
    }

    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 
     * @return
     */
    @Override
    public String exec() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     * @return
     */
    @Override
    public String doList() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     * @return
     */
    @Override
    public String doSave() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     * @return
     */
    @Override
    public String doInsert() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     * @return
     */
    @Override
    public String doUpdate() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     * @return
     */
    @Override
    public String doDelete() {
        throw new UnsupportedOperationException("Not supported yet.");
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
     * @return the SysSource
     */
    public String getSysSource() {
        return SysSource;
    }

    /**
     * @param SysSource the SysSource to set
     */
    public void setSysSource(String SysSource) {
        this.SysSource = SysSource;
    }

    /**
     * @return the MenuSource
     */
    public String getMenuSource() {
        return MenuSource;
    }

    /**
     * @param MenuSource the MenuSource to set
     */
    public void setMenuSource(String MenuSource) {
        this.MenuSource = MenuSource;
    }
}
