/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.web.menu30502;

import bdsm.model.KtpData;
import bdsm.util.BdsmUtil;
import bdsm.util.HttpUtil;
import bdsm.web.BaseContentAction;
import bdsm.web.Constant;
import bdsm.web.request.JSONService;
import com.opensymphony.xwork2.ActionSupport;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;

/**
 * 
 * @author bdsm
 */
public class Menu30502Action extends BaseContentAction {
    private static final String ACTION_GET = "kk_get.action";
	private static final long serialVersionUID = 1L;
        private static final String SystemSrc = "BDSMWEB";
    private static final String MenuSrc = "30502";
    
    private String inputKk;
    private String responData;
    private String contentData;
    private List<KtpData> modelList=new ArrayList<KtpData>();
    private KtpData model = new KtpData();
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
    public String getInputKk() {
        return inputKk;
    }

    /**
     * 
     * @param inputKk
     */
    public void setInputKk(String inputKk) {
        this.inputKk = inputKk;
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

        getLogger().debug("inputKk : " + inputKk);
        
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
        map.put("MenuSource", MenuSrc);
        map.put("cdBranch", cdBranch);
        map.put("inputKk", inputKk);
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
            modelList = new ArrayList<KtpData>();
            StringBuilder sb = new StringBuilder();


            for (Object o: contentList) {
                HashMap content = (HashMap)o;
                String contentData = null;
                getLogger().info("----content=" + content);
                if (content==null) {
                    this.addActionError("Got null content from the response");
                } else {
                    KtpData model = new KtpData();
                    model.read(content);
                    getLogger().info("------RESPON=" + model.getResponseMessage());
                    if (model.getResponseMessage()==null || "".equals(model.getResponseMessage())) {
                        if(model.getStHubKel().equalsIgnoreCase("KEPALA KELUARGA")){
                            model.setRank(1);
                        } else if("SUAMI|ISTRI".contains(model.getStHubKel())){
                            model.setRank(2);
                        } else if("ANAK".contains(model.getStHubKel())){
                            model.setRank(3);
                        } else {
                            model.setRank(4);
                        }
                        modelList.add(model);
                        Collections.sort(modelList);
                        getLogger().debug("LIST : " + modelList);
                    } else {
                        contentData = JSONUtil.serialize(model);
                        this.addActionError(model.getResponseMessage());
                        this.model = model;
                        getLogger().info("--------contentData2=" + contentData);
                        this.contentData = contentData;
                        return "inquiry";
                    }
                }
            }

            sb.append("[");
            for(KtpData d : modelList){
                if(d != null){
                    contentData = JSONUtil.serialize(d);
                    getLogger().info("--------contentData1=" + contentData);
                sb.append(contentData);
                sb.append(",");
            }
            }
            sb.deleteCharAt(sb.length()-1);
            sb.append("]");

            this.contentData = sb.toString();
            getLogger().info("contentData=" + contentData);
        } catch (JSONException ex) {
            getLogger().fatal("[FATAL] inquiry() " + ex.getMessage(), ex);
            setErrorMessage(ex.getMessage());
        } catch (Exception ex) {
            getLogger().fatal("[FATAL] inquiry() " + ex.getMessage(), ex);
            setErrorMessage(ex.getMessage());
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
     * @return the modelList
     */
    public List<KtpData> getModelList() {
        return modelList;
    }

    /**
     * @param modelList the modelList to set
     */
    public void setModelList(List<KtpData> modelList) {
        this.modelList = modelList;
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
