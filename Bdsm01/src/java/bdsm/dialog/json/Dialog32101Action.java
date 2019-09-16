package bdsm.dialog.json;

import bdsm.model.ETaxInquiryBillingResp;
import bdsm.model.ETaxPaymentType;
import bdsm.util.BdsmUtil;
import bdsm.util.HttpUtil;
import java.util.List;

import com.opensymphony.xwork2.ActionSupport;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.apache.struts2.json.JSONUtil;

/**
 *
 * @author user
 */
@SuppressWarnings({"serial", "rawtypes"})
public class Dialog32101Action extends BaseDialogAction {

    private static final String ETAX_CURRENCY_HOST = "ETAX_listCurrency.action";
    private static final String FCR_DATA_MASTER_HOST = "FCRDataMasterHost_list";
    private Map<String, String> paymentTypes;
    private Map<String, String> currencies;
    private Map<String, String> idTypes;
    private Map<String, String> customerTypes;
    
    private ETaxInquiryBillingResp etax;

    @SkipValidation
    public String loadInputParams() throws Exception {
        listPaymentType();
        listCurrency();
        return ActionSupport.SUCCESS;
    }
    
    @SkipValidation
    public String listPaymentType() {
        if(paymentTypes == null) {
            paymentTypes = new HashMap();
            paymentTypes.put(String.valueOf(ETaxPaymentType.CASH.getValue()), ETaxPaymentType.CASH.getDesc());
            paymentTypes.put(String.valueOf(ETaxPaymentType.DEBET_CASA.getValue()), ETaxPaymentType.DEBET_CASA.getDesc());
            paymentTypes.put(String.valueOf(ETaxPaymentType.CEK_BG.getValue()), ETaxPaymentType.CEK_BG.getDesc());
        }
        return ActionSupport.SUCCESS;
    }

    @SkipValidation
    public String listCurrency() throws Exception {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));
        this.getLogger().info("PARAM " + params);

        String result = HttpUtil.request(getBdsmHost() + ETAX_CURRENCY_HOST, params);
        this.getLogger().info(result);
        
        Map<String, Object> ret = (HashMap<String, Object>) JSONUtil.deserialize(result);
        this.getLogger().info("RESPONSE " + ret);
        List listRet = (List) ret.get("currencyList");
        currencies = new HashMap();
        if(listRet != null) {
            for(int a = 0; a < listRet.size(); a++) {
                Map mapCurr = (Map) listRet.get(a);
                currencies.put((String) mapCurr.get("currCode"), mapCurr.get("currCode") + " - " + mapCurr.get("currName"));
            }
        }
        return ActionSupport.SUCCESS;
    }

    @SkipValidation
    public String listIdTypes() throws Exception {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));
        params.put("master", "idType");
        params.put("like", "");
        this.getLogger().info("PARAM " + params);

        String result = HttpUtil.request(getBdsmHost() + FCR_DATA_MASTER_HOST, params);
        this.getLogger().info(result);
        
        Map<String, Object> ret = (HashMap<String, Object>) JSONUtil.deserialize(result);
        this.getLogger().info("RESPONSE " + ret);
        List listRet = (List) ret.get("dataList");
        idTypes = new HashMap();
        if(listRet != null) {
            Object[] listObj = listRet.toArray();
            Arrays.sort(listObj, new Comparator<Object>() {
                @Override
                public int compare(Object o1, Object o2) {
                    List list1 = (List) o1;
                    List list2 = (List) o2;
                    return (((Long) list1.get(2)).compareTo((Long) list2.get(2)));
                }
            });
            
            for(int a = 0; a < listObj.length; a++) {
                List types = (List) listRet.get(a);
                idTypes.put((String) types.get(0), (String) types.get(1));
            }
        }
        return ActionSupport.SUCCESS;
    }

    @SkipValidation
    public String listCustomerTypes() throws Exception {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));
        params.put("master", "WICCustomer");
        params.put("like", "");
        this.getLogger().info("PARAM " + params);

        String result = HttpUtil.request(getBdsmHost() + FCR_DATA_MASTER_HOST, params);
        this.getLogger().info(result);
        
        Map<String, Object> ret = (HashMap<String, Object>) JSONUtil.deserialize(result);
        this.getLogger().info("RESPONSE " + ret);
        List listRet = (List) ret.get("dataList");
        customerTypes = new HashMap();
        if(listRet != null) {
            for(int a = 0; a < listRet.size(); a++) {
                List types = (List) listRet.get(a);
                customerTypes.put(String.valueOf(types.get(0)), (String) types.get(1));
            }
        }
        return ActionSupport.SUCCESS;
    }
    
    /**
     * @return the list
     */
    public Map getPaymentTypes() {
        return this.paymentTypes;
    }

    /**
     * @param paymentTypes the list to set
     */
    public void setPaymentTypes(Map paymentTypes) {
        this.paymentTypes = paymentTypes;
    }

    public Map<String, String> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(Map<String, String> currencies) {
        this.currencies = currencies;
    }

    public Map<String, String> getIdTypes() {
        return idTypes;
    }

    public void setIdTypes(Map<String, String> idTypes) {
        this.idTypes = idTypes;
    }

    public Map<String, String> getCustomerTypes() {
        return customerTypes;
    }

    public void setCustomerTypes(Map<String, String> customerTypes) {
        this.customerTypes = customerTypes;
    }

    public ETaxInquiryBillingResp getEtax() {
        return etax;
    }

    public void setEtax(ETaxInquiryBillingResp etax) {
        this.etax = etax;
    }

    @Override
    public List doList() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
