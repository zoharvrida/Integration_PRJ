/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.web.request;

import bdsm.util.BdsmUtil;
import bdsm.util.HttpUtil;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;

/**
 *
 * @author 00110310
 */
public class RequestService {
    private static final String METHOD = "methodName";
    private String CALLMETHOD_ACTION;
    private String ACTION_GET_DATA;
    private HttpServletRequest servletRequest;
    private Map<String, Object> session;
    private String bdsmHost;
    private String tokenKey;
    private String tzToken;
    private String reqResult;
    private String errorCode;

    /**
     * 
     * @return
     */
    protected Logger getLogger() {
        return Logger.getLogger(this.getClass().getName());
    }
    
    /**
     * 
     * @param servletRequest
     * @param session
     * @param bdsmHost
     * @param tokenKey
     * @param tzToken
     * @param callMethod
     * @param methodName
     */
    public RequestService(HttpServletRequest servletRequest, Map<String, Object> session, String bdsmHost, String tokenKey, String tzToken, String callMethod, String methodName) {
        this.servletRequest = servletRequest;
        this.session = session;
        this.bdsmHost = bdsmHost;
        this.tokenKey = tokenKey;
        this.tzToken = tzToken;
        this.CALLMETHOD_ACTION = callMethod;
        this.ACTION_GET_DATA = methodName;
    }
    
    /**
     * 
     * @param session
     * @param bdsmHost
     * @param tokenKey
     * @param tzToken
     * @param callMethod
     * @param methodName
     */
    public RequestService(Map<String, Object> session, String bdsmHost, String tokenKey, String tzToken, String callMethod, String methodName) {
        this.session = session;
        this.bdsmHost = bdsmHost;
        this.tokenKey = tokenKey;
        this.tzToken = tzToken;
        this.CALLMETHOD_ACTION = callMethod;
        this.ACTION_GET_DATA = methodName;
    }
    /**
     * 
     * @param values
     * @param mapName
     * @param typReq
     * @return
     */
    public Map getRequest(Map values, String mapName, String typReq){
        HashMap mapModel = null;
        try {
            //if (isValidSession()) {
                Map map = new HashMap();
                String result;
                HashMap<Object,Object> resultMap = new HashMap();
                
                values.put("token", BdsmUtil.generateToken(this.tokenKey, this.tzToken));
                getLogger().info("Param Message:" + values);
                
                if (this.CALLMETHOD_ACTION == null){
                    result = HttpUtil.request(this.bdsmHost + ACTION_GET_DATA, values);
                } else {
                    if(values.get(METHOD) == null){
                        return mapModel;
                    } else {
                        result = HttpUtil.request(this.bdsmHost + CALLMETHOD_ACTION, values);
                    }
                }
                //result = HttpUtil.request(getBdsmHost() + ACTION_GET_DATA, map);

                try {
                    resultMap = (HashMap) JSONUtil.deserialize(result);
                    setReqResult((String) resultMap.get("jsonStatus"));
                    setErrorCode((String) resultMap.get("errorCode"));
                    mapModel = (HashMap) resultMap.get(mapName);
                    
                    getLogger().info("JSON Message:" + resultMap);
                    if ("ALL".equalsIgnoreCase(typReq)) {
                        return resultMap; 
                    } else {
                        return mapModel;
                    }
                } catch (JSONException ex) {
                    getLogger().fatal(ex, ex);
                } catch (Exception e) {
                    getLogger().fatal(e, e);
                }
                return mapModel;
            //} else {
            //    return logout();
            //}
        } catch (Throwable e) {
            getLogger().fatal(e, e);
        } finally {
            getLogger().info("[ End ] getRequest()");
        }
        return mapModel;
    }

    /**
     * @return the reqResult
     */
    public String getReqResult() {
        return reqResult;
    }

    /**
     * @param reqResult the reqResult to set
     */
    public void setReqResult(String reqResult) {
        this.reqResult = reqResult;
    }

    /**
     * @return the errorCode
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * @param errorCode the errorCode to set
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
