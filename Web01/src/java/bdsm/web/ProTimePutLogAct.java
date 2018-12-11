/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.web;

import bdsm.model.ProTimePutLog;
import bdsm.util.BdsmUtil;
import bdsm.util.HttpUtil;
import java.util.HashMap;
import java.util.Map;
import org.apache.catalina.Session;
import org.apache.log4j.Logger;
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;

/**
 *
 * @author v00022309
 */
public class ProTimePutLogAct {
      
    private String sessionid;
    private String userid;
    private String cdmenu;
    private String stepname;
    private String errmsg;
    private String TokenKey;
    private String TzToken;
    private String BdsmHost;
    private Map session = new HashMap();

    /**
     * 
     * @param session
     * @param TokenKey
     * @param TzToken
     * @param BdsmHost
     */
    public ProTimePutLogAct(Map session, String TokenKey, String TzToken, String BdsmHost) {
        this.TokenKey = TokenKey;
        this.TzToken = TzToken;
        this.BdsmHost = BdsmHost;
        this.session = session;
        this.sessionid = this.session.get("struts.tokens.token").toString();
        this.cdmenu = this.session.get(Constant.C_IDMENU).toString();
        this.stepname = this.session.get("MESSAGE").toString();
        this.userid = this.session.get(Constant.C_IDUSER).toString();
        //ProTimePutLogAct(log,TzToken,TokenKey,BdsmHost);
        RunProTime();
    }
    
    
    /**
     * 
     * @param prot
     * @param TokenKey
     * @param TzToken
     * @param BdsmHost
     */
    public ProTimePutLogAct(ProTimePutLog prot, String TokenKey, String TzToken, String BdsmHost) {
        this.sessionid = prot.getSessionid();
        this.userid = prot.getUserid();
        this.cdmenu = prot.getCdmenu();
        this.stepname = prot.getStepname();
        this.errmsg = prot.getErrmsg();
        this.TokenKey = TokenKey;
        this.TzToken = TzToken;
        this.BdsmHost = BdsmHost;
    }
       
    
    /**
     * 
     * @return
     */
    protected Logger getLogger() {
        return Logger.getLogger(this.getClass().getName());
    }

    
    /**
     * 
     * @param sessionid
     * @param userid
     * @param cdmenu
     * @param stepname
     * @param errmsg
     * @param TokenKey
     * @param TzToken
     * @param BdsmHost
     */
    public ProTimePutLogAct(String sessionid, String userid, String cdmenu, String stepname, String errmsg,String TokenKey, String TzToken, String BdsmHost) {
        getLogger().info("sessionid:" +sessionid);
        getLogger().info("userid:" +userid);
        getLogger().info("cdmenu:" +cdmenu);
        getLogger().info("stepname:" +stepname);
        getLogger().info("err:" +errmsg);
        this.sessionid = sessionid;
        this.userid = userid;
        this.cdmenu = cdmenu;
        this.stepname = stepname;
        this.errmsg = errmsg;
        this.TokenKey = TokenKey;
        this.TzToken = TzToken;
        this.BdsmHost = BdsmHost;
        
    }

    /**
     * 
     * @return
     */
    public boolean RunProTime() {
        Map<String, String> map = new HashMap<String, String>();
        String result;
        HashMap resultMap;
        String reqResult = null;
        String errorCode = null;
        
        getLogger().info("sessionid :" + this.sessionid);
        getLogger().info("userid :" + getUserid());
        getLogger().info("cdmenu :" + getCdmenu());
        getLogger().info("stepname :" + getStepname());
        getLogger().info("errmsg :" + getErrmsg());
        getLogger().info("TokenKey :" + getTokenKey());
        getLogger().info("TzToken :" + getTzToken());
        
        
        map.put("model.sessionid", this.sessionid);
        map.put("model.userid", getUserid());
        map.put("model.cdmenu", getCdmenu());
        map.put("model.stepname", getStepname());
        map.put("model.errmsg", getErrmsg());
        map.put("model.TokenKey", getTokenKey());
        map.put("model.TzToken", getTzToken());
        map.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));
                       
        result = HttpUtil.request(getBdsmHost() + ACTION, map);
        
        
        try {
                resultMap = (HashMap)JSONUtil.deserialize(result);
                reqResult = (String)resultMap.get("jsonStatus");
            errorCode = (String)resultMap.get("errorCode");
            } catch (JSONException ex) {
            getLogger().fatal(ex, ex);
            }
           
        
        return true;
       
    }
    private static final String ACTION = "procTimePutLog_insert.action";

   

    /**
     * @return the sessionid
     */
    public String getSessionid() {
        return sessionid;
    }

    /**
     * @param sessionid the sessionid to set
     */
    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    /**
     * @return the userid
     */
    public String getUserid() {
        return userid;
    }

    /**
     * @param userid the userid to set
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }

    /**
     * @return the cdmenu
     */
    public String getCdmenu() {
        return cdmenu;
    }

    /**
     * @param cdmenu the cdmenu to set
     */
    public void setCdmenu(String cdmenu) {
        this.cdmenu = cdmenu;
    }

    /**
     * @return the stepname
     */
    public String getStepname() {
        return stepname;
    }

    /**
     * @param stepname the stepname to set
     */
    public void setStepname(String stepname) {
        this.stepname = stepname;
    }

    /**
     * @return the errmsg
     */
    public String getErrmsg() {
        return errmsg;
    }

    /**
     * @param errmsg the errmsg to set
     */
    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    /**
     * @return the TokenKey
     */
    public String getTokenKey() {
        return TokenKey;
    }

    /**
     * @param TokenKey the TokenKey to set
     */
    public void setTokenKey(String TokenKey) {
        this.TokenKey = TokenKey;
    }

    /**
     * @return the TzToken
     */
    public String getTzToken() {
        return TzToken;
    }

    /**
     * @param TzToken the TzToken to set
     */
    public void setTzToken(String TzToken) {
        this.TzToken = TzToken;
    }

    /**
     * @return the BdsmHost
     */
    public String getBdsmHost() {
        return BdsmHost;
    }

    /**
     * @param BdsmHost the BdsmHost to set
     */
    public void setBdsmHost(String BdsmHost) {
        this.BdsmHost = BdsmHost;
    }
      
}
