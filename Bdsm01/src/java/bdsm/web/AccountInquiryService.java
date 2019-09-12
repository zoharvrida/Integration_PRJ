/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.web;

import bdsm.web.request.RequestService;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.collections.map.HashedMap;

/**
 *
 * @author 00110310
 */
public class AccountInquiryService {
    private static final String CHREQUEST = "FCRChAcctMastHost_get.action";
    private HttpServletRequest servletRequest;
    private Map<String, Object> session;
    private String bdsmHost;
    private String tokenKey;
    private String tzToken;
    private String errorCode;

    /**
     * 
     * @param servletRequest
     * @param session
     * @param bdsmHost
     * @param tokenKey
     * @param tzToken
     */
    public AccountInquiryService(HttpServletRequest servletRequest, Map<String, Object> session, String bdsmHost, String tokenKey, String tzToken) {
        this.servletRequest = servletRequest;
        this.session = session;
        this.bdsmHost = bdsmHost;
        this.tokenKey = tokenKey;
        this.tzToken = tzToken;
    }
    
    /**
     * 
     * @param acctNo
     * @return
     */
    public Map getAccount(String acctNo){
        Map request = new HashedMap();
        Map returns = new HashedMap();
        request.put("codAcctNo", acctNo);
        RequestService srv = new RequestService(servletRequest, session, bdsmHost, tokenKey, tzToken, null, CHREQUEST);
        returns = srv.getRequest(request, "model", "");
        setErrorCode(srv.getErrorCode());
        return returns;
    }
    /**
     * 
     * @return
     */
    public String getErrorCode() {
        return errorCode;
    }
    /**
     * 
     * @param errorCode
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
