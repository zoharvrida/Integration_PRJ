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
public class Tab1GridAction extends BaseDialogAction {
	private static final String ACTION = "fcyTxnSummLimUD_list.action";
	private final String ACTION_LLD = "mfcUdMasterLLD_callMethod.action";
	private String flgAcct;
	private String noAccount;

	
    /**
     * 
     * @return
     * @throws Exception
     */
    @Override
	@SuppressWarnings("rawtypes")
	public List doList() throws Exception {
		Map<String, String> params = new HashMap<String, String>();

		this.getLogger().debug("flgAcct : '" + this.flgAcct + "'");
		this.getLogger().debug("noAcct : '" + this.noAccount + "'");
		this.getLogger().debug("idMenu : '" + this.session.get("idMenu") + "'");
		
		String result = null;

		if ("28201".equals(this.session.get("idMenu"))) { // LLD
			if ("0".equals(this.flgAcct)) {
				params.put("model.compositeId.noCif", this.noAccount.replaceFirst("^[Cc][Ii][Ff]\\s*:\\s*", ""));
				params.put("methodName", "listByCIFNo");
			}
			else {
				params.put("model.remarks", this.noAccount);
				params.put("methodName", "listByCodAcctNo");
			}
			params.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));
	
			result = HttpUtil.request(getBdsmHost() + ACTION_LLD, params);
		}
		else { // Valas
			params.put("model.flgAcct", this.flgAcct);
			params.put("model.noAcct", this.noAccount);
			params.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));
	
			result = HttpUtil.request(getBdsmHost() + ACTION, params);
		}

		HashMap ret = (HashMap) JSONUtil.deserialize(result);
		List listRet = (List) ret.get("modelList");
		String statusRet = (String) ret.get("jsonStatus");
		addActionError(statusRet);

		return listRet;
	}


	/**
	 * @return the flgAcct
	 */
	public String getFlgAcct() {
		return this.flgAcct;
	}

	/**
	 * @param flgAcct the flgAcct to set
	 */
	public void setFlgAcct(String flgAcct) {
		this.flgAcct = flgAcct;
	}

	/**
	 * @return the noAccount
	 */
	public String getNoAccount() {
		return this.noAccount;
	}

	/**
	 * @param noAccount the noAccount to set
	 */
	public void setNoAccount(String noAccount) {
		this.noAccount = noAccount;
	}

}
