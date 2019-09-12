package bdsm.dialog.json;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.json.JSONUtil;

import bdsm.dialog.json.BaseDialogAction;
import bdsm.util.BdsmUtil;
import bdsm.util.HttpUtil;


/**
 * 
 * @author bdsm
 */
public class Dialog22201Action extends BaseDialogAction {
	private static final String TPINHOST_RETRIEVE_ACCOUNTS = "TPINHost_retrieveAccounts.action";
	private String cardNo;

    /**
     * 
     * @return
     * @throws Exception
     */
    @Override
	public List doList() throws Exception {
		this.getLogger().info("cardNo : " + this.cardNo);
		
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("cardNo", this.cardNo);
		params.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));
		
		String result = HttpUtil.request(getBdsmHost() + TPINHOST_RETRIEVE_ACCOUNTS, params);
		this.getLogger().info(result);
        
        Map<String, Object> ret = (HashMap<String, Object>)JSONUtil.deserialize(result);
        List<Object[]> listRet = (List<Object[]>)ret.get("modelList");
        String statusRet = (String)ret.get("jsonStatus");
        addActionError(statusRet);

        return listRet;
	}
	
    /**
     * 
     * @param cardNo
     */
    public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

}
