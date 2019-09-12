/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.web.menu29201;

import bdsm.util.BdsmUtil;
import bdsm.util.HttpUtil;
import bdsm.web.BaseContentAction;
import java.util.HashMap;
import java.util.Map;
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;

/**
 *
 * @author v00022309
 */
public class Menu29201Action extends BaseContentAction {
    private static final String LIST_CURRENCYCODE = "CurrMaster_list.action";
    private String currencyCode;
    private String valueDate;

    /**
     * 
     * @return
     */
    @Override
    public String exec() {
        getLogger().info("[Begin proses]");
        
        Map<String, String> map = new HashMap<String, String>();
		String result;
		HashMap<String, ? extends Object> resultMap;
		String reqResult = null;
		
        getLogger().info("currencyCode nya :" + getCurrencyCode());
        getLogger().info("valueDate nya :" + getValueDate());
        //		this.getLogger().info("currencyCode : " + this.currencyCode);
        //		this.getLogger().info("valueDate : " + this. );
        map.put("model.currencyCode", getCurrencyCode());
		map.put("model.valueDate", getValueDate());
		map.put("token", BdsmUtil.generateToken(this.getTokenKey(), this.getTzToken()));

		result = HttpUtil.request(this.getBdsmHost() + LIST_CURRENCYCODE, map);

		try {
			resultMap = (HashMap<String, ? extends Object>) JSONUtil.deserialize(result);
			reqResult = (String) resultMap.get("jsonStatus");
		}
		catch (JSONException ex) {
			this.getLogger().fatal(ex, ex);
		}
        
		return SUCCESS;
    }

    /**
     * 
     * @return
     */
    @Override
    public String doAdd() {
        return SUCCESS;
    }


    /**
     * 
     * @return
     */
    @Override
    public String doEdit() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * 
     * @return
     */
    @Override
    public String doDelete() {
        throw new UnsupportedOperationException("Not supported yet.");
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
