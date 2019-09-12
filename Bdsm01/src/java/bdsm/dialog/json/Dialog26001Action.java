package bdsm.dialog.json;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.json.JSONUtil;

import bdsm.dialog.json.BaseDialogAction;
import bdsm.util.BdsmUtil;
import bdsm.util.HttpUtil;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author bdsm
 */
public class Dialog26001Action extends BaseDialogAction {

    private static final String EXISTING_SEARCH_HOST = "CustomerSearchHost_list";
    private static final String CUSTOMER_SEARCH_HOST = "CustomerSearchHost_customerSearch.action";
    private static final DateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy");
    private String customerName;
    private String dateofBirth;
    private String noKartuIden;
    private String cifNo;
    private String flagsCIF;

    /**
     * 
     * @return
     * @throws Exception
     */
    @Override
    public List doList() throws Exception{
        this.getLogger().info("customerName : " + this.getCustomerName());
        this.getLogger().info("dateofBirth : " + this.getDateofBirth());
        this.getLogger().info("cifNO : " + this.cifNo);

        String result = null;
        HashMap<String, String> params = new HashMap<String, String>();
        if (this.cifNo == null) {
        params.put("customerName", this.getCustomerName());
        try {
            params.put("dateofBirth", this.getDateofBirth());
        } catch (Exception ex) {
            getLogger().info("Parse Date "+ ex,ex);
        }
        } else {
            params.put("cifNo", this.cifNo);
        }
        
        params.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));

        if(this.cifNo == null){
           result = HttpUtil.request(getBdsmHost() + CUSTOMER_SEARCH_HOST, params);
        } else {
           params.put("flagsCIF","EXISTING"); 
           result = HttpUtil.request(getBdsmHost() + EXISTING_SEARCH_HOST, params); 
        }
        
        this.getLogger().info(result);
        this.getLogger().info("RESULT "+ result);


        Map<String, Object> ret = (HashMap<String, Object>) JSONUtil.deserialize(result);
        this.getLogger().info("Ret "+ ret);
        List listRet = (List) ret.get("modelList");
        String statusRet = (String) ret.get("jsonStatus");
        addActionError(statusRet);

        return listRet;
    }

    /**
     * @return the customerName
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * @param customerName the customerName to set
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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

    /**
     * @return the dateofBirth
     */
    public String getDateofBirth() {
        return dateofBirth;
    }

    /**
     * @param dateofBirth the dateofBirth to set
     */
    public void setDateofBirth(String dateofBirth) {
        this.dateofBirth = dateofBirth;
    }

    /**
     * @return the cifNo
     */
    public String getCifNo() {
        return cifNo;
    }
   
    /**
     * @param cifNo the cifNo to set
     */
    public void setCifNo(String cifNo) {
        this.cifNo = cifNo;
    }

    /**
     * @return the flagsCIF
     */
    public String getFlagsCIF() {
        return flagsCIF;
    }

    /**
     * @param flagsCIF the flagsCIF to set
     */
    public void setFlagsCIF(String flagsCIF) {
        this.flagsCIF = flagsCIF;
    }
}
