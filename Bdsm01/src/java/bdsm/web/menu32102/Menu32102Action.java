package bdsm.web.menu32102;

import bdsm.web.ModelDrivenBaseContentAction;
import bdsm.model.BdsmEtaxPaymXref;
import bdsm.model.ETaxInquiryBillingResp;
import bdsm.web.InquiryEtaxAction;
import java.util.Map;

/**
 *
 * @author v00024535
 */
@SuppressWarnings("serial")
public class Menu32102Action extends ModelDrivenBaseContentAction<Object> {

    private BdsmEtaxPaymXref mdp = new BdsmEtaxPaymXref();
    private String codAuthid;
    private String idMaintainedSpv;
    private ETaxInquiryBillingResp etax;
    private String billingId;
    private int paymentType;

    public Menu32102Action() {
    }

    public final String doPost() {
        getLogger().info("[Begin] doPostTransaction()");
        try {
            if (isValidSession()) {
                return this.doPostTrx_();
            } else {
                return logout();
            }

        } catch (Throwable e) {
            this.getLogger().fatal(e, e);
            return ERROR;
        } finally {
            this.getLogger().info("[ End ] doPostTransaction()");
        }
    }

    private final String doPostTrx_() {

        return SUCCESS;
    }

    public final String doValidateLimit() {
        getLogger().info("[Begin] ValidateLimit()");
        try {
            if (isValidSession()) {
                return this.ValidateLimit_();
            } else {
                return logout();
            }

        } catch (Throwable e) {
            this.getLogger().fatal(e, e);
            return ERROR;
        } finally {
            this.getLogger().info("[ End ] ValidateLimit()");
        }
    }

    private String ValidateLimit_() {
        try {
            System.out.println("bill  "+billingId);
            System.out.println("bill 2 "+paymentType);
            InquiryEtaxAction inquiry = new InquiryEtaxAction();
            inquiry.InquiryEtax(billingId, paymentType, getServletRequest(),getServletContext());
            System.out.println("asfa "+inquiry.getEtax());
            this.etax = inquiry.getEtax();
            
            Map<String, String> requestMap = this.createParameterMapFromHTTPRequest();
            requestMap.put("methodName", "validateLimitUser");
            requestMap.put("codAuthid", codAuthid);
            requestMap.put("taxAmount", String.valueOf(this.etax.getAmount()));
            System.out.println("ETAX 2 " + this.etax);

            Map<String, ? extends Object> resultMap = this.callHostHTTPRequest("ETAX", "callMethod", requestMap);
            Map viewData = (Map) resultMap.get("mdpResp");
            System.out.println("RESPONSE " + resultMap.get("mdpResp"));
            System.out.println(viewData.get("errCode"));
            System.out.println(viewData.get("errDesc"));
            this.getLogger().debug("Error Code: " + viewData.get("errCode"));
            this.getLogger().debug("Error Desc: " + viewData.get("errDesc"));
            if (viewData.get("errDesc").toString().equalsIgnoreCase("SUCCESS")) {
                System.out.println("MASUK DO POSTING");
            }
        } catch (Exception e) {
            this.getLogger().debug("Error Authorize Limit User: " + e, e);
        }

        return SUCCESS;
    }

    @Override
    public String doAdd() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String exec() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String doEdit() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String doDelete() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object getModel() {
        return null;
    }

    /**
     * @return the idMaintainedSpv
     */
    public String getIdMaintainedSpv() {
        return idMaintainedSpv;
    }

    /**
     * @param idMaintainedSpv the idMaintainedSpv to set
     */
    public void setIdMaintainedSpv(String idMaintainedSpv) {
        this.idMaintainedSpv = idMaintainedSpv;
    }

    /**
     * @return the codAuthid
     */
    public String getCodAuthid() {
        return codAuthid;
    }

    /**
     * @param codAuthid the codAuthid to set
     */
    public void setCodAuthid(String codAuthid) {
        this.codAuthid = codAuthid;
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
     * @return the billingId
     */
    public String getBillingId() {
        return billingId;
    }

    /**
     * @param billingId the billingId to set
     */
    public void setBillingId(String billingId) {
        this.billingId = billingId;
    }

    /**
     * @return the paymentType
     */
    public int getPaymentType() {
        return paymentType;
    }

    /**
     * @param paymentType the paymentType to set
     */
    public void setPaymentType(int paymentType) {
        this.paymentType = paymentType;
    }
}
