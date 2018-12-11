/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.dialog.json;

import bdsm.util.BdsmUtil;
import bdsm.util.HttpUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.struts2.json.JSONUtil;

/**
 *
 * @author v00022309
 */
public class Dialog29201TabGridAction extends BaseDialogAction {
        private static final String LIST_CURRENCYCODE = "MCRRate_list.action";
        private String currencyCode;
        private String valueDate;

        /**
         * 
         * @return
         * @throws Exception
         */
        public List doList() throws Exception {
        getLogger().info("[BEGIN]");
        getLogger().debug("currencyCode = " + getCurrencyCode());
		getLogger().debug("valueDate = " + getValueDate());
        
		Map<String, String> map = new HashMap<String, String>();
		map.put("model.currencyCode", getCurrencyCode());
        map.put("model.valueDate", getValueDate());
		map.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));
        
        getLogger().info("[TokenKey] :" + getTokenKey());
        getLogger().info("[TzToken] :" + getTzToken());
   
        getLogger().info("[END]");
        getLogger().debug("currencyCode = " + getCurrencyCode());
		getLogger().debug("valueDate = " + getValueDate());
        
        getLogger().info("List Currency :" + LIST_CURRENCYCODE);
		
		String result = HttpUtil.request(getBdsmHost() + LIST_CURRENCYCODE , map);
		getLogger().info("Results : " + result);

        Map<String, Object> ret = (HashMap<String, Object>)JSONUtil.deserialize(result);
        List<Object[]> listRet = (List<Object[]>)ret.get("modelList");
        String statusRet = (String)ret.get("jsonStatus");
        addActionError(statusRet);

        return listRet;
    }

    /**
     * @return the currencyCode
     */
    public String getCurrencyCode() {
        return currencyCode;
    }

    /**
     * @param currencyCode the currencyCode to set
     */
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    /**
     * @return the valueDate
     */
    public String getValueDate() {
        return valueDate;
    }

    /**
     * @param valueDate the valueDate to set
     */
    public void setValueDate(String valueDate) {
        this.valueDate = valueDate;
    }

}
