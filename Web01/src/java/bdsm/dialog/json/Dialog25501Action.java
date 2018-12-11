/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.dialog.json;

import bdsm.model.AmortizeCustomOpening;
import bdsm.util.BdsmUtil;
import bdsm.util.HttpUtil;
import com.opensymphony.xwork2.ModelDriven;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

import org.apache.struts2.json.JSONUtil;

/**
 *
 * @author NCBS
 */
public class Dialog25501Action extends BaseDialogAction {

	private static final String ACTION_LIST = "AmortOpenReg_list.action";
	private String giftCode;
	private String codProd;
	private String mode;
	private String AccountNo;
	private String Term;
	private String state;

    /**
     * 
     */
    public Dialog25501Action() {
	}

    /**
     * 
     * @return
     * @throws Exception
     */
    @Override
	public List doList() throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		getLogger().info("GIFTCODE :" + giftCode);
		getLogger().info("PRODUCT CODE :" + codProd);
		getLogger().info("MODE :" + mode);
		getLogger().info("STATE :" + getState());

		// get Different query for Delete and Add
		if (mode.equalsIgnoreCase("1")) { // Add
			params.put("custom.Mode", "1");
		} else if (mode.equalsIgnoreCase("2")) {
			params.put("custom.Mode", "2");
		} else {
			params.put("custom.Mode", "3");
		}
		// get Different query for Term and Gift Code
		if (getState().equalsIgnoreCase("TERM")) { // Add
			params.put("custom.state", "Term");
			if (StringUtils.isBlank(codProd)) {
				codProd = "0";
			}
			params.put("custom.giftCode", giftCode.toUpperCase());
			params.put("custom.codProd", codProd);
		} else if (getState().equalsIgnoreCase("GIFT")) {
			params.put("custom.state", "Gift");
			if (StringUtils.isBlank(codProd)) {
				codProd = "0";
			}
			params.put("custom.giftCode", giftCode.toUpperCase());
			params.put("custom.codProd", codProd);
		} else {
			params.put("custom.state", "Account");
		}
		params.put("custom.acctNo", AccountNo);
		params.put("token", BdsmUtil.generateToken(getTokenKey(), getTzToken()));

		String result = HttpUtil.request(getBdsmHost() + ACTION_LIST, params);

		HashMap ret = (HashMap) JSONUtil.deserialize(result);

		List listRet = (List) ret.get("modelLIst");
		String status = (String) ret.get("jsonStatus");

		addActionError(status);

		return listRet;
	}

	/**
	 * @return the giftCode
	 */
	public String getGiftCode() {
		return giftCode;
	}

	/**
	 * @param giftCode the giftCode to set
	 */
	public void setGiftCode(String giftCode) {
		this.giftCode = giftCode;
	}

	/**
	 * @return the codProd
	 */
	public String getCodProd() {
		return codProd;
	}

	/**
	 * @param codProd the codProd to set
	 */
	public void setCodProd(String codProd) {
		this.codProd = codProd;
	}

	/**
	 * @return the mode
	 */
	public String getMode() {
		return mode;
	}

	/**
	 * @param mode the mode to set
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	/**
	 * @return the AccountNo
	 */
	public String getAccountNo() {
		return AccountNo;
	}

	/**
	 * @param AccountNo the AccountNo to set
	 */
	public void setAccountNo(String AccountNo) {
		this.AccountNo = AccountNo;
	}

	/**
	 * @return the Term
	 */
	public String getTerm() {
		return Term;
	}

	/**
	 * @param Term the Term to set
	 */
	public void setTerm(String Term) {
		this.Term = Term;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
}
