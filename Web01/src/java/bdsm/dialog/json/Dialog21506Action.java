/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.dialog.json;

import static bdsm.web.Constant.C_WEB_SESSION_ID;

import bdsm.util.BdsmUtil;
import bdsm.util.HttpUtil;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.struts2.json.JSONUtil;

/**
 *
 * @author SDM
 */
public class Dialog21506Action extends BaseDialogAction {

    private static final String DENOM_SEARCH = "CashDelivery_list";
    private String branch;
    private String currency;
    private String denomId;
    private String txnId;
    
    /**
     * 
     * @return
     * @throws Exception
     */
    @Override
    public List doList() throws Exception {
        this.getLogger().info("BRANCH : " + this.getBranch());
        this.getLogger().info("DENOM : " + this.denomId);
        
        
        HashMap<String, String> params = new HashMap<String, String>();

        params.put("branch", this.getBranch());
        params.put("vendorID", this.denomId);
        params.put("currency", this.currency);
        params.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));

        this.getLogger().info("PARAM " + params);

        String result = HttpUtil.request(getBdsmHost() + DENOM_SEARCH, params);
        this.getLogger().info(result);


        Map<String, Object> ret = (HashMap<String, Object>) JSONUtil.deserialize(result);
        List listRet = (List) ret.get("modelList");
        String statusRet = (String) ret.get("jsonStatus");
        addActionError(statusRet);

        return listRet;
    }

    /**
     * 
     * @return
     */
    public String doChange() {
        this.getLogger().info("[Begin] doChange()");
        this.getLogger().info("BRANCH : " + this.getBranch());
        this.getLogger().info("DENOM : " + this.denomId);
        this.getLogger().info("BatchID : " + this.getTxnId());
        
        try {
            if (isValidSession()) {
                
                return SUCCESS;
            } else {
                return logout();
            }
        } finally {
            this.getLogger().info("[ End ] doChange()");
        }
    }

    /**
     * @return the branch
     */
    public String getBranch() {
        return branch;
    }

    /**
     * @param branch the branch to set
     */
    public void setBranch(String branch) {
        this.branch = branch;
    }

    /**
     * @return the currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * @param currency the currency to set
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * @return the vendorID
     */
    public String getVendorID() {
        return denomId;
    }

    /**
     * @param vendorID the vendorID to set
     */
    public void setVendorID(String vendorID) {
        this.denomId = vendorID;
    }

    /**
     * @return the txnId
     */
    public String getTxnId() {
        return txnId;
    }

    /**
     * @param txnId the txnId to set
     */
    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }
}
