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
public class ProductInquiryService {
    private static final String PRODREQUEST = "prodInq_get.action";
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
    public ProductInquiryService(HttpServletRequest servletRequest, Map<String, Object> session, String bdsmHost, String tokenKey, String tzToken) {
        this.servletRequest = servletRequest;
        this.session = session;
        this.bdsmHost = bdsmHost;
        this.tokenKey = tokenKey;
        this.tzToken = tzToken;
    }

    /**
     * 
     * @param product
     * @return
     */
    public Map getProduct(String product){
        Map request = new HashedMap();
        Map returns = new HashedMap();
        request.put("prodNum", product);
        RequestService srv = new RequestService(servletRequest, session, bdsmHost, tokenKey, tzToken, null, PRODREQUEST);
        returns = srv.getRequest(request, "model","");
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
