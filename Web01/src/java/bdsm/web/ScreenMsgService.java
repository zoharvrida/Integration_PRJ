/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.web;

import bdsm.web.request.RequestService;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;

/**
 *
 * @author 00110310
 */
public class ScreenMsgService {
    private static final String ACTION_GET_DATA = "screenMsg_get.action";
    private static final String ACTION_GET_MENU = "menuRed_get.action";
    private String tag;
    private HttpServletRequest servletRequest;
    private Map<String, Object> session;
    private String bdsmHost;
    private String tokenKey;
    private String tzToken;
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
     */
    public ScreenMsgService(HttpServletRequest servletRequest, Map<String, Object> session, String bdsmHost, String tokenKey, String tzToken) {
        this.servletRequest = servletRequest;
        this.session = session;
        this.bdsmHost = bdsmHost;
        this.tokenKey = tokenKey;
        this.tzToken = tzToken;
    }
    
    /**
     * 
     * @param tag
     * @return
     */
    public Map getMessage(String tag){
        Map request = new HashedMap();
        Map returns = new HashedMap();
        request.put("tag", tag);
        RequestService srv = new RequestService(servletRequest, session, bdsmHost, tokenKey, tzToken, null, ACTION_GET_DATA);
        returns = srv.getRequest(request, "model","ALL");
        setErrorCode(srv.getErrorCode());
        return returns;
    }
    
    /**
     * 
     * @param idMenu
     * @param alias
     * @return
     */
    public Map getMenuRed(String idMenu, String alias){
        Map request = new HashedMap();
        Map returns = new HashedMap();
        request.put("idMenu", idMenu);
        request.put("alias", alias);
        RequestService srv = new RequestService(servletRequest, session, bdsmHost, tokenKey, tzToken, null, ACTION_GET_MENU);
        returns = srv.getRequest(request, "model","ALL");
        setErrorCode(srv.getErrorCode());
        return returns;
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
