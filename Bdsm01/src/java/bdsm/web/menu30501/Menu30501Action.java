/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.web.menu30501;

import bdsm.model.KtpData;
import bdsm.util.BdsmUtil;
import bdsm.util.HttpUtil;
import bdsm.web.BaseContentAction;
import bdsm.web.Constant;
import bdsm.web.request.JSONService;
import com.opensymphony.xwork2.ActionSupport;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;

/**
 * 
 * @author bdsm
 */
public class Menu30501Action extends BaseContentAction {
    private static final String ACTION_GET = "ktp_get.action";
    private static final long serialVersionUID = 1L;
    private static final String SystemSrc = "BDSMWEB";
    private static final String MenuSrc = "30501";

    private String inputNik;
    private String responData;
    private String contentData;
    private KtpData model=new KtpData();
    //private JSONService JSONUtil = new JSONService();
    
    /**
     * 
     * @return
     */
    public String adAdd() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     * @return
     */
    public String getInputNik() {
        return inputNik;
    }

    /**
     * 
     * @param inputNik
     */
    public void setInputNik(String inputNik) {
        this.inputNik = inputNik;
    }

    /**
     * 
     * @return
     */
    public String exec() {
        return ActionSupport.SUCCESS;
    }

    /**
     * 
     * @return
     */
    @SkipValidation
    public String inquiry() {
        getLogger().info("[BEGIN] inquiry()");
        if (!isValidSession()) {
            return logout();
        }

        Map<String, String> map = new HashMap<String, String>();
        String result;
        HashMap resultMap;

        getLogger().debug("inputNik : " + inputNik);
        
        String ipAddress = this.getServletRequest().getHeader("X-FORWARDED-FOR");  
        if (ipAddress == null || "0:0:0:0:0:0:0:1".equals(ipAddress)) {  
            getLogger().debug("ipAddress=" + ipAddress);
            ipAddress = this.getServletRequest().getRemoteAddr();
            getLogger().debug("ipAddress2=" + ipAddress);
        }
        String serverIp = null;
        try {
            serverIp = InetAddress.getLocalHost().getHostAddress();
            getLogger().debug("serverIp=" + serverIp);
        } catch (UnknownHostException e) {
            serverIp = this.getServletRequest().getLocalAddr();
            getLogger().debug("serverIp2=" + serverIp);
        }

        String cdBranch = null;
        try {
            cdBranch = ((Integer) session.get(Constant.C_CODEBRANCH)).toString();
        } catch (Exception e) {
            getLogger().info("FAILED GET BRANCH :" + e,e);
        }
        map.put("SysSource", SystemSrc);
        map.put("MenuSource", StringUtils.isBlank((String) this.session.get(Constant.C_IDMENU))? MenuSrc: this.session.get(Constant.C_IDMENU).toString());
        map.put("cdBranch", cdBranch);
        map.put("inputNik", inputNik);
        map.put("clientIp", ipAddress);
        map.put("serverIp", serverIp);
        map.put("userId", (String) session.get(Constant.C_IDUSER));
        map.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));

        result = HttpUtil.request(getBdsmHost() + ACTION_GET, map);
        
        try {
            if (result==null) {
                getLogger().info("result=null");
                responData="{\"content\":[{\"RESPON\":\"Timeout from host\"}]}";
            } else {
                resultMap = (HashMap)JSONUtil.deserialize(result);
                getLogger().info("result=" + resultMap);
                responData = (String)resultMap.get("responseData");
                getLogger().info("--responData=" + responData);
                if (responData==null || "".equals(responData)) {
                    this.addActionError("Got null response from host");
                    responData="{\"content\":[{\"RESPON\":\"Null data from host\"}]}";
                }
            }
            HashMap responseDataMap = (HashMap)JSONUtil.deserialize(responData);
            List contentList = (List) responseDataMap.get("content");
            for (Object o: contentList) {
                HashMap content = (HashMap)o;
                getLogger().info("----content=" + content);
                if (content==null) {
                    this.addActionError("Got null content from the response");
                } else {
                    model.read(content);
                    getLogger().info("------RESPON=" + model.getResponseMessage());
                    if (model.getResponseMessage()==null || "".equals(model.getResponseMessage())) {
                        contentData = JSONUtil.serialize(model);
                        getLogger().info("--------contentData=" + contentData);
                    } else {
                        contentData = JSONUtil.serialize(model);
                        this.addActionError(model.getResponseMessage());
                    }
                }
            }
        } catch (JSONException ex) {
            getLogger().fatal("[FATAL] inquiry() " + ex.getMessage(), ex);
        } finally {
            getLogger().info("[ END] inquiry()");
        }

        return "inquiry";
    }

    /**
     * 
     * @return
     */
    @Override
    public String doAdd() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     * @return
     */
    @Override
    public String doEdit() {
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
     * @return the result
     */
    public String getResponData() {
        return responData;
    }

    /**
     * @param responData 
     */
    public void setResponData(String responData) {
        this.responData = responData;
    }

    /**
     * @return the model
     */
    public KtpData getModel() {
        return model;
    }

    /**
     * @param model the model to set
     */
    public void setModel(KtpData model) {
        this.model = model;
    }

    /**
     * @return the contentData
     */
    public String getContentData() {
        return contentData;
    }

    /**
     * @param contentData the contentData to set
     */
    public void setContentData(String contentData) {
        this.contentData = contentData;
    }
}
