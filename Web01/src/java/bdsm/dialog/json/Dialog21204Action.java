/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.dialog.json;

import bdsm.util.BdsmUtil;
import bdsm.util.HttpUtil;
import static com.opensymphony.xwork2.Action.SUCCESS;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.struts2.json.JSONUtil;

/**
 *
 * @author SDM
 */
public class Dialog21204Action extends BaseDialogAction{

    private static final String DENOM_SEARCH = "CashDelivery_list";
    private String status;
    private String branch;
    private String currency;
    private String dateTxn;
    
    /**
     * 
     * @return
     * @throws Exception
     */
    @Override
    public List doList() throws Exception {
        this.getLogger().info("BRANCH : " + this.branch);
        this.getLogger().info("CURRENCY : " + this.currency);
        this.getLogger().info("DENOM : " + this.dateTxn);
        this.getLogger().info("STATUS : " + this.status);


        HashMap<String, String> params = new HashMap<String, String>();

        params.put("branch", this.getBranch());
        params.put("dateTxn", this.dateTxn);
        params.put("currency", this.getCurrency());
        params.put("status", this.status);
        params.put("menuName", "21204");
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
     * @return the dateTxn
     */
    public String getDateTxn() {
        return dateTxn;
    }

    /**
     * @param dateTxn the dateTxn to set
     */
    public void setDateTxn(String dateTxn) {
        this.dateTxn = dateTxn;
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
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }
    
}
