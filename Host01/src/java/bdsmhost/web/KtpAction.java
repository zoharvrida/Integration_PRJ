/*
 * 2015-11-06: Add ktpMaxQuery
 * 2015-11-06: Fix no response or timeout message
 * 2015-11-06: Fix blank response message
 */
package bdsmhost.web;

import bdsm.model.*;
import bdsm.scheduler.PropertyPersister;
import bdsm.scheduler.StatusDefinition;
import bdsm.util.HibernateUtil;
import bdsmhost.dao.*;
import bdsmhost.svc.KtpSvcClient;
import bdsmhost.svc.SvcContext;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import org.apache.struts2.json.JSONUtil;

/**
 * 
 * @author bdsm
 */
public class KtpAction extends BaseHostAction {

    private static final String keyKtpMaxQuery = "ktpMaxQuery";
    private static final String keyKtpUrl = "ktpUrl";
    private static final String keyKtpIp = "ktpIp";
    private static final String keyKtpProxy = "ktpProxy";
    private static final String keyKtpAllowedMenu = "ktpMenuAllowed";
    private static final String keyKtpAllowedSystem = "ktpSystemAllowed";
    private static final long serialVersionUID = 5078264277068533593L;
    private SvcContext localcontext = new SvcContext();
    private EktpLog elog = new EktpLog();
    private EktpUser user = new EktpUser();
    private String inputNik;
    private String clientIp;
    private String serverIp;
    private String userId;
    private String responseData;
    private String cdBranch;
    private String sysSource;
    private String menuSource;
    private boolean flagQuota = true;

    /**
     * 
     * @return
     */
    @Override
    public String doGet() {

        KtpSvcClient svc = new KtpSvcClient();
        EktpUserDao userdao = new EktpUserDao(getHSession());
        getLogger().debug("userId: " + userId);
        getLogger().debug("inputNik: " + inputNik);
        getLogger().debug("clientIp: " + clientIp);
        getLogger().debug("serverIp: " + serverIp);
        getLogger().debug("SYSTEM: " + sysSource);
        getLogger().debug("MENU: " + menuSource);
        boolean ipFlag = true;

        user = userdao.get(userId);
        elog = EktpLog.newEktpLog();
        elog.setIdUser(userId);
        elog.setDatLog(new Date(Calendar.getInstance().getTimeInMillis()));
        FcrBaBankMastDao bankDao = new FcrBaBankMastDao(getHSession());
        FcrBaBankMast bank = bankDao.get();
        elog.setDatPost(new Date(bank.getDatProcess().getTime()));

        this.localcontext = setKtpContext();
        getLogger().debug("ktpUser: " + user.getKtpUser());
        getLogger().debug("ktpPwd: " + user.getKtpPwd());
        getLogger().debug("ktpIp: " + user.getIpUser());
        getLogger().debug("ktpMaxQuery: " + user.getKtpMaxQuery());
        elog.setKtpUser(user.getKtpUser());
        // check blacklist menu BDSM and System
        //ktpChecking();
        //ktpSysChecking();

        Integer userKtpMaxQuery = user.getKtpMaxQuery();
        if (userKtpMaxQuery != null) {
            localcontext.setMaxQuery(userKtpMaxQuery.intValue());
        }

        localcontext.setKtpIpAddr(clientIp);

        elog.setDtmRequest(new Timestamp(Calendar.getInstance().getTime().getTime()));
        elog.setSystem_Src(sysSource);
        elog.setMenuSrc(menuSource);
        elog.setInputNik(inputNik);
        elog.setClientIp(clientIp);
        elog.setServerIp(serverIp);
        EktpLogDao elogDao = new EktpLogDao(getHSession());
        int cnt = elogDao.count(elog.getDatPost(), elog.getIdUser());
        
        boolean checkKtp = ktpSysChecking();
        if (!checkKtp) {
            getLogger().debug("USER IP :" + user.getIpUser());
            getLogger().debug("USER IP CLIENT :" + clientIp);
            if (!user.getIpUser().contains(clientIp)) {
                if (user.getIpUser() == null || "".equals(user.getIpUser().trim())) {
                    responseData = String.format("{\"content\":[{\"RESPON\":\"IP user tidak terdaftar\"}],\"lastPage\":true,\"numberOfElements\":1,\"sort\":null,\"totalElements\":1,\"firstPage\":true,\"number\":0,\"size\":1}");
                } else {
                    responseData = String.format("{\"content\":[{\"RESPON\":\"IP user (%s) tidak sama dengan IP yang terdaftar (%s)\"}],\"lastPage\":true,\"numberOfElements\":1,\"sort\":null,\"totalElements\":1,\"firstPage\":true,\"number\":0,\"size\":1}", clientIp, user.getIpUser());
                }
                elog.setFlgRespFromExternal("N");
                ipFlag = false;
                flagQuota = false;
                cnt = localcontext.getMaxQuery();
            }
        }

        if (cnt >= localcontext.getMaxQuery() && !checkKtp) {
            if (ipFlag) {
                responseData = String.format("{\"content\":[{\"RESPON\":\"Inquiry data melebihi batas yang diperbolehkan sebesar %d\"}],\"lastPage\":true,\"numberOfElements\":1,\"sort\":null,\"totalElements\":1,\"firstPage\":true,\"number\":0,\"size\":1}", localcontext.getMaxQuery());
                elog.setFlgRespFromExternal("N");
            }
        } else {
            responseData = svc.get(localcontext, inputNik);
            if (responseData == null) {
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
                /*if (checkKtp) {
                    // check if user already have KTPuser
                    if ("".equalsIgnoreCase(user.getKtpUser())) {
                        String msg = this.getText("ektp.notregistered.user");
                        this.addActionMessage(msg);
                    }
                }*/
            }
        }
        // }
        HibernateUtil.evictObjectFromPersistenceContext(user, getHSession());
        getLogger().info("USER AFTER EVICT :" + user.getIpUser());
        elog.setDtmResponse(new Timestamp(Calendar.getInstance().getTime().getTime()));
        elog.setResponse(responseData);
        elog.setCdBranch(cdBranch);
        try {
            getLogger().debug("ektplog: " + JSONUtil.serialize(elog));
        } catch (Exception e) {
        }
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
                } catch (InterruptedException ex) {
                }
            }
        } finally {
            getLogger().info("[  END] Timeout");
        }
    }

    /**
     * 
     * @return
     */
    public boolean ktpSysChecking() {
        EktpSysuserDao sysDao = new EktpSysuserDao(getHSession());
        boolean flagSearchuser = true;
        if (checkSysuser()) {
            //if (user.getKtpUser() == null) {
            getLogger().debug("QUERYMAX :" + localcontext.getMaxQuery());
            getLogger().debug("SYS :" + sysSource);
            getLogger().debug("MENU :" + menuSource);

            //List getAvail = sysDao.getSysMaxQuota(sysSource, menuSource, localcontext.getMaxQuery());
            List getAvail = sysDao.getMaxRank();
            if (!getAvail.isEmpty()) {
                Map indAvail = (Map) getAvail.get(0);

                getLogger().info("MAP QUERY :" + indAvail);

                //Integer profCount = Integer.parseInt(indAvail.get("maxQuery").toString());
                //EktpSysuser sysUser = sysDao.get(indAvail.get("ktpUser").toString());
                if (indAvail.get("MAXRANK") == null) {
                    localcontext.setKtpUser(user.getKtpUser());
                    localcontext.setKtpPwd(user.getKtpPwd());
                    flagQuota = true;
                } else {
                int loopUser = 0;
                while (flagSearchuser) {
                    int quota = StatusDefinition.getQuotaSync();
                    quota++;

                    int maxRanks = Integer.parseInt(indAvail.get("MAXRANK").toString());
                    if (quota > maxRanks) {
                        StatusDefinition.setQuotaSync(1);
                        quota = 1;
                    }
                    StatusDefinition.setQuotaSync(quota);
                    List<Map> lSys = sysDao.getSpecificRank(quota);

                    if (!lSys.isEmpty()) {
                        Map sysUser = lSys.get(0);
                        getLogger().debug("USER_KUOTA :" + StatusDefinition.getQuotaSync());
                        getLogger().info("SYSUSER :" + sysUser.toString());

                        EktpLogDao elogDao = new EktpLogDao(getHSession());
                        int cnt = elogDao.count(elog.getDatPost(), sysUser.get("KTPUSER").toString());
                        int compCnt = 0;
                        try {
                            compCnt = Integer.parseInt(sysUser.get("KTPQUOTA").toString());
                        } catch (Exception numberFormatException) {
                            getLogger().debug("NUMBER :" + numberFormatException);
                            compCnt = PropertyPersister.KTPMAX;
                        };
                        if (cnt > compCnt) {
                            if (loopUser == maxRanks) {
                                localcontext = null;
                                this.addActionMessage("WARNING : QUOTA Expired for all User");
                            }
                            flagSearchuser = false;
                        } else {
                            if (cnt <= localcontext.getMaxQuery()) {
                                flagSearchuser = false;
                            }
                        }
                        try {
                            elog.setKtpUser(sysUser.get("KTPUSER").toString());
                            //} else {
                            localcontext.setKtpUser(sysUser.get("KTPUSER").toString());
                            localcontext.setKtpPwd(sysUser.get("KTPPWD").toString());
                            user.setIpUser(sysUser.get("IPUSER").toString());
                        } catch (Exception e) {
                            getLogger().debug("EXCEPTION :" + e,e);
                            flagSearchuser = true;
                            if(loopUser == maxRanks){
                                flagSearchuser = false;
                            }
                        }
                        loopUser++;
                    }
                }
                }
            } else {
                localcontext = null;
                this.addActionMessage("WARNING : No Available user in Database");
            }

            if (localcontext.getKtpUser() == null) {
                this.addActionMessage("WARNING : Your User is not registered yet to system, please follow up for KTP user Registration");
            }
            return true;
            //}
        } else {
            localcontext.setKtpUser(user.getKtpUser());
            localcontext.setKtpPwd(user.getKtpPwd());
            flagQuota = true;
            return false;
        }
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

    private boolean checkSysuser() {
        ParameterDao paramDao = new ParameterDao(getHSession());
        MasterUserDao userDao = new MasterUserDao(getHSession());
        Parameter param = paramDao.get(keyKtpAllowedMenu);
        boolean flag = false;
        if (param != null) {
            if (param.getStrVal().contains(menuSource)) {
                flag = true;
            }
        }
        
        param = paramDao.get(keyKtpAllowedSystem);
        if (param != null) {
            if (flag) {
                if (param.getStrVal().contains(sysSource)) {
                    flag = true;
                }
            }
        }
        
        param = paramDao.get(keyKtpAllowedMenu + "." + menuSource);
        MasterUser m = userDao.get(userId);
        if (param != null){
            if (flag) {
                if (param.getStrVal().contains(m.getIdTemplate())) {
                    return true;
                }
            }
        }
        getLogger().debug("NOT CRITERIA: " + flag);
        return flag;
    }

    private SvcContext setKtpContext() {
        SvcContext ktpcontext = new SvcContext();
        ParameterDao paramDao = new ParameterDao(getHSession());
        Parameter param = paramDao.get(keyKtpUrl);
        if (param != null) {
            ktpcontext.setWsUrl(param.getStrVal());
        }

        param = paramDao.get(keyKtpIp);
        if (param != null) {
            ktpcontext.setKtpIpAddr(param.getStrVal());
        }

        param = paramDao.get(keyKtpMaxQuery);
        if (param != null) {
            ktpcontext.setMaxQuery(param.getValue());
        }

        String proxyParam = "";
        param = paramDao.get(keyKtpProxy);
        if (param != null) {
            proxyParam = param.getStrVal();
        }
        String[] ss = proxyParam.split(";");
        for (String s : ss) {
            String[] ss2 = s.split("=");
            if ("viaProxy".equals(ss2[0])) {
                boolean b = false;
                try {
                    b = Boolean.parseBoolean(ss2[1]);
                } catch (Exception e) {
                }
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
                try {
                    n = Integer.parseInt(ss2[1]);
                } catch (Exception e) {
                }
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
     * @return the sysSource
     */
    public String getSysSource() {
        return sysSource;
    }

    /**
     * @param SysSource 
     */
    public void setSysSource(String SysSource) {
        this.sysSource = SysSource;
    }

    /**
     * @return the menuSource
     */
    public String getMenuSource() {
        return menuSource;
    }

    /**
     * @param MenuSource 
     */
    public void setMenuSource(String MenuSource) {
        this.menuSource = MenuSource;
    }
}
