package bdsm.web.menu32102;

import bdsm.model.BdsmEtaxPaymXref;
import bdsm.model.ETaxInquiryBillingResp;
import bdsm.model.MasterLimitEtax;
import bdsm.util.ClassConverterUtil;
import bdsm.web.InquiryEtaxAction;
import bdsm.web.ModelDrivenBaseContentAction;
import static com.opensymphony.xwork2.Action.SUCCESS;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author v00024535
 */
@SuppressWarnings("serial")
public class Menu32102Action extends ModelDrivenBaseContentAction<Object> {

    private BdsmEtaxPaymXref epv ;
    private String idMaintainedBy;
    private String idMaintainedSpv;
    private ETaxInquiryBillingResp etax;
    //private ETaxInquiryBillingResponse etax;
    private String billingId;
    private int paymentType;
    private String amount;

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

    public String doValidateLimit() {
        getLogger().info("[Begin] ValidateLimit()");
        try {
            System.out.println("ADAAAA ");
            if (isValidSession()) {
                System.out.println("MASUK VALID");
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
            InquiryEtaxAction inquiry = new InquiryEtaxAction();
            inquiry.InquiryEtax(billingId, paymentType, getServletRequest(),getServletContext());
            this.etax = inquiry.getEtax();     
            this.epv.setInqBillResp(etax);
            //this.epv.setBillInfo(etax.getBillingInfo());
            //this.epv.setCodAuthId(idMaintainedBy);
            if(this.getIdMaintainedSpv().isEmpty())
            {
            this.setIdMaintainedSpv(this.getIdMaintainedBy());            
            }            
            
            Map<String, String> requestMap = this.createParameterMapFromHTTPRequest();
            requestMap.put("methodName", "validateLimitUser");
            requestMap.put("idMaintainedBy", this.getIdMaintainedBy());
            requestMap.put("amount", this.getAmount());
            Map<String, ? extends Object> resultMap = this.callHostHTTPRequest("ETAX", "callMethod", requestMap);
           
            this.getLogger().debug("Error Code: " + getEpv().getLimitVal().getErrCode().toString());
            this.getLogger().debug("Error Desc: " + getEpv().getLimitVal().getErrDesc().toString());
            if (getEpv().getLimitVal().getErrCode().toString().equalsIgnoreCase("0000")) {
                System.out.println("MASUK DO POSTING");
                requestMap = this.createParameterMapFromHTTPRequest();
                requestMap.put("methodName", "PostingEtaxPaymentRequest");
                this.callHostHTTPRequest("ETAX", "callMethod", requestMap);
            }
        } catch (Exception e) {
            this.getLogger().debug("Error Authorize Limit User: " +getEpv().getLimitVal().getErrDesc().toString()+ e, e);
        }

        return SUCCESS;
    }

    @Override
    public String doAdd() {
        System.out.println("DO ADD " + getEtax().toString());
        doValidateLimit();
        return SUCCESS;
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

    /**
     * @return the idMaintainedBy
     */
    public String getIdMaintainedBy() {
        return idMaintainedBy;
    }

    /**
     * @param idMaintainedBy the idMaintainedBy to set
     */
    public void setIdMaintainedBy(String idMaintainedBy) {
        this.idMaintainedBy = idMaintainedBy;
    }

    /**
     * @return the amount
     */
    public String getAmount() {
        return amount;
    }

    /**
     * @param amount the amount to set
     */
    public void setAmount(String amount) {
        this.amount = amount;
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
     * @return the epv
     */
    public BdsmEtaxPaymXref getEpv() {
        return epv;
    }

    /**
     * @param epv the epv to set
     */
    public void setEpv(BdsmEtaxPaymXref epv) {
        this.epv = epv;
    }
    
    
}
