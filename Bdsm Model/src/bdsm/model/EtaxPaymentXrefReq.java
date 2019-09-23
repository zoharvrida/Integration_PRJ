/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bdsm.model;

/**
 *
 * @author rptuatdrsuperid
 */
@SuppressWarnings("serial")
public class EtaxPaymentXrefReq extends BaseModel{
    private String user_ref_no;
    private String request_time;
    private String service_code;
    private String channel_id;
    private String bin_no;
    private String acct_type;
    private String acct_no;
    private String amount;
    private String ccy;
    private String billing_id;    
    private String branch_code;
    private String cost_center;
    private String user_id;
    private String auth_token;
    private ETaxBillingInfo billingInfo;

    /**
     * @return the user_ref_no
     */
    
    public ETaxBillingInfo getBillingInfo() {
        return billingInfo;
    }

    /**
     * @param billingInfo the billingInfo to set
     */
    public void setBillingInfo(ETaxBillingInfo billingInfo) {
        this.billingInfo = billingInfo;
    }
    public String getUser_ref_no() {
        return user_ref_no;
    }

    /**
     * @param user_ref_no the user_ref_no to set
     */
    public void setUser_ref_no(String user_ref_no) {
        this.user_ref_no = user_ref_no;
    }

    /**
     * @return the request_time
     */
    public String getRequest_time() {
        return request_time;
    }

    /**
     * @param request_time the request_time to set
     */
    public void setRequest_time(String request_time) {
        this.request_time = request_time;
    }

    /**
     * @return the service_code
     */
    public String getService_code() {
        return service_code;
    }

    /**
     * @param service_code the service_code to set
     */
    public void setService_code(String service_code) {
        this.service_code = service_code;
    }

    /**
     * @return the channel_id
     */
    public String getChannel_id() {
        return channel_id;
    }

    /**
     * @param channel_id the channel_id to set
     */
    public void setChannel_id(String channel_id) {
        this.channel_id = channel_id;
    }

    /**
     * @return the bin_no
     */
    public String getBin_no() {
        return bin_no;
    }

    /**
     * @param bin_no the bin_no to set
     */
    public void setBin_no(String bin_no) {
        this.bin_no = bin_no;
    }

    /**
     * @return the acct_type
     */
    public String getAcct_type() {
        return acct_type;
    }

    /**
     * @param acct_type the acct_type to set
     */
    public void setAcct_type(String acct_type) {
        this.acct_type = acct_type;
    }

    /**
     * @return the acct_no
     */
    public String getAcct_no() {
        return acct_no;
    }

    /**
     * @param acct_no the acct_no to set
     */
    public void setAcct_no(String acct_no) {
        this.acct_no = acct_no;
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
     * @return the ccy
     */
    public String getCcy() {
        return ccy;
    }

    /**
     * @param ccy the ccy to set
     */
    public void setCcy(String ccy) {
        this.ccy = ccy;
    }

    /**
     * @return the billing_id
     */
    public String getBilling_id() {
        return billing_id;
    }

    /**
     * @param billing_id the billing_id to set
     */
    public void setBilling_id(String billing_id) {
        this.billing_id = billing_id;
    }

    /**
     * @return the branch_code
     */
    public String getBranch_code() {
        return branch_code;
    }

    /**
     * @param branch_code the branch_code to set
     */
    public void setBranch_code(String branch_code) {
        this.branch_code = branch_code;
    }

    /**
     * @return the cost_center
     */
    public String getCost_center() {
        return cost_center;
    }

    /**
     * @param cost_center the cost_center to set
     */
    public void setCost_center(String cost_center) {
        this.cost_center = cost_center;
    }

    /**
     * @return the user_id
     */
    public String getUser_id() {
        return user_id;
    }

    /**
     * @param user_id the user_id to set
     */
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    /**
     * @return the auth_token
     */
    public String getAuth_token() {
        return auth_token;
    }

    /**
     * @param auth_token the auth_token to set
     */
    public void setAuth_token(String auth_token) {
        this.auth_token = auth_token;
    }
    
    
    
}
