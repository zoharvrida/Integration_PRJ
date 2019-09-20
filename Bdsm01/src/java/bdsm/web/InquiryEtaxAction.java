/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.web;

import bdsm.model.ETaxInquiryBillingResp;
import bdsm.util.ClassConverterUtil;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author v00024535
 */
public class InquiryEtaxAction extends ModelDrivenBaseContentAction<Object> {

    private ETaxInquiryBillingResp etax;
    private HttpServletRequest servletRequest;
    private ServletContext servletContext;

    public void InquiryEtax(String billingId, int paymentType, HttpServletRequest servletRequest, ServletContext context) {
        this.servletRequest = servletRequest;
        this.servletContext = context;
        try {
            Map<String, String> requestMapInq = this.createParameterMapFromHTTPRequest();
            requestMapInq.put("methodName", "inquiryBilling");
            requestMapInq.put("billingId", billingId);
            requestMapInq.put("paymentType", String.valueOf(paymentType));
            Map<String, ? extends Object> resultMapInq = this.callHostHTTPRequestExtend("ETAX", "callMethod", requestMapInq, servletContext);
            Map viewDataInq = (Map) resultMapInq.get("inquiryResp");
            if (viewDataInq == null) {
                viewDataInq = new HashMap();
            }
            Map billingInfo = null;
            if (viewDataInq.containsKey("billingInfo")) {
                billingInfo = (Map) viewDataInq.get("billingInfo");
            }
            Map objData = (Map) resultMapInq.get("objData");
            this.getLogger().debug("viewData: " + viewDataInq);

            this.setEtax(new ETaxInquiryBillingResp());
            ClassConverterUtil.MapToClass(viewDataInq, this.getEtax());
        } catch (Exception er) {
            er.printStackTrace();
            this.getLogger().debug("Error Inquiry Etax: " + er, er);
        }
    }

    @Override
    public String exec() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String doAdd() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String doEdit() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String doDelete() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Object getModel() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * @return the etax
     */
    public ETaxInquiryBillingResp getEtax() {
        return etax;
    }

    /**
     * @param etax the etax to set
     */
    public void setEtax(ETaxInquiryBillingResp etax) {
        this.etax = etax;
    }

    /**
     * @return the servletRequest
     */
    public HttpServletRequest getServletRequest() {
        return servletRequest;
    }

    /**
     * @param servletRequest the servletRequest to set
     */
    public void setServletRequest(HttpServletRequest servletRequest) {
        this.servletRequest = servletRequest;
    }

    /**
     * @return the servletContext
     */
    public ServletContext getServletContext() {
        return servletContext;
    }

    /**
     * @param servletContext the servletContext to set
     */
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}
