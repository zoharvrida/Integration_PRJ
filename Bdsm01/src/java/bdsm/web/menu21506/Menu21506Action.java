/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.web.menu21506;

import bdsm.util.BdsmUtil;
import bdsm.util.HttpUtil;
import bdsm.web.BaseContentAction;
import bdsm.web.Constant;
import com.opensymphony.xwork2.ActionSupport;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;

/**
 *
 * @author SDM
 */
public class Menu21506Action extends BaseContentAction{

    private List<Map<String, Object>> denomList;
    private static final String CALLMETHOD_ACTION = "CashDelivery_callMethod.action";
    private static final String DELIVERY_REQUEST = "addRequest";
    private static final String DELIVERY_GETBATCH = "getTxn";
    private String branch;
    private String currency;
    private String denomId;
    private String txnId;
    private String txnBatch;
    private String dateTxn;

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
        this.getLogger().info("BRANCH : " + this.getBranch());
        this.getLogger().info("DENOM : " + this.denomId);
        
        Map<String, String> map = new HashMap<String, String>();
        HashMap resultMap;
        String reqResult = null;
        String errorCode = null;
        String result = null;

        map.put("branch", this.getBranch());
        map.put("txnId", this.txnId);
        map.put("currency", this.getCurrency());
        map.put("methodName", DELIVERY_REQUEST);
        map.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));

        result = HttpUtil.request(getBdsmHost() + CALLMETHOD_ACTION, map);

        try {
            resultMap = (HashMap) JSONUtil.deserialize(result);
            reqResult = (String) resultMap.get("jsonStatus");
            errorCode = (String) resultMap.get("errorCode");
        } catch (JSONException ex) {
            getLogger().fatal(ex, ex);
        } catch (NullPointerException e) {
            getLogger().info(" NULL P : " + e.getMessage());
        }

        if (reqResult.equals(ActionSupport.ERROR)) {
            addActionError(getText(errorCode));
        } else if (reqResult.equals(ActionSupport.SUCCESS)) {
            addActionMessage(getText(errorCode));
        }
        return ActionSupport.SUCCESS;
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
    
    /**
     * 
     * @return
     */
    @SkipValidation
    public String getTrxBatch() {
        String result = null;
        getLogger().debug("BRANCH :" + branch);

        Map<String, String> map = new HashMap<String, String>();
        HashMap resultMap;
        String reqResult = null;
        String errorCode = null;

        try {
            if (isValidSession()) {

                map.put("branch", this.getBranch());
                map.put("userId", Constant.C_IDUSER);
                map.put("dateTxn", dateTxn);
                map.put("txnType", "1");
                map.put("txnStatus", "1");
                map.put("methodName", DELIVERY_GETBATCH);
                map.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));

                result = HttpUtil.request(getBdsmHost() + CALLMETHOD_ACTION, map);

                try {
                    resultMap = (HashMap) JSONUtil.deserialize(result);
                    reqResult = (String) resultMap.get("jsonStatus");
                    errorCode = (String) resultMap.get("errorCode");
                } catch (JSONException ex) {
                    getLogger().fatal(ex, ex);
                } catch (NullPointerException e) {
                    getLogger().info(" NULL P : " + e.getMessage());
                }
                return "txnBatch";
            } else {
                return logout();
            }
        } catch (NullPointerException e) {
            getLogger().debug("null Pointer" + e);
            txnId ="";
            return "txnBatch";
        } catch (Throwable e) {
            getLogger().fatal(e, e);
            return ActionSupport.ERROR;
        } finally {
            getLogger().debug("[ End ] TXNID BATCH()");
        }
        //return ActionSupport.SUCCESS;
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
     * @return the denomId
     */
    public String getDenomId() {
        return denomId;
    }

    /**
     * @param denomId the denomId to set
     */
    public void setDenomId(String denomId) {
        this.denomId = denomId;
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

    /**
     * @return the txnBatch
     */
    public String getTxnBatch() {
        return txnBatch;
    }

    /**
     * @param txnBatch the txnBatch to set
     */
    public void setTxnBatch(String txnBatch) {
        this.txnBatch = txnBatch;
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
}
