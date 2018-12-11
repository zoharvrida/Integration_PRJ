/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.web;

import bdsm.web.request.RequestService;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.collections.map.HashedMap;
import org.apache.struts2.interceptor.validation.SkipValidation;

/**
 *
 * @author v00022309
 */
public class MCRCalcBeforeServices extends BaseContentAction {
    private static final String CALLMETHOD_ACTION = "MCRCalcUSDequivalent_callMethod.action";
    private static final String GET_VALUE = "takeGetBuyRate";
    private static final String GET_RATE = "takeGetRateDestiation";
//    private static final String GET_CALC = "takeCalcEquivalAmount";
    
    private HttpServletRequest servletRequest;
    private Map<String, Object> session;
    private String bdsmHost;
    private String tokenKey;
    private String tzToken;
    private String reqResult;
    private String errorCode;

    /**
     * 
     * @param session
     * @param bdsmHost
     * @param tokenKey
     * @param tzToken
     * @param servletRequest
     */
    public MCRCalcBeforeServices(Map session, String bdsmHost, String tokenKey, String tzToken,HttpServletRequest servletRequest) {
		this.bdsmHost = bdsmHost;
		this.session = session;
		this.tokenKey = tokenKey;
		this.tzToken =tzToken;
	}
        
    /**
     * 
     * @param codCcy1
     * @param amtTxn
     * @return
     */
    @SkipValidation
    public Map getBuyRateValue(String codCcy1, String amtTxn){
        Map request = new HashedMap();
        Map returns = new HashedMap();
        request.put("methodName", GET_VALUE);
        request.put("codCcy1", codCcy1);
        request.put("amtTxn", amtTxn);
        RequestService srv = new RequestService(servletRequest, session, bdsmHost, tokenKey, tzToken, CALLMETHOD_ACTION, GET_VALUE);
        returns = srv.getRequest(request, "","ALL");
        setErrorCode(srv.getErrorCode());
        return returns;
    }
    
    /**
     * 
     * @param currencyCode
     * @return
     */
    @SkipValidation
    public Map getRateDestination(String currencyCode){
        Map request = new HashedMap();
        Map returns = new HashedMap();
        request.put("methodName", GET_RATE);
        request.put("currencyCode", currencyCode);
        RequestService srv = new RequestService(servletRequest, session, bdsmHost, tokenKey, tzToken, CALLMETHOD_ACTION, GET_RATE);
        returns = srv.getRequest(request, "Map","ALL");
        setErrorCode(srv.getErrorCode());
        return returns;
    }
    
//    @SkipValidation
//    public Map getUSDEquivalent(String codCcy1, Integer amtTxn){
//        Map request = new HashedMap();
//        Map returns = new HashedMap();
//        request.put("methodName", GET_CALC);
//        request.put("ccyName", codCcy1);
//        request.put("amtTxn", amtTxn);
//        RequestService srv = new RequestService(servletRequest, session, bdsmHost, tokenKey, tzToken, CALLMETHOD_ACTION, GET_CALC);
//        returns = srv.getRequest(request, "model", "ALL");
//        setErrorCode(srv.getErrorCode());
//        return returns;
//    }

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
