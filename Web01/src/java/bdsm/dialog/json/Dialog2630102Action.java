package bdsm.dialog.json;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.json.JSONUtil;

import bdsm.dialog.json.BaseDialogAction;
import bdsm.util.BdsmUtil;
import bdsm.util.HttpUtil;
import java.util.Date;

/**
 * 
 * @author bdsm
 */
public class Dialog2630102Action extends BaseDialogAction {

    private static final String CUSTOMER_SEARCH_HOST = "CustomerSearchHost_customerSearch.action";
    private String customerName;
    private Date dateofBirth;
    private String noKartuIden;

    /**
     * 
     * @return
     * @throws Exception
     */
    @Override
    public List doList() throws Exception {
        this.getLogger().info("cust Name : " + this.getCustomerName());
        this.getLogger().info("nik : " + this.getNoKartuIden());


this.getLogger().info(">>>>> KTP 1");

        HashMap<String, String> params = new HashMap<String, String>();
this.getLogger().info(">>>>> KTP 2");
        params.put("noKartuIden", this.getNoKartuIden());
        params.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));
this.getLogger().info(">>>>> KTP 3");
this.getLogger().info("PARAM "+ params);

        String result = HttpUtil.request(getBdsmHost() + CUSTOMER_SEARCH_HOST, params);
        this.getLogger().info(result);


        Map<String, Object> ret = (HashMap<String, Object>) JSONUtil.deserialize(result);
        List listRet = (List) ret.get("modelList");
        String statusRet = (String) ret.get("jsonStatus");
        addActionError(statusRet);

        return listRet;
    }

    /**
     * 
     * @param customerName
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * @return the dateofBirth
     */
    public Date getDateofBirth() {
        return dateofBirth;
    }

    /**
     * @param dateofBirth the dateofBirth to set
     */
    public void setDateofBirth(Date dateofBirth) {
        this.dateofBirth = dateofBirth;
    }

    /**
     * @return the customerName
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * @return the noKartuIden
     */
    public String getNoKartuIden() {
        return noKartuIden;
    }

    /**
     * @param noKartuIden the noKartuIden to set
     */
    public void setNoKartuIden(String noKartuIden) {
        this.noKartuIden = noKartuIden;
    }
}
