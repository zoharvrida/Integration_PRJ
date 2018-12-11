/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.web;

import bdsm.util.BdsmUtil;
import bdsm.util.HttpUtil;
import bdsm.web.request.RequestService;
import com.opensymphony.xwork2.ActionSupport;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.collections.map.HashedMap;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;

/**
 *
 * @author bdsm
 */
public class ValasMidRateService extends BaseContentAction {

    private static final String CALLMETHOD_ACTION = "valasMidRate_callMethod.action";
    private static final String GET_DATE = "doCalcDatMidrate";
    private static final String GET_VALUE = "doGetMidrate";
    private static final String GET_CALC = "doCalcMidRate";
    private String dateTXN;
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
    public ValasMidRateService(Map session, String bdsmHost, String tokenKey, String tzToken,HttpServletRequest servletRequest) {
		this.bdsmHost = bdsmHost;
		this.session = session;
		this.tokenKey = tokenKey;
		this.tzToken =tzToken;
	}
    /**
     * 
     * @param ccyName
     * @return
     */
    @SkipValidation
    public Map getMidValue(String ccyName){
        Map request = new HashedMap();
        Map returns = new HashedMap();
        request.put("methodName", GET_VALUE);
        request.put("ccyName", ccyName);
        RequestService srv = new RequestService(servletRequest, session, bdsmHost, tokenKey, tzToken, CALLMETHOD_ACTION, GET_VALUE);
        returns = srv.getRequest(request, "modelClose","");
        setErrorCode(srv.getErrorCode());
        return returns;
    }
    /**
     * 
     * @param ccyAmount
     * @param ccyName
     * @return
     */
    @SkipValidation
    public Map getMidCalc(String ccyAmount, String ccyName){
        Map request = new HashedMap();
        Map returns = new HashedMap();
        request.put("methodName", GET_CALC);
        request.put("ccyName", ccyName);
        request.put("amtTxn", ccyAmount);
        RequestService srv = new RequestService(servletRequest, session, bdsmHost, tokenKey, tzToken, CALLMETHOD_ACTION, GET_CALC);
        returns = srv.getRequest(request, "modelClose", "ALL");
        setErrorCode(srv.getErrorCode());
        return returns;
    }
    /**
     * 
     * @param dateTXN
     * @return
     */
    @SkipValidation
    public Map getMidRate(String dateTXN){
        Map request = new HashedMap();
        Map returns = new HashedMap();
        request.put("methodName", GET_DATE);
        request.put("dateTXN", dateTXN);
        RequestService srv = new RequestService(servletRequest, session, bdsmHost, tokenKey, tzToken, CALLMETHOD_ACTION, GET_DATE);
        returns = srv.getRequest(request, "modelClose","");
        setErrorCode(srv.getErrorCode());
        return returns;
    }
    /**
     * 
     * @param dateTXN
     * @return
     */
    @SkipValidation
    public String getMidRate2(String dateTXN) {
        getLogger().info("[Begin] getDate MIDRATE()");
        try {
            //if (isValidSession()) {
                Map<String, String> map = new HashMap<String, String>();
                String result;
                HashMap resultMap;
                HashMap mapModel;
                String reqResult = null;
                String errorCode = null;

                getLogger().info("DATE TXN :" + dateTXN);
                map.put("methodName", GET_DATE);
                map.put("dateTXN", dateTXN);
                map.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));

                result = HttpUtil.request(this.getBdsmHost() + CALLMETHOD_ACTION, map);
                //result = HttpUtil.request(getBdsmHost() + ACTION_GET_DATA, map);

                try {
                    resultMap = (HashMap) JSONUtil.deserialize(result);
                    reqResult = (String) resultMap.get("jsonStatus");
                    errorCode = (String) resultMap.get("errorCode");
                    mapModel = (HashMap) resultMap.get("modelClose");

                    if (mapModel != null) {
                        dateTXN = (String) resultMap.get("dateTXN");
                        dateTXN = dateTXN.replace("T", " ");
                    }
                } catch (JSONException ex) {
                    getLogger().fatal(ex, ex);
                    return ActionSupport.ERROR;
                } catch (Exception e) {
                    getLogger().fatal(e, e);
                    return ActionSupport.ERROR;
                }

                return dateTXN;
            //} else {
            //    return logout();
            //}
        } catch (Throwable e) {
            getLogger().fatal(e, e);
            return ActionSupport.ERROR;
        } finally {
            getLogger().info("[ End ] getData()");
        }
    }

    /**
     * @return the dateTXN
     */
    public String getDateTXN() {
        return dateTXN;
    }

    /**
     * @param dateTXN the dateTXN to set
     */
    public void setDateTXN(String dateTXN) {
        this.dateTXN = dateTXN;
    }

    /**
     * 
     * @return
     */
    @Override
    public String exec() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * 
     * @return
     */
    @Override
    public String doAdd() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * 
     * @return
     */
    @Override
    public String doEdit() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * 
     * @return
     */
    @Override
    public String doDelete() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    @Override
    public HttpServletRequest getServletRequest() {
        return servletRequest;
    }
    /**
     * 
     * @return
     */
    @Override
    public String getBdsmHost() {
        return bdsmHost;
    }
    /**
     * 
     * @return
     */
    @Override
    public String getTokenKey() {
        return tokenKey;
    }
    /**
     * 
     * @return
     */
    @Override
    public String getTzToken() {
        return tzToken;
    }
    /**
     * 
     * @return
     */
    public String getReqResult() {
        return reqResult;
    }
    /**
     * 
     * @param reqResult
     */
    public void setReqResult(String reqResult) {
        this.reqResult = reqResult;
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
