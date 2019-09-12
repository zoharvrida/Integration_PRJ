package bdsm.web.menu20201;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.json.JSONUtil;

import bdsm.dialog.json.BaseDialogAction;
import bdsm.util.BdsmUtil;
import bdsm.util.HttpUtil;


/**
 * 
 * @author v00013493
 */
@SuppressWarnings("serial")
public class Tab2GridAction extends BaseDialogAction {
	private static final String ACTION = "fcyTxnSummPendingTxn_list.action";
	private String noAccount;


    /**
     * 
     * @return
     * @throws Exception
     */
    @Override
	@SuppressWarnings("rawtypes")
	public List doList() throws Exception {
		this.getLogger().debug("noAccount: " + this.noAccount);
		this.getLogger().debug("idMenu: " + this.session.get("idMenu"));
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("model.noAcct", this.noAccount);
		params.put("model.idTxn", (String) this.session.get("idMenu"));
		params.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));

		String result = HttpUtil.request(getBdsmHost() + ACTION, params);

		HashMap ret = (HashMap) JSONUtil.deserialize(result);
		List listRet = (List) ret.get("modelList");
		String statusRet = (String) ret.get("jsonStatus");
		addActionError(statusRet);

		return listRet;
	}


	/**
	 * @param noAccount the noAccount to set
	 */
	public void setNoAccount(String noAccount) {
		this.noAccount = noAccount;
	}
}
