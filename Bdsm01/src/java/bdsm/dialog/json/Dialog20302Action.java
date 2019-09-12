package bdsm.dialog.json;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;

import bdsm.util.BdsmUtil;
import bdsm.util.HttpUtil;
import bdsm.web.Constant;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.validation.SkipValidation;


/**
 * 
 * @author user
 */
@SuppressWarnings({ "serial", "rawtypes" })
public class Dialog20302Action extends BaseDialogAction {
	private static final String ACTION_GET_LIST_CCY = "baCcyCode_list.action";
	private static final String ACTION = "mfcUdMaster_list.action";
	private static final String ACTION_LLD = "mfcUdMasterLLD_callMethod.action";

	private Integer noCif;
	private String noUd;
	private List listCcy;

    /**
     * 
     * @return
     */
    @SkipValidation
	public String getListCcyCode() {
		Map<String, String> map = new HashMap<String, String>();
		String result;
		HashMap resultMap;
		String reqResult = null;
		String errorCode = null;

		map.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));

		result = HttpUtil.request(this.getBdsmHost() + ACTION_GET_LIST_CCY, map);

		try {
			resultMap = (HashMap) JSONUtil.deserialize(result);
			reqResult = (String) resultMap.get("jsonStatus");
			errorCode = (String) resultMap.get("errorCode");
			listCcy = (List) resultMap.get("modelList");
		}
		catch (JSONException ex) {
			this.getLogger().fatal(ex, ex);
		}

		return ActionSupport.SUCCESS;
	}
    /**
     * 
     * @return
     * @throws Exception
     */
    @Override
	public List doList() throws Exception {
		if (this.noUd == null)
			this.noUd = "";

		Map<String, String> params = new HashMap<String, String>();
		params.put("model.compositeId.noCif", String.valueOf(this.noCif));
		params.put("model.compositeId.noUd", this.noUd);
		params.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));

		String result;
		getLogger().debug("CONTEXT :" + this.session);
		if ("28302".equals(this.session.get(Constant.C_IDMENU))) {
			params.put("methodName", "listByCIFNo");
			result = HttpUtil.request(this.getBdsmHost() + ACTION_LLD, params);
		}
		else
			result = HttpUtil.request(this.getBdsmHost() + ACTION, params);

		HashMap ret = (HashMap) JSONUtil.deserialize(result);
		List listRet = (List) ret.get("modelList");
		String status = (String) ret.get("jsonStatus");
		addActionError(status);

		return listRet;
	}

    /**
     * 
     * @return
     */
    public String checkUDNo() {
		Map<String, String> map = new HashMap<String, String>();
		String result;
		HashMap resultMap;
		String reqResult = null;
		String errorCode = null;

		map.put("methodName", "listByUDNo");
		map.put("model.compositeId.noUd", this.noUd);
		map.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));

		result = HttpUtil.request(this.getBdsmHost() + "mfcUdMaster_callMethod", map);

		try {
			resultMap = (HashMap) JSONUtil.deserialize(result);
			reqResult = (String) resultMap.get("jsonStatus");
			errorCode = (String) resultMap.get("errorCode");
			List listUD = (List) resultMap.get("modelList");

			if ((listUD == null) || (listUD.size() == 0))
				this.noUd = "";
		}
		catch (JSONException ex) {
			this.getLogger().fatal(ex, ex);
		}

		return ActionSupport.SUCCESS;
	}


	/**
	 * @param noCif the noCif to set
	 */
	public void setNoCif(Integer noCif) {
		this.noCif = noCif;
	}

	/**
	 * @return the noUd
	 */
	public String getNoUd() {
		return this.noUd;
	}

	/**
	 * @param noUd the noUd to set
	 */
	public void setNoUd(String noUd) {
		this.noUd = noUd;
	}

	/**
	 * @return the listCcy
	 */
	public List getListCcy() {
		return this.listCcy;
	}

	/**
	 * @param listCcy the listCcy to set
	 */
	public void setListCcy(List listCcy) {
		this.listCcy = listCcy;
	}
}
