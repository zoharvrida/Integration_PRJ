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
public class DateInquiryService {
    private static final String ACTION_GET_DATE = "date_get.action";
    private static final String ACTION_GET_CALL = "date_callMethod.action";
    private static final String METHOD_CALL_LAST = "getMonthRange";
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
    public DateInquiryService(HttpServletRequest servletRequest, Map<String, Object> session, String bdsmHost, String tokenKey, String tzToken) {
        this.servletRequest = servletRequest;
        this.session = session;
        this.bdsmHost = bdsmHost;
        this.tokenKey = tokenKey;
        this.tzToken = tzToken;
    }
    
    /**
     * 
     * @param session
     * @param bdsmHost
     * @param tokenKey
     * @param tzToken
     */
    public DateInquiryService(Map<String, Object> session, String bdsmHost, String tokenKey, String tzToken) {
        this.session = session;
        this.bdsmHost = bdsmHost;
        this.tokenKey = tokenKey;
        this.tzToken = tzToken;
    }
    
    /**
     * 
     * @return
     */
    public Map getBussDate(){
        Map request = new HashedMap();
        Map returns = new HashedMap();
        RequestService srv = new RequestService(servletRequest, session, bdsmHost, tokenKey, tzToken, null, ACTION_GET_DATE);
        returns = srv.getRequest(request, "model","ALL");
        setErrorCode(srv.getErrorCode());
        return returns;
    }

    /**
     * 
     * @param flag
     * @return
     */
    public Map getRangeDate(String flag){
        Map request = new HashedMap();
        Map returns = new HashedMap();
        request.put("flag", flag);
        request.put("methodName", METHOD_CALL_LAST);
        request.put("useTransaction", "false");
        RequestService srv = new RequestService(session, bdsmHost, tokenKey, tzToken, ACTION_GET_CALL, METHOD_CALL_LAST);
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
